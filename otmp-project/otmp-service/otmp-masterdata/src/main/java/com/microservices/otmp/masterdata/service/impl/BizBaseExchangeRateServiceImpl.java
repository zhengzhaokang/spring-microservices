package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.masterdata.domain.BizBaseExchangeRate;
import com.microservices.otmp.masterdata.domain.dto.BizBaseExchangeRateDTO;
import com.microservices.otmp.masterdata.domain.dto.ExchangeRateDTO;
import com.microservices.otmp.masterdata.mapper.BizBaseExchangeRateMapper;
import com.microservices.otmp.masterdata.service.IBizBaseExchangeRateService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Exchange RateService业务层处理
 *
 * @author lovefamily
 * @date 2022-04-09
 */
@Service
public class BizBaseExchangeRateServiceImpl implements IBizBaseExchangeRateService {
    @Autowired
    private BizBaseExchangeRateMapper bizBaseExchangeRateMapper;

    private static final String M = "M";
    private static final String DAILY = "Daily";
    private static final String RVAL = "RVAL";
    private static final String MONTHLY = "Monthly";
    private static final String USD = "USD";

    /**
     * 查询Exchange Rate
     *
     * @param id Exchange Rate主键
     * @return Exchange Rate
     */
    @Override
    public BizBaseExchangeRate selectBizBaseExchangeRateById(Long id) {
        return bizBaseExchangeRateMapper.selectBizBaseExchangeRateById(id);
    }

    @Override
    @Cacheable(value = "exchangeRate", key = "#rateDate+'_'+#fromCurrency+'_'+#toCurrency")
    public BizBaseExchangeRateDTO getExchangeRateByRemote(Date rateDate, String fromCurrency, String toCurrency) throws ParseException {
        BizBaseExchangeRate condition = new BizBaseExchangeRate();
        condition.setRateDate(rateDate);
        condition.setCurrencyCode(fromCurrency);
        condition.setExchangeRateType("M");
        //逻辑体现在sql里 取请求参数rateDate 比他小,挨着最近的日期的数据,如果没传rateDate则取最新数据
        List<BizBaseExchangeRate> fromCurrencyRate = bizBaseExchangeRateMapper.selectBizBaseExchangeRateListByRemote(condition);
        if (fromCurrencyRate.size() != 1) {
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EXCHANGE_RATE_FROM_NOT_EXSIT), DefaultErrorMessage.EXCHANGE_RATE_FROM_NOT_EXSIT.intValue());
        }
        condition.setCurrencyCode(toCurrency);
        List<BizBaseExchangeRate> toCurrencyRate = bizBaseExchangeRateMapper.selectBizBaseExchangeRateListByRemote(condition);
        if (toCurrencyRate.size() != 1) {
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EXCHANGE_RATE_TO_NOT_EXSIT), DefaultErrorMessage.EXCHANGE_RATE_TO_NOT_EXSIT.intValue());
        }
        BigDecimal returnRate = fromCurrencyRate.get(0).getRateValue().divide(toCurrencyRate.get(0).getRateValue(), 8, RoundingMode.HALF_UP);

        //两者rate date 必须一致
        CommonUtils.isNull(fromCurrencyRate.get(0).getRateDate(), "error! fromCurrencyRate has no rate date");
        CommonUtils.isNull(toCurrencyRate.get(0).getRateDate(), "error! toCurrencyRate has no rate date");
        if (fromCurrencyRate.get(0).getRateDate().compareTo(toCurrencyRate.get(0).getRateDate()) != 0) {
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EXCHANGE_RATE_DATE_DIFFERENT), DefaultErrorMessage.EXCHANGE_RATE_DATE_DIFFERENT.intValue());
        }


        BizBaseExchangeRateDTO returnExchangeRateDTO = new BizBaseExchangeRateDTO();
        //fromCurrency对usd的值 也就是 库里存的值
        //returnExchangeRateDTO.setRateToUsd(fromCurrencyRate.get(0).getRateValue());
        //fromCurrency对toCurrency的值 也就是 两者库里值的 相除
        returnExchangeRateDTO.setRateToCurrency(returnRate);
        returnExchangeRateDTO.setRateDate(fromCurrencyRate.get(0).getRateDate());
        //根据业务需求返回fromCurrency
        returnExchangeRateDTO.setCurrencyCode(fromCurrency);
        returnExchangeRateDTO.setFromCurrency(fromCurrency);
        returnExchangeRateDTO.setToCurrency(toCurrency);
        return returnExchangeRateDTO;
    }

    public List<BizBaseExchangeRateDTO> getExchangeRateByRemoteList(List<ExchangeRateDTO> exchangeRateDTOList) throws ParseException {
        List<BizBaseExchangeRateDTO> dtos = new ArrayList<>();
        if (exchangeRateDTOList.size() == 1) {
            BizBaseExchangeRateDTO exchangeRateByRemote = getExchangeRateByRemote(exchangeRateDTOList.get(0).getRateDate(), exchangeRateDTOList.get(0).getFromCurrency(), exchangeRateDTOList.get(0).getToCurrency());
            dtos.add(exchangeRateByRemote);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.OTMP_DEFAULT_DATE_PATTERN);
            String now = sdf.format(DateUtils.getNowDate());
            String rateDate = sdf.format(exchangeRateDTOList.get(0).getRateDate());
            //判断第一条RateDate是否为最新,且list中RateDate只有一个值
            HashSet<Date> set = new HashSet<>();
            for (ExchangeRateDTO exchangeRateDTO : exchangeRateDTOList) {
                set.add(exchangeRateDTO.getRateDate());
            }
            if (Objects.equals(now, rateDate) && set.size() == 1) {
                dtos.addAll(getExchangeRateByNow(exchangeRateDTOList));
            } else {
                for (ExchangeRateDTO exchangeRateDTO : exchangeRateDTOList) {
                    BizBaseExchangeRateDTO exchangeRateByRemote = getExchangeRateByRemote(exchangeRateDTO.getRateDate(), exchangeRateDTO.getFromCurrency(), exchangeRateDTO.getToCurrency());
                    dtos.add(exchangeRateByRemote);
                }
            }
        }
        if (CollectionUtils.isEmpty(dtos) || exchangeRateDTOList.size() != dtos.size()) {
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EXCHANGE_RATE_FROM_NOT_EXSIT), DefaultErrorMessage.EXCHANGE_RATE_FROM_NOT_EXSIT.intValue());
        }
        return dtos;
    }

    /**
     * rateDate为当日的,批量获取汇率
     *
     * @param exchangeRateDTOList
     * @return
     * @throws ParseException
     */
    public List<BizBaseExchangeRateDTO> getExchangeRateByNow(List<ExchangeRateDTO> exchangeRateDTOList) throws ParseException {
        List<BizBaseExchangeRateDTO> dtos = new ArrayList<>();
        //根据currency_code分组查询每组最近一条
        List<BizBaseExchangeRate> currencyRate = bizBaseExchangeRateMapper.selectBizBaseExchangeRateListNow();
        //currencyCode+为key,rateValue为值放入valueMap, rateDate为值放入dateMap
        HashMap<String, BigDecimal> valueMap = new HashMap<>();
        HashMap<String, Date> dateMap = new HashMap<>();
        for (BizBaseExchangeRate exchangeRate : currencyRate) {
            valueMap.put(exchangeRate.getCurrencyCode(), exchangeRate.getRateValue());
            dateMap.put(exchangeRate.getCurrencyCode(), exchangeRate.getRateDate());
        }
        for (ExchangeRateDTO exchangeRateDTO : exchangeRateDTOList) {
            CommonUtils.isNull(valueMap.get(exchangeRateDTO.getFromCurrency()), "error! fromCurrencyRate has no rate date, request for fromCurrencyRate = " + exchangeRateDTO.getFromCurrency());
            CommonUtils.isNull(valueMap.get(exchangeRateDTO.getToCurrency()), "error! toCurrencyRate has no rate date, request for toCurrencyRate = " + exchangeRateDTO.getToCurrency());
            BigDecimal fromCurrencyRate = valueMap.get(exchangeRateDTO.getFromCurrency());
            BigDecimal toCurrencyRate = valueMap.get(exchangeRateDTO.getToCurrency());
            BigDecimal returnRate = fromCurrencyRate.divide(toCurrencyRate, 8, RoundingMode.HALF_UP);
            BizBaseExchangeRateDTO returnExchangeRateDTO = new BizBaseExchangeRateDTO();
            //fromCurrency对usd的值 也就是 库里存的值
            returnExchangeRateDTO.setRateToUsd(fromCurrencyRate);
            //fromCurrency对toCurrency的值 也就是 两者库里值的 相除
            returnExchangeRateDTO.setRateToCurrency(returnRate);
            returnExchangeRateDTO.setRateDate(dateMap.get(exchangeRateDTO.getFromCurrency()));
            //根据业务需求返回fromCurrency
            returnExchangeRateDTO.setCurrencyCode(exchangeRateDTO.getFromCurrency());
            returnExchangeRateDTO.setFromCurrency(exchangeRateDTO.getFromCurrency());
            returnExchangeRateDTO.setToCurrency(exchangeRateDTO.getToCurrency());
            dtos.add(returnExchangeRateDTO);
        }
        return dtos;

    }

    /**
     * 查询Exchange Rate列表
     *
     * @param bizBaseExchangeRate Exchange Rate
     * @return Exchange Rate
     */
    @Override
    public List<BizBaseExchangeRate> selectBizBaseExchangeRateList(BizBaseExchangeRate bizBaseExchangeRate) {

        String ids = bizBaseExchangeRate.getIds();
        if (StringUtils.isNotEmpty(ids)) {
            List<String> idsList = Arrays.stream(ids.split(",")).collect(Collectors.toList());
            List<Long> list = idsList.stream().map(Long::parseLong).collect(Collectors.toList());
            bizBaseExchangeRate.setIdsList(list);
        }
        if (StringUtils.isNotEmpty(bizBaseExchangeRate.getRateDateStr())) {
            try {
                bizBaseExchangeRate.setRateDate(DateUtils.dateParse(bizBaseExchangeRate.getRateDateStr(),"MM/dd/yyyy"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        List<BizBaseExchangeRate> bizBaseExchangeRates = bizBaseExchangeRateMapper.selectBizBaseExchangeRateList(bizBaseExchangeRate);
        bizBaseExchangeRates.forEach(rate -> {
            if (rate.getExchangeRateType().equals(M)) {
                rate.setExchangeRateType(DAILY);
            } else if (rate.getExchangeRateType().equals(RVAL)) {
                rate.setExchangeRateType(MONTHLY);
            }
        });
        return bizBaseExchangeRates;
    }

    @Override
    public String importExcel(List<BizBaseExchangeRate> bizs, String loginName) {
        AtomicInteger successNum = new AtomicInteger();
        AtomicInteger failureNum = new AtomicInteger();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        if (CollectionUtils.isEmpty(bizs)) {
            throw new ServiceException("导入MasterData 数据不能为空！");
        }
        for (BizBaseExchangeRate bizBaseExchangeRate : bizs) {
            try {
                bizBaseExchangeRate.setStatus("Y");
                List<BizBaseExchangeRate> buList = bizBaseExchangeRateMapper.selectBizBaseExchangeRateListCheck(bizBaseExchangeRate);
                if (CollectionUtils.isNotEmpty(buList)) {
                    bizBaseExchangeRate.setId(buList.get(0).getId());
                    bizBaseExchangeRate.setUpdateTime(DateUtils.getNowDate());
                    bizBaseExchangeRateMapper.updateBizBaseExchangeRate(bizBaseExchangeRate);
                    successNum.getAndIncrement();
                    continue;
                }
                bizBaseExchangeRate.setCreateTime(DateUtils.getNowDate());
                bizBaseExchangeRate.setUpdateTime(DateUtils.getNowDate());
                bizBaseExchangeRate.setCreateBy(loginName);
                bizBaseExchangeRateMapper.insertBizBaseExchangeRate(bizBaseExchangeRate);

                successNum.getAndIncrement();
            } catch (Exception e) {
                failureNum.getAndIncrement();
                String msg = "<br/>" + failureNum + "MasterData导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum.intValue() > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 新增Exchange Rate
     *
     * @param bizBaseExchangeRate Exchange Rate
     * @return 结果
     */
    @Override
    @CacheEvict(value = "exchangeRate", allEntries = true)
    public int insertBizBaseExchangeRate(BizBaseExchangeRate bizBaseExchangeRate) {
        bizBaseExchangeRate.setCreateTime(DateUtils.getNowDate());
        return bizBaseExchangeRateMapper.insertBizBaseExchangeRate(bizBaseExchangeRate);
    }

    /**
     * 修改Exchange Rate
     *
     * @param bizBaseExchangeRate Exchange Rate
     * @return 结果
     */
    @Override
    @CacheEvict(value = "exchangeRate", allEntries = true)
    public int updateBizBaseExchangeRate(BizBaseExchangeRate bizBaseExchangeRate) {
        bizBaseExchangeRate.setUpdateTime(DateUtils.getNowDate());
        return bizBaseExchangeRateMapper.updateBizBaseExchangeRate(bizBaseExchangeRate);
    }

    /**
     * 批量删除Exchange Rate
     *
     * @param ids 需要删除的Exchange Rate主键
     * @return 结果
     */
    @Override
    @CacheEvict(value = "exchangeRate", allEntries = true)
    public int deleteBizBaseExchangeRateByIds(Long[] ids) {
        return bizBaseExchangeRateMapper.deleteBizBaseExchangeRateByIds(ids);
    }

    /**
     * 删除Exchange Rate信息
     *
     * @param id Exchange Rate主键
     * @return 结果
     */
    @Override
    @CacheEvict(value = "exchangeRate", allEntries = true)
    public int deleteBizBaseExchangeRateById(Long id) {
        return bizBaseExchangeRateMapper.deleteBizBaseExchangeRateById(id);
    }

    @Override
    public BizBaseExchangeRate exchangeRates(String transactionCurrency, String targetCurrency) throws ParseException {

        if (StringUtils.isEmpty(transactionCurrency) || StringUtils.isEmpty(targetCurrency)) {
            throw new OtmpException("transactionCurrency or targetCurrency is required.");
        }
        Date nowDate = DateUtils.getNowDate();
        BizBaseExchangeRate bizBaseExchangeRate = new BizBaseExchangeRate();
        List<ExchangeRateDTO> exchangeRateDTOList = new ArrayList<>();
        ExchangeRateDTO bizBaseExchangeRateUsd = new ExchangeRateDTO();
        bizBaseExchangeRateUsd.setFromCurrency(transactionCurrency);
        bizBaseExchangeRateUsd.setToCurrency(USD);
        bizBaseExchangeRateUsd.setRateDate(nowDate);
        exchangeRateDTOList.add(bizBaseExchangeRateUsd);
        ExchangeRateDTO bizBaseExchangeRateLocal = new ExchangeRateDTO();
        bizBaseExchangeRateLocal.setFromCurrency(transactionCurrency);
        bizBaseExchangeRateLocal.setToCurrency(targetCurrency);
        bizBaseExchangeRateLocal.setRateDate(nowDate);
        exchangeRateDTOList.add(bizBaseExchangeRateLocal);
        List<BizBaseExchangeRateDTO> exchangeRateByRemoteList = this.getExchangeRateByRemoteList(exchangeRateDTOList);
        for (BizBaseExchangeRateDTO bizBaseExchangeRateDTO : exchangeRateByRemoteList) {
            //local 、 Tran 都是USD，直接返回
            if (USD.equals(targetCurrency)){
                bizBaseExchangeRate.setRateDate(bizBaseExchangeRate.getRateDate());
                bizBaseExchangeRate.setExchangeRateU(bizBaseExchangeRateDTO.getRateToCurrency());
                bizBaseExchangeRate.setExchangeRateL(bizBaseExchangeRateDTO.getRateToCurrency());
                break;
            }
            //local 、Tran 的币种有一个是USD
            if (USD.equals(bizBaseExchangeRateDTO.getToCurrency())) {
                bizBaseExchangeRate.setRateDate(bizBaseExchangeRate.getRateDate());
                bizBaseExchangeRate.setExchangeRateU(bizBaseExchangeRateDTO.getRateToCurrency());
            } else {
                bizBaseExchangeRate.setExchangeRateL(bizBaseExchangeRateDTO.getRateToCurrency());
            }
        }

        return bizBaseExchangeRate;
    }
}
