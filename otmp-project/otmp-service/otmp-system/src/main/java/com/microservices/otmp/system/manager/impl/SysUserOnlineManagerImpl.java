package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysUserOnline;
import com.microservices.otmp.system.domain.entity.SysUserOnlineDO;
import com.microservices.otmp.system.manager.SysUserOnlineManager;
import com.microservices.otmp.system.mapper.SysUserOnlineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:23
 */

@Service
public class SysUserOnlineManagerImpl implements SysUserOnlineManager {
    
    @Autowired
    private SysUserOnlineMapper sysUserOnlineMapper;
    
    @Override
    public SysUserOnlineDO selectOnlineById(String sessionId) {
        return sysUserOnlineMapper.selectOnlineById(sessionId);
    }

    @Override
    public int deleteOnlineById(String sessionId) {
        return sysUserOnlineMapper.deleteOnlineById(sessionId);
    }

    @Override
    public int saveOnline(SysUserOnlineDO online) {
        return sysUserOnlineMapper.saveOnline(online);
    }

    @Override
    public List<SysUserOnlineDO> selectUserOnlineList(SysUserOnline userOnline) {
        return sysUserOnlineMapper.selectUserOnlineList(userOnline);
    }

    @Override
    public List<SysUserOnlineDO> selectOnlineByExpired(String lastAccessTime) {
        return sysUserOnlineMapper.selectOnlineByExpired(lastAccessTime);
    }
}
