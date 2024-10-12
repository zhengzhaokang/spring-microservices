package com.microservices.otmp.financing.domain.param.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class AddBankParam extends BankParam {

    /**
     * 创建人，前端无需传入，仅传递参数使用
     */
    private String createBy;
}
