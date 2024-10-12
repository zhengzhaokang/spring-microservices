package com.microservices.otmp.bank.service;

import com.microservices.otmp.bank.domain.dto.BankCommonResponseHeaderInfoDTO;

import java.util.List;

/**
 * Bank Common Response Header InfoService接口
 *
 * @author lovefamily
 * @date 2023-10-10
 */
public interface IBankCommonResponseHeaderInfoService {
    /**
     * 查询Bank Common Response Header Info
     *
     * @param id Bank Common Response Header Info主键
     * @return Bank Common Response Header InfoDTO
     */
    public BankCommonResponseHeaderInfoDTO selectBankCommonResponseHeaderInfoById(Long id);

    /**
     * 查询Bank Common Response Header Info列表
     *
     * @param bankCommonResponseHeaderInfo Bank Common Response Header Info
     * @return Bank Common Response Header InfoDTO集合
     */
    public List<BankCommonResponseHeaderInfoDTO> selectBankCommonResponseHeaderInfoList(BankCommonResponseHeaderInfoDTO bankCommonResponseHeaderInfo);

    /**
     * 新增Bank Common Response Header Info
     *
     * @param bankCommonResponseHeaderInfo Bank Common Response Header Info
     * @return 结果
     */
    public int insertBankCommonResponseHeaderInfo(BankCommonResponseHeaderInfoDTO bankCommonResponseHeaderInfo);

    /**
     * 修改Bank Common Response Header Info
     *
     * @param bankCommonResponseHeaderInfo Bank Common Response Header Info
     * @return 结果
     */
    public int updateBankCommonResponseHeaderInfo(BankCommonResponseHeaderInfoDTO bankCommonResponseHeaderInfo);

    /**
     * 批量删除Bank Common Response Header Info
     *
     * @param ids 需要删除的Bank Common Response Header Info主键集合
     * @return 结果
     */
    public int deleteBankCommonResponseHeaderInfoByIds(Long[] ids);

    /**
     * 删除Bank Common Response Header Info信息
     *
     * @param id Bank Common Response Header Info主键
     * @return 结果
     */
    public int deleteBankCommonResponseHeaderInfoById(Long id);
}
