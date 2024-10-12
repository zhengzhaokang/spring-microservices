package com.microservices.otmp.finance;

import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.domain.InvoiceAp;

import java.util.List;

import static com.microservices.otmp.common.exception.message.DefaultErrorMessage.INVOICEPAYDATE_OR_INVOICEDUEDATE_ERROR;

/**
 * order 11
 * Reference Not Start With DNF
 */
public class PayDateFilter implements HygieneCheckFilter{
    @Override
    public CheckResult filter(List<InvoiceAp> invoices) {
        return null;
    }

    @Override
    public CheckResult filter(InvoiceAp invoice) {
        CheckResult result = new CheckResult();
        boolean pass = !StringUtils.isEmpty(invoice.getInvoicePayDate()) && !StringUtils.isEmpty(invoice.getInvoiceDueDate());
        result.setPass(pass);
        if(!pass){
            result.setMsg(DefaultErrorMessage.ErrDescription.get(INVOICEPAYDATE_OR_INVOICEDUEDATE_ERROR));
        }
        return result;
    }
}
