package com.microservices.otmp.finance;

import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.domain.InvoiceAp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * order 5
 * Currency
 */
@Slf4j
public class CurrencyFilter implements HygieneCheckFilter{

    @Override
    public CheckResult<InvoiceAp> filter(List<InvoiceAp> invoices) {
        log.info("filter:CurrencyFilter,invoices:{}",JsonUtil.toJSON(invoices));
        List<InvoiceAp> passList = new ArrayList<>();
        CheckResult<InvoiceAp> result = new CheckResult<>();
        result.setPass(true);
        if(CollectionUtils.isEmpty(invoices)){
            log.warn("filter:CurrencyFilter invoices is empty,return");
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
        if(invoice == null){
            log.warn("filter,invoice is null,return");
            result.setMsg(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.INVOICE_IS_NULL));
            result.setPass(false);
            return result;
        }
        String supplierCurrency = invoice.getSupplierCurrency();
        if(StringUtils.isEmpty(supplierCurrency)){
            log.info("filter,CurrencyFilter,supplierCurrency is empty,invoice:{}", JsonUtil.toJSON(invoice));
            result.setMsg(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.INVOICE_CURRENCY_CHECK_FAILED) + invoice.getInvoiceReference());
            result.setPass(false);
        }
        String[] supplierCurrencies = supplierCurrency.split(",");
        List<String> currencyList = Arrays.asList(supplierCurrencies);
        if(currencyList.contains(invoice.getInvoiceCurrency())){
            result.setPass(true);
        }else{
            log.info("filter,invoice:[{}]  currency check failed", invoice.getInvoiceReference());
            result.setMsg(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.INVOICE_CURRENCY_CHECK_FAILED) + invoice.getInvoiceReference());
            result.setPass(false);
        }
        return result;
    }
}
