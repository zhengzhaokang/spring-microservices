package com.microservices.otmp.financing.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OnBoardingSupplierByBankDo {

    private String bankId;
    private Long entityId;
    private Long supplierId;
    private String supplierName;
    private LocalDateTime activationDate;

}
