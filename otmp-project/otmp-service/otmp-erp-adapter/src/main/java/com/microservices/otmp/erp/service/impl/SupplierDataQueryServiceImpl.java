package com.microservices.otmp.erp.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.erp.domain.S4ApiItem;
import com.microservices.otmp.erp.domain.SupplierInvoiceInfo;
import com.microservices.otmp.erp.domain.request.SupplierFinancingActivationRequest;
import com.microservices.otmp.erp.mapper.SupplierInvoiceInfoMapper;
import com.microservices.otmp.erp.service.ICallWso2Service;
import com.microservices.otmp.erp.service.ISupplierDataQueryService;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SupplierDataQueryServiceImpl implements ISupplierDataQueryService {

    @Value("${supplier.data.url}")
    String supplierDataUrl;
    @Value("${supplier.data.sync.url}")
    String supplierDataSyncUrl;
    @Value("${supplier.financing.activation.url}")
    String financingActivationUrl;

    @Autowired
    private ICallWso2Service wso2Service;
    @Autowired
    private SupplierInvoiceInfoMapper supplierInvoiceInfoMapper;

    @Override
    public List<SupplierInvoiceInfo> getSupplierData(Map<String, Object> map) {
        List<SupplierInvoiceInfo> suppliers = null;
        String jaString = JSONObject.toJSONString(map);
        JSONObject obj = wso2Service.callWso2Post(jaString, supplierDataUrl);
        if (null != obj && null != obj.getJSONObject("response") && null != obj.getJSONObject("response").getJSONArray("item")) {
            JSONArray array = obj.getJSONObject("response").getJSONArray("item");
            if(null != array) {
                suppliers = array.toJavaList(SupplierInvoiceInfo.class);
            }
        }
        if(null != suppliers && !suppliers.isEmpty()){
            Date date = new Date();
            suppliers.stream().forEach(s ->{
                s.setCrateTime(date);
            });
        }
        return suppliers;
    }

    @Override
    public Object supplierDataSync(HashMap<String, Object> map) {
        String jaString = JSONObject.toJSONString(map);
        return wso2Service.callWso2Post(jaString, supplierDataSyncUrl);
    }

    @Override
    public Object s4ApiCallBack(List<S4ApiItem> s4ApiItems) {
        if(null != s4ApiItems && s4ApiItems.size() >0){
            s4ApiItems = s4ApiItems.stream().filter(item -> !StringUtils.equalsIgnoreCase(item.getInvoiceCount(),"0")
                    || !StringUtils.equalsIgnoreCase(item.getCreditNoteCount(),"0")).collect(Collectors.toList());
            s4ApiItems.forEach(item -> {
                item.setId(SnowFlakeUtil.nextId());
            });
            supplierInvoiceInfoMapper.saveBatchItem(s4ApiItems);
        }
        return "success";
    }

    @Override
    public List<S4ApiItem> searchS4ApiItem() {
        return supplierInvoiceInfoMapper.searchS4ApiItem();
    }

    @Override
    public void saveSupplierData(List<SupplierInvoiceInfo> suppliers) {
        if (null != suppliers && suppliers.size() > 0) {
            suppliers.forEach(supplier -> {
                supplier.setId(SnowFlakeUtil.nextId());
            });
            supplierInvoiceInfoMapper.insertBatch(suppliers);
        }
    }

    @Override
    public void saveFinalSupplierData(List<SupplierInvoiceInfo> suppliers) {
        if (null != suppliers && suppliers.size() > 0) {
            suppliers.forEach(supplier -> {
                supplier.setId(SnowFlakeUtil.nextId());
            });
            supplierInvoiceInfoMapper.insertFinalBatch(suppliers);
        }
    }

    @Override
    public Object supplierFinancingActivation(List<SupplierFinancingActivationRequest> request) {
        String jaString =JSONObject.toJSONString(request);
        return wso2Service.callWso2Post(jaString, financingActivationUrl);
    }

    @Override
    public void updateFeedStatus(List<S4ApiItem> updateFeeds) {
        if(!updateFeeds.isEmpty()){
            supplierInvoiceInfoMapper.updateFeedStatus(updateFeeds);
        }
    }
}
