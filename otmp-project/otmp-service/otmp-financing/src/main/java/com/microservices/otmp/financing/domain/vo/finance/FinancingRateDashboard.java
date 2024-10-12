package com.microservices.otmp.financing.domain.vo.finance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancingRateDashboard {
    private List<FinancingRateVO> financingRateVOList;

    private String maxRate;

    private String minRate;

    private Integer count;
}
