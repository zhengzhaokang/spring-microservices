package com.microservices.otmp.analysis.common.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author liuzy
 */
@Data
public class SupplierAccountsValue {

    private String valueDate;
    private String vendorCode;
    private BigDecimal amount;
    private Long invoiceCount;
}
