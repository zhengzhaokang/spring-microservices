package com.microservices.otmp.financing.controller;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.constant.StateConstants;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.financing.domain.param.IdParam;
import com.microservices.otmp.financing.domain.param.bank.AddBankParam;
import com.microservices.otmp.financing.domain.param.bank.BankListParam;
import com.microservices.otmp.financing.domain.param.bank.EditBankParam;
import com.microservices.otmp.financing.domain.vo.bank.BankVo;
import com.microservices.otmp.financing.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/anchor/bank")
@RestController
public class AnchorBankController extends BaseController {

    @Autowired
    private BankService bankService;

    @OperLog(title = "添加银行", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public ResultDTO<Boolean> add(@Validated @RequestBody AddBankParam param) {
        String loginName = getLoginName();
        param.setCreateBy(loginName);
        Boolean result = bankService.add(param);
        return ResultDTO.success(result);
    }

    //    @OperLog(title = "查询银行信息", businessType = BusinessType.QUERY)
    @GetMapping("/info")
    public ResultDTO<BankVo> info(@RequestParam("id") Long id) {
        BankVo info = bankService.info(id);
        return ResultDTO.success(info);
    }

    @OperLog(title = "编辑银行信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public ResultDTO<Boolean> edit(@Validated @RequestBody EditBankParam param) {
        String loginName = getLoginName();
        param.setUpdateBy(loginName);
        Boolean result = bankService.update(param);
        return ResultDTO.success(result);
    }

    @OperLog(title = "禁用银行", businessType = BusinessType.UPDATE)
    @PostMapping("/suspend")
    public ResultDTO<Boolean> suspend(@Validated @RequestBody IdParam param) {
        String loginName = getLoginName();
        param.setUpdateBy(loginName);
        Boolean result = bankService.changeStatus(param.getId(), StateConstants.FLAG_DELETED_STR);
        return ResultDTO.success(result);
    }

    @OperLog(title = "激活银行", businessType = BusinessType.UPDATE)
    @PostMapping("/active")
    public ResultDTO<Boolean> active(@Validated @RequestBody IdParam param) {
        String loginName = getLoginName();
        param.setUpdateBy(loginName);
        Boolean result = bankService.changeStatus(param.getId(), StateConstants.FLAG_NORMAL_STR);
        return ResultDTO.success(result);
    }

    //    @OperLog(title = "查询银行列表", businessType = BusinessType.QUERY)
    @PostMapping("/list")
    public ResultDTO<PageInfo<BankVo>> list(@Validated @RequestBody BankListParam param) {
        param.setUserId(getCurrentUserId());
        PageInfo<BankVo> list = bankService.list(param);
        return ResultDTO.success(list);
    }

}
