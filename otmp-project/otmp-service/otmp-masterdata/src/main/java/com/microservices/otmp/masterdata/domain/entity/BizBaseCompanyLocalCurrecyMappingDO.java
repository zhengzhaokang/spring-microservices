package com.microservices.otmp.masterdata.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * companyCode和localcurrency映射对象 biz_base_company_local_currecy_mapping
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
@Data
public class BizBaseCompanyLocalCurrecyMappingDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** $column.columnComment */

    private String companyCode;

    /** $column.columnComment */

    private String localCurrencyCode;

    /** 删除标识 */
    private Integer delFlag;

    private String companyName;


}
