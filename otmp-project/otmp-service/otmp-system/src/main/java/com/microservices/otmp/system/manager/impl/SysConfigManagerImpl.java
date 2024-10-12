package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.entity.SysConfigDO;
import com.microservices.otmp.system.manager.SysConfigManager;
import com.microservices.otmp.system.mapper.SysConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:20
 */

@Service
public class SysConfigManagerImpl implements SysConfigManager {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public SysConfigDO selectConfig(SysConfigDO sysConfigDO) {
        return sysConfigMapper.selectConfig(sysConfigDO);
    }

    @Override
    public List<SysConfigDO> selectConfigList(SysConfigDO sysConfigDO) {
        return sysConfigMapper.selectConfigList(sysConfigDO);
    }

    @Override
    public SysConfigDO checkConfigKeyUnique(String configKey) {
        return sysConfigMapper.checkConfigKeyUnique(configKey);
    }

    @Override
    public int insertConfig(SysConfigDO sysConfigDO) {
        return sysConfigMapper.insertConfig(sysConfigDO);
    }

    @Override
    public int updateConfig(SysConfigDO sysConfigDO) {
        return sysConfigMapper.updateConfig(sysConfigDO);
    }

    @Override
    public int deleteConfigByIds(String[] configIds) {
        return sysConfigMapper.deleteConfigByIds(configIds);
    }
}
