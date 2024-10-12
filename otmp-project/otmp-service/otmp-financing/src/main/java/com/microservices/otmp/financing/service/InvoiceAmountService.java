package com.microservices.otmp.financing.service;

import com.microservices.otmp.financing.domain.param.invoice.InvoiceQueryParam;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceAmountInVo;

public interface InvoiceAmountService {
    InvoiceAmountInVo availableAmount(InvoiceQueryParam param);

    InvoiceAmountInVo submittedAmount(InvoiceQueryParam param);

    InvoiceAmountInVo rejectedAmount(InvoiceQueryParam param);

    InvoiceAmountInVo financedAmount(InvoiceQueryParam param);
}
