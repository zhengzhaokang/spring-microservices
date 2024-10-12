package com.microservices.otmp.bank.service;

import com.microservices.otmp.bank.service.strategy.BankStrategyFactory;
import com.microservices.otmp.bank.service.strategy.IBankStrategy;
import com.microservices.otmp.finance.domain.dto.FinanceBatchInvoiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 银行提交服务入口service
 */
@Service
public class BankSubmitService {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private BankStrategyFactory bankStrategyFactory;

    public void submit(String strategyType, FinanceBatchInvoiceDTO financeBatchInvoice) throws Exception {
        bankStrategyFactory.setApplicationContext(applicationContext);
        IBankStrategy bankStrategy = bankStrategyFactory.getBankStrategy(strategyType);
        bankStrategy.submit(financeBatchInvoice);
    }

    public void pullFileToLocal(String strategyType) throws Exception {

        bankStrategyFactory.setApplicationContext(applicationContext);
        IBankStrategy bankStrategy = bankStrategyFactory.getBankStrategy(strategyType);
        bankStrategy.pullFileToLocal();
    }



}
