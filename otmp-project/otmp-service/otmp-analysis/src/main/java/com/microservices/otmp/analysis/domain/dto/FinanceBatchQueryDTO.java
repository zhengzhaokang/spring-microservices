package com.microservices.otmp.analysis.domain.dto;

import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;


/**
 * Finance Batch Info对象 finance_batch
 *
 * @author lovefamily
 * @date 2023-11-10
 */
@Data
public class FinanceBatchQueryDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    private Long supplierId;

    private String bankId;

    private Long entityId;

    private String queryDateType;

    private String queryDateTime;


}
