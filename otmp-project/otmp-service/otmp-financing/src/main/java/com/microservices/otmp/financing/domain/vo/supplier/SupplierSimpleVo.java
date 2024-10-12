package com.microservices.otmp.financing.domain.vo.supplier;

import lombok.Data;

import java.util.List;

@Data
public class SupplierSimpleVo {

    private String id;
    private String supplierName;
    private String firstName;
    private String lastName;
    private String destination;
    private String email;
    private String phoneRegion;
    private String phone;
    private String financingModel;
    private String supplierModel;
    private String currency;
    private Double minimumNetFinancingAmount;
    private String toUpPricing;
    private Integer invalidDaysBeforeMaturityDate;

    private String basisOfMaturityDateCalculation;
    private String paymentCycle;
    private String weekOfTheMonth;
    private Integer traceBackHistoryInvoiceDays;

    private Boolean onshore;
    private String microservicesEntityName;
    private String preferredFinancingModel;
    private String primarySupplierCode;
    private String maturityDateChangeable;

    private List<String> fileIds;
    private Boolean needUpdate;
}
