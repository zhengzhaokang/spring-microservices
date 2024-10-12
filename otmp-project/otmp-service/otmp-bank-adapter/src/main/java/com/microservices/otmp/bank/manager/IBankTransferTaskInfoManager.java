package com.microservices.otmp.bank.manager;

import java.util.List;
import com.microservices.otmp.bank.domain.entity.BankTransferTaskInfoDO;
import com.microservices.otmp.bank.domain.dto.BankTransferTaskInfoDTO;


/**
 * Bank Transfer Task InfoManager接口
 * 
 * @author lovefamily
 * @date 2023-10-09
 */
public interface IBankTransferTaskInfoManager
{
    /**
     * 查询Bank Transfer Task Info
     * 
     * @param bankTransferId Bank Transfer Task Info主键
     * @return Bank Transfer Task Info
     */
    public BankTransferTaskInfoDO selectBankTransferTaskInfoByBankTransferId(Long bankTransferId);

    /**
     * 查询Bank Transfer Task Info列表
     *
     * @param bankTransferTaskInfo Bank Transfer Task Info
     * @return Bank Transfer Task Info集合
     */
    public List<BankTransferTaskInfoDO> selectBankTransferTaskInfoList(BankTransferTaskInfoDTO bankTransferTaskInfo);

    /**
     * 新增Bank Transfer Task Info
     *
     * @param bankTransferTaskInfo Bank Transfer Task Info
     * @return 结果
     */
    public int insertBankTransferTaskInfo(BankTransferTaskInfoDO bankTransferTaskInfo);

    /**
     * 修改Bank Transfer Task Info
     *
     * @param bankTransferTaskInfo Bank Transfer Task Info
     * @return 结果
     */
    public int updateBankTransferTaskInfo(BankTransferTaskInfoDO bankTransferTaskInfo);

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
