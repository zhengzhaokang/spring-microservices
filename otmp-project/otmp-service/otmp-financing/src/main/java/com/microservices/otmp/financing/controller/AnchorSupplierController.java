package com.microservices.otmp.financing.controller;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.financing.domain.entity.SupplierDo;
import com.microservices.otmp.financing.domain.param.IdParam;
import com.microservices.otmp.financing.domain.param.supplier.*;
import com.microservices.otmp.financing.domain.vo.supplier.*;
import com.microservices.otmp.financing.domain.param.supplier.*;
import com.microservices.otmp.financing.domain.vo.supplier.*;
import com.microservices.otmp.financing.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequestMapping("/anchor/supplier")
@RestController
public class AnchorSupplierController extends BaseController {

    @Autowired
    private SupplierService supplierService;
    private static final int CODE_LEN = 10;

    /**
     * 发送邀请邮件
     *
     * @param param 邮件内容参数
     * @return 发送结果
     */
    @OperLog(title = "邀请供应商", businessType = BusinessType.INSERT)
    @PostMapping("/invite")
    public ResultDTO<String> invite(@Validated @RequestBody MailParam param) {
        String loginName = getLoginName();
        param.setCreateBy(loginName);
        param.setUpdateBy(loginName);
        // 统一转为小写
        param.setEmail(param.getEmail().toLowerCase());
        String result = supplierService.invite(param);
        return ResultDTO.success(result);
    }

    @OperLog(title = "删除供应商", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public ResultDTO<Boolean> delete(@Validated @RequestBody IdParam param) {
        String loginName = getLoginName();
        boolean result = supplierService.deleteInvite(param.getId(), loginName);
        return ResultDTO.success(result);
    }

    @PostMapping("/list/awaiting")
    public ResultDTO<PageInfo<SupplierVo>> awaitingList(@Validated @RequestBody AwaitingSupplierListParam param) {
        PageInfo<SupplierVo> result = supplierService.list(param, SupplierDo.STATUS_AWAITING);
        return ResultDTO.success(result);
    }

    @PostMapping("/list/pending")
    public ResultDTO<PageInfo<SupplierVo>> pendingList(@Validated @RequestBody PendingSupplierListParam param) {
        PageInfo<SupplierVo> result = supplierService.list(param, SupplierDo.STATUS_PENDING);
        return ResultDTO.success(result);
    }

    @PostMapping("/list/active")
    public ResultDTO<PageInfo<SupplierVo>> activeList(@Validated @RequestBody ActiveSupplierListParam param) {
        param.setUserId(getCurrentUserId());
        PageInfo<SupplierVo> result = supplierService.list(param, SupplierDo.STATUS_ACTIVE);
        return ResultDTO.success(result);
    }

    @PostMapping("/list/suspend")
    public ResultDTO<PageInfo<SupplierVo>> suspendList(@Validated @RequestBody SuspendSupplierListParam param) {
        PageInfo<SupplierVo> result = supplierService.list(param, SupplierDo.STATUS_SUSPEND);
        return ResultDTO.success(result);
    }

    @GetMapping("/info/simple")
    public ResultDTO<SupplierSimpleVo> simple(@RequestParam("id") Long id) {
        log.info("simple,id:{}", id);
        SupplierSimpleVo info = supplierService.simple(id);
        return ResultDTO.success(info);
    }

    @GetMapping("/info/detail")
    public ResultDTO<SupplierDetailVo> detail(@RequestParam("id") Long id) {
        SupplierDetailVo info = supplierService.detail(id);
        return ResultDTO.success(info);
    }

    /**
     * 根据erp code查询供应商公司信息
     * @param code   查询条件
     * @return 结果
     */
    @GetMapping("/erp/info")
    public ResultDTO<List<SupplierInformationVo>> information(@RequestParam("code") String code) {
        if (code.length() < CODE_LEN) {
            code = String.format("%" + CODE_LEN + "s", code).replace(" ", "0");
        }
        log.info("information,code:{}", code);
        List<SupplierInformationVo> informationVoList = supplierService.info(code);
        return ResultDTO.success(informationVoList);
    }

    @GetMapping("/available/currency")
    public ResultDTO<List<String>> availableCurrency(@RequestParam("bankId") Long bankId,@RequestParam("entityId") Long entityId){
        List<String> strings = supplierService.availableCurrency(bankId, entityId);
        return ResultDTO.success(strings);
    }

    @GetMapping("/bank/entity/rel")
    public ResultDTO<List<EntityBankPairVo>> entityBankRelationList(){
        List<EntityBankPairVo> result = supplierService.entityBankRelation();
        return ResultDTO.success(result);
    }

    /**
     * 激活supplier
     * @param param 参数
     * @return 执行结果
     */
    @OperLog(title = "激活供应商", businessType = BusinessType.UPDATE)
    @PostMapping("/active")
    public ResultDTO<Boolean> active(@Validated @RequestBody ActiveSupplierParam param) {
        log.info("active,param:{}", JsonUtil.toJSON(param));
        String loginName = getLoginName();
        param.setCreateBy(loginName);
        param.setUpdateBy(loginName);
        param.setEmail(param.getEmail().toLowerCase());
        boolean result = supplierService.active(param);
        return handleResultData(result);
    }

    @OperLog(title = "编辑供应商信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public ResultDTO<Boolean> edit(@Validated @RequestBody EditSupplierParam param) {
        String loginName = getLoginName();
        param.setUpdateBy(loginName);
        param.setUpdateBy(loginName);
        param.setEmail(param.getEmail().toLowerCase());
        boolean result = supplierService.edit(param);
        return ResultDTO.success(result);
    }

    @OperLog(title = "禁用供应商", businessType = BusinessType.UPDATE)
    @PostMapping("/operate/suspend")
    public ResultDTO<Boolean> suspend(@Validated @RequestBody IdParam param) {
        String loginName = getLoginName();
        param.setUpdateBy(loginName);
        Boolean result = supplierService.updateStatus(param.getId(), SupplierDo.STATUS_SUSPEND);
        log.info("suspend,result:{}",result);
        return result
                ? ResultDTO.success(result)
                : ResultDTO.fail(DefaultErrorMessage.SUPPLIER_UPDATE_FAILED.intValue(), DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.SUPPLIER_UPDATE_FAILED));
    }

    @OperLog(title = "重新启用供应商", businessType = BusinessType.UPDATE)
    @PostMapping("/operate/reactive")
    public ResultDTO<Boolean> reactive(@Validated @RequestBody IdParam param) {
        String loginName = getLoginName();
        param.setUpdateBy(loginName);
        Boolean result = supplierService.updateStatus(param.getId(), SupplierDo.STATUS_ACTIVE);
        return result
                ? ResultDTO.success(result)
                : ResultDTO.fail(DefaultErrorMessage.SUPPLIER_UPDATE_FAILED.intValue(), DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.SUPPLIER_UPDATE_FAILED));
    }

    @OperLog(title = "设置供应商为onHold状态", businessType = BusinessType.UPDATE)
    @PostMapping("/operate/onHold")
    public ResultDTO<Boolean> onHold(@Validated @RequestBody OnHoldParam param) {
        boolean result = supplierService.onHold(param);
        return ResultDTO.success(result);
    }

    @OperLog(title = "取消供应商的onHold状态", businessType = BusinessType.UPDATE)
    @PostMapping("/operate/onHold/cancel")
    public ResultDTO<Boolean> cancelOnHold(@Validated @RequestBody IdParam param) {
        boolean result = supplierService.cancelOnHold(param);
        return ResultDTO.success(result);
    }

    @GetMapping("/operate/onHold/status")
    public ResultDTO<Boolean> setOnHoldStatus() {
        supplierService.doOnHold();
        return ResultDTO.success(true);
    }

    @GetMapping("/operate/onHold/cancel/status")
    public ResultDTO<Boolean> cancelOnHoldStatus() {
        supplierService.doCancelOnHold();
        return ResultDTO.success(true);
    }

}
