package com.microservices.otmp.bank.service;

import java.util.List;
import com.microservices.otmp.bank.domain.dto.BankCommonResponseItemInfoDTO;

/**
 * Bank Common Response Item InfoService接口
 * 
 * @author lovefamily
 * @date 2023-10-10
 */
public interface IBankCommonResponseItemInfoService
{
    /**
     * 查询Bank Common Response Item Info
     * 
     * @param id Bank Common Response Item Info主键
     * @return Bank Common Response Item InfoDTO
     */
    public BankCommonResponseItemInfoDTO selectBankCommonResponseItemInfoById(Long id);

    /**
     * 查询Bank Common Response Item Info列表
     *
     * @param bankCommonResponseItemInfo Bank Common Response Item Info
     * @return Bank Common Response Item InfoDTO集合
     */
    public List<BankCommonResponseItemInfoDTO> selectBankCommonResponseItemInfoList(BankCommonResponseItemInfoDTO bankCommonResponseItemInfo);

    /**
     * 新增Bank Common Response Item Info
     * 
     * @param bankCommonResponseItemInfoDTO Bank Common Response Item Info
     * @return 结果
     */
    public int insertBankCommonResponseItemInfo(BankCommonResponseItemInfoDTO bankCommonResponseItemInfo);

    /**
     * 修改Bank Common Response Item Info
     * 
     * @param bankCommonResponseItemInfoDTO Bank Common Response Item Info
     * @return 结果
     */
    public int updateBankCommonResponseItemInfo(BankCommonResponseItemInfoDTO bankCommonResponseItemInfo);

    /**
     * 批量删除Bank Common Response Item Info
     * 
     * @param ids 需要删除的Bank Common Response Item Info主键集合
     * @return 结果
     */
    public int deleteBankCommonResponseItemInfoByIds(Long[] ids);

    /**
     * 删除Bank Common Response Item Info信息
     * 
     * @param id Bank Common Response Item Info主键
     * @return 结果
     */
    public int deleteBankCommonResponseItemInfoById(Long id);
}
