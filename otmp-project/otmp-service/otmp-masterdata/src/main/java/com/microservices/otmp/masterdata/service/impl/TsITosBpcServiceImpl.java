package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.dto.TsITosBpcDTO;
import com.microservices.otmp.masterdata.domain.entity.TsITosBpcDO;
import com.microservices.otmp.masterdata.manager.ITsITosBpcManager;
import com.microservices.otmp.masterdata.service.ITsITosBpcService;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.dto.TsITosBpcDTO;
import com.microservices.otmp.masterdata.domain.entity.TsITosBpcDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * TsITosBpcService业务层处理
 * 
 * @author lovefamily
 * @date 2022-08-26
 */
@Service
public class TsITosBpcServiceImpl implements ITsITosBpcService
{
    @Autowired
    private ITsITosBpcManager tsITosBpcManager;

    /**
     * 查询TsITosBpc
     * 
     * @param id TsITosBpc主键
     * @return TsITosBpcDTO
     */
    @Override
    public TsITosBpcDTO selectTsITosBpcById(Long id)
    {
         TsITosBpcDO tsITosBpcDO =  tsITosBpcManager.selectTsITosBpcById(id);
         TsITosBpcDTO tsITosBpcDTO = new TsITosBpcDTO();
         BeanUtils.copyProperties(tsITosBpcDO, tsITosBpcDTO);
        return tsITosBpcDTO;
    }

    /**
     * 查询TsITosBpc列表
     *
     * @param tsITosBpc TsITosBpc
     * @return TsITosBpcDTO
     */
    @Override
    public List<TsITosBpcDTO> selectTsITosBpcList(TsITosBpcDTO tsITosBpc)
    {

        List<TsITosBpcDO> tsITosBpcDOS =
                    tsITosBpcManager.selectTsITosBpcList(tsITosBpc);
        List<TsITosBpcDTO> tsITosBpcList = new ArrayList<>(tsITosBpcDOS.size());
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(tsITosBpcDOS, tsITosBpcList, TsITosBpcDTO.class);
        return tsITosBpcList;

    }

    /**
     * 新增TsITosBpc
     * 
     * @param tsITosBpcDTO TsITosBpc
     * @return 结果
     */
    @Override
    public int insertTsITosBpc(TsITosBpcDTO tsITosBpc)
    {
        tsITosBpc.setCreateTime(DateUtils.getNowDate());
        TsITosBpcDO tsITosBpcDO =new  TsITosBpcDO();
        BeanUtils.copyProperties(tsITosBpc, tsITosBpcDO);
        return tsITosBpcManager.insertTsITosBpc(tsITosBpcDO);
    }

    /**
     * 修改TsITosBpc
     * 
     * @param tsITosBpcDTO TsITosBpc
     * @return 结果
     */
    @Override
    public int updateTsITosBpc(TsITosBpcDTO tsITosBpc)
    {
        tsITosBpc.setUpdateTime(DateUtils.getNowDate());
       TsITosBpcDO tsITosBpcDO =new  TsITosBpcDO();
        BeanUtils.copyProperties(tsITosBpc, tsITosBpcDO);
        return tsITosBpcManager.updateTsITosBpc(tsITosBpcDO);
    }

    /**
     * 批量删除TsITosBpc
     * 
     * @param ids 需要删除的TsITosBpc主键
     * @return 结果
     */
    @Override
    public int deleteTsITosBpcByIds(Long[] ids)
    {
        return tsITosBpcManager.deleteTsITosBpcByIds(ids);
    }

    /**
     * 删除TsITosBpc信息
     * 
     * @param id TsITosBpc主键
     * @return 结果
     */
    @Override
    public int deleteTsITosBpcById(Long id)
    {
        return tsITosBpcManager.deleteTsITosBpcById(id);
    }
}
