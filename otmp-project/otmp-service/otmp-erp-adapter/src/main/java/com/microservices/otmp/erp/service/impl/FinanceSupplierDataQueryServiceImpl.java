package com.microservices.otmp.erp.service.impl;

import com.google.common.collect.Lists;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.enums.InvoiceStatusEnum;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.*;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.domain.InvoiceAp;
import com.microservices.otmp.erp.domain.*;
import com.microservices.otmp.erp.mapper.InvoiceMaturityAmountInfoMapper;
import com.microservices.otmp.erp.mapper.SupplierInvoiceInfoMapper;
import com.microservices.otmp.erp.service.IFinanceSupplierDataQueryService;
import com.microservices.otmp.finance.*;
import lombok.extern.slf4j.Slf4j;
import nonapi.io.github.classgraph.utils.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FinanceSupplierDataQueryServiceImpl implements IFinanceSupplierDataQueryService {

    private static final String CREDIT_MEMO = "Credit Memo";
    private static final BigDecimal NEGATIVE_FACTOR = new BigDecimal("-1");
    private static final int INSERT_BATCH_SIZE = 5000;
    @Autowired
    private SupplierInvoiceInfoMapper supplierInvoiceInfoMapper;
    @Autowired
    private InvoiceMaturityAmountInfoMapper invoiceMaturityAmountInfoMapper;
    @Autowired
    private RedisUtils redis;

    @Override
    public List<SupplierInvoiceFeedbackInfoVo> invalidData(List<String> invoiceNo) {
        List<SupplierInvoiceFeedbackInfoVo> results = Lists.newArrayList();
        List<SupplierInvoiceInfo> invoiceData;
        String status;
        String statusDes;
        if(null == invoiceNo || invoiceNo.isEmpty()) {
            //查询数据库数据校验
            Date date = new Date();
            status = InvoiceStatusEnum.EXPIRED.code;
            statusDes = InvoiceStatusEnum.EXPIRED.code;
            invoiceData = supplierInvoiceInfoMapper.findExpiredData(date);
        }else{
            status = InvoiceStatusEnum.INVALID.code;
            statusDes = "Invoice Suspended by User Manually";
            invoiceData = supplierInvoiceInfoMapper.findInvoiceData(invoiceNo);
        }
        if(null != invoiceData && !invoiceData.isEmpty()) {
            invoiceData.stream().forEach(invoice -> {
                SupplierInvoiceFeedbackInfoVo back = new SupplierInvoiceFeedbackInfoVo();
                BeanUtils.copyProperties(invoice, back);
                back.setStatus(status);
                back.setMessage(status);
                back.setStatusDescription(statusDes);
                invoice.setStatus(status);
                invoice.setStatusDescription(statusDes);
                results.add(back);
            });
            computeInvoiceMaturityAmount(invoiceData, new BigDecimal(-1));
            supplierInvoiceInfoMapper.updateSupplierStatus(invoiceData);
            supplierInvoiceInfoMapper.updateExpiredDataStatus(invoiceData);
        }
        return results;
    }

    @Override
    public List<SupplierInvoiceFeedbackInfoVo> checkData(List<SupplierInvoiceInfo> suppliers) throws Exception {
        if (null == suppliers && suppliers.isEmpty()) {
            log.error("No Data");
            return null;
        }
        String supplierCode = suppliers.get(0).getVendorCode();
        List<PayCycleCalendarConfig> configs = supplierInvoiceInfoMapper.findPayCycleCalendarConfig();
        SupplierCalendarConfig sconfig = supplierInvoiceInfoMapper.findSupplierCalendarConfig(supplierCode);
        List<String> currency = supplierInvoiceInfoMapper.findCurrency(supplierCode);
        List<String> invoiceReferences = supplierInvoiceInfoMapper.findInvoiceReference(supplierCode);
        List<SupplierInvoiceInfo> finalSuppliers = Lists.newArrayList();
        List<SupplierInvoiceFeedbackInfoVo> results = Lists.newArrayList();
        if(configs.isEmpty() || null == sconfig){
            suppliers.stream().forEach(invoice -> {
                invoice.setStatus(InvoiceStatusEnum.INVALID.code);
                SupplierInvoiceFeedbackInfoVo back = new SupplierInvoiceFeedbackInfoVo();
                BeanUtils.copyProperties(invoice, back);
                back.setStatus(InvoiceStatusEnum.INVALID.code);
                back.setMessage("No Configuration");
                invoice.setStatusDescription("No Configuration");
                results.add(back);
            });
            supplierInvoiceInfoMapper.updateSupplierStatus(suppliers);
            return results;
        }
        suppliers.stream().forEach(supplier -> {
            SupplierInvoiceFeedbackInfoVo back = new SupplierInvoiceFeedbackInfoVo();
            BeanUtils.copyProperties(supplier, back);
            try {
                Date maturityDate = getMaturityDay(configs, supplier, sconfig);
                InvoiceAp invoice = buildInvoiceAp(supplier, maturityDate, invoiceReferences, currency);
                CheckResult result = checkInvoice(invoice);
                //final
                if (result.isPass()) {
                    supplier.setStatus(InvoiceStatusEnum.WAITING.code);
                    back.setStatus(InvoiceStatusEnum.WAITING.code);
                    supplier.setId(SnowFlakeUtil.nextId());
                    //amount abs
                    supplier.setInvoiceAmount(supplier.getInvoiceAmount().abs());
                    finalSuppliers.add(supplier);
                } else {
                    supplier.setStatus(InvoiceStatusEnum.INVALID.code);
                    supplier.setStatusDescription(result.getMsg());
                    back.setStatus(InvoiceStatusEnum.INVALID.code);
                    back.setMessage(result.getMsg());
                }
                result.setPassLists(Arrays.asList(back));
                results.add(back);
            }catch (Exception e){
                log.error("supplier check error：{}", JsonUtil.toJSON(supplier),e);
                supplier.setStatus(InvoiceStatusEnum.INVALID.code);
                back.setStatus(InvoiceStatusEnum.INVALID.code);
                back.setMessage("supplier check error:" + e.getMessage());
                supplier.setStatusDescription("supplier check error:" + e.getMessage());
                results.add(back);
            }
        });
        if(finalSuppliers.size() > 0) {
            supplierInvoiceInfoMapper.insertFinalBatch(finalSuppliers);
            computeInvoiceMaturityAmount(finalSuppliers, new BigDecimal(1));
        }
        supplierInvoiceInfoMapper.updateSupplierStatus(suppliers);
        return results;
    }

    @Override
    public List<SupplierInvoiceFeedbackInfoVo> updateSuppliers(List<SupplierInvoiceInfo> suppliers, String status,String statusDesc, Date updateTime) {
        List<SupplierInvoiceFeedbackInfoVo> results = Lists.newArrayList();
        suppliers.forEach(supplier ->{
            supplier.setStatus(status);
            supplier.setStatusDescription(statusDesc);
            supplier.setUpdateTime(updateTime);
            SupplierInvoiceFeedbackInfoVo back = new SupplierInvoiceFeedbackInfoVo();
            BeanUtils.copyProperties(supplier, back);
            if(CREDIT_MEMO.equalsIgnoreCase(supplier.getTypeOfInvoice())){
                back.setInvoiceAmount(supplier.getInvoiceAmount().multiply(NEGATIVE_FACTOR));
            }
            back.setMaturityDate(DateUtils.dateEnFormat(supplier.getMaturityDate(), DateUtils.DATE_PATTERN));
            results.add(back);
        });
        if(!StringUtils.equalsAnyIgnoreCase(status, InvoiceStatusEnum.FINANCED.code, InvoiceStatusEnum.REJECTED.code)) {
            computeInvoiceMaturityAmount(suppliers, new BigDecimal(-1));
        }

        // 按参数分组
        Collection<List<SupplierInvoiceInfo>> invoiceList = getInvoiceList(suppliers);
        log.info("updateSuppliers,invoiceList.size:{}",invoiceList.size());
        if(invoiceList.size()<20){
            log.info("updateSuppliers,invoiceList:{}",JsonUtil.toJSON(invoiceList));
        }
        if(!CollectionUtils.isEmpty(invoiceList)){
            long start = System.currentTimeMillis();
            for (List<SupplierInvoiceInfo> supplierInvoices : invoiceList) {
                SupplierInvoiceInfo first = supplierInvoices.get(0);
                // 再次分组，防止单个组下数据过多导致的sql参数越过限制
                List<List<SupplierInvoiceInfo>> subLists = splitList(supplierInvoices);
                for (List<SupplierInvoiceInfo> subList : subLists) {
                    supplierInvoiceInfoMapper.updateSupplierStatusBatch(first.getStatus()
                            ,first.getStatusDescription()
                            ,first.getMaturityDate()
                            ,updateTime
                            ,subList);
                    supplierInvoiceInfoMapper.updateFinalSupplierStatusBatch(first.getStatus()
                            ,first.getStatusDescription()
                            ,updateTime
                            ,subList);
                }
            }
            long end = System.currentTimeMillis();
            log.info("updateSuppliers,cost time:{}",(end - start));
        }
        return results;
    }

    private <T> List<List<T>> splitList(List<T> source) {
        List<List<T>> result = new ArrayList<>();
        int loop = (source.size() % INSERT_BATCH_SIZE == 0) ? (source.size() / INSERT_BATCH_SIZE) : (source.size() / 10) + 1;
        log.info("splitList,loop:{}", loop);
        for (int i = 0; i < loop; i++) {
            int start = i * INSERT_BATCH_SIZE;
            if (start > source.size()) {
                break;
            }
            int end = (i + 1) * INSERT_BATCH_SIZE;
            end = Math.min(end, source.size());
            List<T> temp = source.subList(start, end);
            result.add(temp);
        }
        log.info("splitList,result.size:{}", result.size());
        return result;
    }

    private Collection<List<SupplierInvoiceInfo>> getInvoiceList(List<SupplierInvoiceInfo> suppliers){
        Map<String, List<SupplierInvoiceInfo>> invoiceList = suppliers.parallelStream().collect(Collectors.groupingBy(this::groupKey));
        return invoiceList.values();
    }

    private String groupKey(SupplierInvoiceInfo info){
        return info.getStatus() + "_"
                + info.getStatusDescription() + "_"
                + DateUtils.dateFormat(info.getMaturityDate(),DateUtils.DATE_NO_PATTERN) ;
    }

    private CheckResult checkInvoice(InvoiceAp invoice) {
        //校验1，4，7，8,11,12
        CheckResult result = filter(new IssueDateFilter(), invoice);
        if(!result.isPass()){
            return result;
        }
        result = filter(new ReferenceFilter(), invoice);
        if(!result.isPass()){
            return result;
        }
        result = filter(new ReferenceStartFilter(), invoice);
        if(!result.isPass()){
            return result;
        }
        result = filter(new CreditOrDebitNoteFilter(), invoice);
        if(!result.isPass()){
            return result;
        }
        result = filter(new PayDateFilter(), invoice);
        if(!result.isPass()){
            return result;
        }
        result = filter(new CurrencyFilter(), invoice);
        if(!result.isPass()){
            return result;
        }
        result = filter(new DuplicationFilter(), invoice);
        if(!result.isPass()){
            return result;
        }
        return result;
    }

    private CheckResult filter(HygieneCheckFilter filter, InvoiceAp invoice) {
        return filter.filter(invoice);
    }

    private InvoiceAp buildInvoiceAp(SupplierInvoiceInfo supplier, Date maturityDate, List<String> invoiceReferences, List<String> currency) {
        InvoiceAp invoice = new InvoiceAp();
        supplier.setMaturityDate(maturityDate);
        BeanUtils.copyProperties(supplier, invoice);
        invoice.setInvoiceReferences(invoiceReferences);
        invoice.setInvoiceType(supplier.getTypeOfInvoice());
        invoice.setSupplierCurrency(Join.join(",",currency));
        return invoice;
    }

    private Date getMaturityDay(List<PayCycleCalendarConfig> configs, SupplierInvoiceInfo supplier,SupplierCalendarConfig sconfig) {
        Date maturityDate = null;
        String paymentcycl = sconfig.getPaymentCycle();
        String weekOfTheMonth = sconfig.getWeekOfTheMonth();
        String basisOfMaturityDateCalculation = sconfig.getBasisOfMaturityDateCalculation();
        try{
            Class clazz = supplier.getClass();
            String getMethodName = "get".concat((basisOfMaturityDateCalculation.substring(0, 1)).toUpperCase()).concat(basisOfMaturityDateCalculation.substring(1));
            // 通过方法名得到对应的方法获取值
            Object value = clazz.getMethod(getMethodName, new Class[] {}).invoke(supplier, new Object[] {});
            maturityDate = DateUtils.parseDate(value);
        }catch (Exception e){
            log.error("获取maturityDate异常：",e.getMessage(),e);
        }
        if(null != paymentcycl && !StringUtils.equalsIgnoreCase(paymentcycl, "0")) {
            int maturityDay = 0;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(maturityDate);
            calendar.add(Calendar.MONTH, Integer.parseInt(paymentcycl));
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            PayCycleCalendarConfig config = findConig(configs, String.valueOf(year), String.valueOf(month));
            switch (weekOfTheMonth) {
                case "1":
                    maturityDay = Integer.parseInt(config.getFirstWeek());
                    break;
                case "2":
                    maturityDay = Integer.parseInt(config.getSecondWeek());
                    break;
                case "3":
                    maturityDay = Integer.parseInt(config.getThirdWeek());
                    break;
                case "4":
                    maturityDay = Integer.parseInt(config.getFourthWeek());
                    break;
            }
            if (maturityDay == 0){
                return null;
            }
            calendar.set(Calendar.DAY_OF_MONTH, maturityDay);
            maturityDate = calendar.getTime();

        }
        return maturityDate;
    }

    private PayCycleCalendarConfig findConig(List<PayCycleCalendarConfig> configs, String year, String month) {
        List<PayCycleCalendarConfig> list = configs.stream()
                .filter(config ->StringUtils.equals(config.getZyear(), year)
                        && StringUtils.equals(config.getZmonth(), month))
                .collect(Collectors.toList());
        if(list.size() > 1){
            list = list.stream().filter(config -> StringUtils.isNotEmpty(config.getCountry())).collect(Collectors.toList());
        }
        return list.get(0);
    }

    @Override
    public void computeInvoiceMaturityAmount(List<SupplierInvoiceInfo> suppliers, BigDecimal variable){
        log.info("buildInvoiceMaturityAmountInfo:{}", variable);
        suppliers = suppliers.stream().filter(o -> !StringUtils.equalsIgnoreCase(o.getTypeOfInvoice(), CREDIT_MEMO)).collect(Collectors.toList());
        List<SupplierInvoiceInfo> sumSuppliers = suppliers.stream().collect(Collectors.toMap(supplier ->
            DateUtils.dateTime(supplier.getMaturityDate()), supplier -> supplier,
                (s1, s2) ->{
                    s1.setInvoiceAmount(s1.getInvoiceAmount().add(s2.getInvoiceAmount()));
                    return s1;
                }
        )).values().stream().collect(Collectors.toList());
        buildInvoiceMaturityAmountInfo(sumSuppliers, variable);
        log.info("buildInvoiceMaturityAmountInfo end");
    }

    @Override
    public List<SupplierInvoiceInfo> findSuppliersByIds(List<String> supplierIds) {
        return supplierInvoiceInfoMapper.findSuppliersByIds(supplierIds);
    }

    @Override
    public List<SupplierInvoiceFeedbackInfoVo> findSupplierInvoiceFeedbackData(List<String> batchNumbers, String status,String statusDescription) {
        long start = System.currentTimeMillis();
        //todo 优化sql
        List<SupplierInvoiceInfo> suppliers = supplierInvoiceInfoMapper.findSuppliersByBatchNumbers(batchNumbers);
        long end = System.currentTimeMillis();
        log.info("findSupplierInvoiceFeedbackData,cost time:{}",(end - start));
        log.info("findSupplierInvoiceFeedbackData,suppliers.size:{}",suppliers == null ? 0 : suppliers.size());
//        suppliers.stream().forEach(o ->o.setStatusDescription(statusDescription));
        return updateSuppliers(suppliers, status,statusDescription,DateUtils.getNowDate());
    }

    public void buildInvoiceMaturityAmountInfo(List<SupplierInvoiceInfo> sumSuppliers, BigDecimal variable){
        List<InvoiceMaturityAmountInfo> invoiceMaturityAmountInfos = Lists.newArrayList();
        Date date = new Date();
        sumSuppliers.stream().forEach(supplier ->{
            InvoiceMaturityAmountInfo info = new InvoiceMaturityAmountInfo();
            info.setAmount(supplier.getInvoiceAmount().multiply(variable));
            info.setCreateTime(date);
            info.setMaturityDate(supplier.getMaturityDate());
            info.setSupplierCode(supplier.getVendorCode());
            info.setCompanyCode(supplier.getCompanyCode());
            info.setStatus("0");
            info.setId(SnowFlakeUtil.nextId());
            invoiceMaturityAmountInfos.add(info);
        });
        long start = System.currentTimeMillis();
        saveInvoiceMaturityAmountInfo(invoiceMaturityAmountInfos);
        long end = System.currentTimeMillis();
        log.info("buildInvoiceMaturityAmountInfo,cost time:{}",(end - start));
    }

    public void saveInvoiceMaturityAmountInfo(List<InvoiceMaturityAmountInfo> invoiceMaturityAmountInfos) {
        if(!invoiceMaturityAmountInfos.isEmpty()) {
            String supplierCode = invoiceMaturityAmountInfos.get(0).getSupplierCode();
            String companyCode = invoiceMaturityAmountInfos.get(0).getCompanyCode();
            long startData = System.currentTimeMillis();
            List<InvoiceMaturityAmountInfo> dbInfo = invoiceMaturityAmountInfoMapper.findDbInvoiceMaturityAmountInfo(supplierCode,companyCode);
            List<InvoiceMaturityAmountInfo> finalInfo = null;
            if(dbInfo == null){
                dbInfo = Lists.newArrayList();
            }
            dbInfo.addAll(invoiceMaturityAmountInfos);
            finalInfo = dbInfo.stream().collect(Collectors.toMap(info ->
                            DateUtils.dateTime(info.getMaturityDate()), info -> info,
                    (s1, s2) ->{
                        s1.setAmount(s1.getAmount().add(s2.getAmount()));
                        return s1;
                    }
            )).values().stream().collect(Collectors.toList());
            long endData = System.currentTimeMillis();
            log.info("saveInvoiceMaturityAmountInfo, process cost time:{}",(endData - startData));
            if(null != finalInfo && !finalInfo.isEmpty()) {
                long startSave = System.currentTimeMillis();
                invoiceMaturityAmountInfoMapper.saveBatch(finalInfo);
                long endSave = System.currentTimeMillis();
                log.info("saveInvoiceMaturityAmountInfo, save cost time:{}",(endSave - startSave));
            }
        }
    }
}
