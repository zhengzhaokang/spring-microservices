package com.microservices.otmp.analysis.manager.impl;


import com.microservices.otmp.analysis.domain.dto.BankInterestRateTrendDTO;
import com.microservices.otmp.analysis.domain.dto.BankInterestRateTrendVO;
import com.microservices.otmp.analysis.domain.dto.BankInvoiceBatchStatisticsNumDTO;
import com.microservices.otmp.analysis.domain.dto.BankTransferTaskInfoDTO;
import com.microservices.otmp.analysis.domain.entity.BankTransferTaskInfoDO;
import com.microservices.otmp.analysis.manager.IBankTransferTaskInfoManager;
import com.microservices.otmp.analysis.mapper.BankTransferTaskInfoMapper;
import com.microservices.otmp.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Bank Transfer Task InfoManager业务层处理
 *
 * @author lovefamily
 * @date 2023-10-09
 */
@Service
public class BankTransferTaskInfoManagerImpl implements IBankTransferTaskInfoManager {
    private static final Logger log = LoggerFactory.getLogger(BankTransferTaskInfoManagerImpl.class);

    @Autowired
    private BankTransferTaskInfoMapper bankTransferTaskInfoMapper;

    /**
     * 查询Bank Transfer Task Info
     *
     * @param bankTransferId Bank Transfer Task Info主键
     * @return Bank Transfer Task InfoDO
     */
    @Override
    public BankTransferTaskInfoDO selectBankTransferTaskInfoByBankTransferId(Long bankTransferId) {
        return bankTransferTaskInfoMapper.selectBankTransferTaskInfoByBankTransferId(bankTransferId);
    }

    /**
     * 查询Bank Transfer Task Info列表
     *
     * @param bankTransferTaskInfo Bank Transfer Task Info
     * @return Bank Transfer Task InfoDO
     */
    @Override
    public List<BankTransferTaskInfoDO> selectBankTransferTaskInfoList(BankTransferTaskInfoDTO bankTransferTaskInfo) {
        return bankTransferTaskInfoMapper.selectBankTransferTaskInfoList(bankTransferTaskInfo);
    }

    /**
     * 新增Bank Transfer Task Info
     *
     * @param bankTransferTaskInfo Bank Transfer Task Info
     * @return 结果
     */
    @Override
    public int insertBankTransferTaskInfo(BankTransferTaskInfoDO bankTransferTaskInfo) {
        bankTransferTaskInfo.setCreateTime(DateUtils.getNowDate());
        return bankTransferTaskInfoMapper.insertBankTransferTaskInfo(bankTransferTaskInfo);
    }

    /**
     * 修改Bank Transfer Task Info
     *
     * @param bankTransferTaskInfo Bank Transfer Task Info
     * @return 结果
     */
    @Override
    public int updateBankTransferTaskInfo(BankTransferTaskInfoDO bankTransferTaskInfo) {
        bankTransferTaskInfo.setUpdateTime(DateUtils.getNowDate());
        return bankTransferTaskInfoMapper.updateBankTransferTaskInfo(bankTransferTaskInfo);
    }

    /**
     * 批量删除Bank Transfer Task Info
     *
     * @param bankTransferIds 需要删除的Bank Transfer Task Info主键
     * @return 结果
     */
    @Override
    public int deleteBankTransferTaskInfoByBankTransferIds(Long[] bankTransferIds) {
        return bankTransferTaskInfoMapper.deleteBankTransferTaskInfoByBankTransferIds(bankTransferIds);
    }

    /**
     * 删除Bank Transfer Task Info信息
     *
     * @param bankTransferId Bank Transfer Task Info主键
     * @return 结果
     */
    @Override
    public int deleteBankTransferTaskInfoByBankTransferId(Long bankTransferId) {
        return bankTransferTaskInfoMapper.deleteBankTransferTaskInfoByBankTransferId(bankTransferId);
    }

    @Override
    public List<Map<String, Object>> findBankInvoiceBatchCount(BankInvoiceBatchStatisticsNumDTO bankInvoiceBatchStatisticsNumDTO) {
        return bankTransferTaskInfoMapper.findBankInvoiceBatchCount(bankInvoiceBatchStatisticsNumDTO);
    }

    @Override
    public List<BankInterestRateTrendDTO> findBankInvoiceBatchList(BankInterestRateTrendDTO bankInterestRateTrendDTO) {
        return bankTransferTaskInfoMapper.findBankInvoiceBatchList(bankInterestRateTrendDTO);
    }

    @Override
    public List<BankInterestRateTrendVO> findBankInvoiceBatchPageList(BankInterestRateTrendVO bankInterestRateTrendVO) {
        return bankTransferTaskInfoMapper.findBankInvoiceBatchPageList(bankInterestRateTrendVO);
    }
}
