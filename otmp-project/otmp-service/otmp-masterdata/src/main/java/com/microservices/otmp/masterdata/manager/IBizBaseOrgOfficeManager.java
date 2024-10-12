package com.microservices.otmp.masterdata.manager;

import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseTerritoryRelation;
import com.microservices.otmp.masterdata.domain.dto.BizBaseOrgOfficeDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseOrgOfficeDO;
import com.microservices.otmp.masterdata.domain.entity.dto.BizBaseOrgOfficeNameAndCodeDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.BizBaseOrgOfficeTreeDTO;

import java.util.HashSet;
import java.util.List;


/**
 * IBizBaseOrgOfficeManager Manager接口
 * 
 * @author lovefamily
 * @date 2022-12-23
 */
public interface IBizBaseOrgOfficeManager
{
    /**
     * 查询bizBaseOrgOffice
     * 
     * @param id bizBaseOrgOffice主键
     * @return bizBaseOrgOffice
     */
    public BizBaseOrgOfficeDO selectBizBaseOrgOfficeById(Long id);

    /**
     * 查询bizBaseOrgOffice列表
     *
     * @param bizBaseOrgOffice bizBaseOrgOffice
     * @return bizBaseOrgOffice集合
     */
    public List<BizBaseOrgOfficeDO> selectBizBaseOrgOfficeList(BizBaseOrgOfficeDTO bizBaseOrgOffice);

    List<BizBaseOrgOfficeDO> selectBizBaseOrgAndOffice(BizBaseOrgOfficeDTO bizBaseOrgOffice);

    /**
     * 新增bizBaseOrgOffice
     *
     * @param bizBaseOrgOffice bizBaseOrgOffice
     * @return 结果
     */
    public int insertBizBaseOrgOffice(BizBaseOrgOfficeDO bizBaseOrgOffice);

    /**
     * 修改bizBaseOrgOffice
     *
     * @param bizBaseOrgOffice bizBaseOrgOffice
     * @return 结果
     */
    public int updateBizBaseOrgOffice(BizBaseOrgOfficeDO bizBaseOrgOffice);

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
    List<BizBaseTerritoryRelation> getTerritoryDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition);

    List<BizBaseOrgOfficeDO> getDropDownList(BizBaseOrgOfficeDTO bizBaseOrgOffice);

    /**
     * 查询bizBaseOrgOffice树形列表
     *
     * @param bizBaseOrgOffice bizBaseOrgOffice
     * @return bizBaseOrgOfficeDTO集合
     */
    List<BizBaseOrgOfficeDO> selectBizBaseOrgOfficeTreeList(BizBaseOrgOfficeDTO bizBaseOrgOffice);

    /**
     * 校验SalesOrg层级
     * @param bizBaseOrgOffice
     * @return
     */
    BizBaseOrgOfficeDO checkSalesOrg(BizBaseOrgOfficeTreeDTO bizBaseOrgOffice);

    /**
     * 校验SalesOffice层级
     * @param bizBaseOrgOfficeDO
     * @return
     */
    BizBaseOrgOfficeDO checkSalesOffice(BizBaseOrgOfficeDO bizBaseOrgOfficeDO);

    /**
     * 查询当前sales org的所有id set集合
     * @param bizBaseOrgOffice
     * @return
     */
    HashSet<Long> getIdsBySalesOrg(BizBaseOrgOfficeTreeDTO bizBaseOrgOffice);

    /**
     * 通过salesOrgCode和salesOfficeCode获取regionMarketCode
     * @param salesOrgCode
     * @param salesOfficeCode
     * @return
     */
    BizBaseOrgOfficeDO getByOrgOffice(String salesOrgCode, String salesOfficeCode);

    /**
     * 通过salesOrgCode获取accrualCompanyCode
     * @param salesOrgCode
     * @param businessGroup
     * @return
     */
    BizBaseOrgOfficeDO getOrgOfficeByOrgAndBg(String salesOrgCode, String businessGroup);
}
