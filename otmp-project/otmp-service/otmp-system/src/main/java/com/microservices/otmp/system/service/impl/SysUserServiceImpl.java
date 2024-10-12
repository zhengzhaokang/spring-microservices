package com.microservices.otmp.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.microservices.otmp.common.annotation.DataScope;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.constant.UserConstants;
import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.common.exception.BusinessException;
import com.microservices.otmp.common.redis.annotation.DistributedLock;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.RandomUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.common.utils.security.ShaUtils;
import com.microservices.otmp.service.impl.ValidateImportDetails;
import com.microservices.otmp.system.common.Base64Utils;
import com.microservices.otmp.system.common.DataScopeKeyConstants;
import com.microservices.otmp.system.common.GetLoginUserUtil;
import com.microservices.otmp.system.common.ValidateUserImport;
import com.microservices.otmp.system.domain.*;
import com.microservices.otmp.system.domain.entity.*;
import com.microservices.otmp.system.domain.vo.UserExportRoleVO;
import com.microservices.otmp.system.domain.vo.UserExportVO;
import com.microservices.otmp.system.domain.vo.UserImportVo;
import com.microservices.otmp.system.manager.*;
import com.microservices.otmp.system.service.ISysConfigService;
import com.microservices.otmp.system.service.ISysUserService;
import com.microservices.otmp.system.service.SysmicroservicesIdService;
import com.microservices.otmp.system.util.PasswordUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户 业务层处理
 *
 * @author lovefamily
 */
@Service
public class SysUserServiceImpl implements ISysUserService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);
    public static final String REMOVE = "Remove";
    public static final String ADD = "Add";
    public static final String GEO_CODE = "geo_code";

    @Autowired
    private SysUserManager sysUserManager;

    @Autowired
    private SysRoleManager sysRoleManager;

    @Autowired
    private SysPostManager sysPostManager;

    @Autowired
    private SysUserPostManager sysUserPostManager;

    @Autowired
    private SysUserRoleManager sysUserRoleManager;

    @Autowired
    private SysUserDataScopeManager sysUserDataScopeManager;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    GetLoginUserUtil getLoginUserUtil;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    ISysUserService sysUserService;

    @Autowired
    ValidateImportDetails validateImportDetails;


    @Autowired
    ValidateUserImport customerValidate;

    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private SysmicroservicesIdService sysmicroservicesIdService;


    private static final String DEFALUT_PASSWORD_KEY = "defalut_password";

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<SysUser> selectUserList(SysUser user) {
        List<SysUser> sysUserList = sysUserManager.selectUserList(user);
        sysUserList.stream().peek(item -> {
            item.setPassword(null);
            item.setSalt(null);
        }).collect(Collectors.toList());
        return sysUserList;
    }

    /**
     * 根据用户查询相同GEO的用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<SysUser> selectUserListByCommonGeos(SysUser user) {
        Map<String, Object> queryParams = new HashMap<>();
        List<SysUserDataScope> sysUserDataScopeList = user.getSysUserDataScopeList();
        Set<String> geoCodes;
        if (CollectionUtils.isNotEmpty(sysUserDataScopeList)) {
            geoCodes = sysUserDataScopeList.stream().filter(item -> GEO_CODE.equals(item.getDataScopeKey()) && "1".equals(item.getSelectAll())).map(SysUserDataScope::getDataScopeValue).collect(Collectors.toSet());
            queryParams.put("geoCodes", geoCodes);
        }
        queryParams.put("userItCode", user.getLoginName());
        return sysUserManager.selectUserListByCommonGeos(queryParams);
    }


    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectAllocatedList(SysUser user) {
        List<SysUser> sysUserList = sysUserManager.selectAllocatedList(user);
        sysUserList.stream().peek(item -> {
            item.setPassword(null);
            item.setSalt(null);
        }).collect(Collectors.toList());
        return sysUserList;
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user) {
        List<SysUser> sysUserList = sysUserManager.selectUnallocatedList(user);
        sysUserList.stream().peek(item -> {
            item.setPassword(null);
            item.setSalt(null);
        }).collect(Collectors.toList());
        return sysUserList;
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    @Cacheable(value = "user_details", key = "#userName")
    public SysUser selectUserByLoginName(String userName) {

        SysUser sysUser = sysUserManager.selectUserByLoginName(userName);

        if (sysUser != null) {
            sysUser.setPassword(null);
            sysUser.setSalt(null);
            buildSysUserDataScopeInfo(sysUser);
        }
        return sysUser;
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByLoginNameToAdfs(String userName) {
        SysUser sysUser = sysUserManager.selectUserByLoginName(userName);
        if (sysUser != null) {
            sysUser.setPassword(null);
            sysUser.setSalt(null);
        }
        return sysUser;
    }

    /**
     * 构建用户自定义数据范围信息
     *
     * @param sysUser 登录的用户对象
     */
    private void buildSysUserDataScopeInfo(SysUser sysUser) {
        SysUserDataScope sysUserDataScope = new SysUserDataScope();
        sysUserDataScope.setUserId(sysUser.getUserId());
        sysUserDataScope.setUserItcode(sysUser.getLoginName());
        List<SysUserDataScopeDO> sysUserDataScopeList = sysUserDataScopeManager.selectSysUserDataScopeList(sysUserDataScope);
        List<SysUserDataScope> sysUserDataScopes = new ArrayList<>(sysUserDataScopeList.size());
        BeanUtils.copyListProperties(sysUserDataScopeList, sysUserDataScopes, SysUserDataScope.class);
        sysUser.setSysUserDataScopeList(sysUserDataScopes);
        StringBuilder sb = new StringBuilder();
        Map<String, List<SysUserDataScope>> sysUserDataScopeGroupMap = sysUserDataScopes.stream().collect(Collectors.groupingBy(SysUserDataScope::getDataScopeKey));
        Set<Map.Entry<String, List<SysUserDataScope>>> sysUserDataScopeEntries = sysUserDataScopeGroupMap.entrySet();
        sysUserDataScopeEntries.stream().forEach(entry -> {
            sb.append(" and ");
            sb.append(entry.getKey());
            sb.append(" in (");
            String tempValue = entry.getValue().stream().map(SysUserDataScope::getDataScopeValue).collect(Collectors.joining(","));
            sb.append(strToDbin(tempValue));
            sb.append(") ");
        });
        sysUser.setSysUserDataScopeCondition(sb.toString());
    }

    private String strToDbin(String str) {

        return String.format("'%s'", org.apache.commons.lang3.StringUtils.join(str.split(","), "','"));

    }

    /**
     * 通过手机号码查询用户
     *
     * @param phoneNumber 手机号码
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByPhoneNumber(String phoneNumber) {
        SysUser sysUser = sysUserManager.selectUserByPhoneNumber(phoneNumber);
        if (sysUser != null) {
            sysUser.setPassword(null);
            sysUser.setSalt(null);
        }
        return sysUser;
    }

    /**
     * 通过邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByEmail(String email) {

        SysUser sysUser = sysUserManager.selectUserByEmail(email);
        if (sysUser != null) {
            sysUser.setPassword(null);
            sysUser.setSalt(null);
        }
        return sysUser;
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return getUserByCache(userId);
    }

    private SysUser getUserByCache(Long userId) {
        if (null == userId) {
            return null;
        }
        SysUser sysUser = redisUtils.get(RedisConstants.USER + userId, SysUser.class);
        if (null == sysUser) {
            sysUser = sysUserManager.selectUserById(userId);
            if (null != sysUser) {
                redisUtils.set(RedisConstants.USER + userId, sysUser);
            }
        }
        return sysUser;
    }

    private void removeUserCache(Long userId) {
        if (null == userId) {
            return;
        }
        if (redisUtils.isContains(RedisConstants.USER + userId)) {
            redisUtils.delete(RedisConstants.USER + userId);
        }
        SysUser sysUser = sysUserManager.selectUserById(userId);
        if (null != sysUser) {
            String loginName = sysUser.getLoginName();
            redisUtils.delete(RedisConstants.USER_DETAIL + loginName);
        }
    }

    private void removeUserCacheByIds(Long[] userIds) {
        if (null != userIds && userIds.length > 0) {
            for (Long userId : userIds) {
                removeUserCache(userId);
            }
        }
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long userId) {
        deleteUserIncidenceRelation(userId);
        removeUserCache(userId);
        return sysUserManager.deleteUserById(userId);
    }

    public void deleteUserIncidenceRelation(Long userId) {
        // 删除用户与角色关联
        sysUserRoleManager.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        sysUserPostManager.deleteUserPostByUserId(userId);
        // 删除用户权限
        sysUserDataScopeManager.deleteSysUserDataScopeByUserId(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @DistributedLock
    public int deleteUserByIds(String ids) {
        Long[] userIds = Convert.toLongArray(ids);
        for (Long userId : userIds) {
            if (SysUser.isAdmin(userId)) {
                throw new BusinessException("不允许删除超级管理员用户");
            }
        }
        removeUserCacheByIds(userIds);
        return deleteUser(userIds);
    }

    private int deleteUser(Long[] userIds) {
        for (Long id : userIds) {
            //删除用户的关联关系
            deleteUserIncidenceRelation(id);
        }
        return sysUserManager.deleteUserByIds(userIds);
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user) {
        setGeoCode(user);
        // 新增用户信息
        SysUserDO sysUserDO = new SysUserDO();
        org.springframework.beans.BeanUtils.copyProperties(user, sysUserDO);
        int rows = sysUserManager.insertUser(sysUserDO);
        user.setUserId(sysUserDO.getUserId());
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        //新增用户数据权限关联
        updateUserDataScope(user);
        return rows;
    }

    private void setGeoCode(SysUser sysUser) {
        if (null == sysUser || CollUtil.isEmpty(sysUser.getSysUserDataScopeList())) {
            return;
        }
        List<SysUserDataScope> sysUserDataScopes = sysUser.getSysUserDataScopeList();
        for (SysUserDataScope sysUserDataScope : sysUserDataScopes) {
            if (sysUserDataScope.getDataScopeKey().equals(GEO_CODE)) {
                sysUser.setGeoCode(sysUserDataScope.getDataScopeValue());
            }
        }
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user) {
        setGeoCode(user);
        Long userId = user.getUserId();
        //更新角色
        updateUserRole(user);
        //更新数据权限
        updateUserDataScope(user);
        // 删除用户与岗位关联
        sysUserPostManager.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        SysUserDO sysUserDO = new SysUserDO();
        org.springframework.beans.BeanUtils.copyProperties(user, sysUserDO);
        removeUserCache(userId);
        return sysUserManager.updateUser(sysUserDO);
    }

    @Override
    @Transactional
    public int updateUserLoginInfo(SysUser sysUser) {
        SysUserDO sysUserDO = new SysUserDO();
        org.springframework.beans.BeanUtils.copyProperties(sysUser, sysUserDO);
        return sysUserManager.updateUser(sysUserDO);
    }

    @Override
    public List<UserExportVO> export(SysUser sysUser) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(sysUser.getIds())) {
            List<String> idsList = Arrays.stream(sysUser.getIds().split(",")).collect(Collectors.toList());
            List<Long> list = idsList.stream().map(Long::parseLong).collect(Collectors.toList());
            sysUser.setIdsList(list);
        }
        return sysUserManager.export(sysUser);
    }

    @Override
    public List<UserExportRoleVO> exportMenu(List<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Lists.newArrayList();
        }
        return sysUserManager.exportMenu(roleIds);
    }

    @Override
    public SysUser buildUserForAttributes(Map<String, List<String>> attributes, SysUser user, boolean isupdatefordb) {
        try {
            Field[] declaredFields = user.getClass().getDeclaredFields();
            for (Map.Entry<String, List<String>> entry : attributes.entrySet()) {
                Field[] array = declaredFields;
                int length = array.length;
                int i = 0;
                while (i < length) {
                    Field field = array[i];
                    String fieldStr = field.getName();
                    if (field.getName().equalsIgnoreCase(entry.getKey())) {
                        String type = field.getGenericType().toString();
                        if (type.equals("class java.lang.String")) {
                            Method m = user.getClass().getMethod("set" + fieldStr.substring(0, 1).toUpperCase() + fieldStr.substring(1), String.class);
                            List<String> values = entry.getValue();
                            m.invoke(user, values.get(0));
                            break;
                        }
                        break;
                    } else {
                        ++i;
                    }
                }
            }
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (isupdatefordb) {
            return this.sysUserManager.selectUserByLoginName(user.getItcode());
        }
        return user;

    }


    @Override
    public int checkUserEntityByParam(Map<String, List<String>> attributes) {
        SysUser user = new SysUser();
        try {
            SysUser dbUser = this.buildUserForAttributes(attributes, user, true);
            if (dbUser != null) {
                //这个地方是不是应该把user赋给dbuser  因为根据user的itcode能查到----这句直接不要了
                org.springframework.beans.BeanUtils.copyProperties(dbUser, user);
                SysUserDO sysUserDO = new SysUserDO();
                org.springframework.beans.BeanUtils.copyProperties(dbUser, sysUserDO);

                this.sysUserManager.updateUser(sysUserDO);
            } else {
//                user.setUsername(user.getItcode());
//                 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//                 String rawPassword = "123456";
//                user.setPassword(encoder.encode((CharSequence)rawPassword));
//                user.setStatus(1);
//                user.setId(UUID.randomUUID().toString());
//                this.userRepository.save(user);
                user.setStatus(2 + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(user.getStatus());
    }

    @Override
    public RegisterResult registermicroservicesId(String username) {
        RegisterForm registerForm = new RegisterForm();
        registerForm.setUsername(username);
        RegisterResult registerResult = sysmicroservicesIdService.register(registerForm);
        return registerResult;
    }


    /**
     * * 保存user
     *
     * @param list
     */
    public void saveImportUser(List<UserImportVo> list, String loginName) {
        for (UserImportVo user : list) {
            //新增用户
            if (user.getSysUser() == null) {
                SysUser sysUser = buildInsertUser(user, loginName);
                this.insertUser(sysUser);
            } else {
                //新增|删除角色绑定 新增|删除 数据权限
                editImportUser(user, loginName);
            }
        }
    }

    /**
     * * 视为编辑
     *
     * @param userImportVo
     */
    private void editImportUser(UserImportVo userImportVo, String loginName) {
        String action = userImportVo.getAction();
        SysUser sysUser = userImportVo.getSysUser();
        if (REMOVE.equals(action)) {
            removeUserRole(userImportVo);
            removeDataScope(userImportVo);
        }
        if (ADD.equals(action)) {
            addUserRole(userImportVo, loginName);
            addUserDataScope(userImportVo);
        }
        //刷新缓存
        removeUserCache(sysUser.getUserId());
    }

    private void addUserRole(UserImportVo userImportVo, String loginName) {
        if (CollUtil.isNotEmpty(userImportVo.getRoleMaps())) {
            SysUser sysUser = userImportVo.getSysUser();
            sysUser.setRoleIds(new ArrayList<>(userImportVo.getRoleMaps().keySet()));
            insertUserRole(sysUser, loginName);
        }
    }

    private void addUserDataScope(UserImportVo userImportVo) {
        SysUser sysUser = userImportVo.getSysUser();
        sysUser.setSysUserDataScopeList(getScopes(userImportVo));
        insertUserDataScope(sysUser);
    }

    private void removeUserRole(UserImportVo userImportVo) {
        SysUser sysUser = userImportVo.getSysUser();
        if (CollUtil.isNotEmpty(userImportVo.getRoleMaps()) && sysUser != null) {
            for (Long roleId : userImportVo.getRoleMaps().keySet()) {
                for (SysUserRole sysUserRole : userImportVo.getSysUserRoleList()) {
                    if (roleId.equals(sysUserRole.getRoleId()) && sysUser.getUserId().equals(sysUserRole.getUserId())) {
                        SysUserRoleDO sysUserRoleDO = new SysUserRoleDO();
                        sysUserRoleDO.setUserId(sysUser.getUserId());
                        sysUserRoleDO.setRoleId(roleId);
                        sysUserRoleManager.deleteUserRoleInfo(sysUserRoleDO);
                    }
                }
            }
        }
    }

    private void removeDataScope(UserImportVo userImportVo) {
        List<SysUserDataScopeDO> dataScopeDOS = userImportVo.getSysUserDataScopeList();
        Set<Long> removeIds = new HashSet<>();
        String businessGroup = userImportVo.getBusinessGroup();
        if (StrUtil.isNotBlank(businessGroup)) {
            addDataScopeId(businessGroup, dataScopeDOS, DataScopeKeyConstants.business_group, removeIds, userImportVo.getSysUser());
        }

        String geoCode = userImportVo.getGeoCode();
        if (StrUtil.isNotBlank(geoCode)) {
            addDataScopeId(geoCode, dataScopeDOS, DataScopeKeyConstants.geo, removeIds, userImportVo.getSysUser());
        }

        String regionMarketCode = userImportVo.getRegionMarketCode();
        if (StrUtil.isNotBlank(regionMarketCode)) {
            addDataScopeId(regionMarketCode, dataScopeDOS, DataScopeKeyConstants.region_market_code, removeIds, userImportVo.getSysUser());
        }

        String salesOrgCode = userImportVo.getSalesOrgCode();
        if (StrUtil.isNotBlank(salesOrgCode)) {
            addDataScopeId(salesOrgCode, dataScopeDOS, DataScopeKeyConstants.sales_org_code, removeIds, userImportVo.getSysUser());
        }

        String salesOfficeCode = userImportVo.getSalesOfficeCode();
        if (StrUtil.isNotBlank(salesOfficeCode)) {
            addDataScopeId(salesOfficeCode, dataScopeDOS, DataScopeKeyConstants.sales_office_code, removeIds, userImportVo.getSysUser());
        }

        String segmentCode = userImportVo.getSegmentCode();
        if (StrUtil.isNotBlank(segmentCode)) {
            addDataScopeId(segmentCode, dataScopeDOS, DataScopeKeyConstants.segment_code, removeIds, userImportVo.getSysUser());
        }

        String gtnTypeCode = userImportVo.getGtnTypeCode();
        if (StrUtil.isNotBlank(gtnTypeCode)) {
            addDataScopeId(gtnTypeCode, dataScopeDOS, DataScopeKeyConstants.gtn_type_code, removeIds, userImportVo.getSysUser());
        }
        if (CollUtil.isNotEmpty(removeIds)) {
            sysUserDataScopeManager.deleteSysUserDataScopeByUserDataScopeIds(removeIds.toArray(new Long[removeIds.size()]));
        }
    }

    private void addDataScopeId(String data, List<SysUserDataScopeDO> dataScopeDOS, String key, Set<Long> ids, SysUser sysUser) {
        String[] values = data.split(",");
        for (String str : values) {
            for (SysUserDataScopeDO scopeDO : dataScopeDOS) {
                if (str.equals(scopeDO.getDataScopeValue()) && key.equals(scopeDO.getDataScopeKey()) && sysUser.getUserId().equals(scopeDO.getUserId())) {
                    ids.add(scopeDO.getUserDataScopeId());
                }
            }
        }
    }

    /**
     * *
     *
     * @param user
     * @return
     */
    private SysUser buildInsertUser(UserImportVo user, String loginName) {
        SysUser sysUser = new SysUser();
        sysUser.setCreateBy(loginName);
        sysUser.setLoginName(user.getItCode());
        sysUser.setUserName(user.getUserName());
        sysUser.setSalt(RandomUtil.randomStr(6));
        sysUser.setPassword(PasswordUtil.encryptPassword(sysUser.getLoginName(),
                sysConfigService.selectConfigByKey(DEFALUT_PASSWORD_KEY), sysUser.getSalt()));
        if (CollUtil.isNotEmpty(user.getRoleMaps())) {
            sysUser.setRoleIds(new ArrayList<>(user.getRoleMaps().keySet()));
        }
        if ("Active".equals(user.getStatus())) {
            sysUser.setStatus("0");
        }
        if ("Inactive".equals(user.getStatus())) {
            sysUser.setStatus("1");
        }
        sysUser.setSysUserDataScopeList(getScopes(user));
        return sysUser;
    }

    private List<SysUserDataScope> getScopes(UserImportVo user) {
        List<SysUserDataScope> scopes = new ArrayList<>();
        SysUserDataScope bg = new SysUserDataScope();
        bg.setDataScopeKey(DataScopeKeyConstants.business_group);
        bg.setDataScopeValue(user.getBusinessGroup());
        scopes.add(bg);

        SysUserDataScope geo = new SysUserDataScope();
        geo.setDataScopeKey(DataScopeKeyConstants.geo);
        geo.setDataScopeValue(user.getGeoCode());
        scopes.add(geo);

        SysUserDataScope salesOrg = new SysUserDataScope();
        salesOrg.setDataScopeKey(DataScopeKeyConstants.sales_org_code);
        salesOrg.setDataScopeValue(user.getSalesOrgCode());
        scopes.add(salesOrg);

        SysUserDataScope salesOffice = new SysUserDataScope();
        salesOffice.setDataScopeKey(DataScopeKeyConstants.sales_office_code);
        salesOffice.setDataScopeValue(user.getSalesOfficeCode());
        scopes.add(salesOffice);

        SysUserDataScope regionMarket = new SysUserDataScope();
        regionMarket.setDataScopeKey(DataScopeKeyConstants.region_market_code);
        regionMarket.setDataScopeValue(user.getRegionMarketCode());
        scopes.add(regionMarket);

        SysUserDataScope gtnType = new SysUserDataScope();
        gtnType.setDataScopeKey(DataScopeKeyConstants.gtn_type_code);
        gtnType.setDataScopeValue(user.getGtnTypeCode());
        scopes.add(gtnType);

        SysUserDataScope segment = new SysUserDataScope();
        segment.setDataScopeKey(DataScopeKeyConstants.segment_code);
        segment.setDataScopeValue(user.getSegmentCode());
        scopes.add(segment);

        return scopes;
    }


    private void updateUserRole(SysUser user) {
        Long userId = user.getUserId();
        if (null != userId) {
            // 删除用户与角色关联
            sysUserRoleManager.deleteUserRoleByUserId(userId);
            // 新增用户与角色管理
            insertUserRole(user);
        }
    }

    private void updateUserDataScope(SysUser user) {
        Long userId = user.getUserId();
        if (null != userId) {
            sysUserDataScopeManager.deleteSysUserDataScopeByUserId(userId);
            insertUserDataScope(user);
        }

    }

    private void insertUserDataScope(SysUser user) {
        List<SysUserDataScope> sysUserDataScopes = user.getSysUserDataScopeList();
        if (null != sysUserDataScopes && !sysUserDataScopes.isEmpty()) {
            for (SysUserDataScope sysUserDataScope : sysUserDataScopes) {
                sysUserDataScope.setUserId(user.getUserId());
                sysUserDataScope.setUserItcode(user.getLoginName());
                if (null == sysUserDataScope.getSelectAll()) {
                    sysUserDataScope.setSelectAll("1");
                }
                insertUserDataScope(sysUserDataScope);
            }
        }
    }

    public void insertUserDataScope(SysUserDataScope sysUserDataScope) {
        String dataScopeValue = sysUserDataScope.getDataScopeValue();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(dataScopeValue)) {
            String[] values = dataScopeValue.split(",");
            for (String val : values) {
                SysUserDataScopeDO dataScopeDO = new SysUserDataScopeDO();
                org.springframework.beans.BeanUtils.copyProperties(sysUserDataScope, dataScopeDO);
                dataScopeDO.setDataScopeValue(val);
                dataScopeDO.setCreateTime(DateUtils.getNowDate());
                dataScopeDO.setCreateBy(getLoginUserUtil.getLoginUserName());
                sysUserDataScopeManager.insertSysUserDataScope(dataScopeDO);
            }
        } else if (StrUtil.isBlank(dataScopeValue)) {
            SysUserDataScopeDO dataScopeDO = new SysUserDataScopeDO();
            org.springframework.beans.BeanUtils.copyProperties(sysUserDataScope, dataScopeDO);
            dataScopeDO.setCreateTime(DateUtils.getNowDate());
            dataScopeDO.setCreateBy(getLoginUserUtil.getLoginUserName());
            sysUserDataScopeManager.insertSysUserDataScope(dataScopeDO);
        }
    }

    /**
     * 修改用户个人详细信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserInfo(SysUser user) {
        //清除缓存
        removeUserCache(user.getUserId());
        SysUserDO sysUserDO = new SysUserDO();
        org.springframework.beans.BeanUtils.copyProperties(user, sysUserDO);
        return sysUserManager.updateUser(sysUserDO);
    }

    /**
     * 修改用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetUserPwd(SysUser user) {
        return updateUserInfo(user);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user) {
        insertUserRole(user, null);
    }

    public void insertUserRole(SysUser user, String loginName) {
        List<Long> roles = user.getRoleIds();
        String loginUserName = loginName == null ? getLoginUserUtil.getLoginUserName() : loginName;
        if (StringUtils.isNotNull(roles)) {

            // 新增用户与角色管理
            List<SysUserRoleDO> list = new ArrayList<>();
            for (Long roleId : roles) {
                SysUserRoleDO ur = new SysUserRoleDO();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                ur.setCreateBy(loginUserName);
                ur.setCreateTime(DateUtils.getNowDate());
                list.add(ur);
            }
            if (!list.isEmpty()) {
                sysUserRoleManager.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user) {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts)) {
            // 新增用户与岗位管理
            List<SysUserPostDO> list = new ArrayList<>();
            for (Long postId : posts) {
                SysUserPostDO up = new SysUserPostDO();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (!list.isEmpty()) {
                sysUserPostManager.batchUserPost(list);
            }
        }
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param loginName 用户名
     * @return
     */
    @Override
    public String checkLoginNameUnique(String loginName) {
        int count = sysUserManager.checkLoginNameUnique(loginName);
        if (count > 0) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = sysUserManager.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = sysUserManager.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    /**
     * 查询用户所属角色组
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(Long userId) {
        List<SysRoleDO> list = sysRoleManager.selectRolesByUserId(userId);
        StringBuilder idsStr = new StringBuilder();
        for (SysRoleDO role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(Long userId) {
        List<SysPostDO> list = sysPostManager.selectPostsByUserId(userId);
        StringBuilder idsStr = new StringBuilder();
        for (SysPostDO post : list) {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
        final String BR_STR = "<br/>";
        if (StringUtils.isNull(userList) || userList.isEmpty()) {
            throw new BusinessException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList) {
            try {
                // 验证是否存在这个用户
                SysUser u = sysUserManager.selectUserByLoginName(user.getLoginName());
                if (StringUtils.isNull(u)) {
                    user.setPassword(ShaUtils.encodeSHA256(user.getLoginName() + password));
                    user.setCreateBy(operName);
                    sysUserService.insertUser(user);
                    successNum++;
                    successMsg.append(BR_STR + successNum + "、账号 " + user.getLoginName() + " 导入成功");
                } else if (isUpdateSupport.booleanValue()) {
                    user.setUpdateBy(operName);
                    sysUserService.updateUser(user);
                    successNum++;
                    successMsg.append(BR_STR + successNum + "、账号 " + user.getLoginName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append(BR_STR + failureNum + "、账号 " + user.getLoginName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = BR_STR + failureNum + "、账号 " + user.getLoginName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 用户状态修改
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @DistributedLock
    public int changeStatus(SysUser user) {
        if (SysUser.isAdmin(user.getUserId())) {
            throw new BusinessException("不允许修改超级管理员用户");
        }
        removeUserCache(user.getUserId());
        SysUserDO sysUserDO = new SysUserDO();
        org.springframework.beans.BeanUtils.copyProperties(user, sysUserDO);
        return sysUserManager.updateUser(sysUserDO);
    }

    /* (non-Javadoc)
     * @see com.microservices.otmp.system.service.ISysUserService#selectUserHasRole(java.lang.Long)
     */
    @Override
    public Set<Long> selectUserIdsHasRoles(Long[] roleIds) {
        return ArrayUtil.isNotEmpty(roleIds) ? sysUserManager.selectUserIdsHasRoles(roleIds) : null;
    }

    /* (non-Javadoc)
     * @see com.microservices.otmp.system.service.ISysUserService#selectUserInDept(java.lang.Long)
     */
    @Override
    public Set<Long> selectUserIdsInDepts(Long[] deptIds) {
        return ArrayUtil.isNotEmpty(deptIds) ? sysUserManager.selectUserIdsInDepts(deptIds) : null;
    }

    @Override
    public List<SysUserRole> selectUserRoleList(Integer userId) {
        List<SysUserRole> list = sysUserRoleManager.selectUserRoleList(userId);
        if (null != list && !list.isEmpty()) {
            for (SysUserRole userRole : list) {
                Long roleId = userRole.getRoleId();
                SysRoleDO sysRole = sysRoleManager.selectRoleById(roleId);
                if (null != sysRole) {
                    userRole.setRoleKey(sysRole.getRoleKey());
                    userRole.setRoleName(sysRole.getRoleName());
                }
            }
        }
        return list;

    }

    private SysUser selectUserAllInfoByLoginName(String userName) {
        SysUser sysUser = sysUserManager.selectUserByLoginName(userName);
        return sysUser;
    }

     @Override
    public boolean checkPwd(SysUser user) {
        String username = user.getLoginName();
//        String password = user.getPassword();
        String password = Base64Utils.getCleartextPassword(user.getPassword());


        // 查询用户信息
        SysUser oldUser = selectUserAllInfoByLoginName(username);
        if (!PasswordUtil.matches(oldUser, password)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
