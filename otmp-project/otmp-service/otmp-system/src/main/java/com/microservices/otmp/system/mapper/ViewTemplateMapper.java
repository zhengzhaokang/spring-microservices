package com.microservices.otmp.system.mapper;

import com.microservices.otmp.system.domain.ViewTemplate;
import com.microservices.otmp.system.domain.entity.ViewTemplateDO;
import com.microservices.otmp.system.domain.entity.ViewTemplateDO;

import java.util.List;

/**
 * 页面展示模板Mapper接口
 * 
 * @author sdms
 * @date 2022-02-15
 */
public interface ViewTemplateMapper {
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
     * 新增页面展示模板
     *
     * @param viewTemplate 页面展示模板
     * @return 结果
     */
    public int insertViewTemplate(ViewTemplate viewTemplate);

    /**
     * 修改页面展示模板
     *
     * @param viewTemplate 页面展示模板
     * @return 结果
     */
    public int updateViewTemplate(ViewTemplate viewTemplate);

    /**
     * 删除页面展示模板
     *
     * @param id 页面展示模板ID
     * @return 结果
     */
    public int deleteViewTemplateById(Integer id);

    /**
     * 批量删除页面展示模板
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteViewTemplateByIds(String[] ids);
    /**
     * 查询用户默认模板
     *
     * @param  viewTemplate
     * @return 页面展示模板
     */
    List<ViewTemplateDO> templateViewList(ViewTemplate viewTemplate);

    int deleteTemplateGroup(Integer fieldId);

    /**
     * 逻辑删除
     * @param fieldId
     * @return
     */
    int fakeDeleteTemplateGroup(Integer fieldId);
}
