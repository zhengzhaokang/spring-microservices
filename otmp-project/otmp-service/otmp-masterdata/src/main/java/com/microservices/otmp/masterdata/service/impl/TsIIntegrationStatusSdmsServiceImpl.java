package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.masterdata.domain.dto.TsIIntegrationStatusSdmsDTO;
import com.microservices.otmp.masterdata.domain.entity.TsIIntegrationStatusSdmsDO;
import com.microservices.otmp.masterdata.manager.ITsIIntegrationStatusSdmsManager;
import com.microservices.otmp.masterdata.service.ITsIIntegrationStatusSdmsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * TsIIntegrationStatusSdmsService业务层处理
 * 
 * @author lovefamily
 * @date 2022-08-26
 */
@Service
public class TsIIntegrationStatusSdmsServiceImpl implements ITsIIntegrationStatusSdmsService
{
    @Autowired
    private ITsIIntegrationStatusSdmsManager tsIIntegrationStatusSdmsManager;

    /**
     * 查询TsIIntegrationStatusSdms
     * 
     * @param id TsIIntegrationStatusSdms主键
     * @return TsIIntegrationStatusSdmsDTO
     */
    @Override
    public TsIIntegrationStatusSdmsDTO selectTsIIntegrationStatusSdmsById(Long id)
    {
         TsIIntegrationStatusSdmsDO tsIIntegrationStatusSdmsDO =  tsIIntegrationStatusSdmsManager.selectTsIIntegrationStatusSdmsById(id);
         TsIIntegrationStatusSdmsDTO tsIIntegrationStatusSdmsDTO = new TsIIntegrationStatusSdmsDTO();
         BeanUtils.copyProperties(tsIIntegrationStatusSdmsDO, tsIIntegrationStatusSdmsDTO);
        return tsIIntegrationStatusSdmsDTO;
    }

    /**
     * 查询TsIIntegrationStatusSdms列表
     *
     * @param tsIIntegrationStatusSdms TsIIntegrationStatusSdms
     * @return TsIIntegrationStatusSdmsDTO
     */
    @Override
    public List<TsIIntegrationStatusSdmsDTO> selectTsIIntegrationStatusSdmsList(TsIIntegrationStatusSdmsDTO tsIIntegrationStatusSdms)
    {

        List<TsIIntegrationStatusSdmsDO> tsIIntegrationStatusSdmsDOS =
                    tsIIntegrationStatusSdmsManager.selectTsIIntegrationStatusSdmsList(tsIIntegrationStatusSdms);
        List<TsIIntegrationStatusSdmsDTO> tsIIntegrationStatusSdmsList = new ArrayList<>(tsIIntegrationStatusSdmsDOS.size());
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(tsIIntegrationStatusSdmsDOS, tsIIntegrationStatusSdmsList, TsIIntegrationStatusSdmsDTO.class);
        return tsIIntegrationStatusSdmsList;

    }

    /**
     * 新增TsIIntegrationStatusSdms
     * 
     * @param tsIIntegrationStatusSdmsDTO TsIIntegrationStatusSdms
     * @return 结果
     */
    @Override
    public int insertTsIIntegrationStatusSdms(TsIIntegrationStatusSdmsDTO tsIIntegrationStatusSdms)
    {
        TsIIntegrationStatusSdmsDO tsIIntegrationStatusSdmsDO =new  TsIIntegrationStatusSdmsDO();
        BeanUtils.copyProperties(tsIIntegrationStatusSdms, tsIIntegrationStatusSdmsDO);
        return tsIIntegrationStatusSdmsManager.insertTsIIntegrationStatusSdms(tsIIntegrationStatusSdmsDO);
    }

    /**
     * 修改TsIIntegrationStatusSdms
     * 
     * @param tsIIntegrationStatusSdmsDTO TsIIntegrationStatusSdms
     * @return 结果
     */
    @Override
    public int updateTsIIntegrationStatusSdms(TsIIntegrationStatusSdmsDTO tsIIntegrationStatusSdms)
    {
       TsIIntegrationStatusSdmsDO tsIIntegrationStatusSdmsDO =new  TsIIntegrationStatusSdmsDO();
        BeanUtils.copyProperties(tsIIntegrationStatusSdms, tsIIntegrationStatusSdmsDO);
        return tsIIntegrationStatusSdmsManager.updateTsIIntegrationStatusSdms(tsIIntegrationStatusSdmsDO);
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
        return tsIIntegrationStatusSdmsManager.deleteTsIIntegrationStatusSdmsByIds(ids);
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
        return tsIIntegrationStatusSdmsManager.deleteTsIIntegrationStatusSdmsById(id);
    }
}
