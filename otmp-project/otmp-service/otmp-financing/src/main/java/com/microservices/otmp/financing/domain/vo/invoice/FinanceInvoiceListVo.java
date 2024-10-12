package com.microservices.otmp.financing.domain.vo.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FinanceInvoiceListVo {

    private String maturityDateChangeable;
    /**
     * 默认选择的发票金额
     */
    private String amount;
    /**
     * debit的总条数
     */
    private Long debitTotal;
    /**
     * credit的总条数
     */
    private Long creditTotal;
    /**
     * debit列表
     */
    private List<InvoiceVo> debits;
    /**
     * credit列表（全量）
     */
    private List<InvoiceVo> credits;
    /**
     * 选中的debit的id列表
     */
    private List<String> checkedIds;
    /**
     * debit和credit的关联关系
     */
    private Map<String, Collection<String>> relations;

}
