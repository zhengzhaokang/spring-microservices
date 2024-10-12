package com.microservices.otmp.financing.util;

import com.alibaba.fastjson.JSON;
import com.microservices.otmp.common.kafka.listener.KafkaCallback;
import com.microservices.otmp.common.kafka.util.KafkaSend;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.financing.config.KafkaFactory;
import com.microservices.otmp.financing.constant.MailConstant;
import com.microservices.otmp.notice.domain.dto.EmailSendDTO;
import com.microservices.otmp.notice.feign.RemoteEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class SendMailUtil {

    public static final String PARAM_SUPPLIER_NAME = "Supplier Name";
    public static final String PARAM_PWD_LINK = "Password Setup Link";

    @Autowired
    private RemoteEmailService remoteEmailService;

    @Autowired
    private KafkaSend sender;

    @Value("${kafka.topic.send-email}")
    private String sendEmailTopic;

    private EmailSendDTO createMail(String createBy, String sendFrom, List<String> sendTo, List<String> sendCc, MailTitleBean titleBean, MailContentBean contentBean) {
        EmailSendDTO mail = new EmailSendDTO();
        mail.setTraceId(UUID.randomUUID().toString());
        mail.setCreateBy(createBy);
        mail.setMarket(null);
        mail.setMailFrom(sendFrom);
        mail.setSendTo(sendTo);
        mail.setSendCc(sendCc);
        mail.setBean(contentBean == null ? null : JsonUtil.toJSON(contentBean));
        mail.setMailTitleBean(titleBean == null ? null : JsonUtil.toJSON(titleBean));
        mail.setGeoCode(MailConstant.GEO_CODE_ALL);
        mail.setBusinessGroup(MailConstant.BUSINESS_GROUP_ALL);
        mail.setModule(MailConstant.MAIL_MODULE_AP_FINANCING);
        return mail;
    }

    /**
     * 注册邮件，给supplier
     * @param createBy 邮件创建者
     * @param sendFrom 邮件发送者
     * @param sendTo 邮件接收人
     * @param sendCc 邮件抄送人
     * @param titleBean 标题
     * @param contentBean 内容
     */
    public boolean sendRegMailToSupplier(String createBy, String sendFrom, List<String> sendTo, List<String> sendCc, MailTitleBean titleBean, MailContentBean contentBean) {
        EmailSendDTO mail = createMail(createBy, sendFrom, sendTo, sendCc, titleBean, contentBean);
        mail.setBusinessType(MailConstant.BUSINESS_TYPE_SUPPLIER);
        mail.setEmailType(MailConstant.MAIL_TYPE_REG_SUPPLIER);
        return sendMail(mail);
    }

    /**
     * 注册邮件，给anchor
     * @param createBy 邮件创建者
     * @param sendFrom 邮件发送者
     * @param sendTo 邮件接收人
     * @param sendCc 邮件抄送人
     * @param titleBean 标题
     * @param contentBean 内容
     */
    public boolean sendRegMailToAnchor(String createBy, String sendFrom, List<String> sendTo, List<String> sendCc, MailTitleBean titleBean, MailContentBean contentBean) {
        EmailSendDTO mail = createMail(createBy, sendFrom, sendTo, sendCc, titleBean, contentBean);
        mail.setBusinessType(MailConstant.BUSINESS_TYPE_SUPPLIER);
        mail.setEmailType(MailConstant.MAIL_TYPE_REG_ANCHOR);
        return sendMail(mail);
    }

    /**
     * 邀请邮件
     * @param createBy 邮件创建者
     * @param sendFrom 邮件发送者
     * @param sendTo 邮件接收人
     * @param sendCc 邮件抄送人
     * @param titleBean 标题
     * @param contentBean 内容
     */
    public boolean sendInviteMail(String createBy, String inviteCode, String sendFrom, List<String> sendTo, List<String> sendCc, MailTitleBean titleBean, MailContentBean contentBean) {
        contentBean.put("code", inviteCode);
        EmailSendDTO mail = createMail(createBy, sendFrom, sendTo, sendCc, titleBean, contentBean);
        mail.setBusinessType(MailConstant.BUSINESS_TYPE_ANCHOR);
        mail.setEmailType(MailConstant.MAIL_TYPE_INVITE_SUPPLIER);
        return sendMail(mail);
    }

    public boolean sendInviteMail(String sendFrom, String inviteCode, String sendTo, String titleParam, String contentParam) {
        return this.sendInviteMail(sendFrom, inviteCode, sendFrom, Collections.singletonList(sendTo), null, new MailTitleBean(titleParam), new MailContentBean(contentParam));
    }

    /**
     * Supplier激活邮件
     * @param createBy 邮件创建者
     * @param sendFrom 邮件发送者
     * @param sendTo 邮件接收人
     * @param sendCc 邮件抄送人
     * @param titleBean 标题
     * @param contentBean 内容
     */
    public boolean sendActiveSupplier(String createBy, String sendFrom, List<String> sendTo, List<String> sendCc, MailTitleBean titleBean, MailContentBean contentBean) {
        EmailSendDTO mail = createMail(createBy, sendFrom, sendTo, sendCc, titleBean, contentBean);
        mail.setBusinessType(MailConstant.BUSINESS_TYPE_ANCHOR);
        mail.setEmailType(MailConstant.MAIL_TYPE_ACTIVE_SUPPLIER);
        return sendMail(mail);
    }

    public boolean sendActiveSupplier(String createBy, String sendFrom, String sendTo, String titleParam, String contentParam) {
        return sendActiveSupplier(createBy, sendFrom, Collections.singletonList(sendTo), null, new MailTitleBean(titleParam), new MailContentBean(contentParam));
    }

    /**
     * Supplier激活邮件（带重置密码链接）
     */
    public boolean sendActiveSupplierWithLink(String createBy, String sendFrom, String sendTo, String titleParam, String contentParam,String pwdSetupLink) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put(PARAM_SUPPLIER_NAME,contentParam);
        paramMap.put(PARAM_PWD_LINK,pwdSetupLink);
        MailContentBean contentBean = new MailContentBean(paramMap);
        EmailSendDTO mail = createMail(createBy, sendFrom, Collections.singletonList(sendTo), null, new MailTitleBean(titleParam), contentBean);
        mail.setBusinessType(MailConstant.BUSINESS_TYPE_ANCHOR);
        mail.setEmailType(MailConstant.MAIL_TYPE_ACTIVE_SUPPLIER_LINK);
        return sendMail(mail);
    }

    public boolean sendSubmitMail(String createBy, String sendFrom, List<String> sendTo, List<String> sendCc, MailTitleBean titleBean, MailContentBean contentBean) {
        EmailSendDTO mail = createMail(createBy, sendFrom, sendTo, sendCc, titleBean, contentBean);
        mail.setBusinessType(MailConstant.BUSINESS_TYPE_SUPPLIER);
        mail.setEmailType(MailConstant.MAIL_TYPE_FINANCING);
        return sendMail(mail);
    }

    public boolean sendMail(EmailSendDTO mail) {
        log.info("sendMail,mail content:{}", JsonUtil.toJSON(mail));
//        ResultDTO<Object> sendResult = remoteEmailService.sendEmail(mail);
        String emailSendDTOStr = JSON.toJSONString(mail);
        sender.asynSend(sendEmailTopic, UUID.randomUUID().toString(), emailSendDTOStr, KafkaFactory.MSG_KAFKA_PRODUCE_FACTORY, new KafkaCallback(sendEmailTopic), false);
//        if (sendResult == null) {
//            log.error("sendMail,sendResult is null,mail content:{}", JsonUtil.toJSON(mail));
//            return false;
//        }
//        if (!sendResult.isSuccess()) {
//            log.error("sendMail,send failed,msg:{}", sendResult.getMsg());
//            return false;
//        }
        log.info("sendMail,success");
        return true;
    }

    public static class MailTitleBean extends HashMap<String, Object> {
        public MailTitleBean(String titleParam) {
            put(PARAM_SUPPLIER_NAME, titleParam);
        }

        public MailTitleBean(Map<String, Object> params) {
            putAll(params);
        }
    }

    public static class MailContentBean extends HashMap<String, Object> {
        public MailContentBean(String contentParam) {
            put(PARAM_SUPPLIER_NAME, contentParam);
        }

        public MailContentBean(Map<String, Object> params) {
            putAll(params);
        }
    }
}
