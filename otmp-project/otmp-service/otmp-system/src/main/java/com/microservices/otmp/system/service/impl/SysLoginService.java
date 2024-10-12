package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.constant.UserConstants;
import com.microservices.otmp.common.enums.UserStatus;
import com.microservices.otmp.common.exception.user.UserBlockedException;
import com.microservices.otmp.common.exception.user.UserDeleteException;
import com.microservices.otmp.common.exception.user.UserNotExistsException;
import com.microservices.otmp.common.exception.user.UserPasswordNotMatchException;
import com.microservices.otmp.common.log.publish.PublishFactory;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.IpUtils;
import com.microservices.otmp.common.utils.MessageUtils;
import com.microservices.otmp.common.utils.ServletUtils;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.feign.RemoteUserService;
import com.microservices.otmp.system.service.ISysUserService;
import com.microservices.otmp.system.util.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SysLoginService
{

    @Autowired
    private ISysUserService userService;

    /**
     * 登录
     */
    public SysUser login(String username, String password)
    {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password))
        {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null"));
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内 错误
//        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
//                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
//        {
//            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
//                    MessageUtils.message("user.password.not.match"));
//            throw new UserPasswordNotMatchException();
//        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.password.not.match"));
            throw new UserPasswordNotMatchException();
        }
        // 查询用户信息
        SysUser user = userService.selectUserByLoginName(username);
        if (user == null)
        {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.not.exists"));
            throw new UserNotExistsException();
        }
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.password.delete"));
            throw new UserDeleteException();
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.blocked", user.getRemark()));
            throw new UserBlockedException();
        }

        SysUser checkPwdUser = new SysUser();
        checkPwdUser.setLoginName(username);
        checkPwdUser.setPassword(password);
        boolean isMatches = userService.checkPwd(checkPwdUser);
        if (!isMatches)
        {
            throw new UserPasswordNotMatchException();
        }
        PublishFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
        recordLoginInfo(user);
        return user;
    }

    /**
     * 记录登录信息
     */
    public void recordLoginInfo(SysUser user)
    {
        SysUser param = new SysUser();
        param.setUserId(user.getUserId());
        param.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        param.setLoginDate(DateUtils.getNowDate());
        userService.updateUserLoginInfo(param);
    }

    public void logout(String loginName)
    {
        PublishFactory.recordLogininfor(loginName, Constants.LOGOUT, MessageUtils.message("user.logout.success"));
    }

    public SysUser login4microservicesId(String username, String password)
    {

        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password))
        {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null"));
            throw new UserNotExistsException(username);
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.password.not.match"));
            throw new UserPasswordNotMatchException(username);
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.password.not.match"));
            throw new UserPasswordNotMatchException(username);
        }
        // 查询用户信息
        SysUser user = userService.selectUserByLoginName(username);
        if (user == null)
        {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.not.exists"));
            throw new UserNotExistsException(username);
        }
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.password.delete"));
            throw new UserDeleteException(username);
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.blocked", user.getRemark()));
            throw new UserBlockedException(username);
        }
        PublishFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
        recordLoginInfo(user);
        return user;
    }

}