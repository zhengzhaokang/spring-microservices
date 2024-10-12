package com.microservices.otmp.analysis.task;

import com.microservices.otmp.analysis.service.ISupplierInvoiceService;
import com.microservices.otmp.analysis.service.ISupplierInvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TimeTask {

    @Autowired
    private ISupplierInvoiceService supplierInvoiceService;

    @Scheduled(cron = "0 20 5 * * ? ")
    public void accountsPayableJob() {
        log.info("accountsPayableJob执行");
        supplierInvoiceService.doAccountsPayableJob();
        log.info("accountsPayableJob结束");
    }
}
