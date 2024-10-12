package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.ViewUserDefTemplate;
import com.microservices.otmp.system.domain.entity.ViewUserDefTemplateDO;
import com.microservices.otmp.system.manager.ViewUserDefTemplateManager;
import com.microservices.otmp.system.service.IViewUserDefTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户默认模板Service业务层处理
 *
 * @author sdms
 * @date 2022-02-23
 */
@Service
public class ViewUserDefTemplateServiceImpl implements IViewUserDefTemplateService {
    @Autowired
    private ViewUserDefTemplateManager viewUserDefTemplateManager;

    /**
     * 查询用户默认模板
     *
     * @param updateBy 用户默认模板ID
     * @return 用户默认模板
     */
    @Override
    public ViewUserDefTemplate selectViewUserDefTemplateById(String updateBy) {
        ViewUserDefTemplateDO viewUserDefTemplateDO = viewUserDefTemplateManager.selectViewUserDefTemplateById(updateBy);
        ViewUserDefTemplate viewUserDefTemplate = new ViewUserDefTemplate();
        org.springframework.beans.BeanUtils.copyProperties(viewUserDefTemplateDO, viewUserDefTemplate);
        return viewUserDefTemplate;
    }

    /**
     * 查询用户默认模板列表
     *
     * @param viewUserDefTemplate 用户默认模板
     * @return 用户默认模板
     */
    @Override
    public List<ViewUserDefTemplate> selectViewUserDefTemplateList(ViewUserDefTemplate viewUserDefTemplate) {
        List<ViewUserDefTemplateDO> viewUserDefTemplateDOS = viewUserDefTemplateManager.selectViewUserDefTemplateList(viewUserDefTemplate);
        List<ViewUserDefTemplate> viewUserDefTemplates = new ArrayList<>(viewUserDefTemplateDOS.size());
        BeanUtils.copyListProperties(viewUserDefTemplateDOS, viewUserDefTemplates, ViewUserDefTemplate.class);
        return viewUserDefTemplates;
    }

    /**
     * 新增用户默认模板
     *
     * @param viewUserDefTemplate 用户默认模板
     * @return 结果
     */
    @Override
    public int insertViewUserDefTemplate(ViewUserDefTemplate viewUserDefTemplate) {
        ViewUserDefTemplateDO viewUserDefTemplateDO = new ViewUserDefTemplateDO();
        org.springframework.beans.BeanUtils.copyProperties(viewUserDefTemplate, viewUserDefTemplateDO);
        viewUserDefTemplateDO.setCreateTime(DateUtils.getNowDate());
        return viewUserDefTemplateManager.insertViewUserDefTemplate(viewUserDefTemplateDO);
    }

    /**
     * 修改用户默认模板
     *
     * @param viewUserDefTemplate 用户默认模板
     * @return 结果
     */
    @Override
    public int updateViewUserDefTemplate(ViewUserDefTemplate viewUserDefTemplate) {
        ViewUserDefTemplateDO viewUserDefTemplateDO = new ViewUserDefTemplateDO();
        org.springframework.beans.BeanUtils.copyProperties(viewUserDefTemplate, viewUserDefTemplateDO);
        viewUserDefTemplateDO.setUpdateTime(DateUtils.getNowDate());
        return viewUserDefTemplateManager.updateViewUserDefTemplate(viewUserDefTemplateDO);
    }

    /**
     * 删除用户默认模板对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteViewUserDefTemplateByIds(String ids) {
        return viewUserDefTemplateManager.deleteViewUserDefTemplateByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户默认模板信息
     *
     * @param updateBy 用户默认模板ID
     * @return 结果
     */
    public int deleteViewUserDefTemplateById(String updateBy) {
        return viewUserDefTemplateManager.deleteViewUserDefTemplateById(updateBy);
    }
}
