package com.microservices.otmp.system.mapper;

import com.microservices.otmp.system.domain.ViewUserDefTemplate;
import com.microservices.otmp.system.domain.entity.ViewUserDefTemplateDO;

import java.util.List;

/**
 * 用户默认模板Mapper接口
 * 
 * @author sdms
 * @date 2022-02-23
 */
public interface ViewUserDefTemplateMapper 
{
    /**
     * 查询用户默认模板
     * 
     * @param updateBy 用户默认模板ID
     * @return 用户默认模板
     */
    public ViewUserDefTemplateDO selectViewUserDefTemplateById(String updateBy);

    /**
     * 查询用户默认模板列表
     * 
     * @param viewUserDefTemplate 用户默认模板
     * @return 用户默认模板集合
     */
    public List<ViewUserDefTemplateDO> selectViewUserDefTemplateList(ViewUserDefTemplate viewUserDefTemplate);

    /**
     * 新增用户默认模板
     * 
     * @param viewUserDefTemplate 用户默认模板
     * @return 结果
     */
    public int insertViewUserDefTemplate(ViewUserDefTemplateDO viewUserDefTemplate);

    /**
     * 修改用户默认模板
     * 
     * @param viewUserDefTemplate 用户默认模板
     * @return 结果
     */
    public int updateViewUserDefTemplate(ViewUserDefTemplateDO viewUserDefTemplate);

    /**
     * 删除用户默认模板
     * 
     * @param updateBy 用户默认模板ID
     * @return 结果
     */
    public int deleteViewUserDefTemplateById(String updateBy);

    /**
     * 批量删除用户默认模板
     * 
     * @param updateBys 需要删除的数据ID
     * @return 结果
     */
    public int deleteViewUserDefTemplateByIds(String[] updateBys);
}
