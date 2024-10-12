package com.microservices.otmp.financing.domain.vo.invoice;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaturityDateAmountVo {

    private Double amount;
    private String companyCode;
    private String maturityDate;
    private String status;
    private String supplierCode;

}
