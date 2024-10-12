package com.microservices.otmp.bank.manager.impl;

import java.util.List;

import com.microservices.otmp.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.bank.mapper.BankTransferMetadataInfoMapper;
import com.microservices.otmp.bank.domain.entity.BankTransferMetadataInfoDO;
import com.microservices.otmp.bank.domain.dto.BankTransferMetadataInfoDTO;
import com.microservices.otmp.bank.manager.IBankTransferMetadataInfoManager;

/**
 * Bank Transfer Metadata InfoManager业务层处理
 * 
 * @author lovefamily
 * @date 2023-08-29
 */
@Service
public class BankTransferMetadataInfoManagerImpl implements IBankTransferMetadataInfoManager
{
    private static final Logger log = LoggerFactory.getLogger(BankTransferMetadataInfoManagerImpl.class);

    @Autowired
    private BankTransferMetadataInfoMapper bankTransferMetadataInfoMapper;

    /**
     * 查询Bank Transfer Metadata Info
     * 
     * @param metadataInfoId Bank Transfer Metadata Info主键
     * @return Bank Transfer Metadata InfoDO
     */
    @Override
    public BankTransferMetadataInfoDO selectBankTransferMetadataInfoByMetadataInfoId(Long metadataInfoId)
    {
        return bankTransferMetadataInfoMapper.selectBankTransferMetadataInfoByMetadataInfoId(metadataInfoId);
    }

    /**
     * 查询Bank Transfer Metadata Info列表
     *
     * @param bankTransferMetadataInfo Bank Transfer Metadata Info
     * @return Bank Transfer Metadata InfoDO
     */
    @Override
    public List<BankTransferMetadataInfoDO> selectBankTransferMetadataInfoList(BankTransferMetadataInfoDTO bankTransferMetadataInfo)
    {
        return bankTransferMetadataInfoMapper.selectBankTransferMetadataInfoList(bankTransferMetadataInfo);
    }

    /**
     * 新增Bank Transfer Metadata Info
     *
     * @param bankTransferMetadataInfo Bank Transfer Metadata Info
     * @return 结果
     */
    @Override
    public int insertBankTransferMetadataInfo(BankTransferMetadataInfoDO bankTransferMetadataInfo)
    {
        bankTransferMetadataInfo.setCreateTime(DateUtils.getNowDate());
        return bankTransferMetadataInfoMapper.insertBankTransferMetadataInfo (bankTransferMetadataInfo);
    }

    /**
     * 修改Bank Transfer Metadata Info
     *
     * @param bankTransferMetadataInfo  Bank Transfer Metadata Info
     * @return 结果
     */
    @Override
    public int updateBankTransferMetadataInfo(BankTransferMetadataInfoDO bankTransferMetadataInfo)
    {
        bankTransferMetadataInfo.setUpdateTime(DateUtils.getNowDate());
        return bankTransferMetadataInfoMapper.updateBankTransferMetadataInfo (bankTransferMetadataInfo);
    }

    /**
     * 批量删除Bank Transfer Metadata Info
     * 
     * @param metadataInfoIds 需要删除的Bank Transfer Metadata Info主键
     * @return 结果
     */
    @Override
    public int deleteBankTransferMetadataInfoByMetadataInfoIds(Long[] metadataInfoIds)
    {
        return bankTransferMetadataInfoMapper.deleteBankTransferMetadataInfoByMetadataInfoIds(metadataInfoIds);
    }

    /**
     * 删除Bank Transfer Metadata Info信息
     * 
     * @param metadataInfoId Bank Transfer Metadata Info主键
     * @return 结果
     */
    @Override
    public int deleteBankTransferMetadataInfoByMetadataInfoId(Long metadataInfoId)
    {
        return bankTransferMetadataInfoMapper.deleteBankTransferMetadataInfoByMetadataInfoId(metadataInfoId);
    }
}
