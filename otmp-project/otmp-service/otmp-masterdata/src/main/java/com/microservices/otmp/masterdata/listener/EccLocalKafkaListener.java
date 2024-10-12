package com.microservices.otmp.masterdata.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.common.KafkaFactory;
import com.microservices.otmp.masterdata.domain.BizBaseCustomer;
import com.microservices.otmp.masterdata.domain.BizBaseSalesOrg;
import com.microservices.otmp.masterdata.service.IBizBaseCustomerService;
import com.microservices.otmp.masterdata.service.IBizBaseSalesOrgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * * dhc
 */
@Component
@Slf4j
public class EccLocalKafkaListener {


    public static final String CUSTOMER_ACCOUNT_GROUP = "customerAccountGroup";
    public static final String SALES_ORGANIZATION = "salesOrganization";
    public static final String SALES_OFFICE = "salesOffice";
    public static final String CUSTOMER_ORDER_BLOCK = "customerOrderBlock";
    @Autowired
    private IBizBaseCustomerService bizBaseCustomerService;
    @Autowired
    private IBizBaseSalesOrgService bizBaseSalesOrgService;
    @Value("${kafka.consumer.topics.customer_general}")
    private String customerGeneral;


    @KafkaListener(containerFactory = KafkaFactory.ECC_LOCAL_KAFKA_FACTORY,
            topics = {"${kafka.consumer.topics.customer_general}"})
    public void customerGeneralListener(ConsumerRecord<String,Object> obj, Acknowledgment ack,  @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            String value = obj.value().toString();
            JSONObject jsonObject = JSON.parseObject(value);
            String customerAccountGroup = jsonObject.getString(CUSTOMER_ACCOUNT_GROUP);
            //过滤掉中国区的customer,条件countryKey != CN,
            // customerAccountGroup = 81 or customerAccountGroup = 83
            if (!jsonObject.getString("countryKey").equals("CN") &&
                    (Objects.equals("81", customerAccountGroup) || Objects.equals("83", customerAccountGroup))) {
                Date date = DateUtils.getNowDate();
                BizBaseCustomer bizBaseCustomer = new BizBaseCustomer();
                bizBaseCustomer.setCustomerNumber(jsonObject.getString("customerNumber"));
                bizBaseCustomer.setStatus("Y");
                List<BizBaseCustomer> bizBaseCustomers = bizBaseCustomerService.selectBizBaseCustomerList(bizBaseCustomer);
                if (topic.equals(customerGeneral)) {
                    if (CollectionUtils.isEmpty(bizBaseCustomers)) {
                        bizBaseCustomer.setCustomerName(jsonObject.getString("name1"));
                        bizBaseCustomer.setCustomerAccountGroup(jsonObject.getString(CUSTOMER_ACCOUNT_GROUP));
                        bizBaseCustomer.setArchiveFlag(jsonObject.getString("centralDeletionBlock"));
                        bizBaseCustomer.setCreateTime(date);
                        bizBaseCustomerService.insertBizBaseCustomerByEcc(bizBaseCustomer);
                    } else {
                        for (BizBaseCustomer baseCustomer : bizBaseCustomers) {
                            baseCustomer.setCustomerName(jsonObject.getString("name1"));
                            baseCustomer.setCustomerAccountGroup(jsonObject.getString(CUSTOMER_ACCOUNT_GROUP));
                            baseCustomer.setArchiveFlag(jsonObject.getString("centralDeletionBlock"));
                            baseCustomer.setUpdateTime(date);
                            bizBaseCustomerService.updateBizBaseCustomerByEcc(baseCustomer);
                        }
                    }
                }
            }

            ack.acknowledge();
        }catch (Exception e){
            e.printStackTrace();
            ack.nack(1000);
        }

    }

    //@KafkaListener(containerFactory = KafkaFactory.ECC_LOCAL_KAFKA_FACTORY,topics = {"${kafka.consumer.topics.customer_sales}"} )
    public void customerSalesListener(ConsumerRecord<String,Object> obj, Acknowledgment ack) {
        try {
            String value = obj.value().toString();
            JSONObject jsonObject = JSON.parseObject(value);
            String customerAccountGroup = jsonObject.getString(CUSTOMER_ACCOUNT_GROUP);
            BizBaseSalesOrg org = new BizBaseSalesOrg();
            org.setSalesOrgCode(jsonObject.getString(SALES_ORGANIZATION));
            //过滤掉中国区的customer,条件VKORG = SDMS2.0里的sales org, 其中VKORG是指salesOrganization,
            // customerAccountGroup = 81 or customerAccountGroup = 83
            if (CollectionUtils.isNotEmpty(this.bizBaseSalesOrgService.selectBizBaseSalesOrgList(org)) &&
                    (Objects.equals("81", customerAccountGroup) || Objects.equals("83", customerAccountGroup))) {
                Date date = DateUtils.getNowDate();
                BizBaseCustomer bizBaseCustomer = new BizBaseCustomer();
                bizBaseCustomer.setCustomerNumber(jsonObject.getString("customerNumber"));
                bizBaseCustomer.setStatus("Y");
                List<BizBaseCustomer> bizBaseCustomers = bizBaseCustomerService.selectBizBaseCustomerList(bizBaseCustomer);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date ibmsnapLogmarker = dateFormat.parse(jsonObject.getString("ibmsnapLogmarker"));

                bizBaseCustomer.setDistributionChannel(jsonObject.getString("distributionChannel"));
                bizBaseCustomer.setDivisionCode(jsonObject.getString("division"));
                bizBaseCustomer.setSalesOrgCode(jsonObject.getString(SALES_ORGANIZATION));
                bizBaseCustomer.setClient(jsonObject.getString("client"));
                List<BizBaseCustomer> bizBaseCustomers1 = bizBaseCustomerService.selectBizBaseCustomerList(bizBaseCustomer);
                //暂时根据都是增量数据,且都是新数据来处理,后续修改

                bizBaseCustomer.setSalesOfficeCode(jsonObject.getString(SALES_OFFICE));
                bizBaseCustomer.setBlockFlag(jsonObject.getString(CUSTOMER_ORDER_BLOCK));
                if (CollectionUtils.isEmpty(bizBaseCustomers)) {
                    //之前customerNumber一条数据也没存在,直接插入
                    bizBaseCustomer.setCreateTime(date);
                    bizBaseCustomer.setIbmsnapLogmarker(ibmsnapLogmarker);
                    bizBaseCustomerService.insertBizBaseCustomer(bizBaseCustomer);
                } else {
                    //customerNumber有数据,进一步有没有具体该条数据
                    haveCustomerNumber(bizBaseCustomers, bizBaseCustomers1, bizBaseCustomer, jsonObject, ibmsnapLogmarker, date);
                }
            }

            ack.acknowledge();
        }catch (Exception e){
            e.printStackTrace();
            ack.nack(1000);
        }
    }

    public void haveCustomerNumber(List<BizBaseCustomer> bizBaseCustomers, List<BizBaseCustomer> bizBaseCustomers1, BizBaseCustomer bizBaseCustomer, JSONObject jsonObject, Date ibmsnapLogmarker, Date date) {
        if (CollectionUtils.isEmpty(bizBaseCustomers1)) {
            //没有该条数据,判断是否只存在一条customerNumber,且SalesOffice为空
            if (bizBaseCustomers.size() == 1 && bizBaseCustomers.get(0).getSalesOfficeCode() == null) {
                //SalesOffice为空,更新改条数据
                bizBaseCustomer = bizBaseCustomers.get(0);
                if (bizBaseCustomer.getIbmsnapLogmarker().getTime() < ibmsnapLogmarker.getTime()) {
                    bizBaseCustomer.setDistributionChannel(jsonObject.getString("distributionChannel"));
                    bizBaseCustomer.setDivisionCode(jsonObject.getString("division"));
                    bizBaseCustomer.setSalesOrgCode(jsonObject.getString(SALES_ORGANIZATION));
                    bizBaseCustomer.setClient(jsonObject.getString("client"));
                    bizBaseCustomer.setSalesOfficeCode(jsonObject.getString(SALES_OFFICE));
                    bizBaseCustomer.setBlockFlag(jsonObject.getString(CUSTOMER_ORDER_BLOCK));
                    bizBaseCustomer.setUpdateTime(date);
                    bizBaseCustomer.setIbmsnapLogmarker(ibmsnapLogmarker);
                    bizBaseCustomerService.updateBizBaseCustomerByEcc(bizBaseCustomer);
                }
            } else {
                //bizBaseCustomer加上CustomerName CustomerAccountGroup ArchiveFlag
                if (bizBaseCustomers.get(0).getCustomerName() != null) {
                    bizBaseCustomer.setCustomerName(bizBaseCustomers.get(0).getCustomerName());
                    bizBaseCustomer.setCustomerAccountGroup(bizBaseCustomers.get(0).getCustomerAccountGroup());
                    bizBaseCustomer.setArchiveFlag(bizBaseCustomers.get(0).getArchiveFlag());
                }
                bizBaseCustomer.setIbmsnapLogmarker(ibmsnapLogmarker);
                bizBaseCustomer.setCreateTime(date);
                bizBaseCustomerService.insertBizBaseCustomerByEcc(bizBaseCustomer);
            }
        } else {
            //有该条数据,直接更新
            bizBaseCustomer = bizBaseCustomers1.get(0);
            if (bizBaseCustomer.getIbmsnapLogmarker() == null || bizBaseCustomer.getIbmsnapLogmarker().getTime() < ibmsnapLogmarker.getTime()) {
                bizBaseCustomer.setSalesOfficeCode(jsonObject.getString(SALES_OFFICE));
                bizBaseCustomer.setBlockFlag(jsonObject.getString(CUSTOMER_ORDER_BLOCK));
                bizBaseCustomer.setIbmsnapLogmarker(ibmsnapLogmarker);
                bizBaseCustomerService.updateBizBaseCustomerByEcc(bizBaseCustomer);
            }
        }
    }
}
