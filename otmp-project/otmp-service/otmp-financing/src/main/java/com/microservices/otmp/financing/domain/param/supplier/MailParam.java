package com.microservices.otmp.financing.domain.param.supplier;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class MailParam {

    private String firstName;
    private String lastName;
    private String companyName;
    @NotBlank
    @Email
    private String email;
    private String customMessage;
    private Boolean onShore;
    private String designation;
    private String phoneRegion;
    private String phone;

    /**
     * 前端不用传入，只在controller和service间传参使用
     */
    private String createBy;
    private String updateBy;
}
