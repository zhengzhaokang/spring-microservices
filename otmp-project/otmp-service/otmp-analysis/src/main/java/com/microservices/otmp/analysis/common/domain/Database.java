package com.microservices.otmp.analysis.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Database {

    @JsonProperty("schema")
    private String schema;
    @JsonProperty("sequence")
    private String sequence;
    @JsonProperty("connector")
    private String connector;
    @JsonProperty("lsn")
    private Long lsn;
    @JsonProperty("name")
    private String name;
    @JsonProperty("txId")
    private Integer txId;
    @JsonProperty("version")
    private String version;
    @JsonProperty("ts_ms")
    private Long tsMs;
    @JsonProperty("snapshot")
    private String snapshot;
    @JsonProperty("db")
    private String db;
    @JsonProperty("table")
    private String table;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public Long getLsn() {
        return lsn;
    }

    public void setLsn(Long lsn) {
        this.lsn = lsn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTxId() {
        return txId;
    }

    public void setTxId(Integer txId) {
        this.txId = txId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getTsMs() {
        return tsMs;
    }

    public void setTsMs(Long tsMs) {
        this.tsMs = tsMs;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
