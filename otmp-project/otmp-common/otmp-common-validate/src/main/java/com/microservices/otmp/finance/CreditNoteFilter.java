package com.microservices.otmp.finance;

import com.google.common.collect.Lists;
import com.microservices.otmp.domain.InvoiceAp;

import java.util.List;

/**
 * order 9
 * CN > 0
 */
public class CreditNoteFilter implements HygieneCheckFilter{
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
        return null;
    }
}
