package com.microservices.otmp.system.service;

import com.microservices.otmp.system.domain.ViewConditionValue;
import com.microservices.otmp.system.domain.entity.ViewConditionValueDO;

import java.util.List;

/**
 * viewConditionValueService接口
 *
 * @author lovefamily
 * @date 2022-06-13
 */
public interface IViewConditionValueService {
    /**
     * 查询viewConditionValue
     *
     * @param id viewConditionValue主键
     * @return viewConditionValue
     */
    public ViewConditionValueDO selectViewConditionValueById(Integer id);

    /**
     * 查询viewConditionValue列表
     *
     * @param viewConditionValue viewConditionValue
     * @return viewConditionValue集合
     */
    public List<ViewConditionValueDO> selectViewConditionValueList(ViewConditionValue viewConditionValue);

    /**
     * 新增viewConditionValue
     *
     * @param viewConditionValue viewConditionValue
     * @return 结果
     */
    public int insertViewConditionValue(ViewConditionValue viewConditionValue);

    /**
     * 修改viewConditionValue
     *
     * @param viewConditionValue viewConditionValue
     * @return 结果
     */
    public int updateViewConditionValue(ViewConditionValue viewConditionValue);

    /**
     * 批量删除viewConditionValue
     *
     * @param ids 需要删除的viewConditionValue主键集合
     * @return 结果
     */
    public int deleteViewConditionValueByIds(Integer[] ids);

    /**
     * 删除viewConditionValue信息
     *
     * @param id viewConditionValue主键
     * @return 结果
     */
    public int deleteViewConditionValueById(Integer id);

    public int deleteViewConditionValueByFieldIdAndCondition(Integer fieldId, String condition);


}
