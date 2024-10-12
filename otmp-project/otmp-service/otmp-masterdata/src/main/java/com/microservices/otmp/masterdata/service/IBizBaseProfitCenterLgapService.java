package com.microservices.otmp.masterdata.service;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseProfitCenterLgapDTO;

import java.util.List;

/**
 * ProfitCenter Table from LGAPService接口
 * 
 * @author lovefamily
 * @date 2023-07-01
 */
public interface IBizBaseProfitCenterLgapService
{
    /**
     * 查询ProfitCenter Table from LGAP
     * 
     * @param id ProfitCenter Table from LGAP主键
     * @return ProfitCenter Table from LGAPDTO
     */
    public BizBaseProfitCenterLgapDTO selectBizBaseProfitCenterLgapById(Long id);

    /**
     * 查询ProfitCenter Table from LGAP列表
     *
     * @param bizBaseProfitCenterLgap ProfitCenter Table from LGAP
     * @return ProfitCenter Table from LGAPDTO集合
     */
    public List<BizBaseProfitCenterLgapDTO> selectBizBaseProfitCenterLgapList(BizBaseProfitCenterLgapDTO bizBaseProfitCenterLgap);

    /**
     * 新增ProfitCenter Table from LGAP
     * 
     * @param bizBaseProfitCenterLgapDTO ProfitCenter Table from LGAP
     * @return 结果
     */
    public int insertBizBaseProfitCenterLgap(BizBaseProfitCenterLgapDTO bizBaseProfitCenterLgap);

    /**
     * 修改ProfitCenter Table from LGAP
     * 
     * @param bizBaseProfitCenterLgapDTO ProfitCenter Table from LGAP
     * @return 结果
     */
    public int updateBizBaseProfitCenterLgap(BizBaseProfitCenterLgapDTO bizBaseProfitCenterLgap);

    /**
     * 批量删除ProfitCenter Table from LGAP
     * 
     * @param ids 需要删除的ProfitCenter Table from LGAP主键集合
     * @return 结果
     */
    public int deleteBizBaseProfitCenterLgapByIds(Long[] ids);

    /**
     * 删除ProfitCenter Table from LGAP信息
     * 
     * @param id ProfitCenter Table from LGAP主键
     * @return 结果
     */
    public int deleteBizBaseProfitCenterLgapById(Long id);

    /**
     * 校验ProfitCenter Code是否存在
     * @param profitCenterCode
     * @return
     */
    public ResultDTO<Object> checkProfitCenterCode(String profitCenterCode);
}
