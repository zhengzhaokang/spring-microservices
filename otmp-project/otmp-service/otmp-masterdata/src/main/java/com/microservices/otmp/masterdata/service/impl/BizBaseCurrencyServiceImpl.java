package com.microservices.otmp.masterdata.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.masterdata.domain.BizBaseCurrency;
import com.microservices.otmp.masterdata.domain.BizBaseCustomer;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownList;
import com.microservices.otmp.masterdata.domain.dto.BizBaseCurrencyDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseCurrencyDO;
import com.microservices.otmp.masterdata.manager.BizBaseCurrencyManager;
import com.microservices.otmp.masterdata.mapper.BizBaseCurrencyMapper;
import com.microservices.otmp.masterdata.service.IBizBaseCurrencyService;
import com.microservices.otmp.masterdata.util.ConvertStringToLongListUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * bizBaseRegionMarketService业务层处理
 *
 * @author lovefamily
 * @date 2022-04-18
 */
@Service
public class BizBaseCurrencyServiceImpl implements IBizBaseCurrencyService {
    @Autowired
    private BizBaseCurrencyMapper bizBaseCurrencyMapper;

    @Autowired
    private BizBaseCurrencyManager bizBaseCurrencyManager;

    @Autowired
    private RedisUtils redisUtils;
    public static final String REDIS_NAME_PREFIX = "master:";

    public void setCountryToRedis() {
        List<BizBaseCurrency> list = this.selectBizBaseCurrencyList(new BizBaseCurrency());
        Map<String, Object> currencyPoints = list.stream().collect(Collectors.toMap(BizBaseCurrency::getCurrencyCode, BizBaseCurrency::getDecimals, (e1, e2) -> e1));
        redisUtils.putAll(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX+"check_currency_point",currencyPoints, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    /**
     * 查询bu
     *
     * @param id bu主键
     * @return bu
     */
    @Override
    public BizBaseCurrency selectBizBaseCurrencyById(String id) {
        return bizBaseCurrencyMapper.selectBizBaseCurrencyById(id);
    }

    /**
     * 查询bu列表
     *
     * @param bizBaseCurrency bu
     * @return bu
     */
    @Override
    public List<BizBaseCurrency> selectBizBaseCurrencyList(BizBaseCurrency bizBaseCurrency) {
        if (StringUtils.isNotEmpty(bizBaseCurrency.getCurrencyCode())) {
            bizBaseCurrency.setCurrencyCodeList(bizBaseCurrency.getCurrencyCode().split(","));
        }
        List<Long> longIdsList = ConvertStringToLongListUtil.splitToLongList(bizBaseCurrency.getIds());
        bizBaseCurrency.setIdsList(longIdsList);

        return bizBaseCurrencyMapper.selectBizBaseCurrencyList(bizBaseCurrency);
    }

    /**
     * 新增bu
     *
     * @param bizBaseCurrency bu
     * @return 结果
     */
    @Override
    public int insertBizBaseCurrency(BizBaseCurrency bizBaseCurrency) {
        bizBaseCurrency.setCreateTime(DateUtils.getNowDate());
        int i=  bizBaseCurrencyMapper.insertBizBaseCurrency(bizBaseCurrency);
        setCountryToRedis();
        return i;
    }

    /**
     * 修改bu
     *
     * @param bizBaseCurrency bu
     * @return 结果
     */
    @Override
    public int updateBizBaseCurrency(BizBaseCurrency bizBaseCurrency) {
        bizBaseCurrency.setUpdateTime(DateUtils.getNowDate());
         int i= bizBaseCurrencyMapper.updateBizBaseCurrency(bizBaseCurrency);
        setCountryToRedis();
        return i;
    }

    /**
     * 批量删除bu
     *
     * @param ids 需要删除的bu主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseCurrencyByIds(String[] ids) {
        int i= bizBaseCurrencyMapper.deleteBizBaseCurrencyByIds(ids);
        setCountryToRedis();
        return i;
    }

    /**
     * 删除bu信息
     *
     * @param id bu主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseCurrencyById(String id) {
       int i=   bizBaseCurrencyMapper.deleteBizBaseCurrencyById(id);
        setCountryToRedis();
        return i;
    }

    /**
     * 通过SalesOrg获取pool Currency
     *
     * @return 结果
     */
    @Override
    public List<BizBaseDropDownList> getPoolCurrencyBySalesOrg(String salesOrgCode) {
        return bizBaseCurrencyMapper.getPoolCurrencyBySalesOrg(salesOrgCode);
    }

    /**
     * 获取所有Currency
     *
     * @return 结果
     */
    @Override
    public List<BizBaseDropDownList> getAllCurrency() {
        return bizBaseCurrencyMapper.getAllCurrency();
    }

    @Override
    public BizBaseCurrencyDTO selectBizBaseCurrencyByCode(String currencyCode) {
        BizBaseCurrencyDO bizBaseCurrencyDO = bizBaseCurrencyManager.selectBizBaseCurrencyByCode(currencyCode);
        if (Objects.isNull(bizBaseCurrencyDO)) {
            return null;
        }
        BizBaseCurrencyDTO bizBaseCurrencyDTO = new BizBaseCurrencyDTO();
        BeanUtils.copyProperties(bizBaseCurrencyDO, bizBaseCurrencyDTO);
        return bizBaseCurrencyDTO;
    }

    @Override
    public List<BizBaseCurrencyDTO> selectBizBaseCurrencyByCodeList(List<String> currencyCodeList) {
        if (CollectionUtils.isEmpty(currencyCodeList)) {
            return new ArrayList<>();
        }
        List<BizBaseCurrencyDO> bizBaseCurrencyDOList = bizBaseCurrencyManager.selectBizBaseCurrencyByCodeList(currencyCodeList);
        List<BizBaseCurrencyDTO> bizBaseCurrencyDTOList = new ArrayList<>(bizBaseCurrencyDOList.size());
        for (BizBaseCurrencyDO bizBaseCurrencyDO : bizBaseCurrencyDOList) {
            BizBaseCurrencyDTO bizBaseCurrencyDTO = new BizBaseCurrencyDTO();
            BeanUtils.copyProperties(bizBaseCurrencyDO, bizBaseCurrencyDTO);
            bizBaseCurrencyDTOList.add(bizBaseCurrencyDTO);
        }
        return bizBaseCurrencyDTOList;
    }

    @Override
    public String importExcel(List<BizBaseCurrency> bizBaseCurrencies, String loginName) {
        AtomicInteger successNum = new AtomicInteger();
        AtomicInteger failureNum = new AtomicInteger();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        if (CollectionUtils.isEmpty(bizBaseCurrencies)) {
            throw new ServiceException("导入MasterData 数据不能为空！");
        }
        BizBaseCurrency currency = new BizBaseCurrency();
        for (BizBaseCurrency bizBaseCurrency : bizBaseCurrencies) {
            try {
                currency.setCurrencyCode(bizBaseCurrency.getCurrencyCode());
                List<BizBaseCustomer> buList = bizBaseCurrencyMapper.selectBaseCurrencyListCheck(currency);
                if (CollectionUtils.isNotEmpty(buList)) {
                    bizBaseCurrency.setId(String.valueOf(buList.get(0).getId()));
                    bizBaseCurrency.setUpdateTime(DateUtils.getNowDate());
                    bizBaseCurrencyMapper.updateBizBaseCurrency(bizBaseCurrency);
                    successNum.getAndIncrement();
                    continue;
                }
                bizBaseCurrency.setCreateTime(DateUtils.getNowDate());
                bizBaseCurrency.setUpdateTime(DateUtils.getNowDate());
                bizBaseCurrency.setCreateBy(loginName);
                bizBaseCurrency.setStatus("Y");
                bizBaseCurrencyMapper.insertBizBaseCurrency(bizBaseCurrency);

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

}
