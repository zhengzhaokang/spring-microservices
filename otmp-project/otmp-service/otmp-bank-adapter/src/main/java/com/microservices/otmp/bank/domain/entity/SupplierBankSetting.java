package com.microservices.otmp.bank.domain.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class SupplierBankSetting {
    private Long id;

    private Integer financingModel;

    private BigDecimal minimumNetFinancingAmount;

    private Integer currency;

    private Integer toUpPricing;

    private Integer invalidDaysBeforeManurityDate;

    private Integer supplierModel;

    private String bankCode;

    private String supplierCode;

    private String supplierUniqueId;

    private String buyerOrg;

    private String buyerOrgId;

    private String maximumInvoiceTendor;

    private String daysFromPostingDate;

    private Integer invoicePercentage;

    private String margin;

    private String maximumFinanceTendor;

    private String benchMark;

    private Integer deleteFlag;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String companyCode;
}