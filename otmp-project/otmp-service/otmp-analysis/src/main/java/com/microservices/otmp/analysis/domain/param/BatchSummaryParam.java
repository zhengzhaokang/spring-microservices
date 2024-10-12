package com.microservices.otmp.analysis.domain.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BatchSummaryParam {

    @Deprecated
    private String startTime;
    @Deprecated
    private String endTime;

    public static final Integer VALUE_YEAR = 1;
    public static final Integer VALUE_MONTH = 2;

    /**
     * 图表时间类型，1：年，2：月
     */
    @NotNull
    private Integer type;
    /**
     * 图表时间，示例：2023（value=1时），2023-11（value=2时）
     */
    @NotNull
    private String typeValue;
    private String bankId;
    private Long supplierId;
    private Long entityId;
    private Integer pageNum;
    private Integer pageSize;

    private Long userId;

}
