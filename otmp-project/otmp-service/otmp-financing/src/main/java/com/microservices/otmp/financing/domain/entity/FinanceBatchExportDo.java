package com.microservices.otmp.financing.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FinanceBatchExportDo extends FinanceBatchDo{

    private String supplierName;
    private String entityName;

}
