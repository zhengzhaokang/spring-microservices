package com.microservices.otmp.bank.domain.dto;

import com.microservices.otmp.finance.domain.entity.FinanceInvoiceApDO;
import com.microservices.otmp.finance.domain.entity.FinanceInvoiceApDO;
import lombok.Data;

/**
 *
 */
@Data
public class FinanceInvoiceApHasSupplierDTO extends FinanceInvoiceApDO {

    private String supplierUniqueId;

    private Integer currency;
}
