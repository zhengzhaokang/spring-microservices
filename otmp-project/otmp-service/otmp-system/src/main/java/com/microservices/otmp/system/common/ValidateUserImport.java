package com.microservices.otmp.system.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.microservices.otmp.common.ValidField;
import com.microservices.otmp.service.CustomerValidate;
import com.microservices.otmp.system.domain.SysRole;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.domain.SysUserDataScope;
import com.microservices.otmp.system.domain.SysUserRole;
import com.microservices.otmp.system.domain.entity.SysRoleDO;
import com.microservices.otmp.system.domain.entity.SysUserDataScopeDO;
import com.microservices.otmp.system.domain.vo.UserImportVo;
import com.microservices.otmp.system.manager.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component(value = "userImport")
public class ValidateUserImport implements CustomerValidate<UserImportVo> {


    public static final String ADD = "Add";
    public static final String REMOVE = "Remove";
    public static final String INVALID_ACTION = "Invalid action";
    public static final String ACTIVE = "Active";
    public static final String INACTIVE = "Inactive";
    public static final String INVALID_STATUS = "Invalid status";
    @Autowired
    private SysUserManager sysUserManager;

    @Autowired
    private SysRoleManager sysRoleManager;

    @Autowired
    private SysUserRoleManager sysUserRoleManager;

    @Autowired
    private SysUserDataScopeManager sysUserDataScopeManager;


    @Override
    public void beforeCustomValidate(UserImportVo baseImportDTO) {
        String action = baseImportDTO.getAction();
        if (StrUtil.isNotBlank(action) && !ADD.equals(action) && !REMOVE.equals(action)) {
            ValidField.setErrorMsg(baseImportDTO, INVALID_ACTION);
        }
        String status = baseImportDTO.getStatus();
        if (StrUtil.isNotBlank(status) && !ACTIVE.equals(status) && !INACTIVE.equals(status)) {
            ValidField.setErrorMsg(baseImportDTO, INVALID_STATUS);
        }
        String itCode = baseImportDTO.getItCode();
        SysUser sysUser = sysUserManager.selectUserByLoginName(itCode);
        if (null != sysUser && !sysUser.getUserName().equals(baseImportDTO.getUserName())) {
            ValidField.setErrorMsg(baseImportDTO, "User Name does not match existing record");
        }
        if (REMOVE.equals(action) && null == sysUser) {
            ValidField.setErrorMsg(baseImportDTO, "IT Code Not found");
        }
        if (sysUser != null && StrUtil.isBlank(baseImportDTO.getErrorMsg())) {
            setAttribute(baseImportDTO, sysUser);
        }
        validateRole(baseImportDTO);
        if (sysUser != null) {
            validRolesByAction(baseImportDTO, action);
            validateUserDataScopeByAction(baseImportDTO, action);
        }
    }

    private void validateRole(UserImportVo userImportVo) {
        String roleNames = userImportVo.getUserRole();
        Map<Long, String> roleMap = new HashMap<>();
        int errorCount = 0;
        if (StrUtil.isNotBlank(roleNames)) {
            String[] roles = roleNames.split(",");
            for (String role : roles) {
                SysRole sysRole = new SysRole();
                sysRole.setRoleName(role);
                List<SysRoleDO> sysRoles = sysRoleManager.selectRoleList(sysRole);
                if (CollUtil.isEmpty(sysRoles)) {
                    ValidField.setErrorMsg(userImportVo, "Role [" + role + "] does not exist");
                    errorCount++;
                } else {
                    roleMap.put(sysRoles.get(0).getRoleId(), role);
                }
            }
            if (errorCount == 0) {
                userImportVo.setRoleMaps(roleMap);
            }
        }
    }

    private void setAttribute(UserImportVo baseImportDTO, SysUser sysUser) {
        SysUserDataScope scope = new SysUserDataScope();
        scope.setUserItcode(baseImportDTO.getItCode());
        List<SysUserDataScopeDO> sysUserDataScopeList = sysUserDataScopeManager.selectSysUserDataScopeList(scope);
        baseImportDTO.setSysUserDataScopeList(sysUserDataScopeList);
        List<SysUserRole> sysUserRoles = sysUserRoleManager.selectUserRoleList(sysUser.getUserId().intValue());
        baseImportDTO.setSysUserRoleList(sysUserRoles);
        baseImportDTO.setSysUser(sysUser);
    }

    private void validRolesByAction(UserImportVo baseImportDTO, String action) {

        if (CollUtil.isNotEmpty(baseImportDTO.getRoleMaps()) && CollUtil.isNotEmpty(baseImportDTO.getSysUserRoleList())) {
            //导入进来的
            Set<Long> importRoleIds = baseImportDTO.getRoleMaps().keySet();
            //数据库中已经存在的
            List<Long> roleIds = baseImportDTO.getSysUserRoleList().stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(roleIds)) {
                for (Long importRoleId : importRoleIds) {
                    String roleName = baseImportDTO.getRoleMaps().get(importRoleId);
                    if (ADD.equals(action) && roleIds.contains(importRoleId)) {
                        ValidField.setErrorMsg(baseImportDTO, "Role [" + roleName + "] already assigned");
                    }
                    if (REMOVE.equals(action) && !roleIds.contains(importRoleId)) {
                        ValidField.setErrorMsg(baseImportDTO, " The user does not have Role [" + roleName + "]");
                    }
                }
            }
        }
        if (CollUtil.isEmpty(baseImportDTO.getSysUserRoleList()) && REMOVE.equals(action) && StrUtil.isNotBlank(baseImportDTO.getUserRole())) {
            ValidField.setErrorMsg(baseImportDTO, " The user does not have Role [" + baseImportDTO.getUserRole() + "]");
        }
    }


    private void validateUserDataScopeByAction(UserImportVo baseImportDTO, String action) {
        List<SysUserDataScopeDO> sysUserDataScopeDOS = baseImportDTO.getSysUserDataScopeList();
        if (CollUtil.isEmpty(sysUserDataScopeDOS) && REMOVE.equals(action)) {
            ValidField.setErrorMsg(baseImportDTO, "User doesn`t have any data permission");
            return;
        }
        String businessGroup = baseImportDTO.getBusinessGroup();
        if (StrUtil.isNotBlank(businessGroup)) {
            validDataScope(sysUserDataScopeDOS, DataScopeKeyConstants.business_group, "Business Group", baseImportDTO, action, businessGroup);
        }
        String geo = baseImportDTO.getGeoCode();
        if (StrUtil.isNotBlank(geo)) {
            validDataScope(sysUserDataScopeDOS, DataScopeKeyConstants.geo, "Geo", baseImportDTO, action, geo);
        }
        String rm = baseImportDTO.getRegionMarketCode();
        if (StrUtil.isNotBlank(rm)) {
            validDataScope(sysUserDataScopeDOS, DataScopeKeyConstants.region_market_code, "Region/Market", baseImportDTO, action, rm);
        }
        String salesOrg = baseImportDTO.getSalesOrgCode();
        if (StrUtil.isNotBlank(salesOrg)) {
            validDataScope(sysUserDataScopeDOS, DataScopeKeyConstants.sales_org_code, "Sales Org", baseImportDTO, action, salesOrg);
        }
        String salesOffice = baseImportDTO.getSalesOfficeCode();
        if (StrUtil.isNotBlank(salesOffice)) {
            validDataScope(sysUserDataScopeDOS, DataScopeKeyConstants.sales_office_code, "Sales Office", baseImportDTO, action, salesOffice);
        }
        String segment = baseImportDTO.getSegmentCode();
        if (StrUtil.isNotBlank(segment)) {
            validDataScope(sysUserDataScopeDOS, DataScopeKeyConstants.segment_code, "Segment", baseImportDTO, action, segment);
        }
        String gtn = baseImportDTO.getGtnTypeCode();
        if (StrUtil.isNotBlank(gtn)) {
            validDataScope(sysUserDataScopeDOS, DataScopeKeyConstants.gtn_type_code, "Gtn Type", baseImportDTO, action, gtn);
        }
    }

    private void validDataScope(List<SysUserDataScopeDO> sysUserDataScopeDOS, String key, String excelName, UserImportVo baseImportDTO, String action, String importValue) {
        Set<String> set = getValueByKey(sysUserDataScopeDOS, key);
        if (CollUtil.isNotEmpty(set)) {
            String[] strings = importValue.split(",");
            for (String var : strings) {
                if (ADD.equals(action) && set.contains(var)) {
                    ValidField.setErrorMsg(baseImportDTO, excelName + " [" + var + "] already assigned");
                }
                if (REMOVE.equals(action) && !set.contains(var)) {
                    ValidField.setErrorMsg(baseImportDTO, "The user does not have data permission " + excelName + " [" + var + "]");
                }
            }
        } else if (CollUtil.isEmpty(set) && REMOVE.equals(action)) {
            ValidField.setErrorMsg(baseImportDTO, "The user does not have data permission " + excelName + " [" + importValue + "p]");
        }

    }

    private Set<String> getValueByKey(List<SysUserDataScopeDO> list, String key) {
        return list.stream().filter(item -> key.equals(item.getDataScopeKey())).map(SysUserDataScopeDO::getDataScopeValue).collect(Collectors.toSet());
    }


    @Override
    public void afterCustomValidate(UserImportVo baseImportEntity) {

    }

    @Override
    public void beforeCollectionValidate(List<UserImportVo> list) {

    }

    @Override
    public void afterCollectionValidate(List<UserImportVo> list, int errorCount) {

    }
}
