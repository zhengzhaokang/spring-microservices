package com.microservices.otmp.finance.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * Finance Batch Info对象 finance_batch
 *
 * @author lovefamily
 * @date 2023-10-12
 */
@Data
public class FinanceBatchDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * Batch Number
     */

    private String batchNumber;

    /**
     * Submission Date
     */

    private Date submissionDate;

    /**
     * Status
     */

    private String status;

    /**
     * Discount Date
     */

    private Date discountDate;

    /**
     * Discount Amount
     */

    private BigDecimal discountAmount;

    /**
     * Delete Flag
     */

    private Boolean deleteFlag;

    /**
     * Maturity Date
     */

    private String maturityDate;
    private String submitCurrency;
    private String submitAmount;
    private String statusDescription;
    private Long supplierId;
    private String bankId;
    private Long entityId;
    private BigDecimal interestRate;
    private BigDecimal discount;
    private String tenor;


}
