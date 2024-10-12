package com.microservices.otmp.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.microservices.otmp.common.core.page.RemoteResponse;
import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.RandomUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.masterdata.domain.dto.BizBaseRegionMarketDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseSalesOfficeDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseSalesOrgDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseSegmentDTO;
import com.microservices.otmp.masterdata.feign.RemoteMasterDataService;
import com.microservices.otmp.system.common.ConstantViewField;
import com.microservices.otmp.system.domain.*;
import com.microservices.otmp.system.domain.entity.ViewConditionValueDO;
import com.microservices.otmp.system.domain.entity.ViewFieldDO;
import com.microservices.otmp.system.domain.entity.ViewTemplateDO;
import com.microservices.otmp.system.enums.ConstantFieldEnum;
import com.microservices.otmp.system.enums.DivTypeEnum;
import com.microservices.otmp.system.enums.ViewFieldDimensionsEnum;
import com.microservices.otmp.system.enums.ViewTypeEnum;
import com.microservices.otmp.system.manager.ViewFieldManager;
import com.microservices.otmp.system.manager.ViewTemplateManager;
import com.microservices.otmp.system.mapper.ViewFieldMapper;
import com.microservices.otmp.system.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 页面字段展示信息Service业务层处理
 *
 * @author sdms
 * @date 2022-02-15
 */
@Service
public class ViewFieldServiceImpl implements IViewFieldService {
    public static final int DIMENSION_NUM = 1;
    @Autowired
    private ViewTemplateManager viewTemplateManager;
    @Autowired
    private ViewFieldManager viewFieldManager;
    @Autowired
    private ISysMenuService sysMenuService;
    @Autowired
    private IViewTemplateService viewTemplateService;
    @Autowired
    private ISysDictDataService sysDictDataService;
    @Autowired
    private RemoteMasterDataService remoteMasterDataService;
    @Autowired
    private IViewConditionValueService viewConditionValueService;
    @Autowired
    private ViewFieldMapper viewFieldMapper;

    private static final String KEY = "key";
    private static final String VALUE = "value";

    private static final String DEFAULT_FIELD = "{\"fieldRange\":\"\",\"clearable\":true,\"prefix-icon\":\"\",\"__config__\":" +
            "{\"formId\":21,\"defaultValue\":\"\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"labelWidth\":220,\"label\":\"\",\"showLabel\":true,\"required\":false,\"renderKey\":\"1011644471929553\",\"layout\":\"colFormItem\",\"tagIcon\":\"input\",\"changeTag\":true,\"tag\":\"el-input\",\"regList\":[],\"span\":6}," +
            "\"readonly\":false,\"__vModel__\":\"\",\"checked\":false,\"style\":{\"width\":\"\"},\"disabled\":false,\"placeholder\":\"Please Enter\",\"show-word-limit\":false,\"__slot__\":{\"prepend\":\"\",\"options\":[],\"append\":\"\"},\"suffix-icon\":\"\"}";

    private static final String DIMENSIONS_TYPE = "dimensionsType";
    private static final String GEO_CODE = "geoCode";
    private static final String BUSINESS_GROUP = "businessGroup";
    /**
     * payment model auto 如果数据字典的key修改了，这里也需要修改
     */
    private static final String AUTO_PAYMENT_MODE = "auto_payment_mode";
    /**
     * payment model manual 如果数据字典的key修改了，这里也需要修改
     */
    private static final String MANUAL_PAYMENT_MODE = "manual_payment_mode";

    private static final List<String> FIELD_IGNORE = Lists.newArrayList(ConstantViewField.PARENT_ID, ConstantViewField.POPOVER_BOUND_FIELD, ConstantViewField.PAGE_KEY, ConstantViewField.VIEW_TYPE,
            ConstantViewField.DIV_KEY, ConstantViewField.TYPE, ConstantViewField.TABLE_NAME, ConstantViewField.CREATE_BY, ConstantViewField.CREATE_TIME, ConstantViewField.UPDATE_BY, ConstantViewField.UPDATE_TIME,
            ConstantViewField.FIELD_JSON, ConstantViewField.SORT, ConstantViewField.ID, ConstantViewField.STATUS);

    /**
     * 查询页面字段展示信息
     *
     * @param id 页面字段展示信息ID
     * @return 页面字段展示信息
     */
    @Override
    public Map<String, Object> selectViewFieldById(Integer id) {
        Map<String, Object> result;
        ViewFieldDO viewField = viewFieldManager.selectViewFieldById(id);
        String fieldJson = viewField.getFieldJson();
        JSONObject jsonObject = JSON.parseObject(fieldJson);
        JSONArray jsonArray = jsonObject.getJSONArray(ConstantFieldEnum.FIELDS.getName());
        List<JSONObject> list = jsonArray.toJavaList(JSONObject.class);
        for (JSONObject object : list) {
            object.put(ConstantFieldEnum.CHECKED.getName(), false);
        }
        viewField.setFieldJson(JSON.toJSONString(jsonObject));
        result = JSON.parseObject(JSON.toJSONString(viewField), new TypeReference<Map<String, Object>>() {
        });
        ViewConditionValue viewConditionValue = new ViewConditionValue();
        viewConditionValue.setFieldId(id);
        List<ViewConditionValueDO> viewConditionValues = viewConditionValueService.selectViewConditionValueList(viewConditionValue);
        for (ViewConditionValueDO conditionValue : viewConditionValues) {
            result.put(conditionValue.getCondition(), conditionValue.getConditionValue());
        }
        return result;
    }

    /**
     * 查询页面字段展示信息列表
     *
     * @param viewField 页面字段展示信息
     * @return 页面字段展示信息
     */
    @Override

    public List<ViewFieldDO> selectViewFieldList(ViewField viewField) {
        return viewFieldManager.selectViewFieldList(viewField);
    }

    /**
     * 新增页面字段展示信息
     *
     * @param viewField 页面字段展示信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertViewField(Map<String, Object> viewField) {
        verifyField(viewField);
        viewField.put(ConstantViewField.CREATE_TIME, DateUtils.getNowDate());
        viewField.put(ConstantViewField.UPDATE_TIME, DateUtils.getNowDate());

        String fieldJson = viewField.get(ConstantViewField.FIELD_JSON).toString();
        JSONObject jsonObject = JSON.parseObject(fieldJson);
        JSONArray jsonArray = jsonObject.getJSONArray(ConstantFieldEnum.FIELDS.getName());
        List<JSONObject> fields = jsonArray.toJavaList(JSONObject.class);
        for (JSONObject field : fields) {
            field.put(ConstantFieldEnum.CHECKED.getName(), false);
        }

        viewField.put(ConstantViewField.FIELD_JSON, jsonObject.toJSONString());

        Set<String> viewFields = viewField.keySet();
        //过滤选中的维度
        Set<String> dimensionSet = viewFields.stream().filter(s -> !FIELD_IGNORE.contains(s) &&
                !"".equals(viewField.get(s))).collect(Collectors.toSet());
        this.verifyDimensions(null, viewField.get(ConstantViewField.FIELD_JSON).toString(), dimensionSet);
        viewField.put(ConstantViewField.STATUS, 0);
        //新增field 块
        int i = viewFieldManager.insertViewFieldMap(viewField);

        addConditionValue(viewField, dimensionSet);
        return i;
    }

    public void addConditionValue(Map<String, Object> viewField, Set<String> dimensionSet) {
        for (String s : dimensionSet) {
            ViewConditionValue viewConditionValue = new ViewConditionValue();
            viewConditionValue.setFieldId(Integer.parseInt(viewField.get(ConstantViewField.ID).toString()));
            viewConditionValue.setCondition(s);
            String value = viewField.get(s).toString();
            Map<String, String> map = new HashMap<>();
            if (ConstantViewField.ALL_VALUE.equals(value)) {
                businessGroupAndGeoCodeCondionAdd(viewField, map);
                salesOfficeCodeConditionAdd(viewField, s, map);
                map.put(ConstantViewField.DIMENSIONS_TYPE, s);
                List<SysDictData> dataList = this.fieldDimensionsSelect(map);
                List<String> collect1 = dataList.stream().map(SysDictData::getDictValue).collect(Collectors.toList());
                value = org.apache.commons.lang3.StringUtils.join(collect1, ",");
            }
            value = value.replace(ConstantViewField.ALL_VALUE_SUB, "");
            viewField.put(s, value);
            viewConditionValue.setConditionValue(value);
            viewConditionValue.setCreateBy(viewField.get(ConstantViewField.CREATE_BY).toString());
            viewConditionValue.setUpdateBy(viewField.get(ConstantViewField.CREATE_BY).toString());
            viewConditionValue.setCreateTime(DateUtils.getNowDate());
            viewConditionValue.setUpdateTime(DateUtils.getNowDate());
            viewConditionValueService.insertViewConditionValue(viewConditionValue);
        }
    }

    private static void salesOfficeCodeConditionAdd(Map<String, Object> viewField, String s, Map<String, String> map) {
        if (ViewFieldDimensionsEnum.SALES_OFFICE_CODE.getName().equals(s)) {
            if (null != viewField.get(ViewFieldDimensionsEnum.REGION_MARKET_CODE.getName()) && !"".equals(viewField.get(ViewFieldDimensionsEnum.REGION_MARKET_CODE.getName()).toString())) {
                map.put(ViewFieldDimensionsEnum.REGION_MARKET_CODE.getName(), viewField.get(ViewFieldDimensionsEnum.REGION_MARKET_CODE.getName()).toString());
            }
            if (null != viewField.get(ViewFieldDimensionsEnum.SALES_ORG_CODE.getName()) && !"".equals(viewField.get(ViewFieldDimensionsEnum.SALES_ORG_CODE.getName()).toString())) {
                map.put(ViewFieldDimensionsEnum.SALES_ORG_CODE.getName(), viewField.get(ViewFieldDimensionsEnum.SALES_ORG_CODE.getName()).toString());
            }
        }
    }

    private static void verifyField(Map<String, Object> viewField) {
        if (null == viewField.get(ConstantViewField.PAGE_KEY)) {
            throw new OtmpException("Page key is null");
        }
        if (null == viewField.get(ConstantViewField.VIEW_TYPE)) {
            throw new OtmpException("View type is null");
        }
        if (null == viewField.get(ConstantViewField.DIV_KEY)) {
            throw new OtmpException("Div key is null");
        }
        if (null == viewField.get(ConstantViewField.TYPE)) {
            throw new OtmpException("Type is null");
        }
        if (null == viewField.get(ConstantViewField.TABLE_NAME)) {
            throw new OtmpException("Table name is null");
        }

        //给一级字段配置增加默认值
        viewField.putIfAbsent(ConstantViewField.PARENT_ID, 0);
        //字段必须关联到一个父块下面
        if (viewField.containsKey(ConstantViewField.POPOVER_BOUND_FIELD) && null != viewField.get(ConstantViewField.POPOVER_BOUND_FIELD) &&
                null != viewField.get(ConstantViewField.PARENT_ID) && Integer.parseInt(viewField.get(ConstantViewField.PARENT_ID).toString()) < 1) {
            throw new OtmpException("请选择它的父级块");
        }
    }

    /**
     * 通过pageKey查询,edit\details是否已经有配过的维度,如果有就用这个维度去判断新增的块的维度.
     */
    @Override
    public void verifyDimensions(Integer fieldId, String fieldJson, Set<String> dimensionSet) {

        JSONObject jsonObject = JSON.parseObject(fieldJson);
        JSONArray jsonArray = jsonObject.getJSONArray(ConstantFieldEnum.FIELDS.getName());
        List<JSONObject> fields = jsonArray.toJavaList(JSONObject.class);
        if (null != fieldId) {
            ViewConditionValue viewConditionValue = new ViewConditionValue();
            viewConditionValue.setFieldId(fieldId);
            List<ViewConditionValueDO> viewConditionValues = viewConditionValueService.selectViewConditionValueList(viewConditionValue);
            //指定维度的块 验证字段是否和块的总维度是否一样
            verifyDimensions(fields, viewConditionValues);
        } else if (null != dimensionSet && !dimensionSet.isEmpty()) {
            verifyDimensions(dimensionSet, fields);
        } else {
            for (JSONObject field : fields) {
                if (field.containsKey(ConstantFieldEnum.FIELD_RANGE.getName()) && null != field.get(ConstantFieldEnum.FIELD_RANGE.getName())
                        && !"".equals(field.get(ConstantFieldEnum.FIELD_RANGE.getName()))) {
                    throw new OtmpException("块维度不能为空");
                }
            }

        }
    }

    private static void verifyDimensions(Set<String> dimensionSet, List<JSONObject> fields) {
        for (JSONObject field : fields) {
            if (field.containsKey(ConstantFieldEnum.FIELD_RANGE.getName()) && null != field.get(ConstantFieldEnum.FIELD_RANGE.getName()) &&
                    !"".equals(field.get(ConstantFieldEnum.FIELD_RANGE.getName()).toString())) {
                String fieldRange = (String) field.get(ConstantFieldEnum.FIELD_RANGE.getName());
                JSONObject fieldRangeObj = JSON.parseObject(fieldRange);
                Set<String> fieldRangeObjSet = fieldRangeObj.keySet();
                // 获取不在viewFieldDimensionsSet范围的维度
                Set<String> collect = fieldRangeObjSet.stream().filter(s -> !new HashSet<>(dimensionSet).contains(s)).collect(Collectors.toSet());
                collect.remove(ConstantViewField.NEQ);
                if (!collect.isEmpty()) {
                    throw new OtmpException("指定的维度和字段里面【fieldRange】指定的维度不一样");
                }
            }
        }
    }

    private static void verifyDimensions(List<JSONObject> fields, List<ViewConditionValueDO> viewConditionValues) {
        if (!viewConditionValues.isEmpty()) {
            Set<String> viewFieldDimensionsSet = viewConditionValues.stream().map(ViewConditionValueDO::getCondition).collect(Collectors.toSet());
            for (JSONObject field : fields) {
                if (field.containsKey(ConstantFieldEnum.FIELD_RANGE.getName()) && null != field.get(ConstantFieldEnum.FIELD_RANGE.getName())) {
                    String fieldRange = (String) field.get(ConstantFieldEnum.FIELD_RANGE.getName());
                    JSONObject fieldRangeObj = JSON.parseObject(fieldRange);
                    Set<String> fieldRangeObjSet = fieldRangeObj.keySet();
                    // 获取不在viewFieldDimensionsSet范围的维度
                    Set<String> collect = fieldRangeObjSet.stream().filter(s -> !new HashSet<>(viewFieldDimensionsSet).contains(s)).collect(Collectors.toSet());
                    collect.remove(ConstantViewField.NEQ);
                    if (!collect.isEmpty()) {
                        throw new OtmpException("字段指定的维度必须在块的指定范围内");
                    }
                }
            }
        }
    }

    @Override
    public List<SysDictData> getTableNames() {
        List<SysDictData> list = new ArrayList<>();
        for (String tableName : viewFieldManager.getTableNames()) {
            SysDictData sysDictData = new SysDictData();
            sysDictData.setDictValue(tableName);
            sysDictData.setDictLabel(tableName);
            list.add(sysDictData);
        }
        return list;
    }

    @Override
    public List<SysDictData> fieldDimensionsSelect(Map<String, String> condition) {
        if (!condition.containsKey(DIMENSIONS_TYPE) || StringUtils.isEmpty(condition.get(DIMENSIONS_TYPE))) {
            throw new OtmpException("dimensionsType is null.");
        } else {
            //对带有New值的替换成空字符串：例子：regionNew替换成region
            if (condition.get(DIMENSIONS_TYPE).contains("New")) {
                String dimensionsType = condition.get(DIMENSIONS_TYPE).replace("New", "");
                condition.put(DIMENSIONS_TYPE, dimensionsType);
            }
        }
        String dictType = condition.get(DIMENSIONS_TYPE);
        List<SysDictData> dataList = new ArrayList<>();
        SysDictData sysDictDataAll = new SysDictData();
        sysDictDataAll.setDictValue(ConstantViewField.ALL_VALUE);
        sysDictDataAll.setDictLabel(ConstantViewField.ALL_VALUE);
        dataList.add(sysDictDataAll);
        if (ViewFieldDimensionsEnum.REGION_MARKET_CODE.getName().equals(dictType)) {
            regionMarketCodeSelect(condition, dataList);
        } else if (ViewFieldDimensionsEnum.SALES_ORG_CODE.getName().equals(dictType)) {
            salesOrgCodeSelect(condition, dataList);
        } else if (ViewFieldDimensionsEnum.SALES_OFFICE_CODE.getName().equals(dictType)) {
            salesOfficeCodeSelect(condition, dataList);
        } else if (ViewFieldDimensionsEnum.SEGMENT_CODE.getName().equals(dictType)) {
            segmentCodeSelect(condition, dataList);
        } else if (ViewFieldDimensionsEnum.COMPANY_CODE.getName().equals(dictType)) {
            companyCodeSelect(condition, dataList);
        } else if (ViewFieldDimensionsEnum.PAYMENT_MODE.getName().equals(dictType)) {
            paymentModeSelect(dataList);
        } else if (ConstantViewField.VIEW_TYPE.equals(dictType)) {
            viewTypeSelect(dataList, sysDictDataAll);
        } else {
            otherSelect(dictType, dataList);
        }
        return dataList;
    }

    private void otherSelect(String dictType, List<SysDictData> dataList) {
        if (BUSINESS_GROUP.equals(dictType)) {
            dictType = ConstantViewField.DICT_BUSINESS_GROUP;
        }
        if (GEO_CODE.equals(dictType)) {
            dictType = ConstantViewField.DICT_GEO_CODE;
        }

        List<SysDictData> dataList1 = sysDictDataService.selectDictDataByTypeCode(dictType);
        dataList.addAll(dataList1);
    }

    private static void viewTypeSelect(List<SysDictData> dataList, SysDictData sysDictDataAll) {
        SysDictData sysDictDataList = new SysDictData();
        sysDictDataList.setDictValue(ViewTypeEnum.LIST.getCode());
        sysDictDataList.setDictLabel(ViewTypeEnum.LIST.getName());
        dataList.add(sysDictDataList);
        SysDictData sysDictDataEdit = new SysDictData();
        sysDictDataEdit.setDictValue(ViewTypeEnum.EDIT.getCode());
        sysDictDataEdit.setDictLabel(ViewTypeEnum.EDIT.getName());
        dataList.add(sysDictDataEdit);
        SysDictData sysDictDataDDetails = new SysDictData();
        sysDictDataDDetails.setDictValue(ViewTypeEnum.DETAILS.getCode());
        sysDictDataDDetails.setDictLabel(ViewTypeEnum.DETAILS.getName());
        dataList.add(sysDictDataDDetails);
        dataList.remove(sysDictDataAll);
    }

    private void paymentModeSelect(List<SysDictData> dataList) {
        List<SysDictData> dataListAuto = sysDictDataService.selectDictDataByTypeCode(AUTO_PAYMENT_MODE);
        List<SysDictData> dataListManual = sysDictDataService.selectDictDataByTypeCode(MANUAL_PAYMENT_MODE);
        dataListManual.addAll(dataListAuto);
        //去重
        ArrayList<SysDictData> collect = dataListManual.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new
                TreeSet<>(Comparator.comparing(SysDictData::getDictValue))), ArrayList::new));
        dataList.addAll(collect);
    }

    private void companyCodeSelect(Map<String, String> condition, List<SysDictData> dataList) {
        String geoCode = null;
        String businessGroup = null;
        String salesOrgCode = null;
        if (condition.containsKey(GEO_CODE)) {
            geoCode = condition.get(GEO_CODE);
        }
        if (condition.containsKey(BUSINESS_GROUP)) {
            businessGroup = condition.get(BUSINESS_GROUP);
        }
        String name = ViewFieldDimensionsEnum.SALES_ORG_CODE.getName();
        if (condition.containsKey(name)) {
            salesOrgCode = condition.get(name);
        }
        if (ConstantViewField.ALL_VALUE.equals(geoCode)) {
            List<SysDictData> dataList1 = sysDictDataService.selectDictDataByTypeCode(geoCode);
            geoCode = dataList1.stream().map(SysDictData::getDictValue).collect(Collectors.joining(","));
        }
        if (ConstantViewField.ALL_VALUE.equals(businessGroup)) {
            List<SysDictData> dataList1 = sysDictDataService.selectDictDataByTypeCode(ConstantViewField.DICT_BUSINESS_GROUP);
            businessGroup = dataList1.stream().map(SysDictData::getDictValue).collect(Collectors.joining(","));
        }
        if (ConstantViewField.ALL_VALUE.equals(salesOrgCode)) {
            List<BizBaseSalesOrgDTO> data = getBizBaseSalesOrgDTOS(condition);
            salesOrgCode = data.stream().map(BizBaseSalesOrgDTO::getSalesOrgCode).collect(Collectors.joining(","));
        }
        RemoteResponse<BizBaseSalesOrgDTO> listBySalesOrg = remoteMasterDataService.getListBySalesOrg(geoCode, businessGroup,
                salesOrgCode, ViewFieldDimensionsEnum.COMPANY_CODE.getName());
        List<BizBaseSalesOrgDTO> data = listBySalesOrg.getRows();
        for (BizBaseSalesOrgDTO datum : data) {
            SysDictData sysDictData = new SysDictData();
            sysDictData.setDictValue(datum.getCompanyCode());
            sysDictData.setDictLabel(datum.getCompanyCode());
            dataList.add(sysDictData);
        }
    }

    private void segmentCodeSelect(Map<String, String> condition, List<SysDictData> dataList) {
        String geoCode = null;
        String businessGroup = null;
        if (condition.containsKey(GEO_CODE)) {
            geoCode = condition.get(GEO_CODE);
        }
        if (condition.containsKey(BUSINESS_GROUP)) {
            businessGroup = condition.get(BUSINESS_GROUP);
        }
        if (ConstantViewField.ALL_VALUE.equals(geoCode)) {
            List<SysDictData> dataList1 = sysDictDataService.selectDictDataByTypeCode(geoCode);
            geoCode = dataList1.stream().map(SysDictData::getDictValue).collect(Collectors.joining(","));
        }
        if (ConstantViewField.ALL_VALUE.equals(businessGroup)) {
            List<SysDictData> dataList1 = sysDictDataService.selectDictDataByTypeCode(ConstantViewField.DICT_BUSINESS_GROUP);
            businessGroup = dataList1.stream().map(SysDictData::getDictValue).collect(Collectors.joining(","));
        }
        RemoteResponse<BizBaseSegmentDTO> listBySegment = remoteMasterDataService.getListBySegment(geoCode, businessGroup);
        List<BizBaseSegmentDTO> data = listBySegment.getRows();
        for (BizBaseSegmentDTO datum : data) {
            SysDictData sysDictData = new SysDictData();
            sysDictData.setDictValue(datum.getSegmentCode());
            sysDictData.setDictLabel(datum.getSegmentCode());
            dataList.add(sysDictData);
        }
    }

    private void salesOfficeCodeSelect(Map<String, String> condition, List<SysDictData> dataList) {
        String regionMarketCode = null;
        String businessGroup = null;
        String salesOrgCode = null;
        if (condition.containsKey(ViewFieldDimensionsEnum.REGION_MARKET_CODE.getName())) {
            regionMarketCode = condition.get(ViewFieldDimensionsEnum.REGION_MARKET_CODE.getName());
        }
        if (condition.containsKey(BUSINESS_GROUP)) {
            businessGroup = condition.get(BUSINESS_GROUP);
        }
        String name = ViewFieldDimensionsEnum.SALES_ORG_CODE.getName();
        if (condition.containsKey(name)) {
            salesOrgCode = condition.get(name);
        }
        // All转主数据值
        if (ConstantViewField.ALL_VALUE.equals(businessGroup)) {
            List<SysDictData> dataList1 = sysDictDataService.selectDictDataByTypeCode(ConstantViewField.DICT_BUSINESS_GROUP);
            businessGroup = dataList1.stream().map(SysDictData::getDictValue).collect(Collectors.joining(","));
        }
        if (ConstantViewField.ALL_VALUE.equals(regionMarketCode)) {
            List<BizBaseRegionMarketDTO> rows = getBizBaseRegionMarketDTOS(condition);
            regionMarketCode = rows.stream().map(BizBaseRegionMarketDTO::getRegionMarketCode).collect(Collectors.joining(","));
        }
        if (ConstantViewField.ALL_VALUE.equals(salesOrgCode)) {
            List<BizBaseSalesOrgDTO> data = getBizBaseSalesOrgDTOS(condition);
            salesOrgCode = data.stream().map(BizBaseSalesOrgDTO::getSalesOrgCode).collect(Collectors.joining(","));
        }
        RemoteResponse<BizBaseSalesOfficeDTO> listBySalesOffice = remoteMasterDataService.getListBySalesOffice(businessGroup, regionMarketCode, salesOrgCode);
        List<BizBaseSalesOfficeDTO> data = listBySalesOffice.getRows();
        for (BizBaseSalesOfficeDTO datum : data) {
            SysDictData sysDictData = new SysDictData();
            sysDictData.setDictValue(datum.getSalesOfficeCode());
            sysDictData.setDictLabel(datum.getSalesOfficeName());
            dataList.add(sysDictData);
        }
    }

    private void salesOrgCodeSelect(Map<String, String> condition, List<SysDictData> dataList) {
        List<BizBaseSalesOrgDTO> data = getBizBaseSalesOrgDTOS(condition);
        for (BizBaseSalesOrgDTO datum : data) {
            SysDictData sysDictData = new SysDictData();
            sysDictData.setDictValue(datum.getSalesOrgCode());
            sysDictData.setDictLabel(datum.getSalesOrgCode());
            dataList.add(sysDictData);
        }
    }

    private void regionMarketCodeSelect(Map<String, String> condition, List<SysDictData> dataList) {
        List<BizBaseRegionMarketDTO> rows = getBizBaseRegionMarketDTOS(condition);
        for (BizBaseRegionMarketDTO datum : rows) {
            SysDictData sysDictData = new SysDictData();
            sysDictData.setDictValue(datum.getRegionMarketCode());
            sysDictData.setDictLabel(datum.getRegionMarketCode());
            dataList.add(sysDictData);
        }
    }

    private List<BizBaseSalesOrgDTO> getBizBaseSalesOrgDTOS(Map<String, String> condition) {
        String geoCode = null;
        String businessGroup = null;
        String salesOrgCode = null;
        if (condition.containsKey(GEO_CODE)) {
            geoCode = condition.get(GEO_CODE);
        }
        if (condition.containsKey(BUSINESS_GROUP)) {
            businessGroup = condition.get(BUSINESS_GROUP);
        }
        String name = ViewFieldDimensionsEnum.SALES_ORG_CODE.getName();
        if (condition.containsKey(name)) {
            salesOrgCode = condition.get(name);
        }
        // All转主数据值
        if (ConstantViewField.ALL_VALUE.equals(geoCode)) {
            List<SysDictData> dataList1 = sysDictDataService.selectDictDataByTypeCode(geoCode);
            geoCode = dataList1.stream().map(SysDictData::getDictValue).collect(Collectors.joining(","));
        }
        if (ConstantViewField.ALL_VALUE.equals(businessGroup)) {
            List<SysDictData> dataList1 = sysDictDataService.selectDictDataByTypeCode(ConstantViewField.DICT_BUSINESS_GROUP);
            businessGroup = dataList1.stream().map(SysDictData::getDictValue).collect(Collectors.joining(","));
        }
        RemoteResponse<BizBaseSalesOrgDTO> listBySalesOrg = remoteMasterDataService.getListBySalesOrg(geoCode, businessGroup, salesOrgCode, null);
        return listBySalesOrg.getRows();
    }

    private List<BizBaseRegionMarketDTO> getBizBaseRegionMarketDTOS(Map<String, String> condition) {
        String geoCode = null;
        String businessGroup = null;
        if (condition.containsKey(GEO_CODE)) {
            geoCode = condition.get(GEO_CODE);
        }
        if (condition.containsKey(BUSINESS_GROUP)) {
            businessGroup = condition.get(BUSINESS_GROUP);
        }
        // All转主数据值
        if (ConstantViewField.ALL_VALUE.equals(geoCode)) {
            List<SysDictData> dataList1 = sysDictDataService.selectDictDataByTypeCode(geoCode);
            geoCode = dataList1.stream().map(SysDictData::getDictValue).collect(Collectors.joining(","));
        }
        if (ConstantViewField.ALL_VALUE.equals(businessGroup)) {
            List<SysDictData> dataList1 = sysDictDataService.selectDictDataByTypeCode(ConstantViewField.DICT_BUSINESS_GROUP);
            businessGroup = dataList1.stream().map(SysDictData::getDictValue).collect(Collectors.joining(","));
        }

        RemoteResponse<BizBaseRegionMarketDTO> listByRegionMarket = remoteMasterDataService.getListByRegionMarket(geoCode, businessGroup);
        return listByRegionMarket.getRows();
    }

    @Override
    public List<Map<String, Object>> selectViewFieldAndCondition(Map<String, Object> map) {
        List<SysDictData> dataList = sysDictDataService.selectDictDataByTypeCode(ConstantViewField.VIEW_FIELD_DIMENSIONS);
        map.remove(ConstantViewField.PAGE_NUM);
        map.remove(ConstantViewField.PAGE_SIZE);
        map.remove(ConstantViewField.PAGE_PARENT_ID);
        map.remove(ConstantViewField.ROW_ID);
        List<Map<String, Object>> conditionObjList = new ArrayList<>();
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            Map<String, Object> conditionObj = new HashMap<>();
            if (!ConstantViewField.DIFFERENT_TEMPLATE.equals(stringObjectEntry.getKey())) {
                conditionObj.put(KEY, stringObjectEntry.getKey());
                conditionObj.put(VALUE, stringObjectEntry.getValue());
                conditionObjList.add(conditionObj);
            }
        }
        if (!conditionObjList.isEmpty()) {
            map.put(ConstantViewField.CONDITION_OBJ_LIST, conditionObjList);
        }

        if (!dataList.isEmpty()) {
            List<String> collect = dataList.stream().map(SysDictData::getDictValue).collect(Collectors.toList());
            map.put(ConstantViewField.DIMENSIONS, collect);
        }

        return viewFieldManager.selectViewFieldAndCondition(map);
    }

    @Override
    public List<Map<String, Object>> selectViewFieldAndTemplateAndCondition(Map<String, Object> map) {
        List<SysDictData> dataList = sysDictDataService.selectDictDataByTypeCode(ConstantViewField.VIEW_FIELD_DIMENSIONS);
        map.remove(ConstantViewField.IT_CODE_DEF);
        map.remove(ConstantViewField.PAGE_PARENT_ID);
        map.remove(ConstantViewField.PAGE_NUM);
        map.remove(ConstantViewField.PAGE_SIZE);
        map.remove(ConstantViewField.LEVEL);
        if (map.containsKey(ConstantViewField.PARENT_ID) && null != map.get(ConstantViewField.PARENT_ID)
                && !"".equals(map.get(ConstantViewField.PARENT_ID))) {
            map.put(ConstantViewField.PARENT_ID, Integer.parseInt(map.get(ConstantViewField.PARENT_ID).toString()));
        }
        List<Map<String, Object>> conditionObjList = new ArrayList<>();
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            Map<String, Object> conditionObj = new HashMap<>(conditionObjList.size());
            if (!ConstantViewField.DIFFERENT_TEMPLATE.equals(stringObjectEntry.getKey()) && !ConstantViewField.ROW_ID.equals(stringObjectEntry.getKey())) {
                conditionObj.put(KEY, stringObjectEntry.getKey());
                conditionObj.put(VALUE, stringObjectEntry.getValue());
                conditionObjList.add(conditionObj);
            }
        }
        if (!conditionObjList.isEmpty()) {
            map.put(ConstantViewField.CONDITION_OBJ_LIST, conditionObjList);
        }

        if (!dataList.isEmpty()) {
            List<String> collect = dataList.stream().map(SysDictData::getDictValue).collect(Collectors.toList());
            map.put(ConstantViewField.DIMENSIONS, collect);
        }
        return viewFieldManager.selectViewFieldAndTemplateAndCondition(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dataRecovery(Map<String, Object> condition) {
        ViewField viewField1 = new ViewField();
        viewField1.setStatus(0);
        viewField1.setPageKey("2122");
        List<ViewFieldDO> list = viewFieldManager.selectViewFieldList(viewField1);
        for (ViewFieldDO viewField : list) {

            if (StringUtils.isNotEmpty(viewField.getGeoCode())) {
                ViewConditionValue viewConditionValueGeo = new ViewConditionValue();
                viewConditionValueGeo.setFieldId(viewField.getId());
                viewConditionValueGeo.setCondition("geo");
                viewConditionValueGeo.setConditionValue(viewField.getGeoCode());
                viewConditionValueService.insertViewConditionValue(viewConditionValueGeo);
            }
            if (StringUtils.isNotEmpty(viewField.getRegion())) {
                Map<String, String> map = new HashMap<>();
                map.put(ConstantViewField.BUSINESS_GROUP, viewField.getBusinessGroup());
                map.put(ConstantViewField.GEO_CODE, viewField.getGeoCode());
                map.put(ConstantViewField.DIMENSIONS_TYPE, ViewFieldDimensionsEnum.REGION_MARKET_CODE.getName());
                List<SysDictData> dataList1 = this.fieldDimensionsSelect(map);
                List<String> collect1 = dataList1.stream().map(SysDictData::getDictValue).collect(Collectors.toList());
                String value = org.apache.commons.lang3.StringUtils.join(collect1, ",");
                ViewConditionValue viewConditionValueRegion = new ViewConditionValue();
                viewConditionValueRegion.setFieldId(viewField.getId());
                viewConditionValueRegion.setCondition("regionMarketCode");
                viewConditionValueRegion.setConditionValue(value);
                viewConditionValueService.insertViewConditionValue(viewConditionValueRegion);
            }
            if (StringUtils.isNotEmpty(viewField.getBusinessGroup())) {
                ViewConditionValue viewConditionValueBusinessGroup = new ViewConditionValue();
                viewConditionValueBusinessGroup.setFieldId(viewField.getId());
                viewConditionValueBusinessGroup.setCondition(ConstantViewField.BUSINESS_GROUP);
                viewConditionValueBusinessGroup.setConditionValue(viewField.getBusinessGroup());
                viewConditionValueService.insertViewConditionValue(viewConditionValueBusinessGroup);
            }
            if (StringUtils.isNotEmpty(viewField.getPaymentMode())) {
                ViewConditionValue viewConditionValueGeoPaymentMode = new ViewConditionValue();
                viewConditionValueGeoPaymentMode.setFieldId(viewField.getId());
                viewConditionValueGeoPaymentMode.setCondition("paymentMode");
                viewConditionValueGeoPaymentMode.setConditionValue(viewField.getPaymentMode());
                viewConditionValueService.insertViewConditionValue(viewConditionValueGeoPaymentMode);
            }
        }
    }

    @Override
    public List<Map<String, String>> columnHeader() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> columnHeaderPageKey = new LinkedHashMap<>();
        columnHeaderPageKey.put(ConstantViewField.FIELD, ConstantViewField.PAGE_KEY_NAME);
        columnHeaderPageKey.put(ConstantViewField.LABEL, "Page Key");
        list.add(columnHeaderPageKey);
        Map<String, String> columnHeaderViewType = new LinkedHashMap<>();
        columnHeaderViewType.put(ConstantViewField.FIELD, ConstantViewField.VIEW_TYPE);
        columnHeaderViewType.put(ConstantViewField.LABEL, "View Type");
        list.add(columnHeaderViewType);
        Map<String, String> columnHeaderDivKey = new LinkedHashMap<>();
        columnHeaderDivKey.put(ConstantViewField.FIELD, ConstantViewField.DIV_KEY);
        columnHeaderDivKey.put(ConstantViewField.LABEL, "块 Key");
        list.add(columnHeaderDivKey);
        List<SysDictData> dataList = sysDictDataService.selectDictDataByTypeCode(ConstantViewField.VIEW_FIELD_DIMENSIONS);
        for (SysDictData sysDictData : dataList) {
            Map<String, String> columnHeader = new LinkedHashMap<>();
            columnHeader.put(ConstantViewField.FIELD, sysDictData.getDictValue());
            columnHeader.put(ConstantViewField.LABEL, sysDictData.getDictLabel());
            list.add(columnHeader);
        }

        return list;
    }

    @Override
    public PageInfo<Map<String, Object>> tree(Map<String, Object> map) {
        map.put(ConstantViewField.PAGE_KEY_NAME, null);
        //前端为了公用公共组件，把返回的数据结构和菜单的结构一样。rowId 行ID.只查询parentID=0的
        List<Map<String, Object>> treeList = new ArrayList<>();
        // 一级 没有传参数
        int flag = 1;
        int one;
        //页面通过条件查询的时候回传 condition 这个值
        Object condition = map.get(ConstantViewField.CONDITION);
        if (null != condition && condition.equals("1")) {
            one = 1;
        } else {
            one = paramValidation(map, flag);
        }
        if (flag == one) {
            return one(map, treeList);
        } else {
            map.put(ConstantViewField.PAGE_SIZE, 10000);
            // 二级 只传了 pageKey
            int two = paramValidation(map, flag, ConstantViewField.PAGE_KEY);
            if (flag == two) {
                twoTree(map, treeList);
            } else {
                // 等于list 不能进入
                twoTreeElse(map, treeList, flag);
            }
        }
        for (Map<String, Object> stringObjectMap : treeList) {
            stringObjectMap.put(ConstantViewField.TEMPLATE_FIELD_JSON, null);
            stringObjectMap.put(ConstantViewField.FIELD_JSON, null);
        }
        return new PageInfo<>(treeList);
    }

    public void twoTreeElse(Map<String, Object> map, List<Map<String, Object>> treeList, int flag) {
        if (!ViewTypeEnum.LIST.name().equals(map.get(ConstantViewField.VIEW_TYPE))) {
            //  三级 只传了 pageKey、viewType
            int three = paramValidation(map, flag, ConstantViewField.PAGE_KEY, ConstantViewField.VIEW_TYPE);
            if (flag == three) {
                threeTree(map, treeList, flag);
            } else {
                threeTreeElse(map, treeList, flag);
            }
        } else {
            noDimension(map, treeList, flag);
        }
    }

    public void threeTreeElse(Map<String, Object> map, List<Map<String, Object>> treeList, int flag) {
        List<SysDictData> dataListExist = sysDictDataService.selectDictDataByTypeCode(ConstantViewField.VIEW_FIELD_DIMENSIONS);
        int o = 0;
        for (SysDictData sysDictData : dataListExist) {
            if (null != map.get(sysDictData.getDictValue())) {
                o++;
            }
        }
        // edit 没有维度的不能进入
        if (o > 0) {
            // 四级
            // 维度
            List<SysDictData> dataList = sysDictDataService.selectDictDataByTypeCode(ConstantViewField.VIEW_FIELD_DIMENSIONS);
            List<String> collect = dataList.stream().map(SysDictData::getDictValue).collect(Collectors.toList());
            //维度 是 and 关系，前端没有传相关的and 字段，后端补齐
            for (String s : collect) {
                map.putIfAbsent(s, null);
            }
            collect.add(ConstantViewField.PAGE_KEY);
            collect.add(ConstantViewField.VIEW_TYPE);
            String[] strings = collect.toArray(new String[0]);
            int four = paramValidation(map, flag, strings);
            if (flag == four) {
                fourTree(map, treeList);
            } else {
                // N级
                fourTreeElse(map, treeList);
            }
        } else {
            noDimension(map, treeList, flag);
        }
    }

    public void fourTreeElse(Map<String, Object> map, List<Map<String, Object>> treeList) {
        Map<String, Object> other = new HashMap<>();
        other.put(ConstantViewField.PARENT_ID, Integer.parseInt(map.get(ConstantViewField.ID).toString()));
        List<Map<String, Object>> list = this.selectViewFieldListPrecise(other);
        long i;
        int j = 0;
        for (Map<String, Object> stringObjectMap : list) {
            stringObjectMap.put(ConstantViewField.PAGE_KEY_NAME, map.get(ConstantViewField.PAGE_KEY_NAME));
            ViewField viewField = new ViewField();
            viewField.setStatus(0);
            viewField.setParentId(Integer.parseInt(stringObjectMap.get(ConstantViewField.ID).toString()));
            List<ViewFieldDO> viewFieldDOS = viewFieldManager.selectViewFieldList(viewField);
            if (!viewFieldDOS.isEmpty()) {
                stringObjectMap.put(ConstantViewField.CHILDREN, new ArrayList<>());
            }
            String rowId = map.get(ConstantViewField.ROW_ID).toString() + j;
            i = Long.parseLong(rowId);
            j++;
            stringObjectMap.put(ConstantViewField.ROW_ID, i);
            stringObjectMap.put(ConstantViewField.LEVEL, Integer.parseInt(null == map.get(ConstantViewField.LEVEL) ? "0" : map.get(ConstantViewField.LEVEL).toString()) + 1);
        }
        treeList.addAll(list);
    }

    public void fourTree(Map<String, Object> map, List<Map<String, Object>> treeList) {
        map.put(ConstantViewField.PARENT_ID, 0);
        List<Map<String, Object>> list = this.selectViewFieldListPrecise(map);
        long i;
        int j = 0;
        for (Map<String, Object> stringObjectMap : list) {
            stringObjectMap.put(ConstantViewField.PAGE_KEY_NAME, map.get(ConstantViewField.PAGE_KEY_NAME));
            ViewField viewField = new ViewField();
            viewField.setParentId(Integer.parseInt(stringObjectMap.get(ConstantViewField.ID).toString()));
            viewField.setStatus(0);
            List<ViewFieldDO> viewFieldDOS = viewFieldManager.selectViewFieldList(viewField);
            if (!viewFieldDOS.isEmpty()) {
                stringObjectMap.put(ConstantViewField.CHILDREN, new ArrayList<>());
            }
            String rowId = map.get(ConstantViewField.ROW_ID).toString() + j;
            i = Long.parseLong(rowId);
            j++;
            stringObjectMap.put(ConstantViewField.ROW_ID, i);
            stringObjectMap.put(ConstantViewField.LEVEL, Integer.parseInt(null == map.get(ConstantViewField.LEVEL) ? "0" : map.get(ConstantViewField.LEVEL).toString()) + 1);
        }
        treeList.addAll(list);
    }

    public void threeTree(Map<String, Object> map, List<Map<String, Object>> treeList, int flag) {
        map.put(ConstantViewField.PARENT_ID, 0);
        List<Map<String, Object>> list = this.selectViewFieldListPrecise(map);
        Map<String, List<Map<String, Object>>> threeList = list.stream().collect(Collectors.groupingBy(this::key));
        if (threeList.containsKey("")) {
            noDimension(map, treeList, flag);
        } else {
            long i;
            int j = 0;
            for (String threeStr : threeList.keySet()) {
                Map<String, Object> viewTypeGeoAndRegionAndBgAndMode = new HashMap<>();
                viewTypeGeoAndRegionAndBgAndMode.put(ConstantViewField.PAGE_KEY_NAME, map.get(ConstantViewField.PAGE_KEY_NAME));
                viewTypeGeoAndRegionAndBgAndMode.put(ConstantViewField.PAGE_KEY, map.get(ConstantViewField.PAGE_KEY));
                viewTypeGeoAndRegionAndBgAndMode.put(ConstantViewField.VIEW_TYPE, map.get(ConstantViewField.VIEW_TYPE));
                String[] split = threeStr.split("@");
                for (String s1 : split) {
                    String[] split1 = s1.split(":");
                    if (split1.length == 2) {
                        viewTypeGeoAndRegionAndBgAndMode.put(split1[0], split1[1]);
                    }
                }
                viewTypeGeoAndRegionAndBgAndMode.put(ConstantViewField.CHILDREN, new ArrayList<>());
                String rowId = map.get(ConstantViewField.ROW_ID).toString() + j;
                i = Long.parseLong(rowId);
                j++;
                viewTypeGeoAndRegionAndBgAndMode.put(ConstantViewField.ROW_ID, i);
                viewTypeGeoAndRegionAndBgAndMode.put(ConstantViewField.LEVEL, 3);
                treeList.add(viewTypeGeoAndRegionAndBgAndMode);
            }
        }
    }

    public void twoTree(Map<String, Object> map, List<Map<String, Object>> treeList) {
        map.put(ConstantViewField.PARENT_ID, 0);
        List<Map<String, Object>> list = this.selectViewFieldAndTemplateAndCondition(map);
        Set<String> viewTypeSet = list.stream().map(map1 -> map1.get(ConstantViewField.VIEW_TYPE).toString()).collect(Collectors.toSet());
        long i;
        int j = 0;
        for (String object : viewTypeSet) {
            Map<String, Object> oneObj = new HashMap<>();
            oneObj.put(ConstantViewField.PAGE_KEY_NAME, map.get(ConstantViewField.PAGE_KEY_NAME));
            oneObj.put(ConstantViewField.PAGE_KEY, map.get(ConstantViewField.PAGE_KEY));
            oneObj.put(ConstantViewField.VIEW_TYPE, object);
            oneObj.put(ConstantViewField.CHILDREN, new ArrayList<>());
            String rowId = map.get(ConstantViewField.ROW_ID).toString() + j;
            i = Long.parseLong(rowId);
            j++;
            oneObj.put(ConstantViewField.ROW_ID, i);
            // 定义级别
            if (!ViewTypeEnum.LIST.name().equals(map.get(ConstantViewField.VIEW_TYPE))) {
                oneObj.put(ConstantViewField.LEVEL, 2);
            } else {
                oneObj.put(ConstantViewField.LEVEL, 3);
            }
            treeList.add(oneObj);
        }
    }

    public PageInfo<Map<String, Object>> one(Map<String, Object> map, List<Map<String, Object>> treeList) {
        ViewField viewField = new ViewField();
        viewField.setStatus(0);
        viewField.setPageKey(null != map.get(ConstantViewField.PAGE_KEY) ? map.get(ConstantViewField.PAGE_KEY).toString() : null);
        List<ViewFieldDO> viewFields = viewFieldManager.selectPageKeyList(viewField);
        long i = 0;
        for (ViewFieldDO field : viewFields) {
            Map<String, Object> oneObj = new HashMap<>();
            oneObj.put(ConstantViewField.PAGE_KEY_NAME, field.getMenuName());
            oneObj.put(ConstantViewField.PAGE_KEY, field.getPageKey());
            oneObj.put(ConstantViewField.CHILDREN, new ArrayList<>());
            oneObj.put(ConstantViewField.ROW_ID, ++i);
            // 定义级别
            oneObj.put(ConstantViewField.LEVEL, 1);
            treeList.add(oneObj);
        }
        PageInfo<ViewFieldDO> paymentDTOPage = new PageInfo<>(viewFields);
        PageInfo<Map<String, Object>> paymentDTOPageInfo = new PageInfo<>(treeList);
        paymentDTOPageInfo.setTotal(paymentDTOPage.getTotal());
        return paymentDTOPageInfo;
    }

    public void noDimension(Map<String, Object> map, List<Map<String, Object>> treeList, int flag) {
        // 四级
        map.remove(ConstantViewField.CONDITION_OBJ_LIST);
        map.remove(ConstantViewField.DIMENSIONS);
        map.remove(ConstantViewField.SORT_FIELD);
        map.remove(ConstantViewField.PARENT_ID);
        int four = paramValidation(map, flag, ConstantViewField.PAGE_KEY, ConstantViewField.VIEW_TYPE);
        if (flag == four) {
            map.put(ConstantViewField.PARENT_ID, 0);
            List<Map<String, Object>> list = this.selectViewFieldListPrecise(map);
            long i;
            int j = 0;
            for (Map<String, Object> stringObjectMap : list) {
                stringObjectMap.put(ConstantViewField.PAGE_KEY_NAME, map.get(ConstantViewField.PAGE_KEY_NAME));
                ViewField viewField = new ViewField();
                viewField.setStatus(0);
                viewField.setParentId(Integer.parseInt(stringObjectMap.get(ConstantViewField.ID).toString()));
                List<ViewFieldDO> viewFieldDOS = viewFieldManager.selectViewFieldList(viewField);
                if (!viewFieldDOS.isEmpty()) {
                    stringObjectMap.put(ConstantViewField.CHILDREN, new ArrayList<>());
                }
                String rowId = map.get(ConstantViewField.ROW_ID).toString() + j;
                i = Long.parseLong(rowId);
                j++;
                stringObjectMap.put(ConstantViewField.ROW_ID, i);
                // 定义级别
                stringObjectMap.put(ConstantViewField.LEVEL, 3);
            }
            treeList.addAll(list);
        } else {
            // N级
            Map<String, Object> other = new HashMap<>();
            other.put(ConstantViewField.PARENT_ID, Integer.parseInt(map.get(ConstantViewField.ID).toString()));
            List<Map<String, Object>> list = this.selectViewFieldListPrecise(other);
            long i;
            int j = 0;
            for (Map<String, Object> stringObjectMap : list) {
                stringObjectMap.put(ConstantViewField.PAGE_KEY_NAME, map.get(ConstantViewField.PAGE_KEY_NAME));
                ViewField viewField = new ViewField();
                viewField.setStatus(0);
                viewField.setParentId(Integer.parseInt(stringObjectMap.get(ConstantViewField.ID).toString()));
                List<ViewFieldDO> viewFieldDOS = viewFieldManager.selectViewFieldList(viewField);
                if (!viewFieldDOS.isEmpty()) {
                    stringObjectMap.put(ConstantViewField.CHILDREN, new ArrayList<>());
                }
                String rowId = map.get(ConstantViewField.ROW_ID).toString() + j;
                i = Long.parseLong(rowId);
                j++;
                stringObjectMap.put(ConstantViewField.ROW_ID, i);
                // 定义级别
                stringObjectMap.put(ConstantViewField.LEVEL, Integer.parseInt(null == map.get(ConstantViewField.LEVEL) ? "0" : map.get(ConstantViewField.LEVEL).toString()) + 1);
            }
            treeList.addAll(list);
        }
    }

    @Override
    public List<Map<String, Object>> treeSyncData(Map<String, Object> map) {
        map.put(ConstantViewField.PAGE_KEY_NAME, null);
        //前端为了公用公共组件，把返回的数据结构和菜单的结构一样。rowId 行ID.只查询parentID=0的
        List<Map<String, Object>> treeList = new ArrayList<>();
        // 一级 没有传参数
        int flag = 1;
        int one = paramValidation(map, flag);
        if (flag == one) {
            one(treeList);
        } else {
            // 二级 只传了 pageKey
            int two = paramValidation(map, flag, ConstantViewField.PAGE_KEY);
            if (flag == two) {
                two(map, treeList);
            } else {
                twoElse(map, treeList, flag);
            }
        }
        for (Map<String, Object> stringObjectMap : treeList) {
            stringObjectMap.put(ConstantViewField.TEMPLATE_FIELD_JSON, null);
            stringObjectMap.put(ConstantViewField.FIELD_JSON, null);
        }
        return treeList;
    }

    public void twoElse(Map<String, Object> map, List<Map<String, Object>> treeList, int flag) {
        if (!ViewTypeEnum.LIST.name().equals(map.get(ConstantViewField.VIEW_TYPE))) {
            //  三级 只传了 pageKey、viewType
            int three = paramValidation(map, flag, ConstantViewField.PAGE_KEY, ConstantViewField.VIEW_TYPE);
            if (flag == three) {
                three(map, treeList);
            } else {
                threeElse(map, treeList, flag);
            }
        } else {
            // 四级
            int four = paramValidation(map, flag, ConstantViewField.PAGE_KEY, ConstantViewField.VIEW_TYPE);
            if (flag == four) {
                four(map, treeList);
            } else {
                // N级
                nLevel(map, treeList);
            }
        }
    }

    private void threeElse(Map<String, Object> map, List<Map<String, Object>> treeList, int flag) {
        // 四级
        List<SysDictData> dataList = sysDictDataService.selectDictDataByTypeCode(ConstantViewField.VIEW_FIELD_DIMENSIONS);
        List<String> collect = dataList.stream().map(SysDictData::getDictValue).collect(Collectors.toList());
        collect.add(ConstantViewField.PAGE_KEY);
        collect.add(ConstantViewField.VIEW_TYPE);
        String[] strings = collect.toArray(new String[0]);
        int four = paramValidation(map, flag, strings);
        if (flag == four) {
            four(map, treeList);
        } else {
            // N级
            nLevel(map, treeList);
        }
    }

    private void nLevel(Map<String, Object> map, List<Map<String, Object>> treeList) {
        Map<String, Object> other = new HashMap<>();
        other.put(ConstantViewField.PARENT_ID, Integer.parseInt(map.get(ConstantViewField.ID).toString()));
        List<Map<String, Object>> list = this.selectViewFieldListPrecise(other);
        long i;
        int j = 0;
        for (Map<String, Object> stringObjectMap : list) {
            stringObjectMap.put(ConstantViewField.PAGE_KEY_NAME, stringObjectMap.get(ConstantViewField.DIV_KEY));
            ViewField viewField = new ViewField();
            viewField.setParentId(Integer.parseInt(stringObjectMap.get(ConstantViewField.ID).toString()));
            List<ViewFieldDO> viewFieldDOS = viewFieldManager.selectViewFieldList(viewField);
            if (!viewFieldDOS.isEmpty()) {
                stringObjectMap.put(ConstantViewField.CHILDREN, new ArrayList<>());
            }
            String rowId = map.get(ConstantViewField.ROW_ID).toString() + j;
            i = Long.parseLong(rowId);
            j++;
            stringObjectMap.put(ConstantViewField.ROW_ID, i);
        }
        treeList.addAll(list);
    }

    public void four(Map<String, Object> map, List<Map<String, Object>> treeList) {
        map.put(ConstantViewField.PARENT_ID, 0);
        if (map.containsKey(ConstantViewField.BUSINESS_GROUP) && ConstantViewField.DIMENSION_DEFAULT.equals(map.get(ConstantViewField.BUSINESS_GROUP))) {
            map.remove(ConstantViewField.BUSINESS_GROUP);
        }
        List<Map<String, Object>> list = this.selectViewFieldListPrecise(map);
        long i;
        int j = 0;
        for (Map<String, Object> stringObjectMap : list) {
            stringObjectMap.put(ConstantViewField.PAGE_KEY_NAME, stringObjectMap.get(ConstantViewField.DIV_KEY));
            ViewField viewField = new ViewField();
            viewField.setParentId(Integer.parseInt(stringObjectMap.get(ConstantViewField.ID).toString()));
            List<ViewFieldDO> viewFieldDOS = viewFieldManager.selectViewFieldList(viewField);
            if (!viewFieldDOS.isEmpty()) {
                stringObjectMap.put(ConstantViewField.CHILDREN, new ArrayList<>());
            }
            String rowId = map.get(ConstantViewField.ROW_ID).toString() + j;
            i = Long.parseLong(rowId);
            j++;
            stringObjectMap.put(ConstantViewField.ROW_ID, i);
        }
        treeList.addAll(list);
    }

    public void three(Map<String, Object> map, List<Map<String, Object>> treeList) {
        map.put(ConstantViewField.PARENT_ID, 0);
        List<Map<String, Object>> list = this.selectViewFieldListPrecise(map);
        Map<String, List<Map<String, Object>>> threeList = list.stream().collect(Collectors.groupingBy(this::key));
        long i;
        int j = 0;
        for (String threeStr : threeList.keySet()) {
            Map<String, Object> viewTypeGeoAndRegionAndBgAndMode = new HashMap<>();
            viewTypeGeoAndRegionAndBgAndMode.put(ConstantViewField.PAGE_KEY_NAME, StringUtils.isBlank(threeStr) ? ConstantViewField.DIMENSION_DEFAULT : threeStr);
            viewTypeGeoAndRegionAndBgAndMode.put(ConstantViewField.PAGE_KEY, map.get(ConstantViewField.PAGE_KEY));
            viewTypeGeoAndRegionAndBgAndMode.put(ConstantViewField.VIEW_TYPE, map.get(ConstantViewField.VIEW_TYPE));
            String[] split = threeStr.split("@");
            for (String s1 : split) {
                String[] split1 = s1.split(":");
                if (split1.length == 2) {
                    viewTypeGeoAndRegionAndBgAndMode.put(split1[0], split1[1]);
                } else {
                    //模板没有维度，添加一个默认维度
                    viewTypeGeoAndRegionAndBgAndMode.put(ConstantViewField.BUSINESS_GROUP, ConstantViewField.DIMENSION_DEFAULT);
                }
            }
            viewTypeGeoAndRegionAndBgAndMode.put(ConstantViewField.CHILDREN, new ArrayList<>());
            String rowId = map.get(ConstantViewField.ROW_ID).toString() + j;
            i = Long.parseLong(rowId);
            j++;
            viewTypeGeoAndRegionAndBgAndMode.put(ConstantViewField.ROW_ID, i);
            treeList.add(viewTypeGeoAndRegionAndBgAndMode);
        }
    }

    public void two(Map<String, Object> map, List<Map<String, Object>> treeList) {
        map.put(ConstantViewField.PARENT_ID, 0);
        List<Map<String, Object>> list = this.selectViewFieldAndTemplateAndCondition(map);
        Set<String> viewTypeSet = list.stream().map(map1 -> map1.get(ConstantViewField.VIEW_TYPE).toString()).collect(Collectors.toSet());
        long i;
        int j = 0;
        for (String object : viewTypeSet) {
            Map<String, Object> oneObj = new HashMap<>();
            oneObj.put(ConstantViewField.PAGE_KEY_NAME, object);
            oneObj.put(ConstantViewField.PAGE_KEY, map.get(ConstantViewField.PAGE_KEY));
            oneObj.put(ConstantViewField.VIEW_TYPE, object);
            oneObj.put(ConstantViewField.CHILDREN, new ArrayList<>());
            String rowId = map.get(ConstantViewField.ROW_ID).toString() + j;
            i = Long.parseLong(rowId);
            j++;
            oneObj.put(ConstantViewField.ROW_ID, i);

            treeList.add(oneObj);
        }
    }

    private void one(List<Map<String, Object>> treeList) {
        ViewField viewField = new ViewField();
        viewField.setStatus(0);
        List<ViewFieldDO> viewFields = viewFieldManager.selectPageKeyList(viewField);
        long i = 0;
        for (ViewFieldDO field : viewFields) {
            Map<String, Object> oneObj = new HashMap<>();
            oneObj.put(ConstantViewField.PAGE_KEY_NAME, field.getMenuName());
            oneObj.put(ConstantViewField.PAGE_KEY, field.getPageKey());
            oneObj.put(ConstantViewField.CHILDREN, new ArrayList<>());
            oneObj.put(ConstantViewField.ROW_ID, ++i);
            treeList.add(oneObj);
        }
    }


    private int paramValidation(Map<String, Object> map, int flag, String... param) {
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            if (!ObjectUtils.isEmpty(stringObjectEntry.getValue()) && org.apache.commons.lang3.StringUtils.isNotBlank(stringObjectEntry.getValue().toString())
                    && !ConstantViewField.PAGE_NUM.equals(stringObjectEntry.getKey()) && !ConstantViewField.PAGE_SIZE.equals(stringObjectEntry.getKey())
                    && !ConstantViewField.ROW_ID.equals(stringObjectEntry.getKey()) && !ConstantViewField.LEVEL.equals(stringObjectEntry.getKey())
                    && !ConstantViewField.PAGE_KEY_NAME.equals(stringObjectEntry.getKey())) {
                flag = getFlag(flag, stringObjectEntry, param);
            }
        }
        return flag;
    }

    private static int getFlag(int flag, Map.Entry<String, Object> stringObjectEntry, String[] param) {
        if (param.length > 0) {
            List<String> paramList = Arrays.stream(param).collect(Collectors.toList());
            if (!paramList.contains(stringObjectEntry.getKey())) {
                flag++;
            }
        } else {
            flag++;
        }
        return flag;
    }

    @Override
    public List<Map<String, String>> columnHeaderByCopy() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> columnHeaderPageKey = new LinkedHashMap<>();
        columnHeaderPageKey.put(ConstantViewField.FIELD, ConstantViewField.PAGE_KEY_NEW);
        columnHeaderPageKey.put(ConstantViewField.LABEL, "Page Key");
        list.add(columnHeaderPageKey);
        Map<String, String> columnHeaderViewType = new LinkedHashMap<>();
        columnHeaderViewType.put(ConstantViewField.FIELD, ConstantViewField.VIEW_TYPE_NEW);
        columnHeaderViewType.put(ConstantViewField.LABEL, "View Type");
        list.add(columnHeaderViewType);
        List<SysDictData> dataList = sysDictDataService.selectDictDataByTypeCode(ConstantViewField.VIEW_FIELD_DIMENSIONS);
        for (SysDictData sysDictData : dataList) {
            Map<String, String> columnHeader = new LinkedHashMap<>();
            columnHeader.put(ConstantViewField.LABEL, sysDictData.getDictLabel());
            columnHeader.put(ConstantViewField.FIELD, sysDictData.getDictValue() + "New");
            list.add(columnHeader);
        }
        return list;
    }

    /**
     * 修改页面字段展示信息
     *
     * @param viewField 页面字段展示信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateViewField(Map<String, Object> viewField) {
        for (Map.Entry<String, Object> stringObjectEntry : viewField.entrySet()) {
            if (StringUtils.isEmpty(stringObjectEntry.getValue().toString())) {
                viewField.put(stringObjectEntry.getKey(), null);
            }
        }
        // 防止子级维度被修改
        Integer id = Integer.parseInt(viewField.get(ConstantViewField.ID).toString());
        if (ObjectUtils.isEmpty(id)) {
            throw new OtmpException("Id is null");
        }

        Set<String> viewFields = viewField.keySet();
        // 过滤选中的维度
        Set<String> dimensionSet = viewFields.stream().filter(s -> !FIELD_IGNORE.contains(s) && null != viewField.get(s)).collect(Collectors.toSet());
        this.verifyDimensions(null, viewField.get(ConstantViewField.FIELD_JSON).toString(), dimensionSet);

        JSONObject jsonObject = JSON.parseObject(viewField.get(ConstantViewField.FIELD_JSON).toString());
        JSONArray jsonArray = jsonObject.getJSONArray(ConstantFieldEnum.FIELDS.getName());
        List<JSONObject> fields = jsonArray.toJavaList(JSONObject.class);
        for (JSONObject field : fields) {
            if (!field.containsKey(ConstantFieldEnum.CHECKED.getName())) {
                field.put(ConstantFieldEnum.CHECKED.getName(), false);
            }
        }

        viewField.put(ConstantViewField.FIELD_JSON, jsonObject.toJSONString());
        viewField.put(ConstantViewField.UPDATE_TIME, DateUtils.getNowDate());
        int i = viewFieldManager.updateViewFieldMap(viewField);

        // 更新其他模板，数据库里面用的不是同一套模板
        updateOther(id, jsonObject, fields);

        // 新增、修改
        ViewConditionValue viewConditionValueQuery = new ViewConditionValue();
        viewConditionValueQuery.setFieldId(id);
        List<ViewConditionValueDO> viewConditionValues = viewConditionValueService.selectViewConditionValueList(viewConditionValueQuery);
        Map<String, ViewConditionValueDO> collect = viewConditionValues.stream().collect(Collectors.toMap(ViewConditionValueDO::getCondition, viewConditionValue -> viewConditionValue));
        for (String s : dimensionSet) {
            ViewConditionValueDO viewConditionValue1 = collect.get(s);
            if (null == viewConditionValue1) {
                addConditionValue(viewField, s);
            } else {
                upadteCondionValue(viewField, s);
            }
        }
        //删除维度
        Set<String> deleteDimension = collect.keySet().stream().filter(s -> !dimensionSet.contains(s)).collect(Collectors.toSet());
        for (String s : deleteDimension) {
            viewConditionValueService.deleteViewConditionValueByFieldIdAndCondition(id, s);
        }
        return i;
    }

    public void upadteCondionValue(Map<String, Object> viewField, String s) {
        Map<String, String> map = new HashMap<>();
        ViewConditionValue viewConditionValueUpdate = new ViewConditionValue();
        viewConditionValueUpdate.setFieldId(Integer.parseInt(viewField.get(ConstantViewField.ID).toString()));
        viewConditionValueUpdate.setCondition(s);
        String value = viewField.get(s).toString();
        if (ConstantViewField.ALL_VALUE.equals(value)) {
            businessGroupAndGeoCodeCondionAdd(viewField, map);
            salesOfficeCodeConditionAdd(viewField, s, map);
            map.put(ConstantViewField.DIMENSIONS_TYPE, s);
            List<SysDictData> dataList = this.fieldDimensionsSelect(map);
            List<String> collect1 = dataList.stream().map(SysDictData::getDictValue).collect(Collectors.toList());
            value = org.apache.commons.lang3.StringUtils.join(collect1, ",");
        }
        value = value.replace(ConstantViewField.ALL_VALUE_SUB, "");
        viewField.put(s, value);
        viewConditionValueUpdate.setConditionValue(value);
        viewConditionValueUpdate.setUpdateBy(viewField.get(ConstantViewField.UPDATE_BY).toString());
        viewConditionValueUpdate.setUpdateTime(DateUtils.getNowDate());
        viewConditionValueService.updateViewConditionValue(viewConditionValueUpdate);
    }

    private void addConditionValue(Map<String, Object> viewField, String s) {
        Map<String, String> map = new HashMap<>();
        ViewConditionValue viewConditionValue = new ViewConditionValue();
        viewConditionValue.setFieldId(Integer.parseInt(viewField.get(ConstantViewField.ID).toString()));
        viewConditionValue.setCondition(s);
        String value = viewField.get(s).toString();
        if (ConstantViewField.ALL_VALUE.equals(value)) {
            businessGroupAndGeoCodeCondionAdd(viewField, map);
            salesOfficeCodeConditionAdd(viewField, s, map);
            map.put(ConstantViewField.DIMENSIONS_TYPE, s);
            List<SysDictData> dataList = this.fieldDimensionsSelect(map);
            List<String> collect1 = dataList.stream().map(SysDictData::getDictValue).collect(Collectors.toList());
            value = org.apache.commons.lang3.StringUtils.join(collect1, ",");
        }
        value = value.replace(ConstantViewField.ALL_VALUE_SUB, "");
        if (null != viewField.get(ViewFieldDimensionsEnum.GEO_CODE.getName()) &&
                !"".equals(viewField.get(ViewFieldDimensionsEnum.GEO_CODE.getName()).toString())) {
            viewField.put(GEO_CODE, value);
        } else {
            viewField.put(s, value);
        }
        viewConditionValue.setConditionValue(value);
        viewConditionValue.setCreateBy(viewField.get(ConstantViewField.CREATE_BY).toString());
        viewConditionValue.setUpdateBy(viewField.get(ConstantViewField.UPDATE_BY).toString());
        viewConditionValue.setCreateTime(DateUtils.getNowDate());
        viewConditionValue.setUpdateTime(DateUtils.getNowDate());
        viewConditionValueService.insertViewConditionValue(viewConditionValue);
    }

    private void updateOther(Integer id, JSONObject jsonObject, List<JSONObject> fields) {
        ViewTemplate viewTemplate = new ViewTemplate();
        viewTemplate.setFieldId(id);
        viewTemplate.setStatus(0);
        List<ViewTemplateDO> viewTemplates = viewTemplateManager.selectViewTemplateList(viewTemplate);
        for (ViewTemplateDO field : viewTemplates) {
            JSONObject oldJsonObject = JSON.parseObject(field.getFieldJson());
            if (null != oldJsonObject) {
                JSONArray oldJsonArray = oldJsonObject.getJSONArray(ConstantFieldEnum.FIELDS.getName());
                List<JSONObject> oldFields = oldJsonArray.toJavaList(JSONObject.class);
                for (JSONObject newFieldJsonEntity : fields) {
                    for (JSONObject oldField : oldFields) {
                        Boolean checked = (Boolean) oldField.get(ConstantFieldEnum.CHECKED.getName());
                        if (newFieldJsonEntity.get(ConstantFieldEnum.V_MODEL.getName()).equals(oldField.get(ConstantFieldEnum.V_MODEL.getName()))) {
                            newFieldJsonEntity.put(ConstantFieldEnum.CHECKED.getName(), checked);
                        }
                    }
                }
            }
            ViewTemplate oldViewTemplate = new ViewTemplate();
            oldViewTemplate.setId(field.getId());
            oldViewTemplate.setFieldJson(JSON.toJSONString(jsonObject));
            viewTemplateManager.updateViewTemplate(oldViewTemplate);
        }
    }

    private static void businessGroupAndGeoCodeCondionAdd(Map<String, Object> viewField, Map<String, String> map) {
        if (null != viewField.get(ViewFieldDimensionsEnum.BUSINESS_GROUP.getName()) && !"".equals(viewField.get(ViewFieldDimensionsEnum.BUSINESS_GROUP.getName()))) {
            map.put(ConstantViewField.BUSINESS_GROUP, viewField.get(ViewFieldDimensionsEnum.BUSINESS_GROUP.getName()).toString());
        }
        if (null != viewField.get(ViewFieldDimensionsEnum.GEO_CODE.getName()) && !"".equals(viewField.get(ViewFieldDimensionsEnum.GEO_CODE.getName()).toString())) {
            map.put(ConstantViewField.GEO_CODE, viewField.get(ViewFieldDimensionsEnum.GEO_CODE.getName()).toString());
        }
    }


    /**
     * 删除页面字段展示信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteViewFieldByIds(String ids) {
        return viewFieldManager.deleteViewFieldByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除页面字段展示信息信息
     *
     * @param id 页面字段展示信息ID
     * @return 结果
     */
    @Override
    public int deleteViewFieldById(Integer id) {
        return viewFieldManager.deleteViewFieldById(id);
    }

    @Override
    public List<ViewFieldInfo> fields(String relName) {
        ViewFieldInfo viewFieldInfo = new ViewFieldInfo();
        viewFieldInfo.setRelname(relName);
        List<ViewFieldInfo> viewFieldInfos = viewFieldManager.selectViewFieldInfoList(viewFieldInfo);
        for (ViewFieldInfo fieldInfo : viewFieldInfos) {
            fieldInfo.setFieldTest(fieldInfo.getField());
            fieldInfo.setField(StringUtils.toCamelCase(fieldInfo.getField()));
            if (StringUtils.isEmpty(fieldInfo.getFieldLabel())) {
                fieldInfo.setFieldLabel(StringUtils.humpToLabel(fieldInfo.getField()));
            }
        }
        return viewFieldInfos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int disableViewField(ViewField viewField) {
        ViewField viewFieldDO = new ViewField();
        BeanUtils.copyProperties(viewField, viewFieldDO);
        viewFieldDO.setStatus(1);
        viewFieldManager.updateViewField(viewFieldDO);
        viewTemplateManager.fakeDeleteTemplateGroup(viewField.getId());
        ViewFieldDO viewField0 = viewFieldManager.selectViewFieldById(viewField.getId());
        childDelete(viewField0.getId());
        return 1;
    }

    private void childDelete(Integer parentId) {
        ViewField child = new ViewField();
        child.setParentId(parentId);
        List<ViewFieldDO> viewFields = viewFieldManager.selectViewFieldList(child);
        for (ViewFieldDO field : viewFields) {
            ViewField viewFieldDO = new ViewField();
            org.springframework.beans.BeanUtils.copyProperties(field, viewFieldDO);
            viewFieldDO.setStatus(1);
            viewFieldManager.updateViewField(viewFieldDO);
            viewTemplateManager.fakeDeleteTemplateGroup(field.getId());
            childDelete(field.getId());
        }
    }

    @Override
    public List<ViewTemplate> pageKeyList(Map<String, Object> viewField) {
        //通过页面key查询出列表页、编辑页的所有块key和默认要展示的字段

        //查询参数
        Map<String, Object> query = new HashMap<>();
        query.put(ConstantViewField.PAGE_KEY, viewField.get(ConstantViewField.PAGE_KEY));
        query.put(ConstantViewField.VIEW_TYPE, viewField.get(ConstantViewField.VIEW_TYPE));
        query.put(ConstantViewField.POPOVER_BOUND_FIELD, viewField.get(ConstantViewField.POPOVER_BOUND_FIELD));
        if (!viewField.containsKey(ConstantViewField.PARENT_ID) || null == viewField.get(ConstantViewField.PARENT_ID) ||
                "".equals(viewField.get(ConstantViewField.PARENT_ID))) {
            query.put(ConstantViewField.PARENT_ID, 0);
        } else {
            query.put(ConstantViewField.PARENT_ID, viewField.get(ConstantViewField.PARENT_ID));
        }
        query.put(ConstantViewField.SORT_FIELD, ConstantViewField.SORT);
        query.put(ConstantViewField.IS_DEFAULT, 1);
        List<Map<String, Object>> viewFields = this.selectViewFieldAndTemplateAndCondition(query);
        if (viewFields.isEmpty()) {
            throw new OtmpException("No default template");
        }
        List<ViewTemplate> viewTemplates = new ArrayList<>();
        for (Map<String, Object> field : viewFields) {
            ViewConditionValue viewConditionValue = new ViewConditionValue();
            viewConditionValue.setFieldId(Integer.parseInt(field.get(ConstantViewField.ID).toString()));
            List<ViewConditionValueDO> viewConditionValueDOList = viewConditionValueService.selectViewConditionValueList(viewConditionValue);
            //
            int i = 0;
            i = verifyDimension(viewField, viewConditionValueDOList, i);
            if (i != viewConditionValueDOList.size()) {
                continue;
            }
            // 默认模板
            templateView(viewField, viewTemplates, field);
        }
        return viewTemplates;
    }

    private void templateView(Map<String, Object> viewField, List<ViewTemplate> viewTemplates, Map<String, Object> field) {
        if (null != field.get(ConstantViewField.IS_DEFAULT) && 1 == Integer.parseInt(field.get(ConstantViewField.IS_DEFAULT).toString())) {
            viewTemplateService.checkedFields(field, viewField);
            ViewTemplate viewTemplate = JSON.parseObject(JSON.toJSONString(field), ViewTemplate.class);
            viewTemplates.add(viewTemplate);
            // 系统模板
        }
    }

    private static int verifyDimension(Map<String, Object> viewField, List<ViewConditionValueDO> viewConditionValueDOList, int i) {
        for (ViewConditionValueDO viewConditionValueDO : viewConditionValueDOList) {
            if (null == viewConditionValueDO.getConditionValue()) {
                throw new OtmpException("Field dimension value cannot be null. ");
            }
            // 通过查询出来的维度key，获取页面传过来的value，如果为空，那么说明页面没有传这个维度的值。
            if (null == viewField.get(viewConditionValueDO.getCondition())) {
                throw new OtmpException("Missing dimension field [" + viewConditionValueDO.getCondition() + "]. ");
            }
            if (viewConditionValueDO.getConditionValue().contains(viewField.get(viewConditionValueDO.getCondition()).toString())) {
                i++;
            }
        }
        return i;
    }

    @Override
    public List<ViewFieldDO> selectFieldTree(ViewField viewField) {

        List<ViewFieldDO> viewFields = viewFieldManager.selectPageKeyList(viewField);
        for (ViewFieldDO field : viewFields) {
            viewField.setPageKey(field.getPageKey());
            List<ViewFieldDO> fields = viewFieldManager.selectViewFieldList(viewField);
            field.setPageKey(field.getMenuName());
            Map<String, List<ViewFieldDO>> viewTypes = fields.stream().collect(Collectors.groupingBy(ViewFieldDO::getViewType));
            List<ViewFieldDO> children = new ArrayList<>();
            for (String s : viewTypes.keySet()) {
                ViewFieldDO viewType = new ViewFieldDO();
                viewType.setViewType(s);
                children.add(viewType);
            }
            field.setChildren(children);
            buildFiled(field, viewTypes);
        }
        return viewFields;

    }

    private static void buildFiled(ViewFieldDO field, Map<String, List<ViewFieldDO>> viewTypes) {
        for (ViewFieldDO child : field.getChildren()) {
            List<ViewFieldDO> viewFields1 = viewTypes.get(child.getViewType());
            for (ViewFieldDO viewField1 : viewFields1) {
                if (DivTypeEnum.FROM.getCode().equals(viewField1.getType())) {
                    viewField1.setTypeName(DivTypeEnum.FROM.getType());
                } else {
                    viewField1.setTypeName(DivTypeEnum.LIST.getType());
                }
                if (0 == viewField1.getStatus()) {
                    viewField1.setStatusName("No");
                } else {
                    viewField1.setStatusName("Yes");
                }
            }
            viewFields1.sort(Comparator.comparing(ViewFieldDO::getSort));
            child.setChildren(viewFields1);
        }
    }

    @Override
    public List<ViewFieldDO> selectFieldTiled(ViewField viewField) {
        return viewFieldManager.selectViewFieldList(viewField);
    }

    private String key(Map<String, Object> map) {
        ViewConditionValue viewConditionValue = new ViewConditionValue();
        viewConditionValue.setFieldId(Integer.parseInt(map.get(ConstantViewField.ID).toString()));
        List<ViewConditionValueDO> viewConditionValues = viewConditionValueService.selectViewConditionValueList(viewConditionValue);
        viewConditionValues.sort(Comparator.comparing(ViewConditionValueDO::getCondition));
        Map<String, String> dimensionMap = viewConditionValues.stream().collect(Collectors.toMap(ViewConditionValueDO::getCondition, ViewConditionValueDO::getConditionValue));

        StringBuilder k = new StringBuilder();
        for (String s : dimensionMap.keySet()) {
            String[] valueArray = map.get(s).toString().split(",");
            Arrays.sort(valueArray);
            k.append(s).append(":").append(null == map.get(s) ? "" : org.apache.commons.lang3.StringUtils.strip(Arrays.toString(valueArray).replace(", ", ","), "[]") + "@");
        }
        return k.toString();
    }

    @Override
    public List<ViewFieldDO> divSelect(ViewField viewField) {
        viewField.setStatus(0);
        List<ViewFieldDO> viewFields = this.selectViewFieldList(viewField);
        for (ViewFieldDO field : viewFields) {
            String str = "";
            if (StringUtils.isNotEmpty(field.getGeoCode())) {
                str = str + "[GEO = " + field.getGeoCode() + "]";
            }
            if (StringUtils.isNotEmpty(field.getRegion())) {
                str = str + "[Region = " + field.getRegion() + "]";
            }
            if (StringUtils.isNotEmpty(field.getBusinessGroup())) {
                str = str + "[BusinessGroup = " + field.getBusinessGroup() + "]";
            }
            if (StringUtils.isNotEmpty(field.getPaymentMode())) {
                str = str + "[PaymentMode = " + field.getPaymentMode() + "]";
            }
            field.setDivKey(field.getDivKey() + "->" + str);
        }
        return viewFields;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void repairData(ViewField viewField) {
        if (StringUtils.isNotEmpty(viewField.getPageKey())) {
            List<ViewFieldDO> viewFields = viewFieldManager.selectViewFieldList(viewField);
            for (ViewFieldDO field : viewFields) {
                String fieldJson = field.getFieldJson();
                JSONObject jsonObject = JSON.parseObject(fieldJson);
                JSONArray jsonArray = jsonObject.getJSONArray(ConstantFieldEnum.FIELDS.getName());
                List<JSONObject> fieldList = jsonArray.toJavaList(JSONObject.class);
                temporaryChanges(fieldList);
                field.setFieldJson(JSON.toJSONString(jsonObject));
                ViewField viewFieldDO = new ViewField();
                org.springframework.beans.BeanUtils.copyProperties(field, viewFieldDO);
                viewFieldManager.updateViewField(viewFieldDO);
                ViewTemplate viewTemplate = new ViewTemplate();
                viewTemplate.setFieldId(field.getId());
                List<ViewTemplateDO> viewTemplates = viewTemplateService.selectViewTemplateList(viewTemplate);
                for (ViewTemplateDO template : viewTemplates) {
                    String templateFieldJson = template.getFieldJson();
                    if (StringUtils.isNotEmpty(templateFieldJson)) {
                        JSONObject templateJsonObject = JSON.parseObject(templateFieldJson);
                        JSONArray templateJsonObjectFieldListArray = jsonObject.getJSONArray(ConstantFieldEnum.FIELDS.getName());
                        List<JSONObject> templateJsonObjectFieldList = templateJsonObjectFieldListArray.toJavaList(JSONObject.class);
                        setConfig(templateJsonObjectFieldList);
                        template.setFieldJson(JSON.toJSONString(templateJsonObject));
                        ViewTemplate viewTemplate1 = new ViewTemplate();
                        org.springframework.beans.BeanUtils.copyProperties(template, viewTemplate1);
                        viewTemplateService.updateViewTemplate(viewTemplate1);
                    }
                }
            }
        }
    }

    private static void setConfig(List<JSONObject> templateJsonObjectFieldList) {
        for (JSONObject object : templateJsonObjectFieldList) {
            if (object.containsKey(ConstantFieldEnum.CONFIG.getName())) {
                JSONObject config = (JSONObject) object.get(ConstantFieldEnum.CONFIG.getName());
                if (config.containsKey(ConstantFieldEnum.TAG_ICON.getName())) {
                    String tagIcon = (String) config.get(ConstantFieldEnum.TAG_ICON.getName());
                    if ("input".equals(tagIcon) && object.containsKey(ConstantFieldEnum.PLACEHOLDER.getName())) {
                        object.put(ConstantFieldEnum.PLACEHOLDER.getName(), "Please Enter");
                    }
                }
            }
        }
    }

    private static void temporaryChanges(List<JSONObject> fieldList) {
        for (JSONObject object : fieldList) {
            if (object.containsKey(ConstantFieldEnum.CONFIG.getName())) {
                JSONObject config = (JSONObject) object.get(ConstantFieldEnum.CONFIG.getName());
                if (config.containsKey(ConstantFieldEnum.TAG_ICON.getName())) {
                    String tagIcon = (String) config.get(ConstantFieldEnum.TAG_ICON.getName());
                    if ("input".equals(tagIcon) && object.containsKey(ConstantFieldEnum.PLACEHOLDER.getName())) {
                        object.put("placeholder", "Please Enter");
                    }
                }
            }
        }
    }

    @Override
    public List<ViewFieldExcel> export(ViewField viewField) {
        if (null == viewField.getId()) {
            throw new OtmpException("Id is null.");
        }
        ViewFieldDO viewField1 = viewFieldManager.selectViewFieldById(viewField.getId());
        String fieldJson = viewField1.getFieldJson();
        if (StringUtils.isEmpty(fieldJson)) {
            return Collections.emptyList();
        }
        List<ViewFieldExcel> excels = new ArrayList<>();
        JSONObject jsonObject = JSON.parseObject(fieldJson);
        JSONArray jsonArray = jsonObject.getJSONArray(ConstantFieldEnum.FIELDS.getName());
        List<JSONObject> fields = jsonArray.toJavaList(JSONObject.class);
        for (JSONObject field : fields) {
            ViewFieldExcel viewFieldExcel = new ViewFieldExcel();
            vModelAndFieldRangeAndPlaceholderValue(field, viewFieldExcel);
            configValue(field, viewFieldExcel);
            excels.add(viewFieldExcel);
        }
        return excels;
    }

    private static void vModelAndFieldRangeAndPlaceholderValue(JSONObject field, ViewFieldExcel viewFieldExcel) {
        if (field.containsKey(ConstantFieldEnum.V_MODEL.getName()))
            viewFieldExcel.setVModel(field.get(ConstantFieldEnum.V_MODEL.getName()) == null ? null : field.get(ConstantFieldEnum.V_MODEL.getName()).toString());
        else viewFieldExcel.setVModel(null);
        if (field.containsKey(ConstantFieldEnum.FIELD_RANGE.getName()))
            viewFieldExcel.setFieldRange(field.get(ConstantFieldEnum.FIELD_RANGE.getName()) == null ? null : field.get(ConstantFieldEnum.FIELD_RANGE.getName()).toString());
        else viewFieldExcel.setFieldRange(null);
        if (field.containsKey(ConstantFieldEnum.PLACEHOLDER.getName()))
            viewFieldExcel.setPlaceholder(field.get(ConstantFieldEnum.PLACEHOLDER.getName()) == null ? null : field.get(ConstantFieldEnum.PLACEHOLDER.getName()).toString());
        else viewFieldExcel.setPlaceholder(null);
    }

    private static void configValue(JSONObject field, ViewFieldExcel viewFieldExcel) {
        if (field.containsKey(ConstantFieldEnum.CONFIG.getName())) {
            JSONObject config = (JSONObject) field.get(ConstantFieldEnum.CONFIG.getName());
            labelWidthAndLabelValue(viewFieldExcel, config);

            tagIconAndSpanValue(viewFieldExcel, config);
        }
    }

    private static void tagIconAndSpanValue(ViewFieldExcel viewFieldExcel, JSONObject config) {
        if (config.containsKey(ConstantFieldEnum.TAG_ICON.getName()))
            viewFieldExcel.setTagIconConfig(config.get(ConstantFieldEnum.TAG_ICON.getName()) == null ? null : config.get(ConstantFieldEnum.TAG_ICON.getName()).toString());
        else viewFieldExcel.setTagIconConfig(null);

        if (config.containsKey(ConstantFieldEnum.SPAN.getName()))
            viewFieldExcel.setSpanConfig(config.get(ConstantFieldEnum.SPAN.getName()) == null ? null : Integer.parseInt(config.get(ConstantFieldEnum.SPAN.getName()).toString()));
        else viewFieldExcel.setSpanConfig(null);
    }

    private static void labelWidthAndLabelValue(ViewFieldExcel viewFieldExcel, JSONObject config) {
        if (config.containsKey(ConstantFieldEnum.LABEL_WIDTH.getName()))
            viewFieldExcel.setLabelWidthConfig(config.get(ConstantFieldEnum.LABEL_WIDTH.getName()) == null ? null : config.get(ConstantFieldEnum.LABEL_WIDTH.getName()).toString());
        else viewFieldExcel.setLabelWidthConfig(null);

        if (config.containsKey(ConstantFieldEnum.LABEL.getName()))
            viewFieldExcel.setLabelConfig(config.get(ConstantFieldEnum.LABEL.getName()) == null ? null : config.get(ConstantFieldEnum.LABEL.getName()).toString());
        else viewFieldExcel.setLabelConfig(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importExcel(List<ViewFieldExcel> bizs, String loginName, Integer id) {
        if (null == id) {
            throw new OtmpException("Id is null.");
        }
        if (!bizs.isEmpty()) {
            Map<String, ViewFieldExcel> fieldsExcel = bizs.stream().collect(Collectors.toMap(ViewFieldExcel::getVModel, viewFieldExcel -> viewFieldExcel));
            ViewFieldDO viewField = viewFieldManager.selectViewFieldById(id);
            if (null != viewField && null != viewField.getFieldJson()) {
                JSONObject jsonObject = JSON.parseObject(viewField.getFieldJson());
                JSONArray jsonArray = jsonObject.getJSONArray(ConstantFieldEnum.FIELDS.getName());
                List<JSONObject> fields = jsonArray.toJavaList(JSONObject.class);
                updateField(loginName, fieldsExcel, viewField, jsonObject, fields);
            }
        }
    }

    private void updateField(String loginName, Map<String, ViewFieldExcel> fieldsExcel, ViewFieldDO viewField, JSONObject jsonObject, List<JSONObject> fields) {
        for (JSONObject field : fields) {
            if (!field.containsKey(ConstantFieldEnum.V_MODEL.getName()) || null == field.get(ConstantFieldEnum.V_MODEL.getName())) {
                throw new OtmpException("vModel is null");
            }
            ViewFieldExcel viewFieldExcel = fieldsExcel.get(field.get(ConstantFieldEnum.V_MODEL.getName()).toString());
            if (null != viewFieldExcel) {
                updateJsonField(field, viewFieldExcel);
            }
            viewField.setFieldJson(JSON.toJSONString(jsonObject));
            viewField.setUpdateBy(loginName);
            viewField.setUpdateTime(DateUtils.getNowDate());
            ViewField viewFieldDO = new ViewField();
            org.springframework.beans.BeanUtils.copyProperties(viewField, viewFieldDO);
            viewFieldManager.updateViewField(viewFieldDO);
        }
    }

    private static void updateJsonField(JSONObject field, ViewFieldExcel viewFieldExcel) {
        field.put(ConstantFieldEnum.V_MODEL.getName(), viewFieldExcel.getVModel());
        if (StringUtils.isNotEmpty(viewFieldExcel.getFieldRange())) {
            field.put(ConstantFieldEnum.FIELD_RANGE.getName(), viewFieldExcel.getFieldRange());
        }
        if (field.containsKey(ConstantFieldEnum.PLACEHOLDER.getName())) {
            field.put(ConstantFieldEnum.PLACEHOLDER.getName(), viewFieldExcel.getPlaceholder());
        }
        if (field.containsKey(ConstantFieldEnum.CONFIG.getName())) {
            JSONObject config = (JSONObject) field.get(ConstantFieldEnum.CONFIG.getName());
            if (config.containsKey(ConstantFieldEnum.LABEL_WIDTH.getName())) {
                config.put(ConstantFieldEnum.LABEL_WIDTH.getName(), viewFieldExcel.getLabelWidthConfig());
            }
            if (config.containsKey(ConstantFieldEnum.LABEL.getName())) {
                config.put(ConstantFieldEnum.LABEL.getName(), viewFieldExcel.getLabelConfig());
            }
            if (config.containsKey(ConstantFieldEnum.TAG_ICON.getName())) {
                config.put(ConstantFieldEnum.TAG_ICON.getName(), viewFieldExcel.getTagIconConfig());
            }
            if (config.containsKey(ConstantFieldEnum.SPAN.getName())) {
                config.put(ConstantFieldEnum.SPAN.getName(), viewFieldExcel.getSpanConfig());
            }
        }
    }

    @Override
    public List<JSONObject> completionField(ViewField viewField) {
        if (null != viewField.getId()) {
            ViewFieldDO viewField1 = viewFieldManager.selectViewFieldById(viewField.getId());
            List<JSONObject> fields = buildFiledAll(viewField1);
            if (fields != null) return fields;
        } else {
            return returnAllField(viewField);
        }
        return Collections.emptyList();
    }

    private List<JSONObject> buildFiledAll(ViewFieldDO viewField1) {
        if (null != viewField1) {
            String fieldJson = viewField1.getFieldJson();
            if (StringUtils.isNotEmpty(fieldJson)) {
                JSONObject jsonObject = JSON.parseObject(fieldJson);
                JSONArray jsonArray = jsonObject.getJSONArray(ConstantFieldEnum.FIELDS.getName());
                List<JSONObject> fields = jsonArray.toJavaList(JSONObject.class);
                //获取formId 最大值
                // StringUtils.isNumeric(jsonObject1.getJSONObject(ConstantFieldEnum.__config__.name()).getString(ConstantFieldEnum.formId.name())) 用来过滤前端传过来的字符串
                JSONObject maxJsonObject = fields.stream().filter(jsonObject1 -> org.apache.commons.lang3.StringUtils.isNumeric(jsonObject1.getJSONObject(ConstantFieldEnum.CONFIG.getName()).getString(ConstantFieldEnum.FORM_ID.getName()))).
                        max(Comparator.comparingInt(o -> o.getJSONObject(ConstantFieldEnum.CONFIG.getName()).getIntValue(ConstantFieldEnum.FORM_ID.getName()))).orElse(null);
                Integer formId = 0;
                if (null != maxJsonObject) {
                    formId = maxJsonObject.getJSONObject(ConstantFieldEnum.CONFIG.getName()).getInteger(ConstantFieldEnum.FORM_ID.getName());
                }

                List<ViewFieldInfo> fieldsAll = this.fields(viewField1.getTableName());
                for (ViewFieldInfo viewFieldInfo : fieldsAll) {
                    int i = setChecked(fields, viewFieldInfo);
                    //补全字段
                    formId = fieldComplement(fields, formId, viewFieldInfo, i);
                }
                return fields;
            }
        }
        return Collections.emptyList();
    }

    public List<JSONObject> returnAllField(ViewField viewField) {
        List<JSONObject> fieldsAll = new ArrayList<>();
        List<ViewFieldInfo> fields = this.fields(viewField.getTableName());
        for (int i = 0; i < fields.size(); i++) {
            ViewFieldInfo viewFieldInfo = fields.get(i);
            JSONObject defaultFieldObject = JSON.parseObject(DEFAULT_FIELD);
            JSONObject config = defaultFieldObject.getJSONObject(ConstantFieldEnum.CONFIG.getName());
            config.put(ConstantFieldEnum.FORM_ID.getName(), i);
            config.put(ConstantFieldEnum.RENDER_KEY.getName(), config.get(ConstantFieldEnum.RENDER_KEY.getName()) + RandomUtil.randomInt(3));
            config.put(ConstantFieldEnum.LABEL.getName(), viewFieldInfo.getFieldLabel());
            defaultFieldObject.put(ConstantFieldEnum.V_MODEL.getName(), viewFieldInfo.getField());
            fieldsAll.add(defaultFieldObject);
        }
        return fieldsAll;
    }

    private static Integer fieldComplement(List<JSONObject> fields, Integer formId, ViewFieldInfo viewFieldInfo, int i) {
        if (0 == i) {
            ++formId;
            JSONObject defaultFieldObject = JSON.parseObject(DEFAULT_FIELD);
            JSONObject config = defaultFieldObject.getJSONObject(ConstantFieldEnum.CONFIG.getName());
            config.put(ConstantFieldEnum.FORM_ID.getName(), formId);
            config.put(ConstantFieldEnum.RENDER_KEY.getName(), config.get(ConstantFieldEnum.RENDER_KEY.getName()) + RandomUtil.randomInt(3));
            config.put(ConstantFieldEnum.LABEL.getName(), viewFieldInfo.getFieldLabel());
            defaultFieldObject.put(ConstantFieldEnum.V_MODEL.getName(), viewFieldInfo.getField());
            fields.add(defaultFieldObject);
        }
        return formId;
    }

    private static int setChecked(List<JSONObject> fields, ViewFieldInfo viewFieldInfo) {
        int i = 0;
        for (JSONObject field : fields) {
            if (field.containsKey(ConstantFieldEnum.V_MODEL.getName())) {
                boolean checked = viewFieldInfo.getField().equals(field.get(ConstantFieldEnum.V_MODEL.getName()));
                if (checked) {
                    i = 1;
                    field.putIfAbsent(ConstantFieldEnum.CHECKED.getName(), true);
                    break;
                }
            }
        }
        return i;
    }

    @Override
    public List<Map<String, Object>> selectViewFieldListPrecise(Map<String, Object> map) {
        map.put(ConstantViewField.SORT_FIELD, ConstantViewField.SORT);
        List<SysDictData> dataList = sysDictDataService.selectDictDataByTypeCode(ConstantViewField.VIEW_FIELD_DIMENSIONS);
        List<Map<String, Object>> conditionObjList = new ArrayList<>();
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            if (stringObjectEntry.getKey().equals(ConstantViewField.ROW_ID) || stringObjectEntry.getKey().equals(ConstantViewField.UPDATE_BY) ||
                    stringObjectEntry.getKey().equals(ConstantViewField.PAGE_SIZE) || stringObjectEntry.getKey().equals(ConstantViewField.PAGE_NUM) ||
                    stringObjectEntry.getKey().equals(ConstantViewField.PAGE_KEY_NAME) || stringObjectEntry.getKey().equals(ConstantViewField.CONDITION_OBJ_LIST)
                    || stringObjectEntry.getKey().equals(ConstantViewField.DIMENSIONS) || stringObjectEntry.getKey().equals(ConstantViewField.LEVEL)) {
                continue;
            }
            Map<String, Object> conditionObj = new HashMap<>();
            conditionObj.put(KEY, stringObjectEntry.getKey());
            conditionObj.put(VALUE, stringObjectEntry.getValue());
            conditionObjList.add(conditionObj);
        }
        if (!conditionObjList.isEmpty()) {
            map.put(ConstantViewField.CONDITION_OBJ_LIST, conditionObjList);
        }

        if (!dataList.isEmpty()) {
            List<String> collect = dataList.stream().map(SysDictData::getDictValue).collect(Collectors.toList());
            map.put(ConstantViewField.DIMENSIONS, collect);
        }
        return viewFieldManager.selectViewFieldListPrecise(map);
    }

    @Override
    public String parentViewFieldCode(Integer fieldId) {
        ViewFieldDO viewFieldDO = viewFieldManager.selectViewFieldById(fieldId);
        if (null == viewFieldDO) {
            return null;
        }
        return viewFieldDO.getViewFieldCode();
    }

    /**
     * 获取所有字段的配置
     *
     * @param queryMap
     * @return
     */
    @Override
    public List<JSONObject> getViewField(Map<String, Object> queryMap) {
        List<ViewTemplate> templates = this.pageKeyList(queryMap);
        List<JSONObject> jsonObjects = null;
        if (null != templates && !templates.isEmpty()) {
            jsonObjects = new ArrayList<>(templates.size());
            JSONObject jsonObject;
            for (ViewTemplate viewTemplate : templates) {
                if (null != viewTemplate && StrUtil.isNotBlank(viewTemplate.getFieldJson())) {
                    jsonObject = JSON.parseObject(viewTemplate.getFieldJson());
                    if (null != jsonObject) {
                        jsonObjects.add(jsonObject);
                    }
                }
            }
        }
        return jsonObjects;
    }

}
