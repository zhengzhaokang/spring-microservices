package com.microservices.otmp.finance;

import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.domain.BankLimit;
import com.microservices.otmp.domain.InvoiceAp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * order 6
 * Limit Check
 */
@Slf4j
public class LimitCheckFilter implements HygieneCheckFilter{

    public static final String INVOICE_TYPE_DEBIT = "Debit Memo";
    public static final String INVOICE_TYPE_CREDIT = "Credit Memo";

    @Override
    public CheckResult<InvoiceAp> filter(List<InvoiceAp> invoices) {
        // 基本数据校验，发票列表，银行额度列表
        log.info("filter:LimitCheckFilter,invoices:{}", JsonUtil.toJSON(invoices));
        CheckResult<InvoiceAp> result = new CheckResult<>();
        if (CollectionUtils.isEmpty(invoices)) {
            log.warn("filter:LimitCheckFilter,invoices is null,return");
            result.setMsg(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.INVOICE_LIST_IS_NULL));
            result.setPass(false);
            return result;
        }
        InvoiceAp invoiceAp = invoices.get(0);
        List<BankLimit> limits = invoiceAp.getLimits();
        if (CollectionUtils.isEmpty(limits)) {
            log.warn("filter,banks is null,return");
            result.setMsg(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.INVOICE_IS_NULL));
            result.setPass(false);
            return result;
        }

        BigDecimal totalAmount = new BigDecimal("0.00");
        // 计算总额
        for(InvoiceAp invoice : invoices){
            // debit note类型，总额增加
            if(INVOICE_TYPE_DEBIT.equals(invoice.getInvoiceType())) {
                totalAmount = totalAmount.add(invoice.getInvoiceAmount());
            }else{
                // credit note类型，总额减少
                totalAmount = totalAmount.subtract(invoice.getInvoiceAmount());
            }
        }
        // 遍历银行，任一银行额度大于总额度则通过
        for(BankLimit limit : limits){
            if(limit.getLimit().compareTo(totalAmount) > -1){
                result.setPass(true);
                return result;
            }
        }
        result.setPass(false);
        result.setMsg(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.INVOICE_LIMIT_CHECK_FAILED));
        return result;
    }

    @Override
    public CheckResult<InvoiceAp> filter(InvoiceAp invoice) {
        CheckResult<InvoiceAp> result = new CheckResult<>();
        result.setPass(true);
        return result;
    }
}
