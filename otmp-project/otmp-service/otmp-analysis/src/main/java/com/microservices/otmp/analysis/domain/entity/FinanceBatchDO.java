package com.microservices.otmp.analysis.domain.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * Finance Batch Info对象 finance_batch
 * 
 * @author lovefamily
 * @date 2023-11-10
 */
@Data
public class FinanceBatchDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** Batch Number */

    private String batchNumber;

    /** Submission Date */

    private Date submissionDate;

    /** Status */

    private String status;

    /** Discount Date */

    private Date discountDate;

    /** Discount Amount */

    private BigDecimal discountAmount;

    /** Delete Flag */

    private Boolean deleteFlag;

    /** Maturity Date */

    private String maturityDate;

    /** Submit Currency */

    private String submitCurrency;

    /** Submit Amount */

    private BigDecimal submitAmount;

    /** Status Description */

    private String statusDescription;

    /** Supplier Id */

    private Long supplierId;

    /** Bank Id */

    private String bankId;

    /** Entity Id */

    private Long entityId;

    /** Interest Rate */

    private BigDecimal interestRate;

    /** Tenor */

    private String tenor;

    /** Interest Amount */

    private BigDecimal discount;


}
