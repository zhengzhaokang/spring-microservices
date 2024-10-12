package com.microservices.otmp.system.manager;

import com.microservices.otmp.system.domain.ViewField;
import com.microservices.otmp.system.domain.ViewFieldInfo;
import com.microservices.otmp.system.domain.entity.ViewFieldDO;

import java.util.List;
import java.util.Map;

/**
 * 页面字段展示信息Mapper接口
 * 
 * @author sdms
 * @date 2022-02-15
 */
public interface ViewFieldManager
{
    /**
     * 查询页面字段展示信息
     *
     * @param id 页面字段展示信息ID
     * @return 页面字段展示信息
     */
    public ViewFieldDO selectViewFieldById(Integer id);

    /**
     * 查询页面字段展示信息列表
     *
     * @param viewField 页面字段展示信息
     * @return 页面字段展示信息集合
     */
    public List<ViewFieldDO> selectViewFieldList(ViewField viewField);

    public List<Map<String, Object>> selectViewFieldListPrecise(Map<String, Object> viewField);


    /**
     * 新增页面字段展示信息
     *
     * @param viewField 页面字段展示信息
     * @return 结果
     */
    public int insertViewFieldMap(Map<String, Object> viewField);

    public int insertViewField(ViewField viewField);

    /**
     * 修改页面字段展示信息
     *
     * @param viewField 页面字段展示信息
     * @return 结果
     */
    public int updateViewField(ViewField viewField);

    public int updateViewFieldMap(Map<String, Object> viewField);

    /**
     * 删除页面字段展示信息
     *
     * @param id 页面字段展示信息ID
     * @return 结果
     */
    public int deleteViewFieldById(Integer id);

    /**
     * 批量删除页面字段展示信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteViewFieldByIds(String[] ids);

    List<ViewFieldInfo> selectViewFieldInfoList(ViewFieldInfo viewFieldInfo);

    List<ViewFieldDO> selectPageKeyList(ViewField viewField);

    /**
     * 全表查询
     *
     * @return
     */
    List<String> getTableNames();

    List<Map<String, Object>> selectViewFieldAndCondition(Map<String, Object> map);

    /**
     * 查询 view field, view template, view condition
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> selectViewFieldAndTemplateAndCondition(Map<String, Object> map);


}
