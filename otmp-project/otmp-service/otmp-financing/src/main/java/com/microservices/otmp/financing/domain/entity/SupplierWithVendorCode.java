package com.microservices.otmp.financing.domain.entity;

import com.microservices.otmp.financing.domain.entity.SupplierDo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class SupplierWithVendorCode extends SupplierDo {

    private String supplierCode;

}
