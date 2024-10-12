package com.microservices.otmp.system.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.system.domain.SysUserDataScope;
import com.microservices.otmp.system.domain.dto.GenTableColumnDTO;
import com.microservices.otmp.system.domain.entity.GenTableColumnDO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * SysUserDataScopeService接口
 *
 * @author lovefamily
 * @date 2022-04-24
 */
public interface ISysUserDataScopeService {
    /**
     * 查询SysUserDataScope
     *
     * @param userDataScopeId SysUserDataScope主键
     * @return SysUserDataScope
     */
    public SysUserDataScope selectSysUserDataScopeByUserDataScopeId(Long userDataScopeId);

    /**
     * 查询SysUserDataScope列表
     *
     * @param sysUserDataScope SysUserDataScope
     * @return SysUserDataScope集合
     */
    public List<SysUserDataScope> selectSysUserDataScopeList(SysUserDataScope sysUserDataScope);

    /**
     * 新增SysUserDataScope
     *
     * @param sysUserDataScope SysUserDataScope
     * @return 结果
     */
    public int insertSysUserDataScope(SysUserDataScope sysUserDataScope);

    /**
     * 修改SysUserDataScope
     *
     * @param sysUserDataScope SysUserDataScope
     * @return 结果
     */
    public int updateSysUserDataScope(SysUserDataScope sysUserDataScope);

    /**
     * 批量删除SysUserDataScope
     *
     * @param userDataScopeIds 需要删除的SysUserDataScope主键集合
     * @return 结果
     */
    public int deleteSysUserDataScopeByUserDataScopeIds(Long[] userDataScopeIds);

    /**
     * 删除SysUserDataScope信息
     *
     * @param userDataScopeId SysUserDataScope主键
     * @return 结果
     */
    public int deleteSysUserDataScopeByUserDataScopeId(Long userDataScopeId);

    /**
     * 分页查询SysUserDataScope列表
     *
     * @param sysUserDataScope SysUserDataScope
     * @return SysUserDataScope集合
     */
    PageInfo<SysUserDataScope> selectSysUserDataScopePage(SysUserDataScope sysUserDataScope);

    List<SysUserDataScope> getDataScopeByUserId(@RequestParam("userId") Integer userId);

    /**
     * 根据(表名称)查询列信息
     *
     * @param tableName 表名称
     * @return 列信息
     */
    public List<GenTableColumnDTO> selectDbTableColumnsByName(String tableName, String[] tableCatalogs, String[] tableSchemas);

    public void initDbTableColumns();
}
