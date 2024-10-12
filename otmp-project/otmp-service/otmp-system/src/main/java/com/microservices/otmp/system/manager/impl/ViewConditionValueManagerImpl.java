package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.system.domain.ViewConditionValue;
import com.microservices.otmp.system.domain.entity.ViewConditionValueDO;
import com.microservices.otmp.system.manager.ViewConditionValueManager;
import com.microservices.otmp.system.mapper.ViewConditionValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 测试：测试动态展示字段Mapper接口
 *
 * @author sdms
 * @date 2022-02-23
 */

@Service
public class ViewConditionValueManagerImpl implements ViewConditionValueManager {

    @Autowired
    private ViewConditionValueMapper viewConditionValueMapper;

    @Override
    public ViewConditionValueDO selectViewConditionValueById(Integer id) {
        return viewConditionValueMapper.selectViewConditionValueById(id);
    }

    @Override
    public List<ViewConditionValueDO> selectViewConditionValueList(ViewConditionValue viewConditionValue) {
        return viewConditionValueMapper.selectViewConditionValueList(viewConditionValue);
    }

    @Override
    public int insertViewConditionValue(ViewConditionValue viewConditionValue) {
        viewConditionValue.setCreateTime(DateUtils.getNowDate());
        return viewConditionValueMapper.insertViewConditionValue(viewConditionValue);
    }

    @Override
    public int updateViewConditionValue(ViewConditionValue viewConditionValue) {
        return viewConditionValueMapper.updateViewConditionValue(viewConditionValue);
    }

    @Override
    public int deleteViewConditionValueById(Integer id) {
        return viewConditionValueMapper.deleteViewConditionValueById(id);
    }

    @Override
    public int deleteViewConditionValueByFieldIdAndCondition(Integer fieldId, String condition) {
        return viewConditionValueMapper.deleteViewConditionValueByFieldIdAndCondition(fieldId, condition);
    }

    @Override
    public int deleteViewConditionValueByIds(Integer[] ids) {
        return viewConditionValueMapper.deleteViewConditionValueByIds(ids);
    }

    @Override
    public int deleteViewConditionGroup(Integer fieldId) {
        return viewConditionValueMapper.deleteViewConditionGroup(fieldId);
    }
}
