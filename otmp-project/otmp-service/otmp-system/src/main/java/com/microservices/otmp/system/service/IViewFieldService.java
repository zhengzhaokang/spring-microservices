package com.microservices.otmp.system.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.microservices.otmp.system.domain.*;
import com.microservices.otmp.system.domain.entity.ViewFieldDO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 页面字段展示信息Service接口
 *
 * @author sdms
 * @date 2022-02-15
 */
public interface IViewFieldService {
    /**
     * 查询页面字段展示信息
     *
     * @param id 页面字段展示信息ID
     * @return 页面字段展示信息
     */
    public Map<String, Object> selectViewFieldById(Integer id);

    /**
     * 查询页面字段展示信息列表
     *
     * @param viewField 页面字段展示信息
     * @return 页面字段展示信息集合
     */
    public List<ViewFieldDO> selectViewFieldList(ViewField viewField);

    /**
     * 新增页面字段展示信息
     *
     * @param viewField 页面字段展示信息
     * @return 结果
     */
    public int insertViewField(Map<String, Object> viewField);


    /**
     * 修改页面字段展示信息
     *
     * @param viewField 页面字段展示信息
     * @return 结果
     */
    public int updateViewField(Map<String, Object> viewField);

    /**
     * 批量删除页面字段展示信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteViewFieldByIds(String ids);

    /**
     * 删除页面字段展示信息信息
     *
     * @param id 页面字段展示信息ID
     * @return 结果
     */
    public int deleteViewFieldById(Integer id);

    public List<ViewFieldInfo> fields(String relName);

    /**
     * 禁用行
     *
     * @param viewField 页面字段展示信息
     * @return 结果
     */
    int disableViewField(ViewField viewField);

    /**
     * 查询pagkey下面的块key
     *
     * @param map
     * @return
     */
    List<ViewTemplate> pageKeyList(Map<String, Object> map);

    /**
     * tree结构
     *
     * @param viewField
     * @return
     */
    List<ViewFieldDO> selectFieldTree(ViewField viewField);

    /**
     * 平铺
     *
     * @param viewField
     * @return
     */
    List<ViewFieldDO> selectFieldTiled(ViewField viewField);

    List<ViewFieldDO> divSelect(ViewField viewField);

    void repairData(ViewField viewField);

    List<ViewFieldExcel> export(ViewField viewField);

    void importExcel(List<ViewFieldExcel> bizs, String loginName, Integer id);

    List<JSONObject> completionField(ViewField viewField);


    /**
     * 相同页面的维度是否一样
     *
     * @param fieldId
     * @param fieldJson
     * @param dimensionSet
     */
    void verifyDimensions(Integer fieldId, String fieldJson, Set<String> dimensionSet);

    /**
     * 全表查询
     *
     * @return
     */
    List<SysDictData> getTableNames();

    /**
     * 字段维度下拉
     *
     * @return
     */
    List<SysDictData> fieldDimensionsSelect(Map<String, String> condition);

    List<Map<String, Object>> selectViewFieldAndCondition(Map<String, Object> map);

    List<Map<String, Object>> selectViewFieldAndTemplateAndCondition(Map<String, Object> map);

    void dataRecovery(Map<String, Object> condition);

    List<Map<String, String>> columnHeader();

    PageInfo<Map<String, Object>> tree(Map<String, Object> map);

    List<Map<String, Object>> treeSyncData(Map<String, Object> map);

    List<Map<String, String>> columnHeaderByCopy();

    List<Map<String, Object>> selectViewFieldListPrecise(Map<String, Object> map);

    String parentViewFieldCode(Integer fieldId);

    List<JSONObject> getViewField(Map<String, Object> map);
}
