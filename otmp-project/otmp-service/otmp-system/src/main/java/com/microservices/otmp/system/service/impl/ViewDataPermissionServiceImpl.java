package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.ViewDataPermission;
import com.microservices.otmp.system.domain.entity.ViewDataPermissionDO;
import com.microservices.otmp.system.manager.ViewDataPermissionManager;
import com.microservices.otmp.system.service.IViewDataPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据权限，用户属于那个区域Service业务层处理
 *
 * @author lovefamily
 * @date 2022-04-14
 */
@Service
public class ViewDataPermissionServiceImpl implements IViewDataPermissionService {
    @Autowired
    private ViewDataPermissionManager viewDataPermissionManager;

    /**
     * 查询数据权限，用户属于那个区域
     *
     * @param id 数据权限，用户属于那个区域主键
     * @return 数据权限，用户属于那个区域
     */
    @Override
    public ViewDataPermission selectViewDataPermissionById(Integer id) {
        ViewDataPermissionDO viewDataPermissionDO = viewDataPermissionManager.selectViewDataPermissionById(id);
        ViewDataPermission viewDataPermission = new ViewDataPermission();
        org.springframework.beans.BeanUtils.copyProperties(viewDataPermissionDO, viewDataPermission);
        return viewDataPermission;
    }

    /**
     * 查询数据权限，用户属于那个区域列表
     *
     * @param viewDataPermission 数据权限，用户属于那个区域
     * @return 数据权限，用户属于那个区域
     */
    @Override
    public List<ViewDataPermission> selectViewDataPermissionList(ViewDataPermission viewDataPermission) {
        List<ViewDataPermissionDO> viewDataPermissionDOS = viewDataPermissionManager.selectViewDataPermissionList(viewDataPermission);
        List<ViewDataPermission> viewDataPermissions = new ArrayList<>(viewDataPermissionDOS.size());
        BeanUtils.copyListProperties(viewDataPermissionDOS, viewDataPermissions, ViewDataPermission.class);
        return viewDataPermissions;
    }

    /**
     * 新增数据权限，用户属于那个区域
     *
     * @param viewDataPermission 数据权限，用户属于那个区域
     * @return 结果
     */
    @Override
    public int insertViewDataPermission(ViewDataPermission viewDataPermission) {
        ViewDataPermissionDO viewDataPermissionDO = new ViewDataPermissionDO();
        org.springframework.beans.BeanUtils.copyProperties(viewDataPermission, viewDataPermissionDO);
        viewDataPermissionDO.setCreateTime(DateUtils.getNowDate());
        return viewDataPermissionManager.insertViewDataPermission(viewDataPermissionDO);
    }

    /**
     * 修改数据权限，用户属于那个区域
     *
     * @param viewDataPermission 数据权限，用户属于那个区域
     * @return 结果
     */
    @Override
    public int updateViewDataPermission(ViewDataPermission viewDataPermission) {
        ViewDataPermissionDO viewDataPermissionDO = new ViewDataPermissionDO();
        org.springframework.beans.BeanUtils.copyProperties(viewDataPermission, viewDataPermissionDO);
        viewDataPermissionDO.setUpdateTime(DateUtils.getNowDate());
        return viewDataPermissionManager.updateViewDataPermission(viewDataPermissionDO);
    }

    /**
     * 批量删除数据权限，用户属于那个区域
     *
     * @param ids 需要删除的数据权限，用户属于那个区域主键
     * @return 结果
     */
    @Override
    public int deleteViewDataPermissionByIds(Integer[] ids) {
        return viewDataPermissionManager.deleteViewDataPermissionByIds(ids);
    }

    /**
     * 删除数据权限，用户属于那个区域信息
     *
     * @param id 数据权限，用户属于那个区域主键
     * @return 结果
     */
    @Override
    public int deleteViewDataPermissionById(Integer id) {
        return viewDataPermissionManager.deleteViewDataPermissionById(id);
    }
}
