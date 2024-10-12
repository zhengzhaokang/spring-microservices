package com.microservices.otmp.finance;

import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.domain.InvoiceAp;

import java.util.List;
import static com.microservices.otmp.common.exception.message.DefaultErrorMessage.INVOICE_REFERENCE_IS_DUPLICATION_ERROR;

/**
 * order 8
 * Duplication Check
 */
public class DuplicationFilter implements HygieneCheckFilter{
    @Override
    public CheckResult filter(List<InvoiceAp> invoices) {
        return null;
    }

    @Override
    public CheckResult filter(InvoiceAp invoice) {
        CheckResult result = new CheckResult();
        boolean pass = !invoice.getInvoiceReferences().contains(invoice.getInvoiceReference());
        if(pass){
            invoice.getInvoiceReferences().add(invoice.getInvoiceReference());
        }
        result.setPass(pass);
        if(!pass){
            result.setMsg(DefaultErrorMessage.ErrDescription.get(INVOICE_REFERENCE_IS_DUPLICATION_ERROR));
        }
        return result;
    }
}
