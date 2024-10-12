package com.microservices.otmp.financing.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancingRateDTO {
    private Long id;
    private String rateType;
    private Date rateDate;
    private String ratePeriod;
    private BigDecimal rate;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
    private Boolean deleteFlag;
    private String startDate;
    private String endDate;
}
