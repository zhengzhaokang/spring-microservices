package com.microservices.otmp.masterdata.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.util.ConvertStringToLongListUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import com.microservices.otmp.masterdata.mapper.BizBaseEndCustomerMapper;
import com.microservices.otmp.masterdata.domain.BizBaseEndCustomer;
import com.microservices.otmp.masterdata.service.IBizBaseEndCustomerService;

/**
 * BaseEndCustomerService业务层处理
 * 
 * @author lovefamily
 * @date 2022-04-24
 */
@Service
public class BizBaseEndCustomerServiceImpl implements IBizBaseEndCustomerService
{
    @Autowired
    private BizBaseEndCustomerMapper bizBaseEndCustomerMapper;
    @Autowired
    private RedisUtils redisUtils;
    public static final String REDIS_NAME_PREFIX = "master:";

    public void setEndCustomerNameToRedis(List<BizBaseEndCustomer> bizBaseEndCustomers) {
        List<String> customerNames=  bizBaseEndCustomers.stream().map(BizBaseEndCustomer::getEndCustomerName).collect(Collectors.toList());
        redisUtils.set(REDIS_NAME_PREFIX+"end_customer_name",customerNames, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void setEndCustomerIdToRedis(List<BizBaseEndCustomer> bizBaseEndCustomers) {
        List<String> customerIds=  bizBaseEndCustomers.stream().map(BizBaseEndCustomer::getEndCustomerId).collect(Collectors.toList());
        redisUtils.set(REDIS_NAME_PREFIX+"end_customer_id",customerIds, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }




    /**
     * 查询BaseEndCustomer
     * 
     * @param id BaseEndCustomer主键
     * @return BaseEndCustomer
     */
    @Override
    public BizBaseEndCustomer selectBizBaseEndCustomerById(Long id)
    {
        return bizBaseEndCustomerMapper.selectBizBaseEndCustomerById(id);
    }

    /**
     * 查询BaseEndCustomer列表
     * 
     * @param bizBaseEndCustomer BaseEndCustomer
     * @return BaseEndCustomer
     */
    @Override
    public List<BizBaseEndCustomer> selectBizBaseEndCustomerList(BizBaseEndCustomer bizBaseEndCustomer)
    {
        List<Long> longIdsList = ConvertStringToLongListUtil.splitToLongList(bizBaseEndCustomer.getIds());
        bizBaseEndCustomer.setIdsList(longIdsList);
        return bizBaseEndCustomerMapper.selectBizBaseEndCustomerList(bizBaseEndCustomer);
    }

    /**
     * 新增BaseEndCustomer
     * 
     * @param bizBaseEndCustomer BaseEndCustomer
     * @return 结果
     */
    @Override
    public int insertBizBaseEndCustomer(BizBaseEndCustomer bizBaseEndCustomer)
    {
        bizBaseEndCustomer.setCreateTime(DateUtils.getNowDate());
        int  i= bizBaseEndCustomerMapper.insertBizBaseEndCustomer(bizBaseEndCustomer);
        List<BizBaseEndCustomer> bizBaseEndCustomers = this.selectBizBaseEndCustomerList(new BizBaseEndCustomer());
        setEndCustomerIdToRedis(bizBaseEndCustomers);
        setEndCustomerNameToRedis(bizBaseEndCustomers);
        return  i;
    }

    /**
     * 修改BaseEndCustomer
     * 
     * @param bizBaseEndCustomer BaseEndCustomer
     * @return 结果
     */
    @Override
    public int updateBizBaseEndCustomer(BizBaseEndCustomer bizBaseEndCustomer)
    {
        bizBaseEndCustomer.setUpdateTime(DateUtils.getNowDate());
        int  i= bizBaseEndCustomerMapper.updateBizBaseEndCustomer(bizBaseEndCustomer);
        List<BizBaseEndCustomer> bizBaseEndCustomers = this.selectBizBaseEndCustomerList(new BizBaseEndCustomer());
        setEndCustomerIdToRedis(bizBaseEndCustomers);
        setEndCustomerNameToRedis(bizBaseEndCustomers);
        return  i;
    }

    /**
     * 批量删除BaseEndCustomer
     * 
     * @param ids 需要删除的BaseEndCustomer主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseEndCustomerByIds(Long[] ids)
    {
        int  i= bizBaseEndCustomerMapper.updateBizBaseEndCustomerByIds(ids);
        List<BizBaseEndCustomer> bizBaseEndCustomers = this.selectBizBaseEndCustomerList(new BizBaseEndCustomer());
        setEndCustomerIdToRedis(bizBaseEndCustomers);
        setEndCustomerNameToRedis(bizBaseEndCustomers);
        return i;
    }

    /**
     * 删除BaseEndCustomer信息
     * 
     * @param id BaseEndCustomer主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseEndCustomerById(Long id)
    {
        int  i= bizBaseEndCustomerMapper.deleteBizBaseEndCustomerById(id);
        List<BizBaseEndCustomer> bizBaseEndCustomers = this.selectBizBaseEndCustomerList(new BizBaseEndCustomer());
        setEndCustomerIdToRedis(bizBaseEndCustomers);
        setEndCustomerNameToRedis(bizBaseEndCustomers);
        return  i;
    }

    @Override
    public String importExcel(List<BizBaseEndCustomer> bizs, String loginName) {
        AtomicInteger successNum = new AtomicInteger();
        AtomicInteger failureNum = new AtomicInteger();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        if (CollectionUtils.isEmpty(bizs)) {
            throw new ServiceException("导入MasterData 数据不能为空！");
        }
        for (BizBaseEndCustomer bizBaseEndCustomer : bizs) {
            try {
                List<BizBaseEndCustomer> buList = bizBaseEndCustomerMapper.selectBizBaseEndCustomerListCheck(bizBaseEndCustomer);
                if (CollectionUtils.isNotEmpty(buList)) {
                    bizBaseEndCustomer.setId(buList.get(0).getId());
                    bizBaseEndCustomer.setUpdateTime(DateUtils.getNowDate());
                    bizBaseEndCustomerMapper.updateBizBaseEndCustomer(bizBaseEndCustomer);
                    successNum.getAndIncrement();
                    continue;
                }
                bizBaseEndCustomer.setCreateTime(DateUtils.getNowDate());
                bizBaseEndCustomer.setUpdateTime(DateUtils.getNowDate());
                bizBaseEndCustomer.setCreateBy(loginName);
                bizBaseEndCustomer.setStatus("Y");
                bizBaseEndCustomerMapper.insertBizBaseEndCustomer(bizBaseEndCustomer);

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
    public List<BizBaseEndCustomer> getDropDownList(BizBaseEndCustomer bizBaseEndCustomer) {
        return  bizBaseEndCustomerMapper.getDropDownList(bizBaseEndCustomer);

    }

    /**
     * 清除 spring cache 缓存
     * @param id
     * @return
     */
    @CacheEvict(value = "bizBaseEndCustomer", key = "#result.endCustomerId")
    @Override
    public BizBaseEndCustomer removeCache(Long id) {
        return  bizBaseEndCustomerMapper.selectBizBaseEndCustomerById(id);
    }
}
