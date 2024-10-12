package com.microservices.otmp.financing.domain.param.limit;

import com.microservices.otmp.common.core.page.PageDomain;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class OutstandingListParam extends PageDomain {

    @NotEmpty
    private String bankId;
    @NotNull
    private Long entityId;

}
