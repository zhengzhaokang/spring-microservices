package com.microservices.otmp.bank.service.impl;

import java.util.List;
import java.util.ArrayList;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.bank.manager.IBankTransferMetadataInfoManager;
import com.microservices.otmp.bank.domain.dto.BankTransferMetadataInfoDTO;
import com.microservices.otmp.bank.domain.entity.BankTransferMetadataInfoDO;
import com.microservices.otmp.bank.service.IBankTransferMetadataInfoService;

/**
 * Bank Transfer Metadata InfoService业务层处理
 * 
 * @author lovefamily
 * @date 2023-08-29
 */
@Service
@Slf4j
public class BankTransferMetadataInfoServiceImpl implements IBankTransferMetadataInfoService
{

    @Autowired
    private IBankTransferMetadataInfoManager bankTransferMetadataInfoManager;

    /**
     * 查询Bank Transfer Metadata Info
     * 
     * @param metadataInfoId Bank Transfer Metadata Info主键
     * @return Bank Transfer Metadata InfoDTO
     */
    @Override
    public BankTransferMetadataInfoDTO selectBankTransferMetadataInfoByMetadataInfoId(Long metadataInfoId)
    {
         BankTransferMetadataInfoDO bankTransferMetadataInfoDO =  bankTransferMetadataInfoManager.selectBankTransferMetadataInfoByMetadataInfoId(metadataInfoId);
         BankTransferMetadataInfoDTO bankTransferMetadataInfoDTO = new BankTransferMetadataInfoDTO();
         BeanUtils.copyProperties(bankTransferMetadataInfoDO, bankTransferMetadataInfoDTO);
        return bankTransferMetadataInfoDTO;
    }

    /**
     * 查询Bank Transfer Metadata Info列表
     *
     * @param bankTransferMetadataInfo Bank Transfer Metadata Info
     * @return Bank Transfer Metadata InfoDTO
     */
    @Override
    public List<BankTransferMetadataInfoDTO> selectBankTransferMetadataInfoList(BankTransferMetadataInfoDTO bankTransferMetadataInfo)
    {

        List<BankTransferMetadataInfoDO> bankTransferMetadataInfoDOS =
                    bankTransferMetadataInfoManager.selectBankTransferMetadataInfoList(bankTransferMetadataInfo);
        List<BankTransferMetadataInfoDTO> bankTransferMetadataInfoList = new ArrayList<>(bankTransferMetadataInfoDOS.size());
        BeanUtils.copyListProperties(bankTransferMetadataInfoDOS, bankTransferMetadataInfoList, BankTransferMetadataInfoDTO.class);
        return bankTransferMetadataInfoList;

    }

    /**
     * 新增Bank Transfer Metadata Info
     * 
     * @param bankTransferMetadataInfo Bank Transfer Metadata Info
     * @return 结果
     */
    @Override
    public int insertBankTransferMetadataInfo(BankTransferMetadataInfoDTO bankTransferMetadataInfo)
    {
        bankTransferMetadataInfo.setCreateTime(DateUtils.getNowDate());
        BankTransferMetadataInfoDO bankTransferMetadataInfoDO =new  BankTransferMetadataInfoDO();
        BeanUtils.copyProperties(bankTransferMetadataInfo, bankTransferMetadataInfoDO);
        return bankTransferMetadataInfoManager.insertBankTransferMetadataInfo(bankTransferMetadataInfoDO);
    }

    /**
     * 修改Bank Transfer Metadata Info
     * 
     * @param bankTransferMetadataInfo Bank Transfer Metadata Info
     * @return 结果
     */
    @Override
    public int updateBankTransferMetadataInfo(BankTransferMetadataInfoDTO bankTransferMetadataInfo)
    {
        bankTransferMetadataInfo.setUpdateTime(DateUtils.getNowDate());
       BankTransferMetadataInfoDO bankTransferMetadataInfoDO =new  BankTransferMetadataInfoDO();
        BeanUtils.copyProperties(bankTransferMetadataInfo, bankTransferMetadataInfoDO);
        return bankTransferMetadataInfoManager.updateBankTransferMetadataInfo(bankTransferMetadataInfoDO);
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
        return bankTransferMetadataInfoManager.deleteBankTransferMetadataInfoByMetadataInfoIds(metadataInfoIds);
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
        return bankTransferMetadataInfoManager.deleteBankTransferMetadataInfoByMetadataInfoId(metadataInfoId);
    }
}
