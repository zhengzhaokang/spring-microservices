package com.microservices.otmp.system.domain;

import com.microservices.otmp.common.annotation.Excel;
import lombok.Data;

/**
 * @author qiaodf2
 */
@Data
public class ViewFieldExcel {
    @Excel(name = "vModel")
    private String vModel;
    @Excel(name = "标签宽度")
    private String labelWidthConfig;
    @Excel(name = "标题")
    private String labelConfig;
    @Excel(name = "标题")
    private String tagIconConfig;
    @Excel(name = "表单栅格")
    private Integer spanConfig;
    @Excel(name = "占位提示")
    private String placeholder;
    @Excel(name = "字段纬度")
    private String fieldRange;

}
