package com.microservices.otmp.masterdata.service;

import java.util.List;
import com.microservices.otmp.masterdata.domain.BizBaseProfitCenter;
import com.microservices.otmp.masterdata.domain.BizBaseProfitCenter;

/**
 * BaseProfitCenterService接口
 * 
 * @author lovefamily
 * @date 2022-04-22
 */
public interface IBizBaseProfitCenterService
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
    public List<BizBaseProfitCenter> selectBizBaseProfitCenterList(BizBaseProfitCenter bizBaseProfitCenter);
    public List<BizBaseProfitCenter> selectProfitCenterListPrecise(BizBaseProfitCenter bizBaseProfitCenter);

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
     * 批量删除BaseProfitCenter
     * 
     * @param ids 需要删除的BaseProfitCenter主键集合
     * @return 结果
     */
    public int deleteBizBaseProfitCenterByIds(Long[] ids);

    /**
     * 删除BaseProfitCenter信息
     * 
     * @param id BaseProfitCenter主键
     * @return 结果
     */
    public int deleteBizBaseProfitCenterById(Long id);

    /**
     * 查询BaseProfitCenter
     *
     * @param  bizs 通过主键校验
     * @return 结果
     */
    public String importExcel(List<BizBaseProfitCenter> bizs, String loginName);

    List<BizBaseProfitCenter> getDropDownList(BizBaseProfitCenter bizBaseProfitCenter);
}
