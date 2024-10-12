package com.microservices.otmp.masterdata.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.enums.BooleanStatusEnum;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import com.microservices.otmp.masterdata.util.ConvertStringToLongListUtil;
import com.microservices.otmp.masterdata.util.EntityUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.microservices.otmp.masterdata.manager.IBizBaseBpcbuBpcSeriesManager;
import com.microservices.otmp.masterdata.domain.dto.BizBaseBpcbuBpcSeriesDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseBpcbuBpcSeriesDO;
import com.microservices.otmp.masterdata.service.IBizBaseBpcbuBpcSeriesService;

/**
 * BizBaseBpcbuBpcSeries In SDMSService业务层处理
 * 
 * @author lovefamily
 * @date 2022-10-31
 */
@Service
public class BizBaseBpcbuBpcSeriesServiceImpl implements IBizBaseBpcbuBpcSeriesService
{
    private static final Logger log = LoggerFactory.getLogger(BizBaseBpcbuBpcSeriesServiceImpl.class);

    @Autowired
    private RedisUtils redisUtils;
    public static final String REDIS_NAME_PREFIX = "master:";

    public void setBpcSeriesToRedis( List<BizBaseBpcbuBpcSeriesDO> bizBaseBpcbuBpcSeriesDOS) {
        redisUtils.set(REDIS_NAME_PREFIX+"bpc_series",bizBaseBpcbuBpcSeriesDOS, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
        List<String> stringList = bizBaseBpcbuBpcSeriesDOS.stream().filter(t -> StringUtils.isNotBlank(t.getBpcBuCode())).map(BizBaseBpcbuBpcSeriesDO::getBpcBuCode).collect(Collectors.toList());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX + "bpc_bu", stringList, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    @Autowired
    private IBizBaseBpcbuBpcSeriesManager bizBaseBpcbuBpcSeriesManager;

    /**
     * 查询BizBaseBpcbuBpcSeries In SDMS
     * 
     * @param id BizBaseBpcbuBpcSeries In SDMS主键
     * @return BizBaseBpcbuBpcSeries In SDMSDTO
     */
    @Override
    public BizBaseBpcbuBpcSeriesDTO selectBizBaseBpcbuBpcSeriesById(Long id)
    {
         BizBaseBpcbuBpcSeriesDO bizBaseBpcbuBpcSeriesDO =  bizBaseBpcbuBpcSeriesManager.selectBizBaseBpcbuBpcSeriesById(id);
         BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeriesDTO = new BizBaseBpcbuBpcSeriesDTO();
         BeanUtils.copyProperties(bizBaseBpcbuBpcSeriesDO, bizBaseBpcbuBpcSeriesDTO);
        return bizBaseBpcbuBpcSeriesDTO;
    }

    /**
     * 查询BizBaseBpcbuBpcSeries In SDMS列表
     *
     * @param bizBaseBpcbuBpcSeries BizBaseBpcbuBpcSeries In SDMS
     * @return BizBaseBpcbuBpcSeries In SDMSDTO
     */
    @Override
    public PageInfo<BizBaseBpcbuBpcSeriesDTO> selectBizBaseBpcbuBpcSeriesList(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries)
    {
        EntityUtil.objectToTrim(bizBaseBpcbuBpcSeries);
        if(StringUtils.isNotBlank(bizBaseBpcbuBpcSeries.getIds())){
            List<Long> longIdsList = ConvertStringToLongListUtil.splitToLongList(bizBaseBpcbuBpcSeries.getIds());
            bizBaseBpcbuBpcSeries.setIdsList(longIdsList);
        }

        List<BizBaseBpcbuBpcSeriesDO> bizBaseBpcbuBpcSeriesDOS =
                    bizBaseBpcbuBpcSeriesManager.selectBizBaseBpcbuBpcSeriesList(bizBaseBpcbuBpcSeries);
        List<BizBaseBpcbuBpcSeriesDTO> bizBaseBpcbuBpcSeriesList = new ArrayList<>(bizBaseBpcbuBpcSeriesDOS.size());
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(bizBaseBpcbuBpcSeriesDOS, bizBaseBpcbuBpcSeriesList, BizBaseBpcbuBpcSeriesDTO.class);
        setBpcSeriesToRedis(bizBaseBpcbuBpcSeriesDOS);
        PageInfo<BizBaseBpcbuBpcSeriesDO> pageInfo = new PageInfo<>(bizBaseBpcbuBpcSeriesDOS);
        PageInfo<BizBaseBpcbuBpcSeriesDTO> resultPageInfo = new PageInfo<>(bizBaseBpcbuBpcSeriesList);
        resultPageInfo.setTotal(pageInfo.getTotal());
        return resultPageInfo;

    }

    /**
     * 新增BizBaseBpcbuBpcSeries In SDMS
     * 
     * @param bizBaseBpcbuBpcSeriesDTO BizBaseBpcbuBpcSeries In SDMS
     * @return 结果
     */
    @Override
    public int insertBizBaseBpcbuBpcSeries(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries)
    {
        BizBaseBpcbuBpcSeriesDO bizBaseBpcbuBpcSeriesDO =new  BizBaseBpcbuBpcSeriesDO();
        bizBaseBpcbuBpcSeries.setCreateTime(DateUtils.getNowDate());

        BeanUtils.copyProperties(bizBaseBpcbuBpcSeries, bizBaseBpcbuBpcSeriesDO);
        return bizBaseBpcbuBpcSeriesManager.insertBizBaseBpcbuBpcSeries(bizBaseBpcbuBpcSeriesDO);
    }

    /**
     * 修改BizBaseBpcbuBpcSeries In SDMS
     * 
     * @param bizBaseBpcbuBpcSeriesDTO BizBaseBpcbuBpcSeries In SDMS
     * @return 结果
     */
    @Override
    public int updateBizBaseBpcbuBpcSeries(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries)
    {
        bizBaseBpcbuBpcSeries.setUpdateTime(DateUtils.getNowDate());
       BizBaseBpcbuBpcSeriesDO bizBaseBpcbuBpcSeriesDO =new  BizBaseBpcbuBpcSeriesDO();
        BeanUtils.copyProperties(bizBaseBpcbuBpcSeries, bizBaseBpcbuBpcSeriesDO);
        return bizBaseBpcbuBpcSeriesManager.updateBizBaseBpcbuBpcSeries(bizBaseBpcbuBpcSeriesDO);
    }

    /**
     * 批量删除BizBaseBpcbuBpcSeries In SDMS
     * 
     * @param ids 需要删除的BizBaseBpcbuBpcSeries In SDMS主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseBpcbuBpcSeriesByIds(Long[] ids)
    {
        return bizBaseBpcbuBpcSeriesManager.deleteBizBaseBpcbuBpcSeriesByIds(ids);
    }

    /**
     * 删除BizBaseBpcbuBpcSeries In SDMS信息
     * 
     * @param id BizBaseBpcbuBpcSeries In SDMS主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseBpcbuBpcSeriesById(Long id)
    {
        return bizBaseBpcbuBpcSeriesManager.deleteBizBaseBpcbuBpcSeriesById(id);
    }

    @Override
    public List<BizBaseBpcbuBpcSeriesDTO> getBpcList(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries) {
        List<BizBaseBpcbuBpcSeriesDO> bizBaseBpcbuBpcSeriesDOS =
                 bizBaseBpcbuBpcSeriesManager.getBpcList(bizBaseBpcbuBpcSeries);
        List<BizBaseBpcbuBpcSeriesDTO> bizBaseBpcbuBpcSeriesList = new ArrayList<>(bizBaseBpcbuBpcSeriesDOS.size());
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(bizBaseBpcbuBpcSeriesDOS, bizBaseBpcbuBpcSeriesList, BizBaseBpcbuBpcSeriesDTO.class);
        return  bizBaseBpcbuBpcSeriesList;
    }

    @Override
    public List<BizBaseBpcbuBpcSeriesDTO> getBpcSeriesList(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries) {
        List<BizBaseBpcbuBpcSeriesDO> bizBaseBpcbuBpcSeriesDOS =
                bizBaseBpcbuBpcSeriesManager.getBpcSeriesList(bizBaseBpcbuBpcSeries);
        List<BizBaseBpcbuBpcSeriesDTO> bizBaseBpcbuBpcSeriesList = new ArrayList<>(bizBaseBpcbuBpcSeriesDOS.size());
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(bizBaseBpcbuBpcSeriesDOS, bizBaseBpcbuBpcSeriesList, BizBaseBpcbuBpcSeriesDTO.class);
        return  bizBaseBpcbuBpcSeriesList;
    }

    @Override
    public List<BizBaseBpcbuBpcSeriesDO> selectBizBaseBpcbuBpcSeriesListCheck(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries) {
        return bizBaseBpcbuBpcSeriesManager.selectBizBaseBpcbuBpcSeriesListCheck(bizBaseBpcbuBpcSeries);
    }

    @Override
    public ResultDTO<String> importExcel(List<BizBaseBpcbuBpcSeriesDTO> bizs, String loginName) {
        AtomicInteger successNum = new AtomicInteger();
        AtomicInteger failureNum = new AtomicInteger();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        BizBaseBpcbuBpcSeriesDO bizBaseBpcbuBpcSeriesDO =new  BizBaseBpcbuBpcSeriesDO();
        HashSet<String> hashSet = new HashSet<>();


        if (CollectionUtils.isEmpty(bizs)) {
            log.error("BizBaseBpcbuBpcSeries导入MasterData 数据不能为空！");
            throw new ServiceException("导入MasterData 数据不能为空！");
        }
        for (BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeriesDTO : bizs) {
            String check = "BPC BU = " + bizBaseBpcbuBpcSeriesDTO.getBpcBuCode() + "，BPC Series = " + bizBaseBpcbuBpcSeriesDTO.getBpcSeriesCode();
            if (!hashSet.add(check)) {
                return ResultDTO.fail("很抱歉，导入失败，导入数据中存在重复数据：" + check);
            }
            bizBaseBpcbuBpcSeriesDTO.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
            List<BizBaseBpcbuBpcSeriesDO> buList = bizBaseBpcbuBpcSeriesManager.selectBizBaseBpcbuBpcSeriesListCheck(bizBaseBpcbuBpcSeriesDTO);
            if (CollectionUtils.isNotEmpty(buList)) {
                return ResultDTO.fail("Record exists already");
            }
        }
        for (BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeriesDTO : bizs) {
            try {
                List<BizBaseBpcbuBpcSeriesDO> buList = bizBaseBpcbuBpcSeriesManager.selectBizBaseBpcbuBpcSeriesListCheck(bizBaseBpcbuBpcSeriesDTO);
                BeanUtils.copyProperties(bizBaseBpcbuBpcSeriesDTO, bizBaseBpcbuBpcSeriesDO);
                if (CollectionUtils.isNotEmpty(buList)) {
                    bizBaseBpcbuBpcSeriesDTO.setId(buList.get(0).getId());
                    bizBaseBpcbuBpcSeriesDTO.setUpdateTime(DateUtils.getNowDate());
                    bizBaseBpcbuBpcSeriesManager.updateBizBaseBpcbuBpcSeries(bizBaseBpcbuBpcSeriesDO);
                    successNum.getAndIncrement();
                    continue;
                }
                bizBaseBpcbuBpcSeriesDTO.setCreateTime(DateUtils.getNowDate());
                bizBaseBpcbuBpcSeriesDTO.setUpdateTime(DateUtils.getNowDate());
                bizBaseBpcbuBpcSeriesDTO.setCreateBy(loginName);
                bizBaseBpcbuBpcSeriesDTO.setStatus("Y");
                bizBaseBpcbuBpcSeriesManager.insertBizBaseBpcbuBpcSeries(bizBaseBpcbuBpcSeriesDO);

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
        return ResultDTO.success(successMsg.toString());
    }
}
