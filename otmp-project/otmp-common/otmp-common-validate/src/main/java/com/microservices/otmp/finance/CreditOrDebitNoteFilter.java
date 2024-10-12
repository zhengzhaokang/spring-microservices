package com.microservices.otmp.finance;

import com.google.common.collect.Lists;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.domain.InvoiceAp;

import java.util.List;

import static com.microservices.otmp.common.exception.message.DefaultErrorMessage.INVOICE_INVOICE_TYPE_ERROR;

/**
 * order 12
 * CN OR DN
 */
public class CreditOrDebitNoteFilter implements HygieneCheckFilter{
    @Override
    public CheckResult filter(List<InvoiceAp> invoices) {
        CheckResult result = new CheckResult();
        List<InvoiceAp> passLists = Lists.newArrayList();
        invoices.stream().forEach(invoice -> {
            CheckResult singleResult = filter(invoice);
            if(singleResult.isPass()){
                passLists.add(invoice);
            }
        });
        result.setPass(passLists.size() == invoices.size());
        result.setPassLists(passLists);
        return result;
    }

    @Override
    public CheckResult filter(InvoiceAp invoice) {
        CheckResult result = new CheckResult();
        boolean pass = StringUtils.equalsAny(invoice.getInvoiceType(), "Debit Memo", "Credit Memo");
        result.setPass(pass);
        if(!pass){
            result.setMsg(DefaultErrorMessage.ErrDescription.get(INVOICE_INVOICE_TYPE_ERROR));
        }
        return result;
    }
}
