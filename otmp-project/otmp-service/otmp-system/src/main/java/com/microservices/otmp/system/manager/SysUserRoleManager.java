package com.microservices.otmp.system.manager;

import com.microservices.otmp.system.domain.SysUserRole;
import com.microservices.otmp.system.domain.entity.SysUserRoleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:24
 */
public interface SysUserRoleManager {
    /**
     * 通过用户ID删除用户和角色关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserRoleByUserId(Long userId);

    /**
     * 批量删除用户和角色关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserRole(Long[] ids);

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    public int countUserRoleByRoleId(Long roleId);

    List<SysUserRoleDO> selectUserRoleListByRoleId(Long roleId);

    /**
     * 批量新增用户角色信息
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    public int batchUserRole(List<SysUserRoleDO> userRoleList);

    /**
     * 删除用户和角色关联信息
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    public int deleteUserRoleInfo(SysUserRoleDO userRole);
    void deleteUserRoleByRoleId(Long roleId);

    /**
     * 批量取消授权用户角色
     *
     * @param roleId 角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    public int deleteUserRoleInfos(@Param("roleId") Long roleId, @Param("userIds") Long[] userIds);

    public List<SysUserRole> selectUserRoleList(Integer userId);

    List<SysUserRoleDO> selectUserRoleListByUserIds(List<Long> userIds);
}
