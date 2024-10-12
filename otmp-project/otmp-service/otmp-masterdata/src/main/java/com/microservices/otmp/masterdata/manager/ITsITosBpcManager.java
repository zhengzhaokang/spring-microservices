package com.microservices.otmp.masterdata.manager;

import java.util.List;
import com.microservices.otmp.masterdata.domain.entity.TsITosBpcDO;
import com.microservices.otmp.masterdata.domain.dto.TsITosBpcDTO;


/**
 * TsITosBpcManager接口
 * 
 * @author lovefamily
 * @date 2022-08-26
 */
public interface ITsITosBpcManager
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
     * @param tsITosBpc TsITosBpc
     * @return TsITosBpc集合
     */
    public List<TsITosBpcDO> selectTsITosBpcList(TsITosBpcDTO tsITosBpc);

    /**
     * 新增TsITosBpc
     *
     * @param tsITosBpc TsITosBpc
     * @return 结果
     */
    public int insertTsITosBpc(TsITosBpcDO tsITosBpc);

    /**
     * 修改TsITosBpc
     *
     * @param tsITosBpc TsITosBpc
     * @return 结果
     */
    public int updateTsITosBpc(TsITosBpcDO tsITosBpc);

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
