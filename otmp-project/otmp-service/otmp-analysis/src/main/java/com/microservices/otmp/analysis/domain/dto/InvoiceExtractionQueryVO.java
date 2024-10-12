package com.microservices.otmp.analysis.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * Invoice extraction statistics QueryVO对象 invoice_extraction_statistics
 *
 * @author lovefamily
 * @date 2023-10-27
 */
@Data
public class InvoiceExtractionQueryVO extends BaseDTO{

    /**
     * Supplier Id
     */
    private Long supplierId;

    /**
     * Supplier Code
     */
    private String supplierCode;

    /**
     * Company Code
     */
    private String companyCode;

    private String queryDateType;
    private String queryDateTime;
    private String startDateTime;
    private String endDateTime;


}
