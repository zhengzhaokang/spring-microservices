package com.microservices.otmp.masterdata.service;

import java.util.List;
import com.microservices.otmp.masterdata.domain.dto.TsITosBpcDTO;

/**
 * TsITosBpcService接口
 * 
 * @author lovefamily
 * @date 2022-08-26
 */
public interface ITsITosBpcService
{
    /**
     * 查询TsITosBpc
     * 
     * @param id TsITosBpc主键
     * @return TsITosBpcDTO
     */
    public TsITosBpcDTO selectTsITosBpcById(Long id);

    /**
     * 查询TsITosBpc列表
     *
     * @param tsITosBpc TsITosBpc
     * @return TsITosBpcDTO集合
     */
    public List<TsITosBpcDTO> selectTsITosBpcList(TsITosBpcDTO tsITosBpc);

    /**
     * 新增TsITosBpc
     * 
     * @param tsITosBpcDTO TsITosBpc
     * @return 结果
     */
    public int insertTsITosBpc(TsITosBpcDTO tsITosBpc);

    /**
     * 修改TsITosBpc
     * 
     * @param tsITosBpcDTO TsITosBpc
     * @return 结果
     */
    public int updateTsITosBpc(TsITosBpcDTO tsITosBpc);

    /**
     * 批量删除TsITosBpc
     * 
     * @param ids 需要删除的TsITosBpc主键集合
     * @return 结果
     */
    public int deleteTsITosBpcByIds(Long[] ids);

    /**
     * 删除TsITosBpc信息
     * 
     * @param id TsITosBpc主键
     * @return 结果
     */
    public int deleteTsITosBpcById(Long id);
}
