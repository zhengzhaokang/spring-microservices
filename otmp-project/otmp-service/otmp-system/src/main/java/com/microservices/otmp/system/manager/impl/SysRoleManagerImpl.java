package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysRole;
import com.microservices.otmp.system.domain.dto.SysUserRoleListDTO;
import com.microservices.otmp.system.domain.entity.SysRoleDO;
import com.microservices.otmp.system.domain.vo.SysUserRoleListVO;
import com.microservices.otmp.system.manager.SysRoleManager;
import com.microservices.otmp.system.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:22
 */

@Service
public class SysRoleManagerImpl implements SysRoleManager {
    
    @Autowired
    private SysRoleMapper sysRoleMapper;
    
    @Override
    public List<SysRoleDO> selectRoleList(SysRole role) {
        return sysRoleMapper.selectRoleList(role);
    }

    @Override
    public List<SysRoleDO> selectRolesByUserId(Long userId) {
        return sysRoleMapper.selectRolesByUserId(userId);
    }

    @Override
    public SysRoleDO selectRoleById(Long roleId) {
        return sysRoleMapper.selectRoleById(roleId);
    }

    @Override
    public int deleteRoleById(Long roleId) {
        return sysRoleMapper.deleteRoleById(roleId);
    }

    @Override
    public int deleteRoleByIds(Long[] ids) {
        return sysRoleMapper.deleteRoleByIds(ids);
    }

    @Override
    public int updateRole(SysRoleDO role) {
        return sysRoleMapper.updateRole(role);
    }

    @Override
    public int insertRole(SysRoleDO role) {
        return sysRoleMapper.insertRole(role);
    }

    @Override
    public SysRoleDO checkRoleNameUnique(String roleName) {
        return sysRoleMapper.checkRoleNameUnique(roleName);
    }

    @Override
    public SysRoleDO checkRoleKeyUnique(String roleKey) {
        return sysRoleMapper.checkRoleKeyUnique(roleKey);
    }

    @Override
    public List<SysUserRoleListVO> userList(SysUserRoleListDTO sysRole) {
        return sysRoleMapper.userList(sysRole);
    }

    @Override
    public List<SysUserRoleListVO> selectAddSysRoleList(SysUserRoleListDTO sysRole) {
        return sysRoleMapper.selectAddSysRoleList(sysRole);
    }

}
