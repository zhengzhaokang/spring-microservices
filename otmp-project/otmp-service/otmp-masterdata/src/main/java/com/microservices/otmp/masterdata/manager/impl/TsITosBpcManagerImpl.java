package com.microservices.otmp.masterdata.manager.impl;

import java.util.List;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.dto.TsITosBpcDTO;
import com.microservices.otmp.masterdata.domain.entity.TsITosBpcDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.masterdata.mapper.TsITosBpcMapper;
import com.microservices.otmp.masterdata.domain.entity.TsITosBpcDO;
import com.microservices.otmp.masterdata.domain.dto.TsITosBpcDTO;
import com.microservices.otmp.masterdata.manager.ITsITosBpcManager;

/**
 * TsITosBpcManager业务层处理
 * 
 * @author lovefamily
 * @date 2022-08-26
 */
@Service
public class TsITosBpcManagerImpl implements ITsITosBpcManager
{
    @Autowired
    private TsITosBpcMapper tsITosBpcMapper;

    /**
     * 查询TsITosBpc
     * 
     * @param id TsITosBpc主键
     * @return TsITosBpcDO
     */
    @Override
    public TsITosBpcDO selectTsITosBpcById(Long id)
    {
        return tsITosBpcMapper.selectTsITosBpcById(id);
    }

    /**
     * 查询TsITosBpc列表
     *
     * @param tsITosBpc TsITosBpc
     * @return TsITosBpcDO
     */
    @Override
    public List<TsITosBpcDO> selectTsITosBpcList(TsITosBpcDTO tsITosBpc)
    {
        return tsITosBpcMapper.selectTsITosBpcList(tsITosBpc);
    }

    /**
     * 新增TsITosBpc
     *
     * @param tsITosBpc TsITosBpc
     * @return 结果
     */
    @Override
    public int insertTsITosBpc(TsITosBpcDO tsITosBpc)
    {
        tsITosBpc.setCreateTime(DateUtils.getNowDate());
        return tsITosBpcMapper.insertTsITosBpc (tsITosBpc);
    }

    /**
     * 修改TsITosBpc
     *
     * @param tsITosBpc  TsITosBpc
     * @return 结果
     */
    @Override
    public int updateTsITosBpc(TsITosBpcDO tsITosBpc)
    {
        tsITosBpc.setUpdateTime(DateUtils.getNowDate());
        return tsITosBpcMapper.updateTsITosBpc (tsITosBpc);
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
        return tsITosBpcMapper.deleteTsITosBpcByIds(ids);
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
        return tsITosBpcMapper.deleteTsITosBpcById(id);
    }
}
