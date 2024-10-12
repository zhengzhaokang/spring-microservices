package com.microservices.otmp.monitor.meter.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.microservices.otmp.monitor.meter.domain.PgStatReplication;

import java.util.List;

/**
 * @author db117
 * @since 2023/4/13
 */
public interface PgStatReplicationManager extends IService<PgStatReplication> {
    List<PgStatReplication> all();
}
