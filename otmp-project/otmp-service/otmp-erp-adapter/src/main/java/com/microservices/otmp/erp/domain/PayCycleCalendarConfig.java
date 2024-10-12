package com.microservices.otmp.erp.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author sunpan1
 */
@Data
public class PayCycleCalendarConfig {

    private String zyear;

    private String zmonth;

    private String firstWeek;

    private String secondWeek;

    private String thirdWeek;

    private String fourthWeek;

    private String country;

}