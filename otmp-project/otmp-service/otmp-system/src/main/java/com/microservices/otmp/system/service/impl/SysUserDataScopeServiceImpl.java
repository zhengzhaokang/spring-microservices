package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.constant.DbTableConstants;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.domain.SysUserDataScope;
import com.microservices.otmp.system.domain.dto.GenTableColumnDTO;
import com.microservices.otmp.system.domain.entity.GenTableColumnDO;
import com.microservices.otmp.system.domain.entity.SysUserDataScopeDO;
import com.microservices.otmp.system.manager.SysUserDataScopeManager;
import com.microservices.otmp.system.service.ISysUserDataScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * SysUserDataScopeService业务层处理
 *
 * @author lovefamily
 * @date 2022-04-24
 */
@Service
public class SysUserDataScopeServiceImpl implements ISysUserDataScopeService {
    @Autowired
    private SysUserDataScopeManager sysUserDataScopeManager;


    @Autowired
    private RedisUtils redis;

    /**
     * 查询SysUserDataScope
     *
     * @param userDataScopeId SysUserDataScope主键
     * @return SysUserDataScope
     */
    @Override
    public SysUserDataScope selectSysUserDataScopeByUserDataScopeId(Long userDataScopeId) {
        SysUserDataScopeDO sysUserDataScopeDO = sysUserDataScopeManager.selectSysUserDataScopeByUserDataScopeId(userDataScopeId);
        SysUserDataScope sysUserDataScope = new SysUserDataScope();
        org.springframework.beans.BeanUtils.copyProperties(sysUserDataScopeDO, sysUserDataScope);
        return sysUserDataScope;
    }

    /**
     * 查询SysUserDataScope列表
     *
     * @param sysUserDataScope SysUserDataScope
     * @return SysUserDataScope
     */
    @Override
    public List<SysUserDataScope> selectSysUserDataScopeList(SysUserDataScope sysUserDataScope) {
        List<SysUserDataScopeDO> sysUserDataScopeDOS = sysUserDataScopeManager.selectSysUserDataScopeList(sysUserDataScope);
        List<SysUserDataScope> sysUserDataScopes = new ArrayList<>(sysUserDataScopeDOS.size());
        BeanUtils.copyListProperties(sysUserDataScopeDOS, sysUserDataScopes, SysUserDataScope.class);
        return sysUserDataScopes;
    }

    /**
     * 新增SysUserDataScope
     *
     * @param sysUserDataScope SysUserDataScope
     * @return 结果
     */
    @Override
    public int insertSysUserDataScope(SysUserDataScope sysUserDataScope) {
        SysUserDataScopeDO sysUserDataScopeDO = new SysUserDataScopeDO();
        org.springframework.beans.BeanUtils.copyProperties(sysUserDataScope, sysUserDataScopeDO);
        sysUserDataScopeDO.setCreateTime(DateUtils.getNowDate());
        return sysUserDataScopeManager.insertSysUserDataScope(sysUserDataScopeDO);
    }

    /**
     * 修改SysUserDataScope
     *
     * @param sysUserDataScope SysUserDataScope
     * @return 结果
     */
    @Override
    public int updateSysUserDataScope(SysUserDataScope sysUserDataScope) {
        SysUserDataScopeDO sysUserDataScopeDO = new SysUserDataScopeDO();
        org.springframework.beans.BeanUtils.copyProperties(sysUserDataScope, sysUserDataScopeDO);
        sysUserDataScopeDO.setUpdateTime(DateUtils.getNowDate());
        return sysUserDataScopeManager.updateSysUserDataScope(sysUserDataScopeDO);
    }

    /**
     * 批量删除SysUserDataScope
     *
     * @param userDataScopeIds 需要删除的SysUserDataScope主键
     * @return 结果
     */
    @Override
    public int deleteSysUserDataScopeByUserDataScopeIds(Long[] userDataScopeIds) {
        return sysUserDataScopeManager.deleteSysUserDataScopeByUserDataScopeIds(userDataScopeIds);
    }

    /**
     * 删除SysUserDataScope信息
     *
     * @param userDataScopeId SysUserDataScope主键
     * @return 结果
     */
    @Override
    public int deleteSysUserDataScopeByUserDataScopeId(Long userDataScopeId) {
        return sysUserDataScopeManager.deleteSysUserDataScopeByUserDataScopeId(userDataScopeId);
    }

    @Override
    public PageInfo<SysUserDataScope> selectSysUserDataScopePage(SysUserDataScope sysUserDataScope) {
        List<SysUserDataScopeDO> sysUserDataScopeDOS = sysUserDataScopeManager.selectSysUserDataScopeList(sysUserDataScope);
        PageInfo<SysUserDataScopeDO> pageInfo = new PageInfo<>(sysUserDataScopeDOS);
        List<SysUserDataScope> sysUserDataScopes = new ArrayList<>(sysUserDataScopeDOS.size());
        BeanUtils.copyListProperties(sysUserDataScopeDOS, sysUserDataScopes, SysUserDataScope.class);
        PageInfo<SysUserDataScope> resultPageInfo = new PageInfo<>(sysUserDataScopes);
        resultPageInfo.setTotal(pageInfo.getTotal());
        return resultPageInfo;
    }

    /**
     * 这个是把多个value合并
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysUserDataScope> getDataScopeByUserId(Integer userId) {
        List<SysUserDataScopeDO> sysUserDataScopeDOS = sysUserDataScopeManager.getDataScopeByUserId(userId);
        List<SysUserDataScope> sysUserDataScopes = new ArrayList<>(sysUserDataScopeDOS.size());
        BeanUtils.copyListProperties(sysUserDataScopeDOS, sysUserDataScopes, SysUserDataScope.class);
        return sysUserDataScopes;
    }

    @Override
    public List<GenTableColumnDTO> selectDbTableColumnsByName(String tableName, String[] tableCatalogs, String[] tableSchemas) {
        List<GenTableColumnDO> genTableColumnDOS = sysUserDataScopeManager.selectDbTableColumnsByName(tableName, tableCatalogs, tableSchemas);
        List<GenTableColumnDTO> genTableColumnDTOS = new ArrayList<>(genTableColumnDOS.size());
        BeanUtils.copyListProperties(genTableColumnDOS, genTableColumnDTOS, GenTableColumnDTO.class);
        return genTableColumnDTOS;
    }

    @Override
    public void initDbTableColumns() {
        List<GenTableColumnDTO> genTableColumnDTOS = selectDbTableColumnsByName(null, DbTableConstants.DB_CATALOG_NAMES, DbTableConstants.DB_SCHEMA_NAMES);
        Map<String, List<GenTableColumnDTO>> groupByTableName = genTableColumnDTOS.stream().collect(Collectors.groupingBy(GenTableColumnDTO::getTableName));
        groupByTableName.forEach((k, v) -> redis.set(RedisConstants.REDIS_DBTABLECOLUMN_DATA_NAME_PREFIX + k, v, RedisConstants.REDIS_EXPIRE_TIME_FOREVER));
    }
}
