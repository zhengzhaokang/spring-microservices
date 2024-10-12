package com.microservices.otmp.system.enums;

import lombok.Getter;

/**
 * 用户类型
 */
@Getter
public enum UserTypeEnum {
    Manager("00","Manager"),
    Anchor("01","Anchor"),
    Supplier("02","Supplier"),
    ;

    private final String code;
    private final String name;

    UserTypeEnum(String code,String name) {
        this.name = name;
        this.code = code;
    }
}
