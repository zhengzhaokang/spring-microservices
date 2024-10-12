package com.microservices.otmp.erp.domain;

import lombok.Data;

@Data
public class S4ApiItem {

    private Long id;
    private String recordCount;
    private String companyCode;
    private String vendorCode;
    private String requestStartDate;
    private String requestEndDate;
    private String invoiceCount;
    private String totalInvoiceAmount;
    private String creditNoteCount;
    private String totalCreditNoteAmount;

}
