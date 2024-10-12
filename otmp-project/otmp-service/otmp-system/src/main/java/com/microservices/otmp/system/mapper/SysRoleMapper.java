package com.microservices.otmp.system.mapper;

import com.microservices.otmp.system.domain.SysRole;
import com.microservices.otmp.system.domain.dto.SysUserRoleListDTO;
import com.microservices.otmp.system.domain.entity.SysRoleDO;
import com.microservices.otmp.system.domain.vo.SysUserRoleListVO;

import java.util.List;

/**
 * 角色表 数据层
 * 
 * @author lovefamily
 */
public interface SysRoleMapper
{
    /**
     * 根据条件分页查询角色数据
     * 
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    public List<SysRoleDO> selectRoleList(SysRole role);

    /**
     * 根据用户ID查询角色
     * 
     * @param userId 用户ID
     * @return 角色列表
     */
    public List<SysRoleDO> selectRolesByUserId(Long userId);

    /**
     * 通过角色ID查询角色
     * 
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    public SysRoleDO selectRoleById(Long roleId);

    /**
     * 通过角色ID删除角色
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteRoleById(Long roleId);

    /**
     * 批量角色用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRoleByIds(Long[] ids);

    /**
     * 修改角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    public int updateRole(SysRoleDO role);

    /**
     * 新增角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    public int insertRole(SysRoleDO role);

    /**
     * 校验角色名称是否唯一
     * 
     * @param roleName 角色名称
     * @return 角色信息
     */
    public SysRoleDO checkRoleNameUnique(String roleName);
    
    /**
     * 校验角色权限是否唯一
     * 
     * @param roleKey 角色权限
     * @return 角色信息
     */
    public SysRoleDO checkRoleKeyUnique(String roleKey);

    List<SysUserRoleListVO> userList(SysUserRoleListDTO sysRole);

    List<SysUserRoleListVO> selectAddSysRoleList(SysUserRoleListDTO sysRole);
}
