package com.microservices.otmp.bank.service;

import java.util.List;
import com.microservices.otmp.bank.domain.dto.BankTransferMetadataInfoDTO;

/**
 * Bank Transfer Metadata InfoService接口
 * 
 * @author lovefamily
 * @date 2023-08-29
 */
public interface IBankTransferMetadataInfoService
{
    /**
     * 查询Bank Transfer Metadata Info
     * 
     * @param metadataInfoId Bank Transfer Metadata Info主键
     * @return Bank Transfer Metadata InfoDTO
     */
    public BankTransferMetadataInfoDTO selectBankTransferMetadataInfoByMetadataInfoId(Long metadataInfoId);

    /**
     * 查询Bank Transfer Metadata Info列表
     *
     * @param bankTransferMetadataInfo Bank Transfer Metadata Info
     * @return Bank Transfer Metadata InfoDTO集合
     */
    public List<BankTransferMetadataInfoDTO> selectBankTransferMetadataInfoList(BankTransferMetadataInfoDTO bankTransferMetadataInfo);

    /**
     * 新增Bank Transfer Metadata Info
     * 
     * @param bankTransferMetadataInfo Bank Transfer Metadata Info
     * @return 结果
     */
    public int insertBankTransferMetadataInfo(BankTransferMetadataInfoDTO bankTransferMetadataInfo);

    /**
     * 修改Bank Transfer Metadata Info
     * 
     * @param bankTransferMetadataInfo Bank Transfer Metadata Info
     * @return 结果
     */
    public int updateBankTransferMetadataInfo(BankTransferMetadataInfoDTO bankTransferMetadataInfo);

    /**
     * 批量删除Bank Transfer Metadata Info
     * 
     * @param metadataInfoIds 需要删除的Bank Transfer Metadata Info主键集合
     * @return 结果
     */
    public int deleteBankTransferMetadataInfoByMetadataInfoIds(Long[] metadataInfoIds);

    /**
     * 删除Bank Transfer Metadata Info信息
     * 
     * @param metadataInfoId Bank Transfer Metadata Info主键
     * @return 结果
     */
    public int deleteBankTransferMetadataInfoByMetadataInfoId(Long metadataInfoId);
}
