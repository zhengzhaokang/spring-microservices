package com.microservices.otmp.system.service;

import com.alibaba.fastjson.JSON;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.text.UUID;
import com.microservices.otmp.common.kafka.listener.KafkaCallback;
import com.microservices.otmp.common.kafka.util.KafkaSend;
import com.microservices.otmp.notice.domain.dto.EmailSendDTO;
import com.microservices.otmp.system.common.EmailConstant;
import com.microservices.otmp.system.common.KafkaFactory;
import com.microservices.otmp.system.domain.RegisterResult;
import com.microservices.otmp.system.domain.SysRole;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.domain.email.EmailContentParam;
import com.microservices.otmp.system.domain.email.EmailTitleParam;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmailBusinessService {

    protected final Logger logger = LoggerFactory.getLogger(EmailBusinessService.class);

    @Autowired
    private ISysRoleService sysRoleService;

    @Value("${kafka.topic.send-email}")
    private String sendEmailTopic;

    @Autowired
    private KafkaSend kafkaSend;

    public void addUserSendEmail(SysUser sysUser, RegisterResult registerResult, String createBy) {
        logger.info("###EmailBusinessService addUserSendEmail registerResult is {}", JSON.toJSONString(registerResult));
        if (StringUtils.isNotBlank(registerResult.getInitUrl())) {
            // 发送邮件给username -> 用户邮箱  todo sysUser.getLoginName(), registerResult.getInitUrl()
            EmailSendDTO emailSendDTO = getEmailSendDTO(sysUser, registerResult,
                    EmailConstant.EMAIL_TYPE_USER_NEW, createBy);
            kafkaSend.asynSend(sendEmailTopic, java.util.UUID.randomUUID().toString(), JSON.toJSONString(emailSendDTO), KafkaFactory.MSG_KAFKA_PRODUCER_FACTORY, new KafkaCallback(sendEmailTopic), false);
        } else if (StringUtils.isBlank(registerResult.getInitUrl()) && StringUtils.equals(registerResult.getCode(), EmailConstant.CODE_104))  {
            EmailSendDTO emailSendDTO = getEmailSendDTO(sysUser, registerResult,
                    EmailConstant.EMAIL_TYPE_USER_OLD, createBy);
            kafkaSend.asynSend(sendEmailTopic, java.util.UUID.randomUUID().toString(), JSON.toJSONString(emailSendDTO), KafkaFactory.MSG_KAFKA_PRODUCER_FACTORY, new KafkaCallback(sendEmailTopic), false);
        } else {
            logger.warn("###EmailBusinessService addUserSendEmail warn registerResult is {}", JSON.toJSONString(registerResult));
        }
    }

    public EmailSendDTO getEmailSendDTO(SysUser sysUser, RegisterResult registerResult, String emailType,String createBy) {

        EmailSendDTO emailSendDTO = new EmailSendDTO();
        emailSendDTO.setTraceId(UUID.randomUUID().toString());
        emailSendDTO.setCreateBy(createBy);
        emailSendDTO.setMarket(EmailConstant.MARK_USER);
        emailSendDTO.setMailFrom(createBy);
        List<String> mailToList = new ArrayList<>();
        mailToList.add(sysUser.getLoginName());
        emailSendDTO.setSendTo(mailToList);
        emailSendDTO.setModule(EmailConstant.MODULE_MANAGEMENT);
        emailSendDTO.setBusinessType(EmailConstant.BUSINESS_TYPE_USER);
        emailSendDTO.setEmailType(emailType);
        emailSendDTO.setGeoCode(EmailConstant.GEO_CODE_COM);
        emailSendDTO.setBusinessGroup(EmailConstant.BUSINESS_GROUP_COM);
        EmailTitleParam titleParam = EmailTitleParam.builder().username(sysUser.getLoginName()).build();
        String titleParamStr = JSON.toJSONString(titleParam);
        emailSendDTO.setMailTitleBean(titleParamStr);

        List<SysRole> sysRoles = sysRoleService.selectRolesByUserId(sysUser.getUserId());
        String role = "";
        if(CollectionUtils.isNotEmpty(sysRoles) && sysRoles.get(0) != null) {
            role = sysRoles.get(0).getRoleName();
        }
        EmailContentParam emailContentParam = null;
        if (StringUtils.isNotBlank(registerResult.getInitUrl())) {
            String initUrl = registerResult.getInitUrl().replaceAll("&", "&amp;");
            emailContentParam = EmailContentParam.builder().
                    email(sysUser.getLoginName()).initUrl(initUrl).role(role).build();
        } else {
            emailContentParam = EmailContentParam.builder().email(sysUser.getLoginName()).role(role).build();
        }

        String emailContentParamStr = JSON.toJSONString(emailContentParam);
        emailSendDTO.setBean(emailContentParamStr);
        return emailSendDTO;
    }
}
