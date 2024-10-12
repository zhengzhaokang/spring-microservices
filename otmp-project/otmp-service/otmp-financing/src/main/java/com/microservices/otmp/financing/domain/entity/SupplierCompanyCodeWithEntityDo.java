package com.microservices.otmp.financing.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class SupplierCompanyCodeWithEntityDo extends SupplierCompanyCodeDo {
    private String entityName;
    private Integer currency;
    private String region;
    private String registerAddress;
    private Integer type;
    private String bankId;
}
