package com.microservices.otmp.bank.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * created with IntelliJ IDEA.
 * user: licf
 * Date: 2023/8/23
 * time: 16:23
 * Description:
 */
@Data
public class DbsDTO {

    private String no;
    /** 供应商id */
    private String seller;
    /** 供应商组织id */
    private String buyer;
    /** 发票*/
    private String instrumentType;
    /** 发票参考*/
    private String instrumentNumber;
    /** 发票签发日期 */
    private Date instrumentDate;
    /** 发票货币*/
    private String instrumentCurrency;
    /** 发票金额 */
    private String instrumentAmount;
    /** 发票到期日*/
    private Date instrumentDueDate;
    /** 发票付款到期日*/
    private Date paymentDueDate;
    private String correspondingPONumber;
    private String transportDocumentReferenceNumber;
    private String transportDocumentDate;
    private String portOfLoading;
    private String portOfdischarge;
    private String goods;
    private String nameOfShipper;
    private String nameOfShippingCompany;
    private String nameOfVessel;
    private String programsId;//SF
    private String financeAmount;
    private String sellerFees;
    private String buyerName;
    private String sellerName;
    private String externalRefNo;//按照规则生成


}
