package com.microservices.otmp.notice.service;

import java.util.Date;

public interface EmailAttachmentService {

    /**
     * 解析邮件附件内容
     */
    void analysisEmailAttachInfo(Date curDate);

    void analysisEmailAttachByExchange(Date curDate);
}
