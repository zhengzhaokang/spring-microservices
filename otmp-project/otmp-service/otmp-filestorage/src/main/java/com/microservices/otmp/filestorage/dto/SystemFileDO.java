package com.microservices.otmp.filestorage.dto;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.*;
/**
 * 系统文件表 sys_file
 *
 * @author zhangxp21
 * @date 2022-9-5
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemFileDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long           fileId;

    /** 文件唯一id，系统生成 */
    private String           fileUuid;

    /** 原始文件名 */
    private String           fileNameOri;

    /** 文件夹 */
    private String            folder;

    /** 状态：0：文件不可用状态  1：文件可用状态 */
    private String            fileStatus;

    /** 对私时存储item_number，对公时存储ticketNumber */
    private Long            dataId;

}
