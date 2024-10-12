package com.microservices.otmp.financing.controller;

import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.financing.domain.param.supplier.RegisterParam;
import com.microservices.otmp.financing.domain.vo.supplier.InviteLinkVo;
import com.microservices.otmp.financing.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/supplier/onboarding")
@RestController
public class SupplierOnboardingController extends BaseController {

    @Autowired
    private SupplierService supplierService;

    /**
     * 根据邀请code获取公司名称等信息，用于页面展示
     *
     * @param inventionCode 邀请code
     * @return 查询结果
     */
//    @OperLog(title = "查询公司名称", businessType = BusinessType.QUERY)
    @GetMapping("/company/name")
    public ResultDTO<InviteLinkVo> companyName(@RequestParam("inventionCode")String inventionCode){
        InviteLinkVo inviteLinkVo = supplierService.companyName(inventionCode);
        return ResultDTO.success(inviteLinkVo);
    }

    /**
     * supplier注册
     *
     * @param param 注册参数
     * @return 执行结果
     */
    @OperLog(title = "供应商注册", businessType = BusinessType.UPDATE)
    @PostMapping("/register")
    public ResultDTO<Boolean> register(@Validated @RequestBody RegisterParam param) {
        param.setEmail(param.getEmail().toLowerCase());
        return supplierService.register(param);
    }

}
