package com.microservices.otmp.analysis.common.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SupplierAmount {

    private String supplierId;
    private String supplierName;
    private BigDecimal totalAmount;
}
