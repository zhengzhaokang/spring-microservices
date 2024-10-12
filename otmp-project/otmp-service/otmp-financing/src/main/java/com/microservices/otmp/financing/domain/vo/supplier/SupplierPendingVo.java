package com.microservices.otmp.financing.domain.vo.supplier;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SupplierPendingVo extends SupplierVo {

    private String firstName;
    private String lastName;
    private String mail;
    private String inviteDate;
    private Boolean onShore;
    private String phone;
    private String phoneRegion;
    private String designation;

}
