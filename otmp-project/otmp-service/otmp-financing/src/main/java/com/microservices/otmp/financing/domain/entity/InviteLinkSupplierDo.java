package com.microservices.otmp.financing.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class InviteLinkSupplierDo extends InviteLinkDo{
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String phoneRegion;
    private String designation;
}
