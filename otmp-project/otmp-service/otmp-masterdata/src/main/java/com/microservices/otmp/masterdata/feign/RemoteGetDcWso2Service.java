package com.microservices.otmp.masterdata.feign;

import com.alibaba.fastjson.JSONArray;
import com.microservices.otmp.masterdata.common.Response;
import com.microservices.otmp.masterdata.domain.entity.dto.SalesOrgDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author xiaozy8
 */
@FeignClient(name = "lpc", url = "${wso2.maps.fi.url}/uat/v1.0/supply_chain/")
public interface RemoteGetDcWso2Service {

    @PostMapping("/osp_organization_search")
    Response<JSONArray> getDc(@RequestBody SalesOrgDTO salesOrgDTO, @RequestHeader("Authorization")String token);
}
