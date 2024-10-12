package com.microservices.otmp.financing.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class InvoiceSubmitRecordExt extends InvoiceSubmitRecord{

    private String currency;
    private String status;

}
