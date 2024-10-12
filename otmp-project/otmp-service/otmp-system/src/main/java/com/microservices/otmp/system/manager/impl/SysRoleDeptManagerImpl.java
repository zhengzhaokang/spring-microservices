package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.entity.SysRoleDeptDO;
import com.microservices.otmp.system.manager.SysRoleDeptManager;
import com.microservices.otmp.system.mapper.SysRoleDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:22
 */

@Service
public class SysRoleDeptManagerImpl implements SysRoleDeptManager {
    
    @Autowired
    private SysRoleDeptMapper sysRoleDeptMapper;
    
    @Override
    public int deleteRoleDeptByRoleId(Long roleId) {
        return sysRoleDeptMapper.deleteRoleDeptByRoleId(roleId);
    }

    @Override
    public int deleteRoleDept(Long[] ids) {
        return sysRoleDeptMapper.deleteRoleDept(ids);
    }

    @Override
    public int selectCountRoleDeptByDeptId(Long deptId) {
        return sysRoleDeptMapper.selectCountRoleDeptByDeptId(deptId);
    }

    @Override
    public int batchRoleDept(List<SysRoleDeptDO> roleDeptList) {
        return sysRoleDeptMapper.batchRoleDept(roleDeptList);
    }
}
