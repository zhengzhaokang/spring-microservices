package com.microservices.otmp.finance;

import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.domain.InvoiceAp;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * order 3
 * Maturity Date <> Past Date
 */
@Slf4j
public class MaturityDateFilter implements HygieneCheckFilter {

    public static final String INVOICE_TYPE_DEBIT = "Debit Memo";
    public static final String INVOICE_TYPE_CREDIT = "Credit Memo";

    @Override
    public CheckResult<InvoiceAp> filter(List<InvoiceAp> invoices) {
        log.info("filter:MaturityDateFilter,invoices:{}", JsonUtil.toJSON(invoices));
        List<InvoiceAp> passList = new ArrayList<>();
        CheckResult<InvoiceAp> result = new CheckResult<>();
        result.setPass(true);
        for (InvoiceAp invoice : invoices) {
            CheckResult<InvoiceAp> filter = filter(invoice);
            if (filter != null && filter.isPass()) {
                passList.add(invoice);
            } else {
                result.setPass(false);
            }
        }
        result.setPassLists(passList);
        return result;
    }

    @Override
    public CheckResult<InvoiceAp> filter(InvoiceAp invoice) {
        CheckResult<InvoiceAp> result = new CheckResult<>();
        if (invoice == null) {
            log.warn("filter,invoice is null,return");
            result.setMsg(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.INVOICE_IS_NULL));
            result.setPass(false);
            return result;
        }
        // 不校验credit
        if (INVOICE_TYPE_CREDIT.equals(invoice.getInvoiceType())) {
            log.info("filter,invoice:[{}],is credit note,return true", invoice.getInvoiceReference());
            result.setPass(true);
            return result;
        }
        // 空值检查
        if (invoice.getMaturityDate() == null) {
            log.info("filter,invoice:[{}],is credit note,return true", invoice.getInvoiceReference());
            result.setMsg(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.INVOICE_MATURITY_DATE_IS_NULL) + invoice.getInvoiceReference());
            result.setPass(false);
            return result;
        }
        // 到期日期检查
        boolean before = invoice.getMaturityDate().before(new Date());
        if (before) {
            // TODO detail info print
            log.info("filter,invoice:[{}] maturity date check failed", invoice.getInvoiceReference());
            result.setMsg(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.INVOICE_MATURITY_DATE_CHECK_FAILED) + invoice.getInvoiceReference());
            result.setPass(false);
        } else {
            result.setPass(true);
        }
        return result;
    }
}
