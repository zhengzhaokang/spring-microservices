package com.microservices.otmp.masterdata.mapper;

import java.util.List;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseExchangeRate;

/**
 * Exchange RateMapper接口
 * 
 * @author lovefamily
 * @date 2022-04-09
 */
public interface BizBaseExchangeRateMapper
{
    /**
     * 查询Exchange Rate
     * 
     * @param id Exchange Rate主键
     * @return Exchange Rate
     */
    public BizBaseExchangeRate selectBizBaseExchangeRateById(Long id);

    /**
     * 查询Exchange Rate列表
     * 
     * @param bizBaseExchangeRate Exchange Rate
     * @return Exchange Rate集合
     */
    @DataPermissions(tableName = "biz_base_exchange_rate")
    public List<BizBaseExchangeRate> selectBizBaseExchangeRateList(BizBaseExchangeRate bizBaseExchangeRate);

    /**
     * 查询Exchange Rate列表
     *
     * @param bizBaseExchangeRate Exchange Rate
     * @return Exchange Rate集合
     */
    @DataPermissions(tableName = "biz_base_exchange_rate")
    public List<BizBaseExchangeRate> selectBizBaseExchangeRateListByRemote(BizBaseExchangeRate bizBaseExchangeRate);

    /**
     * 新增Exchange Rate
     * 
     * @param bizBaseExchangeRate Exchange Rate
     * @return 结果
     */
    public int insertBizBaseExchangeRate(BizBaseExchangeRate bizBaseExchangeRate);

    /**
     * 修改Exchange Rate
     * 
     * @param bizBaseExchangeRate Exchange Rate
     * @return 结果
     */
    public int updateBizBaseExchangeRate(BizBaseExchangeRate bizBaseExchangeRate);

    /**
     * 删除Exchange Rate
     * 
     * @param id Exchange Rate主键
     * @return 结果
     */
    public int deleteBizBaseExchangeRateById(Long id);

    /**
     * 批量删除Exchange Rate
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseExchangeRateByIds(Long[] ids);

    public List<BizBaseExchangeRate>  selectBizBaseExchangeRateListCheck(BizBaseExchangeRate bizBaseExchangeRate);

    /**
     * 根据currency_code分组查询每组最近一条
     * @return
     */
    public List<BizBaseExchangeRate> selectBizBaseExchangeRateListNow();
}
