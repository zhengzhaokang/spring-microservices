package com.microservices.otmp.financing.domain.param.entity;

import com.microservices.otmp.system.domain.SysOperLog;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class EditEntityParam extends EntityParam{

    @NonNull
    private Long id;
    /**
     * 更新人，前端无需传入，仅用于传递参数
     */
    private String updateBy;

}
