package com.microservices.otmp.monitor.meter.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * pg_stat_replication
 *
 * @author db117
 * @since 2023/4/10
 */
@NoArgsConstructor
@Data
public class PgStatReplication {
    private String slotName;

    private String database;

    private Integer pid;

    private String usename;

    private String clientAddr;

    private Double writeLagSecond;

    private Double flushLagSecond;

    private Double replayLagSecond;
}
