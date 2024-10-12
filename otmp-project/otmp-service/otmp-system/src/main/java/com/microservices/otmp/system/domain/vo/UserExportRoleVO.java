package com.microservices.otmp.system.domain.vo;

import com.microservices.otmp.common.annotation.Excel;
import lombok.Data;

@Data
public class UserExportRoleVO {
    @Excel(name = "Role Name" ,width = 25)
    private String roleName;
    @Excel(name = "Menu Name" ,width = 25)
    private String menuName;
    @Excel(name = "Menu Key" ,width = 25)
    private String menuKey;
    @Excel(name = "Menu Type" ,width = 25)
    private String menuType;
}
