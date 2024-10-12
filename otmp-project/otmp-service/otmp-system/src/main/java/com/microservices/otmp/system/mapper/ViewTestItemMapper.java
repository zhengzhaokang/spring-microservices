package com.microservices.otmp.system.mapper;

import com.microservices.otmp.system.domain.ViewTestItem;
import com.microservices.otmp.system.domain.entity.ViewTestItemDO;

import java.util.List;

/**
 * 测试：测试动态展示字段Mapper接口
 * 
 * @author sdms
 * @date 2022-02-23
 */
public interface ViewTestItemMapper
{
    /**
     * 查询测试：测试动态展示字段
     * 
     * @param name 测试：测试动态展示字段ID
     * @return 测试：测试动态展示字段
     */
    public ViewTestItemDO selectViewTestItemById(String name);

    /**
     * 查询测试：测试动态展示字段列表
     * 
     * @param viewTestItem 测试：测试动态展示字段
     * @return 测试：测试动态展示字段集合
     */
    public List<ViewTestItemDO> selectViewTestItemList(ViewTestItem viewTestItem);

    /**
     * 新增测试：测试动态展示字段
     * 
     * @param viewTestItem 测试：测试动态展示字段
     * @return 结果
     */
    public int insertViewTestItem(ViewTestItemDO viewTestItem);

    /**
     * 修改测试：测试动态展示字段
     * 
     * @param viewTestItem 测试：测试动态展示字段
     * @return 结果
     */
    public int updateViewTestItem(ViewTestItemDO viewTestItem);

    /**
     * 删除测试：测试动态展示字段
     * 
     * @param name 测试：测试动态展示字段ID
     * @return 结果
     */
    public int deleteViewTestItemById(String name);

    /**
     * 批量删除测试：测试动态展示字段
     * 
     * @param names 需要删除的数据ID
     * @return 结果
     */
    public int deleteViewTestItemByIds(String[] names);
}
