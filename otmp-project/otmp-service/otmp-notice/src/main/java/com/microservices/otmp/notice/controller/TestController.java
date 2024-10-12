package com.microservices.otmp.notice.controller;


import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.notice.service.EmailAttachmentService;
import com.microservices.otmp.notice.service.impl.EmailAttachmentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;

@RestController
@RequestMapping("test")
public class TestController {
    private static final Logger log    = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private EmailAttachmentService emailAttachmentService;

    @GetMapping("/aaa")
    public String test(@RequestParam("id") String id) {
        return id;
    }

    @GetMapping("/analysis/attach")
    public String analysisEmailAttachInfo(@RequestParam("date") String date) {
        Date curDate = null;
        try {
            curDate = StringUtils.isNotBlank(date) ? DateUtils.dateTime("yyyy-MM-dd", date): DateUtils.getZero();
        } catch (ParseException e) {
            log.error("###TestController analysisEmailAttachInfo is ", e);
        }
        //重跑哪一天的
        emailAttachmentService.analysisEmailAttachByExchange(curDate);
        return "id";
    }
}
