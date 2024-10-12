package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.ViewTest;
import com.microservices.otmp.system.domain.entity.ViewTestDO;
import com.microservices.otmp.system.manager.ViewTestManager;
import com.microservices.otmp.system.mapper.ViewTestMapper;
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
public class ViewTestManagerImpl implements ViewTestManager {

    @Autowired
    private ViewTestMapper viewTestMapper;

    @Override
    public ViewTestDO selectViewTestById(String name) {
        return viewTestMapper.selectViewTestById(name);
    }

    @Override
    public List<ViewTestDO> selectViewTestList(ViewTest viewTest) {
        return viewTestMapper.selectViewTestList(viewTest);
    }

    @Override
    public int insertViewTest(ViewTestDO viewTest) {
        return viewTestMapper.insertViewTest(viewTest);
    }

    @Override
    public int updateViewTest(ViewTestDO viewTest) {
        return viewTestMapper.updateViewTest(viewTest);
    }

    @Override
    public int deleteViewTestById(String name) {
        return viewTestMapper.deleteViewTestById(name);
    }

    @Override
    public int deleteViewTestByIds(String[] names) {
        return viewTestMapper.deleteViewTestByIds(names);
    }
}
