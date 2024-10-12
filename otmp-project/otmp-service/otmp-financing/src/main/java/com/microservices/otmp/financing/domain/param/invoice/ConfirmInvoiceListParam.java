package com.microservices.otmp.financing.domain.param.invoice;

import com.microservices.otmp.common.core.page.PageDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConfirmInvoiceListParam extends PageDomain {

    private List<Long> unchecked;
    private List<Long> checked;
    private String bankId;
    private String maturityDate;
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
