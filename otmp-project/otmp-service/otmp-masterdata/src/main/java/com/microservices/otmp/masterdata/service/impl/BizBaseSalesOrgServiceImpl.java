package com.microservices.otmp.masterdata.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.util.ConvertStringToLongListUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.masterdata.mapper.BizBaseSalesOrgMapper;
import com.microservices.otmp.masterdata.domain.BizBaseSalesOrg;
import com.microservices.otmp.masterdata.service.IBizBaseSalesOrgService;

/**
 * BizBaseSalesOrgService业务层处理
 * 
 * @author lovefamily
 * @date 2022-04-22
 */
@Service
public class BizBaseSalesOrgServiceImpl implements IBizBaseSalesOrgService
{
    @Autowired
    private BizBaseSalesOrgMapper bizBaseSalesOrgMapper;

    @Autowired
    private RedisUtils redisUtils;
    public static final String REDIS_NAME_PREFIX = "master:";

    public void setSalesOrgToRedis() {
        List<BizBaseSalesOrg> bizBaseSalesOrgs = this.selectBizBaseSalesOrgList(new BizBaseSalesOrg());
        redisUtils.set(REDIS_NAME_PREFIX+"sales_org_code",bizBaseSalesOrgs, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    /**
     * 查询BizBaseSalesOrg
     * 
     * @param id BizBaseSalesOrg主键
     * @return BizBaseSalesOrg
     */
    @Override
    public BizBaseSalesOrg selectBizBaseSalesOrgById(Long id)
    {
        return bizBaseSalesOrgMapper.selectBizBaseSalesOrgById(id);
    }

    /**
     * 查询下拉框
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseSalesOrg集合
     */
    @Override
    public List<BizBaseSalesOrg> getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition)
    {
        List<BizBaseSalesOrg> list = bizBaseSalesOrgMapper.getDropDownList(bizBaseDropDownCondition);
        //根据需要的下拉框label去重
        if(CommonUtils.stringIsNotEmpty(bizBaseDropDownCondition.getTempField())){
            switch (bizBaseDropDownCondition.getTempField()){
                //两个filter 尝试 合并
                case "companyCode":         list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getCompanyCode())).filter(CommonUtils.distinctByKey(BizBaseSalesOrg::getCompanyCode)).collect(Collectors.toList());break;
                case "localCurrency":       list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getLocalCurrencyCode())).filter(CommonUtils.distinctByKey(BizBaseSalesOrg::getLocalCurrencyCode)).collect(Collectors.toList());break;
                case "accrualCompanyCode":  list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getAccrualCompanyCode())).filter(CommonUtils.distinctByKey(BizBaseSalesOrg::getAccrualCompanyCode)).collect(Collectors.toList());break;
                default:                    list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getSalesOrgCode())).filter(CommonUtils.distinctByKey(BizBaseSalesOrg::getSalesOrgCode)).collect(Collectors.toList());
            }
        }else{
            list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getSalesOrgCode())).filter(CommonUtils.distinctByKey(BizBaseSalesOrg::getSalesOrgCode)).collect(Collectors.toList());
        }
        return  list;
    }

    @Override
    public BizBaseSalesOrg getOne(BizBaseDropDownCondition bizBaseDropDownCondition) {

        List<BizBaseSalesOrg> list = bizBaseSalesOrgMapper.getDropDownList(bizBaseDropDownCondition);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 查询BizBaseSalesOrg列表
     * 
     * @param bizBaseSalesOrg BizBaseSalesOrg
     * @return BizBaseSalesOrg
     */
    @Override
    public List<BizBaseSalesOrg> selectBizBaseSalesOrgList(BizBaseSalesOrg bizBaseSalesOrg)
    {
        List<Long> longIdsList = ConvertStringToLongListUtil.splitToLongList(bizBaseSalesOrg.getIds());
        bizBaseSalesOrg.setIdsList(longIdsList);
        return bizBaseSalesOrgMapper.selectBizBaseSalesOrgList(bizBaseSalesOrg);
    }

    /**
     * 新增BizBaseSalesOrg
     * 
     * @param bizBaseSalesOrg BizBaseSalesOrg
     * @return 结果
     */
    @Override
    public int insertBizBaseSalesOrg(BizBaseSalesOrg bizBaseSalesOrg)
    {
        bizBaseSalesOrg.setCreateTime(DateUtils.getNowDate());
        int i= bizBaseSalesOrgMapper.insertBizBaseSalesOrg(bizBaseSalesOrg);
        setSalesOrgToRedis();
        return i;
    }

    /**
     * 修改BizBaseSalesOrg
     * 
     * @param bizBaseSalesOrg BizBaseSalesOrg
     * @return 结果
     */
    @Override
    public int updateBizBaseSalesOrg(BizBaseSalesOrg bizBaseSalesOrg)
    {
        bizBaseSalesOrg.setUpdateTime(DateUtils.getNowDate());
        int i= bizBaseSalesOrgMapper.updateBizBaseSalesOrg(bizBaseSalesOrg);
        setSalesOrgToRedis();
        return i;
    }

    /**
     * 批量删除BizBaseSalesOrg
     * 
     * @param ids 需要删除的BizBaseSalesOrg主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseSalesOrgByIds(Long[] ids)
    {
        int i= bizBaseSalesOrgMapper.updateBizBaseSalesOrgByIds(ids);
        setSalesOrgToRedis();
        return i;
    }

    /**
     * 删除BizBaseSalesOrg信息
     * 
     * @param id BizBaseSalesOrg主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseSalesOrgById(Long id)
    {
        int i= bizBaseSalesOrgMapper.deleteBizBaseSalesOrgById(id);
        setSalesOrgToRedis();
        return i;
    }

    @Override
    public String importExcel(List<BizBaseSalesOrg> bizs, String loginName) {
        AtomicInteger successNum = new AtomicInteger();
        AtomicInteger failureNum = new AtomicInteger();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        if (CollectionUtils.isEmpty(bizs)) {
            throw new ServiceException("导入MasterData 数据不能为空！");
        }
        for (BizBaseSalesOrg bizBaseSalesOrg : bizs) {
            try {

                List<BizBaseSalesOrg> buList = bizBaseSalesOrgMapper.selectBizBaseSalesOrgListCheck(bizBaseSalesOrg);
                if (CollectionUtils.isNotEmpty(buList)) {
                    bizBaseSalesOrg.setId(buList.get(0).getId());
                    bizBaseSalesOrg.setUpdateTime(DateUtils.getNowDate());
                    bizBaseSalesOrgMapper.updateBizBaseSalesOrg(bizBaseSalesOrg);
                    successNum.getAndIncrement();
                    continue;
                }
                bizBaseSalesOrg.setCreateTime(DateUtils.getNowDate());
                bizBaseSalesOrg.setUpdateTime(DateUtils.getNowDate());
                bizBaseSalesOrg.setCreateBy(loginName);
                bizBaseSalesOrg.setStatus("Y");
                bizBaseSalesOrgMapper.insertBizBaseSalesOrg(bizBaseSalesOrg);

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
    public List<BizBaseSalesOrg> localCurrencyList() {
        return bizBaseSalesOrgMapper.localCurrencyList();
    }
}
