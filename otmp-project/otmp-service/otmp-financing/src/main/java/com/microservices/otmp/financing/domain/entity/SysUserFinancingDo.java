package com.microservices.otmp.financing.domain.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="sys_user",schema = "otfp_base")
@Data
public class SysUserFinancingDo {

    public static final String FLAG_NORMAL = "0";

    public static final String USER_TYPE_ADMIN = "00";
    public static final String USER_TYPE_ANCHOR = "01";
    public static final String USER_TYPE_SUPPLIER = "02";

    @Id
    private Long userId;
    private Long deptId;
    private String loginName;
    private String userName;
    private String userType;
    private String email;
    private String phonenumber;
    private Long supplierId;
    private String delFlag;

}
