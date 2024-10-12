package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownList;
import com.microservices.otmp.masterdata.service.IBizBaseDropDownService;
import com.microservices.otmp.masterdata.service.IBizBaseRegionMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * bizBaseRegionMarketController
 * 此文件 之后会弃用
 * @author lovefamily
 * @date 2022-04-18
 */
@RestController
@RequestMapping("/dropDown")
public class BizBaseDropDownController extends BaseController
{
    @Autowired
    private IBizBaseDropDownService bizBaseDropDownService;

    @Autowired
    private IBizBaseRegionMarketService bizBaseRegionMarketService;

    @GetMapping("/getBptype")
    public ResultDTO<Map<String,List<BizBaseDropDownList>>> getBptype(BizBaseDropDownCondition bd)
    {
        List<BizBaseDropDownList> bpType = bizBaseDropDownService.getBptype(bd);
        Map<String,List<BizBaseDropDownList>> result= new HashMap<>();
        result.put("option",bpType);
        return ResultDTO.success(result);
    }

}
