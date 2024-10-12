package com.microservices.otmp.analysis.common.enums;

import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guowb1
 * @description 等同于io.debezium.data.Envelope.Operation
 * @date 2022/12/27 19:20
 */
@Getter
public enum DebeziumOpTypeEnum {
    READ("r"),
    CREATE("c"),
    UPDATE("u"),
    DELETE("d"),
    TRUNCATE("t");;

    private final String code;

    DebeziumOpTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static final Map<String, DebeziumOpTypeEnum> enumMap = new HashMap<>();

    static {
        EnumSet<DebeziumOpTypeEnum> debeziumOpTypeEnumEnumSet = EnumSet.allOf(DebeziumOpTypeEnum.class);
        for (DebeziumOpTypeEnum debeziumOpTypeEnum : debeziumOpTypeEnumEnumSet) {
            enumMap.put(debeziumOpTypeEnum.getCode(), debeziumOpTypeEnum);
        }
    }

    public static DebeziumOpTypeEnum getOpTypeEnumByCode(String code) {
        return enumMap.get(code);
    }

}
