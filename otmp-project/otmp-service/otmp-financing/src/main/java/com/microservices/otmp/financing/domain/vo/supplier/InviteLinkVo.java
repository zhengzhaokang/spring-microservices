package com.microservices.otmp.financing.domain.vo.supplier;

import lombok.Data;

@Data
public class InviteLinkVo {
    private String inviteCode;
    private String companyName;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean onshore;
    private String inviteDate;
    private String designation;
    private String phone;
    private String phoneRegion;
}
