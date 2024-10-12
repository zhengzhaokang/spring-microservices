package com.microservices.otmp.financing.domain.param.supplier;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RegisterParam {

    private String inventionCode;
    private String companyName;
    private String firstName;
    private String lastName;
    @NotBlank
    @Email
    private String email;
    private String designation;
    private String phone;
    private String phoneRegion;

    private String createBy;
    private String updateBy;
}
