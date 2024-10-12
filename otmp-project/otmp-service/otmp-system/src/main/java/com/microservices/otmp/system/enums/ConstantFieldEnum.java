package com.microservices.otmp.system.enums;

import lombok.Getter;

@Getter
public enum ConstantFieldEnum {

    V_MODEL("__vModel__"),
    CHECKED("checked"),
    FIELDS("fields"),
    FIELD_RANGE("fieldRange"),
    CONFIG("__config__"),
    LABEL_WIDTH("labelWidth"),
    LABEL("label"),
    TAG_ICON("tagIcon"),
    SPAN("span"),
    WIDTH("width"),
    PLACEHOLDER("placeholder"),
    STYLE("style"),
    FORM_ID("formId"),
    RENDER_KEY("renderKey"),
    ;

    ConstantFieldEnum(String name) {
        this.name = name;
    }

    private String name;

}
