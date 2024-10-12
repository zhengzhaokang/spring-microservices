package com.microservices.otmp.system.domain.entity;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDO;

/**
 * 用户默认模板对象 view_user_def_template
 *
 * @author sdms
 * @date 2022-02-23
 */
public class ViewUserDefTemplateDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    /**
     * IT Code
     */
    @Excel(name = "IT Code")
    private String itCode;

    /**
     * 模板ID
     */
    @Excel(name = "模板ID")
    private Integer templateId;

    public void setItCode(String itCode) {
        this.itCode = itCode;
    }

    public String getItCode() {
        return itCode;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

}
