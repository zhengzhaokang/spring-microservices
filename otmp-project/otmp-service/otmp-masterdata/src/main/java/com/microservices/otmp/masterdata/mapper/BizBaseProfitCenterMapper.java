package com.microservices.otmp.masterdata.mapper;

import java.util.List;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseProfitCenter;

/**
 * BaseProfitCenterMapper接口
 * 
 * @author lovefamily
 * @date 2022-04-22
 */
public interface BizBaseProfitCenterMapper
{
    /**
     * 查询BaseProfitCenter
     * 
     * @param id BaseProfitCenter主键
     * @return BaseProfitCenter
     */
    public BizBaseProfitCenter selectBizBaseProfitCenterById(Long id);

    /**
     * 查询BaseProfitCenter列表
     * 
     * @param bizBaseProfitCenter BaseProfitCenter
     * @return BaseProfitCenter集合
     */
    @DataPermissions(tableName = "biz_base_profit_center")
    public List<BizBaseProfitCenter> selectBizBaseProfitCenterList(BizBaseProfitCenter bizBaseProfitCenter);
    @DataPermissions(tableName = "biz_base_profit_center")
    public List<BizBaseProfitCenter> selectProfitCenterListPrecise(BizBaseProfitCenter bizBaseProfitCenter);

    @DataPermissions(tableName = "biz_base_profit_center")
    public List<BizBaseProfitCenter> selectProfitCenterlist(BizBaseProfitCenter bizBaseProfitCenter);

    /**
     * 新增BaseProfitCenter
     * 
     * @param bizBaseProfitCenter BaseProfitCenter
     * @return 结果
     */
    public int insertBizBaseProfitCenter(BizBaseProfitCenter bizBaseProfitCenter);

    /**
     * 修改BaseProfitCenter
     * 
     * @param bizBaseProfitCenter BaseProfitCenter
     * @return 结果
     */
    public int updateBizBaseProfitCenter(BizBaseProfitCenter bizBaseProfitCenter);

    /**
     * 删除BaseProfitCenter
     * 
     * @param id BaseProfitCenter主键
     * @return 结果
     */
    public int deleteBizBaseProfitCenterById(Long id);

    /**
     * 批量删除BaseProfitCenter
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseProfitCenterByIds(Long[] ids);

    /**
     * 查询BaseProfitCenter
     *
     * @param  bizBaseProfitCenter 通过主键校验
     * @return 结果
     */
    public List<BizBaseProfitCenter> selectBizBaseProfitCenterListCheck(BizBaseProfitCenter bizBaseProfitCenter);

    int updateBizBaseProfitCenterByIds(Long[] ids);

    /**
     * 下拉查询 profit_center_code
     * @param bizBaseProfitCenter
     * @return
     */
    @DataPermissions(tableName = "biz_base_profit_center")
    List<BizBaseProfitCenter> getDropDownList(BizBaseProfitCenter bizBaseProfitCenter);

    /**
     * 下拉查询 dummy_gtn_mtm
     * @param bizBaseProfitCenter
     * @return
     */
    @DataPermissions(tableName = "biz_base_profit_center")
    List<BizBaseProfitCenter> getDropDownListByMtm(BizBaseProfitCenter bizBaseProfitCenter);

    /**
     * 下拉查询 bwbu
     * @param bizBaseProfitCenter
     * @return
     */
    @DataPermissions(tableName = "biz_base_profit_center")
    List<BizBaseProfitCenter> getDropDownListByBwbu(BizBaseProfitCenter bizBaseProfitCenter);
}
