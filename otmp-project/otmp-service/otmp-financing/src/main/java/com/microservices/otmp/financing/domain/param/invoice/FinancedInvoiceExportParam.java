package com.microservices.otmp.financing.domain.param.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FinancedInvoiceExportParam {

    /**
     * 多个batchNumber
     */
    private String batchNumbers;

    private List<String> batchNumberList;

    private Long userId;

}
