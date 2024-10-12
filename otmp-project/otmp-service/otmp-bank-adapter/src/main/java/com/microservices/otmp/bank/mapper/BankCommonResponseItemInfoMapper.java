package com.microservices.otmp.bank.mapper;

import com.microservices.otmp.bank.domain.dto.BankCommonResponseItemInfoDTO;
import com.microservices.otmp.bank.domain.entity.BankCommonResponseItemInfoDO;

import java.util.List;


/**
 * Bank Common Response Item InfoMapper接口
 *
 * @author lovefamily
 * @date 2023-10-10
 */
public interface BankCommonResponseItemInfoMapper {
    /**
     * 查询Bank Common Response Item Info
     *
     * @param id Bank Common Response Item Info主键
     * @return Bank Common Response Item Info
     */
    public BankCommonResponseItemInfoDO selectBankCommonResponseItemInfoById(Long id);

    /**
     * 查询Bank Common Response Item Info列表
     *
     * @param bankCommonResponseItemInfo Bank Common Response Item Info
     * @return Bank Common Response Item Info集合
     */
    public List<BankCommonResponseItemInfoDO> selectBankCommonResponseItemInfoList(BankCommonResponseItemInfoDTO bankCommonResponseItemInfo);

    /**
     * 新增Bank Common Response Item Info
     *
     * @param bankCommonResponseItemInfo Bank Common Response Item Info
     * @return 结果
     */
    public int insertBankCommonResponseItemInfo(BankCommonResponseItemInfoDO bankCommonResponseItemInfo);

    /**
     * 修改Bank Common Response Item Info
     *
     * @param bankCommonResponseItemInfo Bank Common Response Item Info
     * @return 结果
     */
    public int updateBankCommonResponseItemInfo(BankCommonResponseItemInfoDO bankCommonResponseItemInfo);

    /**
     * 删除Bank Common Response Item Info
     *
     * @param id Bank Common Response Item Info主键
     * @return 结果
     */
    public int deleteBankCommonResponseItemInfoById(Long id);

    /**
     * 批量删除Bank Common Response Item Info
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBankCommonResponseItemInfoByIds(Long[] ids);
}
