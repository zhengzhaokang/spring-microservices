package com.microservices.otmp.notice.controller;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.notice.domain.EmailCondition;
import com.microservices.otmp.notice.domain.EmailDimension;
import com.microservices.otmp.notice.domain.EmailSendHistoryRequest;
import com.microservices.otmp.notice.domain.EmailTemplateEntity;
import com.microservices.otmp.notice.domain.dto.EmailSendDTO;
import com.microservices.otmp.notice.service.EmailSendService;
import com.microservices.otmp.notice.service.EmailTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shirui3
 */
@Api("email")
@RestController
@RequestMapping("emailModule")
public class EmailController extends BaseController{

    @Autowired
    public EmailSendService emailSendService;

    @Autowired
    public EmailTemplateService emailTemplateService;

    @ApiOperation(value = "查询邮件模板列表")
    @PostMapping("getEmailTemplateByPage")
    @HasPermissions("notice:email:view")
    public TableDataInfo getEmailTemplateByPage(@RequestBody EmailTemplateEntity emailTemplateEntity) {
        startPage();
        PageInfo<EmailTemplateEntity> emailTemplateByPage = emailTemplateService.getEmailTemplateByPage(emailTemplateEntity);
        return getDataTable(emailTemplateByPage);
    }

    @ApiOperation(value = "查询下拉框")
    @PostMapping("getConditionOfEmail")
    @HasPermissions("notice:email:view")
    public ResultDTO<Object> getConditionOfEmail(@RequestBody EmailCondition emailCondition)
    {
        try {
            return ResultDTO.success(emailTemplateService.getConditionOfEmail(emailCondition));
        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }

    @ApiOperation(value = "查询邮件模板详情")
    @GetMapping("getEmailTemplateById")
    @HasPermissions("notice:email:view")
    public ResultDTO<List<EmailTemplateEntity>> getEmailTemplateById(@RequestParam("id") Long id) {
        EmailDimension emailCondition = new EmailDimension();
        emailCondition.setId(id);
        return ResultDTO.success(emailTemplateService.getOneEmailTemplate(emailCondition));
    }


    @ApiOperation(value = "更新邮件模板")
    @PostMapping("updateEmailTemplate")
    @HasPermissions("notice:email:edit")
    @OperLog(title = "Update Email Template", businessType = BusinessType.UPDATE)
    public ResultDTO<Integer> updateEmailTemplate(@RequestBody EmailTemplateEntity emailTemplateEntity)
    {
        int updateEmailTemplate = emailTemplateService.updateEmailTemplate(emailTemplateEntity, getLoginName());
        return ResultDTO.success(updateEmailTemplate);
    }

    @ApiOperation(value = "修改是否启用邮件模板")
    @PostMapping("updateEmailTemplateEnable")
    @HasPermissions("notice:email:edit")
    @OperLog(title = "Update Email Template Enable", businessType = BusinessType.UPDATE)
    public ResultDTO<Integer> updateEmailTemplateEnable(@RequestBody EmailTemplateEntity emailTemplateEntity)
    {
        return ResultDTO.success(emailTemplateService.updateEmailTemplateEnable(emailTemplateEntity,getLoginName()));
    }

    @ApiOperation(value = "新增邮件模板")
    @PostMapping("insertEmailTemplate")
    @HasPermissions("notice:email:add")
    @OperLog(title = "Insert Email Template", businessType = BusinessType.INSERT)
    public ResultDTO<Integer> insertEmailTemplate(@RequestBody EmailTemplateEntity emailTemplateEntity)
    {
        return ResultDTO.success(emailTemplateService.insertEmailTemplate(emailTemplateEntity,getLoginName()));
    }

    @ApiOperation(value = "复制邮件模板")
    @PostMapping("copyEmailTemplate")
    @HasPermissions("notice:email:add")
    @OperLog(title = "Copy Email Template", businessType = BusinessType.INSERT)
    public ResultDTO<Integer> copyEmailTemplate(@RequestParam("id") Long id)
    {
        return ResultDTO.success(emailTemplateService.copyEmailTemplate(id,getLoginName()));
    }

    /**
     */
    @OperLog(title = "send email", businessType = BusinessType.INSERT)
    @PostMapping("/sendEmail")
    public ResultDTO<Object> sendEmail(@RequestBody EmailSendDTO sendDTO) {
        try {
            int res = emailSendService.joinParameters(sendDTO);
            if(res==1){
                return ResultDTO.success();
            }else{
                return ResultDTO.fail();
            }

        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }

    @ApiOperation(value = "查询邮件发送历史列表")
    @PostMapping("searchSendHistoryList")
    @HasPermissions("notice:email:view")
    public TableDataInfo searchSendHistoryList(@RequestBody EmailSendHistoryRequest requestEmailSendVO)
    {
        startPage();
        PageInfo<EmailSendHistoryRequest> emailSendHistoryList = emailSendService.getEmailSendHistoryList(requestEmailSendVO);
        return getDataTable(emailSendHistoryList);
    }

    @ApiOperation(value = "查询发送历史详情")
    @GetMapping("getSendHistoryById")
    @HasPermissions("notice:email:view")
    public ResultDTO<List<EmailSendHistoryRequest>> getSendHistoryById(@RequestParam("id") String id) {
        return ResultDTO.success(emailSendService.getSendHistoryById(id));
    }

    @ApiOperation(value = "重发发送失败的邮件")
    @PostMapping("checkSendStatus")
    public ResultDTO<Object> checkSendStatus(@RequestBody EmailSendHistoryRequest emailSendHistoryRequest) {
        try {
            emailSendService.checkSendStatus(emailSendHistoryRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(e.getMessage());
        }
        return ResultDTO.success();
    }

    @PostMapping("getFailSendEmail")
    public ResultDTO<Object> getFailSendEmail(EmailSendHistoryRequest requestEmailSendVO)
    {
        try {
            startPage();
            List<EmailSendHistoryRequest> failSendEmail = emailSendService.getFailSendEmail(requestEmailSendVO);
            return ResultDTO.success(failSendEmail);
        } catch (Exception e) {
            return ResultDTO.fail(e.getMessage());
        }
    }





    @PostMapping("/asynSendEmail")
    public void asynSendEmail(@RequestBody EmailSendDTO emailSendDTO) {
        emailSendService.sendkafka(emailSendDTO);
    }
}
