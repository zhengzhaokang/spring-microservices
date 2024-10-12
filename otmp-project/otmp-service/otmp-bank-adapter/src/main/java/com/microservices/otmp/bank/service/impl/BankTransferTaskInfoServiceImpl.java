package com.microservices.otmp.bank.service.impl;

import com.microservices.otmp.bank.domain.dto.BankTransferTaskInfoDTO;
import com.microservices.otmp.bank.domain.entity.BankTransferTaskInfoDO;
import com.microservices.otmp.bank.manager.IBankTransferTaskInfoManager;
import com.microservices.otmp.bank.service.IBankTransferTaskInfoService;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.bank.service.IBankTransferTaskInfoService;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
