package com.microservices.otmp.financing.remote.param;

import lombok.Data;

@Data
public class RemoteInvoiceSubmitParam {

    private String invoiceUniqueId;
    private String status;

}
