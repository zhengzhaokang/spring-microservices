package com.microservices.otmp.disclosures.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.disclosures.common.DisclosureConstant;
import com.microservices.otmp.disclosures.common.enums.DisclosureItemEnum;
import com.microservices.otmp.disclosures.dto.DisclosuresBasicDto;
import com.microservices.otmp.disclosures.dto.DisclosuresItemDto;
import com.microservices.otmp.disclosures.manager.DisclosuresBasicManager;
import com.microservices.otmp.disclosures.manager.DisclosuresItemManager;
import com.microservices.otmp.disclosures.service.*;
import com.microservices.otmp.disclosures.vo.*;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.disclosures.manager.DisclosuresBasicManager;
import com.microservices.otmp.disclosures.manager.DisclosuresItemManager;
import com.microservices.otmp.disclosures.service.*;
import com.microservices.otmp.disclosures.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DisclosuresItemServiceImpl implements DisclosuresItemService {

    @Autowired
    private DisclosuresItemManager disclosuresItemManager;

    @Autowired
    private DisclosureItemLogService disclosureItemLogService;

    @Autowired
    private DisclosureItemAttachmentService disclosureItemAttachmentService;

    @Autowired
    private DisclosureItemCommentService disclosureItemCommentService;

    @Autowired
    private SnowFlakeUtil snowFlakeUtil;

    @Autowired
    private DisclosureNumberService disclosureNumberService;

    @Autowired
    private DisclosuresBasicManager disclosuresBasicManager;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertDisclosuresItem(DisclosuresItemVO disclosuresItemVO) {
        if (disclosuresItemVO == null || StringUtils.isBlank(disclosuresItemVO.getDisclosuresId())) {
            log.info("### DisclosuresItemServiceImpl insertDisclosuresItem param is null");
            return 0;
        }
        DisclosuresItemDto disclosuresItemDto = new DisclosuresItemDto();

        BeanUtils.copyProperties(disclosuresItemVO, disclosuresItemDto);

        long nextId = SnowFlakeUtil.nextId();
        disclosuresItemDto.setId(nextId);
        disclosuresItemDto.setItemNumber(disclosureNumberService.getDisclosureNumber(DisclosureConstant.QDI_SUFFIX));
        disclosuresItemDto.setStatus(DisclosureItemEnum.DATA_COLLECTED.code);
        disclosuresItemDto.setDelFlag(DisclosureConstant.ENABLE);
        disclosuresItemDto.setCreateTime(new Date());
        disclosuresItemDto.setUpdateTime(new Date());

        handleAttachment(disclosuresItemVO, String.valueOf(nextId), false);

        saveLog(disclosuresItemVO.getCreateBy(), String.valueOf(nextId), DisclosureConstant.CREATED_ITEM);

        return disclosuresItemManager.insertDisclosuresItem(disclosuresItemDto);
    }

    private void handleAttachment(DisclosuresItemVO disclosuresItemVO, String business, Boolean isUpdate) {
        if (disclosuresItemVO == null || StringUtils.isBlank(business)) {
            log.info("### DisclosuresItemServiceImpl handleAttachment param is null");
            return;
        }
        List<DisclosureItemAttachmentVO> disclosureItemAttachmentVOList = disclosuresItemVO.getDisclosureItemAttachmentVOList();
        if (CollectionUtils.isNotEmpty(disclosureItemAttachmentVOList)) {
            disclosureItemAttachmentService.saveDisclosureItemAttachment(disclosureItemAttachmentVOList, business,
                    DisclosureConstant.MODULE_ITEM);
        } else {
            log.info("### DisclosuresItemServiceImpl handleAttachment disclosureItemAttachmentVOList is null");
        }
        if (CollectionUtils.isEmpty(disclosureItemAttachmentVOList) && isUpdate) {
            log.info("### DisclosuresItemServiceImpl handleAttachment deleteDisclosureItemAttachment business is {}", business);
            disclosureItemAttachmentService.deleteDisclosureItemAttachment(business, DisclosureConstant.MODULE_ITEM);
        }
    }

    private void saveLog(String operator, String business, String description) {
        DisclosureItemLogVO disclosureItemLogVO = DisclosureItemLogVO.builder().module(DisclosureConstant.MODULE_ITEM)
                .business(business).operator(operator).description(description)
                .build();
        disclosureItemLogService.insertDisclosureItemLog(disclosureItemLogVO);
    }

    @Override
    public int updateDisclosuresItem(DisclosuresItemVO disclosuresItemVO) {
        if (disclosuresItemVO == null || StringUtils.isBlank(disclosuresItemVO.getItemId())) {
            log.info("### DisclosuresItemServiceImpl updateDisclosuresItem param is null");
            return 0;
        }
        DisclosuresItemDto disclosuresItemDto = new DisclosuresItemDto();
        BeanUtils.copyProperties(disclosuresItemVO, disclosuresItemDto);
        disclosuresItemDto.setId(Long.parseLong(disclosuresItemVO.getItemId()));
        disclosuresItemDto.setUpdateTime(new Date());

        handleAttachment(disclosuresItemVO, disclosuresItemVO.getItemId(), true);

        saveLog(disclosuresItemVO.getUpdateBy(), disclosuresItemVO.getItemId(), DisclosureConstant.CHANGE_ITEM);

        return disclosuresItemManager.updateDisclosuresItem(disclosuresItemDto);
    }

    @Override
    public int deleteDisclosuresItemByIds(List<String> ids, String updateBy) {
        return disclosuresItemManager.deleteDisclosuresItemByIds(ids, updateBy);
    }

    @Override
    public DisclosuresItemVO selectDisclosuresItemById(Long id) {
        if (id == null) {
            log.info("### DisclosuresItemServiceImpl selectDisclosuresItemById param is null");
            return null;
        }
        DisclosuresItemDto disclosuresItemDto = disclosuresItemManager.selectDisclosuresItemById(id);
        if (disclosuresItemDto == null) {
            log.info("### DisclosuresItemServiceImpl selectDisclosuresItemById disclosuresItemDto is null");
            return null;
        }
        DisclosuresItemVO disclosuresItemVO = new DisclosuresItemVO();
        BeanUtils.copyProperties(disclosuresItemDto, disclosuresItemVO);
        disclosuresItemVO.setItemId(String.valueOf(disclosuresItemDto.getId()));
        disclosuresItemVO.setId(null);

        disclosuresItemVO.setDisclosureItemAttachmentVOList(disclosureItemAttachmentService.
                queryDisclosureItemAttachment(String.valueOf(id), DisclosureConstant.MODULE_ITEM));

        DisclosureItemCommentVO disclosureItemCommentVO = new DisclosureItemCommentVO();
        disclosureItemCommentVO.setBusiness(String.valueOf(id));
        disclosuresItemVO.setDisclosureItemCommentVOList(disclosureItemCommentService.getDisclosureItemCommentVOList(disclosureItemCommentVO));

        DisclosuresBasicVO disclosuresBasicVO = getDisclosuresBasicVOById(Long.valueOf(disclosuresItemDto.getDisclosuresId()));
        disclosuresItemVO.setDisclosuresBasicVO(disclosuresBasicVO);

        return disclosuresItemVO;
    }

    private DisclosuresBasicVO getDisclosuresBasicVOById(Long id) {
        DisclosuresBasicDto disclosuresBasicDto = disclosuresBasicManager.selectDisclosuresBasicById(id);
        if (disclosuresBasicDto == null) {
            return null;
        }
        DisclosuresBasicVO disclosuresBasicVO = new DisclosuresBasicVO();
        BeanUtils.copyProperties(disclosuresBasicDto, disclosuresBasicVO);
        return disclosuresBasicVO;
    }

    @Override
    public PageInfo<DisclosuresItemVO> selectDisclosuresItemList(DisclosuresItemVO disclosuresItemVO) {
        DisclosuresItemDto disclosuresItemDto = new DisclosuresItemDto();
        BeanUtils.copyProperties(disclosuresItemVO, disclosuresItemDto);
        List<DisclosuresItemDto> disclosuresItemDtoList = disclosuresItemManager.selectDisclosuresItemList(disclosuresItemDto);
        if (CollectionUtils.isEmpty(disclosuresItemDtoList)) {
            log.info("### DisclosuresItemServiceImpl selectDisclosuresItemList disclosuresItemDtoList is null");
            return null;
        }
        PageInfo<DisclosuresItemDto> pageInfo = new PageInfo<>(disclosuresItemDtoList);
        long total = pageInfo.getTotal();

        List<DisclosuresItemVO> disclosuresItemVOList = Lists.newArrayList();
        BeanUtils.copyListProperties(disclosuresItemDtoList, disclosuresItemVOList, DisclosuresItemVO.class);

        handleDisclosuresItemVOList(disclosuresItemVOList);

        PageInfo<DisclosuresItemVO> disclosuresItemVOPageInfo = new PageInfo<>(disclosuresItemVOList);
        disclosuresItemVOPageInfo.setTotal(total);
        return disclosuresItemVOPageInfo;
    }

    private void handleDisclosuresItemVOList(List<DisclosuresItemVO> disclosuresItemVOList) {
        if (CollectionUtils.isEmpty(disclosuresItemVOList)) {
            log.info("### DisclosuresItemServiceImpl handleDisclosuresItemVOList disclosuresItemVOList is null");
            return;
        }
        String disclosuresId = disclosuresItemVOList.get(0).getDisclosuresId();
        DisclosuresBasicVO disclosuresBasicVO = getDisclosuresBasicVOById(Long.valueOf(disclosuresId));
        disclosuresItemVOList.forEach(item -> {
            Long id = item.getId();
            item.setItemId(String.valueOf(id));
            item.setId(null);
            item.setDisclosuresBasicVO(disclosuresBasicVO);
        });
    }
}
