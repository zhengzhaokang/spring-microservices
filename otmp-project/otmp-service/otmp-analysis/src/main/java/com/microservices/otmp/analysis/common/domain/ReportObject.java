package com.microservices.otmp.analysis.common.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 标记为导出对象
 *
 * @author db117
 * @since 2023/4/20
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface ReportObject {

    /**
     * 报表唯一标识
     */
    default String ident() {
        return toString();
    }
}
