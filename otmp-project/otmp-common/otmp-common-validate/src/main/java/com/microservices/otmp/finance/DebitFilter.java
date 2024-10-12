package com.microservices.otmp.finance;

import com.microservices.otmp.domain.InvoiceAp;

import java.util.List;

/**
 * order 10
 * DN < 0
 */
public class DebitFilter implements HygieneCheckFilter{
    @Override
    public CheckResult filter(List<InvoiceAp> invoices) {
        return null;
    }

    @Override
    public CheckResult filter(InvoiceAp invoice) {
        return null;
    }
}
