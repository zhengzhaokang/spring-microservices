package com.microservices.otmp.analysis.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.microservices.otmp.analysis.domain.dto.*;
import com.microservices.otmp.analysis.domain.dto.BankInterestRateTrendDTO;
import com.microservices.otmp.analysis.domain.dto.BankInterestRateTrendVO;
import com.microservices.otmp.analysis.domain.dto.BankInvoiceBatchStatisticsNumDTO;
import com.microservices.otmp.analysis.domain.dto.BankTransferTaskInfoDTO;
import com.microservices.otmp.analysis.domain.entity.BankTransferTaskInfoDO;
import com.microservices.otmp.analysis.manager.IBankTransferTaskInfoManager;
import com.microservices.otmp.analysis.service.IBankTransferTaskInfoService;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.enums.BatchStatusEnum;
import com.microservices.otmp.common.enums.InvoiceStatusEnum;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.MapUtil;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.analysis.manager.IBankTransferTaskInfoManager;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Bank Transfer Task InfoService业务层处理
 *
 * @author lovefamily
 * @date 2023-10-09
 */
@Service
public class BankTransferTaskInfoServiceImpl implements IBankTransferTaskInfoService {
    private static final Logger log = LoggerFactory.getLogger(BankTransferTaskInfoServiceImpl.class);

    @Autowired
    private IBankTransferTaskInfoManager bankTransferTaskInfoManager;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 查询Bank Transfer Task Info
     *
     * @param bankTransferId Bank Transfer Task Info主键
     * @return Bank Transfer Task InfoDTO
     */
    @Override
    public BankTransferTaskInfoDTO selectBankTransferTaskInfoByBankTransferId(Long bankTransferId) {
        BankTransferTaskInfoDO bankTransferTaskInfoDO = bankTransferTaskInfoManager.selectBankTransferTaskInfoByBankTransferId(bankTransferId);
        BankTransferTaskInfoDTO bankTransferTaskInfoDTO = new BankTransferTaskInfoDTO();
        BeanUtils.copyProperties(bankTransferTaskInfoDO, bankTransferTaskInfoDTO);
        return bankTransferTaskInfoDTO;
    }

    /**
     * 查询Bank Transfer Task Info列表
     *
     * @param bankTransferTaskInfo Bank Transfer Task Info
     * @return Bank Transfer Task InfoDTO
     */
    @Override
    public List<BankTransferTaskInfoDTO> selectBankTransferTaskInfoList(BankTransferTaskInfoDTO bankTransferTaskInfo) {

        List<BankTransferTaskInfoDO> bankTransferTaskInfoDOS =
                bankTransferTaskInfoManager.selectBankTransferTaskInfoList(bankTransferTaskInfo);
        List<BankTransferTaskInfoDTO> bankTransferTaskInfoList = new ArrayList<>(bankTransferTaskInfoDOS.size());
        BeanUtils.copyListProperties(bankTransferTaskInfoDOS, bankTransferTaskInfoList, BankTransferTaskInfoDTO.class);
        return bankTransferTaskInfoList;

    }

    /**
     * 新增Bank Transfer Task Info
     *
     * @param bankTransferTaskInfo Bank Transfer Task Info
     * @return 结果
     */
    @Override
    public int insertBankTransferTaskInfo(BankTransferTaskInfoDTO bankTransferTaskInfo) {
        bankTransferTaskInfo.setCreateTime(DateUtils.getNowDate());
        BankTransferTaskInfoDO bankTransferTaskInfoDO = new BankTransferTaskInfoDO();
        BeanUtils.copyProperties(bankTransferTaskInfo, bankTransferTaskInfoDO);
        return bankTransferTaskInfoManager.insertBankTransferTaskInfo(bankTransferTaskInfoDO);
    }

    /**
     * 修改Bank Transfer Task Info
     *
     * @param bankTransferTaskInfo Bank Transfer Task Info
     * @return 结果
     */
    @Override
    public int updateBankTransferTaskInfo(BankTransferTaskInfoDTO bankTransferTaskInfo) {
        bankTransferTaskInfo.setUpdateTime(DateUtils.getNowDate());
        BankTransferTaskInfoDO bankTransferTaskInfoDO = new BankTransferTaskInfoDO();
        BeanUtils.copyProperties(bankTransferTaskInfo, bankTransferTaskInfoDO);
        return bankTransferTaskInfoManager.updateBankTransferTaskInfo(bankTransferTaskInfoDO);
    }

    /**
     * 批量删除Bank Transfer Task Info
     *
     * @param bankTransferIds 需要删除的Bank Transfer Task Info主键
     * @return 结果
     */
    @Override
    public int deleteBankTransferTaskInfoByBankTransferIds(Long[] bankTransferIds) {
        return bankTransferTaskInfoManager.deleteBankTransferTaskInfoByBankTransferIds(bankTransferIds);
    }

    /**
     * 删除Bank Transfer Task Info信息
     *
     * @param bankTransferId Bank Transfer Task Info主键
     * @return 结果
     */
    @Override
    public int deleteBankTransferTaskInfoByBankTransferId(Long bankTransferId) {
        return bankTransferTaskInfoManager.deleteBankTransferTaskInfoByBankTransferId(bankTransferId);
    }

    @Override
    public List<Map<String, Object>> findBankInvoiceBatchCount(BankInvoiceBatchStatisticsNumDTO bankInvoiceBatchStatisticsNumDTO) {
        return bankTransferTaskInfoManager.findBankInvoiceBatchCount(bankInvoiceBatchStatisticsNumDTO);
    }

    @Override
    public List<BankInterestRateTrendDTO> findBankInvoiceBatchList(BankInterestRateTrendDTO bankInterestRateTrendDTO) {
        return bankTransferTaskInfoManager.findBankInvoiceBatchList(bankInterestRateTrendDTO);
    }

    @Override
    public List<BankInterestRateTrendVO> findBankInvoiceBatchPageList(BankInterestRateTrendVO bankInterestRateTrendVO) {
        List<BankInterestRateTrendVO> bankInterestRateTrendVOList = bankTransferTaskInfoManager.findBankInvoiceBatchPageList(bankInterestRateTrendVO);
        buildResultData(bankInterestRateTrendVOList);
        return bankInterestRateTrendVOList;
    }

    private void buildResultData(List<BankInterestRateTrendVO> bankInterestRateTrendVOList) {
        Map<String, Object> bankCache = new HashMap<>();
        Map<String, Object> supplierCache = new HashMap<>();
        Map<String, Object> entityCache = new HashMap<>();
        bankCache = redissonClient.getMap(RedisConstants.INFO_ALL_BANK);
        supplierCache = redissonClient.getMap(RedisConstants.INFO_ALL_SUPPLIER);
        entityCache = redissonClient.getMap(RedisConstants.INFO_ALL_ENTITY);

        Map<String, Object> finalBankCache = bankCache;
        Map<String, Object> finalSupplierCache = supplierCache;
        Map<String, Object> finalEntityCache = entityCache;
        bankInterestRateTrendVOList.forEach(item -> {

            if (!MapUtil.isEmpty(finalBankCache)) {
                JSONObject bankObj = (JSONObject) finalBankCache.get(item.getBankId());
                if (!Objects.isNull(bankObj)) {
                    item.setBankName(bankObj.getString("bankName"));
                }
            }
            if (!MapUtil.isEmpty(finalSupplierCache)) {
                JSONObject supplierObj = (JSONObject) finalSupplierCache.get(item.getSupplierId());
                if (!Objects.isNull(supplierObj)) {
                    item.setSupplierName(supplierObj.getString("supplierName"));
                }
            }
            if (!MapUtil.isEmpty(finalEntityCache)) {
                JSONObject entityObj = (JSONObject) finalEntityCache.get(item.getEntityId());
                if (!Objects.isNull(entityObj)) {
                    item.setEntityName(entityObj.getString("entityName"));
                }
            }
        });
    }
}
