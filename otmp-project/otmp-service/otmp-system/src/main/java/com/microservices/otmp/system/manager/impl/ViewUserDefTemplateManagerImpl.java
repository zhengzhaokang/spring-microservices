package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.ViewUserDefTemplate;
import com.microservices.otmp.system.domain.entity.ViewUserDefTemplateDO;
import com.microservices.otmp.system.manager.ViewUserDefTemplateManager;
import com.microservices.otmp.system.mapper.ViewUserDefTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户默认模板Mapper接口
 *
 * @author sdms
 * @date 2022-02-23
 */

@Service
public class ViewUserDefTemplateManagerImpl implements ViewUserDefTemplateManager {

    @Autowired
    private ViewUserDefTemplateMapper viewUserDefTemplateMapper;

    @Override
    public ViewUserDefTemplateDO selectViewUserDefTemplateById(String updateBy) {
        return viewUserDefTemplateMapper.selectViewUserDefTemplateById(updateBy);
    }

    @Override
    public List<ViewUserDefTemplateDO> selectViewUserDefTemplateList(ViewUserDefTemplate viewUserDefTemplate) {
        return viewUserDefTemplateMapper.selectViewUserDefTemplateList(viewUserDefTemplate);
    }

    @Override
    public int insertViewUserDefTemplate(ViewUserDefTemplateDO viewUserDefTemplate) {
        return viewUserDefTemplateMapper.insertViewUserDefTemplate(viewUserDefTemplate);
    }

    @Override
    public int updateViewUserDefTemplate(ViewUserDefTemplateDO viewUserDefTemplate) {
        return viewUserDefTemplateMapper.updateViewUserDefTemplate(viewUserDefTemplate);
    }

    @Override
    public int deleteViewUserDefTemplateById(String updateBy) {
        return viewUserDefTemplateMapper.deleteViewUserDefTemplateById(updateBy);
    }

    @Override
    public int deleteViewUserDefTemplateByIds(String[] updateBys) {
        return viewUserDefTemplateMapper.deleteViewUserDefTemplateByIds(updateBys);
    }
}
