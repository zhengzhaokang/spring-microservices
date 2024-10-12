package com.microservices.otmp.analysis.domain.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LimitDashboardDo {

    private BigDecimal amount;
    private String date;

}
