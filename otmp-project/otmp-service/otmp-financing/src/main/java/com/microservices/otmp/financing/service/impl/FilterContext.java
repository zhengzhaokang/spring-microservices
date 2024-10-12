package com.microservices.otmp.financing.service.impl;

import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.domain.InvoiceAp;
import com.microservices.otmp.finance.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class FilterContext {

    public List<HygieneCheckFilter> filters;

    @PostConstruct
    public void init() {
        filters = new ArrayList<>();
        filters.add(new FinancingTenorFilter());
        filters.add(new MaturityDateFilter());
        filters.add(new CurrencyFilter());
        filters.add(new LimitCheckFilter());
    }

    @SuppressWarnings("rawtypes")
    public void doFilter(List<InvoiceAp> invoiceAps) {
        log.info("doFilter,invoiceAps:{}", JsonUtil.toJSON(invoiceAps));
        for (HygieneCheckFilter filter : filters) {
            CheckResult result = filter.filter(invoiceAps);
            if (!result.isPass()) {
                log.info("doFilter,hygiene check failed,msg:{}", result.getMsg());
                throw new OtmpException(result.getMsg(), DefaultErrorMessage.INVOICE_HYGIENE_CHECK_FAILED.intValue());
            }
        }
    }

}
