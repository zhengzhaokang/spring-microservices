package com.microservices.otmp.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateResult {
    private String profileSource;

    private String accountID;

    private String username;

    private String sst;

    private String userType;

    private String code;

    private String timestamp;
}
