package com.microservices.otmp.masterdata.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.common.KafkaFactory;
import com.microservices.otmp.masterdata.domain.BizBaseVendor;
import com.microservices.otmp.masterdata.domain.BizBaseVendorBank;
import com.microservices.otmp.masterdata.service.IBizBaseVendorBankService;
import com.microservices.otmp.masterdata.service.IBizBaseVendorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


/**
 * LGVM kafka
 */
@Component
@Slf4j
public class LgvmKafkaListener {

    @Autowired
    private IBizBaseVendorService bizBaseVendorService;

    @Autowired
    private IBizBaseVendorBankService bizBaseVendorBankService;

    public static final String ERP_VENDOR_CODE = "erpVendorCode";

    @KafkaListener(containerFactory = KafkaFactory.LGVM_KAFKA_FACTORY,
            topics = {"${kafka.consumer.topics.lgvm_generalvendor_all}"})
    public void lgvmListener(ConsumerRecord<String, Object> obj, Acknowledgment ack) {
        try {
            Date date = DateUtils.getNowDate();
            String value = obj.value().toString();
            log.info("lgvmListener kafka params：{}", value);
            JSONObject jsonObject = JSON.parseObject(value);
            String additionalVenCategory = jsonObject.getString("additionalVenCategory");
            String primaryVenCategory = jsonObject.getString("primaryVenCategory");
            String trxSubCategories = jsonObject.getString("trxSubCategories");
            //筛选条件
            if (StringUtils.contains(additionalVenCategory, "microservices Partner Hub") || StringUtils.equals(primaryVenCategory, "microservices Partner Hub") ||
                    ((StringUtils.contains(additionalVenCategory, "Non-PO Payment") || StringUtils.equals(primaryVenCategory, "Non-PO Payment")) &&
                            StringUtils.contains(trxSubCategories, "IO-microservices Business Partners payments"))) {
                JSONArray bankStandardData = jsonObject.getJSONArray("bankStandardData");
                BizBaseVendor bizBaseVendor = new BizBaseVendor();
                bizBaseVendor.setVendorCode(jsonObject.getString(ERP_VENDOR_CODE));
                bizBaseVendor.setVendorName(jsonObject.getString("vendorFullName"));
                bizBaseVendor.setVendorCountry(jsonObject.getString("vendorCountry"));
                bizBaseVendor.setState(jsonObject.getString("vendorRegion"));
                bizBaseVendor.setCity(jsonObject.getString("vendorCity"));
                bizBaseVendor.setVatNumber(jsonObject.getString("vatRegNo"));
                bizBaseVendor.setCustomerId(jsonObject.getString("customerBPid"));
                if (jsonObject.getString("vendorCountry").equals("IN")) {
                    bizBaseVendor.setIndiaGstin(jsonObject.getString("taxNumber3"));
                }
                bizBaseVendor.setAdditionalVendorCategory(additionalVenCategory);
                bizBaseVendor.setPrimaryVendorCategory(primaryVenCategory);
                bizBaseVendor.setTransactionVendorCategories(trxSubCategories);
                bizBaseVendor.setTaxNumber1(jsonObject.getString("taxNumber1"));
                bizBaseVendor.setTaxNumber2(jsonObject.getString("taxNumber2"));
                bizBaseVendor.setTaxNumber3(jsonObject.getString("taxNumber3"));
                bizBaseVendor.setTaxNumber4(jsonObject.getString("taxNumber4"));
                bizBaseVendor.setTaxNumber5(jsonObject.getString("taxNumber5"));
                bizBaseVendor.setStatus("Y");
                //获取companyCode集合
                JSONArray companyCodeData = jsonObject.getJSONArray("companyCodeData");
                for (Object company : companyCodeData) {
                    String companyCode = JSON.parseObject(JSON.toJSONString(company)).getString("companyCode");
                    bizBaseVendor.setCompanyCode(companyCode);
                    int count = bizBaseVendorService.selectCountByVendorCode(jsonObject.getString(ERP_VENDOR_CODE), companyCode);
                    bizBaseVendor.setUpdateTime(date);
                    if (count > 0) {
                        bizBaseVendorService.updateVendor(bizBaseVendor);
                    } else {
                        bizBaseVendor.setCreateTime(date);
                        //插入vendor的时候默认没有插入bank信息,后面在插入bank信息若有当前vendor则更新该字段
                        bizBaseVendor.setErrorInfo("No vendor bank in LGVM");
                        bizBaseVendorService.insertVendorLgvmKafka(bizBaseVendor);
                    }
                }
                //删除updateTime ！= date的数据
                bizBaseVendorService.deleteBizBaseVendorByUpdateTime(bizBaseVendor.getVendorCode(), date);
                save(date, jsonObject, bankStandardData);
            }
            ack.acknowledge();
        } catch (Exception e) {
            ack.nack(1000);
        }

    }

    private void save(Date date, JSONObject jsonObject, JSONArray bankStandardData) {
        if (bankStandardData != null && bankStandardData.size() > 0) {
            //保存bank信息
            for (int i = 0; i < bankStandardData.size(); i++) {
                BizBaseVendorBank bankInfo = new BizBaseVendorBank();
                JSONObject bankJson = bankStandardData.getJSONObject(i);
                bankInfo.setVendorCode(jsonObject.getString(ERP_VENDOR_CODE));
                //BankType,AccountHolder,BranchCode第二版后暂时没有对应字段
                bankInfo.setBankAccount(bankJson.getString("bankAccount"));
                bankInfo.setBankNumber(bankJson.getString("bankNumber"));
                bankInfo.setBankName(bankJson.getString("bankName"));
                bankInfo.setBankCountry(bankJson.getString("bankCountryKey"));
                bankInfo.setSwiftCode(bankJson.getString("swiftbic"));
                bankInfo.setIban(bankJson.getString("iban"));
                bankInfo.setStatus("Y");
                //判断是否存在该数据,有的话更新数据
                List<BizBaseVendorBank> bizBaseVendorBankDTOS = bizBaseVendorBankService.selectBizBaseVendorBankList(bankInfo);
                if (CollectionUtils.isNotEmpty(bizBaseVendorBankDTOS)) {
                    //更新数据
                    bankInfo.setId(bizBaseVendorBankDTOS.get(0).getId());
                    bankInfo.setUpdateTime(date);
                    bizBaseVendorBankService.updateBizBaseVendorBank(bankInfo);
                } else {
                    bankInfo.setCreateTime(date);
                    bankInfo.setUpdateTime(date);
                    bizBaseVendorBankService.insertBizBaseVendorBank(bankInfo);
                }
            }
            //删除updateTime不为最新当前
            bizBaseVendorBankService.deleteBank(jsonObject.getString(ERP_VENDOR_CODE), date);
            //更新error info信息
            bizBaseVendorService.updateErrorInfoByVendor(jsonObject.getString(ERP_VENDOR_CODE));
        }
    }

}
