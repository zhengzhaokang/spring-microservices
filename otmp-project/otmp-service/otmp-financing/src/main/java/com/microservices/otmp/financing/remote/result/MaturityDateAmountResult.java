package com.microservices.otmp.financing.remote.result;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaturityDateAmountResult {

    private Double amount;
    private String companyCode;
    private String maturityDate;
    private String status;
    private String supplierCode;

}
