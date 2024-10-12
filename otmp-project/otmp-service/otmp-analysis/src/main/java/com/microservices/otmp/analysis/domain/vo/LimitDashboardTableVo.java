package com.microservices.otmp.analysis.domain.vo;

import com.microservices.otmp.common.annotation.Excel;
import lombok.Data;

@Data
public class LimitDashboardTableVo {

    @Excel(name="Bank")
    private String bankName;
    @Excel(name = "Entity")
    private String entityName;
    @Excel(name = "Limit")
    private String limit;
    @Excel(name = "Outstanding")
    private String outstanding;
    @Excel(name = "Date")
    private String date;

}
