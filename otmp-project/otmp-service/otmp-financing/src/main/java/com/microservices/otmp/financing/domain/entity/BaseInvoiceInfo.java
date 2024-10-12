package com.microservices.otmp.financing.domain.entity;

import lombok.Data;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class BaseInvoiceInfo {
    // Waiting,Financing,Reject,Financed,Invalid,Confirm
    public static final String STATUS_WAITING = "Waiting";
    public static final String STATUS_FINANCING = "Financing";
    public static final String STATUS_REJECT = "Reject";
    public static final String STATUS_FINANCED = "Financed";
    public static final String STATUS_INVALID = "Invalid";
    public static final String STATUS_CONFIRM = "Confirm";

    @Id
    private Long id;
    /**
     * 公司代码
     */
    private String companyCode;
    /**
     * 数据状态
     */
    private Boolean deleteFlag;
    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;
    private String invoiceUniqueId;
    private String vendorCode;
    private String fiscalYear;
    private String invoiceAdditionalReference;
    private String eccInvoiceNumber;
    private String invoiceIssueDate;
    private String invoiceBaselineDate;
    private String invoiceDueDate;

    private String invoiceCurrency;
    private Double invoiceAmount;
    private String typeOfBilling;
    private String enterDate;
    private String invoicePayDate;
    private LocalDateTime maturityDate;
    private String invoiceType;
    private String invoiceReference;
    private String status;

}
