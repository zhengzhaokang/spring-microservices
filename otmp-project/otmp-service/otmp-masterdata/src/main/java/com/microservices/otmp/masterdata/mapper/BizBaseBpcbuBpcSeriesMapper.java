package com.microservices.otmp.masterdata.mapper;

import java.util.List;
import com.microservices.otmp.masterdata.domain.entity.BizBaseBpcbuBpcSeriesDO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseBpcbuBpcSeriesDTO;


/**
 * BizBaseBpcbuBpcSeries In SDMSMapper接口
 * 
 * @author lovefamily
 * @date 2022-10-31
 */
public interface BizBaseBpcbuBpcSeriesMapper
{
    /**
     * 查询BizBaseBpcbuBpcSeries In SDMS
     * 
     * @param id BizBaseBpcbuBpcSeries In SDMS主键
     * @return BizBaseBpcbuBpcSeries In SDMS
     */
    public BizBaseBpcbuBpcSeriesDO selectBizBaseBpcbuBpcSeriesById(Long id);

    /**
     * 查询BizBaseBpcbuBpcSeries In SDMS列表
     * 
     * @param bizBaseBpcbuBpcSeriesDTO BizBaseBpcbuBpcSeries In SDMS
     * @return BizBaseBpcbuBpcSeries In SDMS集合
     */
    public List<BizBaseBpcbuBpcSeriesDO> selectBizBaseBpcbuBpcSeriesList(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries);

    /**
     * 新增BizBaseBpcbuBpcSeries In SDMS
     * 
     * @param bizBaseBpcbuBpcSeriesDO BizBaseBpcbuBpcSeries In SDMS
     * @return 结果
     */
    public int insertBizBaseBpcbuBpcSeries(BizBaseBpcbuBpcSeriesDO bizBaseBpcbuBpcSeries);

    /**
     * 修改BizBaseBpcbuBpcSeries In SDMS
     * 
     * @param bizBaseBpcbuBpcSeriesDO BizBaseBpcbuBpcSeries In SDMS
     * @return 结果
     */
    public int updateBizBaseBpcbuBpcSeries (BizBaseBpcbuBpcSeriesDO bizBaseBpcbuBpcSeries);

    /**
     * 删除BizBaseBpcbuBpcSeries In SDMS
     *
     * @param id BizBaseBpcbuBpcSeries In SDMS主键
     * @return 结果
     */
    public int deleteBizBaseBpcbuBpcSeriesById(Long id);

    /**
     * 批量删除BizBaseBpcbuBpcSeries In SDMS
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseBpcbuBpcSeriesByIds(Long[] ids);

    List<BizBaseBpcbuBpcSeriesDO> getBpcList(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries);

    List<BizBaseBpcbuBpcSeriesDO> getBpcSeriesList(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries);

    List<BizBaseBpcbuBpcSeriesDO> selectBizBaseBpcbuBpcSeriesListCheck(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries);
}
