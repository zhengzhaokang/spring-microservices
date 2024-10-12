package com.microservices.otmp.financing.domain.vo.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
public class AvailableInvoiceCardVo extends InvoiceCardVo {

    private Integer increment;
}
