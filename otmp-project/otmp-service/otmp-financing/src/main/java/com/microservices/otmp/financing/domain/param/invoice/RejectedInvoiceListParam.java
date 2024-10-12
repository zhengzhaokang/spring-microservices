package com.microservices.otmp.financing.domain.param.invoice;

import com.microservices.otmp.common.core.page.PageDomain;
import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Data
public class RejectedInvoiceListParam extends PageDomain {

    /**
     * 实体名称
     */
    private String entityName;
    /**
     * 发票编号
     */
    private String invoiceNum;
    /**
     * 查询类型，1：Invoices，2：Credit Notes
     */
    @NotNull
    private Integer type;
    /**
     * userId，前端无需传入，仅用于Controller到Service的参数传递
     */
    private Long userId;

    private String supplierId;
}
