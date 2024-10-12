package com.microservices.otmp.analysis.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * Bank Transfer Task Info对象 bank_transfer_task_info
 *
 * @author lovefamily
 * @date 2023-10-09
 */
@Data
public class BankTransferTaskInfoDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    /**
     * Bank Transfer Id
     */

    private Long bankTransferId;

    /**
     * Oper Type
     */

    private String operType;

    /**
     * Bank Code
     */

    private String bankCode;

    /**
     * Supplier Code
     */

    private String supplierCode;

    /**
     * Request File Name
     */

    private String requestFileName;

    /**
     * Response  File Name
     */

    private String responseFileName;

    /**
     * microservices Batch Number
     */

    private String microservicesBatchNumber;

    /**
     * File MD5
     */

    private String fileMd5;

    /**
     * Status
     */

    private String status;

    /**
     * Entity Id
     */

    private Long entityId;

    /**
     * Param Data
     */
    private String paramData;

    private String responseStatus;
    private String responseStatusDescription;


}
