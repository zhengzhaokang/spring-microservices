package com.microservices.otmp.analysis.common.domain;

import lombok.Data;

/**
 * 最原始的数据，后面会转换成 {@link InputObject}
 *
 * @author db117
 * @since 2023/4/20
 */
@Data
public class SourceObject {
    /**
     * 之前的数据（json）
     */
    private String before;
    /**
     * 之后的数据（json）
     */
    private String after;
    /**
     * 表名称
     */
    private String table;
    /**
     * db 名称
     */
    private String db;
    private String schema;
    private String op;


    public static SourceObject of(DebeziumDataDTO dto) {
        SourceObject sourceObject = new SourceObject();
        sourceObject.setDb(dto.getSource().getDb());
        sourceObject.setTable(dto.getSource().getTable());
        sourceObject.setSchema(dto.getSource().getSchema());
        sourceObject.setAfter(dto.getAfter().toPrettyString());
        sourceObject.setBefore(dto.getBefore().toPrettyString());

        sourceObject.setOp(dto.op);

        return sourceObject;
    }
}
