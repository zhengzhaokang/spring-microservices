package com.microservices.otmp.system.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class microservicesIdParam {
    // webauthn_st
    private String webauthnSt;

    // Server service token, used to call the microservicesID interface service, only for server to server.
    private String webauthnSst;
}
