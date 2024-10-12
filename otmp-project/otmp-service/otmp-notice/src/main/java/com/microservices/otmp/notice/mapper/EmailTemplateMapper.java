package com.microservices.otmp.notice.mapper;

import com.microservices.otmp.notice.domain.EmailCondition;
import com.microservices.otmp.notice.domain.EmailDimension;
import com.microservices.otmp.notice.domain.EmailTemplateEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Describe: 邮件发送模板
 * @Author shirui
 * @Date 2021-12-31 15:02
 */
public interface EmailTemplateMapper {

    List<EmailTemplateEntity> getEmailTemplateByPage(EmailTemplateEntity emailTemplateSendRequest);
    List<EmailTemplateEntity> getConditionOfEmail(EmailCondition emailCondition);

    List<EmailTemplateEntity> getOneEmailTemplate(EmailDimension emailCondition);
    /**
      * @Description 新增邮件模板
      * @author shirui3
      * @param emailTemplateEntity
      */
    Integer insertEmailTemplate(@Param("emailTemplate") EmailTemplateEntity emailTemplateEntity);

    /**
     * @Description 更新邮件模板
     * @author shirui3
     * @param emailTemplateEntity
     */
    Integer updateEmailTemplate(@Param("emailTemplate") EmailTemplateEntity emailTemplateEntity);
    Integer updateEmailTemplateEnable(@Param("emailTemplate") EmailTemplateEntity emailTemplateEntity);
}
