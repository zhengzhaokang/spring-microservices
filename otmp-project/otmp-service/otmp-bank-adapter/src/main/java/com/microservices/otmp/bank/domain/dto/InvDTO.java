package com.microservices.otmp.bank.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * created with IntelliJ IDEA.
 * user: licf
 * Date: 2023/8/10
 * time: 14:33
 * Description:
 */
@Data
public class InvDTO {

    //INV 数据
    private String identifier;
    /**
     * 供应商主数据(供应商id)
     */
    private String erpId;
    /**
     * 发票参考
     */
    private String invoiceReference;
    /**
     * 发票签发日期
     */
    private Date invoiceIssueDate;
    /**
     * 发票到期日
     */
    private Date invoiceDueDate;
    /**
     * 发票货币
     */
    private Integer invoiceCurrency;
    /**
     * 发票金额
     */
    private BigDecimal invoiceAmount;
    /**
     * 附件1
     */
    private String additionalReference1;
    /**
     * 附件2
     */
    private Date additionalReference2;
    /**
     * 附件3
     */
    private String additionalReference3;
}
