package com.microservices.otmp.financing.domain.param.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class AvailableSelectListParam extends AvailableInvoiceListParam {

    private boolean first;
    private Double financingAmount;
    private String maturityDate;

}
