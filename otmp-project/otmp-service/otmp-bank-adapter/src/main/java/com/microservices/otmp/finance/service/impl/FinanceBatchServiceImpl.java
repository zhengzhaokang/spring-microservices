package com.microservices.otmp.finance.service.impl;

import java.util.*;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.microservices.otmp.bank.domain.dto.BankTransferInvoiceResponseInfoDTO;
import com.microservices.otmp.bank.kafka.BankInvoiceTransferKafkaSender;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.enums.BatchStatusEnum;
import com.microservices.otmp.common.enums.InvoiceStatusEnum;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.finance.domain.dto.FinanceBatchInvoiceDTO;
import com.microservices.otmp.finance.service.IFinanceBatchInvoiceService;
import com.microservices.otmp.finance.service.IFinanceInvoiceApService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.finance.manager.IFinanceBatchManager;
import com.microservices.otmp.finance.domain.dto.FinanceBatchDTO;
import com.microservices.otmp.finance.domain.entity.FinanceBatchDO;
import com.microservices.otmp.finance.service.IFinanceBatchService;

/**
 * Finance Batch InfoService业务层处理
 *
 * @author lovefamily
 * @date 2023-10-12
 */
@Service
public class FinanceBatchServiceImpl implements IFinanceBatchService {
    private static final Logger log = LoggerFactory.getLogger(FinanceBatchServiceImpl.class);

    @Autowired
    private IFinanceBatchManager financeBatchManager;


    @Autowired
    private IFinanceBatchInvoiceService financeBatchInvoiceService;

    @Autowired
    private IFinanceInvoiceApService financeInvoiceApService;


    @Autowired
    private BankInvoiceTransferKafkaSender bankInvoiceTransferKafkaSender;


    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisUtils redisUtils;

    public static final String SEND_KAFKA_FLAG_KEY = "Send.Kafka.Flag";


    /**
     * 查询Finance Batch Info
     *
     * @param id Finance Batch Info主键
     * @return Finance Batch InfoDTO
     */
    @Override
    public FinanceBatchDTO selectFinanceBatchById(Long id) {
        FinanceBatchDO financeBatchDO = financeBatchManager.selectFinanceBatchById(id);
        FinanceBatchDTO financeBatchDTO = new FinanceBatchDTO();
        BeanUtils.copyProperties(financeBatchDO, financeBatchDTO);
        return financeBatchDTO;
    }

    /**
     * 查询Finance Batch Info列表
     *
     * @param financeBatch Finance Batch Info
     * @return Finance Batch InfoDTO
     */
    @Override
    public List<FinanceBatchDTO> selectFinanceBatchList(FinanceBatchDTO financeBatch) {

        List<FinanceBatchDO> financeBatchDOS =
                financeBatchManager.selectFinanceBatchList(financeBatch);
        List<FinanceBatchDTO> financeBatchList = new ArrayList<>(financeBatchDOS.size());
        BeanUtils.copyListProperties(financeBatchDOS, financeBatchList, FinanceBatchDTO.class);
        return financeBatchList;

    }

    /**
     * 新增Finance Batch Info
     *
     * @param financeBatch Finance Batch Info
     * @return 结果
     */
    @Override
    public int insertFinanceBatch(FinanceBatchDTO financeBatch) {
        financeBatch.setCreateTime(DateUtils.getNowDate());
        FinanceBatchDO financeBatchDO = new FinanceBatchDO();
        BeanUtils.copyProperties(financeBatch, financeBatchDO);
        return financeBatchManager.insertFinanceBatch(financeBatchDO);
    }

    /**
     * 修改Finance Batch Info
     *
     * @param financeBatch Finance Batch Info
     * @return 结果
     */
    @Override
    public int updateFinanceBatch(FinanceBatchDTO financeBatch) {
        financeBatch.setUpdateTime(DateUtils.getNowDate());
        FinanceBatchDO financeBatchDO = new FinanceBatchDO();
        BeanUtils.copyProperties(financeBatch, financeBatchDO);
        return financeBatchManager.updateFinanceBatch(financeBatchDO);
    }

    @Override
    public int updateFinanceBatchByBatchNumber(FinanceBatchDTO financeBatch) {
        financeBatch.setUpdateTime(DateUtils.getNowDate());
        FinanceBatchDO financeBatchDO = new FinanceBatchDO();
        BeanUtils.copyProperties(financeBatch, financeBatchDO);
        return financeBatchManager.updateFinanceBatchByBatchNumber(financeBatchDO);
    }


    @Override
    public int updateBatchInvoiceAndOtherInfo(FinanceBatchDTO financeBatch) {
        log.info("updateBatchInvoiceAndOtherInfo...start.");
        String batchNumber = financeBatch.getBatchNumber();
        String status = financeBatch.getStatus();
        String statusDescription = financeBatch.getStatusDescription();
        String bankId = financeBatch.getBankId();
        String erpVendorId = getBankVendorCode(bankId);
        Date discountDate = financeBatch.getDiscountDate();
        String maturityDate = financeBatch.getMaturityDate();

        Date updateTime = DateUtils.getNowDate();
        FinanceBatchDTO queryFinanceBatch = new FinanceBatchDTO();
        queryFinanceBatch.setBatchNumber(batchNumber);
        List<FinanceBatchDTO> financeBatchDTOList = selectFinanceBatchList(queryFinanceBatch);
        int resultBatchNum = 0;
        if (CollectionUtil.isNotEmpty(financeBatchDTOList)) {
            //更新FinanceBatch数据库表
            FinanceBatchDTO updateFinanceBatch = financeBatchDTOList.get(0);
            financeBatch.setId(updateFinanceBatch.getId());
            financeBatch.setUpdateTime(updateTime);
            financeBatch.setUpdateBy("BANK-BACK-EVENT");
            FinanceBatchDO financeBatchDO = new FinanceBatchDO();
            BeanUtils.copyProperties(financeBatch, financeBatchDO);
            resultBatchNum = financeBatchManager.updateFinanceBatch(financeBatchDO);
        }
        log.info("Bank Back Update FinanceBatch Success.");

        if (resultBatchNum <= 0) {
            return resultBatchNum;
        }

//        FinanceBatchInvoiceDTO queryFinanceBatchInvoice = new FinanceBatchInvoiceDTO();
//        queryFinanceBatchInvoice.setBatchNumber(financeBatch.getBatchNumber());
//        List<FinanceBatchInvoiceDTO> financeBatchInvoiceDTOList = financeBatchInvoiceService.selectFinanceBatchInvoiceList(queryFinanceBatchInvoice);
//
//        int resultNum = 0;
//        List<BankTransferInvoiceResponseInfoDTO> bankTransferInvoiceResponseInfoDTOList = new ArrayList<>();
//        Map<String, Object> bankInvoiceTransferKafkaSenderMapData = new HashMap<>();
//        for (FinanceBatchInvoiceDTO financeBatchInvoiceDTO : financeBatchInvoiceDTOList) {
//            String invoiceReference = financeBatchInvoiceDTO.getInvoiceReference();
//            financeBatchInvoiceDTO.setUpdateBy("BANK-BACK-EVENT");
//            financeBatchInvoiceDTO.setUpdateTime(updateTime);
//            financeBatchInvoiceDTO.setStatus(status);
//            resultNum += financeBatchInvoiceService.updateFinanceBatchInvoice(financeBatchInvoiceDTO);
//
//            if (StringUtils.isNotEmpty(status) && (InvoiceStatusEnum.FINANCED.code.equals(status) || InvoiceStatusEnum.REJECTED.code.equals(status))) {
//                /**
//                 * 发送kafka
//                 */
//                BankTransferInvoiceResponseInfoDTO bankTransferInvoiceResponseInfoDTO = new BankTransferInvoiceResponseInfoDTO();
//                bankTransferInvoiceResponseInfoDTO.setId(SnowFlakeUtil.nextId());
//                bankTransferInvoiceResponseInfoDTO.setInvoiceId(invoiceReference);
//                bankTransferInvoiceResponseInfoDTO.setStatus(status);
//                bankTransferInvoiceResponseInfoDTO.setStatusDescription(statusDescription);
//                bankTransferInvoiceResponseInfoDTO.setmicroservicesBatchNumber(batchNumber);
//                if (!Objects.isNull(discountDate)) {
//                    bankTransferInvoiceResponseInfoDTO.setDiscountDate(DateUtils.dateFormat(discountDate, DateUtils.YYYY_MM_DD));
//                }
//                if (StringUtils.isNotEmpty(maturityDate)) {
//                    bankTransferInvoiceResponseInfoDTO.setBankMaturityDate(maturityDate);
//                }
//                bankTransferInvoiceResponseInfoDTO.setBankVendorCode(erpVendorId);
//                bankTransferInvoiceResponseInfoDTO.setCreateTime(updateTime);
//                bankTransferInvoiceResponseInfoDTOList.add(bankTransferInvoiceResponseInfoDTO);
//                /***
//                 * 将按发票纬度发送kafka改为按批次去发送kafka
//                 */
//                // bankInvoiceTransferKafkaSender.sendBankInvoiceTransferData(bankTransferInvoiceResponseInfoDTO);
//            }
//
//        }

        //按照配置确定是否发送kafka
        if (checkSendKafkaFlag()) {
            BankTransferInvoiceResponseInfoDTO bankTransferInvoiceResponseInfoDTO = new BankTransferInvoiceResponseInfoDTO();
            bankTransferInvoiceResponseInfoDTO.setId(SnowFlakeUtil.nextId());
            bankTransferInvoiceResponseInfoDTO.setStatus(status);
            bankTransferInvoiceResponseInfoDTO.setStatusDescription(statusDescription);
            bankTransferInvoiceResponseInfoDTO.setMicroservicesBatchNumber(batchNumber);
            if (!Objects.isNull(discountDate)) {
                bankTransferInvoiceResponseInfoDTO.setDiscountDate(DateUtils.dateFormat(discountDate, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotEmpty(maturityDate)) {
                bankTransferInvoiceResponseInfoDTO.setBankMaturityDate(maturityDate);
            }
            bankTransferInvoiceResponseInfoDTO.setBankVendorCode(erpVendorId);
            bankTransferInvoiceResponseInfoDTO.setCreateTime(updateTime);
            bankInvoiceTransferKafkaSender.sendBankInvoiceTransferData(bankTransferInvoiceResponseInfoDTO);
        }
//        Map<String, Object> bankInvoiceTransferKafkaSenderMapData = new HashMap<>();
//        bankInvoiceTransferKafkaSenderMapData.put("transferId", SnowFlakeUtil.nextId());
//        bankInvoiceTransferKafkaSenderMapData.put("status", status);
//        bankInvoiceTransferKafkaSenderMapData.put("statusDescription", statusDescription);
//        bankInvoiceTransferKafkaSenderMapData.put("microservicesBatchNumber", batchNumber);
//        if (!Objects.isNull(discountDate)) {
//            bankInvoiceTransferKafkaSenderMapData.put("discountDate", DateUtils.dateFormat(discountDate, DateUtils.YYYY_MM_DD));
//        }
//        bankInvoiceTransferKafkaSenderMapData.put("bankMaturityDate", maturityDate);
//        bankInvoiceTransferKafkaSenderMapData.put("bankVendorCode", erpVendorId);
//        bankInvoiceTransferKafkaSenderMapData.put("transferDateTime", DateUtils.getNowDate());
//        bankInvoiceTransferKafkaSender.sendBankInvoiceTransferMapData(bankInvoiceTransferKafkaSenderMapData);
        log.info("Send kafka success.");

        return resultBatchNum;
    }

    private boolean checkSendKafkaFlag() {
        String sendKafkaFlag = redisUtils.get(SEND_KAFKA_FLAG_KEY);
        log.info("sendKafkaFlag:{}", sendKafkaFlag);
        return !"False".equals(sendKafkaFlag);
    }

    private String getBankVendorCode(String bankId) {
        if (StringUtils.isEmpty(bankId)) {
            return null;
        }
        log.info("Bank Id:{}", bankId);
        String bankInfoKey = RedisConstants.getBankInfoKey(bankId);
        RBucket<Object> bucket = redissonClient.getBucket(bankInfoKey);
        String json = JsonUtil.toJSON(bucket.get());
        log.info("bankObject:{}", json);
        JSONObject jsonObject = JsonUtil.toJsonObject(json);
        return jsonObject.getString("erpVendorId");
    }


    /**
     * 批量删除Finance Batch Info
     *
     * @param ids 需要删除的Finance Batch Info主键
     * @return 结果
     */
    @Override
    public int deleteFinanceBatchByIds(Long[] ids) {
        return financeBatchManager.deleteFinanceBatchByIds(ids);
    }

    /**
     * 删除Finance Batch Info信息
     *
     * @param id Finance Batch Info主键
     * @return 结果
     */
    @Override
    public int deleteFinanceBatchById(Long id) {
        return financeBatchManager.deleteFinanceBatchById(id);
    }
}
