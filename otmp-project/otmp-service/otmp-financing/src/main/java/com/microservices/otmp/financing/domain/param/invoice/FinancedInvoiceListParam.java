package com.microservices.otmp.financing.domain.param.invoice;

import com.microservices.otmp.common.core.page.PageDomain;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Data
public class FinancedInvoiceListParam extends PageDomain {

    /**
     * 实体名称
     */
    private String entityName;
    /**
     * 批编号
     */
    private String batchNum;
    /**
     * 日期，yyyy-MM-dd格式
     */
    private String discountedDate;
    /**
     * userId，前端无需传入，仅用于Controller到Service的参数传递
     */
    private Long userId;

    private String supplierId;
}
