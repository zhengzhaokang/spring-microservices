package com.microservices.otmp.masterdata.mapper;

import java.util.List;
import com.microservices.otmp.masterdata.domain.entity.TsITosBpcDO;
import com.microservices.otmp.masterdata.domain.dto.TsITosBpcDTO;
import com.microservices.otmp.masterdata.domain.dto.TsITosBpcDTO;
import com.microservices.otmp.masterdata.domain.entity.TsITosBpcDO;


/**
 * TsITosBpcMapper接口
 * 
 * @author lovefamily
 * @date 2022-08-26
 */
public interface TsITosBpcMapper
{
    /**
     * 查询TsITosBpc
     * 
     * @param id TsITosBpc主键
     * @return TsITosBpc
     */
    public TsITosBpcDO selectTsITosBpcById(Long id);

    /**
     * 查询TsITosBpc列表
     * 
     * @param tsITosBpcDTO TsITosBpc
     * @return TsITosBpc集合
     */
    public List<TsITosBpcDO> selectTsITosBpcList(TsITosBpcDTO tsITosBpc);

    /**
     * 新增TsITosBpc
     * 
     * @param tsITosBpcDO TsITosBpc
     * @return 结果
     */
    public int insertTsITosBpc(TsITosBpcDO tsITosBpc);

    /**
     * 修改TsITosBpc
     * 
     * @param tsITosBpcDO TsITosBpc
     * @return 结果
     */
    public int updateTsITosBpc (TsITosBpcDO tsITosBpc);

    /**
     * 删除TsITosBpc
     * 
     * @param id TsITosBpc主键
     * @return 结果
     */
    public int deleteTsITosBpcById(Long id);

    /**
     * 批量删除TsITosBpc
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTsITosBpcByIds(Long[] ids);
}
