package com.microservices.otmp.filestorage.dto;

import com.microservices.otmp.common.core.domain.BaseDTO;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SystemFileParam extends BaseDTO {
    /**
     * 文件唯一id，系统生成
     */
    private String fileUuid;

    private String dataId;
}
