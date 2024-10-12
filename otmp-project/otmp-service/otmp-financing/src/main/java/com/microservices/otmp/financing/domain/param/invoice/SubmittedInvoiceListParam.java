package com.microservices.otmp.financing.domain.param.invoice;

import com.microservices.otmp.common.core.page.PageDomain;
import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Data
public class SubmittedInvoiceListParam extends PageDomain {

    private String entityName;
    private String batchNum;
    @NotNull
    private Integer type;
    private String submittedDate;
    /**
     * userId，前端无需传入，仅用于Controller到Service的参数传递
     */
    private Long userId;

    private String supplierId;
}
