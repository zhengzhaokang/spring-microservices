package com.microservices.otmp.bank.domain.dto;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;

/**
 * Bank Transfer Metadata Info对象 bank_transfer_metadata_info
 *
 * @author lovefamily
 * @date 2023-08-29
 */
@Data
public class BankTransferMetadataInfoDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * Metadata Info Id
     */
    @Excel(name = "Metadata Info Id")
    private Long metadataInfoId;

    /**
     * Transfer Type-[request;response]
     */
    @Excel(name = "Transfer Type")
    private String transferType;
    /**
     * Transfer Group Number-[1,2,3...]
     */
    @Excel(name = "Transfer Group Number")
    private String groupNumber;

    private Integer startLineNumber;
    private String includeHeader;

    /**
     * Table Name
     */
    @Excel(name = "Table Name")
    private String tableName;

    /**
     * Column Name
     */
    @Excel(name = "Column Name")
    private String columnName;

    /**
     * Column Comment
     */
    @Excel(name = "Column Comment")
    private String columnComment;

    /**
     * Column Type
     */
    @Excel(name = "Column Type")
    private String columnType;

    /**
     * Java Type
     */
    @Excel(name = "Java Type")
    private String javaType;

    /**
     * Java Field
     */
    @Excel(name = "Java Field")
    private String javaField;

    /**
     * Bank Type-[BNPP;DBS]
     */
    @Excel(name = "Bank Type")
    private String bankType;

    /**
     * Field Format-[yyyyMMdd]
     */
    @Excel(name = "Field Format")
    private String fieldFormat;

    /**
     * Logic Component
     */
    @Excel(name = "Logic Component")
    private String logicComponent;

    /**
     * Field Type-[header;body]
     */
    @Excel(name = "Field Type")
    private String fieldType;

    /**
     * Is Required
     */
    @Excel(name = "Is Required")
    private String isRequired;

    /**
     * Is Pk
     */
    @Excel(name = "Is Pk")
    private String isPk;

    /**
     * Sort
     */
    @Excel(name = "Sort")
    private Integer sort;

    /**
     * Status
     */
    @Excel(name = "Status")
    private String status;

}
