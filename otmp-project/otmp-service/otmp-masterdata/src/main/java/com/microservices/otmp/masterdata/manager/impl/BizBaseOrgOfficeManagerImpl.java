package com.microservices.otmp.masterdata.manager.impl;

import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseTerritoryRelation;
import com.microservices.otmp.masterdata.domain.entity.BizBaseOrgOfficeDO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseOrgOfficeDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.BizBaseOrgOfficeNameAndCodeDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.BizBaseOrgOfficeTreeDTO;
import com.microservices.otmp.masterdata.manager.IBizBaseOrgOfficeManager;
import com.microservices.otmp.masterdata.mapper.BizBaseOrgOfficeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/**
 * BizBaseOrgOfficeManagerImpl Manager业务层处理
 * 
 * @author lovefamily
 * @date 2022-12-23
 */
@Service
public class BizBaseOrgOfficeManagerImpl implements IBizBaseOrgOfficeManager
{
    private static final Logger log = LoggerFactory.getLogger(BizBaseOrgOfficeManagerImpl.class);

    @Autowired
    private BizBaseOrgOfficeMapper bizBaseOrgOfficeMapper;

    /**
     * 查询 bizBaseOrgOffice
     * 
     * @param id bizBaseOrgOffice主键
     * @return bizBaseOrgOfficeDO
     */
    @Override
    public BizBaseOrgOfficeDO selectBizBaseOrgOfficeById(Long id)
    {
        return bizBaseOrgOfficeMapper.selectBizBaseOrgOfficeById(id);
    }

    /**
     * 查询bizBaseOrgOffice列表
     *
     * @param bizBaseOrgOffice bizBaseOrgOffice
     * @return bizBaseOrgOfficeDO
     */
    @Override
    public List<BizBaseOrgOfficeDO> selectBizBaseOrgOfficeList(BizBaseOrgOfficeDTO bizBaseOrgOffice)
    {
        return bizBaseOrgOfficeMapper.selectBizBaseOrgOfficeList(bizBaseOrgOffice);
    }

    @Override
    public List<BizBaseOrgOfficeDO> selectBizBaseOrgAndOffice(BizBaseOrgOfficeDTO bizBaseOrgOffice) {
        return bizBaseOrgOfficeMapper.selectBizBaseOrgAndOffice(bizBaseOrgOffice);
    }

    /**
     * 新增bizBaseOrgOffice
     *
     * @param bizBaseOrgOffice bizBaseOrgOffice
     * @return 结果
     */
    @Override
    public int insertBizBaseOrgOffice(BizBaseOrgOfficeDO bizBaseOrgOffice)
    {
        return bizBaseOrgOfficeMapper.insertBizBaseOrgOffice (bizBaseOrgOffice);
    }

    /**
     * 修改bizBaseOrgOffice
     *
     * @param bizBaseOrgOffice  bizBaseOrgOffice
     * @return 结果
     */
    @Override
    public int updateBizBaseOrgOffice(BizBaseOrgOfficeDO bizBaseOrgOffice)
    {
        return bizBaseOrgOfficeMapper.updateBizBaseOrgOffice (bizBaseOrgOffice);
    }

    /**
     * 批量删除bizBaseOrgOffice
     * 
     * @param ids 需要删除的bizBaseOrgOffice主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseOrgOfficeByIds(Long[] ids)
    {
        return bizBaseOrgOfficeMapper.deleteBizBaseOrgOfficeByIds(ids);
    }

    /**
     * 删除bizBaseOrgOffice信息
     * 
     * @param id bizBaseOrgOffice主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseOrgOfficeById(Long id)
    {
        return bizBaseOrgOfficeMapper.deleteBizBaseOrgOfficeById(id);
    }

    /**
     * 查询name和code
     * @param type regionMarketCode，territoryCode，countryCode，bpcCountryCode，salesOrgCode，salesOfficeCode，distributionChannelCode
     * @param code code
     * @return
     */
    @Override
    public List<BizBaseOrgOfficeNameAndCodeDTO> getCodeAndName(String type, String code) {
        log.info("查询name和code入参:type={},code={}", type, code);
        return bizBaseOrgOfficeMapper.getCodeAndName(type, code);
    }

    /**
     * 查询下拉框查询territoryCode
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseTerritoryRelation集合
     */
    @Override
    public List<BizBaseTerritoryRelation> getTerritoryDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition) {
        return bizBaseOrgOfficeMapper.getTerritoryDropDownList(bizBaseDropDownCondition);
    }

    @Override
    public List<BizBaseOrgOfficeDO> getDropDownList(BizBaseOrgOfficeDTO bizBaseOrgOffice) {
        return bizBaseOrgOfficeMapper.getDropDownList(bizBaseOrgOffice);
    }

    /**
     * 查询bizBaseOrgOffice树形列表
     *
     * @param bizBaseOrgOffice bizBaseOrgOffice
     * @return bizBaseOrgOfficeDTO集合
     */
    @Override
    public List<BizBaseOrgOfficeDO> selectBizBaseOrgOfficeTreeList(BizBaseOrgOfficeDTO bizBaseOrgOffice) {
        return bizBaseOrgOfficeMapper.selectBizBaseOrgOfficeTreeList(bizBaseOrgOffice);
    }

    @Override
    public BizBaseOrgOfficeDO checkSalesOrg(BizBaseOrgOfficeTreeDTO bizBaseOrgOffice) {
        return bizBaseOrgOfficeMapper.checkSalesOrg(bizBaseOrgOffice);
    }

    @Override
    public BizBaseOrgOfficeDO checkSalesOffice(BizBaseOrgOfficeDO bizBaseOrgOfficeDO) {
        return bizBaseOrgOfficeMapper.checkSalesOffice(bizBaseOrgOfficeDO);
    }

    /**
     * 查询当前sales org的所有id set集合
     * @param bizBaseOrgOffice
     * @return
     */
    @Override
    public HashSet<Long> getIdsBySalesOrg(BizBaseOrgOfficeTreeDTO bizBaseOrgOffice) {
        return bizBaseOrgOfficeMapper.getIdsBySalesOrg(bizBaseOrgOffice);
    }

    /**
     * 通过salesOrgCode和salesOfficeCode获取regionMarketCode
     * @param salesOrgCode
     * @param salesOfficeCode
     * @return
     */
    @Override
    public BizBaseOrgOfficeDO getByOrgOffice(String salesOrgCode, String salesOfficeCode) {
        return bizBaseOrgOfficeMapper.getByOrgOffice(salesOrgCode, salesOfficeCode);
    }

    /**
     * 通过salesOrgCode获取accrualCompanyCode
     * @param businessGroup
     * @param businessGroup
     * @return
     */
    @Override
    public BizBaseOrgOfficeDO getOrgOfficeByOrgAndBg(String salesOrgCode, String businessGroup) {
        return bizBaseOrgOfficeMapper.getOrgOfficeByOrgAndBg(salesOrgCode, businessGroup);
    }

}
