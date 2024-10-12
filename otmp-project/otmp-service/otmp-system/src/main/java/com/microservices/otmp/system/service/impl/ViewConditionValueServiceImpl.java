package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.system.domain.ViewConditionValue;
import com.microservices.otmp.system.domain.entity.ViewConditionValueDO;
import com.microservices.otmp.system.manager.ViewConditionValueManager;
import com.microservices.otmp.system.service.IViewConditionValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * viewConditionValueService业务层处理
 *
 * @author lovefamily
 * @date 2022-06-13
 */
@Service
public class ViewConditionValueServiceImpl implements IViewConditionValueService {
    @Autowired
    private ViewConditionValueManager viewConditionValueManager;

    /**
     * 查询viewConditionValue
     *
     * @param id viewConditionValue主键
     * @return viewConditionValue
     */
    @Override
    public ViewConditionValueDO selectViewConditionValueById(Integer id) {

        return viewConditionValueManager.selectViewConditionValueById(id);
    }

    /**
     * 查询viewConditionValue列表
     *
     * @param viewConditionValue viewConditionValue
     * @return viewConditionValue
     */
    @Override
    public List<ViewConditionValueDO> selectViewConditionValueList(ViewConditionValue viewConditionValue) {

        return viewConditionValueManager.selectViewConditionValueList(viewConditionValue);
    }

    /**
     * 新增viewConditionValue
     *
     * @param viewConditionValue viewConditionValue
     * @return 结果
     */
    @Override
    public int insertViewConditionValue(ViewConditionValue viewConditionValue) {
        viewConditionValue.setCreateTime(DateUtils.getNowDate());
        return viewConditionValueManager.insertViewConditionValue(viewConditionValue);
    }

    /**
     * 修改viewConditionValue
     *
     * @param viewConditionValue viewConditionValue
     * @return 结果
     */
    @Override
    public int updateViewConditionValue(ViewConditionValue viewConditionValue) {
        viewConditionValue.setUpdateTime(DateUtils.getNowDate());
        return viewConditionValueManager.updateViewConditionValue(viewConditionValue);
    }

    /**
     * 批量删除viewConditionValue
     *
     * @param ids 需要删除的viewConditionValue主键
     * @return 结果
     */
    @Override
    public int deleteViewConditionValueByIds(Integer[] ids) {
        return viewConditionValueManager.deleteViewConditionValueByIds(ids);
    }

    /**
     * 删除viewConditionValue信息
     *
     * @param id viewConditionValue主键
     * @return 结果
     */
    @Override
    public int deleteViewConditionValueById(Integer id) {
        return viewConditionValueManager.deleteViewConditionValueById(id);
    }

    @Override
    public int deleteViewConditionValueByFieldIdAndCondition(Integer fieldId, String condition) {
        return viewConditionValueManager.deleteViewConditionValueByFieldIdAndCondition(fieldId, condition);
    }
}
