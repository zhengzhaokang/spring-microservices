package com.microservices.otmp.financing.domain.param.entity;

import com.microservices.otmp.common.core.page.PageDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EntityListParam extends PageDomain {
    private Integer status;
    private Long userId;
}
