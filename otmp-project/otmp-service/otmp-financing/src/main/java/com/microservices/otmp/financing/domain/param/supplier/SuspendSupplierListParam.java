package com.microservices.otmp.financing.domain.param.supplier;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SuspendSupplierListParam extends SupplierListParam{
    private String activationDate;
    private String financingModel;
    private String supplierModel;
    private String email;
}
