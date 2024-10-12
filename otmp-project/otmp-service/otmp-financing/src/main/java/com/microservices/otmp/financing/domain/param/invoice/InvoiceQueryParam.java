package com.microservices.otmp.financing.domain.param.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceQueryParam {

    private String supplierId;

    private String entityId;

    private String invoiceNo;

    private String submittedDateStart;

    private String submittedDateEnd;

    private String batchNumber;

    private String disCountDateStart;

    private String disCountDateEnd;

    private Integer type;

    private String invoiceType;

    private String invoiceNumber;

    private Integer limit;

    private Integer offset;

    private Integer pageSize;

    private Integer pageNum;

    private Integer rateType;

    private String startDate;

    private String endDate;

    private String status;

    private Long userId;
}
