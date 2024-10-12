package com.microservices.otmp.masterdata.feign;

import com.alibaba.fastjson.JSONObject;
import com.microservices.otmp.masterdata.common.Response;
import com.microservices.otmp.masterdata.domain.entity.dto.SalesOrgDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "lpc", url = "${wso2.maps.fi.url}/uat/v1.0/supply_chain/")
public interface RemoteCompanyCodeWso2Service {

    @PostMapping("/osp_organization_search")
    Response<JSONObject> getCompanyCodeBySalesOrg(@RequestBody SalesOrgDTO salesOrgDTO, @RequestHeader("Authorization")String token);
}
