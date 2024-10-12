package com.microservices.otmp.masterdata.controller;

import com.alibaba.fastjson.JSONObject;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.utils.wso2.Wso2ModuleType;
import com.microservices.otmp.common.utils.wso2.Wso2Util;
import com.microservices.otmp.masterdata.common.Response;
import com.microservices.otmp.masterdata.domain.entity.dto.PlantCategoryDTO;
import com.microservices.otmp.masterdata.feign.RemotePlantCategoryWso2Service;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.utils.wso2.Wso2ModuleType;
import com.microservices.otmp.common.utils.wso2.Wso2Util;
import com.microservices.otmp.masterdata.common.Response;
import com.microservices.otmp.masterdata.feign.RemotePlantCategoryWso2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plant/category")
public class PlantCategoryController extends BaseController {
    @Autowired
    private RemotePlantCategoryWso2Service remotePlantCategoryWso2Service;
    @Autowired
    private Wso2Util wso2Util;


    //@RequiresPermissions("masterdata:integrate :list")
    @PostMapping("/getPlantCate")
    public ResultDTO<Object> getPlantCate(@RequestBody PlantCategoryDTO plantCategoryDTO) {
        String token = wso2Util.getToken(Wso2ModuleType.lpc.name());
        Response<JSONObject> response = remotePlantCategoryWso2Service.getPlantCategorByCallLpc(plantCategoryDTO, token);
        if (null == response || !"200".equals(response.getCode())) {
            return ResultDTO.fail("not exist");
        }
        return ResultDTO.success(response.getData());
    }
}
