package com.microservices.otmp.analysis.common.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.io.Serializable;

/**
 * Debezium 数据结构
 *
 * @author guowb1
 * @date 2022/12/23 16:52
 */
@Data
public class DebeziumDataDTO implements Serializable {
    protected JsonNode before;
    protected JsonNode after;
    protected DataSourceDTO source;
    protected String op;
    @JSONField(name = "ts_ms")
    protected Long tsMs;
}
