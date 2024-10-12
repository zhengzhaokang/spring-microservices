package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.system.domain.ViewTemplate;
import com.microservices.otmp.system.domain.entity.ViewTemplateDO;
import com.microservices.otmp.system.manager.ViewTemplateManager;
import com.microservices.otmp.system.mapper.ViewTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 页面展示模板Mapper接口
 *
 * @author sdms
 * @date 2022-02-15
 */

@Service
public class ViewTemplateManagerImpl implements ViewTemplateManager {

    @Autowired
    private ViewTemplateMapper viewTemplateMapper;

    @Override
    public ViewTemplateDO selectViewTemplateById(Integer id) {
        return viewTemplateMapper.selectViewTemplateById(id);
    }

    @Override
    public List<ViewTemplateDO> selectViewTemplateList(ViewTemplate viewTemplate) {
        return viewTemplateMapper.selectViewTemplateList(viewTemplate);
    }

    @Override
    public int insertViewTemplate(ViewTemplate viewTemplate) {
        viewTemplate.setCreateTime(DateUtils.getNowDate());
        viewTemplate.setUpdateTime(DateUtils.getNowDate());
        return viewTemplateMapper.insertViewTemplate(viewTemplate);
    }

    @Override
    public int updateViewTemplate(ViewTemplate viewTemplate) {
        viewTemplate.setUpdateTime(DateUtils.getNowDate());
        return viewTemplateMapper.updateViewTemplate(viewTemplate);
    }

    @Override
    public int deleteViewTemplateById(Integer id) {
        return viewTemplateMapper.deleteViewTemplateById(id);
    }

    @Override
    public int deleteViewTemplateByIds(String[] ids) {
        return viewTemplateMapper.deleteViewTemplateByIds(ids);
    }

    @Override
    public List<ViewTemplateDO> templateViewList(ViewTemplate viewTemplate) {
        return viewTemplateMapper.templateViewList(viewTemplate);
    }

    @Override
    public int deleteTemplateGroup(Integer fieldId) {
        return viewTemplateMapper.deleteTemplateGroup(fieldId);
    }

    @Override
    public int fakeDeleteTemplateGroup(Integer fieldId) {
        return viewTemplateMapper.fakeDeleteTemplateGroup(fieldId);
    }
}
