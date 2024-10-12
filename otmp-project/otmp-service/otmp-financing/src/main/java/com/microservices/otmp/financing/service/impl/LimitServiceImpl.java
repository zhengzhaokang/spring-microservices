package com.microservices.otmp.financing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.constant.StateConstants;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.financing.constant.InvoiceConstants;
import com.microservices.otmp.financing.constant.StringConstants;
import com.microservices.otmp.financing.domain.entity.*;
import com.microservices.otmp.financing.domain.param.limit.EditLimitParam;
import com.microservices.otmp.financing.domain.param.limit.LimitListParam;
import com.microservices.otmp.financing.domain.vo.limit.LimitVo;
import com.microservices.otmp.financing.mapper.EntityBankSettingMapper;
import com.microservices.otmp.financing.mapper.InvoiceSubmitRecordMapper;
import com.microservices.otmp.financing.mapper.LimitStatisticMapper;
import com.microservices.otmp.financing.mapper.SupplierBankSettingMapper;
import com.microservices.otmp.financing.service.LimitService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LimitServiceImpl implements LimitService {

    @Autowired
    private EntityBankSettingMapper entityBankSettingMapper;
    @Autowired
    private SupplierBankSettingMapper supplierBankSettingMapper;
    @Autowired
    private InvoiceSubmitRecordMapper invoiceSubmitRecordMapper;
    @Autowired
    private LimitStatisticMapper limitStatisticMapper;

    private String getRecordKey(String bankId, Long entityId) {
        return bankId + StringConstants.UNDER_LINE + entityId;
    }

    /**
     * limit列表
     */
    @Override
    public PageInfo<LimitVo> list(LimitListParam param) {
        log.info("list,param:{}", JsonUtil.toJSON(param));
        int pageNum = (param.getPageNum() == null || param.getPageNum() <= 0) ? Constants.DEFAULT_PAGE_SIZE : param.getPageNum();
        int pageSize = (param.getPageSize() == null || param.getPageSize() <= 0) ? Constants.DEFAULT_PAGE_SIZE : param.getPageSize();
        PageInfo<LimitVo> result = new PageInfo<>();
        PageHelper.startPage(pageNum, pageSize);
        List<EntityBankNameGroupDo> entityBankNameGroupDos = entityBankSettingMapper.entityBankNameGroup();
        PageInfo<EntityBankNameGroupDo> pageInfo = new PageInfo<>(entityBankNameGroupDos);
        result.setTotal(pageInfo.getTotal());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        log.info("list,entityBankNameGroupDos:{}", JsonUtil.toJSON(entityBankNameGroupDos));
        if (CollectionUtils.isEmpty(entityBankNameGroupDos)) {
            result.setList(new ArrayList<>());
            return result;
        }
        // 银行id列表
        List<String> bankIds = entityBankNameGroupDos.parallelStream()
                .map(EntityBankNameGroupDo::getBankId)
                .collect(Collectors.toList());
        // 实体id列表
        Set<Long> entityIds = entityBankNameGroupDos.parallelStream()
                .map(EntityBankNameGroupDo::getEntityId)
                .collect(Collectors.toSet());
        log.info("list,bankIds:{},entityIds:{}", JsonUtil.toJSON(bankIds), JsonUtil.toJSON(entityIds));

        // 查询onboard supplier
        List<OnBoardingSupplierByBankDo> onBoardingSupplierByBankDos = supplierBankSettingMapper.onboardingByBank(bankIds);
        Map<String, List<OnBoardingSupplierByBankDo>> bankIdGroupMap = onBoardingSupplierByBankDos.parallelStream()
                .collect(Collectors.groupingBy(o -> o.getBankId() + StringConstants.UNDER_LINE + o.getEntityId()));
        log.info("list,bankIdGroupMap:{}", JsonUtil.toJSON(bankIdGroupMap));

        List<InvoiceGroupAmount> groupAmounts = invoiceSubmitRecordMapper.selectRecordGroupsByIds(bankIds, entityIds);
        log.info("list,groupAmounts:{}", JsonUtil.toJSON(groupAmounts));
        Map<String, List<InvoiceGroupAmount>> groupAmountMap = groupAmounts.stream().collect(Collectors.groupingBy(this::amountGroupKey));
        log.info("list,groupAmountMap:{}", JsonUtil.toJSON(groupAmountMap));
        List<LimitVo> resultList = new ArrayList<>();
        for (EntityBankNameGroupDo item : entityBankNameGroupDos) {
            List<OnBoardingSupplierByBankDo> suppliers = bankIdGroupMap.get(item.getBankId() + StringConstants.UNDER_LINE + item.getEntityId());
            LimitVo limitVo = convertToLimitVo(item);
            BigDecimal outstandingAmount = new BigDecimal("0");
            if (CollectionUtils.isNotEmpty(suppliers)) {
                List<LimitVo.SupplierSimpleInfo> infoList = new ArrayList<>();
                List<LimitVo.OutStanding> supplierOutStandings = new ArrayList<>();
                for (OnBoardingSupplierByBankDo sup : suppliers) {
                    LimitVo.SupplierSimpleInfo info = new LimitVo.SupplierSimpleInfo();
                    info.setSupplierId(String.valueOf(sup.getSupplierId()));
                    info.setSupplierName(sup.getSupplierName());
                    if (sup.getActivationDate() != null) {
                        info.setActivationDate(DateUtils.parseDateToStr(DateUtils.DATE_PATTERN, localDateTimeToDate(sup.getActivationDate())));
                    }
                    infoList.add(info);

                    List<InvoiceGroupAmount> supplierBatches = groupAmountMap.get(amountGroupKey(item.getBankId(), item.getEntityId(), sup.getSupplierId()));
                    if (CollectionUtils.isNotEmpty(supplierBatches)) {
                        Map<String, List<InvoiceGroupAmount>> batchMap = supplierBatches.stream().collect(Collectors.groupingBy(InvoiceGroupAmount::getBatchNumber));
                        for (Map.Entry<String, List<InvoiceGroupAmount>> entry : batchMap.entrySet()) {
                            List<InvoiceGroupAmount> batchList = entry.getValue();
                            if (CollectionUtils.isEmpty(batchList)) {
                                continue;
                            }
                            InvoiceGroupAmount batchFirst = batchList.get(0);
                            LimitVo.OutStanding outStanding = new LimitVo.OutStanding();
                            outStanding.setSupplierName(batchFirst.getSupplierName());
                            outStanding.setBatchNumber(batchFirst.getBatchNumber());
                            outStanding.setSupplierId(String.valueOf(batchFirst.getSupplierId()));
                            outStanding.setDiscountDate(DateUtils.dateFormat(batchFirst.getMaturityDate(), DateUtils.DATE_PATTERN));
                            BigDecimal batchTotalAmount = new BigDecimal("0");
                            for (InvoiceGroupAmount groupAmount : batchList) {
                                BigDecimal batchAmount = groupAmount.getAmount() == null ? new BigDecimal("0") : groupAmount.getAmount();
                                if (InvoiceConstants.INVOICE_TYPE_DEBIT_STR.equals(groupAmount.getType())) {
                                    batchTotalAmount = batchTotalAmount.add(batchAmount);
                                } else {
                                    batchTotalAmount = batchTotalAmount.subtract(batchAmount);
                                }
                            }
                            outStanding.setFinancingAmount(batchTotalAmount.toPlainString());
                            supplierOutStandings.add(outStanding);
                            outstandingAmount = outstandingAmount.add(batchTotalAmount);
                        }
                    }
                    limitVo.setOutStandings(supplierOutStandings);
                }
                limitVo.setSuppliers(infoList);
            }
            BigDecimal available = calAvailable(item.getBankLimit(), outstandingAmount, item.getAdhocLimit(), item.getAdhocExpiryDate());
            limitVo.setAvailableAmount(available.toPlainString());
            limitVo.setOutstandingAmount(outstandingAmount.toPlainString());
            resultList.add(limitVo);
        }
        // 排序
        resultList.sort((o1, o2) -> {
            if (o1 == null || o2 == null) {
                return 0;
            }
            return Long.valueOf(o2.getId()).compareTo(Long.valueOf(o1.getId()));
        });
        result.setList(resultList);
        log.info("list,result:{}", JsonUtil.toJSON(result));
        return result;
    }

    private String amountGroupKey(InvoiceGroupAmount amount) {
        return amountGroupKey(amount.getBankId(), amount.getEntityId(), amount.getSupplierId());
    }

    private String amountGroupKey(String bankId, Long entityId, Long supplierId) {
        return bankId + StringConstants.UNDER_LINE
                + entityId + StringConstants.UNDER_LINE
                + supplierId + StringConstants.UNDER_LINE;
    }

    private LimitVo convertToLimitVo(EntityBankNameGroupDo entity) {
        LimitVo result = new LimitVo();
        result.setId(String.valueOf(entity.getId()));
        result.setBankName(entity.getBankName());
        result.setEntities(entity.getEntityName());
        String bankLimitStr = entity.getBankLimit() == null ? "0" : entity.getBankLimit().toPlainString();
        result.setBankLimit(bankLimitStr);
        String adhocAmountStr = entity.getAdhocLimit() == null ? "0" : entity.getAdhocLimit().toPlainString();
        result.setAdhocLimit(adhocAmountStr);
        if (entity.getAdhocExpiryDate() != null) {
            result.setAdhocExpireDate(DateUtils.parseDateToStr(DateUtils.DATE_PATTERN, localDateTimeToDate(entity.getAdhocExpiryDate())));
        }
        return result;
    }

    private BigDecimal calAvailable(BigDecimal bankLimit, BigDecimal outstanding, BigDecimal adhoc, LocalDateTime adhocExpiryDate) {
        bankLimit = bankLimit == null ? new BigDecimal("0") : bankLimit;
        outstanding = outstanding == null ? new BigDecimal("0") : outstanding;
        BigDecimal available = bankLimit.subtract(outstanding);
        if (adhocExpiryDate != null && adhocExpiryDate.isAfter(LocalDateTime.now())) {
            adhoc = adhoc == null ? new BigDecimal("0") : adhoc;
            available = available.add(adhoc);
        }
//        log.info("calAvailable,bankLimit:{},outstanding:{}", bankLimit.toPlainString(), outstanding.toPlainString());
        return available;
    }

    @Override
    public Boolean edit(EditLimitParam param) {
        log.info("edit,param:{}", JsonUtil.toJSON(param));
        Example existCondition = new Example(EntityBankSettingDo.class);
        existCondition.createCriteria()
                .andEqualTo("id", param.getId())
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        EntityBankSettingDo entityBankSettingDo = entityBankSettingMapper.selectOneByExample(existCondition);
        log.info("edit,entityBankSettingDo:{}", JsonUtil.toJSON(entityBankSettingDo));
        if (entityBankSettingDo == null) {
            throw throwEx(DefaultErrorMessage.ENTITY_BANK_SETTING_NOT_FOUND);
        }
        entityBankSettingDo.setBankLimit(param.getLimitAmount() == null ? null : Double.valueOf(param.getLimitAmount()));
        entityBankSettingDo.setAdhocLimit(StringUtils.isEmpty(param.getAdhocLimitAmount()) ? 0.0 : Double.parseDouble(param.getAdhocLimitAmount()));
        entityBankSettingDo.setAdhocExpiryDate(StringUtils.isEmpty(param.getAdhocExpireDate()) ? null : dateToLocalDateTime(DateUtils.parseDate(param.getAdhocExpireDate())));
        entityBankSettingDo.setUpdateTime(LocalDateTime.now());
        entityBankSettingDo.setUpdateBy(param.getUpdateBy());
//        if (entityBankSettingDo.getAvailableLimit() == null) {
//            entityBankSettingDo.setAvailableLimit(entityBankSettingDo.getBankLimit());
//        }
        log.info("edit,entityBankSettingDo for update:{}", JsonUtil.toJSON(entityBankSettingDo));
        entityBankSettingMapper.updateByPrimaryKey(entityBankSettingDo);
        return true;
    }

    private LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void statisticLimitDaily(Date calDate) {
        Date now = calDate == null ? DateUtils.getNowDate() : calDate;
        log.info("statisticLimitDaily,now:{}", DateUtils.dateFormat(now, DateUtils.DATE_PATTERN));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date startTime = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date endTime = calendar.getTime();
//        calendar.add(Calendar.DAY_OF_MONTH, -2);
//        Date lastDate = calendar.getTime();
        String statisticDate = DateUtils.parseDateToStr(DateUtils.DATE_PATTERN, startTime);
        log.info("statisticLimitDaily,statisticDate:{}", statisticDate);
        limitStatisticMapper.deleteRecords(statisticDate);

        List<LimitStatisticDo> limits = new ArrayList<>();
        // 查询银行的额度
        List<EntityBankSettingDo> entityBankSettings = entityBankSettingMapper.selectAllActive();
        Set<String> bankIds = new HashSet<>();
        Set<Long> entityIds = new HashSet<>();
        if (CollectionUtils.isNotEmpty(entityBankSettings)) {
            for (EntityBankSettingDo entityBankSetting : entityBankSettings) {
                bankIds.add(entityBankSetting.getBankId());
                entityIds.add(entityBankSetting.getEntityId());
                LimitStatisticDo limit = createLimit(entityBankSetting.getBankId()
                        , entityBankSetting.getEntityId(), now, statisticDate, LimitStatisticDo.TYPE_BANK_LIMIT);
                calBankLimit(limit, entityBankSetting, now);
                limits.add(limit);
            }
        }
        log.info("statisticLimitDaily,startTime:{},endTime:{},bankIds:{},entityIds:{}", DateUtils.dateFormat(startTime, DateUtils.DATE_PATTERN)
                , DateUtils.dateFormat(endTime, DateUtils.DATE_PATTERN), bankIds, entityIds);
        // 查询outstanding
        Example outstandingCondition = new Example(InvoiceSubmitRecord.class);
        outstandingCondition.createCriteria().andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR)
                .andGreaterThanOrEqualTo("createTime", startTime)
                .andLessThan("createTime", endTime)
                .andIn("entityId", entityIds)
                .andIn("bankId", bankIds);
        List<InvoiceSubmitRecord> invoiceSubmitRecords = invoiceSubmitRecordMapper.selectByExample(outstandingCondition);
        Map<String, List<InvoiceSubmitRecord>> recordGroups = new HashMap<>();
        if (CollectionUtils.isNotEmpty(invoiceSubmitRecords)) {
            recordGroups = invoiceSubmitRecords.parallelStream().collect(Collectors.groupingBy(this::recordGroupKey));
        }
        log.info("statisticLimitDaily,recordGroups:{}", JsonUtil.toJSON(recordGroups));
        for (String bankId : bankIds) {
            for (Long entityId : entityIds) {
                LimitStatisticDo outstandingLimit = createLimit(bankId, entityId, now, statisticDate, LimitStatisticDo.TYPE_OUTSTANDING);
                outstandingLimit.setAmount("0");
                String groupKey = recordGroupKey(bankId, entityId);
                List<InvoiceSubmitRecord> records = recordGroups.get(groupKey);
                if (CollectionUtils.isNotEmpty(records)) {
                    BigDecimal groupAmount = getGroupAmount(records);
                    outstandingLimit.setAmount(groupAmount.toPlainString());
                }
                limits.add(outstandingLimit);
            }
        }
        log.info("statisticLimitDaily,limits:{}", JsonUtil.toJSON(limits));
        String lastDateStr = limitStatisticMapper.selectLastStatisticDate();
//        String lastDateStr = DateUtils.dateFormat(lastDate, DateUtils.DATE_PATTERN);
        List<LimitStatisticDo> lastDateLimits = limitStatisticMapper.selectLastDate(lastDateStr);
        boolean check = check(limits, lastDateLimits);
        log.info("statisticLimitDaily,check:{}", check);
        if (!check) {
            log.info("statisticLimitDaily,check result is false,return");
            return;
        }
        limitStatisticMapper.insertWithId(limits);
        log.info("statisticLimitDaily execute success");
    }

    /**
     * 检查当天的统计数据与之前最近一天的数据是否一致，不一致返回true（需要写入数据）
     */
    private boolean check(List<LimitStatisticDo> limits, List<LimitStatisticDo> lastDateLimits) {
        log.info("check,limits:{},lastDateLimits:{}", JsonUtil.toJSON(limits), JsonUtil.toJSON(lastDateLimits));
        if (CollectionUtils.isEmpty(lastDateLimits) || CollectionUtils.isEmpty(limits)) {
            return true;
        }
        Set<String> limitKeySet = limits.parallelStream()
                .map(t -> limitKey(t.getBankId(), t.getEntityId(), t.getType(), t.getAmount()))
                .collect(Collectors.toSet());
        Set<String> lastKeySet = lastDateLimits
                .parallelStream().map(t -> limitKey(t.getBankId(), t.getEntityId(), t.getType(), t.getAmount()))
                .collect(Collectors.toSet());
        log.info("check,limitKeySet:{},lastKeySet:{}", limitKeySet, lastKeySet);
        Set<String> commonKey = new HashSet<>(limitKeySet);
        // 取limitKeySet和lastKeySet的交集
        commonKey.retainAll(lastKeySet);
        // 把交集从limitKeySet和lastKeySet中移除
        limitKeySet.removeAll(commonKey);
        lastKeySet.removeAll(commonKey);
        log.info("check,commonKey:{},\nlimitKeySet after removeAll:{},\nlastKeySet after removeAll:{}", commonKey, limitKeySet, lastKeySet);
        // 任一不为空，说明与前一天的数据不一致，返回true
        return CollectionUtils.isNotEmpty(limitKeySet) || CollectionUtils.isNotEmpty(lastKeySet);
    }

    private String limitKey(String bankId, Long entityId, Integer type, String amount) {
        BigDecimal amountDecimal = new BigDecimal(amount == null ? "0" : amount);
        amount = amountDecimal.setScale(4, RoundingMode.HALF_UP).toPlainString();
        return bankId + StringConstants.UNDER_LINE
                + entityId + StringConstants.UNDER_LINE
                + type + StringConstants.UNDER_LINE
                + amount;
    }

    private static BigDecimal getGroupAmount(List<InvoiceSubmitRecord> records) {
        BigDecimal groupAmount = new BigDecimal("0");
        for (InvoiceSubmitRecord record : records) {
            BigDecimal recordAmount = new BigDecimal(record.getAmount() == null ? "0" : String.valueOf(record.getAmount()));
            if (InvoiceConstants.INVOICE_TYPE_DEBIT_STR.equals(record.getInvoiceType())) {
                groupAmount = groupAmount.add(recordAmount);
            } else {
                groupAmount = groupAmount.subtract(recordAmount);
            }
        }
        return groupAmount;
    }

    private String recordGroupKey(InvoiceSubmitRecord record) {
        return recordGroupKey(record.getBankId(), record.getEntityId());
    }

    private String recordGroupKey(String bankId, Long entityId) {
        return bankId + StringConstants.UNDER_LINE + entityId;
    }

    private LimitStatisticDo createLimit(String bankId, Long entityId, Date baseTime, String statisticDate, Integer type) {
        LimitStatisticDo limit = new LimitStatisticDo();
        limit.setId(SnowFlakeUtil.nextId());
        limit.setCreateBy("system");
        limit.setCreateTime(baseTime);
        limit.setBankId(bankId);
        limit.setEntityId(entityId);
        limit.setStatisticDate(statisticDate);
        limit.setType(type);
        return limit;
    }

    private void calBankLimit(LimitStatisticDo limit, EntityBankSettingDo setting, Date baseTime) {
        BigDecimal bankLimit = new BigDecimal(setting.getBankLimit() == null ? "0" : String.valueOf(setting.getBankLimit()));
        Double adhoc = setting.getAdhocLimit();
        if (adhoc != null
                && setting.getAdhocExpiryDate() != null
                && baseTime.before(localDateTimeToDate(setting.getAdhocExpiryDate()))) {
            bankLimit = bankLimit.add(new BigDecimal(String.valueOf(adhoc)));
        }
        limit.setAmount(bankLimit.toPlainString());
    }

    private OtmpException throwEx(Long errCode) {
        return new OtmpException(DefaultErrorMessage.getErrorMessage(errCode), errCode.intValue());
    }
}
