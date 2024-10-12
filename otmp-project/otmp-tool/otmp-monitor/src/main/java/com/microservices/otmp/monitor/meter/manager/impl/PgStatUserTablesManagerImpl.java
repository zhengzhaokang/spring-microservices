package com.microservices.otmp.monitor.meter.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microservices.otmp.monitor.meter.domain.PgStatUserTables;
import com.microservices.otmp.monitor.meter.manager.PgStatUserTablesManager;
import com.microservices.otmp.monitor.meter.mapper.PgStatUserTablesMapper;
import org.springframework.stereotype.Service;

/**
 * @author db117
 * @since 2023/4/7
 */
@Service
public class PgStatUserTablesManagerImpl extends ServiceImpl<PgStatUserTablesMapper, PgStatUserTables> implements PgStatUserTablesManager {
}
