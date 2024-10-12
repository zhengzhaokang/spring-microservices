package com.microservices.otmp.financing.domain.vo.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class InvoiceCardVo {

    private String amount;
    private Integer count;
    private String currency;

}
