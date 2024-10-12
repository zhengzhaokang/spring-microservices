package com.microservices.otmp.system.service;

import com.microservices.otmp.system.domain.ViewUserDefTemplate;

import java.util.List;

/**
 * 用户默认模板Service接口
 * 
 * @author sdms
 * @date 2022-02-23
 */
public interface IViewUserDefTemplateService 
{
    /**
     * 查询用户默认模板
     * 
     * @param updateBy 用户默认模板ID
     * @return 用户默认模板
     */
    public ViewUserDefTemplate selectViewUserDefTemplateById(String updateBy);

    /**
     * 查询用户默认模板列表
     * 
     * @param viewUserDefTemplate 用户默认模板
     * @return 用户默认模板集合
     */
    public List<ViewUserDefTemplate> selectViewUserDefTemplateList(ViewUserDefTemplate viewUserDefTemplate);

    /**
     * 新增用户默认模板
     * 
     * @param viewUserDefTemplate 用户默认模板
     * @return 结果
     */
    public int insertViewUserDefTemplate(ViewUserDefTemplate viewUserDefTemplate);

    /**
     * 修改用户默认模板
     * 
     * @param viewUserDefTemplate 用户默认模板
     * @return 结果
     */
    public int updateViewUserDefTemplate(ViewUserDefTemplate viewUserDefTemplate);

    /**
     * 批量删除用户默认模板
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteViewUserDefTemplateByIds(String ids);

    /**
     * 删除用户默认模板信息
     * 
     * @param updateBy 用户默认模板ID
     * @return 结果
     */
    public int deleteViewUserDefTemplateById(String updateBy);
}
