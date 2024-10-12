package com.microservices.otmp.masterdata.service;

import com.microservices.otmp.masterdata.domain.BizBaseCurrency;
import com.microservices.otmp.masterdata.domain.BizBaseCustomer;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownList;
import com.microservices.otmp.masterdata.domain.dto.BizBaseCurrencyDTO;

import java.util.List;

/**
 * customerService接口
 *
 * @author lovefamily
 * @date 2022-04-08
 */
public interface IBizBaseCurrencyService {
    /**
     * 查询bu
     *
     * @param id bu主键
     * @return bu
     */
    public BizBaseCurrency selectBizBaseCurrencyById(String id);

    /**
     * 查询bu列表
     *
     * @param bizBaseCurrency bu
     * @return bu集合
     */
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
     * 批量删除bu
     *
     * @param ids 需要删除的bu主键集合
     * @return 结果
     */
    public int deleteBizBaseCurrencyByIds(String[] ids);

    /**
     * 删除bu信息
     *
     * @param id bu主键
     * @return 结果
     */
    public int deleteBizBaseCurrencyById(String id);

    /**
     * 通过SalesOrg获取pool Currency
     *
     * @return 结果
     */
    public List<BizBaseDropDownList> getPoolCurrencyBySalesOrg(String salesOrgCode);

    String importExcel(List<BizBaseCurrency> bizs, String loginName);

    /**
     * 获取所有Currency
     *
     * @return 结果
     */
    public List<BizBaseDropDownList> getAllCurrency();

    /**
     * 通过currencyCode 查询BizBaseCurrencyDTO数据
     *
     * @param currencyCode
     * @return
     */
    BizBaseCurrencyDTO selectBizBaseCurrencyByCode(String currencyCode);

    /**
     * 通过currencyCode集合批量查询BizBaseCurrencyDTO数据
     *
     * @param currencyCodeList
     * @return
     */
    List<BizBaseCurrencyDTO> selectBizBaseCurrencyByCodeList(List<String> currencyCodeList);


}
