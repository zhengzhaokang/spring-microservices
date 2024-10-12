package com.microservices.otmp.financing.domain.param.bank;

import com.microservices.otmp.common.core.page.PageDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BankListParam extends PageDomain {

    /**
     * 状态，1：禁用，0：激活
     */
    private Integer status;
    private Long userId;

}
