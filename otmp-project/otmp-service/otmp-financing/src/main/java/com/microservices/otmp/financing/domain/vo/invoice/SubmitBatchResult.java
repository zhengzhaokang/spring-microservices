package com.microservices.otmp.financing.domain.vo.invoice;

import com.microservices.otmp.financing.domain.entity.FinanceBatchDo;
import com.microservices.otmp.financing.domain.entity.FinanceBatchDo;
import lombok.Data;

import java.util.List;

@Data
public class SubmitBatchResult {
    private int count;
    private String amount;
    private List<FinanceBatchDo> successBatches;
}
