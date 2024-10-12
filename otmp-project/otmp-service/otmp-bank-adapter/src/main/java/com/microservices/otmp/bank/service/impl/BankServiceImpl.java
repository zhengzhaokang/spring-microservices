package com.microservices.otmp.bank.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.microservices.otmp.bank.domain.constant.BankTransferConstant;
import com.microservices.otmp.bank.domain.dto.BankCommonResponseHeaderInfoDTO;
import com.microservices.otmp.bank.domain.dto.BankCommonResponseItemInfoDTO;
import com.microservices.otmp.bank.domain.dto.BankTransferTaskInfoDTO;
import com.microservices.otmp.bank.domain.dto.ParamDTO;
import com.microservices.otmp.bank.kafka.NoticeKafkaSender;
import com.microservices.otmp.bank.service.BankResponseService;
import com.microservices.otmp.bank.service.BankSubmitService;
import com.microservices.otmp.bank.service.IBankTransferTaskInfoService;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.enums.BatchStatusEnum;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.bank.domain.req.BatchReq;
import com.microservices.otmp.bank.service.BankService;
import com.microservices.otmp.bank.service.template.BankTemplate;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.BigDecimalUtil;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.finance.domain.dto.FinanceBatchDTO;
import com.microservices.otmp.finance.domain.dto.FinanceBatchInvoiceDTO;
import com.microservices.otmp.finance.domain.entity.FinanceBatchInvoiceDO;
import com.microservices.otmp.finance.mapper.FinanceBatchInvoiceMapper;
import com.microservices.otmp.finance.service.IFinanceBatchService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * created with IntelliJ IDEA.
 * user: licf
 * Date: 2023/7/24
 * time: 14:37
 * Description:
 */
@Service
public class BankServiceImpl extends BankTemplate implements BankService {

    @Autowired
    private BankSubmitService bankSubmitService;


    @Autowired
    private BankResponseService bankResponseService;

    @Autowired
    private FinanceBatchInvoiceMapper financeBatchInvoiceMapper;

    @Autowired
    private IBankTransferTaskInfoService transferTaskInfoService;

    @Autowired
    private IFinanceBatchService financeBatchService;

    @Autowired
    private NoticeKafkaSender noticeKafkaSender;

    @Autowired
    private RedisUtils redisUtils;

    public static final String SEND_ERROR_USER_EMAIL = "Send.Error.Email";

    private static final Logger log = LoggerFactory.getLogger(BankServiceImpl.class);

    @Override
    public List<List<String>> download(BatchReq batchReq) {
        try {
            //解密后的文件名称
            String localFileName = "plain.csv";
            return feedback(batchReq.getFileUrl(), batchReq.getLocalFileName(), localFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void sendBank(BatchReq batchReq) {

        BankTransferTaskInfoDTO bankTransferTaskInfo = new BankTransferTaskInfoDTO();
        bankTransferTaskInfo.setMicroservicesBatchNumber(batchReq.getBatchNo());
        bankTransferTaskInfo.setStatus(BatchStatusEnum.SUBMITTED.code);
        bankTransferTaskInfo.setOperType(BankTransferConstant.BANK_TRANSFER_REQUEST);
        List<BankTransferTaskInfoDTO> bankTransferTaskInfoDTOList = transferTaskInfoService.selectBankTransferTaskInfoList(bankTransferTaskInfo);
        ParamDTO paramDTO = new ParamDTO();
        if (CollectionUtils.isEmpty(bankTransferTaskInfoDTOList)) {
            log.error("uploadSftpByBatchNo fail.BatchNo");
            throw new OtmpException("uploadSftpByBatchNo fail.BatchNo");
        } else {
            BankTransferTaskInfoDTO bankTransferTaskInfoDTO = bankTransferTaskInfoDTOList.get(0);
            String paramData = bankTransferTaskInfoDTO.getParamData();
            paramDTO = JsonUtil.jsonToObject(paramData, ParamDTO.class);
        }

    }

    @Override
    public Integer submit(BatchReq batchReq) {

        BankTransferTaskInfoDTO bankTransferTaskInfo = new BankTransferTaskInfoDTO();
        bankTransferTaskInfo.setMicroservicesBatchNumber(batchReq.getBatchNo());
        bankTransferTaskInfo.setStatus(BatchStatusEnum.SUBMITTED.code);
        List<BankTransferTaskInfoDTO> bankTransferTaskInfoDTOList = transferTaskInfoService.selectBankTransferTaskInfoList(bankTransferTaskInfo);
        if (CollectionUtils.isNotEmpty(bankTransferTaskInfoDTOList)) {
            log.info("microservicesBatchNumber:{},is Submitted.", batchReq.getBatchNo());
            return null;
        }
        if(checkFinanceBatchInvoiceNotSubmit(batchReq.getBatchNo())){
            return null;
        }
        /**
         *   根据批次编号查询到一条数据 调取对应的银行信息、供应商信息构建request文件的头信息
         */

        List<FinanceBatchInvoiceDO> financeBatchInvoiceDOList = financeBatchInvoiceMapper.getFinanceBatchInvoice(batchReq.getBatchNo());
        if (CollectionUtils.isEmpty(financeBatchInvoiceDOList)) {
            throw new OtmpException("Bank batchNo error");
        }
        List<FinanceBatchInvoiceDTO> financeBatchInvoiceList = new ArrayList<>(financeBatchInvoiceDOList.size());
        BeanUtils.copyListProperties(financeBatchInvoiceDOList, financeBatchInvoiceList, FinanceBatchInvoiceDTO.class);
        financeBatchInvoiceList.forEach(financeBatchInvoice -> {
            try {
                bankSubmitService.submit(financeBatchInvoice.getBankName(), financeBatchInvoice);
            } catch (Exception e) {
                log.error("Submit Invoice to Bank Error:{}", e);
                e.printStackTrace();
            }
        });
        return null;
    }

    private boolean checkFinanceBatchInvoiceNotSubmit(String batchNumber) {
        boolean isReturn = false;
        Map<String, String> paramMap = new HashMap<>();
        FinanceBatchDTO financeBatch = new FinanceBatchDTO();
        financeBatch.setBatchNumber(batchNumber);
        List<FinanceBatchDTO> financeBatchDTOList = financeBatchService.selectFinanceBatchList(financeBatch);
        if (CollectionUtil.isNotEmpty(financeBatchDTOList)) {
            FinanceBatchDTO financeBatchData = financeBatchDTOList.get(0);
            String submitAmount = financeBatchData.getSubmitAmount();
            String createBy = financeBatchData.getCreateBy();
            if (StringUtils.isNotEmpty(submitAmount)) {
                BigDecimal submitAmountDecimal = new BigDecimal(submitAmount);
                boolean isNegative = submitAmountDecimal.signum() <= 0;
                if (isNegative) {
                    paramMap.put("businessType", "FinancingBatch");
                    paramMap.put("emailType", "financing_batch");
                    paramMap.put("bean", JsonUtil.toJSON(financeBatchData));
                    paramMap.put("mailTitleBean", JsonUtil.toJSON(financeBatchData));
                    paramMap.put("module", "AP Financing");
                    noticeKafkaSender.sendEmailData(paramMap,  Arrays.asList(createBy),getSendEmailList());
                    log.error("Submit Invoice BatchNumber:{} to Bank Error,SubmitAmount:{}", batchNumber, submitAmount);
                    isReturn = isNegative;
                }

            }
        }
        return isReturn;
    }

    private List<String> getSendEmailList() {
        String emailListStr = redisUtils.get(SEND_ERROR_USER_EMAIL);
        List<String> emailList = new ArrayList<>();
        if (StringUtils.isNotEmpty(emailListStr)) {
            emailList = StrUtil.split(emailListStr, ',');

        }
        return emailList;

    }

    @Override
    public void pullFileToLocal(BatchReq batchReq) {

        try {
            bankResponseService.decryptFile(batchReq.getBatchNo());
            bankResponseService.parseFeedbackData(batchReq.getBatchNo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateLocalDbData(BankCommonResponseItemInfoDTO commonResponseItemInfoDTO) {
        try {
            bankResponseService.updateLocalDbData(commonResponseItemInfoDTO);
        } catch (Exception e) {
            log.error("UpdateLocalDbData Success.");
            e.printStackTrace();
        }
    }


}
