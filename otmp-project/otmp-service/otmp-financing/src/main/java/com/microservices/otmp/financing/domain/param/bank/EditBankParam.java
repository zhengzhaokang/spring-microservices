package com.microservices.otmp.financing.domain.param.bank;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class EditBankParam extends BankParam{

    @NonNull
    private String id;

    /**
     * 更新人，前端无需传入，仅传递参数使用
     */
    private String updateBy;
}
