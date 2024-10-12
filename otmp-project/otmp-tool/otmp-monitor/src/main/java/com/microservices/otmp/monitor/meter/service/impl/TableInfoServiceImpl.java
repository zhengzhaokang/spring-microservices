package com.microservices.otmp.monitor.meter.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.microservices.otmp.monitor.meter.domain.PgStatUserTables;
import com.microservices.otmp.monitor.meter.manager.PgStatUserTablesManager;
import com.microservices.otmp.monitor.meter.service.DataRefresh;
import com.microservices.otmp.monitor.meter.service.TableInfoService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.MultiGauge;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.BaseUnits;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
public class TableInfoServiceImpl implements TableInfoService, DataRefresh {
    @Autowired
    private PgStatUserTablesManager pgStatUserTablesManager;
    private final Cache<String, List<PgStatUserTables>> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(7, TimeUnit.SECONDS)// 最多 7 秒查询查询一次数据库
            .build();
    private final Map<String, MultiGauge> gaugeMap = new HashMap<>();


    @Override
    @SuppressWarnings("unchecked")
    public void refresh(MeterRegistry registry) {
        for (MeterEnum value : MeterEnum.values()) {
            gaugeMap.computeIfAbsent(value.meterName, s -> MultiGauge.builder(value.meterName)
                    .description(value.desc)
                    .baseUnit(BaseUnits.ROWS)
                    .register(registry));
            MultiGauge multiGauge = gaugeMap.get(value.meterName);

            multiGauge.register(
                    getByCache()
                            .stream()
                            .map(value::buildRow)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList()),
                    true);

        }
    }

    private List<PgStatUserTables> getByCache() {
        try {
            return cache.get("1", () -> pgStatUserTablesManager.lambdaQuery()
                    .notIn(PgStatUserTables::getSchemaname, "xxl_job", "public")
                    .list());
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    public enum MeterEnum {
        SEQ_SCAN("sdms_table_seq_scan", "顺序扫描的次数", PgStatUserTables::getSeqScan),
        SEQ_TUP_READ("sdms_table_seq_tup_read", "顺序扫描读取的行数", PgStatUserTables::getSeqTupRead),
        IDX_SCAN("sdms_table_idx_scan", "索引扫描的次数", PgStatUserTables::getIdxScan),
        IDX_TUP_FETCH("sdms_table_idx_tup_fetch", "索引扫描获取的行数", PgStatUserTables::getIdxTupFetch),
        TUP_INS("sdms_table_tup_ins", "插入的行数", PgStatUserTables::getNTupIns),
        TUP_UPD("sdms_table_tup_upd", "更新的行数", PgStatUserTables::getNTupUpd),
        TUP_DEL("sdms_table_tup_del", "删除的行数", PgStatUserTables::getNTupDel),
        LIVE_TUP("sdms_table_live_tup", "表中存活的行数", PgStatUserTables::getNLiveTup),
        DEAD_TUP("sdms_table_dead_tup", "表中死亡的行数", PgStatUserTables::getNDeadTup),
        ;
        /**
         * 指标名称
         */
        private final String meterName;
        /**
         * 指标含义
         */
        private final String desc;

        /**
         * 从 PgStatUserTables 获取数据
         */
        private final Function<PgStatUserTables, Long> findValueFunc;

        MeterEnum(String meterName, String desc, Function<PgStatUserTables, Long> findValueFunc) {
            this.meterName = meterName;
            this.desc = desc;
            this.findValueFunc = findValueFunc;
        }

        public MultiGauge.Row<?> buildRow(PgStatUserTables tables) {
            Tags tags = Tags.of("schemaname", tables.getSchemaname(),
                    "table_name", tables.getRelname()
            );
            // 这个表查询的都是 行数，过滤掉无效数据
            Long rows = findValueFunc.apply(tables);
            if (rows == null || rows == 0) {
                return null;
            }
            return MultiGauge.Row.of(tags, rows);
        }
    }

}
