package com.microservices.otmp.financing.domain.param.supplier;

import com.microservices.otmp.financing.domain.param.IdParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class OnHoldParam extends IdParam {

    @NotNull
    private String startTime;
    @NotNull
    private String endTime;

}
