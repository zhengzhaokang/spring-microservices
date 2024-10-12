package com.microservices.otmp.financing.remote.result;

import lombok.Data;

@Data
public class RegisterResult {

    public static final String CODE_104 = "USS-0104";

    private String accountId;
    private String initUrl;
    private String code;
    private String timestamp;
}
