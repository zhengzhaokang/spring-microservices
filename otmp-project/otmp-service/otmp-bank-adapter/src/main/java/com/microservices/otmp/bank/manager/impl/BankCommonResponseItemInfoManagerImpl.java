package com.microservices.otmp.bank.manager.impl;

import java.util.List;

import com.microservices.otmp.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.bank.mapper.BankCommonResponseItemInfoMapper;
import com.microservices.otmp.bank.domain.entity.BankCommonResponseItemInfoDO;
import com.microservices.otmp.bank.domain.dto.BankCommonResponseItemInfoDTO;
import com.microservices.otmp.bank.manager.IBankCommonResponseItemInfoManager;

/**
 * Bank Common Response Item InfoManager业务层处理
 * 
 * @author lovefamily
 * @date 2023-10-10
 */
@Service
public class BankCommonResponseItemInfoManagerImpl implements IBankCommonResponseItemInfoManager
{
    private static final Logger log = LoggerFactory.getLogger(BankCommonResponseItemInfoManagerImpl.class);

    @Autowired
    private BankCommonResponseItemInfoMapper bankCommonResponseItemInfoMapper;

    /**
     * 查询Bank Common Response Item Info
     * 
     * @param id Bank Common Response Item Info主键
     * @return Bank Common Response Item InfoDO
     */
    @Override
    public BankCommonResponseItemInfoDO selectBankCommonResponseItemInfoById(Long id)
    {
        return bankCommonResponseItemInfoMapper.selectBankCommonResponseItemInfoById(id);
    }

    /**
     * 查询Bank Common Response Item Info列表
     *
     * @param bankCommonResponseItemInfo Bank Common Response Item Info
     * @return Bank Common Response Item InfoDO
     */
    @Override
    public List<BankCommonResponseItemInfoDO> selectBankCommonResponseItemInfoList(BankCommonResponseItemInfoDTO bankCommonResponseItemInfo)
    {
        return bankCommonResponseItemInfoMapper.selectBankCommonResponseItemInfoList(bankCommonResponseItemInfo);
    }

    /**
     * 新增Bank Common Response Item Info
     *
     * @param bankCommonResponseItemInfo Bank Common Response Item Info
     * @return 结果
     */
    @Override
    public int insertBankCommonResponseItemInfo(BankCommonResponseItemInfoDO bankCommonResponseItemInfo)
    {
        bankCommonResponseItemInfo.setCreateTime(DateUtils.getNowDate());
        return bankCommonResponseItemInfoMapper.insertBankCommonResponseItemInfo (bankCommonResponseItemInfo);
    }

    /**
     * 修改Bank Common Response Item Info
     *
     * @param bankCommonResponseItemInfo  Bank Common Response Item Info
     * @return 结果
     */
    @Override
    public int updateBankCommonResponseItemInfo(BankCommonResponseItemInfoDO bankCommonResponseItemInfo)
    {
        return bankCommonResponseItemInfoMapper.updateBankCommonResponseItemInfo (bankCommonResponseItemInfo);
    }

    /**
     * 批量删除Bank Common Response Item Info
     * 
     * @param ids 需要删除的Bank Common Response Item Info主键
     * @return 结果
     */
    @Override
    public int deleteBankCommonResponseItemInfoByIds(Long[] ids)
    {
        return bankCommonResponseItemInfoMapper.deleteBankCommonResponseItemInfoByIds(ids);
    }

    /**
     * 删除Bank Common Response Item Info信息
     * 
     * @param id Bank Common Response Item Info主键
     * @return 结果
     */
    @Override
    public int deleteBankCommonResponseItemInfoById(Long id)
    {
        return bankCommonResponseItemInfoMapper.deleteBankCommonResponseItemInfoById(id);
    }
}
