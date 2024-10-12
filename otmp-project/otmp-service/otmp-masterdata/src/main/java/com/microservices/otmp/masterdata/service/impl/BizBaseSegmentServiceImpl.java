package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseSegment;
import com.microservices.otmp.masterdata.domain.entity.dto.ToMsSegmentDTO;
import com.microservices.otmp.masterdata.domain.entity.vo.MsSegmentVo;
import com.microservices.otmp.masterdata.manager.BizBaseSegmentManager;
import com.microservices.otmp.masterdata.mapper.BizBaseSegmentMapper;
import com.microservices.otmp.masterdata.service.IBizBaseSegmentService;
import com.microservices.otmp.masterdata.util.ConvertStringToLongListUtil;
import com.microservices.otmp.system.feign.RemoteDictService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * BaseSegmentService业务层处理
 *
 * @author lovefamily
 * @date 2022-04-24
 */
@Service
public class BizBaseSegmentServiceImpl implements IBizBaseSegmentService
{
    @Autowired
    private BizBaseSegmentMapper bizBaseSegmentMapper;

    @Autowired
    private RemoteDictService remoteDictService;
    @Autowired
    private BizBaseSegmentManager bizBaseSegmentManager;
    @Autowired
    RedisUtils redisUtils;

    public static final String REDIS_NAME_PREFIX = "master:";

    public void setSegmentToRedis() {
        List<BizBaseSegment> bizBaseSegments = this.selectBizBaseSegmentList(new BizBaseSegment());
        redisUtils.set(REDIS_NAME_PREFIX + "biz_segments", bizBaseSegments, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    /**
     * 查询BaseSegment
     *
     * @param id BaseSegment主键
     * @return BaseSegment
     */
    @Override
    public BizBaseSegment selectBizBaseSegmentById(Long id)
    {
        return bizBaseSegmentMapper.selectBizBaseSegmentById(id);
    }

    /**
     * 查询下拉框
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseSegment集合
     */
    @Override
    public List<BizBaseSegment> getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition)
    {
        CommonUtils.stringIsEmpty(bizBaseDropDownCondition.getTempField(),"tempField must be 1 or 2 or 3!");

        return bizBaseSegmentMapper.getDropDownList(bizBaseDropDownCondition);

    }
    /**
     * 查询BaseSegment列表
     *
     * @param bizBaseSegment BaseSegment
     * @return BaseSegment
     */
    @Override
    public List<BizBaseSegment> selectBizBaseSegmentList(BizBaseSegment bizBaseSegment)
    {

        List<Long> longIdsList = ConvertStringToLongListUtil.splitToLongList(bizBaseSegment.getIds());
        bizBaseSegment.setIdsList(longIdsList);
        return bizBaseSegmentMapper.selectBizBaseSegmentList(bizBaseSegment);
    }

    /**
     * 查询BaseSegment列表
     *
     * @param bizBaseSegment BaseSegment
     * @return BaseSegment
     */
    @Override
    public List<BizBaseSegment> getGpnCodeList(BizBaseSegment bizBaseSegment)
    {
        return bizBaseSegmentMapper.getGpnCodeList(bizBaseSegment);
    }

    /**
     * 新增BaseSegment
     *
     * @param bizBaseSegment BaseSegment
     * @return 结果
     */
    @Override
    public int insertBizBaseSegment(BizBaseSegment bizBaseSegment)
    {
        bizBaseSegment.setCreateTime(DateUtils.getNowDate());
        return bizBaseSegmentMapper.insertBizBaseSegment(bizBaseSegment);
    }

    /**
     * 修改BaseSegment
     *
     * @param bizBaseSegment BaseSegment
     * @return 结果
     */
    @Override
    @CacheEvict(value = "bizBaseSegment", allEntries = true)
    public int updateBizBaseSegment(BizBaseSegment bizBaseSegment)
    {
        bizBaseSegment.setUpdateTime(DateUtils.getNowDate());
        return bizBaseSegmentMapper.updateBizBaseSegment(bizBaseSegment);
    }

    /**
     * 批量删除BaseSegment
     *
     * @param ids 需要删除的BaseSegment主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseSegmentByIds(Long[] ids)
    {
        return bizBaseSegmentMapper.updateBizBaseSegmentByIds(ids);
    }

    /**
     * 删除BaseSegment信息
     *
     * @param id BaseSegment主键
     * @return 结果
     */
    @Override
    @CacheEvict(value = "bizBaseSegment", allEntries = true)
    public int deleteBizBaseSegmentById(Long id)
    {
        return bizBaseSegmentMapper.deleteBizBaseSegmentById(id);
    }

    @Override
    public String importExcel(List<BizBaseSegment> bizs, String loginName) {
        AtomicInteger successNum = new AtomicInteger();
        AtomicInteger failureNum = new AtomicInteger();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        if (CollectionUtils.isEmpty(bizs)) {
            throw new ServiceException("导入MasterData 数据不能为空！");
        }
        for (BizBaseSegment bizBaseSegment : bizs) {
            try {
                List<BizBaseSegment> buList = bizBaseSegmentMapper.selectBizBaseSegmentListCheck(bizBaseSegment);
                if (CollectionUtils.isNotEmpty(buList)) {
                    bizBaseSegment.setId(buList.get(0).getId());
                    bizBaseSegment.setUpdateTime(DateUtils.getNowDate());
                    bizBaseSegmentMapper.updateBizBaseSegment(bizBaseSegment);
                    successNum.getAndIncrement();
                    continue;
                }
                bizBaseSegment.setCreateTime(DateUtils.getNowDate());
                bizBaseSegment.setUpdateTime(DateUtils.getNowDate());
                bizBaseSegment.setCreateBy(loginName);
                bizBaseSegment.setStatus("Y");
                bizBaseSegmentMapper.insertBizBaseSegment(bizBaseSegment);

                successNum.getAndIncrement();
            } catch (Exception e) {
                failureNum.getAndIncrement();
                String msg = "<br/>" + failureNum + "MasterData导入失败：";
                failureMsg.append(msg).append(e.getMessage());
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
    public List<MsSegmentVo> toMsSegmentList(ToMsSegmentDTO toMsSegmentDTO) {
        return bizBaseSegmentManager.toMsSegmentList(toMsSegmentDTO);
    }

    @Override
    @Cacheable(value = "bizBaseSegment", key = "#businessGroup + ':' + #segmentCode + ':' + #segmentLevel")
    public BizBaseSegment selectBizBaseSegment(String businessGroup, String segmentCode, String segmentLevel) {
        return bizBaseSegmentManager.selectBizBaseSegment(businessGroup, segmentCode, segmentLevel);
    }
}
