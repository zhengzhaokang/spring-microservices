package com.microservices.otmp.financing.domain.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InvoiceGroupAmount {

    private String bankId;
    private Long entityId;
    private Long supplierId;
    private String supplierName;
    private String batchNumber;
    private Date maturityDate;
    private String type;
    private BigDecimal amount;
}
