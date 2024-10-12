package com.microservices.otmp.analysis.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class BatchSummaryWrapperVo {

    private List<BatchSummaryVo> summaryList;
}
