package com.microservices.otmp.masterdata.mapper;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseCurrency;
import com.microservices.otmp.masterdata.domain.BizBaseCustomer;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownList;
import com.microservices.otmp.masterdata.domain.entity.BizBaseCurrencyDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BizBasePaymentIntegrationModeMapper接口
 *
 * @author lovefamily
 * @date 2022-04-09
 */
public interface BizBaseCurrencyMapper {

    /**
     * 查询bu
     *
     * @param id bu主键
     * @return bu
     */
    public BizBaseCurrency selectBizBaseCurrencyById(@Param("id") String id);

    /**
     * 查询bu列表
     *
     * @param bizBaseCurrency bu
     * @return bu集合
     */
    @DataPermissions(tableName = "biz_base_currency")
    public List<BizBaseCurrency> selectBizBaseCurrencyList(BizBaseCurrency bizBaseCurrency);

    /**
     * 新增bu
     *
     * @param bizBaseCurrency bu
     * @return 结果
     */
    public int insertBizBaseCurrency(BizBaseCurrency bizBaseCurrency);

    /**
     * 修改bu
     *
     * @param bizBaseCurrency bu
     * @return 结果
     */
    public int updateBizBaseCurrency(BizBaseCurrency bizBaseCurrency);

    /**
     * 删除bu
     *
     * @param id bu主键
     * @return 结果
     */
    public int deleteBizBaseCurrencyById(@Param("id") String id);

    /**
     * 批量删除bu
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseCurrencyByIds(@Param("ids") String[] ids);

    /**
     * 获取所有Currency
     *
     * @return 结果
     */
    public List<BizBaseDropDownList> getAllCurrency();

    /**
     * 通过SalesOrg获取pool Currency
     *
     * @return 结果
     */
    public List<BizBaseDropDownList> getPoolCurrencyBySalesOrg(@Param("salesOrgCode") String salesOrgCode);

    /**
     * 通过currencyCode获取Currency数据
     * @param currencyCode
     * @return
     */
    BizBaseCurrencyDO selectBizBaseCurrencyByCode(@Param("currencyCode") String currencyCode);

    /**
     * 通过currencyCode集合批量获取Currency数据
     * @param currencyCodeList
     * @return
     */
    List<BizBaseCurrencyDO> selectBizBaseCurrencyByCodeList(@Param("currencyCodeList") List<String> currencyCodeList);

    List<BizBaseCustomer> selectBaseCurrencyListCheck(BizBaseCurrency bizBaseCurrency);
}
