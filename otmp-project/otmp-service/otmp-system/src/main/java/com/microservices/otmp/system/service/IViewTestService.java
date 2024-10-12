package com.microservices.otmp.system.service;

import com.microservices.otmp.system.domain.ViewTest;

import java.util.List;

/**
 * 测试：测试动态展示字段Service接口
 * 
 * @author sdms
 * @date 2022-02-23
 */
public interface IViewTestService
{
    /**
     * 查询测试：测试动态展示字段
     * 
     * @param name 测试：测试动态展示字段ID
     * @return 测试：测试动态展示字段
     */
    public ViewTest selectViewTestById(String name);

    /**
     * 查询测试：测试动态展示字段列表
     * 
     * @param viewTest 测试：测试动态展示字段
     * @return 测试：测试动态展示字段集合
     */
    public List<ViewTest> selectViewTestList(ViewTest viewTest);

    /**
     * 新增测试：测试动态展示字段
     * 
     * @param viewTest 测试：测试动态展示字段
     * @return 结果
     */
    public int insertViewTest(ViewTest viewTest);

    /**
     * 修改测试：测试动态展示字段
     * 
     * @param viewTest 测试：测试动态展示字段
     * @return 结果
     */
    public int updateViewTest(ViewTest viewTest);

    /**
     * 批量删除测试：测试动态展示字段
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteViewTestByIds(String ids);

    /**
     * 删除测试：测试动态展示字段信息
     * 
     * @param name 测试：测试动态展示字段ID
     * @return 结果
     */
    public int deleteViewTestById(String name);


}
