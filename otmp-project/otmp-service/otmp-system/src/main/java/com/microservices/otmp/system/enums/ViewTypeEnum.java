package com.microservices.otmp.system.enums;

import lombok.Getter;

/**
 * @author wanghuan
 */
@Getter
public enum ViewTypeEnum {


    LIST("list", "列表页"),
    EDIT("edit", "编辑页"),
    DETAILS("details", "详情页"),
    ;

    private final String code;
    private final String name;

    ViewTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
