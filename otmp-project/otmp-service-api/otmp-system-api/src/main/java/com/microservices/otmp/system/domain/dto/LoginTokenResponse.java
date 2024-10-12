package com.microservices.otmp.system.domain.dto;

import com.microservices.otmp.common.core.page.RemoteResponse;
import lombok.Data;

@Data
public class LoginTokenResponse extends RemoteResponse {

    private String token;
}
