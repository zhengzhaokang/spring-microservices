package com.microservices.otmp.masterdata.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.masterdata.domain.BizBaseCustomer;
import com.microservices.otmp.masterdata.mapper.BizBaseCustomerMapper;
import com.microservices.otmp.masterdata.service.IBizBaseCustomerService;
import com.microservices.otmp.masterdata.util.ConvertStringToLongListUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.annotation.Resource;

/**
 * BaseCustomerService业务层处理
 * 
 * @author lovefamily
 * @date 2022-04-24
 */
@Service
public class BizBaseCustomerServiceImpl implements IBizBaseCustomerService
{
    @Autowired
    private BizBaseCustomerMapper bizBaseCustomerMapper;
    @Resource(name = "threadPoolTaskExecutor")
    ThreadPoolTaskExecutor executor;
    @Autowired
    private RedisUtils redisUtils;
    public static final String REDIS_NAME_PREFIX = "master:";
    public static final String SOLD_TO = "sold_to";
    public static final String PAYER = "payer";

    public void setSoldToOrPayerToRedis(List<BizBaseCustomer> bizBaseCustomers, String s) {
        List<String> partyList = bizBaseCustomers.stream().map(BizBaseCustomer::getCustomerNumber).collect(Collectors.toList());
        redisUtils.set(s, partyList, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void addSoldToOrPayerToRedis(String customerName, String s) {
        redisUtils.addList(RedisConstants.REDIS_EXPIRE_TIME_FOREVER, s, customerName);
    }
    public void updateSoldToOrPayerToRedis(String customerNameOld, String customerNameNew, String s) {
        List<String> list = JSONObject.parseArray(redisUtils.get(s), String.class);
        HashSet<String> set = new HashSet<>();
        set.addAll(JSONObject.parseArray(redisUtils.get(s), String.class));
        set.remove(customerNameOld);
        set.add(customerNameNew);
        redisUtils.set(s, set, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    /**
     * 查询BaseCustomer
     * 
     * @param id BaseCustomer主键
     * @return BaseCustomer
     */
    @Override
    public BizBaseCustomer selectBizBaseCustomerById(Long id)
    {
        return bizBaseCustomerMapper.selectBizBaseCustomerById(id);
    }

    /**
     * 查询BaseCustomer列表
     * 
     * @param bizBaseCustomer BaseCustomer
     * @return BaseCustomer
     */
    @Override
    public List<BizBaseCustomer> selectBizBaseCustomerList(BizBaseCustomer bizBaseCustomer)
    {
        List<Long> longIdsList = ConvertStringToLongListUtil.splitToLongList(bizBaseCustomer.getIds());
        bizBaseCustomer.setIdsList(longIdsList);
        List<BizBaseCustomer> bizBaseCustomers = bizBaseCustomerMapper.selectBizBaseCustomerList(bizBaseCustomer);
        return bizBaseCustomers;
    }

    /**
     * 新增BaseCustomer
     * 
     * @param bizBaseCustomer BaseCustomer
     * @return 结果
     */
    @Override
    public int insertBizBaseCustomer(BizBaseCustomer bizBaseCustomer)
    {
        bizBaseCustomer.setCreateTime(DateUtils.getNowDate());
        int  i= bizBaseCustomerMapper.insertBizBaseCustomer(bizBaseCustomer);
        List<BizBaseCustomer> bizBaseCustomers = this.selectBizBaseCustomerList(new BizBaseCustomer());
        setSoldToOrPayerToRedis(bizBaseCustomers, REDIS_NAME_PREFIX + SOLD_TO);
        setSoldToOrPayerToRedis(bizBaseCustomers, REDIS_NAME_PREFIX + PAYER);
        return  i;
    }

    /**
     * ecc kafka 新增BaseCustomer
     *
     * @param bizBaseCustomer BaseCustomer
     * @return 结果
     */
    @Override
    public int insertBizBaseCustomerByEcc(BizBaseCustomer bizBaseCustomer)
    {
        bizBaseCustomer.setCreateTime(DateUtils.getNowDate());
        int  i= bizBaseCustomerMapper.insertBizBaseCustomer(bizBaseCustomer);
        addSoldToOrPayerToRedis(bizBaseCustomer.getCustomerName(), REDIS_NAME_PREFIX + SOLD_TO);
        addSoldToOrPayerToRedis(bizBaseCustomer.getCustomerName(), REDIS_NAME_PREFIX + PAYER);
        return  i;
    }

    /**
     * 修改BaseCustomer
     * 
     * @param bizBaseCustomer BaseCustomer
     * @return 结果
     */
    @CacheEvict(value = "bizBaseCustomer", key = "#bizBaseCustomer.id")
    @Override
    public int updateBizBaseCustomer(BizBaseCustomer bizBaseCustomer)
    {
        if(bizBaseCustomer.getTierType().equals("2")){
            throw  new OtmpException("");
        }
        bizBaseCustomer.setUpdateTime(DateUtils.getNowDate());
        int  i= bizBaseCustomerMapper.updateBizBaseCustomer(bizBaseCustomer);
        List<BizBaseCustomer> bizBaseCustomers = this.selectBizBaseCustomerList(new BizBaseCustomer());
        setSoldToOrPayerToRedis(bizBaseCustomers, REDIS_NAME_PREFIX + SOLD_TO);
        setSoldToOrPayerToRedis(bizBaseCustomers, REDIS_NAME_PREFIX + PAYER);
        return  i;
    }

    /**
     * ecc kafka 修改BaseCustomer
     *
     * @param bizBaseCustomer BaseCustomer
     * @return 结果
     */
    @CacheEvict(value = "bizBaseCustomer", key = "#bizBaseCustomer.id")
    @Override
    public int updateBizBaseCustomerByEcc(BizBaseCustomer bizBaseCustomer)
    {
        if(bizBaseCustomer.getTierType().equals("2")){
            throw  new OtmpException("");
        }
        BizBaseCustomer customer = bizBaseCustomerMapper.selectBizBaseCustomerById(bizBaseCustomer.getId());
        bizBaseCustomer.setUpdateTime(DateUtils.getNowDate());
        int  i= bizBaseCustomerMapper.updateBizBaseCustomer(bizBaseCustomer);
        if (!Objects.equals(customer.getCustomerName(), bizBaseCustomer.getCustomerName())) {
            //若customerName不相同,需要更新redis,因无法确定删除redis中的那个name,所以要更新所有数据
            CompletableFuture.runAsync(() ->{
                List<BizBaseCustomer> bizBaseCustomers = this.selectBizBaseCustomerList(new BizBaseCustomer());
                setSoldToOrPayerToRedis(bizBaseCustomers, REDIS_NAME_PREFIX + SOLD_TO);
                setSoldToOrPayerToRedis(bizBaseCustomers, REDIS_NAME_PREFIX + PAYER);
            }, executor);
        }
        return  i;
    }

    /**
     * 批量删除BaseCustomer
     * 
     * @param ids 需要删除的BaseCustomer主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseCustomerByIds(Long[] ids)
    {
        int  i= bizBaseCustomerMapper.updateBizBaseCustomerByIds(ids);
        List<BizBaseCustomer> bizBaseCustomers = this.selectBizBaseCustomerList(new BizBaseCustomer());
        setSoldToOrPayerToRedis(bizBaseCustomers, REDIS_NAME_PREFIX + SOLD_TO);
        setSoldToOrPayerToRedis(bizBaseCustomers, REDIS_NAME_PREFIX + PAYER);
        return  i;
    }

    /**
     * 删除BaseCustomer信息
     * 
     * @param id BaseCustomer主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseCustomerById(Long id)
    {
        int  i= bizBaseCustomerMapper.deleteBizBaseCustomerById(id);
        List<BizBaseCustomer> bizBaseCustomers = this.selectBizBaseCustomerList(new BizBaseCustomer());
        setSoldToOrPayerToRedis(bizBaseCustomers, REDIS_NAME_PREFIX + SOLD_TO);
        setSoldToOrPayerToRedis(bizBaseCustomers, REDIS_NAME_PREFIX + PAYER);
        return  i;
    }

    @Override
    public String importExcel(List<BizBaseCustomer> bizs, String loginName) {

        AtomicInteger successNum = new AtomicInteger();
        AtomicInteger failureNum = new AtomicInteger();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        if (CollectionUtils.isEmpty(bizs)) {
            throw new ServiceException("导入MasterData 数据不能为空！");
        }
        for (BizBaseCustomer bizBaseCustomer : bizs) {
            try {
                List<BizBaseCustomer> buList = bizBaseCustomerMapper.selectBizBaseCustomerListCheck(bizBaseCustomer);
                if (CollectionUtils.isNotEmpty(buList)) {
                    bizBaseCustomer.setId(buList.get(0).getId());
                    bizBaseCustomer.setUpdateTime(DateUtils.getNowDate());
                    bizBaseCustomerMapper.updateBizBaseCustomer(bizBaseCustomer);
                    successNum.getAndIncrement();
                    continue;
                }
                bizBaseCustomer.setCreateTime(DateUtils.getNowDate());
                bizBaseCustomer.setUpdateTime(DateUtils.getNowDate());
                bizBaseCustomer.setCreateBy(loginName);
                bizBaseCustomer.setStatus("Y");
                bizBaseCustomerMapper.insertBizBaseCustomer(bizBaseCustomer);

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

    @Override
    public List<BizBaseCustomer> getDropDownList(BizBaseCustomer bizBaseCustomer) {
        return  bizBaseCustomerMapper.getDropDownList(bizBaseCustomer);
    }

    @Override
    public BizBaseCustomer getCustomerInfo(BizBaseCustomer bizBaseCustomer) {

        List<BizBaseCustomer> bizBaseCustomers = bizBaseCustomerMapper.selectBizBaseCustomerList(bizBaseCustomer);
        if (1 != bizBaseCustomers.size()){
            throw new OtmpException("Invalid information");
        }
        return bizBaseCustomers.get(0);
    }

    @Override
    public BizBaseCustomer getCustomer(BizBaseCustomer bizBaseCustomer) {

        List<BizBaseCustomer> bizBaseCustomers = bizBaseCustomerMapper.selectBizBaseCustomerList(bizBaseCustomer);
        if (null == bizBaseCustomers || bizBaseCustomers.size() <= 0) {
            return null;
        }
        return bizBaseCustomers.get(0);
    }

    /**
     * 清除 spring cache 缓存
     * @param id
     * @return
     */
    @CacheEvict(value = "bizBaseCustomer", key = "#result.customerNumber")
    @Override
    public BizBaseCustomer removeCache(Long id) {
        return bizBaseCustomerMapper.selectBizBaseCustomerById(id);
    }
}
