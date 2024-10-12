package com.microservices.otmp.bank.manager;

import java.util.List;
import com.microservices.otmp.bank.domain.entity.BankCommonResponseHeaderInfoDO;
import com.microservices.otmp.bank.domain.dto.BankCommonResponseHeaderInfoDTO;
import com.microservices.otmp.bank.domain.entity.BankCommonResponseItemInfoDO;


/**
 * Bank Common Response Header InfoManager接口
 * 
 * @author lovefamily
 * @date 2023-10-10
 */
public interface IBankCommonResponseHeaderInfoManager
{
    /**
     * 查询Bank Common Response Header Info
     * 
     * @param id Bank Common Response Header Info主键
     * @return Bank Common Response Header Info
     */
    public BankCommonResponseHeaderInfoDO selectBankCommonResponseHeaderInfoById(Long id);

    /**
     * 查询Bank Common Response Header Info列表
     *
     * @param bankCommonResponseHeaderInfo Bank Common Response Header Info
     * @return Bank Common Response Header Info集合
     */
    public List<BankCommonResponseHeaderInfoDO> selectBankCommonResponseHeaderInfoList(BankCommonResponseHeaderInfoDTO bankCommonResponseHeaderInfo);

    /**
     * 新增Bank Common Response Header Info
     *
     * @param bankCommonResponseHeaderInfo Bank Common Response Header Info
     * @return 结果
     */
    public int insertBankCommonResponseHeaderInfo(BankCommonResponseHeaderInfoDO bankCommonResponseHeaderInfo);

    /**
     * 修改Bank Common Response Header Info
     *
     * @param bankCommonResponseHeaderInfo Bank Common Response Header Info
     * @return 结果
     */
    public int updateBankCommonResponseHeaderInfo(BankCommonResponseHeaderInfoDO bankCommonResponseHeaderInfo);

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


    /**
     * 批量删除Bank Common Response Item Info
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBankCommonResponseItemInfoByHeaderIds(Long[] ids);

    /**
     * 批量新增Bank Common Response Item Info
     *
     * @param bankCommonResponseItemInfoList Bank Common Response Item Info列表
     * @return 结果
     */
    public int batchBankCommonResponseItemInfo(List<BankCommonResponseItemInfoDO> bankCommonResponseItemInfoList);


    /**
     * 通过Bank Common Response Header Info主键删除Bank Common Response Item Info信息
     *
     * @param id Bank Common Response Header InfoID
     * @return 结果
     */
    public int deleteBankCommonResponseItemInfoByHeaderId(Long id);

}
