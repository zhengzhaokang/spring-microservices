package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysUserDataScope;
import com.microservices.otmp.system.domain.entity.GenTableColumnDO;
import com.microservices.otmp.system.domain.entity.SysUserDataScopeDO;
import com.microservices.otmp.system.manager.SysUserDataScopeManager;
import com.microservices.otmp.system.mapper.SysUserDataScopeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:23
 */

@Service
public class SysUserDataScopeManagerImpl implements SysUserDataScopeManager {

    @Autowired
    private SysUserDataScopeMapper sysUserDataScopeMapper;

    @Override
    public SysUserDataScopeDO selectSysUserDataScopeByUserDataScopeId(Long userDataScopeId) {
        return sysUserDataScopeMapper.selectSysUserDataScopeByUserDataScopeId(userDataScopeId);
    }

    @Override
    public List<SysUserDataScopeDO> selectSysUserDataScopeList(SysUserDataScope sysUserDataScope) {
        return sysUserDataScopeMapper.selectSysUserDataScopeList(sysUserDataScope);
    }

    @Override
    public int insertSysUserDataScope(SysUserDataScopeDO sysUserDataScope) {
        return sysUserDataScopeMapper.insertSysUserDataScope(sysUserDataScope);
    }

    @Override
    public int updateSysUserDataScope(SysUserDataScopeDO sysUserDataScope) {
        return sysUserDataScopeMapper.updateSysUserDataScope(sysUserDataScope);
    }

    @Override
    public int deleteSysUserDataScopeByUserDataScopeId(Long userDataScopeId) {
        return sysUserDataScopeMapper.deleteSysUserDataScopeByUserDataScopeId(userDataScopeId);
    }

    @Override
    public int deleteSysUserDataScopeByUserDataScopeIds(Long[] userDataScopeIds) {
        return sysUserDataScopeMapper.deleteSysUserDataScopeByUserDataScopeIds(userDataScopeIds);
    }

    @Override
    public List<SysUserDataScopeDO> getDataScopeByUserId(Integer userId) {
        return sysUserDataScopeMapper.selectDataScopeByUserId(userId);
    }

    @Override
    public int deleteSysUserDataScopeByUserId(Long userId) {
        return sysUserDataScopeMapper.deleteSysUserDataScopeByUserId(userId);
    }

    @Override
    public List<GenTableColumnDO> selectDbTableColumnsByName(String tableName, String[] tableCatalogs, String[] tableSchemas) {
        return sysUserDataScopeMapper.selectDbTableColumnsByName(tableName, tableCatalogs, tableSchemas);
    }
}
