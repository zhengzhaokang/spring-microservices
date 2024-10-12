package com.microservices.otmp.erp.domain;

import lombok.Data;

/**
 * @author sunpan1
 */
@Data
public class SupplierCalendarConfig {

    private String paymentCycle;

    private String weekOfTheMonth;

    private String basisOfMaturityDateCalculation;


}