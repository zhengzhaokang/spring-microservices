package com.microservices.otmp.analysis.domain.vo;

import com.microservices.otmp.common.annotation.Excel;
import lombok.Data;

@Data
public class LimitDashboardVo {

    private String limitAmount;
    private String outstandingAmount;
    private String date;

}
