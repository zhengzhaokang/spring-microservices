package com.microservices.otmp.analysis.common.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据信息
 *
 * @author guowb1
 * @date 2022/12/23 16:56
 */
@Data
public class DataSourceDTO implements Serializable {
    private String version;
    private String connector;
    private String name;
    @JSONField(name = "ts_ms")
    private Long tsMs;
    private String snapshot;
    private String db;
    private String sequence;
    private String schema;
    private String table;
    private Long txId;
    private Long lsn;
}
