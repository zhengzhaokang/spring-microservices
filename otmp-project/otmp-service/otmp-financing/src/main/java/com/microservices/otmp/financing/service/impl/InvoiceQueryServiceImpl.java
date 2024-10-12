package com.microservices.otmp.financing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.constant.StateConstants;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.financing.constant.FinancingConstant;
import com.microservices.otmp.financing.constant.InvoiceConstants;
import com.microservices.otmp.financing.domain.dto.*;
import com.microservices.otmp.financing.domain.entity.BankDo;
import com.microservices.otmp.financing.domain.entity.EntityCompanyCodeDo;
import com.microservices.otmp.financing.domain.entity.EntityDo;
import com.microservices.otmp.financing.domain.entity.FinanceBatchExportDo;
import com.microservices.otmp.financing.domain.param.invoice.FinancedInvoiceExportParam;
import com.microservices.otmp.financing.domain.param.invoice.InvoiceQueryParam;
import com.microservices.otmp.financing.domain.vo.finance.FinancingRateDashboard;
import com.microservices.otmp.financing.domain.vo.finance.FinancingRateVO;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceBatchSummaryVo;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceQueryDto;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceQueryVO;
import com.microservices.otmp.financing.enums.RatePeriodEnum;
import com.microservices.otmp.financing.mapper.*;
import com.microservices.otmp.financing.service.InvoiceQueryService;
import com.microservices.otmp.financing.util.TimeUtil;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.feign.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InvoiceQueryServiceImpl implements InvoiceQueryService {

    @Autowired
    private InvoiceQueryMapper invoiceQueryMapper;
    @Autowired
    private FinanceBatchMapper financeBatchMapper;
    @Autowired
    private BankMapper bankMapper;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private EntityCompanyCodeMapper entityCompanyCodeMapper;
    @Autowired
    private RemoteUserService remoteUserService;

    private OtmpException throwEx(Long errCode) {
        return new OtmpException(DefaultErrorMessage.getErrorMessage(errCode), errCode.intValue());
    }

    private SysUser getSysUserByUserId(Long userId) {
        log.info("getSysUserByUserId,userId:{}", userId);
        SysUser sysUser = remoteUserService.selectSysUserByUserId(userId);
        if (sysUser == null) {
            log.info("submit,sysUser is null,userId:{}", userId);
            throw throwEx(DefaultErrorMessage.ERR_USER_NOT_EXIST);
        }
        return sysUser;
    }

    private String getSupplierIdByUserId(Long userId) {
        SysUser sysUser = getSysUserByUserId(userId);
        String supplierId = sysUser.getSupplierId();
        if (StringUtils.isEmpty(supplierId)) {
            log.error("getSupplierIdByUserId,supplierId is null,userId:{}", userId);
            throw throwEx(DefaultErrorMessage.SUPPLIER_ID_NOT_FOUND);
        }
        return supplierId;
    }

    @Override
    public Integer submittedCount(InvoiceQueryParam param) {
        return invoiceQueryMapper.submittedCount(param);
    }

    @Override
    public InvoiceQueryVO submittedList(InvoiceQueryParam param) {
        String supplierId = getSupplierIdByUserId(param.getUserId());
        log.info("submittedList,supplierId:{},param:{}", supplierId, JsonUtil.toJSON(param));
        param.setSupplierId(getSupplierIdByUserId(param.getUserId()));
        // 设置类型
        param.setInvoiceType(InvoiceConstants.convertType(param.getType()));
        // 分页参数
        setPageParamInfo(param);
        InvoiceQueryVO invoiceQueryVO = new InvoiceQueryVO();
        Integer submittedCount = submittedCount(param);
        if (submittedCount == null || submittedCount < 1) {
            invoiceQueryVO.setCount(0);
            invoiceQueryVO.setInvoiceQueryDtoList(null);
            return invoiceQueryVO;
        }
        invoiceQueryVO.setCount(submittedCount);
        List<InvoiceQueryDaoDto> invoiceQueryDaoDtos = invoiceQueryMapper.submittedList(param);
        List<InvoiceQueryDto> invoiceQueryDtoList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(invoiceQueryDaoDtos)) {
            invoiceQueryVO.setInvoiceQueryDtoList(invoiceQueryDtoList);
            return invoiceQueryVO;
        }
        BeanUtils.copyListProperties(invoiceQueryDaoDtos, invoiceQueryDtoList, InvoiceQueryDto.class);
        // 处理返回字段信息
        handleResultInfo(invoiceQueryDtoList, true);
        invoiceQueryVO.setInvoiceQueryDtoList(invoiceQueryDtoList);
        return invoiceQueryVO;
    }

    private void setPageParamInfo(InvoiceQueryParam param) {
        param.setPageNum((param.getPageNum() == null || param.getPageNum() < 1) ? Constants.DEFAULT_PAGE_NUM : param.getPageNum());
        param.setPageSize((param.getPageSize() == null || param.getPageSize() < 1) ? Constants.DEFAULT_PAGE_SIZE : param.getPageSize());
        param.setOffset((param.getPageNum() - 1) * param.getPageSize());
        param.setLimit(param.getPageSize());
    }

    private void handleResultInfo(List<InvoiceQueryDto> invoiceQueryDtoList, Boolean queryBank) {
        if (queryBank != null && queryBank) {
            Example listCondition = new Example(BankDo.class);
            listCondition.createCriteria().andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
            listCondition.setOrderByClause("create_time DESC");
            List<BankDo> bankList = bankMapper.selectByExample(listCondition);
            if (CollectionUtils.isEmpty(bankList)) {
                return;
            }
            Map<String, String> bankMap = bankList.stream().collect(Collectors.toMap(BankDo::getId, BankDo::getBankName, (key1, key2) -> key2));
            //设置bankName
            for (InvoiceQueryDto invoiceQueryDto : invoiceQueryDtoList) {
                invoiceQueryDto.setBankName(bankMap.get(invoiceQueryDto.getBankId()));
            }
        }

        Example entityCondition = new Example(EntityDo.class);
        entityCondition.createCriteria().andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        List<EntityDo> entities = entityMapper.selectByExample(entityCondition);
        if (CollectionUtils.isEmpty(entities)) {
            return;
        }
        Map<Long, EntityDo> entityMap = entities.stream().collect(Collectors.toMap(EntityDo::getId, Function.identity(), (key1, key2) -> key2));
        for (InvoiceQueryDto invoiceQueryDto : invoiceQueryDtoList) {
            //设置entity信息
            if (invoiceQueryDto != null && invoiceQueryDto.getEntityId() != null && MapUtils.isNotEmpty(entityMap)
                    && entityMap.get(Long.parseLong(invoiceQueryDto.getEntityId())) != null) {
                invoiceQueryDto.setEntityName(entityMap.get(Long.parseLong(invoiceQueryDto.getEntityId())).getEntityName());
            }
            if (invoiceQueryDto != null && invoiceQueryDto.getAmount() != null) {
                invoiceQueryDto.setAmount(invoiceQueryDto.getAmount().abs());
            }
        }

        // 处理日期格式
        handleDateFormat(invoiceQueryDtoList);
    }

    private void handleDateFormat(List<InvoiceQueryDto> invoiceQueryDtoList) {
        if (CollectionUtils.isEmpty(invoiceQueryDtoList)) {
            return;
        }
        try {
            for (InvoiceQueryDto invoiceQueryDto : invoiceQueryDtoList) {
                if (invoiceQueryDto.getMaturityDate() != null) {
                    invoiceQueryDto.setMaturityDateF(DateUtils.dateFormat(invoiceQueryDto.getMaturityDate(), DateUtils.DATE_PATTERN));
                }
                if (invoiceQueryDto.getSubmissionDate() != null) {
                    invoiceQueryDto.setSubmissionDateF(DateUtils.dateFormat(invoiceQueryDto.getSubmissionDate(), DateUtils.DATE_PATTERN));
                }
                if (StringUtils.isNotBlank(invoiceQueryDto.getIssueDate())) {
                    Date issueDate = DateUtils.parseDate(invoiceQueryDto.getIssueDate(), DateUtils.DATE_NO_PATTERN);
                    invoiceQueryDto.setIssueDateF(DateUtils.dateFormat(issueDate, DateUtils.DATE_PATTERN));
                }
                if (StringUtils.isNotBlank(invoiceQueryDto.getDueDate())) {
                    Date invoiceDate = DateUtils.parseDate(invoiceQueryDto.getDueDate(), DateUtils.DATE_NO_PATTERN);
                    invoiceQueryDto.setDueDateF(DateUtils.dateFormat(invoiceDate, DateUtils.DATE_PATTERN));
                }
                if (invoiceQueryDto.getDiscountDate() != null) {
                    invoiceQueryDto.setDiscountDateF(DateUtils.dateFormat(invoiceQueryDto.getDiscountDate(), DateUtils.DATE_PATTERN));
                }
                if (invoiceQueryDto.getConfirmedMaturityDate() != null) {
                    invoiceQueryDto.setConfirmedMaturityDateF(DateUtils.dateFormat(invoiceQueryDto.getConfirmedMaturityDate(), DateUtils.DATE_PATTERN));
                }
            }
        } catch (ParseException e) {
            log.error("### InvoiceQueryServiceImpl handleDateFormat error", e);
        }
    }

    @Override
    public Integer rejectedCount(InvoiceQueryParam param) {
        return invoiceQueryMapper.rejectedCount(param);
    }

    @Override
    public InvoiceQueryVO rejectedList(InvoiceQueryParam param) {
        String supplierId = getSupplierIdByUserId(param.getUserId());
        log.info("submittedList,supplierId:{},param:{}", supplierId, JsonUtil.toJSON(param));
        param.setSupplierId(supplierId);
        // 设置类型
        param.setInvoiceType(InvoiceConstants.convertType(param.getType()));
        // 分页参数
        setPageParamInfo(param);
        InvoiceQueryVO invoiceQueryVO = new InvoiceQueryVO();
        Integer count = rejectedCount(param);
        if (count == null || count < 1) {
            invoiceQueryVO.setCount(0);
            invoiceQueryVO.setInvoiceQueryDtoList(null);
            return invoiceQueryVO;
        }
        invoiceQueryVO.setCount(count);
        List<InvoiceQueryDaoDto> invoiceQueryDaoDtos = invoiceQueryMapper.rejectedList(param);
        List<InvoiceQueryDto> invoiceQueryDtoList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(invoiceQueryDaoDtos)) {
            invoiceQueryVO.setInvoiceQueryDtoList(invoiceQueryDtoList);
            return invoiceQueryVO;
        }
        BeanUtils.copyListProperties(invoiceQueryDaoDtos, invoiceQueryDtoList, InvoiceQueryDto.class);
        // 返回结果存在entityId为空的，需要根据companyCode进行处理
        handleEntityName(invoiceQueryDtoList);
        // 处理返回字段信息
        handleResultInfo(invoiceQueryDtoList, true);
        invoiceQueryVO.setInvoiceQueryDtoList(invoiceQueryDtoList);

        return invoiceQueryVO;
    }

    private void handleEntityName(List<InvoiceQueryDto> invoiceQueryDtoList) {
        List<EntityCompanyCodeDo> entityCompanyCode = entityCompanyCodeMapper.getEntityCompanyCode(Sets.newHashSet());
        if (CollectionUtils.isEmpty(entityCompanyCode)) {
            return;
        }
        Map<String, EntityCompanyCodeDo> entityCompanyCodeMap = entityCompanyCode.stream().collect(Collectors.toMap(
                EntityCompanyCodeDo::getCompanyCode, Function.identity(), (key1, key2) -> key2));
        for (InvoiceQueryDto invoiceQueryDto : invoiceQueryDtoList) {
            EntityCompanyCodeDo entityCompanyCodeDo = entityCompanyCodeMap.get(invoiceQueryDto.getCompanyCode());
            if (entityCompanyCodeDo != null && StringUtils.isBlank(invoiceQueryDto.getEntityId())) {
                invoiceQueryDto.setEntityId(String.valueOf(entityCompanyCodeDo.getEntityId()));
            }
        }
    }

    @Override
    public Integer financedCount(InvoiceQueryParam param) {
        return invoiceQueryMapper.financedCount(param);
    }

    @Override
    public InvoiceQueryVO financedList(InvoiceQueryParam param) {
        String supplierId = getSupplierIdByUserId(param.getUserId());
        log.info("financedList,supplierId:{},param:{}", supplierId, JsonUtil.toJSON(param));
        param.setSupplierId(supplierId);
        // 分页参数
        setPageParamInfo(param);
        InvoiceQueryVO invoiceQueryVO = new InvoiceQueryVO();
        Integer count = financedCount(param);
        if (count == null || count < 1) {
            invoiceQueryVO.setCount(0);
            invoiceQueryVO.setInvoiceQueryDtoList(null);
            return invoiceQueryVO;
        }
        invoiceQueryVO.setCount(count);
        List<InvoiceQueryDaoDto> invoiceQueryDaoDtos = invoiceQueryMapper.financedList(param);
        List<InvoiceQueryDto> invoiceQueryDtoList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(invoiceQueryDaoDtos)) {
            invoiceQueryVO.setInvoiceQueryDtoList(invoiceQueryDtoList);
            return invoiceQueryVO;
        }
        BeanUtils.copyListProperties(invoiceQueryDaoDtos, invoiceQueryDtoList, InvoiceQueryDto.class);
        // 处理返回字段信息
        handleResultInfo(invoiceQueryDtoList, false);
        invoiceQueryVO.setInvoiceQueryDtoList(invoiceQueryDtoList);

        return invoiceQueryVO;
    }

    @Override
    public List<EntityDto> getEntityInfo() {
        Example entityCondition = new Example(EntityDo.class);
        entityCondition.createCriteria().andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        List<EntityDo> entityDos = entityMapper.selectByExample(entityCondition);
        List<EntityDto> entityDtos = Lists.newArrayList();
        if (CollectionUtils.isEmpty(entityDos)) {
            return entityDtos;
        }
        BeanUtils.copyListProperties(entityDos, entityDtos, EntityDto.class);
        entityDtos.forEach(entityDto -> {
            entityDto.setEntityId(String.valueOf(entityDto.getId()));
        });
        return entityDtos;
    }

    @Override
    public FinancingRateDashboard getFinancingRate(InvoiceQueryParam param) {
        FinancingRateDashboard financingRateDashboard = new FinancingRateDashboard();

        FinancingRateDTO financingRateDTO = new FinancingRateDTO();
        financingRateDTO.setRateType(FinancingConstant.RATE_TYPE);
        financingRateDTO.setRatePeriod(RatePeriodEnum.getRateName(param.getRateType()));
        financingRateDTO.setStartDate(param.getStartDate());
        financingRateDTO.setEndDate(param.getEndDate());

        setPageParamInfo(param);
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<FinancingRateDTO> financingRateDaoDtos = invoiceQueryMapper.selectRate(financingRateDTO);

        if (CollectionUtils.isEmpty(financingRateDaoDtos)) {
            return null;
        }
        PageInfo<FinancingRateDTO> pageInfo = new PageInfo<>(financingRateDaoDtos);
        financingRateDashboard.setCount((int) pageInfo.getTotal());
        List<FinancingRateVO> financingRateVOS = Lists.newArrayList();
        BeanUtils.copyListProperties(financingRateDaoDtos, financingRateVOS, FinancingRateVO.class);

        try {
            financingRateVOS.forEach(item -> {
                if (item.getRateDate() != null) {
                    item.setRateDateF(DateUtils.dateFormat(item.getRateDate(), DateUtils.DATE_PATTERN));
                }
            });
        } catch (Exception e) {
            log.error("### InvoiceQueryServiceImpl getFinancingRate error", e);
        }

        financingRateDashboard.setFinancingRateVOList(financingRateVOS);
        List<BigDecimal> bigDecimals = financingRateVOS.stream().map(FinancingRateVO::getRate).collect(Collectors.toList());
        bigDecimals.sort(BigDecimal::compareTo);
        financingRateDashboard.setMinRate(bigDecimals.get(0).toString());
        financingRateDashboard.setMaxRate(bigDecimals.get(bigDecimals.size() - 1).toString());

        return financingRateDashboard;
    }

    @Override
    public InvoiceQueryVO financedDetailList(InvoiceQueryParam param) {
        String supplierId = getSupplierIdByUserId(param.getUserId());
        log.info("financedDetailList,supplierId:{},param:{}", supplierId, JsonUtil.toJSON(param));
        param.setSupplierId(supplierId);
        InvoiceQueryVO result = new InvoiceQueryVO();
        setPageParamInfo(param);
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<InvoiceQueryDaoDto> invoiceDos = invoiceQueryMapper.financedDetailList(param.getBatchNumber(), InvoiceConstants.convertType(param.getType()));
        if (CollectionUtils.isEmpty(invoiceDos)) {
            result.setCount(0);
            result.setInvoiceQueryDtoList(new ArrayList<>());
            return result;
        }
        PageInfo<InvoiceQueryDaoDto> pageInfo = new PageInfo<>(invoiceDos);
        result.setCount((int) pageInfo.getTotal());
        List<InvoiceQueryDto> resultList = new ArrayList<>();
        BeanUtils.copyListProperties(invoiceDos, resultList, InvoiceQueryDto.class);
        handleResultInfo(resultList, false);
        result.setInvoiceQueryDtoList(resultList);
        return result;
    }

    @Override
    public InvoiceBatchSummaryVo financedBatchSummary(FinancedInvoiceExportParam param) {
        log.info("financedBatchSummary,param:{}", JsonUtil.toJSON(param));
        FinancedExportDataDto queryResult = queryBatchExportData(param, false);
        log.info("financedBatchSummary,queryResult:{}", JsonUtil.toJSON(queryResult));
        InvoiceBatchSummaryVo result = new InvoiceBatchSummaryVo();
        result.setSummaryList(queryResult.getSummaryExportList());
        return result;
    }

    private FinancedExportDataDto queryBatchExportData(FinancedInvoiceExportParam param, boolean export) {
        FinancedExportDataDto result = new FinancedExportDataDto();
        List<InvoiceQueryDaoDto> debits = invoiceQueryMapper.financedDetailListByBatches(param.getBatchNumberList(), InvoiceConstants.INVOICE_TYPE_DEBIT_STR);
        List<InvoiceQueryDaoDto> credits = invoiceQueryMapper.financedDetailListByBatches(param.getBatchNumberList(), InvoiceConstants.INVOICE_TYPE_CREDIT_STR);

        result.setDebits(debits);
        result.setCredits(credits);

        Map<String, List<InvoiceQueryDaoDto>> debitBatchGroup = debits.parallelStream().collect(Collectors.groupingBy(InvoiceQueryDaoDto::getBatchNumber));
        Map<String, List<InvoiceQueryDaoDto>> creditBatchGroup = credits.parallelStream().collect(Collectors.groupingBy(InvoiceQueryDaoDto::getBatchNumber));
        result.setDebitBatchGroup(debitBatchGroup);
        result.setCreditBatchGroup(creditBatchGroup);

        List<FinancedSummaryExportDto> summaryExportList = new ArrayList<>();
        List<FinancedExportDto> debitExportList = new ArrayList<>();
        List<FinancedExportDto> creditExportList = new ArrayList<>();

        List<FinanceBatchExportDo> batches = financeBatchMapper.selectByBatchNumbers(param.getBatchNumberList());
        for (FinanceBatchExportDo batch : batches) {
            String batchNum = batch.getBatchNumber();
            List<InvoiceQueryDaoDto> batchDebits = debitBatchGroup.get(batchNum);
            List<InvoiceQueryDaoDto> batchCredits = creditBatchGroup.get(batchNum);
            int batchDebitCount = 0;
            int batchCreditCount = 0;
            BigDecimal batchDebitsTotal = new BigDecimal("0");
            BigDecimal batchCreditsTotal = new BigDecimal("0");

            List<FinancedExportDto> tempDebitExportList = new ArrayList<>();
            List<FinancedExportDto> tempCreditExportList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(batchDebits)) {
                batchDebitCount = batchDebits.size();
                for (InvoiceQueryDaoDto batchDebit : batchDebits) {
                    batchDebitsTotal = batchDebitsTotal.add(batchDebit.getAmount());
                    if (export) {
                        tempDebitExportList.add(createInvoiceExportDto(batchDebit).setInvoiceType(InvoiceConstants.INVOICE_TYPE_DEBIT_STR));
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(batchCredits)) {
                batchCreditCount = batchCredits.size();
                for (InvoiceQueryDaoDto batchCredit : batchCredits) {
                    batchCreditsTotal = batchCreditsTotal.add(batchCredit.getAmount());
                    if (export) {
                        tempCreditExportList.add(createInvoiceExportDto(batchCredit).setInvoiceType(InvoiceConstants.INVOICE_TYPE_CREDIT_STR));
                    }
                }
            }
            if (export) {
                debitExportList.addAll(tempDebitExportList);
                creditExportList.addAll(tempCreditExportList);
            }
            FinancedSummaryExportDto exportDto = createExportDto(batch);
            exportDto.setInvoiceAmount(batchDebitsTotal.toPlainString());
            exportDto.setInvoiceCount(batchDebitCount);
            exportDto.setCreditAmount(batchCreditsTotal.toPlainString());
            exportDto.setCreditCount(batchCreditCount);
            summaryExportList.add(exportDto);

        }
        if (export) {
            result.setDebitExportList(debitExportList);
            result.setCreditExportList(creditExportList);
        }
        result.setSummaryExportList(summaryExportList);
        log.info("queryBatchExportData,result:{}", JsonUtil.toJSON(result));
        return result;
    }

    @Override
    public FinancedExportDataDto financedDetailExport(FinancedInvoiceExportParam param, HttpServletResponse response) {
        log.info("financedDetailExport,param:{}", JsonUtil.toJSON(param));
        return queryBatchExportData(param, true);
//        List<Class<? extends ExportDto>> dataClasses = new ArrayList<>();
//        List<List<?>> data = new ArrayList<>();

//        dataClasses.add(FinancedSummaryExportDto.class);
//        data.add(queryResult.getSummaryExportList());
//        dataClasses.add(FinancedExportDto.class);
//        data.add(queryResult.getDebitExportList());
//        dataClasses.add(FinancedExportDto.class);
//        data.add(queryResult.getCreditExportList());
//        log.info("financedDetailExport,dataClasses:{},data:{}", dataClasses, JsonUtil.toJSON(data));
//        NewExcelUtil util = new NewExcelUtil(dataClasses);
//        util.createWorkbook();
//        util.exportMoreSheetExcel(response, data, new String[]{"summary", "debits", "credits"});
    }

    private FinancedExportDto createInvoiceExportDto(InvoiceQueryDaoDto invoice) {
        FinancedExportDto dto = new FinancedExportDto(invoice);
        if (invoice.getSubmissionDate() != null) {
            dto.setSubmittedDate(DateUtils.dateFormat(invoice.getSubmissionDate(), DateUtils.DATE_PATTERN));
        }
        dto.setIssueDate(invoice.getIssueDate());
        dto.setDueDate(invoice.getDueDate());
        Date md = invoice.getConfirmedMaturityDate() == null ? invoice.getMaturityDate() : invoice.getConfirmedMaturityDate();
        if (md != null) {
            dto.setMaturityDate(DateUtils.dateFormat(md, DateUtils.DATE_PATTERN));
        }
        if (invoice.getDiscountDate() != null) {
            dto.setDiscountDateF(DateUtils.dateFormat(invoice.getDiscountDate(), DateUtils.DATE_PATTERN));
        }
        return dto;
    }

    private FinancedSummaryExportDto createExportDto(FinanceBatchExportDo batch) {
        FinancedSummaryExportDto dto = new FinancedSummaryExportDto();
        dto.setBatchNumber(batch.getBatchNumber());
//        dto.setSupplierName();
//        dto.setEntityName();
        dto.setCurrency(batch.getSubmitCurrency());
        dto.setNetFinancedAmount(batch.getDiscount() == null ? "0" : batch.getDiscount().toPlainString());
        if (batch.getDiscountDate() != null) {
            dto.setDiscountDate(DateUtils.dateFormat(localDateTimeToDate(batch.getDiscountDate()), DateUtils.DATE_PATTERN));
        }
        dto.setAdjustedDueDate(TimeUtil.convert(batch.getMaturityDate()));
        dto.setNoOfTenorDays(batch.getTenor());
        dto.setConfirmedDiscountRate(batch.getInterestRate() == null ? "0" : batch.getInterestRate().toPlainString());
        dto.setConfirmedDiscountCharge(String.valueOf(batch.getDiscountAmount() == null ? 0.00 : batch.getDiscountAmount()));
        dto.setSupplierName(batch.getSupplierName());
        dto.setEntityName(batch.getEntityName());
        return dto;
    }

    private Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
