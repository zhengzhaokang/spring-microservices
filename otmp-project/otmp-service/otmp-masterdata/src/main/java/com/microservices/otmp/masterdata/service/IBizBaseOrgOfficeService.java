package com.microservices.otmp.masterdata.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseTerritoryRelation;
import com.microservices.otmp.masterdata.domain.dto.BizBaseOrgOfficeDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.BizBaseOrgOfficeNameAndCodeDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.BizBaseOrgOfficeTreeDTO;

import java.util.List;

/**
 * IBizBaseOrgOfficeService Service接口
 * 
 * @author lovefamily
 * @date 2022-12-23
 */
public interface IBizBaseOrgOfficeService
{
    /**
     * 查询bizBaseOrgOffice
     * 
     * @param id bizBaseOrgOffice主键
     * @return bizBaseOrgOfficeDTO
     */
    public BizBaseOrgOfficeTreeDTO selectBizBaseOrgOfficeById(Long id);

    /**
     * 查询bizBaseOrgOffice列表
     *
     * @param bizBaseOrgOffice bizBaseOrgOffice
     * @return bizBaseOrgOfficeDTO集合
     */
    public List<BizBaseOrgOfficeDTO> selectBizBaseOrgOfficeList(BizBaseOrgOfficeDTO bizBaseOrgOffice);

    /**
     * 新增bizBaseOrgOffice
     * 
     * @param bizBaseOrgOffice bizBaseOrgOffice
     * @return 结果
     */
    public ResultDTO<Integer> insertBizBaseOrgOffice(BizBaseOrgOfficeTreeDTO bizBaseOrgOffice, String loginName);

    /**
     * 修改bizBaseOrgOffice
     * 
     * @param bizBaseOrgOffice bizBaseOrgOffice
     * @return 结果
     */
    public ResultDTO<Integer> updateBizBaseOrgOffice(BizBaseOrgOfficeTreeDTO bizBaseOrgOffice, String loginName);

    /**
     * 批量删除bizBaseOrgOffice
     * 
     * @param ids 需要删除的bizBaseOrgOffice主键集合
     * @return 结果
     */
    public int deleteBizBaseOrgOfficeByIds(Long[] ids);

    /**
     * 删除bizBaseOrgOffice信息
     * 
     * @param id bizBaseOrgOffice主键
     * @return 结果
     */
    public int deleteBizBaseOrgOfficeById(Long id);

    /**
     * 导入
     * @param bizs
     * @param loginName
     * @return
     */
    public ResultDTO<String> importExcel(List<BizBaseOrgOfficeDTO> bizs, String loginName);

    /**
     * 查询name和code
     * @param type regionMarketCode，territoryCode，countryCode，bpcCountryCode，salesOrgCode，salesOfficeCode，distributionChannelCode
     * @param code code
     * @return
     */
    List<BizBaseOrgOfficeNameAndCodeDTO> getCodeAndName(String type, String code);

    /**
     * 查询下拉框查询territoryCode
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseTerritoryRelation集合
     */
    public List<BizBaseTerritoryRelation> getTerritoryDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition);

    public PageInfo<BizBaseOrgOfficeDTO> getDropDownList(BizBaseOrgOfficeDTO bizBaseOrgOffice);

    /**
     * 查询bizBaseOrgOffice树形列表
     *
     * @param bizBaseOrgOffice bizBaseOrgOffice
     * @return bizBaseOrgOfficeDTO集合
     */
    public PageInfo<BizBaseOrgOfficeTreeDTO> selectBizBaseOrgOfficeTreeList(BizBaseOrgOfficeDTO bizBaseOrgOffice);

    /**
     * 通过salesOrgCode和salesOfficeCode获取regionMarketCode
     * @param salesOrgCode
     * @param salesOfficeCode
     * @return
     */
    public BizBaseOrgOfficeDTO getByOrgOffice(String salesOrgCode, String salesOfficeCode);

    /**
     * 通过salesOrgCode获取accrualCompanyCode
     * @param salesOrgCode
     * @param businessGroup
     * @return
     */
    public BizBaseOrgOfficeDTO getOrgOfficeByOrgAndBg(String salesOrgCode, String businessGroup);

    BizBaseOrgOfficeDTO getOrgAndOffice(BizBaseOrgOfficeDTO bizBaseOrgOffice);
}
