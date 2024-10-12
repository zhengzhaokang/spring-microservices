package com.microservices.otmp.finance;

import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.domain.InvoiceAp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.microservices.otmp.finance.MaturityDateFilter.INVOICE_TYPE_CREDIT;

/**
 * order 2
 * Financing tenor = Maturity Date – Submit date + 2 Bank Actual Pay Date,
 * Financing tenor > 1
 */
@Slf4j
public class FinancingTenorFilter implements HygieneCheckFilter {

    public static final Integer FINANCING_MIN_LIMIT = 1;

    @Override
    public CheckResult<InvoiceAp> filter(List<InvoiceAp> invoices) {
        log.info("filter:FinancingTenorFilter,invoices:{}",JsonUtil.toJSON(invoices));
        List<InvoiceAp> passList = new ArrayList<>();
        CheckResult<InvoiceAp> result = new CheckResult<>();
        result.setPass(true);
        if (CollectionUtils.isEmpty(invoices)) {
            log.warn("filter:FinancingTenorFilter invoices is empty,return");
            result.setMsg(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.INVOICE_LIST_IS_NULL));
            result.setPass(false);
            return result;
        }
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

        int tenor;
        try {
            // 计算日期差值
            int between = DateUtils.dateBetween(invoice.getSubmitDate(), invoice.getMaturityDate());
            tenor = between + (invoice.getBankActualPayDate() << 1);
        } catch (ParseException e) {
            log.error("filter,calculate date error,invoice number:{}", invoice.getInvoiceReference());
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.SERVER_ERROR), DefaultErrorMessage.SERVER_ERROR.intValue());
        }
        // 差值大于1且小于最大值则通过
        if (tenor > FINANCING_MIN_LIMIT && tenor < invoice.getMaxFinancingTenor()) {
            result.setPass(true);
        } else {
            log.info("filter,invoice:[{}] financing tenor check failed", invoice.getInvoiceReference());
            result.setMsg(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.INVOICE_FINANCING_TENOR_CHECK_FAILED) + invoice.getInvoiceReference());
            result.setPass(false);
        }
        return result;
    }
}
