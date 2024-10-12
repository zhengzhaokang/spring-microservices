package com.microservices.otmp.system.enums;

import lombok.Getter;

@Getter
public enum VisibleEnum {
    /**
     *
     */
    DISPLAY("0","显示"),


    /**
     *
     */
    HIDE("1","隐藏"),
    ;

    private final String code;
    private final String name;

    VisibleEnum(String code,String name) {
        this.name = name;
        this.code = code;
    }
}
