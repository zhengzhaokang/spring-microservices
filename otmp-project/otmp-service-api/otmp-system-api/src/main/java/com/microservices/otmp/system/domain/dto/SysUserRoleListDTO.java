package com.microservices.otmp.system.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class SysUserRoleListDTO {
    private Long roleId;
    private String loginName;
    private String userName;
    private List<Long>userIds;
}
