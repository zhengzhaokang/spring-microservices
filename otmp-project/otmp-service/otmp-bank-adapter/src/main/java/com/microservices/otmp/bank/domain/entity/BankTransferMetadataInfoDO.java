package com.microservices.otmp.bank.domain.entity;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * Bank Transfer Metadata Info对象 bank_transfer_metadata_info
 *
 * @author lovefamily
 * @date 2023-08-29
 */
@Data
public class BankTransferMetadataInfoDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    /**
     * Metadata Info Id
     */

    private Long metadataInfoId;

    /**
     * Transfer Type-[request;response]
     */

    private String transferType;

    /**
     * Transfer Group Number-[1,2,3...]
     */
    private String groupNumber;
    private Integer startLineNumber;
    private String includeHeader;

    /**
     * Table Name
     */

    private String tableName;

    /**
     * Column Name
     */

    private String columnName;

    /**
     * Column Comment
     */

    private String columnComment;

    /**
     * Column Type
     */

    private String columnType;

    /**
     * Java Type
     */

    private String javaType;

    /**
     * Java Field
     */

    private String javaField;

    /**
     * Bank Type-[BNPP;DBS]
     */

    private String bankType;

    /**
     * Field Format-[yyyyMMdd]
     */

    private String fieldFormat;

    /**
     * Logic Component
     */

    private String logicComponent;

    /**
     * Field Type-[header;body]
     */

    private String fieldType;

    /**
     * Is Required
     */

    private String isRequired;

    /**
     * Is Pk
     */

    private String isPk;

    /**
     * Sort
     */

    private Integer sort;

    /**
     * Status
     */

    private String status;


}
