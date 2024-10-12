package com.microservices.otmp.notice.schedule;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.notice.service.EmailAttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
public class EmailAttachScheduleTask {

    private static final Logger log    = LoggerFactory.getLogger(EmailAttachScheduleTask.class);

    @Autowired
    private EmailAttachmentService emailAttachmentService;

    @Value("${email.attachment.switch}")
    private Boolean attachmentSwitch;

    /**
     * 每天23点，解析邮件汇率信息，加开关，决定是否执行
     */
    @Scheduled(cron = "0 0 23 * * ?")
    public void analysisEmailAttach() {
        if (!attachmentSwitch) {
            log.error("###EmailAttachScheduleTask attachmentSwitch is false");
            return;
        }
        try {
            emailAttachmentService.analysisEmailAttachByExchange(DateUtils.getZero());
        } catch (ParseException e) {
            log.error("###EmailAttachScheduleTask analysisEmailAttach ParseException is", e);
        }
    }
}
