package com.microservices.otmp.system.manager;

import com.microservices.otmp.system.domain.ViewDataPermission;
import com.microservices.otmp.system.domain.entity.ViewDataPermissionDO;

import java.util.List;

/**
 * @author guowb1
 * @description
 * @date 2022/6/10 19:10
 */
public interface ViewDataPermissionManager {
    /**
     * 查询数据权限，用户属于那个区域
     *
     * @param id 数据权限，用户属于那个区域主键
     * @return 数据权限，用户属于那个区域
     */
    public ViewDataPermissionDO selectViewDataPermissionById(Integer id);

    /**
     * 查询数据权限，用户属于那个区域列表
     *
     * @param viewDataPermission 数据权限，用户属于那个区域
     * @return 数据权限，用户属于那个区域集合
     */
    public List<ViewDataPermissionDO> selectViewDataPermissionList(ViewDataPermission viewDataPermission);

    /**
     * 新增数据权限，用户属于那个区域
     *
     * @param viewDataPermission 数据权限，用户属于那个区域
     * @return 结果
     */
    public int insertViewDataPermission(ViewDataPermissionDO viewDataPermission);

    /**
     * 修改数据权限，用户属于那个区域
     *
     * @param viewDataPermission 数据权限，用户属于那个区域
     * @return 结果
     */
    public int updateViewDataPermission(ViewDataPermissionDO viewDataPermission);

    /**
     * 删除数据权限，用户属于那个区域
     *
     * @param id 数据权限，用户属于那个区域主键
     * @return 结果
     */
    public int deleteViewDataPermissionById(Integer id);

    /**
     * 批量删除数据权限，用户属于那个区域
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteViewDataPermissionByIds(Integer[] ids);
}
