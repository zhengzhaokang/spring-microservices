package com.microservices.otmp.disclosures.service.impl;

import com.alibaba.fastjson.JSON;
import com.microservices.otmp.disclosures.common.DisclosureConstant;
import com.microservices.otmp.disclosures.dto.DisclosureItemAttachmentDto;
import com.microservices.otmp.disclosures.mapper.DisclosureItemAttachmentMapper;
import com.microservices.otmp.disclosures.service.DisclosureItemAttachmentService;
import com.microservices.otmp.disclosures.vo.DisclosureItemAttachmentVO;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.disclosures.mapper.DisclosureItemAttachmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DisclosureItemAttachmentServiceImpl implements DisclosureItemAttachmentService {

    @Autowired
    private DisclosureItemAttachmentMapper disclosureItemAttachmentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveDisclosureItemAttachment(List<DisclosureItemAttachmentVO> disclosureItemAttachmentVOList, String business, String module) {
        if (CollectionUtils.isEmpty(disclosureItemAttachmentVOList) || StringUtils.isAnyBlank(business, module)) {
            log.info("### DisclosureItemAttachmentServiceImpl saveDisclosureItemAttachment disclosureItemAttachmentVOList isEmpty");
            return 0;
        }
        log.info("### DisclosureItemAttachmentServiceImpl saveDisclosureItemAttachment disclosureItemAttachmentVOList:{} business:{} module:{}", JSON.toJSONString(disclosureItemAttachmentVOList), business, module);
        Example example = new Example(DisclosureItemAttachmentDto.class);
        example.createCriteria().andEqualTo(DisclosureConstant.BUSINESS, business).andEqualTo(DisclosureConstant.MODULE, module);
        disclosureItemAttachmentMapper.deleteByExample(example);
        List<DisclosureItemAttachmentDto> disclosureItemAttachmentDtoList = Lists.newArrayList();
        BeanUtils.copyProperties(disclosureItemAttachmentVOList, disclosureItemAttachmentDtoList, DisclosureItemAttachmentDto.class);
        Date date = new Date();
        disclosureItemAttachmentDtoList.forEach(item -> {
            item.setCreateDate(date);
            item.setModule(module);
        });
        return disclosureItemAttachmentMapper.insertList(disclosureItemAttachmentDtoList);
    }

    @Override
    public List<DisclosureItemAttachmentVO> queryDisclosureItemAttachment(String business, String module) {
        if (StringUtils.isAnyBlank(business, module)) {
            log.info("### DisclosureItemAttachmentServiceImpl queryDisclosureItemAttachment param is null");
            return null;
        }
        Example example = new Example(DisclosureItemAttachmentDto.class);
        example.createCriteria().andEqualTo(DisclosureConstant.BUSINESS, business).andEqualTo(DisclosureConstant.MODULE, module);
        example.orderBy(DisclosureConstant.CREATE_DATE).desc();
        List<DisclosureItemAttachmentDto> disclosureItemAttachmentDtoList = disclosureItemAttachmentMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(disclosureItemAttachmentDtoList)) {
            List<DisclosureItemAttachmentVO> disclosureItemAttachmentVOList = Lists.newArrayList();
            BeanUtils.copyProperties(disclosureItemAttachmentDtoList, disclosureItemAttachmentVOList, DisclosureItemAttachmentVO.class);
            return disclosureItemAttachmentVOList;
        }
        log.info("### DisclosureItemAttachmentServiceImpl queryDisclosureItemAttachment disclosureItemAttachmentDtoList is null");
        return null;
    }

    @Override
    public int deleteDisclosureItemAttachment(String business, String module) {
        log.info("### DisclosureItemAttachmentServiceImpl deleteDisclosureItemAttachment business:{},module:{}", business, module);
        if (StringUtils.isAnyBlank(business, module)) {
            log.info("### DisclosureItemAttachmentServiceImpl deleteDisclosureItemAttachment param is null");
            return 0;
        }
        Example example = new Example(DisclosureItemAttachmentDto.class);
        example.createCriteria().andEqualTo(DisclosureConstant.BUSINESS, business).andEqualTo(DisclosureConstant.MODULE, module);
        return disclosureItemAttachmentMapper.deleteByExample(example);
    }
}
