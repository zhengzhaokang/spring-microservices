package com.microservices.otmp.financing.domain.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "invoice_submit_record")
@Data
public class InvoiceSubmitRecord {

    @Id
    private Long id;
    private Long supplierId;
    private String supplierName;
    private Long batchId;
    private String batchNum;
    private Long batchInvoiceId;
    private String bankId;
    private Long entityId;
    private Double amount;
    private String invoiceType;
    private LocalDateTime maturityDate;
    private String deleteFlag;
    private String createBy;
    private LocalDateTime createTime;
    private String updateBy;
    private LocalDateTime updateTime;
    private String discountDate;
    private Long invoiceId;
    private String invoiceReference;
    private String vendorCode;
}
