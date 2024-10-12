package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.ViewTest;
import com.microservices.otmp.system.domain.entity.ViewTestDO;
import com.microservices.otmp.system.manager.ViewTestManager;
import com.microservices.otmp.system.service.IViewTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试：测试动态展示字段Service业务层处理
 * 
 * @author sdms
 * @date 2022-02-23
 */
@Service
public class ViewTestServiceImpl implements IViewTestService
{
    @Autowired
    private ViewTestManager viewTestManager;

    /**
     * 查询测试：测试动态展示字段
     * 
     * @param name 测试：测试动态展示字段ID
     * @return 测试：测试动态展示字段
     */
    @Override
    public ViewTest selectViewTestById(String name)
    {
        ViewTestDO viewTestDO = viewTestManager.selectViewTestById(name);
        ViewTest viewTest = new ViewTest();
        org.springframework.beans.BeanUtils.copyProperties(viewTestDO, viewTest);
        return viewTest;
    }

    /**
     * 查询测试：测试动态展示字段列表
     * 
     * @param viewTest 测试：测试动态展示字段
     * @return 测试：测试动态展示字段
     */
    @Override
    public List<ViewTest> selectViewTestList(ViewTest viewTest)
    {
        List<ViewTestDO> viewTestDOS = viewTestManager.selectViewTestList(viewTest);
        List<ViewTest> viewTests = new ArrayList<>(viewTestDOS.size());
        BeanUtils.copyListProperties(viewTestDOS, viewTests, ViewTest.class);
        return viewTests;
    }

    /**
     * 新增测试：测试动态展示字段
     * 
     * @param viewTest 测试：测试动态展示字段
     * @return 结果
     */
    @Override
    public int insertViewTest(ViewTest viewTest)
    {
        ViewTestDO viewTestDO = new ViewTestDO();
        org.springframework.beans.BeanUtils.copyProperties(viewTest, viewTestDO);
        viewTestDO.setCreateTime(DateUtils.getNowDate());
        return viewTestManager.insertViewTest(viewTestDO);
    }

    /**
     * 修改测试：测试动态展示字段
     * 
     * @param viewTest 测试：测试动态展示字段
     * @return 结果
     */
    @Override
    public int updateViewTest(ViewTest viewTest)
    {
        ViewTestDO viewTestDO = new ViewTestDO();
        org.springframework.beans.BeanUtils.copyProperties(viewTest, viewTestDO);
        viewTestDO.setUpdateTime(DateUtils.getNowDate());
        return viewTestManager.updateViewTest(viewTestDO);
    }

    /**
     * 删除测试：测试动态展示字段对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteViewTestByIds(String ids)
    {
        return viewTestManager.deleteViewTestByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除测试：测试动态展示字段信息
     * 
     * @param name 测试：测试动态展示字段ID
     * @return 结果
     */
    public int deleteViewTestById(String name)
    {
        return viewTestManager.deleteViewTestById(name);
    }
}
