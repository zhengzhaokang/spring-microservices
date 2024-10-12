package com.microservices.otmp.masterdata.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseSegment;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.masterdata.mapper.BizBaseTerritoryRelationMapper;
import com.microservices.otmp.masterdata.domain.BizBaseTerritoryRelation;
import com.microservices.otmp.masterdata.service.IBizBaseTerritoryRelationService;

/**
 * BaseTerritoryRelationService业务层处理
 *
 * @author lovefamily
 * @date 2022-04-26
 */
@Service
public class BizBaseTerritoryRelationServiceImpl implements IBizBaseTerritoryRelationService
{
    @Autowired
    private BizBaseTerritoryRelationMapper bizBaseTerritoryRelationMapper;
    @Autowired
    private RedisUtils redisUtils;
    public static final String REDIS_NAME_PREFIX = "master:";

    public void setTerritoryToRedis() {
        List<BizBaseTerritoryRelation> bizBaseTerritoryRelations = this.selectBizBaseTerritoryRelationList(new BizBaseTerritoryRelation());
        redisUtils.set(REDIS_NAME_PREFIX+"territory",bizBaseTerritoryRelations, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    /**
     * 查询BaseTerritoryRelation
     *
     * @param id BaseTerritoryRelation主键
     * @return BaseTerritoryRelation
     */
    @Override
    public BizBaseTerritoryRelation selectBizBaseTerritoryRelationById(Long id)
    {
        return bizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationById(id);
    }

    /**
     * 查询BaseTerritoryRelation列表
     *
     * @param bizBaseTerritoryRelation BaseTerritoryRelation
     * @return BaseTerritoryRelation
     */
    @Override
    public List<BizBaseTerritoryRelation> selectBizBaseTerritoryRelationList(BizBaseTerritoryRelation bizBaseTerritoryRelation)
    {
        return bizBaseTerritoryRelationMapper.selectBizBaseTerritoryRelationList(bizBaseTerritoryRelation);
    }

    /**
     * 查询下拉框
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseTerritoryRelation集合
     */
    @Override
    public List<BizBaseTerritoryRelation> getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition)
    {
        return bizBaseTerritoryRelationMapper.getDropDownList(bizBaseDropDownCondition);
    }

    /**
     * 新增BaseTerritoryRelation
     *
     * @param bizBaseTerritoryRelation BaseTerritoryRelation
     * @return 结果
     */
    @Override
    public int insertBizBaseTerritoryRelation(BizBaseTerritoryRelation bizBaseTerritoryRelation)
    {
        bizBaseTerritoryRelation.setCreateTime(DateUtils.getNowDate());
        int i= bizBaseTerritoryRelationMapper.insertBizBaseTerritoryRelation(bizBaseTerritoryRelation);
        setTerritoryToRedis();
        return i;
    }

    /**
     * 修改BaseTerritoryRelation
     *
     * @param bizBaseTerritoryRelation BaseTerritoryRelation
     * @return 结果
     */
    @Override
    public int updateBizBaseTerritoryRelation(BizBaseTerritoryRelation bizBaseTerritoryRelation)
    {
        bizBaseTerritoryRelation.setUpdateTime(DateUtils.getNowDate());
        int i= bizBaseTerritoryRelationMapper.updateBizBaseTerritoryRelation(bizBaseTerritoryRelation);
        setTerritoryToRedis();
        return i;
    }

    /**
     * 批量删除BaseTerritoryRelation
     *
     * @param ids 需要删除的BaseTerritoryRelation主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseTerritoryRelationByIds(Long[] ids)
    {
        int i= bizBaseTerritoryRelationMapper.deleteBizBaseTerritoryRelationByIds(ids);
        setTerritoryToRedis();
        return i;
    }

    /**
     * 删除BaseTerritoryRelation信息
     *
     * @param id BaseTerritoryRelation主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseTerritoryRelationById(Long id)
    {
        int i= bizBaseTerritoryRelationMapper.deleteBizBaseTerritoryRelationById(id);
        setTerritoryToRedis();
        return i;
    }

    @Override
    public String importExcel(List<BizBaseTerritoryRelation> bizs, String loginName) {

        AtomicInteger successNum = new AtomicInteger();
        AtomicInteger failureNum = new AtomicInteger();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        if (CollectionUtils.isEmpty(bizs)) {
            throw new ServiceException("导入MasterData 数据不能为空！");
        }
        for (BizBaseTerritoryRelation bizBaseTerritoryRelation : bizs) {
            try {
                List<BizBaseSegment> buList = bizBaseTerritoryRelationMapper.selectbizBaseTerritoryRelationListCheck(bizBaseTerritoryRelation);
                if (CollectionUtils.isNotEmpty(buList)) {
                    bizBaseTerritoryRelation.setId(buList.get(0).getId());
                    bizBaseTerritoryRelation.setUpdateTime(new Date());
                    bizBaseTerritoryRelationMapper.updateBizBaseTerritoryRelation(bizBaseTerritoryRelation);
                    successNum.getAndIncrement();
                    continue;
                }
                bizBaseTerritoryRelationMapper.insertBizBaseTerritoryRelation(bizBaseTerritoryRelation);

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
