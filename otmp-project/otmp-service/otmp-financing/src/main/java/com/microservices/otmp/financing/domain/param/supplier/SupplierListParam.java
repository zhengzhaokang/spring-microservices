package com.microservices.otmp.financing.domain.param.supplier;

import com.microservices.otmp.common.core.page.PageDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SupplierListParam extends PageDomain {

    private String supplierName;
    private Long userId;
}
