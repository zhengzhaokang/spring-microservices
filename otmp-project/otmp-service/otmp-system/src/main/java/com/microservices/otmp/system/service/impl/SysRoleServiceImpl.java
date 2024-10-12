package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.annotation.DataScope;
import com.microservices.otmp.common.constant.UserConstants;
import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.common.exception.BusinessException;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.SysRole;
import com.microservices.otmp.system.domain.SysRoleMenu;
import com.microservices.otmp.system.domain.SysUserRole;
import com.microservices.otmp.system.domain.dto.SysUserRoleListDTO;
import com.microservices.otmp.system.domain.entity.SysRoleDO;
import com.microservices.otmp.system.domain.entity.SysRoleDeptDO;
import com.microservices.otmp.system.domain.entity.SysRoleMenuDO;
import com.microservices.otmp.system.domain.entity.SysUserRoleDO;
import com.microservices.otmp.system.domain.vo.SysUserRoleListVO;
import com.microservices.otmp.system.manager.SysRoleDeptManager;
import com.microservices.otmp.system.manager.SysRoleManager;
import com.microservices.otmp.system.manager.SysRoleMenuManager;
import com.microservices.otmp.system.manager.SysUserRoleManager;
import com.microservices.otmp.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色 业务层处理
 *
 * @author lovefamily
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {
    @Autowired
    private SysRoleManager sysRoleManager;

    @Autowired
    private SysRoleMenuManager sysRoleMenuManager;

    @Autowired
    private SysUserRoleManager sysUserRoleManager;

    @Autowired
    private SysRoleDeptManager sysRoleDeptManager;

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysRole> selectRoleList(SysRole role) {
        List<SysRoleDO> sysRoleDOS = sysRoleManager.selectRoleList(role);
        List<SysRole> sysRoles = new ArrayList<>(sysRoleDOS.size());
        BeanUtils.copyListProperties(sysRoleDOS, sysRoles, SysRole.class);
        return sysRoles;
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRoleKeys(Long userId) {
        List<SysRoleDO> perms = sysRoleManager.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRoleDO perm : perms) {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRolesByUserId(Long userId) {
        List<SysRoleDO> userRoles = sysRoleManager.selectRolesByUserId(userId);
        List<SysRoleDO> roles = sysRoleManager.selectRoleList(new SysRole());
        for (SysRoleDO role : roles) {
            for (SysRoleDO userRole : userRoles) {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        List<SysRole> sysRoles = new ArrayList<>(roles.size());
        BeanUtils.copyListProperties(roles, sysRoles, SysRole.class);
        return sysRoles;
    }

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRoleAll() {
        return selectRoleList(new SysRole());
    }

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public SysRole selectRoleById(Long roleId) {
        SysRoleDO sysRoleDO = sysRoleManager.selectRoleById(roleId);
        if (null == sysRoleDO) {
            return null;
        }
        SysRole sysRole = new SysRole();
        org.springframework.beans.BeanUtils.copyProperties(sysRoleDO, sysRole);
        return sysRole;
    }

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public boolean deleteRoleById(Long roleId) {
        return sysRoleManager.deleteRoleById(roleId) > 0;
    }

    /**
     * 批量删除角色信息
     *
     * @param ids 需要删除的数据ID
     * @throws Exception
     */
    @Override
    public int deleteRoleByIds(String ids) throws BusinessException {
        Long[] roleIds = Convert.toLongArray(ids);
        for (Long roleId : roleIds) {
            SysRole role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new BusinessException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        if (roleIds.length > 0) return sysRoleManager.deleteRoleByIds(roleIds);
        return 0;
    }

    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertRole(SysRole role) {
        // 新增角色信息
        SysRoleDO sysRoleDO = new SysRoleDO();
        org.springframework.beans.BeanUtils.copyProperties(role, sysRoleDO);
        sysRoleManager.insertRole(sysRoleDO);
        return insertRoleMenu(sysRoleDO);
    }

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateRole(SysRole role) {
        // 修改角色信息
        SysRoleDO sysRoleDO = new SysRoleDO();
        org.springframework.beans. BeanUtils.copyProperties(role, sysRoleDO);
        sysRoleManager.updateRole(sysRoleDO);
        // 删除角色与菜单关联
        sysRoleMenuManager.deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(sysRoleDO);
    }

    @Override
    public int updateRoleInfo(SysRole role) {
        SysRoleDO sysRoleDO = new SysRoleDO();
        org.springframework.beans.BeanUtils.copyProperties(role, sysRoleDO);
        sysRoleDO.setUpdateTime(DateUtils.getNowDate());
        return sysRoleManager.updateRole(sysRoleDO);
    }

    /**
     * 修改数据权限信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int authDataScope(SysRole role) {
        // 修改角色信息
        SysRoleDO sysRoleDO = new SysRoleDO();
        org.springframework.beans.BeanUtils.copyProperties(role, sysRoleDO);
        sysRoleManager.updateRole(sysRoleDO);
        // 删除角色与部门关联
        sysRoleDeptManager.deleteRoleDeptByRoleId(sysRoleDO.getRoleId());
        // 新增角色和部门信息（数据权限）
        return insertRoleDept(sysRoleDO);
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public int insertRoleMenu(SysRoleDO role) {
        int rows = 1;
        // 新增用户与角色管理
        List<SysRoleMenuDO> list = new ArrayList<>();
        for (Long menuId : role.getMenuIds()) {
            SysRoleMenuDO rm = new SysRoleMenuDO();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (!list.isEmpty()) {
            rows = sysRoleMenuManager.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 新增角色部门信息(数据权限)
     *
     * @param role 角色对象
     */
    public int insertRoleDept(SysRoleDO role) {
        int rows = 1;
        // 新增角色与部门（数据权限）管理
        List<SysRoleDeptDO> list = new ArrayList<>();
        for (Long deptId : role.getDeptIds()) {
            SysRoleDeptDO rd = new SysRoleDeptDO();
            rd.setRoleId(role.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (!list.isEmpty()) {
            rows = sysRoleDeptManager.batchRoleDept(list);
        }
        return rows;
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleNameUnique(SysRole role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRoleDO info = sysRoleManager.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return UserConstants.ROLE_NAME_NOT_UNIQUE;
        }
        return UserConstants.ROLE_NAME_UNIQUE;
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleKeyUnique(SysRole role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRoleDO info = sysRoleManager.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return UserConstants.ROLE_KEY_NOT_UNIQUE;
        }
        return UserConstants.ROLE_KEY_UNIQUE;
    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return sysUserRoleManager.countUserRoleByRoleId(roleId);
    }

    /**
     * 角色状态修改
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int changeStatus(SysRole role) {
        SysRoleDO sysRoleDO = new SysRoleDO();
        org.springframework.beans.BeanUtils.copyProperties(role, sysRoleDO);
        return sysRoleManager.updateRole(sysRoleDO);
    }

    /**
     * 取消授权用户角色
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    @Override
    public int deleteAuthUser(SysUserRole userRole) {
        SysUserRoleDO sysUserRoleDO = new SysUserRoleDO();
        org.springframework.beans.BeanUtils.copyProperties(userRole, sysUserRoleDO);
        return sysUserRoleManager.deleteUserRoleInfo(sysUserRoleDO);
    }

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    public int deleteAuthUsers(Long roleId, String userIds) {
        return sysUserRoleManager.deleteUserRoleInfos(roleId, Convert.toLongArray(userIds));
    }

    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    public int insertAuthUsers(Long roleId, String userIds) {
        Long[] users = Convert.toLongArray(userIds);
        // 新增用户与角色管理
        List<SysUserRoleDO> list = new ArrayList<>();
        for (Long userId : users) {
            SysUserRoleDO ur = new SysUserRoleDO();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return sysUserRoleManager.batchUserRole(list);
    }

    @Override
    public PageInfo<SysRole> selectRolePage(SysRole role) {
        List<SysRoleDO> sysRoleDOS = sysRoleManager.selectRoleList(role);
        PageInfo<SysRoleDO> pageInfo = new PageInfo<>(sysRoleDOS);
        List<SysRole> sysRoles = new ArrayList<>(sysRoleDOS.size());
        BeanUtils.copyListProperties(sysRoleDOS, sysRoles, SysRole.class);
        PageInfo<SysRole> resultPageInfo = new PageInfo<>(sysRoles);
        resultPageInfo.setTotal(pageInfo.getTotal());
        return resultPageInfo;
    }

    @Override
    public PageInfo<SysUserRoleListVO> userList(SysUserRoleListDTO sysRole) {
        List<SysUserRoleListVO> sysUserRoleListVOS = sysRoleManager.userList(sysRole);
        PageInfo<SysUserRoleListVO> sysUserRoleListVOPageInfo = new PageInfo<>(sysUserRoleListVOS);
        sysUserRoleListVOPageInfo.setTotal(sysUserRoleListVOPageInfo.getTotal());
        return sysUserRoleListVOPageInfo;
    }

    @Override
    public Integer deleteSysRole(SysUserRoleListDTO sysRole) {
        Long[] userIds = sysRole.getUserIds().stream().toArray(Long[]::new);

        return sysUserRoleManager.deleteUserRoleInfos(sysRole.getRoleId(), userIds);
    }

    @Override
    public Integer addSysRole(SysUserRoleListDTO sysRole, String name) {
        List<SysUserRoleDO> roleDOS = sysRole.getUserIds().stream().map(id -> {
            SysUserRoleDO sysUserRoleDO = new SysUserRoleDO();
            sysUserRoleDO.setRoleId(sysRole.getRoleId());
            sysUserRoleDO.setUserId(id);
            sysUserRoleDO.setCreateTime(new Date());
            sysUserRoleDO.setCreateBy(name);
            return sysUserRoleDO;
        }).collect(Collectors.toList());
        return sysUserRoleManager.batchUserRole(roleDOS);
    }

    @Override
    public PageInfo<SysUserRoleListVO> selectAddSysRoleList(SysUserRoleListDTO sysRole, String loginName) {
        List<SysUserRoleListVO> sysUserRoleListVOS = sysRoleManager.selectAddSysRoleList(sysRole);
        PageInfo<SysUserRoleListVO> sysUserRoleListVOPageInfo = new PageInfo<>(sysUserRoleListVOS);
        sysUserRoleListVOPageInfo.setTotal(sysUserRoleListVOPageInfo.getTotal());
        return sysUserRoleListVOPageInfo;
    }

    @Override
    public List<SysRoleMenu> selectRoleMenuListByMenuId(Long menuId) {
        List<SysRoleMenuDO> sysRoleMenuDOS = sysRoleMenuManager.selectRoleMenuListByMenuId(menuId);
        List<SysRoleMenu> sysRoleMenu = new ArrayList<>();
        BeanUtils.copyListProperties(sysRoleMenuDOS, sysRoleMenu, SysRoleMenu.class);
        return sysRoleMenu;
    }

    @Override
    public List<SysUserRole> selectUserRoleListByRoleId(Long roleId) {
        List<SysUserRoleDO> sysUserRoleDOS = sysUserRoleManager.selectUserRoleListByRoleId(roleId);
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        BeanUtils.copyListProperties(sysUserRoleDOS, sysUserRoles, SysUserRole.class);
        return sysUserRoles;
    }

    @Override
    public int batchRoleMenu(List<SysRoleMenu> roleMenuList) {
        List<SysRoleMenuDO> sysRoleMenu = new ArrayList<>();
        BeanUtils.copyListProperties(roleMenuList, sysRoleMenu, SysRoleMenuDO.class);
        return sysRoleMenuManager.batchRoleMenu(sysRoleMenu);
    }

    @Override
    public int batchUserRole(List<SysUserRole> userRoleList) {
        List<SysUserRoleDO> sysUserRoles = new ArrayList<>();
        BeanUtils.copyListProperties(userRoleList, sysUserRoles, SysUserRoleDO.class);
        return sysUserRoleManager.batchUserRole(sysUserRoles);
    }
}
