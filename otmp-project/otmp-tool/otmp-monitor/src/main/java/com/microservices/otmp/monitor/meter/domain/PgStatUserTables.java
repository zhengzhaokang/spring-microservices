package com.microservices.otmp.monitor.meter.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * pg_stat_user_tables
 *
 * @author db117
 * @since 2023/4/7
 */
@NoArgsConstructor
@Data
@TableName("pg_stat_user_tables")
public class PgStatUserTables {

    private String relid;

    private String schemaname;

    private String relname;

    private Long seqScan;

    private Long seqTupRead;

    private Long idxScan;

    private Long idxTupFetch;

    private Long nTupIns;

    private Long nTupUpd;

    private Long nTupDel;

    private Long nTupHotUpd;

    private Long nLiveTup;
    private Long nDeadTup;
}
