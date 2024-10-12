package com.microservices.otmp.financing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.constant.StateConstants;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.InvoiceStatusEnum;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.kafka.listener.KafkaCallback;
import com.microservices.otmp.common.kafka.util.KafkaSend;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.domain.BankLimit;
import com.microservices.otmp.domain.InvoiceAp;
import com.microservices.otmp.financing.config.KafkaFactory;
import com.microservices.otmp.financing.constant.InvoiceConstants;
import com.microservices.otmp.financing.constant.NoticeConstant;
import com.microservices.otmp.financing.constant.StringConstants;
import com.microservices.otmp.financing.constant.SupplierConstants;
import com.microservices.otmp.financing.domain.dto.EntityCodeIdDto;
import com.microservices.otmp.financing.domain.dto.InvoiceDTO;
import com.microservices.otmp.financing.domain.dto.NoticeSendDto;
import com.microservices.otmp.financing.domain.entity.*;
import com.microservices.otmp.financing.domain.param.invoice.*;
import com.microservices.otmp.financing.domain.vo.bank.BankVo;
import com.microservices.otmp.financing.domain.vo.invoice.*;
import com.microservices.otmp.financing.mapper.*;
import com.microservices.otmp.financing.remote.RemoteSupplierDataService;
import com.microservices.otmp.financing.remote.result.MaturityDateAmountResult;
import com.microservices.otmp.financing.remote.result.RemoteSysUserVO;
import com.microservices.otmp.financing.service.InvoiceService;
import com.microservices.otmp.financing.util.RedisKeyUtil;
import com.microservices.otmp.financing.util.SendMailUtil;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.feign.RemoteUserAnchorService;
import com.microservices.otmp.system.feign.RemoteUserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private static final Integer INSERT_BATCH_SIZE = 1000;
    private static final Integer UPDATE_BATCH_SIZE = 5000;
    private static final Integer UPDATE_BATCH_ID_SIZE = 20000;
    private static final Integer QUERY_SIZE = 10000;
    private static final Integer DISCOUNT_AMOUNT_DENOMINATOR = 360;
    private static final Integer TIME_EXPIRY = 180;
    private static final Integer FORBIDDEN_REASON_INVOICE_TENOR = 1;
    private static final Integer FORBIDDEN_REASON_FINANCE_TENOR = 2;
    @Value("${kafka.topic.invoice-submit}")
    private String invoiceSubmitTopic;
    @Value("${kafka.topic.notice}")
    private String sendNoticeTopic;
    @Lazy
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private KafkaSend sender;
    @Autowired
    private FinanceBatchMapper financeBatchMapper;
    @Autowired
    private FinanceBatchInvoiceMapper financeBatchInvoiceMapper;
    @Autowired
    private FinanceInvoiceApMapper financeInvoiceApMapper;
    @Autowired
    private FinancingRateMapper financingRateMapper;
    @Autowired
    private SupplierCompanyCodeMapper supplierCompanyCodeMapper;
    @Autowired
    private BankMapper bankMapper;
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private EntityCompanyCodeMapper entityCompanyCodeMapper;
    @Autowired
    private EntityBankSettingMapper entityBankSettingMapper;
    @Autowired
    private SupplierBankSettingMapper supplierBankSettingMapper;
    @Autowired
    private SupplierUniqueMapper supplierUniqueMapper;
    @Autowired
    private InvoiceSubmitRecordMapper invoiceSubmitRecordMapper;
    @Autowired
    private SysUserFinancingMapper sysUserFinancingMapper;
    @Autowired
    private RemoteUserService remoteUserService;
    @Autowired
    private RemoteSupplierDataService remoteSupplierDataService;
    @Autowired
    private RemoteUserAnchorService remoteUserAnchorService;
    @Autowired
    private FilterContext filterContext;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private SendMailUtil mailUtil;

    private OtmpException throwEx(Long errCode) {
        return new OtmpException(DefaultErrorMessage.getErrorMessage(errCode), errCode.intValue());
    }

    private LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public List<MaturityDateAmountVo> maturityDateAmount(String bankId) {
        log.info("maturityDateAmount,bankId:{}", bankId);
        ResultDTO<List<MaturityDateAmountResult>> remoteResult = remoteSupplierDataService.maturityDateAmount(bankId);
        log.info("maturityDateAmount,remoteResult:{}", JsonUtil.toJSON(remoteResult));
        if (remoteResult == null) {
            log.error("maturityDateAmount call remote error,remoteResult is null");
            throw throwEx(DefaultErrorMessage.SERVER_ERROR);
        }
        if (RemoteSupplierDataService.CODE_OK_NUM != remoteResult.getCode()) {
            log.error("maturityDateAmount call remote failed,msg:{}", remoteResult.getMsg());
            throw throwEx(DefaultErrorMessage.SERVER_ERROR);
        }
        List<MaturityDateAmountResult> data = remoteResult.getData();
        if (CollectionUtils.isEmpty(data)) {
            log.info("maturityDateAmount,data is empty,bankId:{}", bankId);
            return new ArrayList<>();
        }
        List<MaturityDateAmountVo> result = new ArrayList<>(data.size());
        for (MaturityDateAmountResult record : data) {
            MaturityDateAmountVo vo = new MaturityDateAmountVo();
            vo.setMaturityDate(record.getMaturityDate());
            vo.setSupplierCode(record.getSupplierCode());
            vo.setStatus(record.getStatus());
            vo.setAmount(record.getAmount());
            vo.setSupplierCode(record.getSupplierCode());
            result.add(vo);
        }
        log.info("maturityDateAmount,result:{}", JsonUtil.toJSON(result));
        return result;
    }

    @Override
    public Long confirmTimeExpiry(long currentUserId) {
        String supplierId = getSupplierIdByUserId(currentUserId);
        String markTimeKey = RedisKeyUtil.getMarkTimeKey(supplierId, currentUserId);
        log.info("confirmTimeExpiry,markTimeKey:{}", markTimeKey);
        RBucket<String> bucket = redissonClient.getBucket(markTimeKey);
        long expireTime = bucket.remainTimeToLive();
        return expireTime < 0 ? 0 : (expireTime / 1000);
    }

    @Override
    public Boolean setConfirmTime(long currentUserId) {
        SysUser sysUser = getSysUserByUserId(currentUserId);
        String markTimeKey = RedisKeyUtil.getMarkTimeKey(sysUser.getSupplierId(), currentUserId);
        log.info("setConfirmTime,markTimeKey:{}", markTimeKey);
        RBucket<String> bucket = redissonClient.getBucket(markTimeKey);
        if (bucket.get() != null) {
            log.info("setConfirmTime,bucket not null,return");
            return false;
        }
        bucket.set(sysUser.getLoginName(), TIME_EXPIRY, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public Boolean clearConfirmTime(long currentUserId) {
        SysUser sysUser = getSysUserByUserId(currentUserId);
        String redisKey = RedisKeyUtil.getMarkTimeKey(sysUser.getSupplierId(), currentUserId);
        log.info("clearConfirmTime,markTimeKey:{}", redisKey);
        RBucket<String> bucket = redissonClient.getBucket(redisKey);
        if (bucket != null) {
            return bucket.delete();
        }
        return true;
    }

    /**
     * 排序依据：bank.rank（高在前） -> margin（低在前） -> 可用额度（多在前）
     */
    @Override
    public List<BankVo> rankList(Long userId) {
        log.info("rankList,userId:{}", userId);
        Long supplierId = getSupplierId(userId);

        // 按rank asc,margin desc排序
        List<BankWithMarginDo> rankList = bankMapper.rankList(supplierId);
        if (CollectionUtils.isEmpty(rankList)) {
            log.info("rankList, rankList is empty,supplierId:{}", supplierId);
            return new ArrayList<>();
        }
        Set<Long> entityIds = rankList.parallelStream().map(BankWithMarginDo::getEntityId).collect(Collectors.toSet());
        Set<String> bankIds = rankList.parallelStream().map(BankDo::getId).collect(Collectors.toSet());
        log.info("rankList,bankIds:{}", bankIds);
        List<BankOutstandingDo> debitOutstanding = invoiceSubmitRecordMapper.selectBankOutstanding(InvoiceConstants.INVOICE_TYPE_DEBIT_STR, bankIds, entityIds);
        List<BankOutstandingDo> creditOutstanding = invoiceSubmitRecordMapper.selectBankOutstanding(InvoiceConstants.INVOICE_TYPE_CREDIT_STR, bankIds, entityIds);
        Map<String, BigDecimal> debitOutstandingMap = new HashMap<>();
        Map<String, BigDecimal> creditOutstandingMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(debitOutstanding)) {
            debitOutstandingMap = debitOutstanding.stream().collect(Collectors.toMap(t -> (t.getBankId() + StringConstants.UNDER_LINE + t.getEntityId()), BankOutstandingDo::getOutstanding));
        }
        if (CollectionUtils.isNotEmpty(creditOutstanding)) {
            creditOutstandingMap = creditOutstanding.stream().collect(Collectors.toMap(t -> (t.getBankId() + StringConstants.UNDER_LINE + t.getEntityId()), BankOutstandingDo::getOutstanding));
        }
        log.info("rankList,debitOutstandingMap:{},creditOutstandingMap:{}", JsonUtil.toJSON(debitOutstandingMap), JsonUtil.toJSON(creditOutstandingMap));
        Date now = DateUtils.getNowDate();
        List<BankVo> tempList = new ArrayList<>(rankList.size());
        for (BankWithMarginDo bankWithMarginDo : rankList) {
            BigDecimal debitAmount = debitOutstandingMap.remove(bankWithMarginDo.getId() + StringConstants.UNDER_LINE + bankWithMarginDo.getEntityId());
            debitAmount = debitAmount == null ? new BigDecimal("0") : debitAmount;
            BigDecimal creditAmount = creditOutstandingMap.remove(bankWithMarginDo.getId() + StringConstants.UNDER_LINE + bankWithMarginDo.getEntityId());
            creditAmount = creditAmount == null ? new BigDecimal("0") : creditAmount;
            BankVo bankVo = new BankVo(bankWithMarginDo);
            BigDecimal amount = new BigDecimal(bankWithMarginDo.getBankLimit() == null ? "0" : String.valueOf(bankWithMarginDo.getBankLimit()));
            amount = amount.subtract(debitAmount).add(creditAmount);
            if (bankWithMarginDo.getAdhocExpiryDate() != null && bankWithMarginDo.getAdhocExpiryDate().after(now)) {
                BigDecimal adhocLimit = bankWithMarginDo.getAdhocLimit();
                adhocLimit = adhocLimit == null ? new BigDecimal("0") : adhocLimit;
                amount = amount.add(adhocLimit);
            }
            bankVo.setAmount(amount.doubleValue());
            tempList.add(bankVo);
        }
        Map<String, List<BankVo>> collect = tempList.stream().collect(Collectors.groupingBy(BankVo::getId));
        Set<BankVo> bankVoSet = new HashSet<>();
        for (Map.Entry<String, List<BankVo>> entry : collect.entrySet()) {
            List<BankVo> value = entry.getValue();
            BankVo newVo = new BankVo();
            BigDecimal bankAmount = new BigDecimal("0");
            if (CollectionUtils.isNotEmpty(value)) {
                BankVo firstVo = value.get(0);
                newVo.setRank(firstVo.getRank());
                newVo.setMargin(firstVo.getMargin());
                newVo.setId(firstVo.getId());
                newVo.setBankName(firstVo.getBankName());
                newVo.setIconPath(firstVo.getIconPath());
                for (BankVo vo : value) {
                    bankAmount = bankAmount.add(new BigDecimal(vo.getAmount() == null ? "0" : String.valueOf(vo.getAmount())));
                }
            }
            newVo.setAmount(bankAmount.doubleValue());
            bankVoSet.add(newVo);
        }
        List<BankVo> resultList = new ArrayList<>(bankVoSet);

        resultList.sort((o1, o2) -> {
            if (o1 == null || o2 == null) {
                return 0;
            }
            // rank不同,比较rank
            if (o1.getRank() != null
                    && o2.getRank() != null
                    && !o1.getRank().equals(o2.getRank())) {
                return o2.getRank().compareTo(o1.getRank());
            }
            // rank相同,比较margin
            if (o1.getMargin() != null
                    && o2.getMargin() != null
                    && !o1.getMargin().equals(o2.getMargin())) {
                return o2.getMargin().compareTo(o1.getMargin());
            }
            // rank,margin都相同,比较amount
            if (o1.getAmount() == null || o2.getAmount() == null) {
                return 0;
            }
            return o2.getAmount().compareTo(o1.getAmount());
        });

        log.info("rankList,result:{}", JsonUtil.toJSON(resultList));
        return resultList;
    }

    /**
     * TODO 删除弃用代码
     */
    /*@Override
    public PageInfo<InvoiceVo> availableInvoicesFree(AvailableFreeListParam param) {
        log.info("availableInvoicesFree,param:{}", JsonUtil.toJSON(param));
        Long supplierId = getSupplierId(param.getUserId());
//        Long supplierId = 1717459086844592128L;
        List<String> companyCodes = supplierBankSettingMapper.selectBankCompanyCodes(supplierId, param.getBankId());
        List<SupplierUniqueIdDo> supplierUniqueIdDos = selectUniqueIdBySupplierId(supplierId);
        log.info("availableInvoicesFree,companyCodes:{},supplierUniqueIdDos：{}", JsonUtil.toJSON(companyCodes), JsonUtil.toJSON(supplierUniqueIdDos));
        PageInfo<InvoiceVo> result = new PageInfo<>();
        int pageNum = (param.getPageNum() == null || param.getPageNum() <= 0) ? Constants.DEFAULT_PAGE_NUM : param.getPageNum();
        int pageSize = (param.getPageSize() == null || param.getPageSize() <= 0) ? Constants.DEFAULT_PAGE_SIZE : param.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<FinanceInvoiceApDo> invoiceAps = financeInvoiceApMapper.availableInvoiceFreeList(InvoiceConstants.convertType(param.getType()), param.getIssueStartDate(), param.getIssueEndDate()
                , param.getMaturityStartDate(), param.getMaturityEndDate(), companyCodes, supplierUniqueIdDos);
        PageInfo<FinanceInvoiceApDo> pageInfo = new PageInfo<>(invoiceAps);
        result.setTotal(pageInfo.getTotal());
        if (CollectionUtils.isEmpty(invoiceAps)) {
            log.info("availableInvoicesFree,invoiceAps is empty");
            result.setList(new ArrayList<>());
            return result;
        }
        List<InvoiceVo> invoices = new ArrayList<>(invoiceAps.size());
        for (FinanceInvoiceApDo invoice : invoiceAps) {
            InvoiceVo vo = new InvoiceVo();
            vo.setId(String.valueOf(invoice.getId()));
            vo.setAmount(invoice.getInvoiceAmount());
            vo.setCompanyCode(invoice.getCompanyCode());
            vo.setCurrency(invoice.getInvoiceCurrency());
            vo.setInvoiceNumber(invoice.getInvoiceReference());
            vo.setIssueDate(invoice.getInvoiceIssueDate());
            vo.setDueDate(invoice.getInvoiceDueDate());
//            vo.setEntityName(invoice.getEntityName());
            vo.setCurrency(invoice.getInvoiceCurrency());
            if (invoice.getMaturityDate() != null) {
                vo.setMaturityDate(DateUtils.parseDateToStr(DateUtils.DATE_PATTERN, localDateTimeToDate(invoice.getMaturityDate())));
            }
            invoices.add(vo);
        }
        result.setList(invoices);
        return result;
    }*/

    @Override
    public FinanceInvoiceListVo availableInvoicesFreeWithRelation(AvailableFreeListParam param) {
        log.info("availableInvoicesFreeWithRelation,param:{}", JsonUtil.toJSON(param));
        // 1,清空缓存数据
        Long supplierId = getSupplierId(param.getUserId());

        log.info("availableInvoicesFreeWithRelation,supplierId:{}", supplierId);
        // TODO 删除多余代码
//        String dataCheckedKey = RedisKeyUtil.dataCheckedKey(supplierId);
//        String dataCacheKey = RedisKeyUtil.dataCacheKey(supplierId);
//        log.info("availableInvoicesFreeWithRelation,clear cache,dataCacheKey:{}", dataCacheKey);
//        RList<InvoiceVo> checkedList = redissonClient.getList(dataCheckedKey);
//        RList<FinanceInvoiceApDo> cacheList = redissonClient.getList(dataCacheKey);
//        if (checkedList != null) {
//            checkedList.clear();
//        }
//        if (cacheList != null) {
//            cacheList.clear();
//        }
        // 2,查询数据
        List<SupplierBankSettingDo> supplierBankSettingDos = supplierBankSettingMapper.selectBySupplierBankEntityIds(supplierId, param.getBankId(), null);
        if (CollectionUtils.isEmpty(supplierBankSettingDos)) {
            log.info("availableInvoicesFreeWithRelation,supplierBankSettingDos is empty,supplierId:{}", supplierId);
            throw throwEx(DefaultErrorMessage.SUPPLIER_BANK_SETTING_IS_NULL);
        }
        Map<String, SupplierBankSettingDo> settingMap = supplierBankSettingDos.stream()
                .collect(Collectors.toMap(t -> bankSettingKey(t.getEntityId(), t.getBankId()), t -> t, (k1, k2) -> k2));
        log.info("availableInvoicesFreeWithRelation,settingMap:{}", JsonUtil.toJSON(settingMap));
        List<SupplierUniqueIdDo> supplierUniqueIdDos = selectUniqueIdBySupplierId(supplierId);
        log.info("availableInvoicesFreeWithRelation,supplierUniqueIdDos:{}", JsonUtil.toJSON(supplierUniqueIdDos));
        if (CollectionUtils.isEmpty(supplierUniqueIdDos)) {
            log.info("availableInvoicesFreeWithRelation,supplierUniqueIdDos is empty,supplierId:{}", supplierId);
            throw throwEx(DefaultErrorMessage.ERP_IDS_IS_NULL);
        }
        // 这里会通过bankId筛选出companyCode
        List<String> companyCodes = supplierBankSettingMapper.selectBankCompanyCodes(supplierId, param.getBankId());
        EntityCodeIdDto entityCodeIdDto = entityCodeIdMap(companyCodes);
        if (entityCodeIdDto == null) {
            log.error("availableInvoicesFreeWithRelation,entityCodeIdDto is null, companyCodes:{}", companyCodes);
            throw throwEx(DefaultErrorMessage.SUPPLIER_BANK_SETTING_IS_NULL);
        }
        Map<String, String> codeEntityMap = entityCodeIdDto.getCodeNameMap();
        Map<String, Long> codeIdMap = entityCodeIdDto.getCodeIdMap();
        // 查询credit
        List<FinanceInvoiceApDo> credits = financeInvoiceApMapper.availableCreditFinance(companyCodes, supplierUniqueIdDos);
        // 保存credit和companyCode的关系
        Map<String, List<FinanceInvoiceApDo>> invoiceRelationMap = new HashMap<>();
        List<InvoiceVo> creditVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(credits)) {
            invoiceRelationMap = credits.stream().collect(Collectors.groupingBy(BaseInvoiceInfo::getCompanyCode));
            for (FinanceInvoiceApDo credit : credits) {
                String entityName = codeEntityMap.get(credit.getCompanyCode());
                creditVos.add(convertToInvoiceVo(entityName, credit));
            }
        }
        FinanceInvoiceListVo result = new FinanceInvoiceListVo();
        result.setCredits(creditVos);
        result.setCreditTotal((long) creditVos.size());

        // issueDate条件需要把两个参数(issueStartDate,issueEndDate)中的"-"去掉
        String issueStartDate = StringUtils.isNotEmpty(param.getIssueStartDate()) ? param.getIssueStartDate().replace("-", "") : null;
        String issueEndDate = StringUtils.isNotEmpty(param.getIssueEndDate()) ? param.getIssueEndDate().replace("-", "") : null;
        int pageNum = (param.getPageNum() == null || param.getPageNum() <= 0) ? Constants.DEFAULT_PAGE_NUM : param.getPageNum();
        int pageSize = (param.getPageSize() == null || param.getPageSize() <= 0) ? Constants.DEFAULT_PAGE_SIZE : param.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        // 查询debit
        List<FinanceInvoiceApDo> invoiceApDoList = financeInvoiceApMapper.availableInvoiceFreeList(InvoiceConstants.INVOICE_TYPE_DEBIT_STR
                , issueStartDate, issueEndDate, param.getMaturityStartDate(), param.getMaturityEndDate(), companyCodes, supplierUniqueIdDos);
        if (CollectionUtils.isEmpty(invoiceApDoList)) {
            // debit为空直接返回
            result.setDebits(new ArrayList<>());
            result.setRelations(new HashMap<>());
            result.setDebitTotal(0L);
            return result;
        }
        Date now = DateUtils.getNowDate();
        // debit列表不为空则转换为InvoiceVo列表同时处理和credit的关联关系
        List<InvoiceVo> invoiceVos = new ArrayList<>(invoiceApDoList.size());
        PageInfo<FinanceInvoiceApDo> pageInfo = new PageInfo<>(invoiceApDoList);
        // 用于保存debit和credit关联关系的map,key为debit的id,value为关系的credit的id列表
        Map<String, Collection<String>> relation = new HashMap<>();
        for (FinanceInvoiceApDo invoice : invoiceApDoList) {
            String entityName = codeEntityMap.get(invoice.getCompanyCode());
            InvoiceVo invoiceVo = convertToInvoiceVo(entityName, invoice);
            // 获取相同companyCode下的所有credit
            List<FinanceInvoiceApDo> invoiceRelation = invoiceRelationMap.get(invoice.getCompanyCode());
            Long entityId = codeIdMap.get(invoice.getCompanyCode());
            SupplierBankSettingDo setting = settingMap.get(bankSettingKey(entityId, param.getBankId()));
//            invoiceVo.setSelectable(checkMaxFinancingTenor(setting, invoice, now));
            checkSelectable(invoiceVo, setting, invoice, now);
            invoiceVos.add(invoiceVo);
            if (CollectionUtils.isNotEmpty(invoiceRelation)) {
                relation.put(String.valueOf(invoice.getId()), invoiceRelation.parallelStream().map(t -> String.valueOf(t.getId())).collect(Collectors.toSet()));
            }
        }
        result.setRelations(relation);
        result.setDebits(invoiceVos);
        result.setDebitTotal(pageInfo.getTotal());
        log.info("availableInvoicesFreeWithRelation,result:{}", JsonUtil.toJSON(result));
        return result;
    }

    /**
     * 发票选择（自动）
     * 与前端约定：通过参数first区分是否首次查询，首次查询需要返回所有的credit发票列表和被选中的发票的id，之后的查询不再返回这个两个字段值
     */
    @Override
    public FinanceInvoiceListVo availableInvoicesSelect(AvailableSelectListParam param) {
        log.info("availableInvoicesSelectSimple,param:{}", JsonUtil.toJSON(param));
        StopWatch watch = new StopWatch();
        watch.start("getSysUser");
        SysUser sysUser = getSysUserByUserId(param.getUserId());
//        SysUser sysUser = new SysUser();
//        sysUser.setSupplierId("1743109769984897024");
        watch.stop();
        watch.start("getPageLockKey");
        Long supplierId = getSupplierId(sysUser);
        String pageLockKey = RedisKeyUtil.pageLockKey(supplierId);
        log.info("availableInvoicesSelect,pageLockKey:{}", pageLockKey);
        // 检查并设置操作用户
        setOperator(pageLockKey, sysUser);
        Example condition = new Example(SupplierDo.class);
        condition.createCriteria().andEqualTo("id", supplierId)
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        SupplierDo supplier = supplierMapper.selectOneByExample(condition);
        if (supplier == null) {
            log.info("availableInvoicesSelect,supplier not found,supplierId:{}", supplierId);
            throw throwEx(DefaultErrorMessage.SUPPLIER_NOT_FOUND);
        }
        watch.stop();
        watch.start("loadData");
        // ===========================================================================
        FinanceInvoiceListVo result = new FinanceInvoiceListVo();
        // 这里会通过bankId筛选出companyCode
        List<String> companyCodes = supplierBankSettingMapper.selectBankCompanyCodes(supplierId, param.getBankId());
        log.info("availableInvoicesSelect,companyCodes:{}", JsonUtil.toJSON(companyCodes));
        EntityCodeIdDto entityCodeIdDto = entityCodeIdMap(companyCodes);
        if (entityCodeIdDto == null) {
            log.error("availableInvoicesSelect,entityCodeIdDto is null, companyCodes:{}", companyCodes);
            throw throwEx(DefaultErrorMessage.SUPPLIER_BANK_SETTING_IS_NULL);
        }
        log.info("availableInvoicesSelect,entityCodeIdDto:{}", JsonUtil.toJSON(entityCodeIdDto));
        Map<String, String> codeEntityMap = entityCodeIdDto.getCodeNameMap();
//        Map<String, Long> codeIdMap = entityCodeIdDto.getCodeIdMap();
        List<SupplierUniqueIdDo> supplierUniqueIdDos = selectUniqueIdBySupplierId(supplierId);
        log.info("availableInvoicesSelect,supplierUniqueIdDos:{}", JsonUtil.toJSON(supplierUniqueIdDos));
        if (CollectionUtils.isEmpty(supplierUniqueIdDos)) {
            log.error("availableInvoicesSelect,supplierUniqueIdDos is null, supplierId:{}", supplierId);
            throw throwEx(DefaultErrorMessage.ERP_IDS_IS_NULL);
        }
        // credit列表,全量查询
        List<FinanceInvoiceApDo> credits = financeInvoiceApMapper.availableCreditFinance(companyCodes, supplierUniqueIdDos);
        // credit类型发票列表
        List<InvoiceVo> creditResult = new ArrayList<>();
        BigDecimal creditAmount = new BigDecimal("0");
        if (CollectionUtils.isNotEmpty(credits)) {
            for (FinanceInvoiceApDo credit : credits) {
                creditAmount = creditAmount.add(new BigDecimal(credit.getInvoiceAmount() == null ? "0" : String.valueOf(credit.getInvoiceAmount())));
                String entityName = codeEntityMap.get(credit.getCompanyCode());
                InvoiceVo invoiceVo = convertToInvoiceVo(entityName, credit);
                creditResult.add(invoiceVo);
            }
        }
        // 首次查询返回credit发票列表和数量
        if (param.isFirst()) {
            // credit列表
            result.setCredits(creditResult);
            // credit总数量
            result.setCreditTotal((long) creditResult.size());
        }
        // 用于计算debit和credit关联关系的map
        Map<String, List<FinanceInvoiceApDo>> creditCompanyCodeMapForRel = new HashMap<>();
        // 用于计算选中的发票额度的map，key为companyCode，value为该companyCode下的credit列表，
        // 计算时debit通过companyCode从该map中获取(remove)相关的credit
        Map<String, List<FinanceInvoiceApDo>> creditCompanyCodeMapForCal = new HashMap<>();
        if (CollectionUtils.isNotEmpty(credits)) {
            creditCompanyCodeMapForRel = credits.parallelStream().collect(Collectors.groupingBy(BaseInvoiceInfo::getCompanyCode));
            creditCompanyCodeMapForCal = new HashMap<>(creditCompanyCodeMapForRel);
        }
        log.info("availableInvoicesSelect,creditCompanyCodeMapForRel:{},creditCompanyCodeMapForCal:{}", JsonUtil.toJSON(creditCompanyCodeMapForRel), JsonUtil.toJSON(creditCompanyCodeMapForCal));
        List<SupplierBankSettingDo> supplierBankSettingDos = supplierBankSettingMapper.selectBySupplierBankEntityIds(supplierId, param.getBankId(), null);
        if (CollectionUtils.isEmpty(supplierBankSettingDos)) {
            log.info("availableInvoicesSelect,supplierBankSettingDos is empty,supplierId:{}", supplierId);
            throw throwEx(DefaultErrorMessage.SUPPLIER_BANK_SETTING_IS_NULL);
        }
        Map<String, SupplierBankSettingDo> settingMap = supplierBankSettingDos.stream()
                .collect(Collectors.toMap(t -> bankSettingKey(t.getEntityId(), t.getBankId()), t -> t, (k1, k2) -> k2));
        log.info("availableInvoicesSelect,settingMap:{}", JsonUtil.toJSON(settingMap));
        if (param.isFirst()) {
            // 如果是首次查询需要先将数据从DB读取到缓存，同时获取amount,checkIds,debitTotal,relations
            firstSelect(param, supplierId, result, entityCodeIdDto, companyCodes, supplierUniqueIdDos, settingMap, creditCompanyCodeMapForCal, creditCompanyCodeMapForRel);
        }
        // 查询当前页内容，获取debits
        getPageContent(param, supplierId, result, entityCodeIdDto, settingMap);
        // ===========================================================================
        // supplier是否可以修改confirmMaturityDate
        result.setMaturityDateChangeable(SupplierConstants.convertMdChangeableBool(supplier.getMaturityDateChangeable()));
        watch.stop();
        log.info("availableInvoicesSelect,cost time:\n{}", watch.prettyPrint());
        return result;
    }

    /**
     * 从缓存获取指定页的内容
     * 需要根据页码和每页数量计算出起止下标，再从在firstSelect方法中
     * 生成的debitTopCacheList和debitTailCacheList中获取数据
     */
    private void getPageContent(AvailableSelectListParam param
            , Long supplierId
            , FinanceInvoiceListVo result
            , EntityCodeIdDto entityCodeIdDto
            , Map<String, SupplierBankSettingDo> settingMap) {
        int pageNum = (param.getPageNum() == null || param.getPageNum() < 0) ? Constants.DEFAULT_PAGE_NUM : param.getPageNum();
        int pageSize = (param.getPageSize() == null || param.getPageSize() < 0) ? Constants.DEFAULT_PAGE_SIZE : param.getPageSize();
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = pageNum * pageSize;
        log.info("getPageContent,pageNum:{},pageSize:{},startIndex:{},endIndex:{}", pageNum, pageSize, startIndex, endIndex);
        String debitTopCacheKey = RedisKeyUtil.dataCacheKey(supplierId);
        RList<FinanceInvoiceApDo> debitTopCacheList = redissonClient.getList(debitTopCacheKey);
        int topSize = debitTopCacheList.size();
        boolean loadTail = endIndex > topSize;
        log.info("getPageContent,topSize:{},loadTail:{}", topSize, loadTail);
        long loadStart = System.currentTimeMillis();
        List<FinanceInvoiceApDo> debits = new ArrayList<>();
        if (loadTail) {
            int remainder = topSize % pageSize;
            log.info("getPageContent,remainder:{}", remainder);
            String debitTailKey = RedisKeyUtil.dataTailCacheKey(supplierId);
            RList<FinanceInvoiceApDo> debitTailCacheList = redissonClient.getList(debitTailKey);
            int debitTailCacheSize = debitTailCacheList.size();
            if (startIndex < topSize) {
                log.info("getPageContent,load 1,startIndex:{},topSize:{}", startIndex, topSize);
                long loadOneStart = System.currentTimeMillis();
                debits.addAll(debitTopCacheList.subList(startIndex, topSize));
                debits.addAll(debitTailCacheList.subList(0, Math.min(pageSize - remainder, debitTailCacheSize)));
                long loadOneEnd = System.currentTimeMillis();
                log.info("getPageContent,load 1,cost time:{}", (loadOneEnd - loadOneStart));
            } else {
                long loadTwoStart = System.currentTimeMillis();
                int basePage = topSize / pageSize + 1;
                int offset = pageSize - remainder;
                int realPage = pageNum - basePage;
                int start = (realPage - 1) * pageSize + offset;
                int end = start + pageSize;
                start = Math.min(start, debitTailCacheSize);
                end = Math.min(end, debitTailCacheSize);
                log.info("getPageContent,load 2,start:{},end:{},debitTailCacheSize:{}", start, end, debitTailCacheSize);
                debits.addAll(debitTailCacheList.subList(start, end));
                long loadTwoEnd = System.currentTimeMillis();
                log.info("getPageContent,load 2,cost time:{}", (loadTwoEnd - loadTwoStart));
            }
        } else {
            log.info("getPageContent,load 3,startIndex:{},endIndex:{}", startIndex, endIndex);
            long loadThreeStart = System.currentTimeMillis();
            debits.addAll(debitTopCacheList.subList(startIndex, endIndex));
            long loadThreeEnd = System.currentTimeMillis();
            log.info("getPageContent,load 3,cost time:{}", (loadThreeEnd - loadThreeStart));
        }
        long loadEnd = System.currentTimeMillis();
        log.info("getPageContent,debits.size:{},load cost time:{}", debits.size(), (loadEnd - loadStart));
        Date queryTime = DateUtils.getNowDate();
        List<InvoiceVo> debitVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(debits)) {
            Map<String, String> codeEntityMap = entityCodeIdDto.getCodeNameMap();
            Map<String, Long> codeIdMap = entityCodeIdDto.getCodeIdMap();
            for (FinanceInvoiceApDo debit : debits) {
                InvoiceVo invoiceVo = convertToInvoiceVo(codeEntityMap.get(debit.getCompanyCode()), debit);
                String settingKey = bankSettingKey(codeIdMap.get(debit.getCompanyCode()), param.getBankId());
                invoiceVo.setSelectable(checkSelectable(debit, queryTime, settingMap.get(settingKey)));
                debitVos.add(invoiceVo);
            }
        }
        result.setDebits(debitVos);
        log.info("getPageContent,result:{}", JsonUtil.toJSON(result));
    }

    /**
     * 首次查询时调用，将数据从DB读取到缓存，同时获取达到融资额度所选中的发票/发票额度/debit发票总数
     */
    private void firstSelect(AvailableSelectListParam param
            , Long supplierId
            , FinanceInvoiceListVo result
            , EntityCodeIdDto entityCodeIdDto
            , List<String> companyCodes
            , List<SupplierUniqueIdDo> supplierUniqueIdDos
            , Map<String, SupplierBankSettingDo> settingMap
            , Map<String, List<FinanceInvoiceApDo>> creditCompanyCodeMapForCal
            , Map<String, List<FinanceInvoiceApDo>> creditCompanyCodeMapForRel) {
        log.info("firstSelect,supplierId:{},param:{}", supplierId, JsonUtil.toJSON(param));
        // 达到融资金额所需的发票对象，即默认选中的发票（兼容发票提交方法中使用）
        String dataCheckedKey = RedisKeyUtil.dataCheckedKey(supplierId);
        String idCheckedKey = RedisKeyUtil.idCheckedKey(supplierId);
        RList<InvoiceVo> dataCheckedCacheList = redissonClient.getList(dataCheckedKey);
        RList<String> idCheckedCacheList = redissonClient.getList(idCheckedKey);
        dataCheckedCacheList.clear();
        idCheckedCacheList.clear();
        // 达到融资金额所需的发票对象，即默认选中的发票（供前端查询时使用）
        String debitTopCacheKey = RedisKeyUtil.dataCacheKey(supplierId);
        RList<FinanceInvoiceApDo> debitTopCacheList = redissonClient.getList(debitTopCacheKey);
        debitTopCacheList.clear();
        // 所有发票中，非选中的发票
        String debitTailDataCacheKey = RedisKeyUtil.dataTailCacheKey(supplierId);
        RList<FinanceInvoiceApDo> debitTailCacheList = redissonClient.getList(debitTailDataCacheKey);
        debitTailCacheList.clear();
        // 查询debit数量
        Long debitTotal = financeInvoiceApMapper.availableInvoiceFinanceListCount(param.getMaturityDate(), companyCodes, supplierUniqueIdDos);
        if (debitTotal == null || debitTotal == 0L) {
            // debit数量为0,直接返回
            log.info("firstSelect,debitTotal is 0,return. supplierId:{},time:{}", supplierId, param.getMaturityDate());
            fillNullResult(result);
            return;
        }

        Map<String, Long> codeIdMap = entityCodeIdDto.getCodeIdMap();
        Map<String, String> codeEntityMap = entityCodeIdDto.getCodeNameMap();
        int tempQuerySize = QUERY_SIZE >> 1;
        long countLimit = debitTotal % tempQuerySize == 0 ? (debitTotal / tempQuerySize) : (debitTotal / tempQuerySize) + 1;
        log.info("firstSelect,countLimit:{}", countLimit);
        Date queryTime = DateUtils.getNowDate();
        long start = System.currentTimeMillis();
        // 目标额度
        BigDecimal target = new BigDecimal(String.valueOf(param.getFinancingAmount() == null ? 0 : param.getFinancingAmount()));
        // 默认选择的发票的总额度（不计算抵消的credit）
        BigDecimal selectAmount = new BigDecimal("0");
        // 默认选择的发票的总额度（需要减掉相关的credit的额度）
        BigDecimal checkedAmount = new BigDecimal("0");
        BigDecimal lastAmount = new BigDecimal("0");
        Map<String, Collection<String>> relations = new HashMap<>();
        List<String> checkedIds = new ArrayList<>();
        boolean reach = false;
        for (int queryCount = 1; queryCount <= countLimit; queryCount++) {
            List<InvoiceVo> dataChecked = new ArrayList<>();
            List<FinanceInvoiceApDo> top = new ArrayList<>();
            int queryOffsetMin = (queryCount - 1) * tempQuerySize;
            List<FinanceInvoiceApDo> debitsInDb = financeInvoiceApMapper.availableInvoiceFinanceList(param.getMaturityDate(), companyCodes, supplierUniqueIdDos, queryOffsetMin, tempQuerySize);
            if (CollectionUtils.isNotEmpty(debitsInDb)) {
                // amount,checkIds,debitTotal
                Iterator<FinanceInvoiceApDo> iterator = debitsInDb.iterator();
                while (iterator.hasNext()) {
                    FinanceInvoiceApDo debit = iterator.next();
                    String settingKey = bankSettingKey(codeIdMap.get(debit.getCompanyCode()), param.getBankId());
                    // 判断发票是否可被选择(invoiceTenor、maxTenor)
                    if (checkSelectable(debit, queryTime, settingMap.get(settingKey))) {
                        // 没有达到融资额度且融资额度不为0时需要进行发票选择
                        if (!reach && target.compareTo(new BigDecimal("0")) > 0) {
                            BigDecimal invoiceAmount = new BigDecimal(String.valueOf(Math.abs(debit.getInvoiceAmount())));
                            List<FinanceInvoiceApDo> creditInvoiceAps = creditCompanyCodeMapForCal.remove(debit.getCompanyCode());
                            selectAmount = selectAmount.add(invoiceAmount);
                            checkedAmount = checkedAmount.add(invoiceAmount);
                            // selectAmount 表示选中的debit的额度,因此不用减掉相关的credit的额度
                            if (CollectionUtils.isNotEmpty(creditInvoiceAps)) {
                                BigDecimal creditAmount = new BigDecimal("0");
                                for (FinanceInvoiceApDo creditInvoiceAp : creditInvoiceAps) {
                                    creditAmount = creditAmount.add(new BigDecimal(creditInvoiceAp.getInvoiceAmount() == null ? "0" : String.valueOf(creditInvoiceAp.getInvoiceAmount())));
                                }
                                checkedAmount = checkedAmount.subtract(creditAmount);
//                                log.info("selectAmount:{}", checkedAmount.toPlainString());
                            }

                            if (lastAmount.compareTo(target) < 0) {
                                // 如果总额度小于目标融资额度,勾选当前debit
                                checkedIds.add(String.valueOf(debit.getId()));
                                String entityName = codeEntityMap.get(debit.getCompanyCode());
                                dataChecked.add(convertToInvoiceVo(entityName, debit));
                            }
                            if (checkedAmount.compareTo(target) >= 0) {
                                // 如果达到目标额度,标识设置为true
                                reach = true;
                            }
                            lastAmount = checkedAmount;
                            top.add(debit);
                            iterator.remove();
                        }
                        List<FinanceInvoiceApDo> financeInvoiceApDosForRel = creditCompanyCodeMapForRel.get(debit.getCompanyCode());
                        if (!CollectionUtils.isEmpty(financeInvoiceApDosForRel)) {
                            relations.put(String.valueOf(debit.getId()), financeInvoiceApDosForRel.parallelStream().map(t -> String.valueOf(t.getId())).collect(Collectors.toSet()));
                        }
                    }
                }
            }
            dataCheckedCacheList.addAll(dataChecked);
            debitTopCacheList.addAll(top);
            debitTailCacheList.addAll(debitsInDb);
            /*if(queryCount == 1){
                RFuture<Boolean> booleanRFuture = debitTailCacheList.addAllAsync(debitsInDb);
                try {
                    // 阻塞执行，以等待addAll完成
                    booleanRFuture.get();
                } catch (InterruptedException | ExecutionException e) {
                    log.error("first select add debitsInDb to cache error",e);
                    throw throwEx(DefaultErrorMessage.SERVER_ERROR);
                }
            }else{
                debitTailCacheList.addAll(debitsInDb);
            }*/
        }
        idCheckedCacheList.addAll(checkedIds);
        try {
            // 让redisson的异步addAll操作完成
            Thread.sleep(600);
        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
            log.error("firstSelect, sleep error");
        }
        long end = System.currentTimeMillis();
        log.info("firstSelect,load data cost time:{}", (end - start));
        result.setDebitTotal(debitTotal);
        result.setAmount(selectAmount.toPlainString());
        result.setCheckedIds(checkedIds);
        result.setRelations(relations);
        log.info("firstSelect,result:{}", JsonUtil.toJSON(result));
    }

    private void fillNullResult(FinanceInvoiceListVo result) {
        result.setDebits(new ArrayList<>());
        result.setAmount("0");
        result.setDebitTotal(0L);
        result.setCheckedIds(new ArrayList<>());
        result.setRelations(new HashMap<>());
    }

    private boolean checkSelectable(BaseInvoiceInfo invoice, Date submitTime, SupplierBankSettingDo setting) {
        return checkMaxInvoiceTenor(setting, invoice)
                && checkMaxFinancingTenor(setting, invoice, submitTime);
    }

    private void checkSelectable(InvoiceVo invoiceVo, SupplierBankSettingDo setting, BaseInvoiceInfo invoice, Date submitTime) {
        boolean checkResult = checkMaxInvoiceTenor(setting, invoice);
        if (!checkResult) {
//            log.info("checkSelectable,checkMaxInvoiceTenor failed,invoiceReference:{}", invoiceVo.getInvoiceNumber());
            invoiceVo.setSelectable(false);
            invoiceVo.setForbiddenReason(FORBIDDEN_REASON_INVOICE_TENOR);
            return;
        }

        checkResult = checkMaxFinancingTenor(setting, invoice, submitTime);
        if (!checkResult) {
//            log.info("checkSelectable,checkMaxFinancingTenor failed,invoiceReference:{}", invoiceVo.getInvoiceNumber());
            invoiceVo.setSelectable(false);
            invoiceVo.setForbiddenReason(FORBIDDEN_REASON_FINANCE_TENOR);
            return;
        }
        invoiceVo.setSelectable(true);
    }

    private boolean checkMaxInvoiceTenor(SupplierBankSettingDo setting, BaseInvoiceInfo invoice) {
        if (setting == null) {
            log.warn("checkMaxFinancingTenor,setting is null");
            return false;
        }
        try {
            int days = DateUtils.dateBetween(DateUtils.parseDate(invoice.getInvoiceIssueDate()), localDateTimeToDate(invoice.getMaturityDate()));
//            if (days >= setting.getMaximumInvoiceTenor()) {
//                log.info("days:{},invoiceReference:{},id:{}", days, invoice.getInvoiceReference(), invoice.getId());
//            }
            return days < setting.getMaximumInvoiceTenor();
        } catch (ParseException e) {
            log.error("checkMaxInvoiceTenor,error:{}", e.getMessage(), e);
            throw throwEx(DefaultErrorMessage.SERVER_ERROR);
        }
    }

    private boolean checkMaxFinancingTenor(SupplierBankSettingDo setting, BaseInvoiceInfo invoice, Date submitTime) {
        if (setting == null) {
            log.warn("checkMaxFinancingTenor,setting is null");
            return false;
        }
        try {
            Date maturityDate = localDateTimeToDate(invoice.getMaturityDate());
            // 减掉daysFromPostingDate
            int submitInterval = DateUtils.dateBetween(submitTime, maturityDate);
            int interval = submitInterval - setting.getDaysFromPostingDate();
//            log.info("checkMaxFinancingTenor,submitInterval:{},postingDate:{},interval:{},MaximumFinanceTenor:{}", submitInterval, setting.getDaysFromPostingDate(), interval, setting.getMaximumFinanceTenor() == null ? 0 : setting.getMaximumFinanceTenor().intValue());
            return interval <= setting.getMaximumFinanceTenor().intValue();
        } catch (ParseException e) {
            log.error("checkMaxFinancingTenor,error:{}", e.getMessage(), e);
            throw throwEx(DefaultErrorMessage.SERVER_ERROR);
        }
    }

    private void setOperator(String pageLockKey, SysUser sysUser) {
        RMap<String, Object> map = redissonClient.getMap(pageLockKey);
        Object oldUserId = map.putIfAbsent(RedisKeyUtil.FINANCE_OPERATE_USER_ID, sysUser.getUserId());
        log.info("setOperator,oldUserId:{},sysUser:{}", oldUserId, JsonUtil.toJSON(sysUser));
        if (oldUserId != null && !sysUser.getUserId().equals(oldUserId)) {
            // 返回的userId与当前登录用户id不同,说明有其他用户在操作
            throw new OtmpException(String.valueOf(map.get(RedisKeyUtil.FINANCE_OPERATE_USER_NAME)), DefaultErrorMessage.OTHER_USER_OPERATING.intValue());
        }
        map.putIfAbsent(RedisKeyUtil.FINANCE_OPERATE_USER_NAME, sysUser.getLoginName());
        map.expire(addTimeForInstant(DateUtils.getNowDate(), Calendar.MINUTE, 5));
    }

    private EntityCodeIdDto entityCodeIdMap(Collection<String> companyCodes) {
        log.info("entityCodeIdMap,companyCodes:{}", JsonUtil.toJSON(companyCodes));
        if (CollectionUtils.isEmpty(companyCodes)) {
            log.warn("entityCodeIdMap,companyCodes is empty,return");
            return null;
        }
        List<EntityNameCompanyCodeDo> entityNameCompanyCodeDos = entityCompanyCodeMapper.entityNameCompanyCodes(companyCodes);
        if (CollectionUtils.isEmpty(entityNameCompanyCodeDos)) {
            log.warn("entityCodeIdMap,entityNameCompanyCodeDos is empty,return");
            return null;
        }
        Map<String, String> codeNameMap = new HashMap<>();
        Map<String, Long> codeIdMap = new HashMap<>();
        for (EntityNameCompanyCodeDo item : entityNameCompanyCodeDos) {
            codeNameMap.put(item.getCompanyCode(), item.getEntityName());
            codeIdMap.put(item.getCompanyCode(), item.getEntityId());
        }
        EntityCodeIdDto result = new EntityCodeIdDto();
        result.setCodeNameMap(codeNameMap);
        result.setCodeIdMap(codeIdMap);
        log.info("entityCodeIdMap,result:{}", JsonUtil.toJSON(result));
        return result;
    }

    private Map<String, String> codeEntityMap(Collection<String> companyCodes) {
        EntityCodeIdDto entityCodeIdDto = entityCodeIdMap(companyCodes);
        if (entityCodeIdDto == null) {
            return new HashMap<>();
        }
        return entityCodeIdDto.getCodeNameMap();
    }

    private Map<String, Long> codeIdMap(Collection<String> companyCodes) {
        EntityCodeIdDto entityCodeIdDto = entityCodeIdMap(companyCodes);
        if (entityCodeIdDto == null) {
            return new HashMap<>();
        }
        return entityCodeIdDto.getCodeIdMap();
    }

    private Instant addTimeForInstant(Date baseTime, int field, int interval) {
        return Instant.ofEpochMilli(addTime(baseTime, field, interval).getTime());
    }

    private Date addTime(Date baseTime, int field, int interval) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseTime);
        calendar.add(field, interval);
        return calendar.getTime();
    }

    private String bankSettingKey(Long entityId, String bankId) {
        return entityId + StringConstants.UNDER_LINE + bankId;
    }

    @Override
    public PageInfo<InvoiceVo> confirmInvoiceList(ConfirmInvoiceListParam param) {
        Long supplierId = getSupplierId(param.getUserId());
//        Long supplierId = 1743109769984897024L;
        log.info("confirmInvoiceListConfirm,supplierId:{},param:{}", supplierId, JsonUtil.toJSON(param));
        PageInfo<InvoiceVo> result = new PageInfo<>();
        long processStart = System.currentTimeMillis();
        List<Long> defaultChecked = processUserCheck(supplierId, param.getUnchecked(), param.getChecked());
        long processEnd = System.currentTimeMillis();
        log.info("confirmInvoiceList, processUserCheck cost time:{}", (processEnd - processStart));
//        log.info("confirmInvoiceListConfirm,defaultChecked:{}", JsonUtil.toJSON(defaultChecked));
        if (CollectionUtils.isEmpty(defaultChecked)) {
            log.info("confirmInvoiceListConfirm,defaultChecked is empty");
            result.setTotal(0);
            result.setList(new ArrayList<>());
            return result;
        }
        Collections.sort(defaultChecked);
        List<SupplierUniqueIdDo> supplierUniqueIdDos = selectUniqueIdBySupplierId(supplierId);
        // supplier关联的公司代码
        List<String> companyCodes = supplierBankSettingMapper.selectBankCompanyCodes(supplierId, param.getBankId());
        // entityName和companyCode的关联关系
        Map<String, String> codeEntityMap = codeEntityMap(companyCodes);
        log.info("confirmInvoiceListConfirm,codeEntityMap:{}", JsonUtil.toJSON(codeEntityMap));
        int pageNum = (param.getPageNum() == null || param.getPageNum() <= 0) ? Constants.DEFAULT_PAGE_NUM : param.getPageNum();
        int pageSize = (param.getPageSize() == null || param.getPageSize() <= 0) ? Constants.DEFAULT_PAGE_SIZE : param.getPageSize();
        long countStart = System.currentTimeMillis();
        long totalCount = 0;
        List<List<Long>> lists = splitList(defaultChecked, UPDATE_BATCH_ID_SIZE);
        for (List<Long> list : lists) {
            Long batchCount = financeInvoiceApMapper.selectConfirmInvoicesCount(param.getMaturityDate(), list, companyCodes, supplierUniqueIdDos);
            totalCount += (batchCount == null ? 0 : batchCount);
        }
        long countEnd = System.currentTimeMillis();
        log.info("confirmInvoiceListConfirm,count cost time:{}", (countEnd - countStart));
        if (totalCount == 0) {
            log.info("confirmInvoiceListConfirm,totalCount is 0,return");
            result.setTotal(0);
            result.setList(new ArrayList<>());
            return result;
        }
        // ============================
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = startIndex + pageSize;
        startIndex = Math.min(defaultChecked.size(), startIndex);
        endIndex = Math.min(defaultChecked.size(), endIndex);
        log.info("confirmInvoiceListConfirm,page:{},pageSize:{},startIndex:{},endIndex:{}", pageNum, pageSize, startIndex, endIndex);
        List<Long> ids = defaultChecked.subList(startIndex, endIndex);
        // ============================
//        PageHelper.startPage(pageNum, pageSize);
        log.info("confirmInvoiceListConfirm,ids:{}", JsonUtil.toJSON(ids));
        long contentStart = System.currentTimeMillis();
        if (CollectionUtils.isEmpty(ids)) {
            log.info("confirmInvoiceListConfirm,ids is empty,return");
            result.setTotal(totalCount);
            result.setList(new ArrayList<>());
            return result;
        }
        List<FinanceInvoiceApDo> financeInvoice = financeInvoiceApMapper.selectConfirmInvoices(param.getMaturityDate(), ids, companyCodes, supplierUniqueIdDos);
        long contentEnd = System.currentTimeMillis();
        log.info("confirmInvoiceListConfirm,get page content cost time:{}", (contentEnd - contentStart));
        Set<String> companyCodeSet = financeInvoice.parallelStream().map(BaseInvoiceInfo::getCompanyCode).collect(Collectors.toSet());
        log.info("confirmInvoiceListConfirm,companyCodeSet:{}", JsonUtil.toJSON(companyCodeSet));
        List<EntityCompanyCodeDo> entityCompanyCodeDos = getEntityCompanyCodeDos(companyCodeSet);
        Map<String, Long> companyCodeMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(entityCompanyCodeDos)) {
            companyCodeMap = entityCompanyCodeDos.stream().collect(Collectors.toMap(EntityCompanyCodeDo::getCompanyCode, EntityCompanyCodeDo::getEntityId));
        }
        log.info("confirmInvoiceListConfirm,companyCodeMap:{}", JsonUtil.toJSON(companyCodeMap));
        Collection<Long> entityIds = companyCodeMap.values();
        List<SupplierBankSettingDo> settings = supplierBankSettingMapper.selectBySupplierBankEntityIds(supplierId, param.getBankId(), entityIds);
        Map<String, SupplierBankSettingDo> settingEntityIdMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(settings)) {
            settingEntityIdMap = settings.stream().collect(Collectors.toMap(t -> bankSettingKey(t.getEntityId(), t.getBankId()), t -> t));
        }

        Date baseTime = DateUtils.getNowDate();
        List<InvoiceVo> resultList = new ArrayList<>(financeInvoice.size());
        for (FinanceInvoiceApDo invoiceApDo : financeInvoice) {
            String name = codeEntityMap.get(invoiceApDo.getCompanyCode());
            InvoiceVo invoiceVo = convertToInvoiceVo(name, invoiceApDo);
            if (invoiceApDo.getConfirmedMaturityDate() != null) {
                invoiceVo.setMaturityDate(DateUtils.parseDateToStr(DateUtils.DATE_PATTERN, localDateTimeToDate(invoiceApDo.getConfirmedMaturityDate())));
            }
            Long entityId = companyCodeMap.get(invoiceApDo.getCompanyCode());
            SupplierBankSettingDo setting = null;
            if (entityId != null) {
                setting = settingEntityIdMap.get(bankSettingKey(entityId, param.getBankId()));
            }
            if (setting != null) {
                fillTenor(invoiceVo, setting, baseTime);
            }
            resultList.add(invoiceVo);
        }
//        result.setSize((int) pageInfo.getTotal());
        result.setSize((int) totalCount);
        result.setList(resultList);
        log.info("confirmInvoiceListConfirm,result:{}", JsonUtil.toJSON(result));
        return result;
    }

    private List<Long> processUserCheck(Long supplierId, List<Long> userUnchecked, List<Long> userChecked) {
        log.info("processUserCheck,supplierId:{}", supplierId);
        StopWatch watch = new StopWatch("processUserCheck");
        watch.start("get data");
        String checkedIdListKey = RedisKeyUtil.idCheckedKey(supplierId);
        log.info("processUserCheck,checkedIdListKey:{}", checkedIdListKey);
        RList<String> checkedIdList = redissonClient.getList(checkedIdListKey);
        int dataLength = checkedIdList.size();
        log.info("processUserCheck,dataLength:{}", dataLength);
        List<String> dataCopy = new ArrayList<>();
        if (dataLength < UPDATE_BATCH_ID_SIZE) {
            dataCopy.addAll(checkedIdList);
        } else {
            int loopCount = (dataLength % UPDATE_BATCH_SIZE) == 0 ? dataLength / UPDATE_BATCH_SIZE : dataLength / UPDATE_BATCH_SIZE + 1;
            for (int i = 0; i < loopCount; i++) {
                int startIndex = i * UPDATE_BATCH_SIZE;
                int endIndex = startIndex + UPDATE_BATCH_SIZE;
                startIndex = Math.min(dataLength, startIndex);
                endIndex = Math.min(dataLength, endIndex);
                dataCopy.addAll(checkedIdList.subList(startIndex, endIndex));
            }
        }
        watch.stop();
        // 默认选中的发票id减去用户手动取消选中的发票id,加上手动选中的发票id
        watch.start("get checked");
        List<Long> defaultChecked = dataCopy.parallelStream().map(Long::valueOf).collect(Collectors.toList());
        log.info("processUserCheck,defaultChecked.size:{}", defaultChecked.size());
        watch.stop();
        watch.start("process param");
        if (CollectionUtils.isNotEmpty(userUnchecked)) {
            defaultChecked.removeAll(userUnchecked);
        }
        if (CollectionUtils.isNotEmpty(userChecked)) {
            defaultChecked.addAll(userChecked);
        }
        watch.stop();
//        log.info("processUserCheck,defaultChecked:{}", JsonUtil.toJSON(defaultChecked));
        log.info("processUserCheck,cost time:\n{}", watch.prettyPrint());
        return defaultChecked;
    }

    private void fillTenor(InvoiceVo invoiceVo, SupplierBankSettingDo setting, Date submitTime) {
        invoiceVo.setMaxFinanceTenor(setting.getMaximumFinanceTenor().intValue());
        try {
            Date maturityDate = DateUtils.dateParse(invoiceVo.getMaturityDate(), DateUtils.DATE_PATTERN);
            int interval = DateUtils.dateBetween(submitTime, maturityDate) - setting.getDaysFromPostingDate();
            invoiceVo.setFinanceTenorInterval(interval);
        } catch (ParseException e) {
            log.error("fillTenor,parse time error,invoiceVo:{}", JsonUtil.toJSON(invoiceVo));
        }
    }

    @Override
    public boolean renewalLockPageKey(Long userId) {
        Long supplierId = getSupplierId(userId);
        log.info("renewalLockPageKey,userId:{},supplierId:{}", userId, supplierId);
        String redisKey = RedisKeyUtil.pageLockKey(supplierId);
        Instant time = addTimeForInstant(DateUtils.getNowDate(), Calendar.MINUTE, 5);
        RMap<String, Object> map = redissonClient.getMap(redisKey);
        if (map != null) {
            map.expire(time);
        }
        String dataKey = RedisKeyUtil.dataCacheKey(supplierId);
        RList<FinanceInvoiceApDo> list = redissonClient.getList(dataKey);
        if (list != null) {
            list.expire(time);
        }
        String checkKey = RedisKeyUtil.dataCheckedKey(supplierId);
        RList<InvoiceVo> checkedDataList = redissonClient.getList(checkKey);
        if (checkedDataList != null) {
            checkedDataList.expire(time);
        }
        String idCheckedKey = RedisKeyUtil.idCheckedKey(supplierId);
        RList<String> checkIdList = redissonClient.getList(idCheckedKey);
        if (checkIdList != null) {
            checkIdList.expire(time);
        }
        String dataTailCacheKey = RedisKeyUtil.dataTailCacheKey(supplierId);
        RList<FinanceInvoiceApDo> debitTailCacheList = redissonClient.getList(dataTailCacheKey);
        if (debitTailCacheList != null) {
            debitTailCacheList.expire(time);
        }
        return true;
    }

    @Override
    public boolean removeLockPageKey(Long userId) {
        Long supplierId = getSupplierId(userId);
        log.info("removeLockPageKey,userId:{},supplierId:{}", userId, supplierId);
        String dataKey = RedisKeyUtil.dataCacheKey(supplierId);
        RList<FinanceInvoiceApDo> list = redissonClient.getList(dataKey);
        String checkKey = RedisKeyUtil.dataCheckedKey(supplierId);
        RList<InvoiceVo> checkedIdList = redissonClient.getList(checkKey);
        String redisKey = RedisKeyUtil.pageLockKey(supplierId);
        RMap<String, Object> map = redissonClient.getMap(redisKey);
        if (map != null) {
            Object o = map.get(RedisKeyUtil.FINANCE_OPERATE_USER_ID);
            if (o == null) {
                releaseCacheData(map, list, checkedIdList);
            } else {
                Long userIdInCache = (Long) o;
                if (userIdInCache.equals(userId)) {
                    releaseCacheData(map, list, checkedIdList);
                }
            }
        }
        return true;
    }

    private static void releaseCacheData(RMap<String, Object> map, RList<FinanceInvoiceApDo> list, RList<InvoiceVo> checkedIdList) {
        map.delete();
        if (list != null) {
            list.delete();
        }
        if (checkedIdList != null) {
            checkedIdList.delete();
        }
    }

    //    @Transactional(rollbackFor = Exception.class)
    @Override
    public SubmitResultVo submit(InvoiceSubmitParam param) {
        SubmitResultVo result = new SubmitResultVo();
        SubmitBatchResult submitBatchResult = doSubmit(param);
        List<FinanceBatchDo> successBatches = submitBatchResult.getSuccessBatches();
        if (!CollectionUtils.isNotEmpty(submitBatchResult.getSuccessBatches())) {
            log.warn("submit,successBatches is empty,return");
            return result;
        }
        sender.asynSend(invoiceSubmitTopic, UUID.randomUUID().toString(), JsonUtil.toJSON(successBatches), KafkaFactory.INVOICE_BATCH_SUBMIT_FACTORY, new KafkaCallback(invoiceSubmitTopic), false);
        result.setAmount(submitBatchResult.getAmount());
        result.setCount(submitBatchResult.getCount());
        return result;
    }

    private List<InvoiceVo> getDataChecked(Long supplierId) {
        String dataCheckedKey = RedisKeyUtil.dataCheckedKey(supplierId);
        log.info("getDataChecked,dataCheckedKey:{}", dataCheckedKey);
        RList<InvoiceVo> checked = redissonClient.getList(dataCheckedKey);
        int dataLength = checked.size();
        List<InvoiceVo> allInvoice = new ArrayList<>();
        if (dataLength < QUERY_SIZE) {
            allInvoice.addAll(checked);
        } else {
            int loopCount = (dataLength % QUERY_SIZE) == 0 ? dataLength / QUERY_SIZE : dataLength / QUERY_SIZE + 1;
            for (int i = 0; i < loopCount; i++) {
                int startIndex = i * QUERY_SIZE;
                int endIndex = startIndex + QUERY_SIZE;
                startIndex = Math.min(dataLength, startIndex);
                endIndex = Math.min(dataLength, endIndex);
                allInvoice.addAll(checked.subList(startIndex, endIndex));
            }
        }
        return allInvoice;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SubmitBatchResult doSubmit(InvoiceSubmitParam param) {
        log.info("submit,param:{}", JsonUtil.toJSON(param));
        // 用户的supplierId及登录名
        String loginName = param.getLoginName();
        Long userId = param.getUserId();
        String supplierId = getSupplierIdByUserId(userId);
        log.info("submit,supplierId:{}",supplierId);
//        String supplierId = "1743109769984897024";
        Long supplierIdNum = Long.valueOf(supplierId);

        SubmitBatchResult result = new SubmitBatchResult();
        RLock decreaseLock = redissonClient.getLock(RedisKeyUtil.getDecreaseAmountLockKey());
        try {
            boolean tryResult = decreaseLock.tryLock(5, 30, TimeUnit.SECONDS);
            if (tryResult) {
                StopWatch watch = new StopWatch("doSubmit");
                watch.start("check");
                String bankId = param.getBankId();
                // 0,检查数据合法性
                // 0.0,检查supplier目前是否可用
                SupplierDo supplier = checkSupplierStatus(supplierIdNum, supplierId, userId);
                // 默认选中的发票
                List<InvoiceVo> checkedCopy = getDataChecked(supplierIdNum);
                // 用户手动选择的id
                List<Long> userChecked = param.getChecked();
                // 用户手动取消选择的id
                List<Long> userUnchecked = param.getUnchecked();
                log.info("submit,userChecked:{},userUnchecked:{}", JsonUtil.toJSON(userChecked), JsonUtil.toJSON(userUnchecked));
                List<InvoiceVo> tempCheckResult = new ArrayList<>();
                Set<String> companyCodes = new HashSet<>();
                // 0.2,处理取消选中
                if (CollectionUtils.isNotEmpty(checkedCopy)) {
                    for (InvoiceVo invoiceVo : checkedCopy) {
                        companyCodes.add(invoiceVo.getCompanyCode());
                        if (userUnchecked.contains(Long.valueOf(invoiceVo.getId()))) {
                            continue;
                        }
                        invoiceVo.setBankId(param.getBankId());
                        tempCheckResult.add(invoiceVo);
                    }
                }
                if (CollectionUtils.isEmpty(tempCheckResult)
                        && CollectionUtils.isEmpty(userChecked)) {
                    log.info("submit,tempCheckResult and userChecked are empty,userChecked:{},userUnchecked:{},", JsonUtil.toJSON(userChecked), JsonUtil.toJSON(userUnchecked));
                    throw throwEx(DefaultErrorMessage.INVOICE_LIST_IS_NULL);
                }

                // 添加用户手动选择的发票
                if (CollectionUtils.isNotEmpty(userChecked)) {
                    List<FinanceInvoiceApDo> financeInvoiceApDos = financeInvoiceApMapper.selectInvoicesByIds(userChecked);
                    for (FinanceInvoiceApDo financeInvoiceApDo : financeInvoiceApDos) {
                        tempCheckResult.add(convertToInvoiceVo(null, financeInvoiceApDo));
                        companyCodes.add(financeInvoiceApDo.getCompanyCode());
                    }
                }
                if (CollectionUtils.isEmpty(tempCheckResult)) {
                    log.info("submit,tempCheckResult is empty,userChecked:{},userUnchecked:{},", JsonUtil.toJSON(userChecked), JsonUtil.toJSON(userUnchecked));
                    throw throwEx(DefaultErrorMessage.INVOICE_LIST_IS_NULL);
                }
                // 提取出发票id列表
                List<Long> submitInvoiceIds = tempCheckResult.parallelStream().map(t -> Long.valueOf(t.getId())).collect(Collectors.toList());
                log.info("submit,submitInvoiceIds:{}",JsonUtil.toJSON(submitInvoiceIds));
                // 0.2,当前supplier关联的supplierCode(vendorCode)
                List<SupplierCompanyCodeWithEntityDo> vendorCodeDos = supplierCompanyCodeMapper.selectCompanyCodeBySupplier(Collections.singletonList(supplierIdNum));
                log.info("submit,vendorCodeDos:{}", JsonUtil.toJSON(vendorCodeDos));
                if (CollectionUtils.isEmpty(vendorCodeDos)) {
                    log.info("submit,vendorCodeDos is empty,supplierId:{}", supplierId);
                    throw throwEx(DefaultErrorMessage.VENDOR_CODE_IS_NULL);
                }
                // 0.3,根据vendorCode和传入的发票id查询supplier拥有的发票列表
                Set<String> vendorCodes = vendorCodeDos.parallelStream().map(SupplierCompanyCodeDo::getSupplierCode).collect(Collectors.toSet());
                log.info("submit,vendorCodes:{}", JsonUtil.toJSON(vendorCodes));
                long start = System.currentTimeMillis();
                List<FinanceInvoiceApDo> invoiceApList = new ArrayList<>();
                if (submitInvoiceIds.size() < UPDATE_BATCH_ID_SIZE) {
                    invoiceApList = financeInvoiceApMapper.selectInvoicesBySupplierId(vendorCodes, submitInvoiceIds);
                } else {
                    List<List<Long>> lists = splitList(submitInvoiceIds, UPDATE_BATCH_ID_SIZE);
                    for (List<Long> list : lists) {
                        List<FinanceInvoiceApDo> batchInvoices = financeInvoiceApMapper.selectInvoicesBySupplierId(vendorCodes, list);
                        invoiceApList.addAll(batchInvoices);
                    }
                }
                long end = System.currentTimeMillis();
                log.info("doSubmit,queryInvoiceApList cost time:{}", (end - start));
                if (CollectionUtils.isEmpty(invoiceApList)) {
                    log.info("submit,invoiceApList is empty,vendCodes:{},invoiceIds:{}", JsonUtil.toJSON(vendorCodes), JsonUtil.toJSON(param.getInvoiceIds()));
                    throw throwEx(DefaultErrorMessage.INVOICE_LIST_IS_NULL);
                }
                // 0.4,如果查询到的发票列表长度小于传入的列表长度,说明传入了不可用的发票,中断流程
                if (invoiceApList.size() < submitInvoiceIds.size()) {
                    log.info("submit,supplierId:{},param.invoiceIds:{} contains illegal invoice", supplierId, JsonUtil.toJSON(submitInvoiceIds));
                    throw throwEx(DefaultErrorMessage.ILLEGAL_SUBMIT_INVOICE_ID);
                }
                log.info("submit,invoiceApList:{}", JsonUtil.toJSON(invoiceApList));
                watch.stop();
                watch.start("prepare data");
                // 1,准备数据
                // 1.1,准备数据,invoiceApList整理为supplierCode(vendorCode)集合.
                Set<String> vendorCodeSet = invoiceApList.parallelStream().map(BaseInvoiceInfo::getVendorCode).collect(Collectors.toSet());
                log.info("submit,vendorCodeSet:{}", JsonUtil.toJSON(vendorCodeSet));
                // 1.1,根据vendorCodeSet查询相关的supplier
                List<SupplierWithVendorCode> supplierWithVendorCodeList = supplierMapper.selectSupplierByVendorCodes(null);
                if (CollectionUtils.isEmpty(supplierWithVendorCodeList)) {
                    log.info("submit,supplierWithVendorCodeList is empty");
                    throw throwEx(DefaultErrorMessage.VENDOR_CODE_IS_NULL);
                }
                log.info("submit,supplierWithVendorCodeList:{}", JsonUtil.toJSON(supplierWithVendorCodeList));
                // 1.2,查询结果整理为map,key: supplierCode(vendorCode),value: currency
                Map<String, String> vendorCodeMap = supplierWithVendorCodeList.parallelStream()
                        .collect(Collectors.toMap(SupplierWithVendorCode::getSupplierCode, SupplierDo::getCurrency, (k1, k2) -> k2));
                log.info("submit,vendorCodeMap:{}", JsonUtil.toJSON(vendorCodeMap));
                // 1.3,准备数据,查询相关的entity
                Set<String> companyCodeSet = invoiceApList.parallelStream().map(BaseInvoiceInfo::getCompanyCode).collect(Collectors.toSet());
                log.info("submit,companyCodeSet:{}", JsonUtil.toJSON(companyCodeSet));
                List<EntityCompanyCodeDo> entityCompanyCodeDos = getEntityCompanyCodeDos(companyCodeSet);
                log.info("submit,entityCompanyCodeDos:{}", JsonUtil.toJSON(entityCompanyCodeDos));
                Map<String, Long> companyCodeMap = new HashMap<>();
                // 1.3,整理为map,key: companyCode,value: entityId
                if (CollectionUtils.isNotEmpty(entityCompanyCodeDos)) {
                    companyCodeMap = entityCompanyCodeDos.parallelStream()
                            .collect(Collectors.toMap(EntityCompanyCodeDo::getCompanyCode, EntityCompanyCodeDo::getEntityId, (k1, k2) -> k1));
                }
                // 1.4,准备数据,查询相关的SupplierBankSetting
                Set<Long> supplierIdSet = supplierWithVendorCodeList.parallelStream().map(SupplierDo::getId).collect(Collectors.toSet());
                log.info("submit,supplierIdSet:{}", JsonUtil.toJSON(supplierIdSet));
                List<SupplierBankSettingDo> supplierBankSettingDos = getSupplierBankSettingDos(supplierIdSet);
                log.info("submit,supplierBankSettingDos:{}", JsonUtil.toJSON(supplierBankSettingDos));
                // 1.4,整理为map,key为: supplierId_bankId_entityId的形式
                Map<String, SupplierBankSettingDo> bankSettingMap = supplierBankSettingDos.parallelStream()
                        .collect(Collectors.toMap(s -> getKey(String.valueOf(s.getSupplierId()), s.getBankId(), s.getEntityId()), t -> t, (k1, k2) -> k2));
                // 1.5,准备数据,用于进行HygieneCheck的Limit额度校验
                Map<String, EntityBankSettingDo> entityBankSettingMap = getEntityBankSettingMap(Collections.singletonList(bankId), new ArrayList<>(companyCodeMap.values()));
                log.info("submit,entityBankSettingMap:{}", JsonUtil.toJSON(entityBankSettingMap));
                // 1.6,准备数据,supplier已提交的发票。
                List<InvoiceSubmitRecord> invoiceSubmitRecords = invoiceSubmitRecordMapper.selectRecordsBySupplierId(supplierIdNum, bankId);
                Map<String, List<InvoiceSubmitRecord>> outstandingRecordGroups = invoiceSubmitRecords.parallelStream().collect(Collectors.groupingBy(this::outstandingGroupKey));
                Map<String, BigDecimal> outstandingAmountGroups = new HashMap<>();
                if (!outstandingRecordGroups.isEmpty()) {
                    for (Map.Entry<String, List<InvoiceSubmitRecord>> entry : outstandingRecordGroups.entrySet()) {
                        BigDecimal outstandingAmount = new BigDecimal("0");
                        List<InvoiceSubmitRecord> records = entry.getValue();
                        if (CollectionUtils.isNotEmpty(records)) {
                            for (InvoiceSubmitRecord record : records) {
                                if (InvoiceConstants.INVOICE_TYPE_DEBIT_STR.equals(record.getInvoiceType())) {
                                    BigDecimal debitAmount = new BigDecimal(String.valueOf(record.getAmount()));
                                    outstandingAmount = outstandingAmount.add(debitAmount);
                                } else {
                                    BigDecimal creditAmount = new BigDecimal(String.valueOf(record.getAmount()));
                                    outstandingAmount = outstandingAmount.subtract(creditAmount);
                                }
                            }
                        }
                        outstandingAmountGroups.put(entry.getKey(), outstandingAmount);
                    }
                }
                watch.stop();
                watch.start("hygiene check");
                LocalDateTime now = LocalDateTime.now();
                // 2,Hygiene Check
                // 2.1,遍历发票列表,填充HygieneCheck所需参数
                List<InvoiceAp> invoiceAps = new ArrayList<>();
                // 2.1,可用发票按id整理为map,供之后拆分batch使用
//                 Map<Long, FinanceInvoiceApDo> invoiceIdMap = new HashMap<>();
                // 2.2.1,对invoice按bankId,entityId分组计算额度,供之后扣减限度使用
                Map<String, Map<Long, BigDecimal>> bankEntityAmountMap = new HashMap<>();
//                // 2.2.2,生成InvoiceDTO列表,用于之后的发票拆分
//                List<InvoiceDTO> invoiceDTOList = new ArrayList<>(invoiceApList.size());
                // 区分debit和credit,credit需要单独计算额度
                List<InvoiceDTO> tempDebits = new ArrayList<>();
                List<InvoiceDTO> tempCredits = new ArrayList<>();
                BigDecimal debitsAmount = new BigDecimal("0");
                // credit的总额度
                BigDecimal creditsAmount = new BigDecimal("0");
                List<SupplierUniqueIdDo> supplierUniqueIdDos = selectUniqueIdBySupplierId(supplierIdNum);
                log.info("submit,supplierUniqueIdDos:{}", JsonUtil.toJSON(supplierUniqueIdDos));
                List<FinanceInvoiceApDo> availableCredits = financeInvoiceApMapper.availableCreditFinance(companyCodes, supplierUniqueIdDos);
                log.info("submit,availableCredits:{}", JsonUtil.toJSON(availableCredits));
                if (CollectionUtils.isNotEmpty(availableCredits)) {
                    invoiceApList.addAll(availableCredits);
                }
                log.info("submit,invoiceApList:{}", JsonUtil.toJSON(invoiceApList));
                Map<String, Map<String, DebitCompanyBatchAmountWrapper>> debitCompanyBatchAmountMap = new HashMap<>();
                Map<String, CreditCompanyAmountWrapper> creditCompanyAmountMap = new HashMap<>();
                EntityBankSettingDo entityBankSettingDo;
                for (FinanceInvoiceApDo invoiceApDo : invoiceApList) {
                    Long entityId = companyCodeMap.get(invoiceApDo.getCompanyCode());
                    String bankSettingKey = getKey(supplierId, bankId, entityId);
                    SupplierBankSettingDo setting = bankSettingMap.get(bankSettingKey);
                    if (InvoiceConstants.INVOICE_TYPE_DEBIT_STR.equals(invoiceApDo.getInvoiceType())
                            && !checkMaxFinancingTenor(setting, invoiceApDo, localDateTimeToDate(now))) {
                        log.info("submit,invalid maturity date,invoiceApDo:{}", JsonUtil.toJSON(invoiceApDo));
                        throw throwEx(DefaultErrorMessage.ILLEGAL_MATURITY_DATE);
                    }
                    if (InvoiceConstants.INVOICE_TYPE_DEBIT_STR.equals(invoiceApDo.getInvoiceType())
                            && !checkMaxInvoiceTenor(setting, invoiceApDo)) {
                        log.info("submit,check max invoice tenor failed,invoiceApDo:{}", JsonUtil.toJSON(invoiceApDo));
                        throw throwEx(DefaultErrorMessage.INVOICE_TENOR_ERROR);
                    }

                    String currency = vendorCodeMap.get(invoiceApDo.getVendorCode());
                    InvoiceAp invoiceAp = createInvoiceAp(now, invoiceApDo, currency, setting);
                    String entityBankSettingKey = getEntityBankSettingKey(bankId, entityId);
                    Double limitValue;
                    // 设置limit值,用于hygieneCheck
                    entityBankSettingDo = entityBankSettingMap.get(entityBankSettingKey);
                    if (entityBankSettingDo != null) {
                        BigDecimal outstanding = outstandingAmountGroups.get(outstandingGroupKey(supplierIdNum, bankId, entityId));
                        limitValue = calculateLimit(entityBankSettingDo, outstanding);
                        BankLimit limit = new BankLimit(bankId, limitValue);
                        invoiceAp.setLimits(Collections.singletonList(limit));
                    } else {
                        log.info("submit,entityBankSettingDo is null.entityBankSettingKey:{}", entityBankSettingKey);
                        throw throwEx(DefaultErrorMessage.ENTITY_BANK_SETTING_NOT_FOUND);
                    }
                    invoiceAps.add(invoiceAp);

                    // 分离debit和credit
                    InvoiceDTO invoiceDTO = new InvoiceDTO(invoiceApDo).setBankId(bankId).setEntityId(entityId);
                    Double invoiceAmount = invoiceApDo.getInvoiceAmount();
                    BigDecimal amount = invoiceAmount == null ? new BigDecimal("0") : new BigDecimal(String.valueOf(Math.abs(invoiceAmount)));
                    if (InvoiceConstants.INVOICE_TYPE_DEBIT_STR.equals(invoiceApDo.getInvoiceType())) {
                        tempDebits.add(invoiceDTO);
                        debitsAmount = debitsAmount.add(amount);
                    } else {
                        tempCredits.add(invoiceDTO);
                        creditsAmount = creditsAmount.add(amount);

                        CreditCompanyAmountWrapper creditAmountWrapper = creditCompanyAmountMap.get(invoiceDTO.getCompanyCode());
                        BigDecimal invoiceCompanyAmount;
                        List<InvoiceDTO> companyCodeCredits;
                        if (creditAmountWrapper == null) {
                            creditAmountWrapper = new CreditCompanyAmountWrapper();
                            invoiceCompanyAmount = new BigDecimal("0");
                            companyCodeCredits = new ArrayList<>();
                        } else {
                            invoiceCompanyAmount = creditAmountWrapper.getAmount();
                            companyCodeCredits = creditAmountWrapper.getCredits();
                        }
                        invoiceCompanyAmount = invoiceCompanyAmount.add(BigDecimal.valueOf(invoiceDTO.getInvoiceAmount()));
                        companyCodeCredits.add(invoiceDTO);
                        creditAmountWrapper.setCredits(companyCodeCredits);
                        creditAmountWrapper.setAmount(invoiceCompanyAmount);
                        creditCompanyAmountMap.put(invoiceDTO.getCompanyCode(), creditAmountWrapper);
                    }
                    fillMap(bankId, entityId, invoiceAp.getInvoiceAmount(), bankEntityAmountMap);
                }
                log.info("submit,creditCompanyAmountMap:{}", JsonUtil.toJSON(creditCompanyAmountMap));
                if (CollectionUtils.isEmpty(tempDebits)) {
                    log.info("submit,tempDebits is empty,tempDebits:{}", JsonUtil.toJSON(tempDebits));
                    throw throwEx(DefaultErrorMessage.INVOICE_LIST_IS_NULL);
                }
                // 计算总融资额度
                BigDecimal totalAmount = debitsAmount.subtract(creditsAmount);
                log.info("submit,totalAmount:{}", totalAmount.toPlainString());

                // 校验最小融资额度
                Double minAmountNum = supplier.getMinimumNetFinancingAmount();
                BigDecimal minAmount = new BigDecimal(String.valueOf(minAmountNum == null ? "0" : minAmountNum));
                if (totalAmount.compareTo(minAmount) < 0) {
                    log.info("submit,totalAmount is less than minAmount,minAmount:{},totalAmount:{}", minAmountNum, totalAmount);
                    throw throwEx(DefaultErrorMessage.LESS_THAN_MIN_AMOUNT);
                }
                watch.stop();
                watch.start("execute hygiene check");
                log.info("submit,invoiceAps:{}", JsonUtil.toJSON(invoiceAps));
                // 2.3,执行Hygiene Check
                filterContext.doFilter(invoiceAps);
                watch.stop();
                watch.start("create invoice batch");
                // 3,发票拆分到batch,拆分规则: bank + entity + maturity_date + currency
                // 3.1,获取银行map
                Map<String, BankDo> bankMap = getBankMap();
                List<FinanceBatchDo> batches = new ArrayList<>();
                Map<Long, List<FinanceBatchInvoiceDo>> invoiceListMap = new HashMap<>();
                Map<Long, List<InvoiceSubmitRecord>> recordMap = new HashMap<>();
                // 遍历map,按银行-Entity-MaturityDate分组
                Map<String, List<InvoiceDTO>> bankEntityDateGroupMap = tempDebits.parallelStream()
                        .collect(Collectors.groupingBy(t -> t.getBankId() + StringConstants.UNDER_LINE + t.getEntityId() + StringConstants.UNDER_LINE
                                + DateUtils.parseDateToStr(DateUtils.DATE_TIME_PATTERN, localDateTimeToDate(t.getMaturityDate())) + StringConstants.UNDER_LINE + t.getInvoiceCurrency()));
                log.info("submit,bankEntityDateGroupMap:{}", JsonUtil.toJSON(bankEntityDateGroupMap));
                // 外层key:companyCode,内层key:batchNumber
                List<Long> entityIds = new ArrayList<>();
                for (Map.Entry<String, List<InvoiceDTO>> entry : bankEntityDateGroupMap.entrySet()) {
                    List<InvoiceDTO> invoices = entry.getValue();
                    if (CollectionUtils.isEmpty(invoices)) {
                        continue;
                    }

                    InvoiceDTO firstInvoice = invoices.get(0);
                    entityIds.add(firstInvoice.getEntityId());
                    BankDo bankDo = bankMap.get(bankId);
                    if (bankDo == null) {
                        log.error("submit,bank not found,bankId:{}", bankId);
                        throw throwEx(DefaultErrorMessage.BANK_NOT_FOUND);
                    }

                    String batchNum = generateBatchNum(bankDo.getBankIntegrationChain(), now, getSerialNo(now, bankId));
                    log.info("submit,batchNum:{}", batchNum);
                    BigDecimal batchAmount = new BigDecimal("0");
                    FinanceBatchDo batch = createBatch(batchNum, now, loginName, DateUtils.parseDateToStr(DateUtils.DATE_TIME_PATTERN, localDateTimeToDate(firstInvoice.getMaturityDate())));
                    batch.setSupplierId(supplierIdNum);
                    batch.setBankId(bankId);
                    batch.setEntityId(firstInvoice.getEntityId());
                    batch.setSubmitCurrency(firstInvoice.getInvoiceCurrency());
                    batch.setUpdateTime(now);
                    List<FinanceBatchInvoiceDo> invoiceList = new ArrayList<>();
                    List<InvoiceSubmitRecord> records = new ArrayList<>();
                    for (InvoiceDTO invoice : invoices) {
                        FinanceBatchInvoiceDo financeBatchInvoice = createBatchInvoice(batchNum, bankId, invoice.getId(), now, loginName);
                        financeBatchInvoice.setInvoiceReference(invoice.getInvoiceReference());
                        financeBatchInvoice.setSupplierCode(invoice.getVendorCode());
                        financeBatchInvoice.setEntityId(invoice.getEntityId());
                        financeBatchInvoice.setSupplierId(supplierIdNum);
                        // 发票额度
                        BigDecimal amount = invoice.getInvoiceAmount() == null
                                ? new BigDecimal("0")
                                : new BigDecimal(String.valueOf(Math.abs(invoice.getInvoiceAmount())));
                        batchAmount = batchAmount.add(amount);
                        invoiceList.add(financeBatchInvoice);

                        InvoiceSubmitRecord record = createInvoiceSubmitRecord(supplierIdNum, supplier, invoice, batch, batchNum, financeBatchInvoice, bankId, loginName, now);
                        records.add(record);
                        Map<String, DebitCompanyBatchAmountWrapper> debitBatchAmountMap = debitCompanyBatchAmountMap.get(invoice.getCompanyCode());
                        BigDecimal debitBatchAmount = null;
                        DebitCompanyBatchAmountWrapper debitBatchAmountWrapper;
                        if (debitBatchAmountMap == null) {
                            debitBatchAmountMap = new HashMap<>();
                            debitBatchAmountWrapper = new DebitCompanyBatchAmountWrapper();
                        } else {
                            debitBatchAmountWrapper = debitBatchAmountMap.get(batchNum);
                            if (debitBatchAmountWrapper == null) {
                                debitBatchAmountWrapper = new DebitCompanyBatchAmountWrapper();
                            } else {
                                debitBatchAmount = debitBatchAmountWrapper.getAmount();
                            }
                        }
                        debitBatchAmount = debitBatchAmount == null ? new BigDecimal("0") : debitBatchAmount;
                        debitBatchAmount = debitBatchAmount.add(BigDecimal.valueOf(invoice.getInvoiceAmount()));

                        debitBatchAmountWrapper.setAmount(debitBatchAmount);
                        debitBatchAmountWrapper.setBatchNumber(batchNum);
                        debitBatchAmountWrapper.setBatch(batch);
                        debitBatchAmountWrapper.setRecords(records);
                        debitBatchAmountWrapper.setInvoiceList(invoiceList);

                        debitBatchAmountMap.put(batchNum, debitBatchAmountWrapper);
                        debitCompanyBatchAmountMap.put(invoice.getCompanyCode(), debitBatchAmountMap);
                    }
                    batch.setSubmitAmount(batchAmount.doubleValue());
                    invoiceListMap.put(batch.getId(), invoiceList);
                    recordMap.put(batch.getId(), records);
                    batches.add(batch);
                }
                log.info("submit,entityIds:{},debitCompanyBatchAmountMap:{}",JsonUtil.toJSON(entityIds), JsonUtil.toJSON(debitCompanyBatchAmountMap));
                for (Map.Entry<String, CreditCompanyAmountWrapper> creditCompanyAmountEntry : creditCompanyAmountMap.entrySet()) {
                    String companyCode = creditCompanyAmountEntry.getKey();
                    Map<String, DebitCompanyBatchAmountWrapper> debitBatchAmountMap = debitCompanyBatchAmountMap.get(companyCode);
                    if (debitBatchAmountMap == null || debitBatchAmountMap.isEmpty()) {
                        log.info("submit,no available debit batch amount,company code:{}", companyCode);
                        throw throwEx(DefaultErrorMessage.NO_MATCHED_INVOICE_BATCH);
                    }
                    CreditCompanyAmountWrapper valueWrapper = creditCompanyAmountEntry.getValue();
                    boolean cover = false;
                    for (Map.Entry<String, DebitCompanyBatchAmountWrapper> debitBatchAmountEntry : debitBatchAmountMap.entrySet()) {
//                        BigDecimal debitCompanyBatchValue = debitBatchAmountEntry.getValue();
                        DebitCompanyBatchAmountWrapper debitCompanyBatchAmountWrapper = debitBatchAmountEntry.getValue();
                        // debit的额度小于credit的额度时跳过
                        if (debitCompanyBatchAmountWrapper == null
                                || debitCompanyBatchAmountWrapper.getAmount() == null
                                || debitCompanyBatchAmountWrapper.getAmount().compareTo(valueWrapper.getAmount()) <= 0) {
//                            log.info("submit,no available debit batch amount,company code:{},debitCompanyBatchValue:{},creditAmount:{}", companyCode, debitCompanyBatchValue, valueWrapper.getAmount());
                            continue;
                        }
                        cover = true;
                        // 有debit的批次大于credit的额度时，把credit放到该批次中
                        String batchNumber = debitBatchAmountEntry.getKey();

                        FinanceBatchDo batch = debitCompanyBatchAmountWrapper.getBatch();
                        BigDecimal tempAmount = new BigDecimal(String.valueOf(batch.getSubmitAmount()));
                        tempAmount = tempAmount.subtract(valueWrapper.getAmount());
                        batch.setSubmitAmount(tempAmount.doubleValue());
                        List<InvoiceSubmitRecord> records = debitCompanyBatchAmountWrapper.getRecords();
                        List<FinanceBatchInvoiceDo> invoiceList = debitCompanyBatchAmountWrapper.getInvoiceList();

                        List<InvoiceDTO> credits = valueWrapper.getCredits();
                        for (InvoiceDTO credit : credits) {
                            FinanceBatchInvoiceDo creditBatchInvoice = createBatchInvoice(batchNumber, bankId, credit.getId(), now, loginName);
                            creditBatchInvoice.setInvoiceReference(credit.getInvoiceReference());
                            creditBatchInvoice.setSupplierCode(credit.getVendorCode());
                            creditBatchInvoice.setEntityId(credit.getEntityId());
                            creditBatchInvoice.setSupplierId(supplierIdNum);
                            invoiceList.add(creditBatchInvoice);
                            InvoiceSubmitRecord record = createInvoiceSubmitRecord(supplierIdNum, supplier, credit, batch, batchNumber, creditBatchInvoice, bankId, loginName, now);
                            records.add(record);
                        }
                        invoiceListMap.put(batch.getId(), invoiceList);
                        recordMap.put(batch.getId(), records);
                    }
                    if (!cover) {
                        // 同companyCode下没有额度大于credit的debit批次，拋出异常
                        log.info("submit,no available debit batch amount,companyCode:{},amount:{}", companyCode, valueWrapper.getAmount());
                        throw throwEx(DefaultErrorMessage.NO_MATCHED_INVOICE_BATCH);
                    }
                }

                // 检查batchNumber重复
                log.info("submit,batches:{}", JsonUtil.toJSON(batches));
                FinanceBatchDo batchObj = financeBatchMapper.checkBatchDuplicate(batches);
                if (batchObj != null) {
                    log.info("submit,duplicate batch number,batchObj:{}", JsonUtil.toJSON(batchObj));
                    throw throwEx(DefaultErrorMessage.DUPLICATE_BATCH_NUMBER);
                }
                watch.stop();
                watch.start("check limit");
                log.info("submit,tempDebits:{}", JsonUtil.toJSON(tempDebits));
                log.info("submit,tempCredits:{}", JsonUtil.toJSON(tempCredits));
                log.info("submit,invoiceListMap:{}", JsonUtil.toJSON(invoiceListMap));
                log.info("submit,recordMap:{}", JsonUtil.toJSON(recordMap));

                // bankLimit
                List<EntityBankSettingDo> entityBankSettingDos = entityBankSettingMapper.selectByBankIdAndEntityIds(bankId, entityIds);
                if (CollectionUtils.isEmpty(entityBankSettingDos)) {
                    log.info("submit,entityBankSettingDos is empty,bankId:{},entityIds:{}", bankId, entityIds);
                    throw throwEx(DefaultErrorMessage.ENTITY_BANK_SETTING_NOT_FOUND);
                }
                Map<Long, BigDecimal> bankLimitMap = new HashMap<>();
                for (EntityBankSettingDo setting : entityBankSettingDos) {
                    BigDecimal bankLimitAmount = new BigDecimal(setting.getBankLimit() == null ? "0" : String.valueOf(setting.getBankLimit()));
                    if (setting.getAdhocExpiryDate() != null
                            && setting.getAdhocExpiryDate().isAfter(now)
                            && setting.getAdhocLimit() != null) {
                        bankLimitAmount = bankLimitAmount.add(new BigDecimal(String.valueOf(setting.getAdhocLimit())));
                    }
                    bankLimitMap.put(setting.getEntityId(), bankLimitAmount);
                }
                // outstanding
                List<InvoiceGroupAmount> groupAmounts = invoiceSubmitRecordMapper.selectRecordsGroups(bankId, entityIds);
                for (InvoiceGroupAmount ga : groupAmounts) {
                    BigDecimal oldVal = bankLimitMap.get(ga.getEntityId());
                    if (oldVal == null) {
                        continue;
                    }
                    BigDecimal groupAmountValue = ga.getAmount() == null ? new BigDecimal("0") : ga.getAmount();
                    if (InvoiceConstants.INVOICE_TYPE_DEBIT_STR.equals(ga.getType())) {
                        oldVal = oldVal.subtract(groupAmountValue);
                    } else {
                        oldVal = oldVal.add(groupAmountValue);
                    }
                    bankLimitMap.put(ga.getEntityId(), oldVal);
                }
                log.info("submit,bankLimitMap:{}", JsonUtil.toJSON(bankLimitMap));
                watch.stop();
                watch.start("do submit");
                BigDecimal successSubmitAmount = new BigDecimal(0);
                List<Long> updateStatusIds = new ArrayList<>();
                List<FinanceBatchDo> successBatches = new ArrayList<>();
                List<FinanceBatchInvoiceDo> successInvoiceDos = new ArrayList<>();
                List<InvoiceSubmitRecord> successSubmitRecords = new ArrayList<>();
                for (FinanceBatchDo batch : batches) {
                    List<FinanceBatchInvoiceDo> invoiceDos = invoiceListMap.get(batch.getId());
                    log.info("submit,batchId:{},invoiceDos.size:{}", batch.getId(), invoiceDos == null ? 0 : invoiceDos.size());
                    if (CollectionUtils.isNotEmpty(invoiceDos)) {
                        FinanceBatchInvoiceDo first = invoiceDos.get(0);
                        Long entityId = first.getEntityId();
                        BigDecimal batchAmount = new BigDecimal(String.valueOf(batch.getSubmitAmount()));
                        BigDecimal availableAmount = bankLimitMap.get(entityId);
                        if (availableAmount.compareTo(batchAmount) >= 0) {
                            successBatches.add(batch);
                            successInvoiceDos.addAll(invoiceDos);
                            List<InvoiceSubmitRecord> records = recordMap.get(batch.getId());
                            if (CollectionUtils.isNotEmpty(records)) {
                                successSubmitRecords.addAll(records);
                            }
                            Set<Long> ids = invoiceDos.stream().map(FinanceBatchInvoiceDo::getInvoiceId).collect(Collectors.toSet());
                            updateStatusIds.addAll(ids);
                            availableAmount = availableAmount.subtract(batchAmount);
                            successSubmitAmount = successSubmitAmount.add(batchAmount);
                            bankLimitMap.put(entityId, availableAmount);
                        } else {
                            log.warn("submit,bankLimit is less than batchAmount.batch:{}", JsonUtil.toJSON(batch));
                        }
                    }
                }
                watch.stop();
                watch.start("insert data");
                updateInvoices(successInvoiceDos, successBatches, successSubmitRecords);
                watch.stop();
                watch.start("update status");
                // 更新发票状态(Waiting -> Financing)
                log.info("submit,updateStatusIds:{}", updateStatusIds);
                if (CollectionUtils.isNotEmpty(updateStatusIds)) {
                    List<List<Long>> batchIdList = splitUpdateList(updateStatusIds);
                    for (List<Long> batchIds : batchIdList) {
                        financeInvoiceApMapper.updateStatus(FinanceInvoiceApDo.STATUS_FINANCING, FinanceInvoiceApDo.STATUS_WAITING, now, loginName, batchIds);
                    }
                }
                watch.stop();
                watch.start("send msg");
                log.info("submit,successBatches:{}", JsonUtil.toJSON(successBatches));
                if (CollectionUtils.isNotEmpty(successBatches)) {
                    // 发送邮件和系统消息
                    invoiceService.sendMailAndNotice(successSubmitAmount.toPlainString(), supplier);
                }
                result.setCount(successInvoiceDos.size());
                result.setAmount(successSubmitAmount.toPlainString());
                result.setSuccessBatches(successBatches);
                watch.stop();
                log.info("doSubmit,cost time:\n{}", watch.prettyPrint());
                return result;
            } else {
                log.error("submit,try lock failed,supplierId:{}", supplierId);
                throw throwEx(DefaultErrorMessage.SUBMIT_FAILED);
            }
        } catch (InterruptedException e) {
            log.error("submit,tryLock error,msg:{}", e.getMessage(), e);
            throw throwEx(DefaultErrorMessage.SERVER_ERROR);
        } finally {
            // 释放缓存过的内容
            releaseCacheData(supplierIdNum);
            if (decreaseLock.isHeldByCurrentThread()) {
                decreaseLock.unlock();
            }
        }
    }

    private void updateInvoices(List<FinanceBatchInvoiceDo> successInvoiceDos, List<FinanceBatchDo> successBatches, List<InvoiceSubmitRecord> successSubmitRecords) {
        if (CollectionUtils.isNotEmpty(successInvoiceDos)) {
            insertSuccessInvoiceDos(successInvoiceDos);
        }
        if (CollectionUtils.isNotEmpty(successBatches)) {
            insertSuccessBatches(successBatches);
        }
        if (CollectionUtils.isNotEmpty(successSubmitRecords)) {
            insertSuccessSubmitRecords(successSubmitRecords);
        }
    }

    private void insertSuccessInvoiceDos(List<FinanceBatchInvoiceDo> successInvoiceDos) {
        List<List<FinanceBatchInvoiceDo>> dataBatches = splitInsertList(successInvoiceDos);
        for (List<FinanceBatchInvoiceDo> dataBatch : dataBatches) {
            financeBatchInvoiceMapper.insertListWithId(dataBatch);
        }
    }

    private void insertSuccessBatches(List<FinanceBatchDo> successBatches) {
        List<List<FinanceBatchDo>> dataBatches = splitInsertList(successBatches);
        for (List<FinanceBatchDo> dataBatch : dataBatches) {
            financeBatchMapper.insertListWithId(dataBatch);
        }
    }

    private void insertSuccessSubmitRecords(List<InvoiceSubmitRecord> successSubmitRecords) {
        List<List<InvoiceSubmitRecord>> dataBatches = splitInsertList(successSubmitRecords);
        for (List<InvoiceSubmitRecord> dataBatch : dataBatches) {
            invoiceSubmitRecordMapper.insertListWithId(dataBatch);
        }
    }

    private <T> List<List<T>> splitUpdateList(List<T> source) {
        return splitList(source, UPDATE_BATCH_SIZE);
    }

    private <T> List<List<T>> splitInsertList(List<T> source) {
        return splitList(source, INSERT_BATCH_SIZE);
    }

    private <T> List<List<T>> splitList(List<T> source, int batchSize) {
        List<List<T>> result = new ArrayList<>();
        int loop = (source.size() % batchSize == 0) ? (source.size() / batchSize) : (source.size() / 10) + 1;
        log.info("splitList,loop:{}", loop);
        for (int i = 0; i < loop; i++) {
            int start = i * batchSize;
            if (start > source.size()) {
                break;
            }
            int end = (i + 1) * batchSize;
            end = Math.min(end, source.size());
            List<T> temp = source.subList(start, end);
            result.add(temp);
        }
        log.info("splitList,result.size:{}", result.size());
        return result;
    }

    private String outstandingGroupKey(InvoiceSubmitRecord record) {
        return outstandingGroupKey(record.getSupplierId(), record.getBankId(), record.getEntityId());
    }

    private String outstandingGroupKey(Long supplierId, String bankId, Long entityId) {
        return supplierId + StringConstants.UNDER_LINE
                + bankId + StringConstants.UNDER_LINE
                + entityId;
    }

    @Async
    @Override
    public void sendMailAndNotice(String amount, SupplierDo supplier) {
        try {
            // 发送邮件,查询所有的anchor
            TableDataInfo tableDataInfo = remoteUserAnchorService.selectAnchorList();
            log.info("sendMailAndNotice,tableDataInfo:{}", JsonUtil.toJSON(tableDataInfo));
            List<?> rows = tableDataInfo.getRows();
            List<String> anchorMails = new ArrayList<>();
            List<String> anchorIds = getAnchorIds(rows, anchorMails);
            log.info("sendMailAndNotice,anchorMails:{},anchorIds:{}", anchorMails, anchorIds);
            String createBy = supplier.getSupplierName();
            String supplierName = supplier.getSupplierName();
            List<SysUserFinancingDo> sysUserFinancingDos = suppliersBySupplierId(supplier.getId());
            if (CollectionUtils.isEmpty(sysUserFinancingDos)) {
                log.warn("sendMailAndNotice,sysUserFinancingDos is empty");
                return;
            }
            List<String> supplierMails = new ArrayList<>();
            List<String> supplierIds = new ArrayList<>();
            for (SysUserFinancingDo sysUserFinancingDo : sysUserFinancingDos) {
                if (StringUtils.isNotEmpty(sysUserFinancingDo.getLoginName())) {
                    supplierMails.add(sysUserFinancingDo.getLoginName());
                }
                supplierIds.add(String.valueOf(sysUserFinancingDo.getUserId()));
            }
            log.info("sendMailAndNotice,supplierName:{},supplierMails:{},supplierIds:{}", supplierName, JsonUtil.toJSON(supplierMails), JsonUtil.toJSON(supplierIds));
            SendMailUtil.MailContentBean contentBean = new SendMailUtil.MailContentBean(supplierName);
            mailUtil.sendSubmitMail(createBy, supplier.getContactEmail(), supplierMails, anchorMails, null, contentBean);
            // 发送消息
            Map<String, String> titleBean = new HashMap<>();
            titleBean.put("SupplierName", supplierName);
            Map<String, String> noticeContentBean = new HashMap<>();
            noticeContentBean.put("Amount", amount);
            NoticeSendDto notice = new NoticeSendDto(NoticeConstant.NOTICE_TYPE_FINANCING, createBy, JsonUtil.toJSON(titleBean), JsonUtil.toJSON(noticeContentBean), supplierIds, anchorIds);
            String noticeContent = JsonUtil.toJSON(notice);
            sender.asynSend(sendNoticeTopic, UUID.randomUUID().toString(), noticeContent, KafkaFactory.SEND_KAFKA_PRODUCER_FACTORY, new KafkaCallback(sendNoticeTopic), false);
        } catch (Exception e) {
            log.error("sendMailAndNotice,send mail or notice error", e);
        }
    }

    private List<SysUserFinancingDo> suppliersBySupplierId(Long supplierId) {
        Example condition = new Example(SysUserFinancingDo.class);
        condition.createCriteria()
                .andEqualTo("delFlag", SysUserFinancingDo.FLAG_NORMAL)
                .andEqualTo("userType", SysUserFinancingDo.USER_TYPE_SUPPLIER)
                .andEqualTo("supplierId", supplierId);
        return sysUserFinancingMapper.selectByExample(condition);
    }

    private List<String> getAnchorIds(List<?> rows, List<String> anchorMails) {
        List<String> anchorIds = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(rows)) {
            for (Object row : rows) {
                RemoteSysUserVO vo = JsonUtil.jsonToObject(JsonUtil.toJSON(row), RemoteSysUserVO.class);
                if (vo != null) {
                    if (StringUtils.isNotEmpty(vo.getLoginName())) {
                        anchorMails.add(vo.getLoginName());
                    }
                    if (vo.getUserId() != null) {
                        anchorIds.add(String.valueOf(vo.getUserId()));
                    }
                }
            }
        }
        return anchorIds;
    }

    private InvoiceSubmitRecord createInvoiceSubmitRecord(Long supplierIdNum, SupplierDo supplier, InvoiceDTO invoiceObject,
                                                          FinanceBatchDo batch, String batchNum, FinanceBatchInvoiceDo creditBatchInvoice,
                                                          String bankId, String loginName, LocalDateTime now) {
        InvoiceSubmitRecord record = new InvoiceSubmitRecord();
        record.setId(SnowFlakeUtil.nextId());
        record.setSupplierId(supplierIdNum);
        record.setSupplierName(supplier.getSupplierName());
        record.setEntityId(invoiceObject.getEntityId());
        record.setBatchId(batch.getId());
        record.setBatchNum(batchNum);
        record.setBatchInvoiceId(creditBatchInvoice.getId());
        record.setBankId(bankId);
        record.setAmount(invoiceObject.getInvoiceAmount());
        record.setInvoiceType(invoiceObject.getInvoiceType());
        record.setMaturityDate(invoiceObject.getMaturityDate());
        record.setCreateBy(loginName);
        record.setCreateTime(now);
        record.setInvoiceId(invoiceObject.getId());
        record.setInvoiceReference(invoiceObject.getInvoiceReference());
        record.setVendorCode(invoiceObject.getVendorCode());
        if (batch.getDiscountDate() != null) {
            record.setDiscountDate(DateUtils.parseDateToStr(DateUtils.DATE_PATTERN, localDateTimeToDate(batch.getDiscountDate())));
        }
        return record;
    }

    private void releaseCacheData(Long supplierId) {
        String dataCheckedKey = RedisKeyUtil.dataCheckedKey(supplierId);
        RList<Object> checkedDataList = redissonClient.getList(dataCheckedKey);
        if (checkedDataList != null) {
            checkedDataList.delete();
        }
        String dataCacheKey = RedisKeyUtil.dataCacheKey(supplierId);
        RList<Object> dataCached = redissonClient.getList(dataCacheKey);
        if (dataCached != null) {
            dataCached.delete();
        }
        String idCheckedKey = RedisKeyUtil.idCheckedKey(supplierId);
        RList<String> checkIdList = redissonClient.getList(idCheckedKey);
        if (checkIdList != null) {
            checkIdList.delete();
        }
        String debitTailDataCacheKey = RedisKeyUtil.dataTailCacheKey(supplierId);
        RList<FinanceInvoiceApDo> debitTailCacheList = redissonClient.getList(debitTailDataCacheKey);
        if (debitTailCacheList != null) {
            debitTailCacheList.delete();
        }
    }

    /**
     * 计算availableLimit
     */
    private Double calculateLimit(EntityBankSettingDo setting, BigDecimal outstanding) {
        return calculateLimit(new BigDecimal(String.valueOf(setting.getBankLimit())),
                outstanding,
                new BigDecimal(String.valueOf(setting.getAdhocLimit())),
                setting.getAdhocExpiryDate());
    }

    /**
     * 计算availableLimit,用于hygieneCheck,bankLimit - outstanding,如果adhoc未过期,则 +adhoc
     */
    private Double calculateLimit(BigDecimal bankLimit, BigDecimal outstanding, BigDecimal adhoc, LocalDateTime adhocExpiryDate) {
        bankLimit = bankLimit == null ? new BigDecimal("0") : bankLimit;
        outstanding = outstanding == null ? new BigDecimal("0") : outstanding;
        BigDecimal available = bankLimit.subtract(outstanding);
        if (adhocExpiryDate != null && LocalDateTime.now().isAfter(adhocExpiryDate)) {
            adhoc = adhoc == null ? new BigDecimal("0") : adhoc;
            available = available.add(adhoc);
        }
        return available.doubleValue();
    }

    private String getEntityBankSettingKey(String bankId, Long entityId) {
        return bankId + StringConstants.UNDER_LINE + entityId;
    }

    private Map<String, EntityBankSettingDo> getEntityBankSettingMap(List<String> bankIds, List<Long> entityIds) {
        List<EntityBankSettingDo> entityBankSettings = this.getEntityBankSettings(bankIds, entityIds);
        if (CollectionUtils.isEmpty(entityBankSettings)) {
            return new HashMap<>();
        }
        return entityBankSettings.parallelStream()
                .collect(Collectors.toMap(t -> (getEntityBankSettingKey(t.getBankId(), t.getEntityId())), ebs -> ebs, (k1, k2) -> k1));
    }

    private List<EntityBankSettingDo> getEntityBankSettings(List<String> bankIds, List<Long> entityIds) {
        Example condition = new Example(EntityBankSettingDo.class);
        condition.createCriteria()
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR)
                .andIn("entityId", entityIds)
                .andIn("bankId", bankIds);
        return entityBankSettingMapper.selectByExample(condition);
    }

    /**
     * 获取流水号
     */
    private String getSerialNo(LocalDateTime baseTime, String bankId) {
        String supplierSubmitSerialNoKey = RedisKeyUtil.submitSerialNum(bankId);
        RAtomicLong atomicLong = redissonClient.getAtomicLong(supplierSubmitSerialNoKey);
        long num = atomicLong.incrementAndGet();
        String serialNum = String.format("%05d", num);
        log.info("getSerialNo,serialNum:{},redisKey:{}", serialNum, supplierSubmitSerialNoKey);
        // 过期时间为base时间+1天,即最后一次操作后+1天.
        Date expiryDate = DateUtils.dateAddDays(baseTime == null ? DateUtils.getNowDate() : localDateTimeToDate(baseTime), 1);
        atomicLong.expire(Instant.ofEpochMilli(expiryDate.getTime()));
        return serialNum;
    }

    private SupplierDo checkSupplierStatus(Long supplierIdNum, String supplierId, Long userId) {
        SupplierDo supplierDo = supplierMapper.selectByPrimaryKey(supplierIdNum);
        if (supplierDo == null) {
            log.info("submit,supplierDo is null,supplierId:{},userId:{}", supplierId, userId);
            throw throwEx(DefaultErrorMessage.SUPPLIER_NOT_FOUND);
        }
        if (!supplierDo.getStatus().equals(SupplierDo.STATUS_ACTIVE)) {
            log.info("submit,supplierDo.status is wrong,supplierId:{},status:{}", supplierId, supplierDo.getStatus());
            throw throwEx(DefaultErrorMessage.SUPPLIER_STATUS_IS_WRONG);
        }
        return supplierDo;
    }

    /**
     * 记录每个Bank-Entity在提交时的发票额度
     */
    private void fillMap(String bankId, Long entityId, BigDecimal amount, Map<String, Map<Long, BigDecimal>> amountMap) {
        Map<Long, BigDecimal> subAmountMap = amountMap.get(bankId);
        if (subAmountMap == null) {
            subAmountMap = new HashMap<>();
            subAmountMap.put(entityId, amount);
        } else {
            BigDecimal oldAmount = subAmountMap.get(entityId);
            if (oldAmount == null) {
                oldAmount = new BigDecimal(0);
            }
            oldAmount = oldAmount.add(amount);
            subAmountMap.put(entityId, oldAmount);
        }
        amountMap.put(bankId, subAmountMap);
    }

    private static FinanceBatchInvoiceDo createBatchInvoice(String batchNum, String bankId, Long invoiceId, LocalDateTime now, String loginName) {
        FinanceBatchInvoiceDo financeBatchInvoice = new FinanceBatchInvoiceDo();
        financeBatchInvoice.setId(SnowFlakeUtil.nextId());
        financeBatchInvoice.setBatchNumber(batchNum);
        financeBatchInvoice.setBankId(bankId);
        financeBatchInvoice.setInvoiceId(invoiceId);
        financeBatchInvoice.setDeleteFlag(StateConstants.FLAG_NORMAL_BOOL);
        financeBatchInvoice.setCreateTime(now);
        financeBatchInvoice.setCreateBy(loginName);
        return financeBatchInvoice;
    }

    /**
     * 根据supplierId查询SupplierBankSetting
     */
    private List<SupplierBankSettingDo> getSupplierBankSettingDos(Set<Long> supplierIdSet) {
        Example bankSettingCondition = new Example(SupplierBankSettingDo.class);
        bankSettingCondition.createCriteria()
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR)
                .andIn("supplierId", supplierIdSet);
        List<SupplierBankSettingDo> supplierBankSettingDos = supplierBankSettingMapper.selectByExample(bankSettingCondition);
        if (CollectionUtils.isEmpty(supplierBankSettingDos)) {
            log.info("submit,supplierBankSettingDos is empty");
            throw throwEx(DefaultErrorMessage.SUPPLIER_BANK_SETTING_IS_NULL);
        }
        return supplierBankSettingDos;
    }

    /**
     * 根据companyCode获取查询的Entity信息
     */
    private List<EntityCompanyCodeDo> getEntityCompanyCodeDos(Set<String> companyCodeSet) {
        List<EntityCompanyCodeDo> entityCompanyCodeDos = entityCompanyCodeMapper.getEntityCompanyCode(companyCodeSet);
        if (CollectionUtils.isEmpty(entityCompanyCodeDos)) {
            log.info("submit,entityCompanyCodeDos is empty");
            throw throwEx(DefaultErrorMessage.ENTITY_COMPANY_CODE_IS_NULL);
        }
        log.info("getEntityCompanyCodeDos,entityCompanyCodeDos:{}", JsonUtil.toJSON(entityCompanyCodeDos));
        return entityCompanyCodeDos;
    }

    private Long getSupplierId(SysUser sysUser) {
        if (sysUser == null) {
            log.info("getSupplierId,sysUser is null");
            throw throwEx(DefaultErrorMessage.ERR_USER_NOT_EXIST);
        }
        String supplierId = sysUser.getSupplierId();
        if (StringUtils.isEmpty(supplierId)) {
            log.error("getSupplierId,supplierId is null,userId:{}", sysUser.getUserId());
            throw throwEx(DefaultErrorMessage.SUPPLIER_ID_NOT_FOUND);
        }
        return Long.valueOf(supplierId);
    }

    private Long getSupplierId(Long userId) {
        return Long.valueOf(getSupplierIdByUserId(userId));
    }

    /**
     * 根据userId获取supplierId
     */
    private String getSupplierIdByUserId(Long userId) {
        SysUser sysUser = getSysUserByUserId(userId);
        String supplierId = sysUser.getSupplierId();
        if (StringUtils.isEmpty(supplierId)) {
            log.error("getSupplierIdByUserId,supplierId is null,userId:{}", userId);
            throw throwEx(DefaultErrorMessage.SUPPLIER_ID_NOT_FOUND);
        }
        SupplierDo supplierDo = supplierMapper.selectByPrimaryKey(supplierId);
        if (supplierDo == null) {
            log.error("getSupplierIdByUserId,supplierDo is null");
            throw throwEx(DefaultErrorMessage.SUPPLIER_NOT_FOUND);
        }

        return supplierId;
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

    private InvoiceAp createInvoiceAp(LocalDateTime submitDate
            , FinanceInvoiceApDo invoice
            , String currency
            , SupplierBankSettingDo settingDo) {
        InvoiceAp item = new InvoiceAp();
        item.setId(String.valueOf(invoice.getId()));
        item.setCompanyCode(invoice.getCompanyCode());
        item.setInvoiceUniqueId(invoice.getInvoiceUniqueId());
        item.setVendorCode(invoice.getVendorCode());
        item.setFiscalYear(invoice.getFiscalYear());
        item.setInvoiceAdditionalReference(invoice.getInvoiceAdditionalReference());
        item.setEccInvoiceNumber(invoice.getEccInvoiceNumber());
        item.setInvoiceIssueDate(invoice.getInvoiceIssueDate());
        item.setInvoiceBaselineDate(invoice.getInvoiceBaselineDate());
        item.setInvoiceDueDate(invoice.getInvoiceDueDate());
        item.setInvoiceCurrency(invoice.getInvoiceCurrency());
        item.setInvoiceAmount(new BigDecimal(String.valueOf(invoice.getInvoiceAmount())));
        item.setTypeOfBilling(invoice.getTypeOfBilling());
        item.setEnterDate(invoice.getEnterDate());
        item.setInvoicePayDate(invoice.getInvoicePayDate());
        item.setInvoiceType(invoice.getInvoiceType());
        item.setInvoiceReference(invoice.getInvoiceReference());
        item.setMaturityDate(localDateTimeToDate(invoice.getMaturityDate()));
        item.setSubmitDate(localDateTimeToDate(submitDate));
        item.setSupplierCurrency(currency);
        if (settingDo == null) {
            log.info("generateInvoiceAp,settingDo is null,invoiceId:{}", invoice.getId());
            throw throwEx(DefaultErrorMessage.SUPPLIER_BANK_SETTING_IS_NULL);
        }
        item.setBankActualPayDate(settingDo.getDaysFromPostingDate());
        item.setMaxFinancingTenor(settingDo.getMaximumFinanceTenor());
        return item;
    }

    private String getKey(String supplierId, String bankId, Long entityId) {
        if (StringUtils.isEmpty(supplierId) || entityId == null || StringUtils.isEmpty(bankId)) {
            log.error("getKey,illegal param,supplierId:{},entityId:{},bankId:{}", supplierId, entityId, bankId);
            throw throwEx(DefaultErrorMessage.SERVER_ERROR);
        }
        return supplierId + StringConstants.UNDER_LINE + bankId + StringConstants.UNDER_LINE + entityId;
    }

    private Map<String, BankDo> getBankMap() {
        Example bankExample = new Example(BankDo.class);
        bankExample.createCriteria().andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        List<BankDo> bankDos = bankMapper.selectByExample(bankExample);
        if (CollectionUtils.isEmpty(bankDos)) {
            log.info("getBanks,bankDos is empty");
            throw throwEx(DefaultErrorMessage.BANK_NOT_FOUND);
        }
        return bankDos.stream().collect(Collectors.toMap(BankDo::getId, t -> t));
    }

    private FinanceBatchDo createBatch(String batchNum, LocalDateTime now, String user, String maturityDate) {
        FinanceBatchDo batch = new FinanceBatchDo();
        batch.setId(SnowFlakeUtil.nextId());
        batch.setBatchNumber(batchNum);
        batch.setSubmissionDate(now);
        batch.setMaturityDate(maturityDate);
        batch.setStatus(InvoiceStatusEnum.FINANCING.code);
        batch.setCreateTime(now);
        batch.setCreateBy(user);
        batch.setDeleteFlag(StateConstants.FLAG_NORMAL_BOOL);
        return batch;
    }

    private String generateBatchNum(String chainName, LocalDateTime now, String serialNo) {
        return chainName + StringConstants.UNDER_LINE
                + DateUtils.dateFormat(localDateTimeToDate(now), DateUtils.DATE_NO_PATTERN)
                + StringConstants.UNDER_LINE + serialNo;
    }

    private List<SupplierUniqueIdDo> selectUniqueIdBySupplierId(Long supplierId) {
        Example condition = new Example(SupplierUniqueIdDo.class);
        condition.createCriteria()
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR)
                .andEqualTo("supplierId", supplierId);
        return supplierUniqueMapper.selectByExample(condition);
    }

    @Override
    public InvoiceCardVo availableMaximumAmount(Long userId, String bankId) {
        Long supplierId = getSupplierId(userId);
        log.info("availableMaximumAmount,userId:{},supplierId:{},bankId:{}", userId, supplierId, bankId);
        SupplierDo supplier = supplierMapper.selectByPrimaryKey(supplierId);
        if (supplier == null) {
            log.info("availableMaximumAmount,supplier not found,supplierId:{}", supplierId);
            throw throwEx(DefaultErrorMessage.SUPPLIER_NOT_FOUND);
        }
        MaxAmountInvoiceCardVo result = new MaxAmountInvoiceCardVo();
        fillResult(result, supplier);
        Example condition = new Example(SupplierBankSettingDo.class);
        condition.createCriteria().andEqualTo("bankId", bankId)
                .andEqualTo("supplierId", supplierId)
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        List<SupplierBankSettingDo> supplierBankSettings = supplierBankSettingMapper.selectByExample(condition);
        if (CollectionUtils.isEmpty(supplierBankSettings)) {
            log.info("availableMaximumAmount,supplierBankSettings is empty");
            throw throwEx(DefaultErrorMessage.SUPPLIER_BANK_SETTING_IS_NULL);
        }

        Set<Long> entityIds = supplierBankSettings.stream().map(SupplierBankSettingDo::getEntityId).collect(Collectors.toSet());
        List<EntityCompanyCodeDo> entityCompanyCodeDos = entityCompanyCodeMapper.selectByEntityIds(entityIds);
        Map<Long, List<EntityCompanyCodeDo>> companyCodeGroups = new HashMap<>();
        if (CollectionUtils.isNotEmpty(entityCompanyCodeDos)) {
            companyCodeGroups = entityCompanyCodeDos.stream().collect(Collectors.groupingBy(EntityCompanyCodeDo::getEntityId));
        }
        log.info("availableMaximumAmount,companyCodeGroups:{}", JsonUtil.toJSON(companyCodeGroups));
        // key:entityId,value:发票融资额度百分比
        Map<String, Double> percentageMap = supplierBankSettings.stream()
                .collect(Collectors.toMap(t -> bankSettingKey(t.getEntityId(), t.getBankId()), SupplierBankSettingDo::getInvoicePercentage));
        log.info("availableMaximumAmount,percentageMap:{}", JsonUtil.toJSON(percentageMap));
        List<SupplierUniqueIdDo> supplierUniqueIdDos = selectUniqueIdBySupplierId(supplierId);
        BigDecimal invoiceTotalAmount = new BigDecimal("0");
        for (Map.Entry<Long, List<EntityCompanyCodeDo>> entry : companyCodeGroups.entrySet()) {
            List<EntityCompanyCodeDo> value = entry.getValue();
            if (CollectionUtils.isEmpty(value)) {
                continue;
            }
            Long entityId = entry.getKey();
            Set<String> companyCodeSet = value.stream().map(EntityCompanyCodeDo::getCompanyCode).collect(Collectors.toSet());
            // TODO 优化for内查询
            AmountWrapper availableAmount = getAvailableAmount(companyCodeSet, supplierUniqueIdDos);
            BigDecimal debitAmount = availableAmount.getDebitAmount();
            BigDecimal creditAmount = availableAmount.getCreditAmount();
            Double percentageNum = percentageMap.get(bankSettingKey(entityId, bankId));
            BigDecimal percentage = percentageNum == null ? new BigDecimal("0.0") : new BigDecimal(String.valueOf(percentageNum));
            debitAmount = debitAmount.multiply(percentage).divide(new BigDecimal(SupplierBankSettingDo.DEFAULT_PERCENTAGE), 2, RoundingMode.HALF_UP);
            invoiceTotalAmount = invoiceTotalAmount.add(debitAmount).subtract(creditAmount);
        }
        log.info("availableMaximumAmount,invoiceTotalAmount:{}", invoiceTotalAmount.toPlainString());
        // 银行可融资额度
        LocalDateTime now = LocalDateTime.now();
        List<EntityBankSettingDo> entityBankSettingDos = entityBankSettingMapper.selectByBankIdAndEntityIds(bankId, entityIds);
        BigDecimal totalBankLimitAmount = new BigDecimal("0");
        if (CollectionUtils.isNotEmpty(entityBankSettingDos)) {
            for (EntityBankSettingDo entityBankSetting : entityBankSettingDos) {
                Double bankLimit = entityBankSetting.getBankLimit();
                BigDecimal bankLimitAmount = new BigDecimal(bankLimit == null ? "0" : String.valueOf(bankLimit));
                totalBankLimitAmount = totalBankLimitAmount.add(bankLimitAmount);
                if (entityBankSetting.getAdhocExpiryDate() != null
                        && entityBankSetting.getAdhocExpiryDate().isAfter(now)
                        && entityBankSetting.getAdhocLimit() != null) {
                    Double adhocLimit = entityBankSetting.getAdhocLimit();
                    BigDecimal adhocLimitAmount = new BigDecimal(String.valueOf(adhocLimit));
                    totalBankLimitAmount = totalBankLimitAmount.add(adhocLimitAmount);
                }
            }
        }
        log.info("availableMaximumAmount,totalBankLimitAmount:{}", totalBankLimitAmount.toPlainString());
        // 查询outstanding
        BigDecimal totalOutstanding = new BigDecimal("0");
        BigDecimal debitAmount = invoiceSubmitRecordMapper.selectAmountByType(bankId, InvoiceConstants.INVOICE_TYPE_DEBIT_STR, entityIds);
        BigDecimal creditAmount = invoiceSubmitRecordMapper.selectAmountByType(bankId, InvoiceConstants.INVOICE_TYPE_CREDIT_STR, entityIds);
        debitAmount = debitAmount == null ? new BigDecimal("0") : debitAmount;
        creditAmount = creditAmount == null ? new BigDecimal("0") : creditAmount;
        totalOutstanding = totalOutstanding.add(debitAmount);
        totalOutstanding = totalOutstanding.subtract(creditAmount);

        totalBankLimitAmount = totalBankLimitAmount.subtract(totalOutstanding);
        log.info("availableMaximumAmount,totalBankLimitAmount:{}", totalBankLimitAmount.toPlainString());
        BigDecimal resultAmount = invoiceTotalAmount.compareTo(totalBankLimitAmount) < 0 ? invoiceTotalAmount : totalBankLimitAmount;
        resultAmount = resultAmount.compareTo(new BigDecimal("0")) < 0 ? new BigDecimal("0") : resultAmount;
        result.setAmount(resultAmount.toPlainString());

        log.info("availableMaximumAmount,result:{}", JsonUtil.toJSON(result));
        return result;
    }

    private AmountWrapper getAvailableAmount(Set<String> companyCodeSet, List<SupplierUniqueIdDo> supplierUniqueIdDos) {
        AmountWrapper result = new AmountWrapper();
//        BigDecimal debitAmount = financeInvoiceApMapper.availableDebitAmount(companyCodeSet, supplierUniqueIdDos);
//        BigDecimal creditAmount = financeInvoiceApMapper.availableCreditAmount(companyCodeSet, supplierUniqueIdDos);
//        debitAmount = debitAmount == null ? new BigDecimal("0") : debitAmount;
//        creditAmount = creditAmount == null ? new BigDecimal("0") : creditAmount;
//        result.setDebitAmount(debitAmount);
//        result.setCreditAmount(creditAmount);
//        return result;

        List<FinanceInvoiceApDo> debitList = financeInvoiceApMapper.availableDebitFinance(companyCodeSet, supplierUniqueIdDos);
        // 如果debitList为空,说明没有可用的发票,不用再查询credit
        if (CollectionUtils.isEmpty(debitList)) {
            log.info("getAvailableAmount,debitList is empty");
            result.setDebitAmount(new BigDecimal("0"));
            result.setCreditAmount(new BigDecimal("0"));
            log.info("getAvailableAmount,result:{}", JsonUtil.toJSON(result));
            return result;
        }
        List<FinanceInvoiceApDo> creditList = financeInvoiceApMapper.availableCreditFinance(companyCodeSet, supplierUniqueIdDos);
        // 如果creditList为空,说明没有credit可以用来抵扣debit的额度,直接查询debit的总额度并返回
        if (CollectionUtils.isEmpty(creditList)) {
            log.info("getAvailableAmount,creditList is empty");
            BigDecimal debitAmount = financeInvoiceApMapper.availableDebitAmount(companyCodeSet, supplierUniqueIdDos);
            result.setDebitAmount(debitAmount == null ? new BigDecimal("0") : debitAmount);
            result.setCreditAmount(new BigDecimal("0"));
            log.info("getAvailableAmount,result:{}", JsonUtil.toJSON(result));
            return result;
        }
        // debit和credit按companyCode分组
        Map<String, List<FinanceInvoiceApDo>> debitGroups = debitList.parallelStream().collect(Collectors.groupingBy(BaseInvoiceInfo::getCompanyCode));
        Map<String, List<FinanceInvoiceApDo>> creditGroups = creditList.parallelStream().collect(Collectors.groupingBy(BaseInvoiceInfo::getCompanyCode));
        BigDecimal debitTotalAmount = new BigDecimal("0");
        BigDecimal creditTotalAmount = new BigDecimal("0");
        for (Map.Entry<String, List<FinanceInvoiceApDo>> debitGroup : debitGroups.entrySet()) {
            List<FinanceInvoiceApDo> debits = debitGroup.getValue();
            BigDecimal debitGroupAmount = new BigDecimal("0");
            if (CollectionUtils.isNotEmpty(debits)) {
                for (FinanceInvoiceApDo debit : debits) {
                    Double amount = debit.getInvoiceAmount() == null ? 0.0 : debit.getInvoiceAmount();
                    debitGroupAmount = debitGroupAmount.add(new BigDecimal(String.valueOf(amount)));
                }
            }
            debitTotalAmount = debitTotalAmount.add(debitGroupAmount);
            BigDecimal creditGroupAmount = new BigDecimal("0");
            List<FinanceInvoiceApDo> credits = creditGroups.get(debitGroup.getKey());
            if (CollectionUtils.isNotEmpty(credits)) {
                for (FinanceInvoiceApDo credit : credits) {
                    Double amount = credit.getInvoiceAmount() == null ? 0.0 : credit.getInvoiceAmount();
                    creditGroupAmount = creditGroupAmount.add(new BigDecimal(String.valueOf(amount)));
                }
            }
            creditTotalAmount = creditTotalAmount.add(creditGroupAmount);
        }
        result.setDebitAmount(debitTotalAmount);
        result.setCreditAmount(creditTotalAmount);
        log.info("getAvailableAmount,result:{}", JsonUtil.toJSON(result));
        return result;
    }

    private void fillResult(MaxAmountInvoiceCardVo result, SupplierDo supplier) {
        result.setMinAmount(supplier.getMinimumNetFinancingAmount() == null ? null : String.valueOf(supplier.getMinimumNetFinancingAmount()));
        result.setFinancingModel(supplier.getFinancingModel());
        result.setTopUpPricing(supplier.getToUpPricing() == null ? null : String.valueOf(supplier.getToUpPricing()));
        result.setSupplierModel(supplier.getSupplierModel());
        result.setInvalidDaysBefore(supplier.getInvalidDaysBeforeMaturityDate() == null ? null : String.valueOf(supplier.getInvalidDaysBeforeMaturityDate()));
        result.setBasisOfMaturityDate(supplier.getBasisOfMaturityDateCalculation());
        result.setTraceBackDays(supplier.getTraceBackHistoryInvoiceDays() == null ? null : String.valueOf(supplier.getTraceBackHistoryInvoiceDays()));
    }

    private InvoiceVo convertToInvoiceVo(String entityName, BaseInvoiceInfo invoice) {
        InvoiceVo vo = new InvoiceVo();
        vo.setId(String.valueOf(invoice.getId()));
        vo.setAmount(invoice.getInvoiceAmount());
        vo.setCompanyCode(invoice.getCompanyCode());
        vo.setInvoiceNumber(invoice.getInvoiceReference());
        vo.setIssueDate(invoice.getInvoiceIssueDate());
        vo.setDueDate(invoice.getInvoiceDueDate());
        vo.setCurrency(invoice.getInvoiceCurrency());
        vo.setVendorCode(invoice.getVendorCode());
        vo.setEntityName(entityName);
        if (invoice.getMaturityDate() != null) {
            vo.setMaturityDate(DateUtils.parseDateToStr(DateUtils.DATE_PATTERN, localDateTimeToDate(invoice.getMaturityDate())));
        }
        if (invoice instanceof FinanceInvoiceApDo) {
            FinanceInvoiceApDo financeInvoiceApDo = (FinanceInvoiceApDo) invoice;
            if (financeInvoiceApDo.getConfirmedMaturityDate() != null) {
                vo.setConfirmedMaturityDate(DateUtils.dateFormat(localDateTimeToDate(financeInvoiceApDo.getConfirmedMaturityDate()), DateUtils.DATE_PATTERN));
            }
        }
        return vo;
    }

    private BigDecimal calculateRate(String bankId, SupplierDo supplier) {
        log.info("calculateRate,bankId:{},supplier:{}", bankId, JsonUtil.toJSON(supplier));
        // sofr值
        List<FinancingRateDo> rateDos = financingRateMapper.selectLatestRate(FinancingRateDo.RATE_TYPE_SOFR, FinancingRateDo.PERIOD_3M);
        if (CollectionUtils.isEmpty(rateDos)) {
            log.info("discountRate,rateDos is empty");
            throw throwEx(DefaultErrorMessage.SOFR_RATE_NOT_FOUND);
        }

        List<SupplierBankSettingDo> settings = supplierBankSettingMapper.selectBySupplierIdAndBankId(supplier.getId(), bankId);
        if (CollectionUtils.isEmpty(settings)) {
            log.info("discountRate,settings is empty");
            throw throwEx(DefaultErrorMessage.SUPPLIER_BANK_SETTING_IS_NULL);
        }

        Double topUpPricing = supplier.getToUpPricing();
        BigDecimal topUp = topUpPricing == null ? new BigDecimal("0") : new BigDecimal(String.valueOf(topUpPricing));
        BigDecimal sofrRate = rateDos.get(0).getRate();
        sofrRate = sofrRate == null ? new BigDecimal("0") : sofrRate;
        log.info("calculateRate,sofrRate:{}", sofrRate.toPlainString());
        String marginStr = settings.get(0).getMargin();
        BigDecimal margin = StringUtils.isEmpty(marginStr) ? new BigDecimal("0") : new BigDecimal(marginStr);
        log.info("calculateRate,margin:{}", margin.toPlainString());

        return topUp.add(sofrRate).add(margin);
    }


    /**
     * rate = sofr + topup + margin
     */
    @Override
    public String discountRate(Long userId, String bankId) {
        Long supplierId = getSupplierId(userId);
        SupplierDo supplierDo = getSupplierDo(supplierId);
        return calculateRate(bankId, supplierDo).toPlainString();
    }

    private SupplierDo getSupplierDo(Long supplierId) {
        SupplierDo supplierDo = supplierMapper.selectByPrimaryKey(supplierId);
        if (supplierDo == null) {
            log.info("getSupplierDo,supplier not found,supplierId:{}", supplierId);
            throw throwEx(DefaultErrorMessage.SUPPLIER_NOT_FOUND);
        }
        if (!SupplierDo.STATUS_ACTIVE.equals(supplierDo.getStatus())) {
            log.info("getSupplierDo,supplier status is wrong,status:{}", supplierDo.getStatus());
            throw throwEx(DefaultErrorMessage.STATUS_IS_WRONG);
        }
        return supplierDo;
    }

    /**
     * 提交发票页面的discount amount值
     */
    @Override
    public String discountAmount(ConfirmInvoiceListParam param) {
        Long userId = param.getUserId();
        String bankId = param.getBankId();
        Long supplierId = getSupplierId(userId);
        log.info("discountAmount,supplierId:{},param:{}", supplierId, JsonUtil.toJSON(param));
        SupplierDo supplier = getSupplierDo(supplierId);
        // discount rate
        BigDecimal rate = calculateRate(bankId, supplier);
        log.info("discountAmount,rate:{}", rate.toPlainString());
        List<SupplierBankSettingDo> settings = supplierBankSettingMapper.selectBySupplierIdAndBankId(supplierId, bankId);
        if (CollectionUtils.isEmpty(settings)) {
            log.info("discountAmount,settings is empty,supplierId:{},bankId:{}", supplierId, bankId);
            throw throwEx(DefaultErrorMessage.SUPPLIER_BANK_SETTING_IS_NULL);
        }
        Map<String, SupplierBankSettingDo> settingMap = settings.stream()
                .collect(Collectors.toMap(t -> bankSettingKey(t.getEntityId(), t.getBankId()), t -> t, (k1, k2) -> k2));
        SupplierBankSettingDo setting = settings.get(0);
        // tenor = maturity date - submit date - days from posting date
        // 用户手动选择的id
        List<Long> userChecked = param.getChecked();
        // 用户手动取消选择的id
        List<Long> userUnchecked = param.getUnchecked();
        List<InvoiceVo> tempCheckResult = new ArrayList<>();

//        String dataCheckedKey = RedisKeyUtil.dataCheckedKey(supplierId);
        // 默认选中的发票
//        RList<InvoiceVo> checked = redissonClient.getList(dataCheckedKey);
        long start = System.currentTimeMillis();
        List<InvoiceVo> checked = getDataChecked(supplierId);
        long end = System.currentTimeMillis();
        log.info("discountAmount getDataChecked cost time:{}", (end - start));
        Set<String> companyCodes = new HashSet<>();
        // 0.2,处理取消选中
        if (CollectionUtils.isNotEmpty(checked)) {
            for (InvoiceVo invoiceVo : checked) {
                companyCodes.add(invoiceVo.getCompanyCode());
                if (userUnchecked.contains(Long.valueOf(invoiceVo.getId()))) {
                    continue;
                }
                invoiceVo.setBankId(param.getBankId());
                tempCheckResult.add(invoiceVo);
            }
        }
        if (CollectionUtils.isEmpty(tempCheckResult)
                && CollectionUtils.isEmpty(userChecked)) {
            log.info("discountAmount,tempCheckResult and userChecked are empty,userChecked:{},userUnchecked:{},", JsonUtil.toJSON(userChecked), JsonUtil.toJSON(userUnchecked));
            throw throwEx(DefaultErrorMessage.INVOICE_LIST_IS_NULL);
        }

        log.info("discountAmount,userChecked:{}", JsonUtil.toJSON(userChecked));
        // 添加用户手动选择的发票
        if (CollectionUtils.isNotEmpty(userChecked)) {
            List<FinanceInvoiceApDo> financeInvoiceApDos = financeInvoiceApMapper.selectInvoicesByIds(userChecked);
            for (FinanceInvoiceApDo financeInvoiceApDo : financeInvoiceApDos) {
                tempCheckResult.add(convertToInvoiceVo(null, financeInvoiceApDo));
                companyCodes.add(financeInvoiceApDo.getCompanyCode());
            }
        }

        log.info("discountAmount,companyCodes:{},tempCheckResult:{}", companyCodes, JsonUtil.toJSON(tempCheckResult));
        List<SupplierUniqueIdDo> supplierUniqueIdDos = selectUniqueIdBySupplierId(supplierId);
        log.info("discountAmount,supplierUniqueIdDos:{}", JsonUtil.toJSON(supplierUniqueIdDos));
        BigDecimal creditAmount = financeInvoiceApMapper.availableCreditAmount(companyCodes, supplierUniqueIdDos);
//        BigDecimal creditAmount = new BigDecimal(String.valueOf(creditAmountNum == null ? "0" : creditAmountNum));
        creditAmount = creditAmount == null ? new BigDecimal("0") : creditAmount;
        log.info("discountAmount,creditAmount:{}", creditAmount);
        Map<String, List<InvoiceVo>> maturityDateGroups = tempCheckResult.parallelStream()
                .collect(Collectors.groupingBy(this::maturityGroupKey));
        log.info("discountAMount,maturityDateGroups:{}", JsonUtil.toJSON(maturityDateGroups));
        SimpleDateFormat maturityDateFormat = new SimpleDateFormat(DateUtils.DATE_PATTERN);
        // 标识creditAmount是否已经被减掉
        boolean used = false;
        BigDecimal totalDiscountAmount = new BigDecimal("0");
        Date baseTime = DateUtils.getNowDate();
        for (Map.Entry<String, List<InvoiceVo>> entry : maturityDateGroups.entrySet()) {
            List<InvoiceVo> value = entry.getValue();
            if (CollectionUtils.isEmpty(value)) {
                continue;
            }
            double maturityAmountNum = value.stream().mapToDouble(InvoiceVo::getAmount).sum();
            BigDecimal maturityAmount = new BigDecimal(String.valueOf(maturityAmountNum));
            if (maturityAmount.compareTo(creditAmount) > 0 && !used) {
                maturityAmount = maturityAmount.subtract(creditAmount);
                used = true;
            }
            int interval;
            try {
                int postDays = setting.getDaysFromPostingDate() == null ? 0 : setting.getDaysFromPostingDate();
                interval = DateUtils.dateBetween(baseTime, maturityDateFormat.parse(entry.getKey())) - postDays;
                log.info("discountAmount,interval:{},postDays:{}", interval, postDays);
            } catch (ParseException e) {
                log.error("discountAmount,parse time error,time:{}", entry.getKey());
                throw throwEx(DefaultErrorMessage.SERVER_ERROR);
            }
            maturityAmount = maturityAmount.multiply(rate)
                    .multiply(new BigDecimal(interval))
                    .divide(new BigDecimal(DISCOUNT_AMOUNT_DENOMINATOR), 3, RoundingMode.HALF_UP)
                    .divide(new BigDecimal(100), 3, RoundingMode.HALF_UP);
            totalDiscountAmount = totalDiscountAmount.add(maturityAmount);
            log.info("discountAmount,maturityDate:{},maturityAmount:{},interval:{},totalDiscountAmount:{}", entry.getKey(), maturityAmount, interval, totalDiscountAmount);
        }
        if (!used) {
            log.info("discountAmount,credit amount:{}", creditAmount);
            throw throwEx(DefaultErrorMessage.NO_MATCHED_INVOICE_BATCH);
        }
        return totalDiscountAmount.toPlainString();
    }

    private String maturityGroupKey(InvoiceVo invoice) {
        return StringUtils.isEmpty(invoice.getConfirmedMaturityDate()) ? invoice.getMaturityDate() : invoice.getConfirmedMaturityDate();
    }

    @Override
    public void processExpiredRecords(String updateBy) {
        // 过期的记录id
        List<Long> recordIds = invoiceSubmitRecordMapper.selectExpiredRecordIds(DateUtils.getNowDate(), QUERY_SIZE);
        log.info("processExpiredRecords,recordIds:{}", recordIds);
        if (CollectionUtils.isEmpty(recordIds)) {
            log.info("processExpiredRecords,recordIds is empty");
            return;
        }
        int updateRows = invoiceSubmitRecordMapper.updateExpireRecords(recordIds, updateBy, LocalDateTime.now());
        log.info("processExpiredRecords,update success,rows:{}", updateRows);
    }

    @Override
    public List<MaturityGroupVo> maturityDateGroup(MaturityDateGroupParam param) {
        Long supplierId = getSupplierId(param.getUserId());
        StopWatch watch = new StopWatch("maturityDateGroup");
        watch.start("processUserCheck");
        List<Long> checkedIds = processUserCheck(supplierId, param.getUnchecked(), param.getChecked());
        watch.stop();
//        log.info("maturityDateGroup,checkedIds:{}", checkedIds);
        if (CollectionUtils.isEmpty(checkedIds)) {
            log.info("maturityDateGroup,checkedIds is empty");
            return new ArrayList<>();
        }

        watch.start("get invoices");
        long start = System.currentTimeMillis();
        List<FinanceInvoiceApDo> invoices = new ArrayList<>();
        List<List<Long>> lists = splitList(checkedIds, UPDATE_BATCH_ID_SIZE);
        for (List<Long> list : lists) {
            Example condition = new Example(FinanceInvoiceApDo.class);
            condition.createCriteria().andIn("id", list);
            List<FinanceInvoiceApDo> tempList = financeInvoiceApMapper.selectByExample(condition);
            invoices.addAll(tempList);
        }
        long end = System.currentTimeMillis();
        log.info("maturityDateGroup, cost time:{}", (end - start));
        watch.stop();

        if (CollectionUtils.isEmpty(invoices)) {
            log.info("maturityDateGroup,financeInvoiceApDos is empty");
            return new ArrayList<>();
        }

        watch.start("get bankSettings");
        List<SupplierBankSettingDo> supplierBankSettingDos = supplierBankSettingMapper.selectBySupplierIdAndBankId(supplierId, param.getBankId());
        if (CollectionUtils.isEmpty(supplierBankSettingDos)) {
            throw throwEx(DefaultErrorMessage.SUPPLIER_BANK_SETTING_IS_NULL);
        }
        log.info("maturityDateGroup,supplierBankSettingDos:{}", JsonUtil.toJSON(supplierBankSettingDos));
        List<MaturityGroupVo> result = new ArrayList<>();
        // map,key:entityId_bankId,value:days
        Map<String, Integer> daysMap = supplierBankSettingDos.stream()
                .collect(Collectors.toMap(t -> bankSettingKey(t.getEntityId(), t.getBankId()), SupplierBankSettingDo::getDaysFromPostingDate));
        log.info("maturityDateGroup,daysMap:{}", JsonUtil.toJSON(daysMap));
        Set<String> companyCodeSet = invoices.parallelStream().map(BaseInvoiceInfo::getCompanyCode).collect(Collectors.toSet());
        EntityCodeIdDto entityCodeIdDto = entityCodeIdMap(companyCodeSet);
        log.info("maturityDateGroup,companyCodeSet:{},entityCodeIdDto:{}", companyCodeSet, JsonUtil.toJSON(entityCodeIdDto));
        if (entityCodeIdDto == null) {
            throw throwEx(DefaultErrorMessage.ENTITY_COMPANY_CODE_IS_NULL);
        }
        watch.stop();
        watch.start("get entityMaturityGroup");
        Map<String, List<FinanceInvoiceApDo>> entityMaturityGroup = invoices.parallelStream()
                .collect(Collectors.groupingBy(t -> groupKey(t.getCompanyCode(), t.getMaturityDate(), entityCodeIdDto)));
        watch.stop();
        log.info("maturityDateGroup,entityMaturityGroup:{}", JsonUtil.toJSON(entityMaturityGroup));
        watch.start("get result");
        for (Map.Entry<String, List<FinanceInvoiceApDo>> entry : entityMaturityGroup.entrySet()) {
            String key = entry.getKey();
            String[] keys = key.split(StringConstants.COMPLEX_SEPARATOR);
            String entityIdStr = keys[1];
            Integer day = daysMap.get(bankSettingKey(Long.valueOf(entityIdStr), param.getBankId()));
            MaturityGroupVo item = new MaturityGroupVo(keys[0], keys[2], day);
            result.add(item);
        }
        watch.stop();
        log.info("maturityDateGroup, execute cost time:\n{}", watch.prettyPrint());
        log.info("maturityDateGroup, result:{}", JsonUtil.toJSON(result));
        return result;
    }

    /**
     * key：entityName + entityId + maturityDate
     */
    private String groupKey(String companyCode, LocalDateTime maturityDate, EntityCodeIdDto entityCodeIdDto) {
        if (maturityDate == null) {
            log.info("groupKey,maturityDate is null");
            throw throwEx(DefaultErrorMessage.ILLEGAL_MATURITY_DATE);
        }
        Map<String, String> codeNameMap = entityCodeIdDto.getCodeNameMap();
        String entityName = codeNameMap.get(companyCode);
        Map<String, Long> codeIdMap = entityCodeIdDto.getCodeIdMap();
        Long entityId = codeIdMap.get(companyCode);
        return entityName + StringConstants.COMPLEX_SEPARATOR + entityId + StringConstants.COMPLEX_SEPARATOR + DateUtils.dateFormat(localDateTimeToDate(maturityDate), DateUtils.DATE_PATTERN);
    }

    @Override
    public boolean updateMaturityDate(UpdateMaturityParam param) {
        if (CollectionUtils.isEmpty(param.getDates())) {
            log.info("updateMaturityDate,param.dates is empty");
            throw throwEx(DefaultErrorMessage.ILLEGAL_MATURITY_DATE);
        }
        Long supplierId = getSupplierId(param.getUserId());
        SupplierDo supplierDo = getSupplierDo(supplierId);
        long processStart = System.currentTimeMillis();
        log.info("updateMaturityDate,supplierId:{},param:{}", supplierId, JsonUtil.toJSON(param));
        List<Long> checkedIds = processUserCheck(supplierId, param.getUnchecked(), param.getChecked());
//        log.info("updateMaturityDate,checkedIds:{}", checkedIds);
        long processEnd = System.currentTimeMillis();
        log.info("updateMaturityDate, processUserCheck cost time:{}", (processEnd - processStart));
        List<UpdateMaturityParam.ParamItem> dates = param.getDates();
        LocalDateTime updateTime = LocalDateTime.now();
        // list拆分，防止sql参数过长
        List<List<Long>> lists = splitList(checkedIds, UPDATE_BATCH_ID_SIZE);
        long start = System.currentTimeMillis();
        for (UpdateMaturityParam.ParamItem item : dates) {
            for (List<Long> list : lists) {
                financeInvoiceApMapper.updateConfirmedMaturityDateByIds(list, item.getMaturityDate(), item.getConfirmedDate(), updateTime, supplierDo.getSupplierName());
//                financeInvoiceApMapper.updateConfirmedMaturityDateByIds(checkedIds, item.getMaturityDate(), item.getConfirmedDate(), updateTime, supplierDo.getSupplierName());
            }
        }
        long end = System.currentTimeMillis();
        log.info("updateMaturityDate,update cost time:{}", (end - start));
        return true;
    }

    @Data
    private static class AmountWrapper {
        private BigDecimal debitAmount;
        private BigDecimal creditAmount;
    }

    @Data
    private static class DebitCompanyBatchAmountWrapper {
        private FinanceBatchDo batch;
        private List<InvoiceSubmitRecord> records;
        private List<FinanceBatchInvoiceDo> invoiceList;
        private String batchNumber;
        private BigDecimal amount;
    }

    @Data
    private static class CreditCompanyAmountWrapper {
        private BigDecimal amount;
        private List<InvoiceDTO> credits;
    }
}
