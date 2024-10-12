package com.microservices.otmp.masterdata.mapper;

import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseTerritoryRelation;
import com.microservices.otmp.masterdata.domain.dto.BizBaseOrgOfficeDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseOrgOfficeDO;
import com.microservices.otmp.masterdata.domain.entity.dto.BizBaseOrgOfficeNameAndCodeDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.BizBaseOrgOfficeTreeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.HashSet;
import java.util.List;


/**
 * BizBaseOrgOfficeMapper Mapper接口
 * 
 * @author lovefamily
 * @date 2022-12-23
 */
public interface BizBaseOrgOfficeMapper
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public BizBaseOrgOfficeDO selectBizBaseOrgOfficeById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param bizBaseOrgOffice 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<BizBaseOrgOfficeDO> selectBizBaseOrgOfficeList(BizBaseOrgOfficeDTO bizBaseOrgOffice);
    public List<BizBaseOrgOfficeDO> selectBizBaseOrgAndOffice(BizBaseOrgOfficeDTO bizBaseOrgOffice);

    /**
     * 新增【请填写功能名称】
     * 
     * @param bizBaseOrgOffice 【请填写功能名称】
     * @return 结果
     */
    public int insertBizBaseOrgOffice(BizBaseOrgOfficeDO bizBaseOrgOffice);

    /**
     * 修改【请填写功能名称】
     * 
     * @param bizBaseOrgOffice 【请填写功能名称】
     * @return 结果
     */
    public int updateBizBaseOrgOffice (BizBaseOrgOfficeDO bizBaseOrgOffice);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteBizBaseOrgOfficeById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseOrgOfficeByIds(Long[] ids);

    /**
     * 查询code和name
     * @param type
     * @param code
     * @return
     */
    List<BizBaseOrgOfficeNameAndCodeDTO> getCodeAndName(@Param("type") String type, @Param("code") String code);

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
    BizBaseOrgOfficeDO getByOrgOffice(@Param("salesOrgCode") String salesOrgCode, @Param("salesOfficeCode") String salesOfficeCode);

    /**
     * 通过salesOrgCode获取accrualCompanyCode
     * @param salesOrgCode
     * @param businessGroup
     * @return
     */
    BizBaseOrgOfficeDO getOrgOfficeByOrgAndBg(@Param("salesOrgCode") String salesOrgCode, @Param("businessGroup") String businessGroup);
}
