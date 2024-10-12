package com.microservices.otmp.system.mapper;

import com.microservices.otmp.system.domain.ViewTest;
import com.microservices.otmp.system.domain.entity.ViewTestDO;

import java.util.List;

/**
 * 测试：测试动态展示字段Mapper接口
 * 
 * @author sdms
 * @date 2022-02-23
 */
public interface ViewTestMapper
{
    /**
     * 查询测试：测试动态展示字段
     * 
     * @param name 测试：测试动态展示字段ID
     * @return 测试：测试动态展示字段
     */
    public ViewTestDO selectViewTestById(String name);

    /**
     * 查询测试：测试动态展示字段列表
     * 
     * @param viewTest 测试：测试动态展示字段
     * @return 测试：测试动态展示字段集合
     */
    public List<ViewTestDO> selectViewTestList(ViewTest viewTest);

    /**
     * 新增测试：测试动态展示字段
     * 
     * @param viewTest 测试：测试动态展示字段
     * @return 结果
     */
    public int insertViewTest(ViewTestDO viewTest);

    /**
     * 修改测试：测试动态展示字段
     * 
     * @param viewTest 测试：测试动态展示字段
     * @return 结果
     */
    public int updateViewTest(ViewTestDO viewTest);

    /**
     * 删除测试：测试动态展示字段
     * 
     * @param name 测试：测试动态展示字段ID
     * @return 结果
     */
    public int deleteViewTestById(String name);

    /**
     * 批量删除测试：测试动态展示字段
     * 
     * @param names 需要删除的数据ID
     * @return 结果
     */
    public int deleteViewTestByIds(String[] names);
}
