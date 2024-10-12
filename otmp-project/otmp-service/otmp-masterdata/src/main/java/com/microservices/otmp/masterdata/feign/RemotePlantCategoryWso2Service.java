package com.microservices.otmp.masterdata.feign;

import com.alibaba.fastjson.JSONObject;
import com.microservices.otmp.masterdata.common.Response;
import com.microservices.otmp.masterdata.domain.entity.dto.PlantCategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "lpc", url = "${wso2.maps.lpc.url}/sit/v1.0/sales/lpc/product/global/")
public interface RemotePlantCategoryWso2Service {

    /**
     * 通过lpc查询plantCategory
     * @param plantCategoryDTO
     * @param token
     * @return
     */
    @PostMapping("/plant_category")
    Response<JSONObject> getPlantCategorByCallLpc(@RequestBody PlantCategoryDTO plantCategoryDTO, @RequestHeader("Authorization") String token);
}
