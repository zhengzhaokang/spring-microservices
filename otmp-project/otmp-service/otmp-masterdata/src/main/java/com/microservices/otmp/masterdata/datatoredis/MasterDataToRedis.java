package com.microservices.otmp.masterdata.datatoredis;

import com.microservices.otmp.common.constant.MasterDataConstants;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.masterdata.domain.*;
import com.microservices.otmp.masterdata.domain.dto.BizBaseApcDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseBpcbuBpcSeriesDTO;
import com.microservices.otmp.masterdata.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MasterDataToRedis implements ApplicationListener<ContextStartedEvent> {

    @Autowired
    private IBizBaseRegionMarketService bizBaseRegionMarketService;
    @Autowired
    private IBizBaseTerritoryRelationService bizBaseTerritoryRelationService;
    @Autowired
    private IBizBaseCountryService bizBaseCountryService;
    @Autowired
    private IBizBaseCurrencyService currencyService;
    @Autowired
    private IBizBaseSalesOrgService bizBaseSalesOrgService;
    @Autowired
    private IBizBaseSalesOfficeService bizBaseSalesOfficeService;
    @Autowired
    private IBizBaseProfitCenterService bizBaseProfitCenterService;
    //BW BU      biz_base_bw_bu
    //BPC BU   biz_base_bpc_bu
    //biz_base_customer_group
    @Autowired
    private IBizBaseComBizService bizBaseComBizService;
    @Autowired
    private IBizBaseBpcbuBpcSeriesService bizBaseBpcbuBpcSeriesService;
    @Autowired
    private IBizBaseProductHierarchyByLevelService bizBaseProductHierarchyByLevelService;
    //APC Code	 biz_base_apc → apc_code
    //Product No	  biz_base_apc → mtm
    @Autowired
    private IBizBaseApcService bizBaseApcService;
    @Autowired
    private IBizBaseTosService bizBaseTosService;
    @Autowired
    private IBizBaseBpTypeCustomerService bizBaseBpTypeCustomerService;
    //Sold-To Party
    //Payer
    @Autowired
    private IBizBaseCustomerService bizBaseCustomerService;
    @Autowired
    private IBizBaseEndCustomerService bizBaseEndCustomerService;
    @Autowired
    private IBizBaseGtnTypeService bizBaseGtnTypeService;

    @Autowired
    private IBizBaseDropDownService bizBaseDropDownService;

    @Autowired
    private IBizBaseSegmentService bizBaseSegmentService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IBizBaseDcDivisionMappingService bizBaseDcDivisionMappingService;
    @Autowired
    IBizBaseVendorBankService bizBaseVendorBankService;

    @Override
    public void onApplicationEvent(ContextStartedEvent contextStartedEvent) {
        setMarketToRedis();
        setTerritoryToRedis();
        setCountryToRedis();
        setSalesOrgToRedis();
        setSalesOfficeToRedis();
        setProfitCenterToRedis();
        //D新增
        setBpTypeToRedis();
        setSegmentToRedis();
        setCurrencyToRedis();
        setDcDivisionMappingToRedis();
        BizBaseComBiz bizBaseComBiz = new BizBaseComBiz();
        setSingleToRedis(bizBaseComBiz, "biz_base_bw_bu", "bw_bu");
        setBpcBuToRedis("bpc_bu");
        setSingleToRedis(bizBaseComBiz, "biz_base_customer_group", "customer_group");
        setSingleToRedis(bizBaseComBiz,"biz_base_bu","base_bu");
        setBpcSeriesToRedis();
        List<BizBaseProductHierarchyByLevel> hierarchyByLevels = bizBaseProductHierarchyByLevelService.selectBizBaseProductHierarchyByLevelList(new BizBaseProductHierarchyByLevel());
        setPhLevelToRedis(hierarchyByLevels, 1, "ph_level1");
        setPhLevelToRedis(hierarchyByLevels, 3, "ph_level3");
        setPhLevelToRedis(hierarchyByLevels, 4, "ph_level4");
        setPhLevelToRedis(hierarchyByLevels, 2, "ph_level2");
        setPhLevelToRedis(hierarchyByLevels, 5, "ph_level5");
        setPhLevelToRedis(hierarchyByLevels, 6, "ph_level6");
        //APC Code	 biz_base_apc → apc_code
        //Product No	  biz_base_apc → mtm
        List<BizBaseApcDTO> bizBaseApcList = bizBaseApcService.selectBizBaseApcList(new BizBaseApcDTO());
        setApcToRedis(bizBaseApcList);
        setMtmToRedis(bizBaseApcList);
        setTosToRedis();
        // bizBaseTosService
        setPartnerTypeToRedis();
        //Sold-To Party
        //Payer
        List<BizBaseCustomer> bizBaseCustomers = bizBaseCustomerService.selectBizBaseCustomerList(new BizBaseCustomer());
        /*//D新增
        */
        setSoldToOrPayerToRedis(bizBaseCustomers, RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX + "sold_to");
        setSoldToOrPayerToRedis(bizBaseCustomers, RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX + "payer");
        List<BizBaseEndCustomer> bizBaseEndCustomers = bizBaseEndCustomerService.selectBizBaseEndCustomerList(new BizBaseEndCustomer());
        setEndCustomerIdToRedis(bizBaseEndCustomers);
        setEndCustomerNameToRedis(bizBaseEndCustomers);
        //D新增
        setGtnTypeToRedis();
        setBizBaseVendorBankToRedis();
    }

    public void setBizBaseVendorBankToRedis() {
        List<BizBaseVendorBank> list = bizBaseVendorBankService.selectBizBaseVendorBankList(new BizBaseVendorBank());
        if (null != list) {
            redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX + MasterDataConstants.BIZ_BASE_VENDOR_BRANK, list, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
        }

    }

    public void setDcDivisionMappingToRedis() {
        List<BizBaseDcDivisionMapping> list = bizBaseDcDivisionMappingService.selectBizBaseDcDivisionMappingList(new BizBaseDcDivisionMapping());
        if (null != list) {
            redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX + "biz_base_dc_division_mapping",list,RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
        }
    }
    public void setCurrencyToRedis() {
        List<BizBaseCurrency> list = currencyService.selectBizBaseCurrencyList(new BizBaseCurrency());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX+"biz_base_currency",list, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
        Map<String, Object> currencyPoints = list.stream().collect(Collectors.toMap(BizBaseCurrency::getCurrencyCode, BizBaseCurrency::getDecimals, (e1, e2) -> e1));
        redisUtils.putAll(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX+"check_currency_point",currencyPoints, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }
    public void setSegmentToRedis() {
        List<BizBaseSegment> bizBaseSegments = bizBaseSegmentService.selectBizBaseSegmentList(new BizBaseSegment());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX + "biz_segments", bizBaseSegments, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void setBpTypeToRedis() {
        List<String> bpTypes = bizBaseDropDownService.getBptype(new BizBaseDropDownCondition()).stream().map(BizBaseDropDownList::getLabel).collect(Collectors.toList());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX + "bp_type", bpTypes, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void setEndCustomerNameToRedis(List<BizBaseEndCustomer> bizBaseEndCustomers) {
        List<String> customerNames=  bizBaseEndCustomers.stream().map(BizBaseEndCustomer::getEndCustomerName).collect(Collectors.toList());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX+"end_customer_name",customerNames, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void setEndCustomerIdToRedis(List<BizBaseEndCustomer> bizBaseEndCustomers) {
        List<String> customerIds=  bizBaseEndCustomers.stream().map(BizBaseEndCustomer::getEndCustomerId).collect(Collectors.toList());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX+"end_customer_id",customerIds, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void setGtnTypeToRedis() {
        List<BizBaseGtnType> list = bizBaseGtnTypeService.getDropDownList(new BizBaseDropDownCondition());
        List<BizBaseGtnType> allList = bizBaseGtnTypeService.getAllList(new BizBaseDropDownCondition());
        List<String> bizBaseGtnTypes = list.stream().map(BizBaseGtnType::getGtnTypeCode).collect(Collectors.toList());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX+"gtn_type",bizBaseGtnTypes, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX+"biz_gtn_type",allList, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void setSoldToOrPayerToRedis(List<BizBaseCustomer> bizBaseCustomers, String s) {
        List<String> partyList = bizBaseCustomers.stream().map(BizBaseCustomer::getCustomerNumber).collect(Collectors.toList());
        redisUtils.delete(s);
        redisUtils.addAllList(RedisConstants.REDIS_EXPIRE_TIME_FOREVER, s, partyList);
    }

    public void setPartnerTypeToRedis() {
        List<String> bpTypeCustomers = bizBaseBpTypeCustomerService.selectBizBaseBpTypeCustomerList(new BizBaseBpTypeCustomer()).stream().map(BizBaseBpTypeCustomer::getBpType).collect(Collectors.toList());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX+"partner_type",bpTypeCustomers, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void setMtmToRedis(List<BizBaseApcDTO> bizBaseApcList) {
        List<String> mtmList = bizBaseApcList.stream().map(BizBaseApcDTO::getMtm).collect(Collectors.toList());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX+"mtm",mtmList, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void setApcToRedis(List<BizBaseApcDTO> bizBaseApcList) {
        List<String> apcCodeList = bizBaseApcList.stream().map(BizBaseApcDTO::getApcCode).collect(Collectors.toList());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX+"apc_code",apcCodeList, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void setTosToRedis() {
        List<String> bizBaseTos = bizBaseTosService.selectBizBaseTosList(new BizBaseTos()).stream().map(BizBaseTos::getMemberId).collect(Collectors.toList());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX+"tos",bizBaseTos, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void setPhLevelToRedis(List<BizBaseProductHierarchyByLevel> hierarchyByLevels, Integer s, String phLevel1) {
        List<String> byLevel1s = hierarchyByLevels.stream().filter(t -> t.getPhLevel().equals(s)).map(BizBaseProductHierarchyByLevel::getProductHierarchyCode).collect(Collectors.toList());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX + phLevel1, byLevel1s, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void setBpcSeriesToRedis() {
        List<BizBaseBpcbuBpcSeriesDTO> bizBaseBpcbuBpcSeriesDTOPageInfo = bizBaseBpcbuBpcSeriesService.selectBizBaseBpcbuBpcSeriesList(new BizBaseBpcbuBpcSeriesDTO()).getList();
        //biz_base_bpc_bu表删除,改用biz_base_bpcbu_bpc_series表
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX+"bpc_series",bizBaseBpcbuBpcSeriesDTOPageInfo, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void setSingleToRedis(BizBaseComBiz bizBaseComBiz, String tableName, String key) {
        //BW BU      biz_base_bw_bu
        //BPC BU   biz_base_bpc_bu
        //biz_base_customer_group
        bizBaseComBiz.setBizTable(tableName);
        List<String> bwBuList = bizBaseComBizService.selectBizBaseComBizList(bizBaseComBiz).stream().map(BizBaseComBiz::getBizCode).collect(Collectors.toList());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX + key, bwBuList, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }
    public void setBpcBuToRedis( String key) {
        //BW BU      biz_base_bw_bu
        //BPC BU   biz_base_bpc_bu
        //biz_base_customer_group
        //biz_base_bpc_bu表删除,改用biz_base_bpcbu_bpc_series表
        List<BizBaseBpcbuBpcSeriesDTO> list = bizBaseBpcbuBpcSeriesService.selectBizBaseBpcbuBpcSeriesList(new BizBaseBpcbuBpcSeriesDTO()).getList();
        List<String> stringList = list.stream().filter(t -> StringUtils.isNotBlank(t.getBpcBuCode())).map(BizBaseBpcbuBpcSeriesDTO::getBpcBuCode).collect(Collectors.toList());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX + key, stringList, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void setProfitCenterToRedis() {
        List<BizBaseProfitCenter> bizBaseProfitCenters = bizBaseProfitCenterService.selectBizBaseProfitCenterList(new BizBaseProfitCenter());
        List<String> centerList = bizBaseProfitCenters.stream().map(BizBaseProfitCenter::getProfitCenterCode).collect(Collectors.toList());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX+"profit_center_code",centerList, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void setSalesOfficeToRedis() {
        List<BizBaseSalesOffice> bizBaseSalesOffices = bizBaseSalesOfficeService.selectBizBaseSalesOfficeList(new BizBaseSalesOffice());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX+"sales_office_code",bizBaseSalesOffices, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void setSalesOrgToRedis() {
        List<BizBaseSalesOrg> bizBaseSalesOrgs = bizBaseSalesOrgService.selectBizBaseSalesOrgList(new BizBaseSalesOrg());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX+"sales_org_code",bizBaseSalesOrgs, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void setCountryToRedis() {
        List<BizBaseCountry> bizBaseCountries = bizBaseCountryService.selectBizBaseCountryList(new BizBaseCountry());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX + "bpc_country", bizBaseCountries, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void setTerritoryToRedis() {
        List<BizBaseTerritoryRelation> bizBaseTerritoryRelations = bizBaseTerritoryRelationService.selectBizBaseTerritoryRelationList(new BizBaseTerritoryRelation());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX + "territory", bizBaseTerritoryRelations, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

    public void setMarketToRedis() {
        List<BizBaseRegionMarket> regionMarkets = bizBaseRegionMarketService.selectBizBaseRegionMarketList(new BizBaseRegionMarket());
        redisUtils.set(RedisConstants.REDIS_MASTER_DATA_NAME_PREFIX + "region_market_code", regionMarkets, RedisConstants.REDIS_EXPIRE_TIME_FOREVER);
    }

}
