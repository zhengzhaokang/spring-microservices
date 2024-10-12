package com.microservices.otmp.system.controller;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.microservices.otmp.common.annotation.LoginUser;
import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.constant.UserConstants;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.kafka.listener.KafkaCallback;
import com.microservices.otmp.common.kafka.util.KafkaSend;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.RandomUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.notice.domain.dto.EmailSendDTO;
import com.microservices.otmp.notice.feign.RemoteEmailService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.system.common.EmailConstant;
import com.microservices.otmp.system.common.KafkaFactory;
import com.microservices.otmp.system.domain.RegisterResult;
import com.microservices.otmp.system.domain.SysRole;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.domain.SysUserDataScope;
import com.microservices.otmp.system.domain.dto.SupplierSimpleDTO;
import com.microservices.otmp.system.domain.entity.SupplierBaseInfoDto;
import com.microservices.otmp.system.domain.entity.SysUserRoleDO;
import com.microservices.otmp.system.domain.vo.UserExportRoleVO;
import com.microservices.otmp.system.domain.vo.UserExportVO;
import com.microservices.otmp.system.feign.RemoteConfigService;
import com.microservices.otmp.system.manager.SysUserRoleManager;
import com.microservices.otmp.system.mapper.FinancingMapper;
import com.microservices.otmp.system.mapper.SysMenuMapper;
import com.microservices.otmp.system.remote.RemoteFinancingService;
import com.microservices.otmp.system.service.ISysConfigService;
import com.microservices.otmp.system.service.ISysMenuService;
import com.microservices.otmp.system.service.ISysUserDataScopeService;
import com.microservices.otmp.system.service.ISysUserService;
import com.microservices.otmp.system.service.EmailBusinessService;
import com.microservices.otmp.system.util.PasswordUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户 提供者
 *
 * @Author lovefamily
 * @date 2019-05-20
 */
@RestController
@RequestMapping("user")
public class SysUserController extends BaseController {
    private static final String DEFALUT_PASSWORD_KEY = "defalut_password";
    public static final String ADD_USER = "Add account ";
    public static final String NO_UPDATE_ADMIN = "不允许修改超级管理员用户";

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private SysUserRoleManager sysUserRoleManager;

    @Autowired
    private ISysMenuService sysMenuService;

    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private ISysUserDataScopeService userDataScopeService;

    @Value("${microservices.id.switch}")
    private Boolean microservicesIdSwitch;

    @Autowired
    private RemoteEmailService remoteEmailService;

    @Autowired
    private EmailBusinessService emailBusinessService;

    @Autowired
    private RemoteFinancingService remoteFinancingService;

    @Value("${kafka.topic.send-email}")
    private String sendEmailTopic;

    @Autowired
    private KafkaSend kafkaSend;

    @Autowired
    private FinancingMapper financingMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 查询用户
     */
    @GetMapping("get/{userId}")
    public ResultDTO<SysUser> get(@PathVariable("userId") Long userId) {
        SysUser sysUser = sysUserService.selectUserById(userId);
        sysUser.setPassword(StringUtils.EMPTY);
        return ResultDTO.success(sysUser);
    }

    /**
     * 查询用户 给远程调用的使用
     */
    @GetMapping("getUserById/{userId}")
    public SysUser getUserById(@PathVariable("userId") Long userId) {
        return sysUserService.selectUserById(userId);
    }

    @GetMapping("info")
    public SysUser info(@LoginUser SysUser sysUser) {
        sysUser.setButtons(sysMenuService.selectPermsByUserId(sysUser.getUserId()));
        sysUser.setmicroservicesIdSwitch(microservicesIdSwitch);
        sysUser.setPassword(StringUtils.EMPTY);
        // chart权限
        List<String> chartPerms = sysMenuMapper.selectChartPermsByUserId(sysUser.getUserId());
        if (CollectionUtils.isNotEmpty(chartPerms)) {
            sysUser.setChartPerms(new HashSet<>(chartPerms));
        }

        if (StringUtils.isNotBlank(sysUser.getSupplierId())) {
            //设置供应商信息
            ResultDTO<SupplierSimpleDTO> resultDTO = remoteFinancingService.simple(Long.parseLong(sysUser.getSupplierId()));
            if (resultDTO != null) {
                sysUser.setSupplierSimpleDTO(resultDTO.getData());
            }
        }
        boolean isAnchorAdmin = false;
        List<SysRole> roles = sysUser.getRoles();
        if (CollectionUtils.isNotEmpty(roles)) {
            isAnchorAdmin = roles.stream().anyMatch(role -> role.getRoleKey().equals("AnchorAdmin"));
        }
        sysUser.setIsAnchorAdmin(isAnchorAdmin);
        return sysUser;
    }

    private void setUserDataScope(SysUser sysUser) {
        SysUserDataScope sysUserDataScope = new SysUserDataScope();
        sysUserDataScope.setUserId(sysUser.getUserId());
        sysUser.setSysUserDataScopeList(userDataScopeService.selectSysUserDataScopeList(sysUserDataScope));

    }

    /**
     * 校验用户登录（提供给M&S)
     *
     * @param sysUser
     * @return
     */
    @GetMapping("checkInfo")
    public ResultDTO<SysUser> loginInfo(@LoginUser SysUser sysUser) {
        setUserDataScope(sysUser);
        sysUser.setButtons(sysMenuService.selectPermsByUserId(sysUser.getUserId()));
        return ResultDTO.success(sysUser);
    }

    /**
     * 查询用户
     */
    @GetMapping("find/{username}")
    public SysUser findByUsername(@PathVariable("username") String username) {
        SysUser sysUser = sysUserService.selectUserByLoginName(username);
        if (sysUser != null) {
            sysUser.setPassword(null);
            sysUser.setSalt(null);
        }
        return sysUser;
    }


    /**
     * 查询拥有当前角色的所有用户
     */
    @GetMapping("hasRoles")
    public Set<Long> hasRoles(String roleIds) {
        Long[] arr = Convert.toLongArray(roleIds);
        return sysUserService.selectUserIdsHasRoles(arr);
    }

    /**
     * 查询所有当前部门中的用户
     */
    @GetMapping("inDepts")
    public Set<Long> inDept(String deptIds) {
        Long[] arr = Convert.toLongArray(deptIds);
        return sysUserService.selectUserIdsInDepts(arr);
    }

    /**
     * 查询用户列表(分页)
     */
    @HasPermissions("system:user:list")
    @GetMapping("list")
    public TableDataInfo list(SysUser sysUser) {
        startPage();
        List<SysUser> sysUsers = sysUserService.selectUserList(sysUser);
        TableDataInfo dataTable = getDataTable(sysUsers);
        // 填充supplier信息
        fillSupplierInfo(sysUsers);
        dataTable.setRows(sysUsers);
        return dataTable;
    }

    private void fillSupplierInfo(List<SysUser> sysUsers) {
        List<SupplierBaseInfoDto> supplierBaseInfoDtos = financingMapper.selectSupplierBaseInfo();
        if (CollectionUtils.isEmpty(supplierBaseInfoDtos)) {
            return; // 没有数据，直接返回，不需要进行数据填充。这样可以减少数据库访问次数。（如果数据量很大，可能会导致数据库访问过于频繁）；
        }
        Map<String, SupplierBaseInfoDto> supplierBaseInfoMap = supplierBaseInfoDtos.stream().
                collect(Collectors.toMap(SupplierBaseInfoDto::getId, Function.identity(), (key1, key2) -> key2));
        sysUsers.forEach(sysUser -> {
            if (StringUtils.isNotBlank(sysUser.getSupplierId()) && supplierBaseInfoMap.get(sysUser.getSupplierId()) != null) {
                sysUser.setSupplierName(supplierBaseInfoMap.get(sysUser.getSupplierId()).getSupplierName());
            }
        });
    }

    /**
     * 查询用户列表
     */
    @HasPermissions("system:user:list")
    @GetMapping("userList")
    public List<SysUser> userList(SysUser sysUser) {
        return sysUserService.selectUserList(sysUser);
    }

    /**
     * 根据用户查询相同GEO的用户列表
     *
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    @PostMapping("geoUserList")
    public List<SysUser> selectUserListByCommonGeos(@RequestBody SysUser sysUser) {
        SysUser currentSysUser = sysUserService.selectUserById(getCurrentUserId());
        setUserDataScope(currentSysUser);
        sysUser.setSysUserDataScopeList(currentSysUser.getSysUserDataScopeList());
        return sysUserService.selectUserListByCommonGeos(sysUser);
    }

    /**
     * 新增保存用户
     */
    @HasPermissions("system:user:add")
    @PostMapping("save")
    @OperLog(title = "用户管理", businessType = BusinessType.INSERT)
    public ResultDTO<Object> addSave(@RequestBody SysUser sysUser) {
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(sysUserService.checkLoginNameUnique(sysUser.getLoginName()))) {
            return ResultDTO.fail(ADD_USER + sysUser.getLoginName() + " failed, account already exists");
        } else if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(sysUserService.checkPhoneUnique(sysUser))) {
            return ResultDTO.fail(ADD_USER + sysUser.getLoginName() + " failed, phone already exists");
        } else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(sysUserService.checkEmailUnique(sysUser))) {
            return ResultDTO.fail(ADD_USER + sysUser.getLoginName() + " failed, Email account already exists");
        }
        sysUser.setSalt(RandomUtil.randomStr(6));
        sysUser.setPassword(PasswordUtil.encryptPassword(sysUser.getLoginName(),
                sysConfigService.selectConfigByKey(DEFALUT_PASSWORD_KEY), sysUser.getSalt()));
        sysUser.setCreateBy(getLoginName());
        sysUserService.insertUser(sysUser);
        RegisterResult registerResult = sysUserService.registermicroservicesId(sysUser.getLoginName());
        emailBusinessService.addUserSendEmail(sysUser, registerResult, String.valueOf(getCurrentUserId()));
        sysUser.setPassword(null);
        return ResultDTO.success(registerResult);
    }




    /**
     * 修改保存用户
     */
    @HasPermissions("system:user:edit")
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
        return ResultDTO.success(sysUserService.updateUser(sysUser));
    }

    /**
     * 修改用户信息
     *
     * @param sysUser
     * @return
     */
    @HasPermissions("system:user:edit")
    @PostMapping("update/info")
    @OperLog(title = "用户管理", businessType = BusinessType.UPDATE)
    public ResultDTO<Integer> updateInfo(@RequestBody SysUser sysUser) {
        return ResultDTO.success(sysUserService.updateUserInfo(sysUser));
    }

    @PostMapping("update/chart")
    @OperLog(title = "修改个人报表信息", businessType = BusinessType.UPDATE)
    public ResultDTO<Integer> updateChart(@RequestBody SysUser sysUser) {
        if (sysUser == null) {
            return ResultDTO.fail("参数错误");
        }
        SysUser sysUserParam = new SysUser();
        sysUserParam.setUserId(getCurrentUserId());
        sysUserParam.setChart(sysUser.getChart());
        return ResultDTO.success(sysUserService.updateUserInfo(sysUserParam));
    }

    /**
     * 限定最小修改范围
     * @param sysUser
     * @return
     */
    @PostMapping("update/personal")
    @OperLog(title = "修改个人信息", businessType = BusinessType.UPDATE)
    public ResultDTO<Integer> updatePerson(@RequestBody SysUser sysUser) {
        if (sysUser == null) {
            return ResultDTO.fail("参数错误");
        }
        SysUser sysUserParam = new SysUser();
        sysUserParam.setUserId(getCurrentUserId());
        if (StringUtils.isNotBlank(sysUser.getTheme())) {
            sysUserParam.setTheme(sysUser.getTheme());
        }
        if (StringUtils.isNotBlank(sysUser.getDateStyle())) {
            sysUserParam.setDateStyle(sysUser.getDateStyle());
        }
        if (StringUtils.isNotBlank(sysUser.getCurrencyStyle())) {
            sysUserParam.setCurrencyStyle(sysUser.getCurrencyStyle());
        }
        if (StringUtils.isNotBlank(sysUser.getLang())) {
            sysUserParam.setLang(sysUser.getLang());
        }
        if (StringUtils.isNotBlank(sysUser.getUserName())) {
            sysUserParam.setUserName(sysUser.getUserName());
        }
        if (StringUtils.isNotBlank(sysUser.getSupplierId())) {
            sysUserParam.setSupplierId(sysUser.getSupplierId());
        }

        return ResultDTO.success(sysUserService.updateUserInfo(sysUserParam));
    }

    /**
     * 记录登陆信息
     *
     * @param sysUser
     * @return
     */
    @PostMapping("update/login")
    public ResultDTO<Integer> updateLoginRecord(@RequestBody SysUser sysUser) {
        return ResultDTO.success(sysUserService.updateUserLoginInfo(sysUser));
    }

    @HasPermissions("system:user:resetPwd")
    @OperLog(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    public ResultDTO<Integer> resetPwdSave(@RequestBody SysUser user) {
        if (null != user.getUserId() && SysUser.isAdmin(user.getUserId())) {
            return ResultDTO.fail(NO_UPDATE_ADMIN);
        }
        user.setSalt(RandomUtil.randomStr(6));
        user.setPassword(PasswordUtil.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        return ResultDTO.success(sysUserService.resetUserPwd(user));
    }

    @HasPermissions("system:user:resetPwd")
    @OperLog(title = "修改密码", businessType = BusinessType.UPDATE)
    @PostMapping("/updatePwd")
    public ResultDTO<Integer> updatePwdSave(@RequestBody SysUser user) {
        Long userId = getCurrentUserId();
        user.setUserId(userId);
        user.setSalt(RandomUtil.randomStr(6));
        user.setPassword(PasswordUtil.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        return ResultDTO.success(sysUserService.resetUserPwd(user));
    }

    /**
     * 修改状态
     *
     * @param user
     * @return
     */
    @HasPermissions("system:user:edit")
    @PostMapping("status")
    @OperLog(title = "用户管理", businessType = BusinessType.UPDATE)
    public ResultDTO<Integer> status(@RequestBody SysUser user) {
        if (null != user.getUserId() && SysUser.isAdmin(user.getUserId())) {
            return ResultDTO.fail(NO_UPDATE_ADMIN);
        }
        if (user.getUserId() == getCurrentUserId()) {
            return ResultDTO.fail("Not allowed to modify own status");
        }
        return ResultDTO.success(sysUserService.changeStatus(user));
    }

    /**
     * 删除用户
     */
    @HasPermissions("system:user:remove")
    @OperLog(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("remove")
    public ResultDTO<Integer> remove(String ids) {
        return ResultDTO.success(sysUserService.deleteUserByIds(ids));
    }

    @GetMapping("getUserRoleByUserId")
    public TableDataInfo getUserRoleByUserId(@RequestParam(value = "userId") Integer userId) {
        startPage();
        return getDataTable(sysUserService.selectUserRoleList(userId));
    }

    /**
     * 导出pool列表
     */
    @RequiresPermissions("system:user:export")
    @OperLog(title = "用户管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @SuppressWarnings("java:S3740")
    public void export(HttpServletResponse response, SysUser sysUser) {
        List<UserExportVO> list = sysUserService.export(sysUser);
        if (CollectionUtils.isEmpty(list)) {
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.POOL_EXPORT_NO_DATA, ""), DefaultErrorMessage.POOL_EXPORT_NO_DATA.intValue());
        }
        List<Long> userIds = list.stream().map(UserExportVO::getUserId).collect(Collectors.toList());
        List<SysUserRoleDO> sysUserRoles = sysUserRoleManager.selectUserRoleListByUserIds(userIds);
        List<Long> roleIds = sysUserRoles.stream().map(SysUserRoleDO::getRoleId).distinct().collect(Collectors.toList());
        List<UserExportRoleVO> roleList = sysUserService.exportMenu(roleIds);
        if (CollectionUtils.isEmpty(roleList)) {
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.POOL_EXPORT_NO_DATA, ""), DefaultErrorMessage.POOL_EXPORT_NO_DATA.intValue());
        }
        NewExcelUtil util = new NewExcelUtil();
        util.writeSheet(list, "User Info", null, UserExportVO.class, false);
        util.writeSheet(roleList, "Role Access", null, UserExportRoleVO.class, false);
        util.exportExcel2Response(response);
    }

    @Autowired
    private RemoteConfigService remoteConfigService;

    @HasPermissions("system:user:edit")
    @PostMapping("update/password")
    @OperLog(title = "统一更新密码", businessType = BusinessType.UPDATE)
    public ResultDTO<Object> updatePassword(@RequestBody SysUser sysUser) {
        List<SysUser> users = sysUserService.selectUserList(sysUser);
        String defalut = "123456";
        String amdinName = remoteConfigService.getConfigKey("ms.admin.name");
        String amdinPassword = remoteConfigService.getConfigKey("ms.admin.password");
        for (SysUser user : users) {
            if (user.getLoginName().equals("admin")) {
                defalut = "admin123";
            }
            if (user.getLoginName().equals(amdinName)) {
                defalut = amdinPassword;
            }
            user.setPassword(PasswordUtil.encryptPassword(user.getLoginName(), defalut, user.getSalt()));
            sysUserService.updateUser(user);
        }
        return ResultDTO.success();
    }

    @PostMapping("test")
    public ResultDTO<Object> test() {
        SysUser sysUser = new SysUser();
        sysUser.setLoginName("zhengzk2@microservices.com");
        RegisterResult registerResult = new RegisterResult();
        registerResult.setInitUrl("https://uss-test.microservicesmm.cn/webauthn/credential/init?init_code=%2FvwO1R%2Fj%2B3NMhkYhRUj01Q%3D%3D&init_username=jVoDl0VW7yMZMzmtxwuE5WGJQfInDBrgxToJUwANHtE%3D&webauthn_action=uilogin&webauthn_realm=lscfp.microservices.com&webauthn_callback=otfp-ui-tst.appz1p-fraex.earth.xcloud.microservices.com/api/system/callback/microservicesid&webauthn_state=Y&webauthn_lang=en_US&account_type=new");
        EmailSendDTO emailSendDTO = emailBusinessService.getEmailSendDTO(sysUser, registerResult,
                EmailConstant.EMAIL_TYPE_USER_NEW, String.valueOf(getCurrentUserId()));
        remoteEmailService.asynSendEmail(emailSendDTO);
        return ResultDTO.success(emailSendDTO);
    }

    @PostMapping("suppliers")
    public ResultDTO<Object> selectSupplierBaseInfo() {
        long currentUserId = getCurrentUserId();
        SysUser curSysUser = sysUserService.selectUserById(currentUserId);

        return ResultDTO.success(financingMapper.selectSupplierBaseInfo());
    }
}
