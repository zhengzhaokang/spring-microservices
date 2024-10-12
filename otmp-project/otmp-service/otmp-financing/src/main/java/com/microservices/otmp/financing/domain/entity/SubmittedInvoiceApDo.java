package com.microservices.otmp.financing.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class SubmittedInvoiceApDo extends BaseInvoiceInfo{

    private String batchNumber;
    private String submittedDate;
    private String bankName;

}
