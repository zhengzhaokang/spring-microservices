package com.microservices.otmp.system.manager.impl;

import com.google.common.collect.Lists;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.SysUserRole;
import com.microservices.otmp.system.domain.entity.SysUserRoleDO;
import com.microservices.otmp.system.manager.SysUserRoleManager;
import com.microservices.otmp.system.mapper.SysUserRoleMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:24
 */

@Service
public class SysUserRoleManagerImpl implements SysUserRoleManager {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public int deleteUserRoleByUserId(Long userId) {
        return sysUserRoleMapper.deleteUserRoleByUserId(userId);
    }

    @Override
    public int deleteUserRole(Long[] ids) {
        return sysUserRoleMapper.deleteUserRole(ids);
    }

    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return sysUserRoleMapper.countUserRoleByRoleId(roleId);
    }

    @Override
    public List<SysUserRoleDO> selectUserRoleListByRoleId(Long roleId) {
        return sysUserRoleMapper.selectUserRoleListByRoleId(roleId);
    }

    @Override
    public int batchUserRole(List<SysUserRoleDO> userRoleList) {
        return sysUserRoleMapper.batchUserRole(userRoleList);
    }

    @Override
    public int deleteUserRoleInfo(SysUserRoleDO userRole) {
        return sysUserRoleMapper.deleteUserRoleInfo(userRole);
    }

    @Override
    public void deleteUserRoleByRoleId(Long roleId) {
        sysUserRoleMapper.deleteUserRoleByRoleId(roleId);
    }

    @Override
    public int deleteUserRoleInfos(Long roleId, Long[] userIds) {
        return sysUserRoleMapper.deleteUserRoleInfos(roleId, userIds);
    }

    @Override
    public List<SysUserRole> selectUserRoleList(Integer userId) {
        List<SysUserRoleDO> list = sysUserRoleMapper.selectUserRoleList(userId);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        BeanUtils.copyListProperties(list, sysUserRoles, SysUserRole.class);
        return sysUserRoles;
    }

    @Override
    public List<SysUserRoleDO> selectUserRoleListByUserIds(List<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return Lists.newArrayList();
        }
        return sysUserRoleMapper.selectUserRoleListByUserIds(userIds);
    }

}
