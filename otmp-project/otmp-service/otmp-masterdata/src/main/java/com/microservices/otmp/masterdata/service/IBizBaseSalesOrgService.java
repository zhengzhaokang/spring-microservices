package com.microservices.otmp.masterdata.service;

import java.util.List;

import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseSalesOrg;
import com.microservices.otmp.masterdata.domain.entity.dto.ToMsCountryDTO;
import com.microservices.otmp.masterdata.domain.entity.vo.TpMsCountryVo;

/**
 * BizBaseSalesOrgService接口
 * 
 * @author lovefamily
 * @date 2022-04-22
 */
public interface IBizBaseSalesOrgService
{
    /**
     * 查询BizBaseSalesOrg
     * 
     * @param id BizBaseSalesOrg主键
     * @return BizBaseSalesOrg
     */
    public BizBaseSalesOrg selectBizBaseSalesOrgById(Long id);

    /**
     * 查询BizBaseSalesOrg列表
     * 
     * @param bizBaseSalesOrg BizBaseSalesOrg
     * @return BizBaseSalesOrg集合
     */
    public List<BizBaseSalesOrg> selectBizBaseSalesOrgList(BizBaseSalesOrg bizBaseSalesOrg);

    /**
     * 查询下拉框
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseSalesOrg集合
     */
    public List<BizBaseSalesOrg> getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition);

    public BizBaseSalesOrg  getOne(BizBaseDropDownCondition bizBaseDropDownCondition);
    /**
     * 新增BizBaseSalesOrg
     * 
     * @param bizBaseSalesOrg BizBaseSalesOrg
     * @return 结果
     */
    public int insertBizBaseSalesOrg(BizBaseSalesOrg bizBaseSalesOrg);

    /**
     * 修改BizBaseSalesOrg
     * 
     * @param bizBaseSalesOrg BizBaseSalesOrg
     * @return 结果
     */
    public int updateBizBaseSalesOrg(BizBaseSalesOrg bizBaseSalesOrg);

    /**
     * 批量删除BizBaseSalesOrg
     * 
     * @param ids 需要删除的BizBaseSalesOrg主键集合
     * @return 结果
     */
    public int deleteBizBaseSalesOrgByIds(Long[] ids);

    /**
     * 删除BizBaseSalesOrg信息
     * 
     * @param id BizBaseSalesOrg主键
     * @return 结果
     */
    public int deleteBizBaseSalesOrgById(Long id);

    public String importExcel(List<BizBaseSalesOrg> bizs, String loginName);

    /**
     *  下拉
     * @return
     */
    List<BizBaseSalesOrg> localCurrencyList();

}
