package com.microservices.otmp.financing.domain.param.invoice;

import com.microservices.otmp.common.core.page.PageDomain;
import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Data
public class AvailableInvoiceListParam extends PageDomain {

    /**
     * 查询类型，1：Invoices，2：Credit Notes
     */
    @NotNull
    private Integer type;
//    @NotNull
    private String bankId;
    private Long userId;
}
