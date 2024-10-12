package com.microservices.otmp.monitor.meter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.microservices.otmp.monitor.meter.domain.PgStatReplication;

import java.util.List;

/**
 * @author db117
 * @since 2023/4/7
 */
public interface PgStatReplicationMapper extends BaseMapper<PgStatReplication> {

    List<PgStatReplication> list();
}
