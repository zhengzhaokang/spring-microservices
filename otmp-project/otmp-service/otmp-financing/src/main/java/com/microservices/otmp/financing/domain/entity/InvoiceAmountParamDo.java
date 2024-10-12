package com.microservices.otmp.financing.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InvoiceAmountParamDo {

    private String supplierId;

    private String status;

    private String invoiceType;
}
