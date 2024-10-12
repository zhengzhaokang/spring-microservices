package com.microservices.otmp.financing.domain.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class IdParam {
    @NotNull
    private Long id;
    /**
     * 更新人，前端无需传入，仅用于参数传递
     */
    private String updateBy;
}
