package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.domain.entity.SysUserDO;
import com.microservices.otmp.system.domain.vo.UserExportRoleVO;
import com.microservices.otmp.system.domain.vo.UserExportVO;
import com.microservices.otmp.system.manager.SysUserManager;
import com.microservices.otmp.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author guowb1
 * @date 2022/6/10 17:23
 */

@Service
public class SysUserManagerImpl implements SysUserManager {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> selectUserList(SysUser sysUser) {
        return sysUserMapper.selectUserList(sysUser);
    }

    @Override
    public List<SysUser> selectUserListByCommonGeos(Map<String, Object> queryParams) {
        return sysUserMapper.selectUserListByCommonGeos(queryParams);
    }

    @Override
    public List<SysUser> selectAllocatedList(SysUser user) {
        return sysUserMapper.selectAllocatedList(user);
    }

    @Override
    public List<SysUser> selectUnallocatedList(SysUser user) {
        return sysUserMapper.selectUnallocatedList(user);
    }

    @Override
    public SysUser selectUserByLoginName(String userName) {
        return sysUserMapper.selectUserByLoginName(userName);
    }

    @Override
    public SysUser selectUserByPhoneNumber(String phoneNumber) {
        return sysUserMapper.selectUserByPhoneNumber(phoneNumber);
    }

    @Override
    public SysUser selectUserByEmail(String email) {
        return sysUserMapper.selectUserByEmail(email);
    }

    @Override
    public SysUser selectUserById(Long userId) {
        return sysUserMapper.selectUserById(userId);
    }

    @Override
    public int deleteUserById(Long userId) {
        return sysUserMapper.deleteUserById(userId);
    }

    @Override
    public int deleteUserByIds(Long[] ids) {
        return sysUserMapper.deleteUserByIds(ids);
    }

    @Override
    public int updateUser(SysUserDO user) {
        return sysUserMapper.updateUser(user);
    }

    @Override
    public int insertUser(SysUserDO user) {
        return sysUserMapper.insertUser(user);
    }

    @Override
    public int checkLoginNameUnique(String loginName) {
        return sysUserMapper.checkLoginNameUnique(loginName);
    }

    @Override
    public SysUser checkPhoneUnique(String phonenumber) {
        return sysUserMapper.checkPhoneUnique(phonenumber);
    }

    @Override
    public SysUser checkEmailUnique(String email) {
        return sysUserMapper.checkEmailUnique(email);
    }

    @Override
    public Set<Long> selectUserIdsHasRoles(Long[] roleIds) {
        return sysUserMapper.selectUserIdsHasRoles(roleIds);
    }

    @Override
    public Set<Long> selectUserIdsInDepts(Long[] deptIds) {
        return sysUserMapper.selectUserIdsInDepts(deptIds);
    }

    @Override
    public List<UserExportVO> export(SysUser sysUser) {
        return sysUserMapper.export(sysUser);
    }

    @Override
    public List<UserExportRoleVO> exportMenu(List<Long> roleIds) {
        return sysUserMapper.exportMenu(roleIds);
    }
}
