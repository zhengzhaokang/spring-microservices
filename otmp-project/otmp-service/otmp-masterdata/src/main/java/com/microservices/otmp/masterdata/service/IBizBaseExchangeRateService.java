package com.microservices.otmp.masterdata.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import com.microservices.otmp.masterdata.domain.BizBaseExchangeRate;
import com.microservices.otmp.masterdata.domain.dto.BizBaseExchangeRateDTO;
import com.microservices.otmp.masterdata.domain.dto.ExchangeRateDTO;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Exchange RateService接口
 * 
 * @author lovefamily
 * @date 2022-04-09
 */
public interface IBizBaseExchangeRateService
{
    /**
     * 查询Exchange Rate
     * 
     * @param id Exchange Rate主键
     * @return Exchange Rate
     */
    public BizBaseExchangeRate selectBizBaseExchangeRateById(Long id);

    public BizBaseExchangeRateDTO getExchangeRateByRemote(Date rateDate, String fromCurrency, String toCurrency) throws ParseException;

    public List<BizBaseExchangeRateDTO> getExchangeRateByRemoteList(List<ExchangeRateDTO> exchangeRateDTOList) throws ParseException;

    /**
     * 查询Exchange Rate列表
     * 
     * @param bizBaseExchangeRate Exchange Rate
     * @return Exchange Rate集合
     */
    public List<BizBaseExchangeRate> selectBizBaseExchangeRateList(BizBaseExchangeRate bizBaseExchangeRate);

    /**
     * 新增Exchange Rate
     * 
     * @param bizBaseExchangeRate Exchange Rate
     * @return 结果
     */
    public int insertBizBaseExchangeRate(BizBaseExchangeRate bizBaseExchangeRate);

    /**
     * 导入Exchange Rate
     *
     * @param  bizs Rate
     * @return 结果
     */
    public String importExcel(List<BizBaseExchangeRate> bizs, String loginName);
    /**
     * 修改Exchange Rate
     * 
     * @param bizBaseExchangeRate Exchange Rate
     * @return 结果
     */
    public int updateBizBaseExchangeRate(BizBaseExchangeRate bizBaseExchangeRate);

    /**
     * 批量删除Exchange Rate
     * 
     * @param ids 需要删除的Exchange Rate主键集合
     * @return 结果
     */
    public int deleteBizBaseExchangeRateByIds(Long[] ids);

    /**
     * 删除Exchange Rate信息
     * 
     * @param id Exchange Rate主键
     * @return 结果
     */
    public int deleteBizBaseExchangeRateById(Long id);

    BizBaseExchangeRate exchangeRates(String transactionCurrency, String targetCurrency) throws ParseException;
}
