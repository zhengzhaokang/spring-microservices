package com.microservices.otmp.masterdata.mapper;

import com.microservices.otmp.masterdata.domain.dto.BizBaseProfitCenterLgapDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseProfitCenterLgapDO;

import java.util.List;


/**
 * ProfitCenter Table from LGAPMapper接口
 * 
 * @author lovefamily
 * @date 2023-07-01
 */
public interface BizBaseProfitCenterLgapMapper
{
    /**
     * 查询ProfitCenter Table from LGAP
     * 
     * @param id ProfitCenter Table from LGAP主键
     * @return ProfitCenter Table from LGAP
     */
    public BizBaseProfitCenterLgapDO selectBizBaseProfitCenterLgapById(Long id);

    /**
     * 查询ProfitCenter Table from LGAP列表
     * 
     * @param bizBaseProfitCenterLgapDTO ProfitCenter Table from LGAP
     * @return ProfitCenter Table from LGAP集合
     */
    public List<BizBaseProfitCenterLgapDO> selectBizBaseProfitCenterLgapList(BizBaseProfitCenterLgapDTO bizBaseProfitCenterLgap);

    /**
     * 新增ProfitCenter Table from LGAP
     * 
     * @param bizBaseProfitCenterLgapDO ProfitCenter Table from LGAP
     * @return 结果
     */
    public int insertBizBaseProfitCenterLgap(BizBaseProfitCenterLgapDO bizBaseProfitCenterLgap);

    /**
     * 修改ProfitCenter Table from LGAP
     * 
     * @param bizBaseProfitCenterLgapDO ProfitCenter Table from LGAP
     * @return 结果
     */
    public int updateBizBaseProfitCenterLgap (BizBaseProfitCenterLgapDO bizBaseProfitCenterLgap);

    /**
     * 删除ProfitCenter Table from LGAP
     * 
     * @param id ProfitCenter Table from LGAP主键
     * @return 结果
     */
    public int deleteBizBaseProfitCenterLgapById(Long id);

    /**
     * 批量删除ProfitCenter Table from LGAP
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseProfitCenterLgapByIds(Long[] ids);
}
