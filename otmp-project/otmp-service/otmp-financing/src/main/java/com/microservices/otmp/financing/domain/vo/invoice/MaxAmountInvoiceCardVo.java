package com.microservices.otmp.financing.domain.vo.invoice;

import com.microservices.otmp.financing.domain.vo.supplier.SupplierVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaxAmountInvoiceCardVo extends InvoiceCardVo{

    private String minAmount;
    private String financingModel;
    private String topUpPricing;
    private String supplierModel;
    private String invalidDaysBefore;
    private String basisOfMaturityDate;
    private String traceBackDays;

}
