package com.microservices.otmp.system.enums;

import lombok.Getter;

@Getter
public enum DeleteFlagEnum {
    /**
     *
     */
    DELETE("1","Y"),


    /**
     *
     */
    NOT_DELETE("0","N")
    ;

    private final String code;
    private final String name;

    DeleteFlagEnum(String code,String name) {
        this.name = name;
        this.code = code;
    }
}
