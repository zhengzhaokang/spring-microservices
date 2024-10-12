package com.microservices.otmp.bank.mapper;

import java.util.List;
import com.microservices.otmp.bank.domain.entity.BankTransferMetadataInfoDO;
import com.microservices.otmp.bank.domain.dto.BankTransferMetadataInfoDTO;


/**
 * Bank Transfer Metadata InfoMapper接口
 * 
 * @author lovefamily
 * @date 2023-08-29
 */
public interface BankTransferMetadataInfoMapper
{
    /**
     * 查询Bank Transfer Metadata Info
     * 
     * @param metadataInfoId Bank Transfer Metadata Info主键
     * @return Bank Transfer Metadata Info
     */
    public BankTransferMetadataInfoDO selectBankTransferMetadataInfoByMetadataInfoId(Long metadataInfoId);

    /**
     * 查询Bank Transfer Metadata Info列表
     * 
     * @param bankTransferMetadataInfo Bank Transfer Metadata Info
     * @return Bank Transfer Metadata Info集合
     */
    public List<BankTransferMetadataInfoDO> selectBankTransferMetadataInfoList(BankTransferMetadataInfoDTO bankTransferMetadataInfo);

    /**
     * 新增Bank Transfer Metadata Info
     * 
     * @param bankTransferMetadataInfo Bank Transfer Metadata Info
     * @return 结果
     */
    public int insertBankTransferMetadataInfo(BankTransferMetadataInfoDO bankTransferMetadataInfo);

    /**
     * 修改Bank Transfer Metadata Info
     * 
     * @param bankTransferMetadataInfo Bank Transfer Metadata Info
     * @return 结果
     */
    public int updateBankTransferMetadataInfo (BankTransferMetadataInfoDO bankTransferMetadataInfo);

    /**
     * 删除Bank Transfer Metadata Info
     * 
     * @param metadataInfoId Bank Transfer Metadata Info主键
     * @return 结果
     */
    public int deleteBankTransferMetadataInfoByMetadataInfoId(Long metadataInfoId);

    /**
     * 批量删除Bank Transfer Metadata Info
     * 
     * @param metadataInfoIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBankTransferMetadataInfoByMetadataInfoIds(Long[] metadataInfoIds);
}
