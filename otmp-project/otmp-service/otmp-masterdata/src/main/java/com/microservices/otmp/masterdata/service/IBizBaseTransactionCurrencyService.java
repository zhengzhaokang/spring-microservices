package com.microservices.otmp.masterdata.service;

import java.util.List;
import com.microservices.otmp.masterdata.domain.BizBaseTransactionCurrency;

/**
 * Transaction CurrencyService接口
 * 
 * @author lovefamily
 * @date 2022-04-09
 */
public interface IBizBaseTransactionCurrencyService
{
    /**
     * 查询Transaction Currency
     * 
     * @param id Transaction Currency主键
     * @return Transaction Currency
     */
    public BizBaseTransactionCurrency selectBizBaseTransactionCurrencyById(Long id);

    /**
     * 查询Transaction Currency列表
     * 
     * @param bizBaseTransactionCurrency Transaction Currency
     * @return Transaction Currency集合
     */
    public List<BizBaseTransactionCurrency> selectBizBaseTransactionCurrencyList(BizBaseTransactionCurrency bizBaseTransactionCurrency);

    /**
     * 新增Transaction Currency
     * 
     * @param bizBaseTransactionCurrency Transaction Currency
     * @return 结果
     */
    public int insertBizBaseTransactionCurrency(BizBaseTransactionCurrency bizBaseTransactionCurrency);

    /**
     * 修改Transaction Currency
     * 
     * @param bizBaseTransactionCurrency Transaction Currency
     * @return 结果
     */
    public int updateBizBaseTransactionCurrency(BizBaseTransactionCurrency bizBaseTransactionCurrency);

    /**
     * 批量删除Transaction Currency
     * 
     * @param ids 需要删除的Transaction Currency主键集合
     * @return 结果
     */
    public int deleteBizBaseTransactionCurrencyByIds(Long[] ids);

    /**
     * 删除Transaction Currency信息
     * 
     * @param id Transaction Currency主键
     * @return 结果
     */
    public int deleteBizBaseTransactionCurrencyById(Long id);
}
