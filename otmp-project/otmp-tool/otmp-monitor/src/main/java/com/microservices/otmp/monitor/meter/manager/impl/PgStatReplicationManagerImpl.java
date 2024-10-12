package com.microservices.otmp.monitor.meter.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microservices.otmp.monitor.meter.domain.PgStatReplication;
import com.microservices.otmp.monitor.meter.manager.PgStatReplicationManager;
import com.microservices.otmp.monitor.meter.mapper.PgStatReplicationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author db117
 * @since 2023/4/13
 */
@Service
@Slf4j
public class PgStatReplicationManagerImpl extends ServiceImpl<PgStatReplicationMapper, PgStatReplication> implements PgStatReplicationManager {
    @Override
    public List<PgStatReplication> all() {
        return baseMapper.list();
    }
}
