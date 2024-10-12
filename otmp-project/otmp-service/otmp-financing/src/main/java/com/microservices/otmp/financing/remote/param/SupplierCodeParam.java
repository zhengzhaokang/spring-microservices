package com.microservices.otmp.financing.remote.param;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class SupplierCodeParam {

    private RemoteRequest request;

    public void setHeaderParam(Map<String,Object> param){
        if(request == null){
            request = new RemoteRequest();
        }
        Map<String, Object> header = request.getHeader();
        if(header == null){
            header = new HashMap<>(param);
            request.setHeader(header);
        }else{
            header.putAll(param);
        }
    }

    @Data
    public static class RemoteRequest{
        private Map<String,Object> header;
    }
}
