package com.microservices.otmp.masterdata.manager;

import java.util.List;
import com.microservices.otmp.masterdata.domain.entity.TsIIntegrationStatusSdmsDO;
import com.microservices.otmp.masterdata.domain.dto.TsIIntegrationStatusSdmsDTO;


/**
 * TsIIntegrationStatusSdmsManager接口
 * 
 * @author lovefamily
 * @date 2022-08-26
 */
public interface ITsIIntegrationStatusSdmsManager
{
    /**
     * 查询TsIIntegrationStatusSdms
     * 
     * @param id TsIIntegrationStatusSdms主键
     * @return TsIIntegrationStatusSdms
     */
    public TsIIntegrationStatusSdmsDO selectTsIIntegrationStatusSdmsById(Long id);

    /**
     * 查询TsIIntegrationStatusSdms列表
     *
     * @param tsIIntegrationStatusSdms TsIIntegrationStatusSdms
     * @return TsIIntegrationStatusSdms集合
     */
    public List<TsIIntegrationStatusSdmsDO> selectTsIIntegrationStatusSdmsList(TsIIntegrationStatusSdmsDTO tsIIntegrationStatusSdms);

    /**
     * 新增TsIIntegrationStatusSdms
     *
     * @param tsIIntegrationStatusSdms TsIIntegrationStatusSdms
     * @return 结果
     */
    public int insertTsIIntegrationStatusSdms(TsIIntegrationStatusSdmsDO tsIIntegrationStatusSdms);

    /**
     * 修改TsIIntegrationStatusSdms
     *
     * @param tsIIntegrationStatusSdms TsIIntegrationStatusSdms
     * @return 结果
     */
    public int updateTsIIntegrationStatusSdms(TsIIntegrationStatusSdmsDO tsIIntegrationStatusSdms);

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
