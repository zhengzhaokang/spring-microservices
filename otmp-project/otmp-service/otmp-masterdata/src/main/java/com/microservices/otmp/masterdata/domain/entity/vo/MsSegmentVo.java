package com.microservices.otmp.masterdata.domain.entity.vo;

import com.microservices.otmp.common.annotation.Excel;
import lombok.Data;


@Data
public class MsSegmentVo
{
    private static final long serialVersionUID = 1L;

    /** Business Group */
    private String businessGroup;

    /** Segment */
    private String segmentCode;

    /** Segment Name */
    private String segmentName;
}
