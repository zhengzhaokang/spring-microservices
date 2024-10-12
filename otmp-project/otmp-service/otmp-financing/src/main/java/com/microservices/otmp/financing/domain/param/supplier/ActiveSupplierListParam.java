package com.microservices.otmp.financing.domain.param.supplier;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class ActiveSupplierListParam extends SupplierListParam{
    private String activationDate;
    private String financingModel;
    private String supplierModel;
    private String email;
}
