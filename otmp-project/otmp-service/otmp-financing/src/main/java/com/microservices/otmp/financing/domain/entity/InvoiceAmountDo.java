package com.microservices.otmp.financing.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InvoiceAmountDo {

    private String companyCode;

    private String invoiceType;

    private BigDecimal amount;

    private BigDecimal count;
}
