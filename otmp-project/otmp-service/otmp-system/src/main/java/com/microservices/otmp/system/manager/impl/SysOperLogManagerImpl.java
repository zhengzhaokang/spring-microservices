package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysOperLog;
import com.microservices.otmp.system.domain.entity.SysOperLogDO;
import com.microservices.otmp.system.manager.SysOperLogManager;
import com.microservices.otmp.system.mapper.SysOperLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:21
 */

@Service
public class SysOperLogManagerImpl implements SysOperLogManager {

    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    @Override
    public void insertOperlog(SysOperLogDO operLog) {
        sysOperLogMapper.insertOperlog(operLog);
    }

    @Override
    public List<SysOperLogDO> selectOperLogList(SysOperLog operLog) {
        return sysOperLogMapper.selectOperLogList(operLog);
    }

    @Override
    public int deleteOperLogByIds(String[] ids) {
        return sysOperLogMapper.deleteOperLogByIds(ids);
    }

    @Override
    public SysOperLogDO selectOperLogById(Long operId) {
        return sysOperLogMapper.selectOperLogById(operId);
    }

    @Override
    public void cleanOperLog() {
        sysOperLogMapper.cleanOperLog();
    }
}
