package com.microservices.otmp.finance;

import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.domain.InvoiceAp;

import java.util.List;

import static com.microservices.otmp.common.exception.message.DefaultErrorMessage.INVOICE_REFERENCE_IS_EMPTY_ERROR;

/**
 * order 7
 * Reference Valid
 */
public class ReferenceFilter implements HygieneCheckFilter{
    @Override
    public CheckResult filter(List<InvoiceAp> invoices) {
        return null;
    }

    @Override
    public CheckResult filter(InvoiceAp invoice) {
        CheckResult result = new CheckResult();
        boolean pass = StringUtils.isNotEmpty(invoice.getInvoiceReference());
        result.setPass(pass);
        if(!pass){
            result.setMsg(DefaultErrorMessage.ErrDescription.get(INVOICE_REFERENCE_IS_EMPTY_ERROR));
        }
        return result;
    }
}
