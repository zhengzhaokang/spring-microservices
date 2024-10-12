package com.microservices.otmp.finance;

import com.google.common.collect.Lists;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.domain.InvoiceAp;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

import static com.microservices.otmp.common.exception.message.DefaultErrorMessage.INVOICE_TENOR_ERROR;

/**
 * order 1
 * Invoice tenor = Maturity Date – Invoice Date
 * Invoice tenor < Supplier tenor date
 */
@Slf4j
public class InvoiceTenorFilter implements HygieneCheckFilter {
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
        try {
            boolean pass = checkInvoiceTenor(invoice);
            result.setPass(pass);
            if(!pass){
                result.setMsg(DefaultErrorMessage.ErrDescription.get(INVOICE_TENOR_ERROR));
            }
        } catch (Exception e) {
            log.error("校验时间差异常：", e.getMessage(), e);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    private boolean checkInvoiceTenor(InvoiceAp invoice) throws Exception {
        int days = DateUtils.dateBetween(invoice.getMaturityDate(), DateUtils.parseDate(invoice.getInvoiceIssueDate()));
        return days < invoice.getDays();
    }
}
