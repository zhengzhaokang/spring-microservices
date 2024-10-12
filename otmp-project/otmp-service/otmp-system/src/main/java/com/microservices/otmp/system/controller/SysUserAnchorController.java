package com.microservices.otmp.system.controller;

import com.alibaba.fastjson.JSON;
import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.constant.UserConstants;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.kafka.listener.KafkaCallback;
import com.microservices.otmp.common.kafka.util.KafkaSend;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.RandomUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.notice.domain.dto.EmailSendDTO;
import com.microservices.otmp.notice.feign.RemoteEmailService;
import com.microservices.otmp.system.common.ConstantSystem;
import com.microservices.otmp.system.common.EmailConstant;
import com.microservices.otmp.system.common.KafkaFactory;
import com.microservices.otmp.system.domain.*;
import com.microservices.otmp.system.domain.RegisterResult;
import com.microservices.otmp.system.domain.SysRole;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.domain.SysUserVO;
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
import java.util.UUID;

@RestController
@RequestMapping("user/anchor")
public class SysUserAnchorController extends BaseController {

    public static final String ADD_USER = "Add account ";

    private static final String DEFALUT_PASSWORD_KEY = "defalut_password";

    public static final String NO_UPDATE_ADMIN = "不允许修改超级管理员用户";

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private SysRoleManager sysRoleManager;

    @Autowired
    private RemoteEmailService remoteEmailService;

    @Autowired
    private EmailBusinessService emailBusinessService;

    @Value("${kafka.topic.send-email}")
    private String sendEmailTopic;

    @Autowired
    private KafkaSend kafkaSend;

    @Autowired
    private ISysRoleService sysRoleService;

    @HasPermissions("system:anchor:list")
    @GetMapping("list")
    public TableDataInfo list(SysUser sysUser) {
        startPage();
        //设置用户类型，可以看到 Supplier的类型
        List<String> userTypes = Lists.newArrayList();
        userTypes.add(UserTypeEnum.Anchor.getCode());
//        userTypes.add(UserTypeEnum.Supplier.getCode());
        sysUser.setUserTypes(userTypes);
        List<SysUser> sysUsers = sysUserService.selectUserList(sysUser);
        TableDataInfo dataTable = getDataTable(sysUsers);
        List<SysUserVO> sysUserVOs = Lists.newArrayList();
        BeanUtils.copyListProperties(sysUsers, sysUserVOs, SysUserVO.class, "userType", "roles", "roleIds");
        dataTable.setRows(sysUserVOs);
        return dataTable;
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

    /**
     * 设置用户类型
     * @param sysUser
     */
    private void setUserInfo(SysUser sysUser) {
        sysUser.setUserType(UserTypeEnum.Anchor.getCode());
        if (StringUtils.isBlank(sysUser.getUserName())) {
            sysUser.setUserName(sysUser.getLoginName());
        }
    }

    @HasPermissions("system:anchor:add")
    @PostMapping("save")
    @OperLog(title = "用户管理", businessType = BusinessType.INSERT)
    public ResultDTO<Object> addSave(@RequestBody SysUser sysUser) {
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(sysUserService.checkLoginNameUnique(sysUser.getLoginName()))) {
            return ResultDTO.fail(ADD_USER + sysUser.getLoginName() + " failed, account already exists");
        } else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(sysUserService.checkEmailUnique(sysUser))) {
            return ResultDTO.fail(ADD_USER + sysUser.getLoginName() + " failed, Email account already exists");
        }
        sysUser.setSalt(RandomUtil.randomStr(6));
        sysUser.setPassword(PasswordUtil.encryptPassword(sysUser.getLoginName(),
                sysConfigService.selectConfigByKey(DEFALUT_PASSWORD_KEY), sysUser.getSalt()));
        sysUser.setCreateBy(getLoginName());
        //设置用户信息
        setUserInfo(sysUser);
        //新增用户信息
        sysUserService.insertUser(sysUser);
        RegisterResult registerResult = sysUserService.registermicroservicesId(sysUser.getLoginName());
        emailBusinessService.addUserSendEmail(sysUser, registerResult, String.valueOf(getCurrentUserId()));
        sysUser.setPassword(null);
        return ResultDTO.success(registerResult);
    }

    /**
     * 修改保存用户
     */
    @HasPermissions("system:anchor:edit")
    @OperLog(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("update")
    public ResultDTO<Object> editSave(@RequestBody SysUser sysUser) {
        if (null != sysUser.getUserId() && SysUser.isAdmin(sysUser.getUserId())) {
            return ResultDTO.fail(NO_UPDATE_ADMIN);
        } else if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(sysUserService.checkPhoneUnique(sysUser))) {
            return ResultDTO.fail("修改用户'" + sysUser.getLoginName() + "'失败，手机号码已存在");
        } else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(sysUserService.checkEmailUnique(sysUser))) {
            return ResultDTO.fail("修改用户'" + sysUser.getLoginName() + "'失败，邮箱账号已存在");
        }
        //不允许更新用户类型
        if (sysUser != null) {
            sysUser.setUserType(null);
        }
        return ResultDTO.success(sysUserService.updateUser(sysUser));
    }

    @HasPermissions("system:anchor:del")
    @OperLog(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("remove")
    public ResultDTO<Integer> remove(String ids) {
        Long[] userIds = Convert.toLongArray(ids);
        if (userIds[0] == getCurrentUserId()) {
            return ResultDTO.fail("Not allowed to delete own account");
        }
        SysUser sysUser = new SysUser();
        List<String> userTypes = Lists.newArrayList();
        userTypes.add(UserTypeEnum.Anchor.getCode());
        sysUser.setUserTypes(userTypes);
        List<SysUser> sysUsers = sysUserService.selectUserList(sysUser);
        filterSysUsers(sysUsers);
        if (CollectionUtils.isNotEmpty(sysUsers) && sysUsers.size() <= 1){
            return ResultDTO.fail("Delete user failed, At least one users are required");
        }
        return ResultDTO.success(sysUserService.deleteUserByIds(ids));
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
        if (roleKeys.contains("AnchorAdmin")) {
            return false;
        }
        return true;
    }

    /**
     * 用户可选下拉角色
     * @return
     */
    @GetMapping("select/roles")
    public ResultDTO<Object> selectUserRoles() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleKey(UserTypeEnum.Anchor.getName());
        List<SysRoleDO> sysRoles = sysRoleManager.selectRoleList(sysRole);
        return ResultDTO.success(sysRoles);
    }
}
