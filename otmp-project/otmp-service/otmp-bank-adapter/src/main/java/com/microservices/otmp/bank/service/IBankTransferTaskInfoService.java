package com.microservices.otmp.bank.service;

import com.microservices.otmp.bank.domain.dto.BankTransferTaskInfoDTO;

import java.util.List;

/**
 * Bank Transfer Task InfoService接口
 *
 * @author lovefamily
 * @date 2023-10-09
 */
public interface IBankTransferTaskInfoService {
    /**
     * 查询Bank Transfer Task Info
     *
     * @param bankTransferId Bank Transfer Task Info主键
     * @return Bank Transfer Task InfoDTO
     */
    public BankTransferTaskInfoDTO selectBankTransferTaskInfoByBankTransferId(Long bankTransferId);

    /**
     * 查询Bank Transfer Task Info列表
     *
     * @param bankTransferTaskInfo Bank Transfer Task Info
     * @return Bank Transfer Task InfoDTO集合
     */
    public List<BankTransferTaskInfoDTO> selectBankTransferTaskInfoList(BankTransferTaskInfoDTO bankTransferTaskInfo);

    /**
     * 新增Bank Transfer Task Info
     *
     * @param bankTransferTaskInfo Bank Transfer Task Info
     * @return 结果
     */
    public int insertBankTransferTaskInfo(BankTransferTaskInfoDTO bankTransferTaskInfo);

    /**
     * 修改Bank Transfer Task Info
     *
     * @param bankTransferTaskInfo Bank Transfer Task Info
     * @return 结果
     */
    public int updateBankTransferTaskInfo(BankTransferTaskInfoDTO bankTransferTaskInfo);

    /**
     * 批量删除Bank Transfer Task Info
     *
     * @param bankTransferIds 需要删除的Bank Transfer Task Info主键集合
     * @return 结果
     */
    public int deleteBankTransferTaskInfoByBankTransferIds(Long[] bankTransferIds);

    /**
     * 删除Bank Transfer Task Info信息
     *
     * @param bankTransferId Bank Transfer Task Info主键
     * @return 结果
     */
    public int deleteBankTransferTaskInfoByBankTransferId(Long bankTransferId);
}
