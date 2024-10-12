package com.microservices.otmp.financing.domain.param.invoice;

import com.microservices.otmp.common.core.page.PageDomain;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Builder
@EqualsAndHashCode(callSuper = false)
@Data
public class FinancedInvoiceDetailParam extends PageDomain {

    private Long batchId;
    @NotNull
    private Integer type;
}
