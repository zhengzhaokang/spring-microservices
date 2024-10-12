package com.microservices.otmp.analysis.domain.dto;


import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * Bank Invoice Batch Statistics
 *
 * @author lovefamily
 * @date 2023-10-30
 */
@Data
public class BankInvoiceBatchStatisticsNumDTO {

    private String bankCode;

    private Long bankId;

    private String supplierCode;

    private String companyCode;

    private Long supplierId;

    private Long entityId;

    private String queryDateType;

    private String startDateTime;

    private String endDateTime;

    private Integer submittedNum;

    private Integer rejectedNum;

    private Integer acceptedNum;

    private String lastUpdateTime;

    private String queryDateTime;





}
