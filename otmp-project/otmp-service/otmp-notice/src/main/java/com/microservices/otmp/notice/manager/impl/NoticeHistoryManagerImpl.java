package com.microservices.otmp.notice.manager.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.notice.common.NoticeConstant;
import com.microservices.otmp.notice.domain.entity.NoticeParamEntity;
import com.microservices.otmp.notice.domain.entity.NoticeSendKafka;
import com.microservices.otmp.notice.dto.NoticeHistoryDto;
import com.microservices.otmp.notice.dto.NoticeHistoryUserDto;
import com.microservices.otmp.notice.manager.NoticeHistoryManager;
import com.microservices.otmp.notice.mapper.NoticeHistoryMapper;
import com.microservices.otmp.notice.mapper.NoticeHistoryUserMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Component
public class NoticeHistoryManagerImpl implements NoticeHistoryManager {

    @Autowired
    private NoticeHistoryMapper noticeHistoryMapper;

    @Autowired
    private NoticeHistoryUserMapper noticeHistoryUserMapper;
    @Override
    public int insertNoticeHistory(NoticeHistoryDto noticeHistoryDto) {
        return noticeHistoryMapper.insert(noticeHistoryDto);
    }

    @Override
    public int updateNoticeHistory(NoticeHistoryDto noticeHistoryDto) {
        if (noticeHistoryDto == null || noticeHistoryDto.getId() == null) {
            return 0;
        }
        noticeHistoryMapper.updateByPrimaryKeySelective(noticeHistoryDto);
        return 1;
    }

    @Override
    public NoticeHistoryDto getNoticeHistory(Long id) {
        return noticeHistoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteNoticeHistory(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return 0;
        }
        ids.forEach(id -> {
            NoticeHistoryDto noticeHistoryDto = new NoticeHistoryDto();
            noticeHistoryDto.setId(id);
            noticeHistoryDto.setStatus(NoticeConstant.STATUS_DISABLE);
            noticeHistoryMapper.updateByPrimaryKeySelective(noticeHistoryDto);
        });
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void noticeHistoryToDb(NoticeSendKafka noticeSendKafka) {
        Map<String, String> noticeDimension = noticeSendKafka.getNoticeDimension();
        Map<String, String> noticeInfo = noticeSendKafka.getNoticeInfo();
        NoticeParamEntity noticeParamEntity = noticeSendKafka.getNoticeParamEntity();
        Map<String, List<String>> sendInfo = noticeSendKafka.getSendInfo();
        JSONObject normalParams = noticeSendKafka.getNormalParams();
        JSONObject titleBean = noticeSendKafka.getTitleBean();

        String uniqueId = UUID.randomUUID().toString();
        List<String> sendToList = sendInfo.get(NoticeConstant.SEND_TO_LIST);
        List<String> ccToList = sendInfo.get(NoticeConstant.SEND_CC);

        NoticeHistoryDto noticeHistoryDto = NoticeHistoryDto.builder()
                .id(SnowFlakeUtil.nextId())
                .uniqueId(uniqueId)
                .traceId(noticeInfo.get(NoticeConstant.TRACE_ID))
                .noticeType(noticeDimension.get(NoticeConstant.BUSINESS_TYPE))
                .normalParams(JSON.toJSONString(normalParams))
                .titleParams(JSON.toJSONString(titleBean))
                .mailTo(JSON.toJSONString(sendToList))
                .mailCc(CollectionUtils.isEmpty(ccToList) ? "" : JSON.toJSONString(ccToList))
                .deleteFlag(false)
                .createBy(noticeInfo.get(NoticeConstant.CREATE_BY))
                .updateBy(noticeInfo.get(NoticeConstant.CREATE_BY))
                .createTime(new Date())
                .updateTime(new Date())
                .noticeTitle(noticeParamEntity.getSubject())
                .noticeContent(noticeParamEntity.getContent())
                .status(NoticeConstant.STATUS_ENABLE)
                .build();
        noticeHistoryMapper.insert(noticeHistoryDto);

        Example example = new Example(NoticeHistoryDto.class);
        example.createCriteria().andEqualTo("uniqueId", uniqueId).andEqualTo("deleteFlag", false);
        List<NoticeHistoryDto> noticeHistoryDtos = noticeHistoryMapper.selectByExample(example);
        CommonUtils.collectIsEmpty(noticeHistoryDtos, "notice history is empty");
        Long noticeHistoryId = null;
        if (noticeHistoryDtos.get(0) != null) {
            noticeHistoryId = noticeHistoryDtos.get(0).getId();
        }
        NoticeHistoryUserDto noticeHistoryUserDto = new NoticeHistoryUserDto();
        Set<String> userSet = new HashSet<>();
        userSet.addAll(sendToList);
        userSet.addAll(ccToList);

        for (String userId : userSet) {
            noticeHistoryUserDto.setId(SnowFlakeUtil.nextId());
            noticeHistoryUserDto.setNoticeHistoryId(noticeHistoryId);
            noticeHistoryUserDto.setUserId(Long.parseLong(userId));
            noticeHistoryUserDto.setStatus(NoticeConstant.STATUS_ENABLE);
            noticeHistoryUserMapper.insert(noticeHistoryUserDto);
        }

    }
}
