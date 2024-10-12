package com.microservices.otmp.financing.domain.param.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class AddEntityParam extends EntityParam{
    /**
     * 创建人，前端无需传入，仅用于传递参数
     */
    private String createBy;
}
