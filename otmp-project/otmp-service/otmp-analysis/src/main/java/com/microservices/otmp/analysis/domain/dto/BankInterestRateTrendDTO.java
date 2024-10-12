package com.microservices.otmp.analysis.domain.dto;


import lombok.Data;

import java.math.BigDecimal;

/**
 * BankInterestRateTrendDTO
 *
 * @author lovefamily
 * @date 2023-11-07
 */
@Data
public class BankInterestRateTrendDTO {


    private String bankId;

    private String queryDateType;

    private String startDateTime;

    private String endDateTime;

    private BigDecimal interestRate;

    private BigDecimal interestAmount;

    private String lastUpdateTime;


}
