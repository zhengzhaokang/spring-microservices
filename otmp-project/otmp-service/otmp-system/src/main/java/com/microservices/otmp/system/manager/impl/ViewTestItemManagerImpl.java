package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.ViewTestItem;
import com.microservices.otmp.system.domain.entity.ViewTestItemDO;
import com.microservices.otmp.system.manager.ViewTestItemManager;
import com.microservices.otmp.system.mapper.ViewTestItemMapper;
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
public class ViewTestItemManagerImpl implements ViewTestItemManager {
    
    @Autowired
    private ViewTestItemMapper viewTestItemMapper;

    @Override
    public ViewTestItemDO selectViewTestItemById(String name) {
        return viewTestItemMapper.selectViewTestItemById(name);
    }

    @Override
    public List<ViewTestItemDO> selectViewTestItemList(ViewTestItem viewTestItem) {
        return viewTestItemMapper.selectViewTestItemList(viewTestItem);
    }

    @Override
    public int insertViewTestItem(ViewTestItemDO viewTestItem) {
        return viewTestItemMapper.insertViewTestItem(viewTestItem);
    }

    @Override
    public int updateViewTestItem(ViewTestItemDO viewTestItem) {
        return viewTestItemMapper.updateViewTestItem(viewTestItem);
    }

    @Override
    public int deleteViewTestItemById(String name) {
        return viewTestItemMapper.deleteViewTestItemById(name);
    }

    @Override
    public int deleteViewTestItemByIds(String[] names) {
        return viewTestItemMapper.deleteViewTestItemByIds(names);
    }
}
