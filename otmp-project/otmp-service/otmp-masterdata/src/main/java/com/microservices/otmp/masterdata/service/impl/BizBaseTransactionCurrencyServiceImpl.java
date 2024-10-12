package com.microservices.otmp.masterdata.service.impl;

import java.util.List;
import com.microservices.otmp.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.masterdata.mapper.BizBaseTransactionCurrencyMapper;
import com.microservices.otmp.masterdata.domain.BizBaseTransactionCurrency;
import com.microservices.otmp.masterdata.service.IBizBaseTransactionCurrencyService;

/**
 * Transaction CurrencyService业务层处理
 * 
 * @author lovefamily
 * @date 2022-04-09
 */
@Service
public class BizBaseTransactionCurrencyServiceImpl implements IBizBaseTransactionCurrencyService
{
    @Autowired
    private BizBaseTransactionCurrencyMapper bizBaseTransactionCurrencyMapper;

    /**
     * 查询Transaction Currency
     * 
     * @param id Transaction Currency主键
     * @return Transaction Currency
     */
    @Override
    public BizBaseTransactionCurrency selectBizBaseTransactionCurrencyById(Long id)
    {
        return bizBaseTransactionCurrencyMapper.selectBizBaseTransactionCurrencyById(id);
    }

    /**
     * 查询Transaction Currency列表
     * 
     * @param bizBaseTransactionCurrency Transaction Currency
     * @return Transaction Currency
     */
    @Override
    public List<BizBaseTransactionCurrency> selectBizBaseTransactionCurrencyList(BizBaseTransactionCurrency bizBaseTransactionCurrency)
    {
        return bizBaseTransactionCurrencyMapper.selectBizBaseTransactionCurrencyList(bizBaseTransactionCurrency);
    }

    /**
     * 新增Transaction Currency
     * 
     * @param bizBaseTransactionCurrency Transaction Currency
     * @return 结果
     */
    @Override
    public int insertBizBaseTransactionCurrency(BizBaseTransactionCurrency bizBaseTransactionCurrency)
    {
        bizBaseTransactionCurrency.setCreateTime(DateUtils.getNowDate());
        return bizBaseTransactionCurrencyMapper.insertBizBaseTransactionCurrency(bizBaseTransactionCurrency);
    }

    /**
     * 修改Transaction Currency
     * 
     * @param bizBaseTransactionCurrency Transaction Currency
     * @return 结果
     */
    @Override
    public int updateBizBaseTransactionCurrency(BizBaseTransactionCurrency bizBaseTransactionCurrency)
    {
        bizBaseTransactionCurrency.setUpdateTime(DateUtils.getNowDate());
        return bizBaseTransactionCurrencyMapper.updateBizBaseTransactionCurrency(bizBaseTransactionCurrency);
    }

    /**
     * 批量删除Transaction Currency
     * 
     * @param ids 需要删除的Transaction Currency主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseTransactionCurrencyByIds(Long[] ids)
    {
        return bizBaseTransactionCurrencyMapper.deleteBizBaseTransactionCurrencyByIds(ids);
    }

    /**
     * 删除Transaction Currency信息
     * 
     * @param id Transaction Currency主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseTransactionCurrencyById(Long id)
    {
        return bizBaseTransactionCurrencyMapper.deleteBizBaseTransactionCurrencyById(id);
    }
}
