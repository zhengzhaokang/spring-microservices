package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysMenu;
import com.microservices.otmp.system.domain.dto.SysMenuImportDTO;
import com.microservices.otmp.system.domain.entity.SysMenuDO;
import com.microservices.otmp.system.manager.SysMenuManager;
import com.microservices.otmp.system.mapper.SysMenuMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:20
 */

@Service
public class SysMenuManagerImpl implements SysMenuManager {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenuDO> selectMenuAll() {
        return sysMenuMapper.selectMenuAll();
    }

    @Override
    public List<SysMenuDO> selectMenuNormalAll() {
        return sysMenuMapper.selectMenuNormalAll();
    }

    @Override
    public List<SysMenuDO> selectMenusByUserId(Long userId) {
        return sysMenuMapper.selectMenusByUserId(userId);
    }

    @Override
    public SysMenuDO selectMenuByMenuId(Long menuId) {
        return sysMenuMapper.selectMenuByMenuId(menuId);
    }

    @Override
    public List<String> selectPermsByUserId(Long userId) {
        return sysMenuMapper.selectPermsByUserId(userId);
    }

    @Override
    public List<SysMenuDO> selectMenuIdsByRoleId(Long roleId) {
        return sysMenuMapper.selectMenuIdsByRoleId(roleId);
    }

    @Override
    public List<String> selectMenuTree(Long roleId) {
        return sysMenuMapper.selectMenuTree(roleId);
    }

    @Override
    public List<SysMenuDO> selectMenuList(SysMenu menu) {
        return sysMenuMapper.selectMenuList(menu);
    }

    @Override
    public List<SysMenuDO> selectMenus(SysMenu menu) {
        return sysMenuMapper.selectMenus(menu);
    }

    @Override
    public int deleteMenuById(Long menuId) {
        return sysMenuMapper.deleteMenuById(menuId);
    }

    @Override
    public SysMenuDO selectMenuById(Long menuId) {
        return sysMenuMapper.selectMenuById(menuId);
    }

    @Override
    public int selectCountMenuByParentId(Long parentId) {
        return sysMenuMapper.selectCountMenuByParentId(parentId);
    }

    @Override
    public int insertMenu(SysMenuDO menu) {
        return sysMenuMapper.insertMenu(menu);
    }

    @Override
    public int updateMenu(SysMenuDO menu) {
        return sysMenuMapper.updateMenu(menu);
    }

    @Override
    public SysMenuDO checkMenuNameUnique(String menuName, Long parentId) {
        return sysMenuMapper.checkMenuNameUnique(menuName, parentId);
    }

    @Override
    public List<SysMenuImportDTO> exportMenuList(SysMenuImportDTO sysMenu) {
        return sysMenuMapper.exportMenuList(sysMenu);
    }

    @Override
    public Integer batchInsert(List<SysMenuDO> dos) {
        if (CollectionUtils.isEmpty(dos)) {
            return 0;
        }
        return sysMenuMapper.batchInsert(dos);
    }
}
