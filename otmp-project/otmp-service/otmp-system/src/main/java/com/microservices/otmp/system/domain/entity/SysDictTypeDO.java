package com.microservices.otmp.system.domain.entity;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * 字典类型表 sys_dict_type
 *
 * @author lovefamily
 */
@Data
public class SysDictTypeDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    /**
     * 字典主键
     */
    @Excel(name = "Dict Id")
    private Long dictId;

    /**
     * 字典名称
     */
    @Excel(name = "Dict Name")
    private String dictName;

    /**
     * 字典类型
     */
    @Excel(name = "Dict Type ")
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "Status", readConverterExp = "0=Active,1=Inactive")
    private String status;

    /**
     * 数据展示状态（0仅配置功能使用 1展示给用户）
     */
    private String showDataStatus;

    private String ids;

    private Long[] idsArray;

}
