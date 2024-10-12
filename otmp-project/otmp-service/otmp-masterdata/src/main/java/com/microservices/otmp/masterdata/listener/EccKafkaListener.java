package com.microservices.otmp.masterdata.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.masterdata.common.KafkaFactory;
import com.microservices.otmp.masterdata.domain.BizBaseExchangeRate;
import com.microservices.otmp.masterdata.domain.BizBaseSalesOffice;
import com.microservices.otmp.masterdata.domain.dto.*;
import com.microservices.otmp.masterdata.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * * dhc
 */
@Component
@Slf4j
public class EccKafkaListener {


    public static final String IBMSNAP_OPERATION = "ibmsnapOperation";
    public static final String PROFIT_CENTER = "profitCenter";
    @Autowired
    IBizBaseExchangeRateService exchangeRateService;
    @Autowired
    IBizBaseCompanyLocalCurrecyMappingService currecyMappingService;
    @Autowired
    IBizBaseSalesOfficeProfitCenterMappingService salesOfficeProfitCenterMappingService;
    @Autowired
    IBizBaseBwBuBgMappingService bizBaseBwBuBgMappingService;
    @Autowired
    IBizBaseSalesOfficeService bizBaseSalesOfficeService;
    @Autowired
    IBizBasemicroservicesEntityService bizBasemicroservicesEntityService;
    @Autowired
    IBizBaseProfitCenterLgapService bizBaseProfitCenterLgapService;

    @Autowired
    private RedissonClient redissonClient;

    @Resource(name = "threadPoolTaskExecutor")
    ThreadPoolTaskExecutor executor;

    // @KafkaListener(topics = {"${kafka.consumer.topics.bw_bu_bg_mapping}"}, groupId = GROUP_ID,id="bwBu")
    public void listenerBwBuBg(Object value) {
        if (null == value) {
            return;
        }
        log.info("监听到消息 bw bu", value);
        JSONObject jsonObject = JSON.parseObject(value.toString());
        BizBaseBwBuBgMappingDTO bizBaseBwBuBgMappingDTO = getBizBaseBwBuBgMapping(jsonObject);
        if (bizBaseBwBuBgMappingService.updateByKey(bizBaseBwBuBgMappingDTO) <= 0) {
            bizBaseBwBuBgMappingDTO.setUpdateTime(null);
            bizBaseBwBuBgMappingService.insertBizBaseBwBuBgMapping(bizBaseBwBuBgMappingDTO);
        }

    }

    /**
     * * 构建实体
     * @param jsonObject
     * @return
     */
    private BizBaseBwBuBgMappingDTO getBizBaseBwBuBgMapping(JSONObject jsonObject) {
        String bwbu = jsonObject.getString("bwbu");
        String bgBusinessGroup = jsonObject.getString("bgBusinessGroup");
        String profitCenter = jsonObject.getString(PROFIT_CENTER);
        String validateFrom = jsonObject.getString("validateFrom");
        String validateTo = jsonObject.getString("validateTo");
        String ibmsnapOperation = jsonObject.getString(IBMSNAP_OPERATION);
        BizBaseBwBuBgMappingDTO bizBaseBwBuBgMappingDTO = new BizBaseBwBuBgMappingDTO();
        if (StrUtil.isNotBlank(ibmsnapOperation) && ibmsnapOperation.equals("D")) {
            bizBaseBwBuBgMappingDTO.setDelFlag(2);
        }
        bizBaseBwBuBgMappingDTO.setBwBu(bwbu);
        bizBaseBwBuBgMappingDTO.setBusinessGroup(bgBusinessGroup);
        bizBaseBwBuBgMappingDTO.setValidateFrom(validateFrom);
        bizBaseBwBuBgMappingDTO.setValidateTo(validateTo);
        bizBaseBwBuBgMappingDTO.setProfitCenterCode(profitCenter);
        return bizBaseBwBuBgMappingDTO;
    }


    @KafkaListener(containerFactory = KafkaFactory.ECC_KAFKA_FACTORY,topics = {"${kafka.consumer.topics.company_local_currency}"})
    public void companyCodeLocalListener(ConsumerRecord<String,Object> obj, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Acknowledgment ack) {
        log.info("companyCodeLocalListener kafka params：{}", obj.value());
        try{
            if (null == obj) {
                return;
            }
            Object value = obj.value();
            if (null == value) {
                return;
            }
            if (StrUtil.isBlank(topic)) {
                return;
            }
            executor.execute(()->{
                listenerCompanyCodeLocalCurrency(value);
                //手动提交
                ack.acknowledge();
            });
        }catch (Exception e){
            ack.nack(1000);
        }
    }
    @KafkaListener(containerFactory = KafkaFactory.ECC_KAFKA_FACTORY,topics = {"${kafka.consumer.topics.exchange_rate}"})
    public void exChangeRateListener(ConsumerRecord<String,Object> obj, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Acknowledgment ack) {
        log.info("exChangeRateListener kafka params：{}", obj.value());
        try{
            if (null == obj) {
                return;
            }
            Object value = obj.value();
            if (null == value) {
                return;
            }
            if (StrUtil.isBlank(topic)) {
                return;
            }
            log.info("监听到 exChaangeRate 消息");
            executor.execute(()->{
                listenerExChangeRate(value);
                //手动提交
                ack.acknowledge();
            });
        }catch (Exception e){
            ack.nack(1000);
        }
    }
    @KafkaListener(containerFactory = KafkaFactory.ECC_KAFKA_FACTORY,topics = {"${kafka.consumer.topics.bw_bu_bg_mapping}"} )
    public void bwBuBgListener(ConsumerRecord<String,Object> obj, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Acknowledgment ack) {
        log.info("ConsumerRecord kafka params：{}", obj.value());
        try{
            if (null == obj) {
                return;
            }
            Object value = obj.value();
            if (null == value) {
                return;
            }
            if (StrUtil.isBlank(topic)) {
                return;
            }
            log.info("监听到 bw bu bg 消息");
            executor.execute(()->{
                listenerBwBuBg(value);
                //手动提交
                ack.acknowledge();
            });
        }catch (Exception e){
            ack.nack(1000);
        }
    }
    @KafkaListener(containerFactory = KafkaFactory.ECC_KAFKA_FACTORY,topics = {"${kafka.consumer.topics.sales_office_procenter}"} )
    public void salesOfficeProCenterListener(ConsumerRecord<String,Object> obj, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Acknowledgment ack) {
        log.info("salesOfficeProCenterListener kafka params：{}", obj.value());
        try{
            if (null == obj) {
                return;
            }
            Object value = obj.value();
            if (null == value) {
                return;
            }
            if (StrUtil.isBlank(topic)) {
                return;
            }
            executor.execute(()->{
                listenerSalesOfficeProCenter(value);
                //手动提交
                ack.acknowledge();
            });
        }catch (Exception e){
            ack.nack(1000);
        }
    }

    private void listenerCompanyCodeLocalCurrency(Object value) {
        log.info("监听到消息 local currency", value);
        JSONObject jsonObject = JSON.parseObject(value.toString());
        String companyCode = jsonObject.getString("companyCode");
        String currencyCode = jsonObject.getString("currencyCode");
        String companyName = jsonObject.getString("companyName");
        String ibmsnapOperation = jsonObject.getString(IBMSNAP_OPERATION);
        if (filterCompanyCodeLocalCurrencyMapping(companyCode, currencyCode)) {
            BizBaseCompanyLocalCurrecyMappingDTO bizBaseCompanyLocalCurrecyMappingDTO = new BizBaseCompanyLocalCurrecyMappingDTO();
            bizBaseCompanyLocalCurrecyMappingDTO.setLocalCurrencyCode(currencyCode);
            bizBaseCompanyLocalCurrecyMappingDTO.setCompanyCode(companyCode);
            bizBaseCompanyLocalCurrecyMappingDTO.setCreateTime(DateUtils.getNowDate());
            bizBaseCompanyLocalCurrecyMappingDTO.setCompanyName(companyName);
            if (StrUtil.isNotBlank(ibmsnapOperation) && ibmsnapOperation.equals("D")) {
                bizBaseCompanyLocalCurrecyMappingDTO.setDelFlag(2);
            }
            if (null == bizBaseCompanyLocalCurrecyMappingDTO.getDelFlag()) {
                bizBaseCompanyLocalCurrecyMappingDTO.setDelFlag(0);
            }
            if (currecyMappingService.updateByCompanyCode(bizBaseCompanyLocalCurrecyMappingDTO) <= 0) {
                bizBaseCompanyLocalCurrecyMappingDTO.setUpdateTime(null);
                currecyMappingService.insertBizBaseCompanyLocalCurrecyMapping(bizBaseCompanyLocalCurrecyMappingDTO);
            }
        }
    }
    // @KafkaListener(topics = {"${kafka.consumer.topics.sales_office_procenter}"}, groupId = GROUP_ID,id="salesOfficeProCenter")
    public void listenerSalesOfficeProCenter(Object value) {
        if (null == value) {
            return;
        }
        log.info("监听到消息 pro center", value);
        JSONObject jsonObject = JSON.parseObject(value.toString());
        String salesOffice = jsonObject.getString("salesOffice");
        String salesOrg = jsonObject.getString("salesOrg");
        String profitCenter = jsonObject.getString(PROFIT_CENTER);
        String ibmsnapOperation = jsonObject.getString(IBMSNAP_OPERATION);
        String client = jsonObject.getString("client");
        if (StrUtil.isBlank(salesOffice) || StrUtil.isBlank(profitCenter) || StrUtil.isBlank(client) || !"301".equals(client)) {
            log.info("数据不符合要求");
            return;
        }
        //校验salesOffice是否在biz_base_sales_office
        BizBaseSalesOffice bizBaseSalesOffice = new BizBaseSalesOffice();
        bizBaseSalesOffice.setSalesOfficeCode(salesOffice);
        List<BizBaseSalesOffice> salesOffices = bizBaseSalesOfficeService.selectBizBaseSalesOfficeList(bizBaseSalesOffice);
        if (CollectionUtils.isEmpty(salesOffices)) {
            log.info("salesOffice未在biz_base_sales_office");
            return;
        }
        BizBaseSalesOfficeProfitCenterMappingDTO salesOfficeProfitCenterMappingDTO = new BizBaseSalesOfficeProfitCenterMappingDTO();
        salesOfficeProfitCenterMappingDTO.setProfitCenterCode(profitCenter);
        salesOfficeProfitCenterMappingDTO.setSalesOfficeCode(salesOffice);
        salesOfficeProfitCenterMappingDTO.setSalesOrgCode(salesOrg);
        if (StrUtil.isNotBlank(ibmsnapOperation) && ibmsnapOperation.equals("D")) {
            salesOfficeProfitCenterMappingDTO.setDelFlag(2);
        }
        if (null == salesOfficeProfitCenterMappingDTO.getDelFlag()) {
            salesOfficeProfitCenterMappingDTO.setDelFlag(0);
        }
        if (salesOfficeProfitCenterMappingService.updateBySalesOffice(salesOfficeProfitCenterMappingDTO) <= 0) {
            salesOfficeProfitCenterMappingDTO.setUpdateTime(null);
            salesOfficeProfitCenterMappingService.insertBizBaseSalesOfficeProfitCenterMapping(salesOfficeProfitCenterMappingDTO);
        }
    }

    //@KafkaListener(topics = {"${kafka.consumer.topics.exchage_rate}"}, groupId = GROUP_ID,id="exChangeRate")
    public void listenerExChangeRate(Object value) {
        if (null == value) {
            return;
        }
        log.info("监听到消息 exChangeRate ", value);
        int count = 0;
        JSONArray jsonArray = JSON.parseArray(value.toString());
        BizBaseExchangeRate bizBaseExchangeRate;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            bizBaseExchangeRate = new BizBaseExchangeRate();
            BigDecimal exRate = jsonObject.getBigDecimal("exRate");
            String exchangeRateType = jsonObject.getString("exRateType");
            String bloombergRate = jsonObject.getString("bloombergRate");
            String fromRatio = jsonObject.getString("fromRatio");
            String toRatio = jsonObject.getString("toRatio");
            if (isContinue(jsonObject, bizBaseExchangeRate, exchangeRateType, bloombergRate) || (bloombergRate.contains("-") && fromRatio == null)) {
                continue;
            }
            //如果为负数
            BigDecimal  bloom = null;
            if (bloombergRate.contains("-")) {
                bloombergRate = bloombergRate.trim();
                bloom = new BigDecimal(StrUtil.sub(bloombergRate,1,bloombergRate.length()));
                exRate = NumberUtil.div(NumberUtil.mul(bloom, new BigDecimal(fromRatio)), new BigDecimal(toRatio));
            }

            BizBaseExchangeRate dbExChangeRate = exChangeNotRepeat(bizBaseExchangeRate);
            if (null == dbExChangeRate) {
                count++;
                bizBaseExchangeRate.setExchangeRateType(exchangeRateType);
                bizBaseExchangeRate.setRateValue(exRate);
                bizBaseExchangeRate.setStatus("Y");
                exchangeRateService.insertBizBaseExchangeRate(bizBaseExchangeRate);
            } else {
                dbExChangeRate.setExchangeRateType(exchangeRateType);
                dbExChangeRate.setRateValue(exRate);
                exchangeRateService.updateBizBaseExchangeRate(dbExChangeRate);
            }

        }
        log.info("本次共监听到消息 ++++++++" + count + "条");
    }

    /**
     * * 过滤
     * @param companyCode
     * @param currencyCode
     * @return
     */
    private boolean filterCompanyCodeLocalCurrencyMapping(String companyCode, String currencyCode) {
        if (StrUtil.isBlank(companyCode) || StrUtil.isBlank(currencyCode)) {
            return Boolean.FALSE;
        }
        BizBaseCompanyLocalCurrecyMappingDTO currecyMappingDTO = new BizBaseCompanyLocalCurrecyMappingDTO();
        currecyMappingDTO.setCompanyCode(companyCode);
        currecyMappingDTO.setLocalCurrencyCode(currencyCode);
        List<BizBaseCompanyLocalCurrecyMappingDTO> list = currecyMappingService.selectBizBaseCompanyLocalCurrecyMappingList(currecyMappingDTO);
        if (null != list && list.size() > 0) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
    /**
     * * 过滤需要的数据
     **
     * @param toCurrency
     * @param exchangeRateType
     * @return
     */
    private boolean filterExchangeRateData(String toCurrency, String exchangeRateType) {
        if (StrUtil.isBlank(exchangeRateType)||StrUtil.isBlank(toCurrency)) {
            return Boolean.FALSE;
        }
        if (!"M".equals(exchangeRateType) && !"RVAL".equals(exchangeRateType) && !"P".equals(exchangeRateType)) {
            return Boolean.FALSE;
        }
        if (!"USD".equals(toCurrency) ) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * * 重复 返回false 不重复返回 true
     * @param bizBaseExchangeRate
     * @return
     */
    private BizBaseExchangeRate exChangeNotRepeat(BizBaseExchangeRate bizBaseExchangeRate) {
        BizBaseExchangeRate exchangeRate = new BizBaseExchangeRate();
        exchangeRate.setCurrencyCode(bizBaseExchangeRate.getCurrencyCode());
        exchangeRate.setRateValue(bizBaseExchangeRate.getRateValue());
        exchangeRate.setRateDate(bizBaseExchangeRate.getRateDate());
        exchangeRate.setExchangeRateType(bizBaseExchangeRate.getExchangeRateType());
        List<BizBaseExchangeRate> list = exchangeRateService.selectBizBaseExchangeRateList(exchangeRate);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public boolean isContinue(JSONObject jsonObject, BizBaseExchangeRate bizBaseExchangeRate, String exchangeRateType, String bloombergRate) {
        String toCurrency = jsonObject.getString("toCurrency");
        if (!filterExchangeRateData(toCurrency, exchangeRateType)) {
            return true;
        }
        if (StrUtil.isBlank(bloombergRate)) {
            return true;
        }
        String rateDate = jsonObject.getString("date");
        if(rateDate.contains("7998")){
            return true;
        }
        Date date;
        try {
            date = DateUtil.parse(rateDate, "yyyy-MM-dd");
        } catch (Exception e) {
            return true;
        }
        bizBaseExchangeRate.setRateDate(date);
        bizBaseExchangeRate.setCurrencyCode(jsonObject.getString("fromCurrency"));
        return false;
    }


    @KafkaListener(containerFactory = KafkaFactory.ECC_KAFKA_FACTORY,
            topics = {"${kafka.consumer.topics.fmdp_company_code}"})
    public void fmdpCompanyCode(ConsumerRecord<String,Object> obj, Acknowledgment ack) {
        log.info("fmdpCompanyCode kafka params：{}", obj.value());
        try {
            if (Objects.nonNull(obj)) {
                String value = obj.value().toString();
                JSONObject jsonObject = JSON.parseObject(value);
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("items");
                if (CollectionUtils.isNotEmpty(jsonArray)) {
                    for (Object o : jsonArray) {
                        updateOrInsert(o);
                    }
                }
            }
            ack.acknowledge();
        } catch (Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            ack.nack(1000);
        }
    }

    private void updateOrInsert(Object o) {
        Date date = DateUtils.getNowDate();
        JSONObject object = JSON.parseObject(JSON.toJSONString(o));
        String companyCode = object.getString("companyCode");
        String operationType = object.getString("operationType");
        String country = object.getString("country");
        String companyName = object.getString("companyName");
        String vatRegistrationNumber = object.getString("vatRegistrationNumber");
        /**
         * a. 如果消费数据的companyCode在当前数据表中不存在，且operationType="update"，执行新增数据插入；
         * b. 如果消费数据的companyCode在当前数据表中不存在，且operationType="delete"，不做任何操作；
         * b. 如果消费数据的companyCode在当前数据表中已存在，且operationType="update"，执行数据更新;
         * d.如果消费数据的companyCode在当前数据表中已存在，且operationType="delete", 执行数据逻辑删除，标记delete_flag
         */
        BizBasemicroservicesEntityDTO bizBasemicroservicesEntityDTO = new BizBasemicroservicesEntityDTO();
        bizBasemicroservicesEntityDTO.setCompanyCode(companyCode);
        bizBasemicroservicesEntityDTO.setDeleteFlag(false);
        bizBasemicroservicesEntityDTO.setStatus("Y");
        List<BizBasemicroservicesEntityDTO> bizBasemicroservicesEntityDTOS = bizBasemicroservicesEntityService.selectBizBasemicroservicesEntityList(bizBasemicroservicesEntityDTO);
        bizBasemicroservicesEntityDTO.setMicroservicesEntityName(companyName);
        bizBasemicroservicesEntityDTO.setCountry(country);
        bizBasemicroservicesEntityDTO.setMicroservicesVatId(vatRegistrationNumber);
        bizBasemicroservicesEntityDTO.setUpdateTime(date);
        if (CollectionUtils.isEmpty(bizBasemicroservicesEntityDTOS)) {
            if (operationType.equals("update")) {
                bizBasemicroservicesEntityDTO.setCreateTime(date);
                bizBasemicroservicesEntityService.insertBizBasemicroservicesEntity(bizBasemicroservicesEntityDTO);
            }
        } else {
            bizBasemicroservicesEntityDTO.setId(bizBasemicroservicesEntityDTOS.get(0).getId());
            bizBasemicroservicesEntityDTO.setCreateTime(bizBasemicroservicesEntityDTOS.get(0).getCreateTime());
            if (operationType.equals("update")) {
                bizBasemicroservicesEntityService.updateBizBasemicroservicesEntity(bizBasemicroservicesEntityDTO);
            } else if (operationType.equals("delete")) {
                bizBasemicroservicesEntityDTO.setDeleteFlag(true);
                bizBasemicroservicesEntityService.updateBizBasemicroservicesEntity(bizBasemicroservicesEntityDTO);
            }
        }
    }

    @KafkaListener(containerFactory = KafkaFactory.ECC_KAFKA_FACTORY,
            topics = {"${kafka.consumer.topics.fmdp_profit_center}"})
    public void fmdpProfitCenter(ConsumerRecord<String,Object> obj, Acknowledgment ack) {
        log.info("fmdpProfitCenter kafka params：{}", obj.value());
        try {
            if (Objects.nonNull(obj)) {
                String value = obj.value().toString();
                JSONObject jsonObject = JSON.parseObject(value);
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("items");
                if (CollectionUtils.isNotEmpty(jsonArray)) {
                    for (Object o : jsonArray) {
                        updateOrInsertProfitCenter(o);
                    }
                }
            }
            ack.acknowledge();
        } catch (Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            ack.nack(1000);
        }
    }

    private void updateOrInsertProfitCenter(Object o) {
        Date date = DateUtils.getNowDate();
        JSONObject object = JSON.parseObject(JSON.toJSONString(o));
        String profitCenter = object.getString(PROFIT_CENTER);
        String validToDate = object.getString("validToDate");
        String lockIndicator = object.getString("lockIndicator");
        /**
         * a. 如果消费数据的profitCenter在当前数据表中不存在，validitytodate>systemdate &  lockIndicator为空，
         * 则进行新profitcentercode插入逻辑，并设置status='Y'
         * b. 如果消费数据的profitCenter在当前数据表中不存在，但 validitytodate≤systemdate or profitcenterisblock=‘X’ ，
         * 则该profitcenter在上游系统失效或者是被禁用，SDMS无需消费使用
         * c. 如果消费数据的profitCenter在当前数据表中已存在，且 validitytodate>systemdate & lockIndicator为空，
         * 如果status='Y',则无需做任何处理，保持该profitcenter数据
         * d.如果消费数据的profitCenter在当前数据表中已存在，但 validitytodate≤systemdate  or profitcenterisblock=‘X’ ，
         * 则该profitcenter在上游系统失效或者是被禁用，如果status='Y', SDMS需要进行状态更新，设置status为'N'
         * 如果status='N',则无需做任何处理，保持该profitcenter数据
         */
        BizBaseProfitCenterLgapDTO bizBaseProfitCenterLgapDTO = new BizBaseProfitCenterLgapDTO();
        bizBaseProfitCenterLgapDTO.setProfitCenterCode(profitCenter);
        bizBaseProfitCenterLgapDTO.setProfitCenterName(profitCenter);
        bizBaseProfitCenterLgapDTO.setStatus("Y");
        List<BizBaseProfitCenterLgapDTO> bizBaseProfitCenterLgapDTOS = bizBaseProfitCenterLgapService.selectBizBaseProfitCenterLgapList(bizBaseProfitCenterLgapDTO);
        bizBaseProfitCenterLgapDTO.setUpdateTime(date);
        if (CollectionUtils.isEmpty(bizBaseProfitCenterLgapDTOS)) {
            if (validToDate.compareTo(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, date)) > 0 && StringUtils.isEmpty(lockIndicator)) {
                bizBaseProfitCenterLgapDTO.setCreateTime(date);
                bizBaseProfitCenterLgapService.insertBizBaseProfitCenterLgap(bizBaseProfitCenterLgapDTO);
            }
        } else {
            bizBaseProfitCenterLgapDTO.setId(bizBaseProfitCenterLgapDTOS.get(0).getId());
            bizBaseProfitCenterLgapDTO.setCreateTime(bizBaseProfitCenterLgapDTOS.get(0).getCreateTime());
            if (validToDate.compareTo(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, date)) > 0 && StringUtils.isEmpty(lockIndicator)) {
                if (bizBaseProfitCenterLgapDTOS.get(0).getStatus().equals("N")) {
                    bizBaseProfitCenterLgapDTO.setStatus("Y");
                    bizBaseProfitCenterLgapService.updateBizBaseProfitCenterLgap(bizBaseProfitCenterLgapDTO);
                }
            } else {
                if (bizBaseProfitCenterLgapDTOS.get(0).getStatus().equals("Y")) {
                    bizBaseProfitCenterLgapDTO.setStatus("N");
                    bizBaseProfitCenterLgapService.updateBizBaseProfitCenterLgap(bizBaseProfitCenterLgapDTO);
                }
            }
        }



    }
}
