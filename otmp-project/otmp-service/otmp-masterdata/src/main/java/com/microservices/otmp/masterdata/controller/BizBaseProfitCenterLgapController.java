package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.masterdata.service.IBizBaseProfitCenterLgapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProfitCenter Table from LGAPController
 *
 * @author lovefamily
 * @date 2023-07-01
 */
@RestController
@RequestMapping("/bizBaseProfitCenterLgap")
public class BizBaseProfitCenterLgapController extends BaseController {
    @Autowired
    private IBizBaseProfitCenterLgapService bizBaseProfitCenterLgapService;

    /**
     * 查询ProfitCenter Table from LGAP列表
     */
    @GetMapping("/check/{profitCenterCode}")
    public ResultDTO<Object> list(@PathVariable("profitCenterCode") String profitCenterCode) {
        return bizBaseProfitCenterLgapService.checkProfitCenterCode(profitCenterCode);
    }


}
