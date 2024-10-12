package com.microservices.otmp.financing.domain.dto;

import com.microservices.otmp.financing.domain.entity.FinanceInvoiceApDo;
import com.microservices.otmp.financing.domain.entity.SupplierUniqueIdDo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InvoiceSelectDTO {

    private List<String> companyCodes;
    private List<SupplierUniqueIdDo> supplierUniqueIdDos;
    private Integer debitTotal;
    private List<FinanceInvoiceApDo> debits;
    private List<FinanceInvoiceApDo> credits;
    private boolean fromCache;
    private Map<String, Collection<String>> relations;

}
