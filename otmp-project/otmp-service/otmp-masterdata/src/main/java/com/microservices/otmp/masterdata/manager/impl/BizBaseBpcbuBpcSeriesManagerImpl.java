package com.microservices.otmp.masterdata.manager.impl;

import java.util.List;
import com.microservices.otmp.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.masterdata.mapper.BizBaseBpcbuBpcSeriesMapper;
import com.microservices.otmp.masterdata.domain.entity.BizBaseBpcbuBpcSeriesDO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseBpcbuBpcSeriesDTO;
import com.microservices.otmp.masterdata.manager.IBizBaseBpcbuBpcSeriesManager;

/**
 * BizBaseBpcbuBpcSeries In SDMSManager业务层处理
 * 
 * @author lovefamily
 * @date 2022-10-31
 */
@Service
public class BizBaseBpcbuBpcSeriesManagerImpl implements IBizBaseBpcbuBpcSeriesManager
{
    private static final Logger log = LoggerFactory.getLogger(BizBaseBpcbuBpcSeriesManagerImpl.class);

    @Autowired
    private BizBaseBpcbuBpcSeriesMapper bizBaseBpcbuBpcSeriesMapper;

    /**
     * 查询BizBaseBpcbuBpcSeries In SDMS
     * 
     * @param id BizBaseBpcbuBpcSeries In SDMS主键
     * @return BizBaseBpcbuBpcSeries In SDMSDO
     */
    @Override
    public BizBaseBpcbuBpcSeriesDO selectBizBaseBpcbuBpcSeriesById(Long id)
    {
        log.info("查询BizBaseBpcbuBpcSeries,id:{}", id);
        return bizBaseBpcbuBpcSeriesMapper.selectBizBaseBpcbuBpcSeriesById(id);
    }

    /**
     * 查询BizBaseBpcbuBpcSeries In SDMS列表
     *
     * @param bizBaseBpcbuBpcSeries BizBaseBpcbuBpcSeries In SDMS
     * @return BizBaseBpcbuBpcSeries In SDMSDO
     */
    @Override
    public List<BizBaseBpcbuBpcSeriesDO> selectBizBaseBpcbuBpcSeriesList(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries)
    {
        return bizBaseBpcbuBpcSeriesMapper.selectBizBaseBpcbuBpcSeriesList(bizBaseBpcbuBpcSeries);
    }

    /**
     * 新增BizBaseBpcbuBpcSeries In SDMS
     *
     * @param bizBaseBpcbuBpcSeries BizBaseBpcbuBpcSeries In SDMS
     * @return 结果
     */
    @Override
    public int insertBizBaseBpcbuBpcSeries(BizBaseBpcbuBpcSeriesDO bizBaseBpcbuBpcSeries)
    {
        bizBaseBpcbuBpcSeries.setCreateTime(DateUtils.getNowDate());
        return bizBaseBpcbuBpcSeriesMapper.insertBizBaseBpcbuBpcSeries (bizBaseBpcbuBpcSeries);
    }

    /**
     * 修改BizBaseBpcbuBpcSeries In SDMS
     *
     * @param bizBaseBpcbuBpcSeries  BizBaseBpcbuBpcSeries In SDMS
     * @return 结果
     */
    @Override
    public int updateBizBaseBpcbuBpcSeries(BizBaseBpcbuBpcSeriesDO bizBaseBpcbuBpcSeries)
    {
        bizBaseBpcbuBpcSeries.setUpdateTime(DateUtils.getNowDate());
        return bizBaseBpcbuBpcSeriesMapper.updateBizBaseBpcbuBpcSeries (bizBaseBpcbuBpcSeries);
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
        return bizBaseBpcbuBpcSeriesMapper.deleteBizBaseBpcbuBpcSeriesByIds(ids);
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
        return bizBaseBpcbuBpcSeriesMapper.deleteBizBaseBpcbuBpcSeriesById(id);
    }

    @Override
    public List<BizBaseBpcbuBpcSeriesDO> getBpcList(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries) {
        return bizBaseBpcbuBpcSeriesMapper.getBpcList(bizBaseBpcbuBpcSeries);
    }

    @Override
    public List<BizBaseBpcbuBpcSeriesDO> getBpcSeriesList(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries) {

        return bizBaseBpcbuBpcSeriesMapper.getBpcSeriesList(bizBaseBpcbuBpcSeries);
    }

    @Override
    public List<BizBaseBpcbuBpcSeriesDO> selectBizBaseBpcbuBpcSeriesListCheck(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries) {
        return bizBaseBpcbuBpcSeriesMapper.selectBizBaseBpcbuBpcSeriesListCheck(bizBaseBpcbuBpcSeries);
    }
}
