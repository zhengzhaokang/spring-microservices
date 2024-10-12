package com.microservices.otmp.word.domain;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;

/**
 * pool对象 biz_sdms_pool
 *
 * @author shirui3
 * @date 2022-04-19
 */
@Data
public class RemoteBizSdmsWord extends BaseDTO {

    private Integer pageNum;
    private Integer pageSize;

    /** Pool Id */
    private Integer poolId;

    /** Geo */
    @Excel(name = "Geo")
    private String geo;

    /** Business Group */
    @Excel(name = "Business Group")
    private String businessGroup;

    /** Pool Code */
    @Excel(name = "Pool Code")
    private String poolCode;


}
