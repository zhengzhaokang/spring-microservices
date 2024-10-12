package com.microservices.otmp.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResult {
    private String accountId;
    private String initUrl;
    private String code;
    private String timestamp;
}
