package com.microservices.otmp.system.domain.entity;

import com.microservices.otmp.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * 的字段信息对象 view_Field_info
 *
 * @author sdms
 * @date 2022-02-16
 */
@Data
public class ViewFieldInfoDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Excel(name = "ID")
    private Integer attnum;

    /**
     * 表名称
     */
    @Excel(name = "表名称")
    private String relname;

    /**
     * 字段
     */
    @Excel(name = "字段")
    private String field;

    /**
     * 字段类型
     */
    @Excel(name = "字段类型")
    private String fieldType;

    /**
     * label
     */
    @Excel(name = "label")
    private String fieldLabel;

    @Excel(name = "fieldTest")
    private String fieldTest;

    private boolean checked;

}
