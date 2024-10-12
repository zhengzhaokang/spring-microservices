package com.microservices.otmp.task;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.microservices.otmp.common.BaseImportDTO;
import com.microservices.otmp.common.ValidField;
import com.microservices.otmp.common.ValidImportFields;
import com.microservices.otmp.common.ViewConfigCache;
import com.microservices.otmp.common.constant.DicKeyConstants;
import com.microservices.otmp.common.core.page.RemoteResponse;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.utils.MapDataUtil;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.domain.ImportResult;
import com.microservices.otmp.service.CustomerValidate;
import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.feign.RemoteViewFieldService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 效验导入任务
 *
 * @author daihuaicai
 */
public class ValidateTask implements Callable<ImportResult<BaseImportDTO>> {

    private ValidImportFields validImportFields;
    Logger logger = LoggerFactory.getLogger(ValidateTask.class);
    /**
     * 字典表
     */
    private Map<String, List<SysDictData>> dicMap;
    /**
     * 导入的数据实体
     */
    private BaseImportDTO importEntity;
    /**
     * 字段配置
     */
    private RemoteViewFieldService remoteViewFieldService;
    /**
     * 自定义的效验逻辑
     */
    private CustomerValidate<? extends BaseImportDTO> customerValidate;

    private static String businessGroup = "businessGroup";

    private static String geoCode = "geoCode";
    private String pageKey;
    /**
     * 是否验证bg 和 geo
     */
    private  boolean isHasBgAndGeo = true;

    private List<String> viewFieldDimensions;

    /**
     * 方便效验
     */
    private Map<String,Object> dataMap;

    public ValidateTask(ValidImportFields validImportFields, Map<String, List<SysDictData>> dicMap, BaseImportDTO baseImportEntity,
                        RemoteViewFieldService remoteViewFieldService,
                        CustomerValidate<? extends BaseImportDTO> customerValidate, String pageKey, List<String> viewFieldDimensions) {
        this.validImportFields = validImportFields;
        this.dicMap = dicMap;
        this.importEntity = baseImportEntity;
        this.remoteViewFieldService = remoteViewFieldService;
        this.customerValidate = customerValidate;
        this.pageKey = pageKey;
        this.viewFieldDimensions = viewFieldDimensions;
    }

    @Override
    public ImportResult<BaseImportDTO> call() throws Exception {
        Map<String, Object> viewFieldDimensionMap = new HashMap<>();
        if (null != this.importEntity) {
            if (null != this.viewFieldDimensions) {
                for (String viewFieldDimension : this.viewFieldDimensions) {
                    this.dataMap = MapDataUtil.beanToMap(this.importEntity);
                    viewFieldDimensionMap.put(viewFieldDimension, this.dataMap.get(viewFieldDimension));
                }
                this.isHasBgAndGeo = CollectionUtils.isNotEmpty(this.viewFieldDimensions);
            } else {
                this.dataMap = MapDataUtil.beanToMap(this.importEntity);
                this.isHasBgAndGeo = this.dataMap.containsKey(geoCode) && this.dataMap.containsKey(businessGroup);
            }
        }

        return validData(viewFieldDimensionMap);
    }

    /**
     * 效验方法
     *
     * @return
     */
    public ImportResult<BaseImportDTO> validData(Map<String, Object> viewFieldDimensionMap) {
        String geoVal = "";
        String bgVal = "";
        //是否要验证bg和geo
        if (isHasBgAndGeo) {
            //效验bg
            bgVal = BeanUtils.getFieldValue(importEntity, businessGroup) == null ? "" : BeanUtils.getFieldValue(importEntity, businessGroup).toString();
            String bgStr = ValidField.validBGGeo(bgVal, ValidImportFields.BUSINESS_GROUP, DicKeyConstants.B_G, dicMap);
            if (StrUtil.isNotBlank(bgStr)) {
                ValidField.setErrorMsg(importEntity, bgStr);
            }
            //效验geo
            geoVal = BeanUtils.getFieldValue(importEntity, geoCode) == null ? "" : BeanUtils.getFieldValue(importEntity, geoCode).toString();
            String geoStr = ValidField.validBGGeo(geoVal, ValidImportFields.GEO, DicKeyConstants.GEO, dicMap);
            if (StrUtil.isNotBlank(geoStr)) {
                ValidField.setErrorMsg(importEntity, geoStr);
            }
            if (StrUtil.isNotBlank(bgStr) || StrUtil.isNotBlank(geoStr)) {
                return ImportResult.importFail("", importEntity);
            }
        }
        List<JSONObject> viewFields;
        if (viewFieldDimensionMap.isEmpty()) {
            viewFields = getViewFieldByCache(geoVal, bgVal);
        } else {
            //手动指定维度
            viewFields = getViewFieldByCache(viewFieldDimensionMap);
        }
        try {
            ImportResult<BaseImportDTO> importResult = validImportFields.validField(importEntity, dataMap, viewFields, dicMap, customerValidate);
            importResult.setData(importEntity);
            return importResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ImportResult.importFail("", importEntity);
    }

    /**
     * 查询字段配置
     *
     * @param geo
     * @param bg
     * @param
     * @return
     */
    private List<JSONObject> getViewFieldByCache(String geo, String bg) {
        if (StrUtil.isBlank(geo) || StrUtil.isBlank(bg) || StrUtil.isBlank(pageKey)) {
            return new ArrayList<>();
        }
        String key = geo + ":" + bg + ":" + pageKey;
        List<JSONObject> list = ViewConfigCache.get(key);
        if (CollectionUtils.isEmpty(list)) {
                   Object obj = null;
                   try {
                       obj = remoteViewFieldService.getViewFieldByBgAndGeo(geo, bg, pageKey, "edit");
                       if (null != obj) {
                           return getViewConfig(obj, key);
                       }
                   } catch (Exception e) {
                       logger.error("KEY : {},{}",key,"GET VIEW CONFIG ERROR");
                       return getViewConfig(obj, key);
                   }
        }
        return list;
    }

    private List<JSONObject> getViewConfig(Object obj, String key) {
        try {
            List<Map<String,Object>> resultList = (List<Map<String,Object>>) obj;
            List<JSONObject> jsonObjects = getJsonObjects(resultList);
            if (CollectionUtils.isNotEmpty(jsonObjects)) {
                ViewConfigCache.put(key, jsonObjects);
            }
            return jsonObjects;
        } catch (Exception e) {
            logger.error("解析页面配置异常", e);
            return new ArrayList<>();
        }

    }

    /**
     * 查询字段配置
     *
     * @param fields
     * @return
     */
    private List<JSONObject> getViewFieldByCache(Map<String, Object> fields) {
        fields.put("pageKey", pageKey);
        fields.put("viewType", "edit");
        String key = String.join(":", fields.keySet());
        List<JSONObject> list = ViewConfigCache.get(key);
        if (CollectionUtils.isEmpty(list)) {
            RemoteResponse resultDTO = remoteViewFieldService.pageKeyList(fields);
            if (500 == resultDTO.getCode()) {
                throw new OtmpException(resultDTO.getMsg());
            }
            List<Map<String,Object>> resultList = (List<Map<String,Object>>) resultDTO.getRows();
            List<JSONObject> jsonObjects = getJsonObjects(resultList);
            if (CollectionUtils.isNotEmpty(jsonObjects)) {
                ViewConfigCache.put(key, jsonObjects);
                return jsonObjects;
            }
        }
        return list;
    }

    /**
     * map转JSONObject
     *
     * @param list
     * @return
     */
    public List<JSONObject> getJsonObjects(List<Map<String,Object>> list) {
        List<JSONObject> objects = new ArrayList<>();
        if (null != list) {
            JSONObject jsonObject;
            for (Map<String, Object> map : list) {
                jsonObject = JSON.parseObject(JSON.toJSON(map).toString());
                objects.add(jsonObject);
            }
        }
        return objects;
    }

}
