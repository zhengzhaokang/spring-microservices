package com.microservices.otmp.analysis.domain.dto;


import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;

/**
 * Bank Transfer Task Info对象 bank_transfer_task_info
 *
 * @author lovefamily
 * @date 2023-10-09
 */
@Data
public class BankTransferTaskInfoDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * Bank Transfer Id
     */
    @Excel(name = "Bank Transfer Id")
    private Long bankTransferId;

    /**
     * Oper Type
     */
    @Excel(name = "Oper Type")
    private String operType;

    /**
     * Bank Code
     */
    @Excel(name = "Bank Code")
    private String bankCode;

    /**
     * Supplier Code
     */
    @Excel(name = "Supplier Code")
    private String supplierCode;

    /**
     * Request File Name
     */
    @Excel(name = "Request File Name")
    private String requestFileName;

    /**
     * Response  File Name
     */
    @Excel(name = "Response  File Name")
    private String responseFileName;

    /**
     * microservices Batch Number
     */
    @Excel(name = "microservices Batch Number")
    private String microservicesBatchNumber;

    /**
     * File MD5
     */
    @Excel(name = "File MD5")
    private String fileMd5;

    /**
     * Status
     */
    @Excel(name = "Status")
    private String status;

    /**
     * Entity Id
     */
    @Excel(name = "Entity Id")
    private Long entityId;
    /**
     * Param Data
     */
    private String paramData;

    private String responseStatus;
    private String responseStatusDescription;

}
