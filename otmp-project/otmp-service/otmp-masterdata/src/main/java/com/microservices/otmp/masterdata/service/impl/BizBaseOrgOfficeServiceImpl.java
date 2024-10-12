package com.microservices.otmp.masterdata.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.enums.BooleanStatusEnum;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseTerritoryRelation;
import com.microservices.otmp.masterdata.domain.dto.BizBaseOrgOfficeDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseOrgOfficeDO;
import com.microservices.otmp.masterdata.domain.entity.dto.BizBaseOrgOfficeNameAndCodeDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.BizBaseOrgOfficeTreeDTO;
import com.microservices.otmp.masterdata.manager.IBizBaseOrgOfficeManager;
import com.microservices.otmp.masterdata.service.IBizBaseOrgOfficeService;
import com.microservices.otmp.masterdata.util.ConvertStringToLongListUtil;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.enums.BooleanStatusEnum;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseTerritoryRelation;
import com.microservices.otmp.masterdata.domain.dto.BizBaseOrgOfficeDTO;
import com.microservices.otmp.masterdata.util.ConvertStringToLongListUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * BizBaseOrgOfficeServiceImpl Service业务层处理
 *
 * @author lovefamily
 * @date 2022-12-23
 */
@Service
public class BizBaseOrgOfficeServiceImpl implements IBizBaseOrgOfficeService {
    private static final Logger log = LoggerFactory.getLogger(BizBaseOrgOfficeServiceImpl.class);

    @Autowired
    private IBizBaseOrgOfficeManager bizBaseOrgOfficeManager;

    /**
     * 查询bizBaseOrgOffice
     *
     * @param id bizBaseOrgOffice主键
     * @return bizBaseOrgOfficeDTO
     */
    @Override
    public BizBaseOrgOfficeTreeDTO selectBizBaseOrgOfficeById(Long id) {
        BizBaseOrgOfficeDO bizBaseOrgOfficeDO = bizBaseOrgOfficeManager.selectBizBaseOrgOfficeById(id);
        BizBaseOrgOfficeTreeDTO bizBaseOrgOfficeTreeDTO = new BizBaseOrgOfficeTreeDTO();
        BeanUtils.copyProperties(bizBaseOrgOfficeDO, bizBaseOrgOfficeTreeDTO);
        BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO = new BizBaseOrgOfficeDTO();
        BeanUtils.copyProperties(bizBaseOrgOfficeDO, bizBaseOrgOfficeDTO);
        List<BizBaseOrgOfficeDO> dos = bizBaseOrgOfficeManager.selectBizBaseOrgOfficeList(bizBaseOrgOfficeDTO);
        List<BizBaseOrgOfficeDTO> list = new ArrayList<>(dos.size());
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(dos, list, BizBaseOrgOfficeDTO.class);
        bizBaseOrgOfficeTreeDTO.setChildren(list);
        return bizBaseOrgOfficeTreeDTO;
    }

    /**
     * 查询bizBaseOrgOffice列表
     *
     * @param bizBaseOrgOffice bizBaseOrgOffice
     * @return bizBaseOrgOfficeDTO
     */
    @Override
    public List<BizBaseOrgOfficeDTO> selectBizBaseOrgOfficeList(BizBaseOrgOfficeDTO bizBaseOrgOffice) {
        List<Long> longIdsList = ConvertStringToLongListUtil.splitToLongList(bizBaseOrgOffice.getIds());
        bizBaseOrgOffice.setIdsList(longIdsList);
        List<BizBaseOrgOfficeDO> bizBaseOrgOfficeDOS =
                bizBaseOrgOfficeManager.selectBizBaseOrgOfficeList(bizBaseOrgOffice);
        List<BizBaseOrgOfficeDTO> bizBaseOrgOfficeList = new ArrayList<>(bizBaseOrgOfficeDOS.size());
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(bizBaseOrgOfficeDOS, bizBaseOrgOfficeList, BizBaseOrgOfficeDTO.class);
        return bizBaseOrgOfficeList;

    }

    /**
     * 新增bizBaseOrgOffice
     *
     * @param bizBaseOrgOffice bizBaseOrgOffice
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    @CacheEvict(value = "bizBaseOrgOffice", allEntries = true)
    public ResultDTO<Integer> insertBizBaseOrgOffice(BizBaseOrgOfficeTreeDTO bizBaseOrgOffice, String loginName) {
        //sales org唯一性校验，如果填写的business group, geo, region/market code, territory code, sales org code在系统中已存在，但是其他sales org层级字段与现有数据不一致
        BizBaseOrgOfficeDO countSalesOrg = bizBaseOrgOfficeManager.checkSalesOrg(bizBaseOrgOffice);
        if (countSalesOrg != null) {
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.SALES_ORG_EXSIT), DefaultErrorMessage.SALES_ORG_EXSIT.intValue());
        }
        Date date = DateUtils.getNowDate();
        List<BizBaseOrgOfficeDTO> list = bizBaseOrgOffice.getChildren();
        BizBaseOrgOfficeDO bizBaseOrgOfficeDO = new BizBaseOrgOfficeDO();
        for (BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO : list) {
            bizBaseOrgOfficeDTO.setBusinessGroup(bizBaseOrgOffice.getBusinessGroup());
            bizBaseOrgOfficeDTO.setGeoCode(bizBaseOrgOffice.getGeoCode());
            bizBaseOrgOfficeDTO.setRegionMarketCode(bizBaseOrgOffice.getRegionMarketCode());
            bizBaseOrgOfficeDTO.setRegionMarketName(bizBaseOrgOffice.getRegionMarketName());
            bizBaseOrgOfficeDTO.setTerritoryCode(bizBaseOrgOffice.getTerritoryCode());
            bizBaseOrgOfficeDTO.setTerritoryName(bizBaseOrgOffice.getTerritoryName());
            bizBaseOrgOfficeDTO.setSalesOrgCode(bizBaseOrgOffice.getSalesOrgCode());
            bizBaseOrgOfficeDTO.setSalesOrgName(bizBaseOrgOffice.getSalesOrgName());
            bizBaseOrgOfficeDTO.setIsDummy(bizBaseOrgOffice.getIsDummy());
            bizBaseOrgOfficeDTO.setLocalCurrencyCode(bizBaseOrgOffice.getLocalCurrencyCode());
            bizBaseOrgOfficeDTO.setCompanyCode(bizBaseOrgOffice.getCompanyCode());
            bizBaseOrgOfficeDTO.setAccrualCompanyCode(bizBaseOrgOffice.getAccrualCompanyCode());

            BeanUtils.copyProperties(bizBaseOrgOfficeDTO, bizBaseOrgOfficeDO);
            //id为空执行插入操作
            //sales office唯一性校验，如果填写的business group, geo, region/market code, territory code, sales org code，sales office code, country code在系统中已存在
            BizBaseOrgOfficeDO countSalesOffice = bizBaseOrgOfficeManager.checkSalesOffice(bizBaseOrgOfficeDO);
            if (countSalesOffice != null) {
                throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.SALES_OFFICE_EXSIT, bizBaseOrgOfficeDO.getSalesOfficeCode()), DefaultErrorMessage.SALES_OFFICE_EXSIT.intValue());
            }
            bizBaseOrgOfficeDO.setCreateBy(loginName);
            bizBaseOrgOfficeDO.setCreateTime(date);
            bizBaseOrgOfficeDO.setUpdateBy(loginName);
            bizBaseOrgOfficeDO.setUpdateTime(date);
            bizBaseOrgOfficeDO.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
            bizBaseOrgOfficeManager.insertBizBaseOrgOffice(bizBaseOrgOfficeDO);
        }
        return ResultDTO.success();
    }

    /**
     * 修改bizBaseOrgOffice
     *
     * @param bizBaseOrgOffice bizBaseOrgOffice
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    @CacheEvict(value = "bizBaseOrgOffice", allEntries = true)
    public ResultDTO<Integer> updateBizBaseOrgOffice(BizBaseOrgOfficeTreeDTO bizBaseOrgOffice, String loginName) {
        try {
            List<BizBaseOrgOfficeDTO> list = bizBaseOrgOffice.getChildren();
            if (CollectionUtils.isEmpty(list)) {
                throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.SALES_OFFICE_NOT_NULL), DefaultErrorMessage.SALES_OFFICE_NOT_NULL.intValue());
            }
            Date date = DateUtils.getNowDate();
            //查询当前sales org的所有id set集合,后续判断删除操作
            HashSet<Long> set = bizBaseOrgOfficeManager.getIdsBySalesOrg(bizBaseOrgOffice);
            BizBaseOrgOfficeDO bizBaseOrgOfficeDO = new BizBaseOrgOfficeDO();
            for (BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO : list) {
                bizBaseOrgOfficeDTO.setBusinessGroup(bizBaseOrgOffice.getBusinessGroup());
                bizBaseOrgOfficeDTO.setGeoCode(bizBaseOrgOffice.getGeoCode());
                bizBaseOrgOfficeDTO.setRegionMarketCode(bizBaseOrgOffice.getRegionMarketCode());
                bizBaseOrgOfficeDTO.setRegionMarketName(bizBaseOrgOffice.getRegionMarketName());
                bizBaseOrgOfficeDTO.setTerritoryCode(bizBaseOrgOffice.getTerritoryCode());
                bizBaseOrgOfficeDTO.setTerritoryName(bizBaseOrgOffice.getTerritoryName());
                bizBaseOrgOfficeDTO.setSalesOrgCode(bizBaseOrgOffice.getSalesOrgCode());
                bizBaseOrgOfficeDTO.setSalesOrgName(bizBaseOrgOffice.getSalesOrgName());
                bizBaseOrgOfficeDTO.setIsDummy(bizBaseOrgOffice.getIsDummy());
                bizBaseOrgOfficeDTO.setLocalCurrencyCode(bizBaseOrgOffice.getLocalCurrencyCode());
                bizBaseOrgOfficeDTO.setCompanyCode(bizBaseOrgOffice.getCompanyCode());
                bizBaseOrgOfficeDTO.setAccrualCompanyCode(bizBaseOrgOffice.getAccrualCompanyCode());

                BeanUtils.copyProperties(bizBaseOrgOfficeDTO, bizBaseOrgOfficeDO);
                bizBaseOrgOfficeDO.setUpdateBy(loginName);
                bizBaseOrgOfficeDO.setUpdateTime(date);
                if (bizBaseOrgOfficeDO.getId() == null) {
                    //id为空执行插入操作
                    //sales office唯一性校验，如果填写的business group, geo, region/market code, territory code, sales org code，sales office code, country code在系统中已存在
                    BizBaseOrgOfficeDO countSalesOffice = bizBaseOrgOfficeManager.checkSalesOffice(bizBaseOrgOfficeDO);
                    if (countSalesOffice != null) {
                        throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.SALES_OFFICE_EXSIT, bizBaseOrgOfficeDO.getSalesOfficeCode()), DefaultErrorMessage.SALES_OFFICE_EXSIT.intValue());
                    }
                    bizBaseOrgOfficeDO.setCreateBy(loginName);
                    bizBaseOrgOfficeDO.setCreateTime(date);
                    bizBaseOrgOfficeDO.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
                    bizBaseOrgOfficeManager.insertBizBaseOrgOffice(bizBaseOrgOfficeDO);
                } else {
                    //id不为空执行更新操作
                    //若查询出来不为空,且id与当前id不同则表示重复
                    BizBaseOrgOfficeDO countSalesOffice = bizBaseOrgOfficeManager.checkSalesOffice(bizBaseOrgOfficeDO);
                    if (countSalesOffice != null && !countSalesOffice.getId().equals(bizBaseOrgOfficeDO.getId())) {
                        throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.SALES_OFFICE_EXSIT, bizBaseOrgOfficeDO.getSalesOfficeCode()), DefaultErrorMessage.SALES_OFFICE_EXSIT.intValue());
                    }
                    bizBaseOrgOfficeManager.updateBizBaseOrgOffice(bizBaseOrgOfficeDO);
                    set.remove(bizBaseOrgOfficeDO.getId());
                }
            }
            //set不为空执行删除操作
            if (CollectionUtils.isNotEmpty(set)) {
                bizBaseOrgOfficeManager.deleteBizBaseOrgOfficeByIds(set.toArray(new Long[set.size()]));
            }
            return ResultDTO.success();
        } catch (BeansException e) {
            e.printStackTrace();
            return ResultDTO.error();
        }
    }

    /**
     * 批量删除bizBaseOrgOffice
     *
     * @param ids 需要删除的bizBaseOrgOffice主键
     * @return 结果
     */
    @Override
    @CacheEvict(value = "bizBaseOrgOffice", allEntries = true)
    public int deleteBizBaseOrgOfficeByIds(Long[] ids) {
        return bizBaseOrgOfficeManager.deleteBizBaseOrgOfficeByIds(ids);
    }

    /**
     * 删除bizBaseOrgOffice信息
     *
     * @param id bizBaseOrgOffice主键
     * @return 结果
     */
    @Override
    @CacheEvict(value = "bizBaseOrgOffice", allEntries = true)
    public int deleteBizBaseOrgOfficeById(Long id) {
        return bizBaseOrgOfficeManager.deleteBizBaseOrgOfficeById(id);
    }

    /**
     * 导入
     *
     * @param bizs
     * @param loginName
     * @return
     */
    @Override
    public ResultDTO importExcel(List<BizBaseOrgOfficeDTO> bizs, String loginName) {
        AtomicInteger successNum = new AtomicInteger();
        AtomicInteger failureNum = new AtomicInteger();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        Date date = DateUtils.getNowDate();
        HashSet<String> hashSet = new HashSet<>();

        if (CollectionUtils.isEmpty(bizs)) {
            throw new ServiceException("导入MasterData 数据不能为空！");
        }
        List<BizBaseOrgOfficeDO> dos = new ArrayList<>();
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(bizs, dos, BizBaseOrgOfficeDO.class);
        for (BizBaseOrgOfficeDO OrgOfficeDO : dos) {
            //Business Group	Geo	Region/Market	Territory	Country	BPC Country	Sales Org	Sales Office
            String check = "Business Grou" + OrgOfficeDO.getBusinessGroup() +
                    "Geo" + OrgOfficeDO.getGeoCode() +
                    "Region/Market" + OrgOfficeDO.getRegionMarketCode() +
                    "Territory" + OrgOfficeDO.getTerritoryCode() +
                    "Country" + OrgOfficeDO.getCountryCode() +
                    "BPC Country" + OrgOfficeDO.getBpcCountryCode() +
                    "Sales Org" + OrgOfficeDO.getSalesOrgCode() +
                    "Sales Office" + OrgOfficeDO.getSalesOfficeCode();
            if (!hashSet.add(check)) {
                //返回导入数据存在重复提示
                log.info("导入数据中存在重复数据:{}", check);
                return ResultDTO.fail("很抱歉，导入失败，导入数据中存在重复数据：" + check);
            }
            //校验数据库是否已经存在该数据
            BizBaseOrgOfficeDTO dto = new BizBaseOrgOfficeDTO();
            dto.setBusinessGroup(OrgOfficeDO.getBusinessGroup());
            dto.setGeoCode(OrgOfficeDO.getGeoCode());
            dto.setRegionMarketCode(OrgOfficeDO.getRegionMarketName());
            dto.setTerritoryCode(OrgOfficeDO.getTerritoryName());
            dto.setCountryCode(OrgOfficeDO.getCountryName());
            dto.setBpcCountryCode(OrgOfficeDO.getBpcCountryName());
            dto.setSalesOrgCode(OrgOfficeDO.getSalesOrgName());
            dto.setSalesOfficeCode(OrgOfficeDO.getSalesOfficeName());
            dto.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
            dto.setDeleteFlag(false);
            List<BizBaseOrgOfficeDO> list = bizBaseOrgOfficeManager.selectBizBaseOrgOfficeList(dto);
            if (CollectionUtils.isNotEmpty(list)) {
                //返回重复提示
                return ResultDTO.fail("Record exists already");
            }

        }
        for (BizBaseOrgOfficeDO bizBaseOrgOfficeDO : dos) {
            try {
                bizBaseOrgOfficeDO.setRegionMarketCode(bizBaseOrgOfficeDO.getCountryName());
                bizBaseOrgOfficeDO.setSalesOrgCode(bizBaseOrgOfficeDO.getSalesOrgName());
                bizBaseOrgOfficeDO.setSalesOfficeCode(bizBaseOrgOfficeDO.getSalesOfficeName());
                bizBaseOrgOfficeDO.setTerritoryCode(bizBaseOrgOfficeDO.getTerritoryName());
                bizBaseOrgOfficeDO.setCountryCode(bizBaseOrgOfficeDO.getCountryName());
                bizBaseOrgOfficeDO.setBpcCountryCode(bizBaseOrgOfficeDO.getBpcCountryName());
                bizBaseOrgOfficeDO.setDistributionChannelCode(bizBaseOrgOfficeDO.getDistributionChannelName());
                bizBaseOrgOfficeDO.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
                bizBaseOrgOfficeDO.setCreateTime(date);
                bizBaseOrgOfficeDO.setUpdateTime(date);
                bizBaseOrgOfficeDO.setCreateBy(loginName);
                bizBaseOrgOfficeDO.setUpdateBy(loginName);
                bizBaseOrgOfficeManager.insertBizBaseOrgOffice(bizBaseOrgOfficeDO);
                successNum.getAndIncrement();
            } catch (Exception e) {
                failureNum.getAndIncrement();
                String msg = "<br/>" + failureNum + "MasterData导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum.intValue() > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return ResultDTO.success(successMsg.toString());
    }

    /**
     * 查询name和code
     *
     * @param type regionMarketCode，territoryCode，countryCode，bpcCountryCode，salesOrgCode，salesOfficeCode，distributionChannelCode
     * @param code code
     * @return
     */
    @Override
    public List<BizBaseOrgOfficeNameAndCodeDTO> getCodeAndName(String type, String code) {
        log.info("BizBaseOrgOffice查询code和name入参:type{},code{}", type, code);
        List<BizBaseOrgOfficeNameAndCodeDTO> list = bizBaseOrgOfficeManager.getCodeAndName(type, code);
        //去重
        if (CollectionUtils.isNotEmpty(list)) {
            switch (type) {
                case "regionMarketCode":
                    list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getRegionMarketCode())).filter(CommonUtils.distinctByKey(BizBaseOrgOfficeNameAndCodeDTO::getRegionMarketCode)).collect(Collectors.toList());
                    break;
                case "regionMarketName":
                    list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getRegionMarketName())).filter(CommonUtils.distinctByKey(BizBaseOrgOfficeNameAndCodeDTO::getRegionMarketName)).collect(Collectors.toList());
                    break;
                case "territoryCode":
                    list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getTerritoryCode())).filter(CommonUtils.distinctByKey(BizBaseOrgOfficeNameAndCodeDTO::getTerritoryCode)).collect(Collectors.toList());
                    break;
                case "territoryName":
                    list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getTerritoryName())).filter(CommonUtils.distinctByKey(BizBaseOrgOfficeNameAndCodeDTO::getTerritoryName)).collect(Collectors.toList());
                    break;
                case "countryCode":
                    list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getCountryCode())).filter(CommonUtils.distinctByKey(BizBaseOrgOfficeNameAndCodeDTO::getCountryCode)).collect(Collectors.toList());
                    break;
                case "countryName":
                    list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getCountryName())).filter(CommonUtils.distinctByKey(BizBaseOrgOfficeNameAndCodeDTO::getCountryName)).collect(Collectors.toList());
                    break;
                case "bpcCountryCode":
                    list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getBpcCountryCode())).filter(CommonUtils.distinctByKey(BizBaseOrgOfficeNameAndCodeDTO::getBpcCountryCode)).collect(Collectors.toList());
                    break;
                case "bpcCountryName":
                    list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getBpcCountryName())).filter(CommonUtils.distinctByKey(BizBaseOrgOfficeNameAndCodeDTO::getBpcCountryName)).collect(Collectors.toList());
                    break;
                case "salesOrgCode":
                    list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getSalesOrgCode())).filter(CommonUtils.distinctByKey(BizBaseOrgOfficeNameAndCodeDTO::getSalesOrgCode)).collect(Collectors.toList());
                    break;
                case "salesOrgName":
                    list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getSalesOrgName())).filter(CommonUtils.distinctByKey(BizBaseOrgOfficeNameAndCodeDTO::getSalesOrgName)).collect(Collectors.toList());
                    break;
                case "salesOfficeCode":
                    list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getSalesOfficeCode())).filter(CommonUtils.distinctByKey(BizBaseOrgOfficeNameAndCodeDTO::getSalesOfficeCode)).collect(Collectors.toList());
                    break;
                case "salesOfficeName":
                    list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getSalesOfficeName())).filter(CommonUtils.distinctByKey(BizBaseOrgOfficeNameAndCodeDTO::getSalesOfficeName)).collect(Collectors.toList());
                    break;
                case "distributionChannelCode":
                    list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getDistributionChannelCode())).filter(CommonUtils.distinctByKey(BizBaseOrgOfficeNameAndCodeDTO::getDistributionChannelCode)).collect(Collectors.toList());
                    break;
                default:
                    list = list.stream().filter(t -> StringUtils.isNotEmpty(t.getRegionMarketCode())).filter(CommonUtils.distinctByKey(BizBaseOrgOfficeNameAndCodeDTO::getRegionMarketCode)).collect(Collectors.toList());
            }
        }
        return list;
    }

    /**
     * 查询下拉框查询territoryCode
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseTerritoryRelation集合
     */
    @Override
    public List<BizBaseTerritoryRelation> getTerritoryDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition) {
        return bizBaseOrgOfficeManager.getTerritoryDropDownList(bizBaseDropDownCondition);
    }

    @Override
    public PageInfo<BizBaseOrgOfficeDTO> getDropDownList(BizBaseOrgOfficeDTO bizBaseOrgOffice) {
        List<BizBaseOrgOfficeDO> list = bizBaseOrgOfficeManager.getDropDownList(bizBaseOrgOffice);
        PageInfo<BizBaseOrgOfficeDO> pageInfo = new PageInfo<>(list);
        List<BizBaseOrgOfficeDTO> dtos = new ArrayList<>();
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(list, dtos, BizBaseOrgOfficeDTO.class);
        PageInfo<BizBaseOrgOfficeDTO> resultPageInfo = new PageInfo<>(dtos);
        resultPageInfo.setTotal(pageInfo.getTotal());
        return resultPageInfo;
    }

    /**
     * 查询bizBaseOrgOffice树形列表
     *
     * @param bizBaseOrgOffice bizBaseOrgOffice
     * @return bizBaseOrgOfficeDTO集合
     */
    @Override
    public PageInfo<BizBaseOrgOfficeTreeDTO> selectBizBaseOrgOfficeTreeList(BizBaseOrgOfficeDTO bizBaseOrgOffice) {
        //获取外层数据
        List<Long> longIdsList = ConvertStringToLongListUtil.splitToLongList(bizBaseOrgOffice.getIds());
        bizBaseOrgOffice.setIdsList(longIdsList);
        List<BizBaseOrgOfficeDO> bizBaseOrgOfficeDOS = bizBaseOrgOfficeManager.selectBizBaseOrgOfficeTreeList(bizBaseOrgOffice);
        PageInfo<BizBaseOrgOfficeDO> pageInfo = new PageInfo<>(bizBaseOrgOfficeDOS);
        List<BizBaseOrgOfficeTreeDTO> bizBaseOrgOfficeList = new ArrayList<>(bizBaseOrgOfficeDOS.size());
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(bizBaseOrgOfficeDOS, bizBaseOrgOfficeList, BizBaseOrgOfficeTreeDTO.class);
        //获取第二层数据
        for (BizBaseOrgOfficeTreeDTO bizBaseOrgOfficeTreeDTO : bizBaseOrgOfficeList) {
            BizBaseOrgOfficeDTO dto = new BizBaseOrgOfficeDTO();
            BeanUtils.copyProperties(bizBaseOrgOfficeTreeDTO, dto);
            List<BizBaseOrgOfficeDO> dos = bizBaseOrgOfficeManager.selectBizBaseOrgOfficeList(dto);
            List<BizBaseOrgOfficeDTO> list = new ArrayList<>(dos.size());
            com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(dos, list, BizBaseOrgOfficeDTO.class);
            bizBaseOrgOfficeTreeDTO.setChildren(list);
        }
        PageInfo<BizBaseOrgOfficeTreeDTO> resultPageInfo = new PageInfo<>(bizBaseOrgOfficeList);
        resultPageInfo.setTotal(pageInfo.getTotal());
        return resultPageInfo;
    }

    /**
     * 通过salesOrgCode和salesOfficeCode获取regionMarketCode
     *
     * @param salesOrgCode
     * @param salesOfficeCode
     * @return
     */
    @Override
    @Cacheable(value = "bizBaseOrgOffice", key = "#salesOrgCode+'_'+#salesOfficeCode")
    public BizBaseOrgOfficeDTO getByOrgOffice(String salesOrgCode, String salesOfficeCode) {
        BizBaseOrgOfficeDO baseOrgOfficeDO = bizBaseOrgOfficeManager.getByOrgOffice(salesOrgCode, salesOfficeCode);
        BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO = new BizBaseOrgOfficeDTO();
        BeanUtils.copyProperties(baseOrgOfficeDO, bizBaseOrgOfficeDTO);
        return bizBaseOrgOfficeDTO;
    }

    /**
     * 通过salesOrgCode获取accrualCompanyCode
     *
     * @param salesOrgCode
     * @param businessGroup
     * @return
     */
    @Override
    public BizBaseOrgOfficeDTO getOrgOfficeByOrgAndBg(String salesOrgCode, String businessGroup) {
        BizBaseOrgOfficeDO baseOrgOfficeDO = bizBaseOrgOfficeManager.getOrgOfficeByOrgAndBg(salesOrgCode, businessGroup);
        if (Objects.isNull(baseOrgOfficeDO)) {
            return null;
        }
        BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO = new BizBaseOrgOfficeDTO();
        BeanUtils.copyProperties(baseOrgOfficeDO, bizBaseOrgOfficeDTO);
        return bizBaseOrgOfficeDTO;
    }

    @Override
    public BizBaseOrgOfficeDTO getOrgAndOffice(BizBaseOrgOfficeDTO bizBaseOrgOffice) {
        List<BizBaseOrgOfficeDO> bizBaseOrgOfficeDOS = bizBaseOrgOfficeManager.selectBizBaseOrgAndOffice(bizBaseOrgOffice);
        if (bizBaseOrgOfficeDOS.size() > 1) {
            throw new OtmpException("Querying multiple pieces of data. ");
        }
        BizBaseOrgOfficeDO bizBaseOrgOfficeDO = bizBaseOrgOfficeDOS.get(0);
        BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO = new BizBaseOrgOfficeDTO();
        BeanUtils.copyProperties(bizBaseOrgOfficeDO, bizBaseOrgOfficeDTO);
        return bizBaseOrgOfficeDTO;
    }
}
