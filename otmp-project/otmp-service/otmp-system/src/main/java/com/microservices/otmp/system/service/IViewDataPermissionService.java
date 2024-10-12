package com.microservices.otmp.system.service;

import com.microservices.otmp.system.domain.ViewDataPermission;

import java.util.List;

/**
 * 数据权限，用户属于那个区域Service接口
 *
 * @author lovefamily
 * @date 2022-04-14
 */
public interface IViewDataPermissionService {
    /**
     * 查询数据权限，用户属于那个区域
     *
     * @param id 数据权限，用户属于那个区域主键
     * @return 数据权限，用户属于那个区域
     */
    public ViewDataPermission selectViewDataPermissionById(Integer id);

    /**
     * 查询数据权限，用户属于那个区域列表
     *
     * @param viewDataPermission 数据权限，用户属于那个区域
     * @return 数据权限，用户属于那个区域集合
     */
    public List<ViewDataPermission> selectViewDataPermissionList(ViewDataPermission viewDataPermission);

    /**
     * 新增数据权限，用户属于那个区域
     *
     * @param viewDataPermission 数据权限，用户属于那个区域
     * @return 结果
     */
    public int insertViewDataPermission(ViewDataPermission viewDataPermission);

    /**
     * 修改数据权限，用户属于那个区域
     *
     * @param viewDataPermission 数据权限，用户属于那个区域
     * @return 结果
     */
    public int updateViewDataPermission(ViewDataPermission viewDataPermission);

    /**
     * 批量删除数据权限，用户属于那个区域
     *
     * @param ids 需要删除的数据权限，用户属于那个区域主键集合
     * @return 结果
     */
    public int deleteViewDataPermissionByIds(Integer[] ids);

    /**
     * 删除数据权限，用户属于那个区域信息
     *
     * @param id 数据权限，用户属于那个区域主键
     * @return 结果
     */
    public int deleteViewDataPermissionById(Integer id);
}
