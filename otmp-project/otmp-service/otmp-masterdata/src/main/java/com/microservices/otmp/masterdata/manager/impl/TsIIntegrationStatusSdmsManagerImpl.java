package com.microservices.otmp.masterdata.manager.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.masterdata.mapper.TsIIntegrationStatusSdmsMapper;
import com.microservices.otmp.masterdata.domain.entity.TsIIntegrationStatusSdmsDO;
import com.microservices.otmp.masterdata.domain.dto.TsIIntegrationStatusSdmsDTO;
import com.microservices.otmp.masterdata.manager.ITsIIntegrationStatusSdmsManager;

/**
 * TsIIntegrationStatusSdmsManager业务层处理
 * 
 * @author lovefamily
 * @date 2022-08-26
 */
@Service
public class TsIIntegrationStatusSdmsManagerImpl implements ITsIIntegrationStatusSdmsManager
{
    @Autowired
    private TsIIntegrationStatusSdmsMapper tsIIntegrationStatusSdmsMapper;

    /**
     * 查询TsIIntegrationStatusSdms
     * 
     * @param id TsIIntegrationStatusSdms主键
     * @return TsIIntegrationStatusSdmsDO
     */
    @Override
    public TsIIntegrationStatusSdmsDO selectTsIIntegrationStatusSdmsById(Long id)
    {
        return tsIIntegrationStatusSdmsMapper.selectTsIIntegrationStatusSdmsById(id);
    }

    /**
     * 查询TsIIntegrationStatusSdms列表
     *
     * @param tsIIntegrationStatusSdms TsIIntegrationStatusSdms
     * @return TsIIntegrationStatusSdmsDO
     */
    @Override
    public List<TsIIntegrationStatusSdmsDO> selectTsIIntegrationStatusSdmsList(TsIIntegrationStatusSdmsDTO tsIIntegrationStatusSdms)
    {
        return tsIIntegrationStatusSdmsMapper.selectTsIIntegrationStatusSdmsList(tsIIntegrationStatusSdms);
    }

    /**
     * 新增TsIIntegrationStatusSdms
     *
     * @param tsIIntegrationStatusSdms TsIIntegrationStatusSdms
     * @return 结果
     */
    @Override
    public int insertTsIIntegrationStatusSdms(TsIIntegrationStatusSdmsDO tsIIntegrationStatusSdms)
    {
        return tsIIntegrationStatusSdmsMapper.insertTsIIntegrationStatusSdms (tsIIntegrationStatusSdms);
    }

    /**
     * 修改TsIIntegrationStatusSdms
     *
     * @param tsIIntegrationStatusSdms  TsIIntegrationStatusSdms
     * @return 结果
     */
    @Override
    public int updateTsIIntegrationStatusSdms(TsIIntegrationStatusSdmsDO tsIIntegrationStatusSdms)
    {
        return tsIIntegrationStatusSdmsMapper.updateTsIIntegrationStatusSdms (tsIIntegrationStatusSdms);
    }

    /**
     * 批量删除TsIIntegrationStatusSdms
     * 
     * @param ids 需要删除的TsIIntegrationStatusSdms主键
     * @return 结果
     */
    @Override
    public int deleteTsIIntegrationStatusSdmsByIds(Long[] ids)
    {
        return tsIIntegrationStatusSdmsMapper.deleteTsIIntegrationStatusSdmsByIds(ids);
    }

    /**
     * 删除TsIIntegrationStatusSdms信息
     * 
     * @param id TsIIntegrationStatusSdms主键
     * @return 结果
     */
    @Override
    public int deleteTsIIntegrationStatusSdmsById(Long id)
    {
        return tsIIntegrationStatusSdmsMapper.deleteTsIIntegrationStatusSdmsById(id);
    }
}
