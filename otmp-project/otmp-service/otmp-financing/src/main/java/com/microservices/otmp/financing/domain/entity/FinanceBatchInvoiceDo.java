package com.microservices.otmp.financing.domain.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name="finance_batch_invoice")
@Data
public class FinanceBatchInvoiceDo {

    @Id
    private Long id;
    private String batchNumber;
    private String bankId;
    private Long invoiceId;
    private Long entityId;
    private String supplierCode;
    private String invoiceReference;

    private Long supplierId;

    /**
     * 数据状态
     */
    private Boolean deleteFlag;
    private LocalDateTime createTime;
    private String createBy;
    private LocalDateTime updateTime;
    private String updateBy;
}
