package com.microservices.otmp.financing.domain.vo.invoice;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode(callSuper = false)
@Data
public class FinancedInvoiceVo {

    private String id;
    private String entityName;
    private String batchNum;
    private Double rate;
    private Double discount;
    private Double discountedAmount;
    private String discountedDate;
}
