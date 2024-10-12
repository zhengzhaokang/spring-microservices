package com.microservices.otmp.analysis.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.microservices.otmp.analysis.common.domain.*;
import com.microservices.otmp.analysis.domain.vo.SupplierCompanyCodeInfo;
import com.microservices.otmp.analysis.mapper.SupplierInvoiceMapper;
import com.microservices.otmp.analysis.service.ISupplierInvoiceService;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.enums.InvoiceStatusEnum;
import com.microservices.otmp.common.enums.SupplierStatusEnum;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SupplierInvoiceServiceImpl implements ISupplierInvoiceService {

    @Autowired
    private SupplierInvoiceMapper supplierInvoiceMapper;
    @Autowired
    private RedisUtils redis;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Integer> findSupplierInvoiceCount() {
        Map<String, Integer> map = Maps.newHashMap();
        Integer suppliers = supplierInvoiceMapper.findSupplierInvoiceAllCount();
        BigDecimal bankLimit = supplierInvoiceMapper.findBankLimit();
        if(null == bankLimit){
            bankLimit = new BigDecimal(0);
        }
        Integer limitOccupyRate = bankLimit.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
        if (limitOccupyRate > 100) {
            limitOccupyRate = 100;
        }
        map.put(SupplierStatusEnum.AWAITING.code, 0);
        map.put(SupplierStatusEnum.PENDING.code, 0);
        map.put(SupplierStatusEnum.ACTIVE.code, 0);
        map.put(SupplierStatusEnum.SUSPEND.code, 0);
        List<SupplierInvoiceCount> supplierInvoiceCount = supplierInvoiceMapper.findSupplierInvoiceCount();
        supplierInvoiceCount.stream().forEach(su -> {
            if (SupplierStatusEnum.AWAITING.num.equals(su.getStatus())) {
                map.put(SupplierStatusEnum.AWAITING.code, su.getCount());
            } else if (SupplierStatusEnum.PENDING.num.equals(su.getStatus())) {
                map.put(SupplierStatusEnum.PENDING.code, su.getCount());
            } else if (SupplierStatusEnum.ACTIVE.num.equals(su.getStatus())) {
                map.put(SupplierStatusEnum.ACTIVE.code, (Integer) map.get(SupplierStatusEnum.ACTIVE.code) + su.getCount());
            } else if (SupplierStatusEnum.SUSPEND.num.equals(su.getStatus())) {
                map.put(SupplierStatusEnum.SUSPEND.code, su.getCount());
            } else if (SupplierStatusEnum.ONHOLD.num.equals(su.getStatus())) {
                map.put(SupplierStatusEnum.ACTIVE.code, (Integer) map.get(SupplierStatusEnum.ACTIVE.code) + su.getCount());
            }
        });
        map.put("suppliers", suppliers);
        map.put("limitOccupyRate", limitOccupyRate);
        return map;
    }

    @Override
    public List<Map<String, Object>> findSupplierAmount() {
        List<Map<String, Object>> resultList = Lists.newArrayList();
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> result2 = Maps.newHashMap();
        List<SupplierAmount> financedSupplier = redis.getList(RedisConstants.SUPPLIERAMOUNTFINANCEDSUPPLIER,SupplierAmount.class);
        List<SupplierAmount> payableSupplier = redis.getList(RedisConstants.SUPPLIERAMOUNTPAYABLESUPPLIER,SupplierAmount.class);
        if(null == financedSupplier || null == payableSupplier || financedSupplier.isEmpty() || payableSupplier.isEmpty()) {
            financedSupplier = supplierInvoiceMapper.findSupplierAmount(InvoiceStatusEnum.FINANCED.code);
            payableSupplier = supplierInvoiceMapper.findSupplierAmount(null);
            redis.set(RedisConstants.SUPPLIERAMOUNTFINANCEDSUPPLIER, financedSupplier, 60L * 60L * 24L);
            redis.set(RedisConstants.SUPPLIERAMOUNTPAYABLESUPPLIER, payableSupplier, 60L * 60L * 24L);
        }
        result.put("data", financedSupplier);
        result.put("title", "Financed Supplier");
        result2.put("data", payableSupplier);
        result2.put("title", "Accounts Payable Volumn Supplier");
        resultList.add(result);
        resultList.add(result2);
        return resultList;
    }

    @Override
    public List<SupplierAccountsVO> findAccountsPayable(QueryAccountsPayableParam param) {
        List<String> supplierCodes = null;
        if(!StringUtils.isEmpty(param.getBankId()) || null != param.getEntityId() || null != param.getSupplierId()){
            supplierCodes = getVenderCode(supplierInvoiceMapper.findSupplierCode(param));
        }
        Map<String, List<SupplierAccountsValue>> map;
        List<SupplierAccountsValue> values = supplierInvoiceMapper.findAccountsAmount(param);
        if(null != supplierCodes && !supplierCodes.isEmpty()){
            List<String> finalSupplierCodes = supplierCodes;
            map = values.stream().filter(v -> finalSupplierCodes.contains(v.getVendorCode())).collect(Collectors.toList()).stream().collect(Collectors.groupingBy(SupplierAccountsValue::getValueDate));
        }else{
            map = values.stream().collect(Collectors.groupingBy(SupplierAccountsValue::getValueDate));
        }
        List<SupplierAccountsVO> finalValues = Lists.newArrayList();
        List<SupplierAccountsVO> lastValues = Lists.newArrayList();
        for (Map.Entry<String, List<SupplierAccountsValue>> entry :map.entrySet()) {
            String key = entry.getKey();
            List<SupplierAccountsValue> value = entry.getValue();
            SupplierAccountsVO sv = new SupplierAccountsVO();
            sv.setValueDate(key);
            sv.setAmount(value.stream().map(SupplierAccountsValue::getAmount).reduce((a,b) -> a.add(b)).get());
            sv.setInvoiceCount(value.stream().map(SupplierAccountsValue::getInvoiceCount).reduce((a,b) -> a + b).get());
            finalValues.add(sv);
        }
        //finalValues.sort(Comparator.comparing(SupplierAccountsVO::getValueDate));
        List<String> lists = Lists.newArrayList();
        Integer type = param.getType();
        Integer amountType = param.getAmountType();
        if(type == 1){
            lists = DateUtils.getLastYear(5);
        }else if(type == 2){
            if(amountType == 2){
                lists = DateUtils.getPastTwelveYearMonth(null);
            }else{
                lists = DateUtils.getPastTwelveYearMonth(4);
            }
        }
        lists.stream().forEach(m ->{
            SupplierAccountsVO sv = new SupplierAccountsVO();
            sv.setValueDate(m);
            sv.setAmount(new BigDecimal(0));
            sv.setInvoiceCount(0L);
            lastValues.add(sv);
        });
        finalValues.stream().forEach(f ->{
            List<SupplierAccountsVO> svs = lastValues.stream().filter(l -> StringUtils.equals(f.getValueDate(),l.getValueDate())).collect(Collectors.toList());
            if(!svs.isEmpty()){
                SupplierAccountsVO sv = svs.get(0);
                sv.setAmount(f.getAmount().divide(new BigDecimal(100 * 10000)).setScale(2, BigDecimal.ROUND_HALF_UP));
                sv.setInvoiceCount(f.getInvoiceCount());
            }
        });
        return lastValues;
    }

    @Override
    public void doAccountsPayableJob() {
        String uuid = System.currentTimeMillis() + UUID.randomUUID().toString().replaceAll("-", "");
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 10, TimeUnit.SECONDS);
        if(lock) {
            supplierInvoiceMapper.computePayableAccountsAmount();
            supplierInvoiceMapper.computeFinancingAccountsAmount();
        }
    }

    @Override
    public Map<String, Long> findModelRatio() {
        Map<String, Long> map = Maps.newHashMap();
        Integer boe = supplierInvoiceMapper.findBoeModelRatio();
        Integer all = supplierInvoiceMapper.findAllModelRatio();
        double boeRate = (double)boe /  (double)all * 100;
        long boeLong = Math.round(boeRate);
        map.put("boe", boeLong);
        map.put("ap", boeLong == 0 ? 0 : 100 - boeLong);
        return map;
    }

    public List<String> getVenderCode(List<SupplierCompanyCodeInfo> supplierCompanyCodeInfo){
        List<String> supplierCode = null;
        if(supplierCompanyCodeInfo != null){
            supplierCode = Lists.newArrayList();
            List<String> finalSupplierCode = supplierCode;
            supplierCompanyCodeInfo.stream().forEach(su ->{
                finalSupplierCode.add(su.getSupplierCode());
            });
            finalSupplierCode.stream().distinct();
            supplierCode = finalSupplierCode;
        }
        return supplierCode;
    }
}
