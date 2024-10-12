package com.microservices.otmp.system.util;

import com.microservices.otmp.common.utils.security.ShaUtils;
import com.microservices.otmp.system.domain.SysUser;

public class PasswordUtil {
    public static boolean matches(SysUser user, String newPassword) {
        return user.getPassword().equalsIgnoreCase(encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
    }

    public static String encryptPassword(String username, String password, String salt) {
        return ShaUtils.encodeSHA256(username + password + salt);
    }
}