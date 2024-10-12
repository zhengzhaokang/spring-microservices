package com.microservices.otmp.masterdata.domain.entity.vo;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;


@Data
public class MsGtnTypeVo
{
    private static final long serialVersionUID = 1L;

    /** GEO */
    private String geoCode;

    /** GTN Type */
    private String gtnTypeCode;

    /** GTN Category */
    private String gtnCategoryCode;

    /** Order Reason */
    private String cndnOrderReason;

    /** Business Group */
    private String businessGroup;
}
