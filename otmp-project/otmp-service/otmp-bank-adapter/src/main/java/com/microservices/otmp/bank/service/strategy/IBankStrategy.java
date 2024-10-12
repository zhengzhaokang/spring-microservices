package com.microservices.otmp.bank.service.strategy;

import cn.hutool.core.text.csv.CsvRow;
import com.microservices.otmp.bank.domain.dto.BankCommonResponseItemInfoDTO;
import com.microservices.otmp.bank.domain.dto.ParamDTO;
import com.microservices.otmp.finance.domain.dto.FinanceBatchInvoiceDTO;

import java.util.List;

/**
 * Bank业务的策略接口定义
 */
public interface IBankStrategy {


    /**
     * 策略类型，用于区分不同的业务 (DBS、BNPP)
     *
     * @return
     */
    String strategyType();

    /**
     * 提交接口
     */
    void submit(FinanceBatchInvoiceDTO financeBatchInvoice) throws Exception;

    /**
     * 文件拉取接口
     */
    void pullFileToLocal();


    void decryptFile(ParamDTO paramDTO) throws Exception;

    List<CsvRow> parseFeedbackData(ParamDTO paramDTO) throws Exception;

    public void updateLocalDbData(BankCommonResponseItemInfoDTO bankCommonResponseItemInfoDTO)throws Exception;


}
