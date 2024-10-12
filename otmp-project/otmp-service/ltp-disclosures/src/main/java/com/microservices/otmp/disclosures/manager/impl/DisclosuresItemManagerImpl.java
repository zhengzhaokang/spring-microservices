package com.microservices.otmp.disclosures.manager.impl;

import com.microservices.otmp.disclosures.dto.DisclosuresItemDto;
import com.microservices.otmp.disclosures.manager.DisclosuresItemManager;
import com.microservices.otmp.disclosures.mapper.DisclosuresItemMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DisclosuresItemManagerImpl implements DisclosuresItemManager {

    @Autowired
    private DisclosuresItemMapper disclosuresItemMapper;

    @Override
    public int insertDisclosuresItem(DisclosuresItemDto disclosuresItemDto) {
        return disclosuresItemMapper.insertSelective(disclosuresItemDto);
    }

    @Override
    public int updateDisclosuresItem(DisclosuresItemDto disclosuresItemDto) {
        if (disclosuresItemDto == null || disclosuresItemDto.getId() == null) {
            return 0;
        }
        return disclosuresItemMapper.updateByPrimaryKeySelective(disclosuresItemDto);
    }

    @Override
    public int deleteDisclosuresItemByIds(List<String> ids, String updateBy) {
        if (CollectionUtils.isEmpty(ids)) {
            return 0;
        }
        return disclosuresItemMapper.deleteDisclosuresItemByIds(updateBy, ids);
    }

    @Override
    public DisclosuresItemDto selectDisclosuresItemById(Long id) {
        if (id == null) {
            return null;
        }
        return disclosuresItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DisclosuresItemDto> selectDisclosuresItemList(DisclosuresItemDto disclosuresItemDto) {
        if (disclosuresItemDto == null) {
            return null;
        }
        return disclosuresItemMapper.selectDisclosuresItemList(disclosuresItemDto);
    }
}
