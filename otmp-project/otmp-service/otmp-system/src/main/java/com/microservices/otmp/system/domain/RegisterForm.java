package com.microservices.otmp.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterForm {
    // 请求url
    private String url;

    // 用户名称，Email
    private String username;

    // Language i.e. zh_CN,en_US webauthn_lang
    private String webauthnLang;

    // Fixed Value e.g. src.example.com  webauthn_source
    private String webauthnSource;

    // RP Callback URL. webauthn_callback
    private String webauthnCallback;

    // Application Realm-ID (assigned by microservices-ID)  webauthn_realm
    private String webauthnRealm;

    // When callback, webauthn_state is sent to the third-part application,
    // and can be verified by application. webauthn_state
    private  String webauthnState;

    // Signed by SHA_256, lower(username去空) + webauthn_realm + publickey.
    private String signature;

    // 需要签名的字符串:username+realm+pubKey
    private String publicKey;

    //uilogin – will show a sign-in page
    //uilogout – Sign-Out; end an active authentication
    //forgetpassword – Initiate the forget Password process
    private String webauthnAction;
}
