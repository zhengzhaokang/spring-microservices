package com.microservices.otmp.erp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.microservices.otmp.erp.domain.SupplierInvoiceFeedbackInfoVo;
import com.microservices.otmp.erp.service.ICallWso2Service;
import com.microservices.otmp.erp.service.ISupplierDataBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SupplierDataBackServiceImpl implements ISupplierDataBackService {

    @Autowired
    private ICallWso2Service wso2Service;
    @Value("${supplier.data.callBack.url}")
    String supplierCallBackUrl;

    @Override
    @Async
    public void callBack(List<SupplierInvoiceFeedbackInfoVo> backData, String type) {
        if(null == backData || backData.isEmpty()) {
            return;
        }
        Map<String, Object> map = Maps.newHashMap();
        Map<String, Object> request = Maps.newHashMap();
        request.put("header", backData.size());
        request.put("type", type);
        request.put("item", backData);
        map.put("request",request);
        String jaString = JSONObject.toJSONString(map);
        wso2Service.callWso2Post(jaString, supplierCallBackUrl);
    }
}
