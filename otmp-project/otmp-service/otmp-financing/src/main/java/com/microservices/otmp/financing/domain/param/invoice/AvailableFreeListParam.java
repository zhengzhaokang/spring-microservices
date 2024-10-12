package com.microservices.otmp.financing.domain.param.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class AvailableFreeListParam extends AvailableInvoiceListParam {

    private String issueStartDate;
    private String issueEndDate;
    private String maturityStartDate;
    private String maturityEndDate;
    private String bankId;
}
