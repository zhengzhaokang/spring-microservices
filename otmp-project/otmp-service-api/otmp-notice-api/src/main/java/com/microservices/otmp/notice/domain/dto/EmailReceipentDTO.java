package com.microservices.otmp.notice.domain.dto;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;

/**
 * Email Receipent对象 email_receipent
 * 
 * @author lovefamily
 * @date 2022-11-10
 */
@Data
public class EmailReceipentDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** Business Group */
    @Excel(name = "Business Group")
    private String businessGroup;

    /** Geo */
    @Excel(name = "Geo")
    private String geoCode;

    /** Job Type */
    @Excel(name = "Job Type")
    private String jobType;

    /** Email Receipent */
    @Excel(name = "Email Receipent")
    private String emailReceipent;

    /** Delete Flag */
    @Excel(name = "Delete Flag")
    private Boolean deleteFlag;

    @Excel(name = "Remark")
    private String remark;

}
