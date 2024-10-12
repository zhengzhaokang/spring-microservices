package com.microservices.otmp.masterdata.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.masterdata.mapper.BizBaseBpTypeCustomerMapper;
import com.microservices.otmp.masterdata.domain.BizBaseBpTypeCustomer;
import com.microservices.otmp.masterdata.service.IBizBaseBpTypeCustomerService;

/**
 * BaseBpTypeCustomerService业务层处理
 * 
 * @author lovefamily
 * @date 2022-06-24
 */
@Service
public class BizBaseBpTypeCustomerServiceImpl implements IBizBaseBpTypeCustomerService
{
    @Autowired
    private BizBaseBpTypeCustomerMapper bizBaseBpTypeCustomerMapper;
    @Autowired
    private RedisUtils redisUtils;
    public static final String REDIS_NAME_PREFIX = "master:";
    public void setPartnerTypeToRedis() {
        List<String> bpTypeCustomers = this.selectBizBaseBpTypeCustomerList(new BizBaseBpTypeCustomer()).stream().map(BizBaseBpTypeCustomer::getCustomerNumber).collect(Collectors.toList());
        redisUtils.set(REDIS_NAME_PREFIX+"partner_type",bpTypeCustomers, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }
    /**
     * 查询BaseBpTypeCustomer
     * 
     * @param id BaseBpTypeCustomer主键
     * @return BaseBpTypeCustomer
     */
    @Override
    public BizBaseBpTypeCustomer selectBizBaseBpTypeCustomerById(Long id)
    {
        return bizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerById(id);
    }

    /**
     * 查询BaseBpTypeCustomer列表
     * 
     * @param bizBaseBpTypeCustomer BaseBpTypeCustomer
     * @return BaseBpTypeCustomer
     */
    @Override
    public List<BizBaseBpTypeCustomer> selectBizBaseBpTypeCustomerList(BizBaseBpTypeCustomer bizBaseBpTypeCustomer)
    {
        return bizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(bizBaseBpTypeCustomer);
    }

    /**
     * 新增BaseBpTypeCustomer
     * 
     * @param bizBaseBpTypeCustomer BaseBpTypeCustomer
     * @return 结果
     */
    @Override
    public int insertBizBaseBpTypeCustomer(BizBaseBpTypeCustomer bizBaseBpTypeCustomer)
    {
        bizBaseBpTypeCustomer.setCreateTime(DateUtils.getNowDate());
        int  i= bizBaseBpTypeCustomerMapper.insertBizBaseBpTypeCustomer(bizBaseBpTypeCustomer);
        setPartnerTypeToRedis();
        return  i;
    }

    /**
     * 修改BaseBpTypeCustomer
     * 
     * @param bizBaseBpTypeCustomer BaseBpTypeCustomer
     * @return 结果
     */
    @Override
    public int updateBizBaseBpTypeCustomer(BizBaseBpTypeCustomer bizBaseBpTypeCustomer)
    {
        bizBaseBpTypeCustomer.setUpdateTime(DateUtils.getNowDate());
        int  i= bizBaseBpTypeCustomerMapper.updateBizBaseBpTypeCustomer(bizBaseBpTypeCustomer);
        setPartnerTypeToRedis();
        return  i;
    }

    /**
     * 批量删除BaseBpTypeCustomer
     * 
     * @param ids 需要删除的BaseBpTypeCustomer主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseBpTypeCustomerByIds(Long[] ids)
    {
        int  i= bizBaseBpTypeCustomerMapper.deleteBizBaseBpTypeCustomerByIds(ids);
        setPartnerTypeToRedis();
        return  i;
    }

    /**
     * 删除BaseBpTypeCustomer信息
     * 
     * @param id BaseBpTypeCustomer主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseBpTypeCustomerById(Long id)
    {
        int  i= bizBaseBpTypeCustomerMapper.deleteBizBaseBpTypeCustomerById(id);
        setPartnerTypeToRedis();
        return  i;
    }

    @Override
    public String importExcel(List<BizBaseBpTypeCustomer> bizs, String loginName) {
        return null;
    }

    @Override
    public List<BizBaseBpTypeCustomer> selectBizBaseBpTypeCustomerListDistinctBpType(BizBaseBpTypeCustomer bizBaseBpTypeCustomer) {
        List<BizBaseBpTypeCustomer> bizBaseBpTypeCustomers = bizBaseBpTypeCustomerMapper.selectBizBaseBpTypeCustomerList(bizBaseBpTypeCustomer);
        return new ArrayList<>(bizBaseBpTypeCustomers.stream()
                .collect(Collectors.toMap(BizBaseBpTypeCustomer::getBpType, Function.identity(), (oldValue, newValue) -> oldValue))
                .values());
    }
}
