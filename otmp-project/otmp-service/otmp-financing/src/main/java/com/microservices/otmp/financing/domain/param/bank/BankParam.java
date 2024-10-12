package com.microservices.otmp.financing.domain.param.bank;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BankParam {

    @NotNull
    private String bankName;
    @NotNull
    private String region;
    @NotNull
    private List<String> currency;
    private String iban;
    private String swift;
    private String account;
    private String contactFocal;
    private String contactMail;
    @NotNull
    private String integrationChain;
    @NotNull
    private String erpVendorId;
    private String bankAddress;
    private Integer rank;
    private Long iconFileId;
}
