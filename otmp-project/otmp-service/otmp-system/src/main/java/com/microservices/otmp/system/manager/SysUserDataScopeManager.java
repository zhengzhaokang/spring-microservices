package com.microservices.otmp.system.manager;

import com.microservices.otmp.system.domain.SysUserDataScope;
import com.microservices.otmp.system.domain.entity.GenTableColumnDO;
import com.microservices.otmp.system.domain.entity.SysUserDataScopeDO;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:23
 */
public interface SysUserDataScopeManager {
    /**
     * 查询SysUserDataScope
     *
     * @param userDataScopeId SysUserDataScope主键
     * @return SysUserDataScopeDO
     */
    public SysUserDataScopeDO selectSysUserDataScopeByUserDataScopeId(Long userDataScopeId);

    /**
     * 查询SysUserDataScope列表
     *
     * @param sysUserDataScope SysUserDataScopeDO
     * @return SysUserDataScope集合
     */
    public List<SysUserDataScopeDO> selectSysUserDataScopeList(SysUserDataScope sysUserDataScope);

    /**
     * 新增SysUserDataScope
     *
     * @param sysUserDataScope SysUserDataScopeDO
     * @return 结果
     */
    public int insertSysUserDataScope(SysUserDataScopeDO sysUserDataScope);

    /**
     * 修改SysUserDataScope
     *
     * @param sysUserDataScope SysUserDataScopeDO
     * @return 结果
     */
    public int updateSysUserDataScope(SysUserDataScopeDO sysUserDataScope);

    /**
     * 删除SysUserDataScope
     *
     * @param userDataScopeId SysUserDataScope主键
     * @return 结果
     */
    public int deleteSysUserDataScopeByUserDataScopeId(Long userDataScopeId);

    /**
     * 批量删除SysUserDataScope
     *
     * @param userDataScopeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysUserDataScopeByUserDataScopeIds(Long[] userDataScopeIds);

    public List<SysUserDataScopeDO> getDataScopeByUserId(Integer userId);

    public int deleteSysUserDataScopeByUserId(Long userId);

    /**
     * 根据(表名称)查询列信息
     *
     * @param tableName
     * @param tableCatalogs
     * @param tableSchemas
     * @return 列信息
     */
    public List<GenTableColumnDO> selectDbTableColumnsByName(String tableName, String[] tableCatalogs, String[] tableSchemas);
}
