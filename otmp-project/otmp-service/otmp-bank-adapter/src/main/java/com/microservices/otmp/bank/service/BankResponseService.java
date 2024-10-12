package com.microservices.otmp.bank.service;

import cn.hutool.core.text.csv.CsvRow;
import com.microservices.otmp.bank.domain.constant.BankTransferConstant;
import com.microservices.otmp.bank.domain.dto.BankCommonResponseItemInfoDTO;
import com.microservices.otmp.bank.domain.dto.BankTransferTaskInfoDTO;
import com.microservices.otmp.bank.domain.dto.ParamDTO;
import com.microservices.otmp.bank.kafka.BankInvoiceTransferKafkaSender;
import com.microservices.otmp.bank.service.strategy.BankStrategyFactory;
import com.microservices.otmp.bank.service.strategy.IBankStrategy;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.enums.BatchStatusEnum;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.finance.service.IFinanceBatchInvoiceService;
import com.microservices.otmp.finance.service.IFinanceBatchService;
import com.microservices.otmp.finance.service.IFinanceInvoiceApService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 银行Response返回信息处理service
 */
@Service
@Slf4j
public class BankResponseService {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private BankStrategyFactory bankStrategyFactory;
    @Autowired
    private BankInvoiceTransferKafkaSender bankInvoiceTransferKafkaSender;
    @Autowired
    private IFinanceBatchService financeBatchService;
    @Autowired
    private IFinanceBatchInvoiceService financeBatchInvoiceService;
    @Autowired
    private IFinanceInvoiceApService financeInvoiceApService;
    @Autowired
    private IBankTransferTaskInfoService bankTransferTaskInfoService;

    public void doAction(BankTransferTaskInfoDTO bankTransferTaskInfo) {
        String batchNum = bankTransferTaskInfo.getMicroservicesBatchNumber();
        String requestFileName = bankTransferTaskInfo.getRequestFileName();

    }

    public void decryptFile(String batchNo) throws Exception {
        bankStrategyFactory.setApplicationContext(applicationContext);
        List<BankTransferTaskInfoDTO> bankTransferTaskInfoDTOList = findResponseBankTransferTaskInfoList(batchNo, BatchStatusEnum.FILE_DOWNLOAD_COMPLETED.code);
        if (CollectionUtils.isEmpty(bankTransferTaskInfoDTOList)) {
            log.error("DecryptFileByBatchNo fail.BatchNo:{}", batchNo);
        } else {
            int resultNum = 0;
            for (BankTransferTaskInfoDTO bankTransferTaskInfoDTO : bankTransferTaskInfoDTOList) {
                IBankStrategy bankStrategy = bankStrategyFactory.getBankStrategy(bankTransferTaskInfoDTO.getBankCode());
                ParamDTO paramDTO = new ParamDTO();
                String paramData = bankTransferTaskInfoDTO.getParamData();
                paramDTO = JsonUtil.jsonToObject(paramData, ParamDTO.class);
                paramDTO.setResponseFileName(bankTransferTaskInfoDTO.getResponseFileName());
                bankStrategy.decryptFile(paramDTO);
                bankTransferTaskInfoDTO.setStatus(BatchStatusEnum.FILE_DECRYPTION_COMPLETED.code);
                bankTransferTaskInfoDTO.setUpdateTime(DateUtils.getNowDate());
                bankTransferTaskInfoDTO.setUpdateBy("SYSTEM_DECRYPT");
                resultNum += bankTransferTaskInfoService.updateBankTransferTaskInfo(bankTransferTaskInfoDTO);

            }
            log.info("DecryptFileByBatchNo success.BatchNo:{},Success num:{}", batchNo, resultNum);
        }

    }

    public void parseFeedbackData(String batchNo) throws Exception {
        bankStrategyFactory.setApplicationContext(applicationContext);
        List<BankTransferTaskInfoDTO> bankTransferTaskInfoDTOList = findResponseBankTransferTaskInfoList(batchNo, BatchStatusEnum.FILE_DECRYPTION_COMPLETED.code);
        if (CollectionUtils.isEmpty(bankTransferTaskInfoDTOList)) {
            log.error("ParseFeedbackDataByBatchNo fail.BatchNo:{}", batchNo);
        } else {
            int resultNum = 0;
            for (BankTransferTaskInfoDTO bankTransferTaskInfoDTO : bankTransferTaskInfoDTOList) {
                IBankStrategy bankStrategy = bankStrategyFactory.getBankStrategy(bankTransferTaskInfoDTO.getBankCode());
                ParamDTO paramDTO = new ParamDTO();
                String paramData = bankTransferTaskInfoDTO.getParamData();
                paramDTO = JsonUtil.jsonToObject(paramData, ParamDTO.class);
                paramDTO.setResponseFileName(bankTransferTaskInfoDTO.getResponseFileName());
                List<CsvRow> csvRowList = bankStrategy.parseFeedbackData(paramDTO);
                log.info("csvRowList:{}", csvRowList.toString());
                BankTransferTaskInfoDTO updateBankTransferTaskInfoDTO = new BankTransferTaskInfoDTO();
                //bankTransferTaskInfoDTO.setStatus(BatchStatusEnum.FILE_DECRYPTION_COMPLETED.code);
                updateBankTransferTaskInfoDTO.setBankTransferId(bankTransferTaskInfoDTO.getBankTransferId());
                updateBankTransferTaskInfoDTO.setUpdateTime(DateUtils.getNowDate());
                updateBankTransferTaskInfoDTO.setUpdateBy("SYSTEM_PARSEDATA");
                resultNum += bankTransferTaskInfoService.updateBankTransferTaskInfo(updateBankTransferTaskInfoDTO);
            }
            log.info("ParseFeedbackDataByBatchNo success.BatchNo:{},Success num:{}", batchNo, resultNum);

        }

    }

    public void updateLocalDbData(BankCommonResponseItemInfoDTO commonResponseItemInfoDTO) throws Exception {
        String batchNo = commonResponseItemInfoDTO.getExternalRefNo();
        bankStrategyFactory.setApplicationContext(applicationContext);
        BankTransferTaskInfoDTO bankTransferTaskInfo = new BankTransferTaskInfoDTO();
        bankTransferTaskInfo.setOperType(BankTransferConstant.BANK_TRANSFER_REQUEST);
        bankTransferTaskInfo.setMicroservicesBatchNumber(batchNo);
        List<BankTransferTaskInfoDTO> bankTransferTaskInfoDTOList = bankTransferTaskInfoService.selectBankTransferTaskInfoList(bankTransferTaskInfo);
        if (CollectionUtils.isEmpty(bankTransferTaskInfoDTOList)) {
            log.error("UpdateLocalDbData fail.BatchNo:{}", batchNo);
        } else {
            int resultNum = 1;
            for (BankTransferTaskInfoDTO bankTransferTaskInfoDTO : bankTransferTaskInfoDTOList) {
                IBankStrategy bankStrategy = bankStrategyFactory.getBankStrategy(bankTransferTaskInfoDTO.getBankCode());
                bankStrategy.updateLocalDbData(commonResponseItemInfoDTO);
            }
            log.info("UpdateLocalDbData success.BatchNo:{},Success num:{}", batchNo, resultNum);
        }

    }


    private List<BankTransferTaskInfoDTO> findResponseBankTransferTaskInfoList(String batchNo, String statusType) {
        BankTransferTaskInfoDTO bankTransferTaskInfo = new BankTransferTaskInfoDTO();
        bankTransferTaskInfo.setMicroservicesBatchNumber(batchNo);
        bankTransferTaskInfo.setStatus(statusType);
        bankTransferTaskInfo.setOperType(BankTransferConstant.BANK_TRANSFER_RESPONSE);
        List<BankTransferTaskInfoDTO> bankTransferTaskInfoDTOList = bankTransferTaskInfoService.selectBankTransferTaskInfoList(bankTransferTaskInfo);
        return bankTransferTaskInfoDTOList;
    }

}
