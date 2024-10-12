package com.microservices.otmp.financing.domain.vo.invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvoiceQueryVO {
    private Integer count;

    private List<InvoiceQueryDto> invoiceQueryDtoList;
}
