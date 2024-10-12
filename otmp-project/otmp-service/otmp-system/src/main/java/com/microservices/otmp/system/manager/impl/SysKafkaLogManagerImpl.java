package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysKafkaLog;
import com.microservices.otmp.system.domain.entity.SysKafkaLogDO;
import com.microservices.otmp.system.manager.SysKafkaLogManager;
import com.microservices.otmp.system.mapper.SysKafkaLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:21
 */

@Service
public class SysKafkaLogManagerImpl implements SysKafkaLogManager {

    @Autowired
    private SysKafkaLogMapper sysKafkaLogMapper;

    @Override
    public void insertKafkalog(SysKafkaLogDO operLog) {
        sysKafkaLogMapper.insertKafkalog(operLog);
    }

    @Override
    public List<SysKafkaLogDO> selectKafkaLogList(SysKafkaLog operLog) {
        return sysKafkaLogMapper.selectKafkaLogList(operLog);
    }

    @Override
    public int deleteKafkaLogByIds(String[] ids) {
        return sysKafkaLogMapper.deleteKafkaLogByIds(ids);
    }

    @Override
    public SysKafkaLogDO selectKafkaLogById(Long operId) {
        return sysKafkaLogMapper.selectKafkaLogById(operId);
    }

    @Override
    public void cleanKafkaLog() {
        sysKafkaLogMapper.cleanKafkaLog();
    }
}
