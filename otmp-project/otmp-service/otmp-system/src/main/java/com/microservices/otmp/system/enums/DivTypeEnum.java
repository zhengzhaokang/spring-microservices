package com.microservices.otmp.system.enums;

import lombok.Getter;

/**
 * @author wanghuan
 */
@Getter
public enum DivTypeEnum {
    /**
     *
     */
    FROM(1, "from"),

    /**
     *
     */
    LIST(2, "list"),
    ;

    private final Integer code;
    private final String type;

    DivTypeEnum(Integer code, String type) {
        this.type = type;
        this.code = code;
    }

}
