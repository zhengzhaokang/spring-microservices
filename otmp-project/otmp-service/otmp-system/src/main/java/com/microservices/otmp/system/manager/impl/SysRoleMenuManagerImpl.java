package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.entity.SysRoleMenuDO;
import com.microservices.otmp.system.manager.SysRoleMenuManager;
import com.microservices.otmp.system.mapper.SysRoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:22
 */

@Service
public class SysRoleMenuManagerImpl implements SysRoleMenuManager {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public int deleteRoleMenuByRoleId(Long roleId) {
        return sysRoleMenuMapper.deleteRoleMenuByRoleId(roleId);
    }

    @Override
    public int deleteRoleMenuByMenuId(Long menuId) {
        return sysRoleMenuMapper.deleteRoleMenuByMenuId(menuId);
    }

    @Override
    public int deleteRoleMenu(Long[] ids) {
        return sysRoleMenuMapper.deleteRoleMenu(ids);
    }

    @Override
    public int selectCountRoleMenuByMenuId(Long menuId) {
        return sysRoleMenuMapper.selectCountRoleMenuByMenuId(menuId);
    }

    @Override
    public List<SysRoleMenuDO> selectRoleMenuListByMenuId(Long menuId) {
        return sysRoleMenuMapper.selectRoleMenuListByMenuId(menuId);
    }

    @Override
    public int batchRoleMenu(List<SysRoleMenuDO> roleMenuList) {
        return sysRoleMenuMapper.batchRoleMenu(roleMenuList);
    }
}
