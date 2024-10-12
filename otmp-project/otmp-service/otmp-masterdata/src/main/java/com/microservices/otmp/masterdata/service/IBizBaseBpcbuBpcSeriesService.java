package com.microservices.otmp.masterdata.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseBpcbuBpcSeriesDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseBpcbuBpcSeriesDO;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseBpcbuBpcSeriesDTO;

import java.util.List;

/**
 * BizBaseBpcbuBpcSeries In SDMSService接口
 * 
 * @author lovefamily
 * @date 2022-10-31
 */
public interface IBizBaseBpcbuBpcSeriesService
{
    /**
     * 查询BizBaseBpcbuBpcSeries In SDMS
     * 
     * @param id BizBaseBpcbuBpcSeries In SDMS主键
     * @return BizBaseBpcbuBpcSeries In SDMSDTO
     */
    public BizBaseBpcbuBpcSeriesDTO selectBizBaseBpcbuBpcSeriesById(Long id);

    /**
     * 查询BizBaseBpcbuBpcSeries In SDMS列表
     *
     * @param bizBaseBpcbuBpcSeries BizBaseBpcbuBpcSeries In SDMS
     * @return BizBaseBpcbuBpcSeries In SDMSDTO集合
     */
    public PageInfo<BizBaseBpcbuBpcSeriesDTO>
    selectBizBaseBpcbuBpcSeriesList(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries);

    /**
     * 新增BizBaseBpcbuBpcSeries In SDMS
     * 
     * @param bizBaseBpcbuBpcSeriesDTO BizBaseBpcbuBpcSeries In SDMS
     * @return 结果
     */
    public int insertBizBaseBpcbuBpcSeries(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries);

    /**
     * 修改BizBaseBpcbuBpcSeries In SDMS
     * 
     * @param bizBaseBpcbuBpcSeriesDTO BizBaseBpcbuBpcSeries In SDMS
     * @return 结果
     */
    public int updateBizBaseBpcbuBpcSeries(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries);

    /**
     * 批量删除BizBaseBpcbuBpcSeries In SDMS
     * 
     * @param ids 需要删除的BizBaseBpcbuBpcSeries In SDMS主键集合
     * @return 结果
     */
    public int deleteBizBaseBpcbuBpcSeriesByIds(Long[] ids);

    /**
     * 删除BizBaseBpcbuBpcSeries In SDMS信息
     * 
     * @param id BizBaseBpcbuBpcSeries In SDMS主键
     * @return 结果
     */
    public int deleteBizBaseBpcbuBpcSeriesById(Long id);

    List<BizBaseBpcbuBpcSeriesDTO> getBpcList(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries);

    List<BizBaseBpcbuBpcSeriesDTO> getBpcSeriesList(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries);

    List<BizBaseBpcbuBpcSeriesDO> selectBizBaseBpcbuBpcSeriesListCheck(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries);

    /**
     * 导入BizBaseBpcbuBpcSeries In SDMS
     * @param bizs
     * @param loginName
     * @return
     */
    ResultDTO<String> importExcel(List<BizBaseBpcbuBpcSeriesDTO> bizs, String loginName);
}
