package com.microservices.otmp.erp.service;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public interface ICallWso2Service {

    String getToken() throws Exception;

    /**
     * 调用Wso2 Post接口
     * @param requestBody 请求参数Body
     * @param requestUrl 请求URL
     * @return
     */
    JSONObject callWso2Post(String requestBody, String requestUrl);
}
