package com.microservices.otmp.masterdata.domain.dto;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;
import lombok.ToString;

/**
 * salesOffice与profitMapping映射对象 biz_base_sales_office_profit_center_mapping
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
@Data
@ToString
public class BizBaseSalesOfficeProfitCenterMappingDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String salesOfficeCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String profitCenterCode;

    /** $column.columnComment */
    private Integer delFlag;

    private String salesOrgCode;

}
