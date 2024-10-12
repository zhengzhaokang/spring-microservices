package com.microservices.otmp.notice.controller;

import com.alibaba.fastjson.JSON;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.kafka.listener.KafkaCallback;
import com.microservices.otmp.common.kafka.util.KafkaSend;
import com.microservices.otmp.notice.common.KafkaFactory;
import com.microservices.otmp.notice.param.NoticeHistoryParam;
import com.microservices.otmp.notice.service.NoticeHistoryService;
import com.microservices.otmp.notice.vo.NoticeHistoryData;
import com.microservices.otmp.notice.vo.NoticeSendVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("notice/history")
public class NoticeHistoryController extends BaseController {

    @Autowired
    private NoticeHistoryService noticeHistoryService;

    @Value("${kafka.topic.notice}")
    private String sendNoticeTopic;

    @Autowired
    private KafkaSend kafkaSend;

    @Log(title = "NoticeHistory INSERT", businessType = BusinessType.INSERT)
    @PostMapping("insert")
    public ResultDTO<Integer> insertNoticeHistory(@RequestBody NoticeSendVO noticeSendVO)
    {
        String noticeSendDTOStr = JSON.toJSONString(noticeSendVO);
        kafkaSend.asynSend(sendNoticeTopic, UUID.randomUUID().toString(), noticeSendDTOStr, KafkaFactory.SEND_KAFKA_PRODUCER_FACTORY, new KafkaCallback(sendNoticeTopic), false);
        //noticeHistoryService.insertNoticeHistory(noticeSendVO);
        return ResultDTO.success();
    }

    @PostMapping("person/list")
    public ResultDTO<NoticeHistoryData> selectNoticeHistory(@RequestBody NoticeHistoryParam param)
    {
        param.setUserId(String.valueOf(getCurrentUserId()));
        return ResultDTO.success(noticeHistoryService.selectNoticeHistory(param));
    }

    @Log(title = "NoticeHistory STATUS", businessType = BusinessType.UPDATE)
    @PostMapping("update/status")
    public ResultDTO<Object> updateNoticeHistoryStatus(@RequestBody NoticeHistoryParam param)
    {
        param.setUserId(String.valueOf(getCurrentUserId()));
        noticeHistoryService.updateNoticeHistoryStatus(param);
        return ResultDTO.success();
    }

    @Log(title = "NoticeHistory BATCH STATUS", businessType = BusinessType.UPDATE)
    @PostMapping("batch/update/status")
    public ResultDTO<Object> batchUpdateNoticeHistoryStatus(@RequestBody NoticeHistoryParam param)
    {
        param.setUserId(String.valueOf(getCurrentUserId()));
        noticeHistoryService.batchUpdateNoticeHistoryStatus(param);
        return ResultDTO.success();
    }

    @PostMapping("manage/list")
    @HasPermissions("notice:message:view")
    public ResultDTO<NoticeHistoryData> selectManageNoticeHistory(@RequestBody NoticeHistoryParam param)
    {
        return ResultDTO.success(noticeHistoryService.selectManageNoticeHistory(param));
    }

}
