package com.microservices.otmp.bank.manager.impl;

import java.util.List;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.bank.mapper.BankTransferInvoiceResponseInfoMapper;
import com.microservices.otmp.bank.domain.entity.BankTransferInvoiceResponseInfoDO;
import com.microservices.otmp.bank.domain.dto.BankTransferInvoiceResponseInfoDTO;
import com.microservices.otmp.bank.manager.IBankTransferInvoiceResponseInfoManager;

/**
 * 银行处理后返回发票状态信息Manager业务层处理
 * 
 * @author lovefamily
 * @date 2023-10-09
 */
@Service
public class BankTransferInvoiceResponseInfoManagerImpl implements IBankTransferInvoiceResponseInfoManager
{
    private static final Logger log = LoggerFactory.getLogger(BankTransferInvoiceResponseInfoManagerImpl.class);

    @Autowired
    private BankTransferInvoiceResponseInfoMapper bankTransferInvoiceResponseInfoMapper;

    /**
     * 查询银行处理后返回发票状态信息
     * 
     * @param id 银行处理后返回发票状态信息主键
     * @return 银行处理后返回发票状态信息DO
     */
    @Override
    public BankTransferInvoiceResponseInfoDO selectBankTransferInvoiceResponseInfoById(Long id)
    {
        return bankTransferInvoiceResponseInfoMapper.selectBankTransferInvoiceResponseInfoById(id);
    }

    /**
     * 查询银行处理后返回发票状态信息列表
     *
     * @param bankTransferInvoiceResponseInfo 银行处理后返回发票状态信息
     * @return 银行处理后返回发票状态信息DO
     */
    @Override
    public List<BankTransferInvoiceResponseInfoDO> selectBankTransferInvoiceResponseInfoList(BankTransferInvoiceResponseInfoDTO bankTransferInvoiceResponseInfo)
    {
        return bankTransferInvoiceResponseInfoMapper.selectBankTransferInvoiceResponseInfoList(bankTransferInvoiceResponseInfo);
    }

    /**
     * 新增银行处理后返回发票状态信息
     *
     * @param bankTransferInvoiceResponseInfo 银行处理后返回发票状态信息
     * @return 结果
     */
    @Override
    public int insertBankTransferInvoiceResponseInfo(BankTransferInvoiceResponseInfoDO bankTransferInvoiceResponseInfo)
    {
        bankTransferInvoiceResponseInfo.setCreateTime(DateUtils.getNowDate());
        return bankTransferInvoiceResponseInfoMapper.insertBankTransferInvoiceResponseInfo (bankTransferInvoiceResponseInfo);
    }

    /**
     * 修改银行处理后返回发票状态信息
     *
     * @param bankTransferInvoiceResponseInfo  银行处理后返回发票状态信息
     * @return 结果
     */
    @Override
    public int updateBankTransferInvoiceResponseInfo(BankTransferInvoiceResponseInfoDO bankTransferInvoiceResponseInfo)
    {
        bankTransferInvoiceResponseInfo.setUpdateTime(DateUtils.getNowDate());
        return bankTransferInvoiceResponseInfoMapper.updateBankTransferInvoiceResponseInfo (bankTransferInvoiceResponseInfo);
    }

    /**
     * 批量删除银行处理后返回发票状态信息
     * 
     * @param ids 需要删除的银行处理后返回发票状态信息主键
     * @return 结果
     */
    @Override
    public int deleteBankTransferInvoiceResponseInfoByIds(Long[] ids)
    {
        return bankTransferInvoiceResponseInfoMapper.deleteBankTransferInvoiceResponseInfoByIds(ids);
    }

    /**
     * 删除银行处理后返回发票状态信息信息
     * 
     * @param id 银行处理后返回发票状态信息主键
     * @return 结果
     */
    @Override
    public int deleteBankTransferInvoiceResponseInfoById(Long id)
    {
        return bankTransferInvoiceResponseInfoMapper.deleteBankTransferInvoiceResponseInfoById(id);
    }
}
