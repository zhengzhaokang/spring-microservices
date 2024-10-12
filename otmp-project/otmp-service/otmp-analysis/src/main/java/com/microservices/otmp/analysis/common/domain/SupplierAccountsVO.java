package com.microservices.otmp.analysis.common.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author liuzy
 */
@Data
public class SupplierAccountsVO {

    private String valueDate;
    private BigDecimal amount;
    private Long invoiceCount;
}
