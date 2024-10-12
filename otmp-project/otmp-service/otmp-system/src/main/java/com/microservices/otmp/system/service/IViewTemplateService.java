package com.microservices.otmp.system.service;

import com.microservices.otmp.system.domain.ViewTemplate;
import com.microservices.otmp.system.domain.entity.ViewTemplateDO;

import java.util.List;
import java.util.Map;

/**
 * 页面展示模板Service接口
 *
 * @author sdms
 * @date 2022-02-15
 */
public interface IViewTemplateService {
    /**
     * 查询页面展示模板
     *
     * @param id 页面展示模板ID
     * @return 页面展示模板
     */
    public ViewTemplateDO selectViewTemplateById(Integer id);

    /**
     * 查询页面展示模板列表
     *
     * @param viewTemplate 页面展示模板
     * @return 页面展示模板集合
     */
    public List<ViewTemplateDO> selectViewTemplateList(ViewTemplate viewTemplate);

    /**
     * 查询页面展示模板列表
     *
     * @param viewTemplate 页面展示模板
     * @return 页面展示模板集合
     */
    public ViewTemplateDO viewTemplateDetails(ViewTemplate viewTemplate);

    /**
     * 新增页面展示模板
     *
     * @param templateView 页面展示模板
     * @return 结果
     */
    public int insertViewTemplate(ViewTemplate templateView);

    /**
     * 修改页面展示模板
     *
     * @param viewTemplate 页面展示模板
     * @return 结果
     */
    public int updateViewTemplate(ViewTemplate viewTemplate);

    /**
     * 批量删除页面展示模板
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteViewTemplateByIds(String ids);

    /**
     * 删除页面展示模板信息
     *
     * @param id 页面展示模板ID
     * @return 结果
     */
    public int deleteViewTemplateById(Integer id);

    /**
     * 新增、修改模板
     *
     * @param templateView
     * @return
     */
    int saveTemplate(ViewTemplate templateView);
    int saveTemplateByUser(ViewTemplate templateView);

    Map<String, Object>  viewTemplateByUser(Map<String,Object> viewTemplate);

    List<ViewTemplateDO> viewTemplateSelect(ViewTemplate viewTemplate);

    ViewTemplateDO viewTemplateDetailsByUser(ViewTemplate viewTemplate);

    /**
     * 获取页面选中的字段
     * @param viewTemplate
     * @return
     */
    List<String> getSelectedFields(ViewTemplate viewTemplate);

    int copyTemplate(Map<String,Object> templateView);

    /**
     * 分组逻辑删除模板
     * @param templateView
     */
    void deleteTemplateGroup(Map<String, Object> templateView);

    void updateDefaultTemplate(ViewTemplate viewTemplate);

    void checkedFields(Map<String, Object> viewTemplate,Map<String,Object> viewField);
}
