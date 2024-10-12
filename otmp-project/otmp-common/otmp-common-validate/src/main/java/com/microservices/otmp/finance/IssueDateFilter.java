package com.microservices.otmp.finance;

import com.google.common.collect.Lists;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.domain.InvoiceAp;

import java.util.Date;
import java.util.List;
import static com.microservices.otmp.common.exception.message.DefaultErrorMessage.ISSUE_DATEFUTURE_DATE_ERROR;

/**
 * order 4
 * Issue Date < Future Date
 */
public class IssueDateFilter implements HygieneCheckFilter{

    @Override
    public CheckResult filter(List<InvoiceAp> invoices) {
        CheckResult result = new CheckResult();
        List<InvoiceAp> passLists = Lists.newArrayList();
        invoices.stream().forEach(invoice ->{
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
        Date issueDate = DateUtils.parseDate(invoice.getInvoiceIssueDate());
        boolean pass = chekDate(issueDate,  new Date());
        result.setPass(pass);
        if(!pass){
            result.setMsg(DefaultErrorMessage.ErrDescription.get(ISSUE_DATEFUTURE_DATE_ERROR));
        }
        return result;
    }

    private boolean chekDate(Date issueDate, Date futureDate) {
        return !issueDate.after(futureDate);
    }
}
