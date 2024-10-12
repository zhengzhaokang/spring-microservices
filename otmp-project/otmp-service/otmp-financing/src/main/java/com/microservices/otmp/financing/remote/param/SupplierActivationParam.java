package com.microservices.otmp.financing.remote.param;

import lombok.Data;

@Data
public class SupplierActivationParam {

    public static final String TARGET_SYSTEM_OTFP = "otfp";

    private String vendorName;
    private String supplierCode;
    private String companyCode;
    /**
     * 1: Active
     * 2: Suspend
     * 3: on hold
     * 4: cancel on hold
     */
    private String status;
    /**
     * BOE: "W"
     * APFinancing: null
     */
    private String supplierModel;
    private String bankVendor;
    private String validFrom;
    private String targetSystem = TARGET_SYSTEM_OTFP;
}
