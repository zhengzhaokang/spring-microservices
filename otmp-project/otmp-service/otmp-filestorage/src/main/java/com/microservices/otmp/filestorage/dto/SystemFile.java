package com.microservices.otmp.filestorage.dto;

import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemFile extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long fileId;

    /**
     * 文件唯一id，系统生成
     */
    private String fileUuid;

    /**
     * 原始文件名
     */
    private String fileNameOri;

    /**
     * 文件夹
     */
    private String folder;

    /**
     * 状态：0：文件不可用状态  1：文件可用状态 默认可用
     */
    private String fileStatus;

    /**
     * 对私时存储item_number，对公时存储ticketNumber
     */
    private Long dataId;

    /**
     * 格式化时间
     */
    private String createTimeFormat;
}
