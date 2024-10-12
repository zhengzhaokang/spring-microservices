package com.microservices.otmp.financing.domain.vo.invoice;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvoiceAmountInVo {

    private String invoiceAmount;

    private String invoiceCount;

    private String creditAmount;

    private String creditCount;

    private String netAmount;

    private String netCount;

    private Map<String, String> invoiceAmountDetailMap;

    private Map<String, String> creditAmountDetailMap;
}
