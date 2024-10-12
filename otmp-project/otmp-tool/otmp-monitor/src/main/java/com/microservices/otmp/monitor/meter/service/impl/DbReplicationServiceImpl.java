package com.microservices.otmp.monitor.meter.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.microservices.otmp.monitor.meter.domain.PgStatReplication;
import com.microservices.otmp.monitor.meter.manager.PgStatReplicationManager;
import com.microservices.otmp.monitor.meter.service.DataRefresh;
import com.microservices.otmp.monitor.meter.service.DbReplicationService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.MultiGauge;
import io.micrometer.core.instrument.Tags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author db117
 * @since 2023/4/13
 */
@Service
@Slf4j
public class DbReplicationServiceImpl implements DbReplicationService, DataRefresh {
    @Autowired
    private PgStatReplicationManager pgStatReplicationManager;
    private final Cache<String, List<PgStatReplication>> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(7, TimeUnit.SECONDS)// 最多 7 秒查询查询一次数据库
            .build();

    private final Map<String, MultiGauge> map = new HashMap<>();


    @Override
    @SuppressWarnings("unchecked")
    public void refresh(MeterRegistry registry) {
        for (MeterEnum value : MeterEnum.values()) {
            map.computeIfAbsent(value.meterName,
                    s -> MultiGauge.builder(value.meterName)
                            .description(value.desc)
                            .baseUnit("sec")
                            .register(registry)
            );

            // 获取 MultiGauge 指标
            MultiGauge multiGauge = map.get(value.meterName);

            // 更新数据
            multiGauge
                    .register(
                            getByCache()
                                    .stream()
                                    .map(value::buildRow)
                                    .collect(Collectors.toList())
                            , true);
        }
    }

    public List<PgStatReplication> getByCache() {
        try {
            return cache.get("1", () -> pgStatReplicationManager.all());
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    public enum MeterEnum {
        WRITE("sdms_db_replication_write_leg", "写入延迟", PgStatReplication::getWriteLagSecond),
        FLUSH("sdms_db_replication_flush_leg", "刷新延迟", PgStatReplication::getFlushLagSecond),
        REPLAY("sdms_db_replication_replay_leg", "重放延迟", PgStatReplication::getReplayLagSecond);;
        /**
         * 指标名称
         */
        private final String meterName;
        /**
         * 指标含义
         */
        private final String desc;

        /**
         * 从 PgStatReplication 获取数据
         */
        private final Function<PgStatReplication, Double> findValueFunc;

        MeterEnum(String meterName, String desc, Function<PgStatReplication, Double> findValueFunc) {
            this.meterName = meterName;
            this.desc = desc;
            this.findValueFunc = findValueFunc;
        }

        public MultiGauge.Row<?> buildRow(PgStatReplication replication) {
            Tags tags = Tags.of("slot_name", replication.getSlotName(),
                    "client_addr", replication.getClientAddr(),
                    "database", replication.getDatabase(),
                    "pid", replication.getPid().toString(),
                    "usename", replication.getUsename()
            );
            return MultiGauge.Row.of(tags, findValueFunc.apply(replication));
        }
    }
}
