package com.microservices.otmp.financing.domain.vo.invoice;

import lombok.Data;

@Data
public class AvailableCardWrapperVo {

    private InvoiceCardVo availableFinance;

    private InvoiceCardVo availableCredit;

}
