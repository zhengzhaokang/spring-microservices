package com.microservices.otmp.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.microservices.otmp.common.constant.UserConstants;
import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.system.common.ConstantViewField;
import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.domain.ViewConditionValue;
import com.microservices.otmp.system.domain.ViewField;
import com.microservices.otmp.system.domain.ViewTemplate;
import com.microservices.otmp.system.domain.entity.ViewFieldDO;
import com.microservices.otmp.system.domain.entity.ViewTemplateDO;
import com.microservices.otmp.system.enums.ConstantFieldEnum;
import com.microservices.otmp.system.enums.ViewFieldDimensionsEnum;
import com.microservices.otmp.system.manager.ViewConditionValueManager;
import com.microservices.otmp.system.manager.ViewFieldManager;
import com.microservices.otmp.system.manager.ViewTemplateManager;
import com.microservices.otmp.system.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 页面展示模板Service业务层处理
 *
 * @author sdms
 * @date 2022-02-15
 */
@Service
public class ViewTemplateServiceImpl implements IViewTemplateService {

    @Autowired
    private ViewTemplateManager viewTemplateManager;
    @Autowired
    private ViewFieldManager viewFieldManager;
    @Autowired
    private IViewFieldService viewFieldService;
    @Autowired
    private IViewUserDefTemplateService viewUserDefTemplateService;
    @Autowired
    private IViewDataPermissionService sysUserRegionService;
    @Autowired
    private IViewConditionValueService viewConditionValueService;
    @Autowired
    private ISysDictDataService sysDictDataService;
    @Autowired
    private ViewConditionValueManager viewConditionValueManager;


    /**
     * 查询页面展示模板
     *
     * @param id 页面展示模板ID
     * @return 页面展示模板
     */
    @Override
    public ViewTemplateDO selectViewTemplateById(Integer id) {
        return viewTemplateManager.selectViewTemplateById(id);
    }

    /**
     * 查询页面展示模板列表
     *
     * @param viewTemplate 页面展示模板
     * @return 页面展示模板
     */
    @Override
    public List<ViewTemplateDO> selectViewTemplateList(ViewTemplate viewTemplate) {
        return viewTemplateManager.selectViewTemplateList(viewTemplate);
    }

    @Override
    public ViewTemplateDO viewTemplateDetails(ViewTemplate viewTemplate) {

        List<ViewTemplateDO> viewTemplates = viewTemplateManager.selectViewTemplateList(viewTemplate);
        //不等于0，展示已经和数据权限绑定的json
        if (viewTemplates.isEmpty()) {
            // 等于0，展示字段属性表的全量json
            ViewFieldDO viewField = viewFieldManager.selectViewFieldById(viewTemplate.getFieldId());
            viewTemplate.setFieldJson(viewField.getFieldJson());
            return analysisField(viewTemplate);
        } else {
            ViewTemplateDO viewTemplate1 = viewTemplates.get(0);
            if (StringUtils.isEmpty(viewTemplate1.getFieldJson())) {
                // 等于0，展示字段属性表的全量json
                ViewFieldDO viewField = viewFieldManager.selectViewFieldById(viewTemplate.getFieldId());
                viewTemplate.setFieldJson(viewField.getFieldJson());
                return analysisField(viewTemplate);
            }
        }
        ViewTemplateDO viewTemp = viewTemplates.get(0);
        ViewTemplate viewTemplate1 = new ViewTemplate();
        org.springframework.beans.BeanUtils.copyProperties(viewTemp, viewTemplate1);
        return analysisField(viewTemplate1);
    }


    /**
     * 新增页面展示模板
     *
     * @param templateView 页面展示模板
     * @return 结果
     */
    @Override
    public int insertViewTemplate(ViewTemplate templateView) {
        //查询原始模板ID
        templateView.setCreateTime(DateUtils.getNowDate());
        templateView.setIsDefault(0);
        return viewTemplateManager.insertViewTemplate(templateView);
    }

    /**
     * 修改页面展示模板
     *
     * @param viewTemplate 页面展示模板
     * @return 结果
     */
    @Override
    public int updateViewTemplate(ViewTemplate viewTemplate) {
        viewTemplate.setUpdateTime(DateUtils.getNowDate());
        return viewTemplateManager.updateViewTemplate(viewTemplate);
    }

    /**
     * 删除页面展示模板对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteViewTemplateByIds(String ids) {
        return viewTemplateManager.deleteViewTemplateByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除页面展示模板信息
     *
     * @param id 页面展示模板ID
     * @return 结果
     */
    @Override
    public int deleteViewTemplateById(Integer id) {
        return viewTemplateManager.deleteViewTemplateById(id);
    }


    @Override
    public int saveTemplate(ViewTemplate templateView) {

        //验证 模板名称 是否 重复
        verifyRepetition(templateView);

        //解析json传增加fields属性，前端使用
        String fieldJson = templateView.getFieldJson();
        JSONObject jsonObject = JSON.parseObject(fieldJson);
        jsonObject.put(ConstantFieldEnum.FIELDS.getName(), templateView.getFields());
        templateView.setFieldJson(jsonObject.toJSONString());
        Integer id = templateView.getId();

        if (id == null || id == 0) {
            return addTemplate(templateView);
        } else {
            return updateTemplate(templateView);
        }
    }

    private int updateTemplate(ViewTemplate templateView) {
        //默认的模板设置为非默认
        if (null != templateView.getIsDefault() && 1 == templateView.getIsDefault()) {
            ViewTemplate isDefault = new ViewTemplate();
            isDefault.setIsDefault(1);
            isDefault.setItCode(templateView.getItCode());
            isDefault.setFieldId(templateView.getFieldId());
            List<ViewTemplateDO> viewTemplates = viewTemplateManager.selectViewTemplateList(isDefault);
            for (ViewTemplateDO viewTemplate : viewTemplates) {
                viewTemplate.setIsDefault(0);
                ViewTemplate viewTemplate1 = new ViewTemplate();
                org.springframework.beans.BeanUtils.copyProperties(viewTemplate, viewTemplate1);
                viewTemplateManager.updateViewTemplate(viewTemplate1);
            }
        }

        ViewTemplateDO viewTemplateDO = viewTemplateManager.selectViewTemplateById(templateView.getId());
        templateView.setUpdateTime(DateUtils.getNowDate());
        //用户才会进入这个判断
        if (!UserConstants.SYSTEM_TEMPLATE.equals(templateView.getItCode()) && UserConstants.SYSTEM_TEMPLATE.equals(viewTemplateDO.getItCode())) {
            //默认模板不允许修改ITCode，itCode是用来区分是系统模板还是用户模板。SYS_TEMPLATE是系统模板，如果修改了，那就没有系统模板
            throw new OtmpException("You can modify only user-defined templates, but not system templates");
        }
        return viewTemplateManager.updateViewTemplate(templateView);
    }

    private int addTemplate(ViewTemplate templateView) {
        List<ViewTemplateDO> viewTemplates = getViewTemplates(templateView);
        if (viewTemplates.size() == 1) {
            ViewTemplateDO viewTemplate = viewTemplates.get(0);
            templateView.setId(viewTemplate.getId());
            return viewTemplateManager.updateViewTemplate(templateView);
        } else {
            templateView.setUpdateTime(DateUtils.getNowDate());
            templateView.setCreateTime(DateUtils.getNowDate());
            templateView.setStatus(0);
            if (UserConstants.SYSTEM_TEMPLATE.equals(templateView.getItCode())) {
                templateView.setIsDefault(1);
            } else {
                templateView.setIsDefault(0);
            }
            return viewTemplateManager.insertViewTemplate(templateView);
        }
    }

    @Override
    public int saveTemplateByUser(ViewTemplate templateView) {
        //验证 模板名称 是否 重复
        verifyRepetition(templateView);

        String fieldJson = templateView.getFieldJson();
        JSONObject jsonObject = JSON.parseObject(fieldJson);
        jsonObject.put(ConstantFieldEnum.FIELDS.getName(), templateView.getFields());
        templateView.setFieldJson(jsonObject.toJSONString());

        templateView.setUpdateTime(DateUtils.getNowDate());
        templateView.setCreateTime(DateUtils.getNowDate());
        templateView.setStatus(0);
        templateView.setIsDefault(0);

        return viewTemplateManager.insertViewTemplate(templateView);
    }

    /**
     * 验证模板名称不能重复
     *
     * @param templateView
     */
    private void verifyRepetition(ViewTemplate templateView) {

        ViewTemplate viewTemplateRepetition = new ViewTemplate();
        viewTemplateRepetition.setFieldId(templateView.getFieldId());
        viewTemplateRepetition.setName(templateView.getName());
        viewTemplateRepetition.setStatus(0);
        List<ViewTemplateDO> repetitionList = this.selectViewTemplateList(viewTemplateRepetition);
        for (ViewTemplateDO viewTemplate : repetitionList) {
            if (!ObjectUtil.equal(templateView.getId(), viewTemplate.getId())) {
                throw new OtmpException("Template name already exists.");
            }
        }
    }

    private List<ViewTemplateDO> getViewTemplates(ViewTemplate templateView) {
        ViewTemplate viewTemplate = new ViewTemplate();
        viewTemplate.setFieldId(templateView.getFieldId());
        viewTemplate.setPaymentMode(templateView.getPaymentMode());
        viewTemplate.setRegion(templateView.getRegion());
        viewTemplate.setBusinessGroup(templateView.getBusinessGroup());
        viewTemplate.setGeoCode(templateView.getGeoCode());
        viewTemplate.setStatus(0);
        viewTemplate.setItCode(templateView.getItCode());
        return viewTemplateManager.selectViewTemplateList(viewTemplate);
    }

    @Override
    public Map<String, Object> viewTemplateByUser(Map<String, Object> viewTemplate) {
        List<Map<String, Object>> list;

        if (viewTemplate.containsKey(ConstantViewField.ID) && null != viewTemplate.get(ConstantViewField.ID)) {
            //修改为默认模板
            ViewTemplate viewTemplateUpdate = new ViewTemplate();
            viewTemplateUpdate.setId(Integer.parseInt(viewTemplate.get(ConstantViewField.ID).toString()));
            this.updateDefaultTemplate(viewTemplateUpdate);
            //查询选中的模板
            Map<String, Object> map = new HashMap<>(2);
            map.put(ConstantViewField.TEMPLATE_ID, Integer.parseInt(viewTemplate.get(ConstantViewField.ID).toString()));
            map.put(ConstantViewField.IS_DEFAULT, 1);
            list = viewFieldService.selectViewFieldAndTemplateAndCondition(map);
            Map<String, Object> map1 = list.get(0);
            String fieldJson = map1.get(ConstantViewField.TEMPLATE_FIELD_JSON).toString();
            JSONObject jsonObject = JSON.parseObject(fieldJson);
            JSONArray jsonArray = jsonObject.getJSONArray(ConstantFieldEnum.FIELDS.getName());
            List<JSONObject> fieldArray = jsonArray.toJavaList(JSONObject.class);
            List<JSONObject> showArray = new ArrayList<>();
            for (JSONObject fieldJsonObj : fieldArray) {
                if (fieldJsonObj.containsKey(ConstantFieldEnum.CHECKED.getName()) && Boolean.TRUE.equals(fieldJsonObj.getBoolean(ConstantFieldEnum.CHECKED.getName()))) {
                    showArray.add(fieldJsonObj);
                }
            }
            jsonObject.put(ConstantFieldEnum.FIELDS.getName(), showArray);
            map1.put(ConstantViewField.FIELD_JSON, jsonObject.toJSONString());
            map1.put(ConstantViewField.TEMPLATE_NAME_SHOW, map1.get(ConstantViewField.TEMPLATE_NAME));
            return map1;
        }
        throw new OtmpException("No corresponding template");
    }

    @Override
    public List<ViewTemplateDO> viewTemplateSelect(ViewTemplate viewTemplate) {
        String itCode = null == viewTemplate.getItCode() ? "" : viewTemplate.getItCode();
        viewTemplate.setItCode(null);
        List<ViewTemplateDO> list = new ArrayList<>();
        viewTemplate.setPageKey(viewTemplate.getPageKey());
        viewTemplate.setViewType(viewTemplate.getViewType());
        viewTemplate.setDivKey(viewTemplate.getDivKey());
        viewTemplate.setType(viewTemplate.getType());
        viewTemplate.setStatus(0);
        List<ViewTemplateDO> viewTemplates = viewTemplateManager.templateViewList(viewTemplate);
        Map<String, ViewTemplateDO> customMap = viewTemplates.stream().filter(viewTemplate1 -> !UserConstants.SYSTEM_TEMPLATE.equals(viewTemplate1.getItCode()) &&
                itCode.equals(viewTemplate1.getItCode())).collect(Collectors.toMap(viewTemplate1 -> viewTemplate1.getName().toLowerCase() + viewTemplate1.getId(), viewTemplate1 -> viewTemplate1));
        int isCustom = 0;
        if (!customMap.isEmpty()) {
            Object[] objects = customMap.keySet().toArray();
            Arrays.sort(objects);
            for (Object object : objects) {
                ViewTemplateDO viewTemplate1 = customMap.get(object.toString());
                list.add(viewTemplate1);
                if (null != viewTemplate1 && 1 == viewTemplate1.getIsDefault()) {
                    isCustom++;
                }
            }
        }
        ViewTemplateDO noCustom = viewTemplates.stream().filter(viewTemplate1 -> UserConstants.SYSTEM_TEMPLATE.equals(viewTemplate1.getItCode())).collect(Collectors.toList()).get(0);
        if (isCustom > 0 && null != noCustom) {
            noCustom.setIsDefault(0);
        }
        list.add(noCustom);
        return list;
    }

    @Override
    public ViewTemplateDO viewTemplateDetailsByUser(ViewTemplate viewTemplate) {
        ViewTemplateDO viewTemplateDO = viewTemplateManager.selectViewTemplateById(viewTemplate.getId());
        if (null == viewTemplateDO) {
            throw new OtmpException("There is no default template for the page");
        }
        ViewTemplate template = new ViewTemplate();
        org.springframework.beans.BeanUtils.copyProperties(viewTemplateDO, template);
        return analysisField(template);
    }

    @Override
    public List<String> getSelectedFields(ViewTemplate viewTemplate) {
        List<String> list = new ArrayList<>();
        JSONObject jsonObject = JSON.parseObject("fieldJson");
        JSONArray jsonArray = jsonObject.getJSONArray(ConstantFieldEnum.FIELDS.getName());
        List<JSONObject> fields = jsonArray.toJavaList(JSONObject.class);
        fields.removeIf(field -> !(boolean) field.get(ConstantFieldEnum.CHECKED.getName()));
        for (JSONObject fieldJsonEntity : fields) {
            list.add((String) fieldJsonEntity.get(ConstantFieldEnum.V_MODEL.getName()));
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int copyTemplate(Map<String, Object> templateView) {

        verifyRequired(templateView);

        List<SysDictData> dataList = sysDictDataService.selectDictDataByType(ConstantViewField.VIEW_FIELD_DIMENSIONS);
        Map<String, Object> newDimensionMap = new HashMap<>();
        //模板是否存在
        Map<String, Object> existTemple = new HashMap<>();
        existTemple.put(ConstantViewField.PAGE_KEY, templateView.get(ConstantViewField.PAGE_KEY_NEW));
        existTemple.put(ConstantViewField.VIEW_TYPE, templateView.get(ConstantViewField.VIEW_TYPE_NEW));
        // 过滤出新维度判断
        getNewDimension(templateView, dataList, newDimensionMap, existTemple);
        List<Map<String, Object>> existList = viewFieldService.selectViewFieldListPrecise(existTemple);
        if (!existList.isEmpty()) {
            throw new OtmpException("已经存在相同的模板");
        }

        Map<String, Object> viewField = new HashMap<>();
        viewField.put(ConstantViewField.PAGE_KEY, templateView.get(ConstantViewField.PAGE_KEY));
        viewField.put(ConstantViewField.VIEW_TYPE, templateView.get(ConstantViewField.VIEW_TYPE));
        viewField.put(ConstantViewField.PARENT_ID, 0);
        //过滤出老维度
        getOldDimension(templateView, dataList, viewField);
        List<Map<String, Object>> viewFields = viewFieldService.selectViewFieldListPrecise(viewField);
        for (Map<String, Object> field : viewFields) {
            field.put(ConstantViewField.PARENT_ID_OLD, field.get(ConstantViewField.ID));
            field.put(ConstantViewField.PAGE_KEY, templateView.get(ConstantViewField.PAGE_KEY_NEW));
            field.put(ConstantViewField.VIEW_TYPE, templateView.get(ConstantViewField.VIEW_TYPE_NEW));
            field.put(ConstantViewField.CREATE_BY, templateView.get(ConstantViewField.CREATE_BY));
            field.put(ConstantViewField.UPDATE_BY, templateView.get(ConstantViewField.CREATE_BY));
            field.put(ConstantViewField.CREATE_TIME, DateUtils.getNowDate());
            field.put(ConstantViewField.UPDATE_TIME, DateUtils.getNowDate());
            field.put(ConstantViewField.IS_DEFAULT, 1);
            viewFieldManager.insertViewFieldMap(field);
            Set<String> newDimensionKey = newDimensionMap.keySet();
            for (String s : newDimensionKey) {
                ViewConditionValue viewConditionValue = new ViewConditionValue();
                viewConditionValue.setFieldId(Integer.parseInt(field.get(ConstantViewField.ID).toString()));
                viewConditionValue.setCondition(s);
                String value = newDimensionMap.get(s).toString();
                // All全选中，把所有值都插入进入
                if (ConstantViewField.ALL_VALUE.equals(value)) {
                    Map<String, String> map = new HashMap<>();
                    businessGroupAndGeoCodeCondition(viewField, map);
                    salesOfficeCodeCondition(viewField, s, map);
                    map.put(ConstantViewField.DIMENSIONS_TYPE, s);
                    List<SysDictData> dataList1 = viewFieldService.fieldDimensionsSelect(map);
                    List<String> collect1 = dataList1.stream().map(SysDictData::getDictValue).collect(Collectors.toList());
                    value = org.apache.commons.lang3.StringUtils.join(collect1, ",");
                }
                value = value.replace(ConstantViewField.ALL_VALUE_SUB, "");
                if (null != viewField.get(ViewFieldDimensionsEnum.GEO_CODE.getName()) && !"".equals(viewField.get(ViewFieldDimensionsEnum.GEO_CODE.getName()).toString())) {
                    viewField.put(ConstantViewField.GEO_CODE, value);
                } else {
                    viewField.put(s, value);
                }
                viewConditionValue.setConditionValue(value);
                viewConditionValue.setCreateBy(templateView.get(ConstantViewField.CREATE_BY).toString());
                viewConditionValue.setUpdateBy(templateView.get(ConstantViewField.CREATE_BY).toString());
                viewConditionValue.setCreateTime(DateUtils.getNowDate());
                viewConditionValue.setUpdateTime(DateUtils.getNowDate());
                viewConditionValueService.insertViewConditionValue(viewConditionValue);
            }
            copyChild(field, newDimensionMap);
        }
        return 1;
    }

    private static void businessGroupAndGeoCodeCondition(Map<String, Object> viewField, Map<String, String> map) {
        if (null != viewField.get(ViewFieldDimensionsEnum.BUSINESS_GROUP.getName()) && !"".equals(viewField.get(ViewFieldDimensionsEnum.BUSINESS_GROUP.getName()))) {
            map.put(ConstantViewField.BUSINESS_GROUP, viewField.get(ViewFieldDimensionsEnum.BUSINESS_GROUP.getName()).toString());
        }
        if (null != viewField.get(ViewFieldDimensionsEnum.GEO_CODE.getName()) && !"".equals(viewField.get(ViewFieldDimensionsEnum.GEO_CODE.getName()).toString())) {
            map.put(ConstantViewField.GEO_CODE, viewField.get(ViewFieldDimensionsEnum.GEO_CODE.getName()).toString());
        }
    }

    private static void salesOfficeCodeCondition(Map<String, Object> viewField, String s, Map<String, String> map) {
        if (ViewFieldDimensionsEnum.SALES_OFFICE_CODE.getName().equals(s)) {
            if (null != viewField.get(ViewFieldDimensionsEnum.REGION_MARKET_CODE.getName()) && !"".equals(viewField.get(ViewFieldDimensionsEnum.REGION_MARKET_CODE.getName()).toString())) {
                map.put(ViewFieldDimensionsEnum.REGION_MARKET_CODE.getName(), viewField.get(ViewFieldDimensionsEnum.REGION_MARKET_CODE.getName()).toString());
            }
            if (null != viewField.get(ViewFieldDimensionsEnum.SALES_ORG_CODE.getName()) && !"".equals(viewField.get(ViewFieldDimensionsEnum.SALES_ORG_CODE.getName()).toString())) {
                map.put(ViewFieldDimensionsEnum.SALES_ORG_CODE.getName(), viewField.get(ViewFieldDimensionsEnum.SALES_ORG_CODE.getName()).toString());
            }
        }
    }

    private static void getOldDimension(Map<String, Object> templateView, List<SysDictData> dataList, Map<String, Object> viewField) {
        for (SysDictData sysDictData : dataList) {
            String dictValue = sysDictData.getDictValue();
            for (Map.Entry<String, Object> stringObjectEntry : templateView.entrySet()) {
                if (dictValue.equals(stringObjectEntry.getKey())) {
                    viewField.putIfAbsent(dictValue, stringObjectEntry.getValue());
                    break;
                }
            }
            viewField.putIfAbsent(dictValue, null);
        }
    }

    private static void getNewDimension(Map<String, Object> templateView, List<SysDictData> dataList, Map<String, Object> newDimensionMap, Map<String, Object> existTemple) {
        for (SysDictData sysDictData : dataList) {
            String dictValue = sysDictData.getDictValue() + "New";
            for (Map.Entry<String, Object> stringObjectEntry : templateView.entrySet()) {
                if (dictValue.equals(stringObjectEntry.getKey())) {
                    existTemple.putIfAbsent(sysDictData.getDictValue(), stringObjectEntry.getValue());
                    newDimensionMap.putIfAbsent(sysDictData.getDictValue(), stringObjectEntry.getValue());
                    break;
                }
            }
            existTemple.putIfAbsent(sysDictData.getDictValue(), null);
        }
    }

    private static void verifyRequired(Map<String, Object> templateView) {
        if (!templateView.containsKey(ConstantViewField.PAGE_KEY) || null == templateView.get(ConstantViewField.PAGE_KEY) ||
                !templateView.containsKey(ConstantViewField.VIEW_TYPE) || null == templateView.get(ConstantViewField.VIEW_TYPE) ||
                !templateView.containsKey(ConstantViewField.PAGE_KEY_NEW) || null == templateView.get(ConstantViewField.PAGE_KEY_NEW) ||
                !templateView.containsKey(ConstantViewField.VIEW_TYPE_NEW) || null == templateView.get(ConstantViewField.VIEW_TYPE_NEW)) {
            throw new OtmpException("PageKey、ViewType、PageKeyNew、VIewTypeNew is null");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTemplateGroup(Map<String, Object> templateView) {
        if (!templateView.containsKey(ConstantViewField.PAGE_KEY) || null == templateView.get(ConstantViewField.PAGE_KEY) ||
                "".equals(templateView.get(ConstantViewField.PAGE_KEY))) {
            throw new OtmpException("PageKey is null!");
        }
        deleteTemplateGroupChild(templateView, 0);
    }

    @Override
    public void updateDefaultTemplate(ViewTemplate viewTemplate) {
        ViewTemplateDO viewTemplateDO = this.selectViewTemplateById(viewTemplate.getId());

        if (null != viewTemplateDO) {
            //把默认模板修改为非默认
            ViewTemplate viewTemplateQuery = new ViewTemplate();
            viewTemplateQuery.setFieldId(viewTemplateDO.getFieldId());
            viewTemplateQuery.setIsDefault(1);
            viewTemplateQuery.setStatus(0);
            List<ViewTemplateDO> viewTemplates = this.selectViewTemplateList(viewTemplateQuery);
            for (ViewTemplateDO template : viewTemplates) {
                template.setIsDefault(0);
                ViewTemplate viewTemplate1 = new ViewTemplate();
                org.springframework.beans.BeanUtils.copyProperties(template, viewTemplate1);
                this.updateViewTemplate(viewTemplate1);
            }
            //非默认模板修改为默认模板
            viewTemplate.setIsDefault(1);
            this.updateViewTemplate(viewTemplate);
        }
    }

    private void deleteTemplateGroupChild(Map<String, Object> templateView, Integer parentId) {
        List<SysDictData> dataList = sysDictDataService.selectDictDataByType(ConstantViewField.VIEW_FIELD_DIMENSIONS);
        for (SysDictData sysDictData : dataList) {
            templateView.putIfAbsent(sysDictData.getDictValue(), null);
        }
        templateView.put(ConstantViewField.PARENT_ID, parentId);
        List<Map<String, Object>> viewFields = viewFieldService.selectViewFieldListPrecise(templateView);
        if (!viewFields.isEmpty()) {
            for (Map<String, Object> field : viewFields) {
                ViewField viewFieldDO = new ViewField();
                viewFieldDO.setStatus(1);
                int id = Integer.parseInt(field.get(ConstantViewField.ID).toString());
                viewFieldDO.setId(id);
                viewFieldManager.updateViewField(viewFieldDO);
                viewTemplateManager.fakeDeleteTemplateGroup(id);
                viewConditionValueManager.deleteViewConditionGroup(id);
                deleteTemplateGroupChild(templateView, id);
            }
        }
    }

    public void copyChild(Map<String, Object> field, Map<String, Object> newDimensionKey) {
        Map<String, Object> child = new HashMap<>(1);
        child.put(ConstantViewField.PARENT_ID, field.get(ConstantViewField.PARENT_ID_OLD));
        List<Map<String, Object>> viewFields = viewFieldService.selectViewFieldListPrecise(child);
        if (!viewFields.isEmpty()) {
            for (Map<String, Object> childViewField : viewFields) {

                childViewField.put(ConstantViewField.PARENT_ID_OLD, childViewField.get(ConstantViewField.ID));
                childViewField.put(ConstantViewField.PARENT_ID, field.get(ConstantViewField.ID));
                childViewField.put(ConstantViewField.PAGE_KEY, field.get(ConstantViewField.PAGE_KEY));
                childViewField.put(ConstantViewField.VIEW_TYPE, field.get(ConstantViewField.VIEW_TYPE));
                childViewField.put(ConstantViewField.CREATE_BY, field.get(ConstantViewField.CREATE_BY));
                childViewField.put(ConstantViewField.UPDATE_BY, field.get(ConstantViewField.CREATE_BY));
                childViewField.put(ConstantViewField.CREATE_TIME, DateUtils.getNowDate());
                childViewField.put(ConstantViewField.UPDATE_TIME, DateUtils.getNowDate());
                //增加字段配置
                viewFieldManager.insertViewFieldMap(childViewField);
                for (Map.Entry<String, Object> stringObjectEntry : newDimensionKey.entrySet()) {
                    addConditionValue(field, newDimensionKey, childViewField, stringObjectEntry);
                }
                copyChild(childViewField, newDimensionKey);
            }
        }
    }

    private void addConditionValue(Map<String, Object> field, Map<String, Object> newDimensionKey, Map<String, Object> childViewField,
                                   Map.Entry<String, Object> stringObjectEntry) {
        String key = stringObjectEntry.getKey();
        ViewConditionValue viewConditionValue = new ViewConditionValue();
        viewConditionValue.setFieldId(Integer.parseInt(childViewField.get(ConstantViewField.ID).toString()));
        viewConditionValue.setCondition(key);
        String value = stringObjectEntry.getValue().toString();
        // All全选中，把所有值都插入进入
        if (ConstantViewField.ALL_VALUE.equals(value)) {
            Map<String, String> map = new HashMap<>();
            businessGroupAndGeoCodeCondition(newDimensionKey, map);
            salesOfficeCodeCondition(newDimensionKey, key, map);
            map.put(ConstantViewField.DIMENSIONS_TYPE, key);
            List<SysDictData> dataList1 = viewFieldService.fieldDimensionsSelect(map);
            List<String> collect1 = dataList1.stream().map(SysDictData::getDictValue).collect(Collectors.toList());
            value = org.apache.commons.lang3.StringUtils.join(collect1, ",");
        }
        value = value.replace(ConstantViewField.ALL_VALUE_SUB, "");
        if (null != newDimensionKey.get(ViewFieldDimensionsEnum.GEO_CODE.getName()) &&
                !"".equals(newDimensionKey.get(ViewFieldDimensionsEnum.GEO_CODE.getName()).toString())) {
            newDimensionKey.put(ConstantViewField.GEO_CODE, value);
        } else {
            newDimensionKey.put(key, value);
        }
        viewConditionValue.setConditionValue(value);
        viewConditionValue.setCreateBy(field.get(ConstantViewField.CREATE_BY).toString());
        viewConditionValue.setUpdateBy(field.get(ConstantViewField.CREATE_BY).toString());
        viewConditionValue.setCreateTime(DateUtils.getNowDate());
        viewConditionValue.setUpdateTime(DateUtils.getNowDate());
        viewConditionValueService.insertViewConditionValue(viewConditionValue);
    }

    @Override
    public void checkedFields(Map<String, Object> viewTemplate, Map<String, Object> viewField) {
        // 过滤字段配置的维度
        String fieldJson = viewTemplate.get(ConstantViewField.TEMPLATE_FIELD_JSON).toString();
        JSONObject jsonObject = JSON.parseObject(fieldJson);
        JSONArray jsonArray = jsonObject.getJSONArray(ConstantFieldEnum.FIELDS.getName());
        List<JSONObject> fields = jsonArray.toJavaList(JSONObject.class);
        List<JSONObject> collect = new ArrayList<>();
        for (JSONObject fieldJsonObj : fields) {
            if (fieldJsonObj.containsKey(ConstantFieldEnum.CHECKED.getName()) && Boolean.TRUE.equals(fieldJsonObj.getBoolean(ConstantFieldEnum.CHECKED.getName()))) {
                int rangeCount = 0;
                int rangeCountAll = 0;
                String fieldRangeName = ConstantFieldEnum.FIELD_RANGE.getName();
                if (fieldJsonObj.containsKey(fieldRangeName)) {
                    String fieldRange = fieldJsonObj.getString(fieldRangeName);
                    if (StringUtils.isNotEmpty(fieldRange)) {
                        verifyFieldRange(viewField, collect, fieldJsonObj, rangeCount, rangeCountAll, fieldRangeName, fieldRange);
                    } else {
                        collect.add(fieldJsonObj);
                    }
                } else {
                    collect.add(fieldJsonObj);
                }
            }
        }
        jsonObject.put(ConstantFieldEnum.FIELDS.getName(), collect);
        // 这个是 template fieldJson
        viewTemplate.put(ConstantViewField.FIELD_JSON, JSON.toJSONString(jsonObject));
        viewTemplate.put(ConstantViewField.TEMPLATE_NAME_SHOW, viewTemplate.get(ConstantViewField.TEMPLATE_NAME));
    }

    private static void verifyFieldRange(Map<String, Object> viewField, List<JSONObject> collect, JSONObject fieldJsonObj, int rangeCount, int rangeCountAll, String fieldRangeName, String fieldRange) {
        if (StringUtils.checkChinesePunctuationByBlock(fieldRange)) {
            throw new OtmpException(fieldJsonObj.get(fieldRangeName) + " 存在中文字符,关于" + fieldJsonObj.get(ConstantFieldEnum.V_MODEL.getName()) + "字段");
        }
        JSONObject fieldRangeObj = JSON.parseObject(fieldRange);
        String neqFields = fieldRangeObj.getString(ConstantViewField.NEQ);
        //删除 neq key，防止影响下面的维度判断
        fieldRangeObj.remove(ConstantViewField.NEQ);
        String[] neqFieldsArray = null;
        if (StringUtils.isNotEmpty(neqFields)) {
            neqFieldsArray = neqFields.split(",");
        }
        // 维度 是 维度1 and 维度2
        verifyDimensionCount(viewField, collect, fieldJsonObj, rangeCount, rangeCountAll, fieldRangeObj, neqFieldsArray);
    }

    private static void verifyDimensionCount(Map<String, Object> viewField, List<JSONObject> collect, JSONObject fieldJsonObj,
                                             int rangeCount, int rangeCountAll, JSONObject fieldRangeObj, String[] neqFieldsArray) {
        for (Map.Entry<String, Object> stringObjectEntry : fieldRangeObj.entrySet()) {
            if (null != stringObjectEntry.getValue() && StringUtils.isNotEmpty(stringObjectEntry.getValue().toString())) {
                String fieldRangeObjValue = stringObjectEntry.getValue().toString();
                if (null != viewField.get(stringObjectEntry.getKey())) {
                    String s = viewField.get(stringObjectEntry.getKey()).toString();
                    String[] fieldValueArray = fieldRangeObjValue.split(",");
                    rangeCount = reverse(rangeCount, neqFieldsArray, stringObjectEntry, s, fieldValueArray);
                }
                rangeCountAll++;
            }
        }
        //true 满足页面传入的条件
        if (rangeCount == rangeCountAll) {
            collect.add(fieldJsonObj);
        }
    }

    private static int reverse(int rangeCount, String[] neqFieldsArray, Map.Entry<String, Object> stringObjectEntry, String s, String[] fieldValueArray) {
        /**{"geo":"AP","businessGroup":"ISG,PCSD,,MBG","regionMarketCode":"India"}*/
        // neqFieldsArray 等于空，所有维度都是正向的,例子： geo="AP" and businessGroup="ISG,PCSD,,MBG" and regionMarketCode ="India"
        if (null == neqFieldsArray) {
            if (ConstantViewField.ALL_VALUE.equals(s) || Arrays.stream(fieldValueArray).collect(Collectors.toList()).contains(s)) {
                rangeCount++;
            }
        } else {
            /**{"geo":"AP","businessGroup":"ISG,PCSD,,MBG","regionMarketCode":"India","NEQ":"regionMarketCode,businessGroup"}*/
            //neqFieldsArray 不为空, neq 字段的值有维度的key，说明这个反向的 businessGroup!="ISG,PCSD,,MBG" and regionMarketCode!="India"
            if (Arrays.asList(neqFieldsArray).contains(stringObjectEntry.getKey())) {
                if (!Arrays.stream(fieldValueArray).collect(Collectors.toList()).contains(s)) {
                    rangeCount++;
                }
            } else {
                //neqFieldsArray 不为空, neq 字段的值没有有维度的key，说明这个反向的 geo="AP"
                if (ConstantViewField.ALL_VALUE.equals(s) || Arrays.stream(fieldValueArray).collect(Collectors.toList()).contains(s)) {
                    rangeCount++;
                }
            }
        }
        return rangeCount;
    }

    private ViewTemplateDO analysisField(ViewTemplate viewTemp) {
        String fieldJson = viewTemp.getFieldJson();
        JSONObject jsonObject = JSON.parseObject(fieldJson);
        JSONArray jsonArray = jsonObject.getJSONArray(ConstantFieldEnum.FIELDS.getName());
        List<JSONObject> fields = jsonArray.toJavaList(JSONObject.class);
        viewTemp.setFields(fields);

        ViewTemplateDO viewTemplateDO = new ViewTemplateDO();
        BeanUtils.copyProperties(viewTemp, viewTemplateDO);
        return viewTemplateDO;
    }

}
