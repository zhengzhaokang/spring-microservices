package com.microservices.otmp.notice.service.impl;

import com.google.common.collect.Lists;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.notice.common.NoticeConstant;
import com.microservices.otmp.notice.dto.NoticeDto;
import com.microservices.otmp.notice.manager.NoticeManager;
import com.microservices.otmp.notice.mapper.NoticeMapper;
import com.microservices.otmp.notice.service.NoticeService;
import com.microservices.otmp.notice.vo.NoticeVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeManager noticeManager;

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public NoticeVO selectNoticeById(Long noticeId) {
        NoticeDto noticeDto = noticeManager.selectNoticeById(noticeId);
        if (noticeDto == null) {
            return null;
        }
        NoticeVO noticeVO = new NoticeVO();
        BeanUtils.copyProperties(noticeDto, noticeVO);
        noticeVO.setNoticeId(String.valueOf(noticeDto.getNoticeId()));
        return noticeVO;
    }

    @Override
    public List<NoticeDto> selectNoticeList(NoticeVO notice) {
        NoticeDto noticeDto = new NoticeDto();
        BeanUtils.copyProperties(notice, noticeDto);
        List<NoticeDto> noticeDtos = noticeManager.selectNoticeList(noticeDto);

        return noticeDtos;
    }

    @Override
    public int insertNotice(NoticeVO notice) {
        if (notice == null || StringUtils.isAnyBlank(notice.getNoticeType(), notice.getNoticeTitle(),
                notice.getNoticeContent())) {
            return NoticeConstant.INSERT_ERROR_CODE_0;
        }
        //校验noticeType的唯一性
        Example example = new Example(NoticeDto.class);
        example.createCriteria().andEqualTo("noticeType", notice.getNoticeType()).
                andEqualTo("status", NoticeConstant.STATUS_ENABLE);
        List<NoticeDto> noticeDtos = noticeMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(noticeDtos)) {
            return NoticeConstant.INSERT_ERROR_CODE_2; //已经存在该noticeType的notice，不允许重复添加
        }
        NoticeDto noticeDto = new NoticeDto();
        BeanUtils.copyProperties(notice, noticeDto);
        noticeDto.setNoticeId(SnowFlakeUtil.nextId());
        noticeDto.setStatus(NoticeConstant.STATUS_ENABLE);
        noticeDto.setCreateTime(new Date());
        noticeDto.setUpdateTime(new Date());
        return noticeManager.insertNotice(noticeDto);
    }

    @Override
    public int updateNotice(NoticeVO notice) {
        if (notice == null) {
            return 0;
        }
        NoticeDto noticeDto = new NoticeDto();
        BeanUtils.copyProperties(notice, noticeDto);
        noticeDto.setNoticeId(Long.valueOf(notice.getNoticeId()));
        noticeDto.setUpdateTime(new Date());
        return noticeManager.updateNotice(noticeDto);
    }

    @Override
    public int deleteNoticeByIds(String ids) {
        if (StringUtils.isBlank(ids)) {
            return 0;
        }
        String[] noticeIds = ids.split(",");
        return noticeManager.deleteNoticeByIds(noticeIds);
    }
}
