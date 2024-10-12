package com.microservices.otmp.disclosures.manager.impl;

import com.microservices.otmp.disclosures.dto.DisclosuresBasicDto;
import com.microservices.otmp.disclosures.entity.DisclosuresBasicCount;
import com.microservices.otmp.disclosures.manager.DisclosuresBasicManager;
import com.microservices.otmp.disclosures.mapper.DisclosuresBasicMapper;
import com.microservices.otmp.common.utils.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DisclosuresBasicManagerImpl implements DisclosuresBasicManager {

    @Autowired
    private DisclosuresBasicMapper disclosuresBasicMapper;

    @Override
    public int insertDisclosuresBasic(DisclosuresBasicDto disclosuresBasicDto) {
        return disclosuresBasicMapper.insertSelective(disclosuresBasicDto);
    }

    @Override
    public int updateDisclosuresBasic(DisclosuresBasicDto disclosuresBasicDto) {
        if (disclosuresBasicDto == null || disclosuresBasicDto.getId() == null) {
            return 0;
        }
        return disclosuresBasicMapper.updateByPrimaryKeySelective(disclosuresBasicDto);
    }

    @Override
    public int deleteDisclosuresBasicByIds(List<String> ids, String updateBy) {
        if (CollectionUtils.isEmpty(ids)) {
            return 0;
        }
        return disclosuresBasicMapper.deleteDisclosuresBasicByIds(updateBy, ids);
    }

    @Override
    public DisclosuresBasicDto selectDisclosuresBasicById(Long id) {
        if (id== null) {
            return null;
        }
        return disclosuresBasicMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DisclosuresBasicDto> selectDisclosuresBasicList(DisclosuresBasicDto disclosuresBasicDto) {
        if (disclosuresBasicDto == null) {
            return null;
        }
        return disclosuresBasicMapper.selectDisclosuresBasicList(disclosuresBasicDto);
    }

    @Override
    public int selectDisclosuresBasicCountByStatus(String status) {
        if (StringUtils.isBlank(status)) {
            return 0;
        }
        return disclosuresBasicMapper.selectDisclosuresBasicCountByStatus(status);
    }

    @Override
    public List<DisclosuresBasicCount> selectDisclosuresBasicCount() {
        return disclosuresBasicMapper.selectDisclosuresBasicCount();
    }
}
