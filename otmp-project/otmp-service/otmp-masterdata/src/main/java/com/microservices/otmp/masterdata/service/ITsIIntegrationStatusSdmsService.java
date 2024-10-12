package com.microservices.otmp.masterdata.service;

import java.util.List;
import com.microservices.otmp.masterdata.domain.dto.TsIIntegrationStatusSdmsDTO;

/**
 * TsIIntegrationStatusSdmsService接口
 * 
 * @author lovefamily
 * @date 2022-08-26
 */
public interface ITsIIntegrationStatusSdmsService
{
    /**
     * 查询TsIIntegrationStatusSdms
     * 
     * @param id TsIIntegrationStatusSdms主键
     * @return TsIIntegrationStatusSdmsDTO
     */
    public TsIIntegrationStatusSdmsDTO selectTsIIntegrationStatusSdmsById(Long id);

    /**
     * 查询TsIIntegrationStatusSdms列表
     *
     * @param tsIIntegrationStatusSdms TsIIntegrationStatusSdms
     * @return TsIIntegrationStatusSdmsDTO集合
     */
    public List<TsIIntegrationStatusSdmsDTO> selectTsIIntegrationStatusSdmsList(TsIIntegrationStatusSdmsDTO tsIIntegrationStatusSdms);

    /**
     * 新增TsIIntegrationStatusSdms
     * 
     * @param tsIIntegrationStatusSdmsDTO TsIIntegrationStatusSdms
     * @return 结果
     */
    public int insertTsIIntegrationStatusSdms(TsIIntegrationStatusSdmsDTO tsIIntegrationStatusSdms);

    /**
     * 修改TsIIntegrationStatusSdms
     * 
     * @param tsIIntegrationStatusSdmsDTO TsIIntegrationStatusSdms
     * @return 结果
     */
    public int updateTsIIntegrationStatusSdms(TsIIntegrationStatusSdmsDTO tsIIntegrationStatusSdms);

    /**
     * 批量删除TsIIntegrationStatusSdms
     * 
     * @param ids 需要删除的TsIIntegrationStatusSdms主键集合
     * @return 结果
     */
    public int deleteTsIIntegrationStatusSdmsByIds(Long[] ids);

    /**
     * 删除TsIIntegrationStatusSdms信息
     * 
     * @param id TsIIntegrationStatusSdms主键
     * @return 结果
     */
    public int deleteTsIIntegrationStatusSdmsById(Long id);
}
