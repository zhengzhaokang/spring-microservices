package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.ViewDataPermission;
import com.microservices.otmp.system.domain.entity.ViewDataPermissionDO;
import com.microservices.otmp.system.manager.ViewDataPermissionManager;
import com.microservices.otmp.system.mapper.ViewDataPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @description
 * @date 2022/6/10 19:10
 */

@Service
public class ViewDataPermissionManagerImpl implements ViewDataPermissionManager {

    @Autowired
    private ViewDataPermissionMapper viewDataPermissionMapper;

    @Override
    public ViewDataPermissionDO selectViewDataPermissionById(Integer id) {
        return viewDataPermissionMapper.selectViewDataPermissionById(id);
    }

    @Override
    public List<ViewDataPermissionDO> selectViewDataPermissionList(ViewDataPermission viewDataPermission) {
        return viewDataPermissionMapper.selectViewDataPermissionList(viewDataPermission);
    }

    @Override
    public int insertViewDataPermission(ViewDataPermissionDO viewDataPermission) {
        return viewDataPermissionMapper.insertViewDataPermission(viewDataPermission);
    }

    @Override
    public int updateViewDataPermission(ViewDataPermissionDO viewDataPermission) {
        return viewDataPermissionMapper.updateViewDataPermission(viewDataPermission);
    }

    @Override
    public int deleteViewDataPermissionById(Integer id) {
        return viewDataPermissionMapper.deleteViewDataPermissionById(id);
    }

    @Override
    public int deleteViewDataPermissionByIds(Integer[] ids) {
        return viewDataPermissionMapper.deleteViewDataPermissionByIds(ids);
    }
}
