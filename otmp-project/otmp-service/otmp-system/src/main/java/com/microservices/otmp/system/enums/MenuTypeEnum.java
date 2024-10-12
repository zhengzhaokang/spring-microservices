package com.microservices.otmp.system.enums;

import lombok.Getter;

@Getter
public enum MenuTypeEnum {
    /**
     *
     */
    M("M","目录"),
    C("C","菜单"),

    /**
     *
     */
    F("F","按钮"),
    ;

    private final String code;
    private final String name;

    MenuTypeEnum(String code,String name) {
        this.name = name;
        this.code = code;
    }

}
