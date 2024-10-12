package com.microservices.otmp.analysis.domain.param;

import lombok.Data;

@Data
public class LimitDashboardParam {

    private String bankId;
    private Long entityId;
    private String startTime;
    private String endTime;
    private Integer pageNum;
    private Integer pageSize;

}
