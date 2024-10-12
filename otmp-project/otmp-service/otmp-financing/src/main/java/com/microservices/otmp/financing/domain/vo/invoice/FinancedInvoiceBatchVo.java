package com.microservices.otmp.financing.domain.vo.invoice;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.financing.domain.entity.FinancedInvoiceBatchDo;
import com.microservices.otmp.financing.domain.entity.FinancedInvoiceBatchDo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FinancedInvoiceBatchVo {

    private String batchId;
    private String batchNumber;
    private String entityName;
    private Double discountAmount;
    private String discountDate;

    public FinancedInvoiceBatchVo(FinancedInvoiceBatchDo financed){
        this.setBatchId(String.valueOf(financed.getBatchId()));
        this.setBatchNumber(financed.getBatchNumber());
        this.setEntityName(financed.getEntityName());
        this.setDiscountAmount(financed.getDiscountAmount());
        if(financed.getDiscountDate() != null){
            this.setDiscountDate(DateUtils.parseDateToStr(DateUtils.DATE_PATTERN,financed.getDiscountDate()));
        }
    }

}
