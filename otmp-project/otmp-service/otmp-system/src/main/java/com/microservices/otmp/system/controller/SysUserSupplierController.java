package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.constant.UserConstants;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.kafka.util.KafkaSend;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.RandomUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.common.ConstantSystem;
import com.microservices.otmp.system.domain.*;
import com.microservices.otmp.system.domain.entity.SysRoleDO;
import com.microservices.otmp.system.enums.UserTypeEnum;
import com.microservices.otmp.system.manager.SysRoleManager;
import com.microservices.otmp.system.service.EmailBusinessService;
import com.microservices.otmp.system.service.ISysConfigService;
import com.microservices.otmp.system.service.ISysRoleService;
import com.microservices.otmp.system.service.ISysUserService;
import com.microservices.otmp.system.util.PasswordUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("user/supplier")
public class SysUserSupplierController extends BaseController {
    public static final String ADD_USER = "Add account ";

    private static final String DEFALUT_PASSWORD_KEY = "defalut_password";

    public static final String NO_UPDATE_ADMIN = "不允许修改超级管理员用户";

    public static final String WEB_FROM = "1";

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private SysRoleManager sysRoleManager;

    @Autowired
    private EmailBusinessService emailBusinessService;

    @Value("${kafka.topic.send-email}")
    private String sendEmailTopic;

    @Autowired
    private KafkaSend kafkaSend;

    @Autowired
    private ISysRoleService sysRoleService;

    @HasPermissions("system:supplier:list")
    @GetMapping("list")
    public TableDataInfo list(SysUser sysUser) {
        //设置用户类型
        setUserType(sysUser);
        setSupplierId(sysUser);
        //开始分页查询
        startPage();
        List<SysUser> sysUsers = sysUserService.selectUserList(sysUser);
        TableDataInfo dataTable = getDataTable(sysUsers);
        List<SysUserVO> sysUserVOs = Lists.newArrayList();
        BeanUtils.copyListProperties(sysUsers, sysUserVOs, SysUserVO.class, "userType", "roles", "roleIds");
        dataTable.setRows(sysUserVOs);
        return dataTable;
    }

    private void setSupplierId(SysUser sysUser) {
        long currentUserId = getCurrentUserId();
        SysUser currentUser = sysUserService.selectUserById(currentUserId);
        if (currentUser != null && StringUtils.isNotBlank(currentUser.getSupplierId())) {
            //限制SupplierId，前端传参需带上supplierId
            sysUser.setSupplierId(currentUser.getSupplierId());
        }
    }

    /**
     * 设置用户类型
     * @param sysUser
     */
    private void setUserType(SysUser sysUser) {
        sysUser.setUserType(UserTypeEnum.Supplier.getCode());
    }

    @GetMapping("get/{userId}")
    public ResultDTO<SysUserVO> get(@PathVariable("userId") Long userId) {
        SysUser sysUser = sysUserService.selectUserById(userId);
        if (sysUser != null) {
            sysUser.setUserType(null);
        }
        SysUserVO sysUserVO = new SysUserVO();
        BeanUtils.copyProperties(sysUser, sysUserVO);
        return ResultDTO.success(sysUserVO);
    }

    @HasPermissions("system:supplier:add")
    @PostMapping("save")
    @OperLog(title = "用户管理", businessType = BusinessType.INSERT)
    public ResultDTO<RegisterResult> addSave(@RequestBody SysUser sysUser) {
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(sysUserService.checkLoginNameUnique(sysUser.getLoginName()))) {
            return ResultDTO.fail(DefaultErrorMessage.SUPPLIER_ACCOUNT_EXIST,ADD_USER + sysUser.getLoginName() + " failed, account already exists");
        }else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(sysUserService.checkEmailUnique(sysUser))) {
            return ResultDTO.fail(ADD_USER + sysUser.getLoginName() + " failed, Email account already exists");
        }
        setUserType(sysUser); //设置用户类型
        setSupplierId(sysUser); // 设置supplierId
        List<SysUser> sysUsers = getSupplierUsers(sysUser);
        if (CollectionUtils.isNotEmpty(sysUsers) && sysUsers.size() >= 3) {
            return ResultDTO.fail(ADD_USER + sysUser.getLoginName() + " failed, The maximum number of users has been reached");
        }

        sysUser.setSalt(RandomUtil.randomStr(6));
        sysUser.setPassword(PasswordUtil.encryptPassword(sysUser.getLoginName(),
                sysConfigService.selectConfigByKey(DEFALUT_PASSWORD_KEY), sysUser.getSalt()));
        sysUser.setCreateBy(getLoginName());
        if (StringUtils.isBlank(sysUser.getUserName())) {
            sysUser.setUserName(sysUser.getLoginName());
        }
        sysUserService.insertUser(sysUser);

        RegisterResult registerResult = sysUserService.registermicroservicesId(sysUser.getLoginName());
        if (StringUtils.equals(sysUser.getFrom(), WEB_FROM)) { //页面创建才去发邮件
            emailBusinessService.addUserSendEmail(sysUser, registerResult, String.valueOf(getCurrentUserId()));
        }
        sysUser.setPassword(null);
        return ResultDTO.success(registerResult);
    }

    private List<SysUser> getSupplierUsers(SysUser sysUser) {
        SysUser param = new SysUser();
        param.setSupplierId(sysUser.getSupplierId());
        param.setStatus(ConstantSystem.STATUS_ENABLE);
        return sysUserService.selectUserList(param);
    }

    @HasPermissions("system:supplier:edit")
    @PostMapping("status")
    @OperLog(title = "用户管理", businessType = BusinessType.UPDATE)
    public ResultDTO<Integer> status(@RequestBody SysUser user) {
        if (null != user.getUserId() && SysUser.isAdmin(user.getUserId())) {
            return ResultDTO.fail(NO_UPDATE_ADMIN);
        }
        if (user.getUserId() == getCurrentUserId()) {
            return ResultDTO.fail("Not allowed to modify own status");
        }
        setSupplierId(user); // 设置supplierId
        List<SysUser> sysUsers = getSupplierUsers(user);
        if (StringUtils.equals(user.getStatus(), ConstantSystem.STATUS_ENABLE)){
            if (CollectionUtils.isNotEmpty(sysUsers) && sysUsers.size() >= 3) {
                return ResultDTO.fail(ADD_USER + user.getLoginName() + " failed, The maximum number of users has been reached");
            }
        }
        if (StringUtils.equals(user.getStatus(), ConstantSystem.STATUS_DISABLE)){
            filterSysUsers(sysUsers);
            if (CollectionUtils.isNotEmpty(sysUsers) && sysUsers.size() <= 1){
                return ResultDTO.fail("Change user status failed, At least one users are required");
            }
        }
        return ResultDTO.success(sysUserService.changeStatus(user));
    }

    private void filterSysUsers(List<SysUser> sysUsers) {
         sysUsers.removeIf(this::checkUser);
    }

    private boolean checkUser(SysUser sysUser) {
        if (sysUser == null) {
            return true;
        }
        Set<String> roleKeys = sysRoleService.selectRoleKeys(sysUser.getUserId());
        if (CollectionUtils.isEmpty(roleKeys)) {
            return true;
        }
        if (roleKeys.contains("SupplierAdmin")) {
            return false;
        }
        return true;
    }

    /**
     * 修改保存用户
     */
    @HasPermissions("system:supplier:edit")
    @OperLog(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("update")
    public ResultDTO<Object> editSave(@RequestBody SysUser sysUser) {
        if (null != sysUser.getUserId() && SysUser.isAdmin(sysUser.getUserId())) {
            return ResultDTO.fail(NO_UPDATE_ADMIN);
        } else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(sysUserService.checkEmailUnique(sysUser))) {
            return ResultDTO.fail("修改用户'" + sysUser.getLoginName() + "'失败，邮箱账号已存在");
        }
        //不允许更新用户类型
        if (sysUser != null) {
            sysUser.setUserType(null);
        }
        return ResultDTO.success(sysUserService.updateUser(sysUser));
    }

    @HasPermissions("system:supplier:del")
    @OperLog(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("remove")
    public ResultDTO<Integer> remove(String ids) {
        Long[] userIds = Convert.toLongArray(ids);
        if (userIds[0] == getCurrentUserId()) {
            return ResultDTO.fail("Not allowed to delete own account");
        }
        SysUser user = new SysUser();
        setSupplierId(user); // 设置supplierId
        List<SysUser> sysUsers = getSupplierUsers(user);
        filterSysUsers(sysUsers);
        if (CollectionUtils.isNotEmpty(sysUsers) && sysUsers.size() <= 1){
            return ResultDTO.fail("Delete user failed, At least one users are required");
        }
        return ResultDTO.success(sysUserService.deleteUserByIds(ids));
    }

    @GetMapping("select/roles")
    public ResultDTO<Object> selectUserRoles() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleKey(UserTypeEnum.Supplier.getName());
        List<SysRoleDO> sysRoles = sysRoleManager.selectRoleList(sysRole);
        return ResultDTO.success(sysRoles);
    }
}
