package com.microservices.otmp.masterdata.mapper;

import java.util.List;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseTransactionCurrency;

/**
 * Transaction CurrencyMapper接口
 * 
 * @author lovefamily
 * @date 2022-04-09
 */
public interface BizBaseTransactionCurrencyMapper
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
    @DataPermissions(tableName = "biz_base_transaction_currency")
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
     * 删除Transaction Currency
     * 
     * @param id Transaction Currency主键
     * @return 结果
     */
    public int deleteBizBaseTransactionCurrencyById(Long id);

    /**
     * 批量删除Transaction Currency
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseTransactionCurrencyByIds(Long[] ids);
}
