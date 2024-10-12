package com.microservices.otmp.system.service;

import com.microservices.otmp.system.domain.ViewTestItem;

import java.util.List;

/**
 * 测试：测试动态展示字段Service接口
 * 
 * @author sdms
 * @date 2022-02-23
 */
public interface IViewTestItemService
{
    /**
     * 查询测试：测试动态展示字段
     * 
     * @param name 测试：测试动态展示字段ID
     * @return 测试：测试动态展示字段
     */
    public ViewTestItem selectViewTestItemById(String name);

    /**
     * 查询测试：测试动态展示字段列表
     * 
     * @param viewTestItem 测试：测试动态展示字段
     * @return 测试：测试动态展示字段集合
     */
    List<ViewTestItem> selectViewTestItemList(ViewTestItem viewTestItem);
    List<ViewTestItem> selectViewTestItemListAsyncTask(ViewTestItem viewTestItem);

    /**
     * 新增测试：测试动态展示字段
     * 
     * @param viewTestItem 测试：测试动态展示字段
     * @return 结果
     */
    public int insertViewTestItem(ViewTestItem viewTestItem);

    /**
     * 修改测试：测试动态展示字段
     * 
     * @param viewTestItem 测试：测试动态展示字段
     * @return 结果
     */
    public int updateViewTestItem(ViewTestItem viewTestItem);

    /**
     * 批量删除测试：测试动态展示字段
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteViewTestItemByIds(String ids);

    /**
     * 删除测试：测试动态展示字段信息
     * 
     * @param name 测试：测试动态展示字段ID
     * @return 结果
     */
    public int deleteViewTestItemById(String name);

}
