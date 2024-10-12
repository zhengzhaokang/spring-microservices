package com.microservices.otmp.disclosures.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.microservices.otmp.disclosures.common.DisclosureConstant;
import com.microservices.otmp.disclosures.common.enums.DisclosureBasicEnum;
import com.microservices.otmp.disclosures.dto.DisclosuresBasicDto;
import com.microservices.otmp.disclosures.entity.DisclosuresBasicCount;
import com.microservices.otmp.disclosures.manager.DisclosuresBasicManager;
import com.microservices.otmp.disclosures.service.DisclosureNumberService;
import com.microservices.otmp.disclosures.service.DisclosuresBasicService;
import com.microservices.otmp.disclosures.vo.DisclosuresBasicVO;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DisclosuresBasicServiceImpl implements DisclosuresBasicService {

    @Autowired
    private DisclosuresBasicManager disclosuresBasicManager;

    @Autowired
    private SnowFlakeUtil snowFlakeUtil;

    @Autowired
    private DisclosureNumberService disclosureNumberService;

    @Override
    public int insertDisclosuresBasic(DisclosuresBasicVO disclosuresBasicVO) {
        if (disclosuresBasicVO == null) {
            log.info("### DisclosuresBasicServiceImpl insertDisclosuresBasic disclosuresBasicVO is null");
            return 0;
        }
        DisclosuresBasicDto disclosuresBasicDto = new DisclosuresBasicDto();
        BeanUtils.copyProperties(disclosuresBasicVO, disclosuresBasicDto);

        disclosuresBasicDto.setId(SnowFlakeUtil.nextId());
        disclosuresBasicDto.setBasicNumber(disclosureNumberService.getDisclosureNumber(DisclosureConstant.QDP_SUFFIX));
        disclosuresBasicDto.setStatus(DisclosureBasicEnum.SUBMITTED.code);
        disclosuresBasicDto.setDelFlag(DisclosureConstant.ENABLE);
        disclosuresBasicDto.setCreateTime(new Date());
        disclosuresBasicDto.setUpdateTime(new Date());

        return disclosuresBasicManager.insertDisclosuresBasic(disclosuresBasicDto);
    }

    @Override
    public int updateDisclosuresBasic(DisclosuresBasicVO disclosuresBasicVO) {
        if (disclosuresBasicVO == null || StringUtils.isBlank(disclosuresBasicVO.getBasicId())) {
            log.info("### DisclosuresBasicServiceImpl updateDisclosuresBasic param is null");
            return 0;
        }
        DisclosuresBasicDto disclosuresBasicDto = new DisclosuresBasicDto();
        BeanUtils.copyProperties(disclosuresBasicVO, disclosuresBasicDto);
        disclosuresBasicDto.setId(Long.parseLong(disclosuresBasicVO.getBasicId()));
        disclosuresBasicDto.setUpdateTime(new Date());
        return disclosuresBasicManager.updateDisclosuresBasic(disclosuresBasicDto);
    }

    @Override
    public int deleteDisclosuresBasicByIds(List<String> ids, String updateBy) {
        return disclosuresBasicManager.deleteDisclosuresBasicByIds(ids, updateBy);
    }

    @Override
    public DisclosuresBasicVO selectDisclosuresBasicById(Long id) {
        if (id == null) {
            log.info("### DisclosuresBasicServiceImpl selectDisclosuresBasicById id is null");
            return null;
        }
        DisclosuresBasicDto disclosuresBasicDto = disclosuresBasicManager.selectDisclosuresBasicById(id);
        if (disclosuresBasicDto == null) {
            log.info("### DisclosuresBasicServiceImpl selectDisclosuresBasicById disclosuresBasicDto is null");
            return null;
        }
        DisclosuresBasicVO disclosuresBasicVO = new DisclosuresBasicVO();
        BeanUtils.copyProperties(disclosuresBasicDto, disclosuresBasicVO);
        disclosuresBasicVO.setBasicId(String.valueOf(id));
        disclosuresBasicVO.setId(null);
        return disclosuresBasicVO;
    }

    @Override
    public PageInfo<DisclosuresBasicVO> selectDisclosuresBasicList(DisclosuresBasicVO disclosuresBasicVO) {
        DisclosuresBasicDto disclosuresBasicDto = new DisclosuresBasicDto();
        BeanUtils.copyProperties(disclosuresBasicVO, disclosuresBasicDto);
        List<DisclosuresBasicDto> disclosuresBasicDtoList = disclosuresBasicManager.selectDisclosuresBasicList(disclosuresBasicDto);
        if (CollectionUtils.isEmpty(disclosuresBasicDtoList)) {
            log.info("### DisclosuresBasicServiceImpl selectDisclosuresBasicList disclosuresBasicDtoList is null");
            return null;
        }
        PageInfo<DisclosuresBasicDto> disclosuresBasicDtoPageInfo = new PageInfo<>(disclosuresBasicDtoList);
        long total = disclosuresBasicDtoPageInfo.getTotal();
        List<DisclosuresBasicVO> disclosuresBasicVOList = Lists.newArrayList();
        BeanUtils.copyListProperties(disclosuresBasicDtoList, disclosuresBasicVOList, DisclosuresBasicVO.class);
        //处理id
        handleDisclosuresBasicVOList(disclosuresBasicVOList);
        PageInfo<DisclosuresBasicVO> disclosuresBasicVOPageInfo = new PageInfo<>(disclosuresBasicVOList);
        disclosuresBasicVOPageInfo.setTotal(total);
        return disclosuresBasicVOPageInfo;
    }

    private void handleDisclosuresBasicVOList(List<DisclosuresBasicVO> disclosuresBasicVOList) {
        if (CollectionUtils.isEmpty(disclosuresBasicVOList)) {
            log.info("### DisclosuresBasicServiceImpl handleDisclosuresBasicVOList disclosuresBasicVOList isEmpty");
            return;
        }
        disclosuresBasicVOList.forEach(item -> {
            Long id = item.getId();
            item.setBasicId(String.valueOf(id));
            item.setId(null);
        });
    }

    @Override
    public int selectDisclosuresBasicCountByStatus(String status) {
        return disclosuresBasicManager.selectDisclosuresBasicCountByStatus(status);
    }

    @Override
    public List<DisclosuresBasicCount> selectDisclosuresBasicCount() {
        List<DisclosuresBasicCount> disclosuresBasicCountList = Lists.newArrayList();
        List<String> allStatus = DisclosureBasicEnum.getAllStatus();
        List<DisclosuresBasicCount> disclosuresBasicCounts = disclosuresBasicManager.selectDisclosuresBasicCount();
        Map<String, Integer> map = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(disclosuresBasicCounts)) {
            map = disclosuresBasicCounts.stream().collect(Collectors.toMap(DisclosuresBasicCount::getStatus, DisclosuresBasicCount::getCount, (k1, k2) -> k1));
        }
        for (String status : allStatus) {
            Integer count = MapUtils.getInteger(map, status, 0);
            DisclosuresBasicCount disclosuresBasicCount = new DisclosuresBasicCount();
            disclosuresBasicCount.setStatus(status);
            disclosuresBasicCount.setCount(count);
            disclosuresBasicCountList.add(disclosuresBasicCount);
        }
        return disclosuresBasicCountList;
    }

    @Override
    public List<DisclosuresBasicVO> selectDisclosuresBasic(DisclosuresBasicVO disclosuresBasicVO) {
        DisclosuresBasicDto disclosuresBasicDto = new DisclosuresBasicDto();
        BeanUtils.copyProperties(disclosuresBasicVO, disclosuresBasicDto);
        List<DisclosuresBasicDto> disclosuresBasicDtoList = disclosuresBasicManager.selectDisclosuresBasicList(disclosuresBasicDto);
        if (CollectionUtils.isEmpty(disclosuresBasicDtoList)) {
            log.info("### DisclosuresBasicServiceImpl selectDisclosuresBasicList disclosuresBasicDtoList is null");
            return null;
        }
        List<DisclosuresBasicVO> disclosuresBasicVOList = Lists.newArrayList();
        BeanUtils.copyListProperties(disclosuresBasicDtoList, disclosuresBasicVOList, DisclosuresBasicVO.class);
        //处理id
        handleDisclosuresBasicVOList(disclosuresBasicVOList);
        return disclosuresBasicVOList;
    }
}
