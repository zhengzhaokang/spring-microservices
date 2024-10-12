package com.microservices.otmp.financing.domain.param.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceSubmitParam {

    private List<Long> unchecked;
    private List<Long> checked;
    private String bankId;

    private List<MaturityDateMapParam> dateMap;

//    @NotEmpty
    private List<BankInvoice> invoiceIds;
    private Long userId;
    private String loginName;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class BankInvoice{
        private String bankId;
        private Long invoiceId;
    }

    @Data
    public static class MaturityDateMapParam{
        private String maturityDate;
        private String confirmedMaturityDate;
    }

}
