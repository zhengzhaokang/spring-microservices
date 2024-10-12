package com.microservices.otmp.finance;

import com.microservices.otmp.domain.InvoiceAp;

import java.util.List;

public interface HygieneCheckFilter {

    CheckResult filter(List<InvoiceAp> invoices);

    CheckResult filter(InvoiceAp invoice);

}
