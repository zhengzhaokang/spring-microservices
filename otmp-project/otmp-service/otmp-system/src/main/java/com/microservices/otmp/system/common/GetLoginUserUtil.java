package com.microservices.otmp.system.common;

import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.ServletUtils;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.domain.SysUserDataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Component
public class GetLoginUserUtil {
    @Autowired
    RedisUtils redisUtils;

    /**
     * 获取当前登录人
     *
     * @return
     */
    public SysUser getLoginUser() {
        HttpServletRequest request = ServletUtils.getRequest();
        String token = request.getHeader("token");
        return redisUtils.get(Constants.ACCESS_TOKEN + token, SysUser.class);
    }

    public String getLoginUserName() {
        HttpServletRequest request = ServletUtils.getRequest();
        String token = request.getHeader("token");
        SysUser currentUser = redisUtils.get(Constants.ACCESS_TOKEN + token, SysUser.class);
        if (null != currentUser) {
            return currentUser.getLoginName();
        }
        return "";
    }

    /**
     * 获取当前登录人的数据权限
     *
     * @return
     */
    public List<SysUserDataScope> getLoginUserDataScope() {
        SysUser loginUser = getLoginUser();
        if (null == loginUser) {
            return Collections.emptyList();
        }
        return loginUser.getSysUserDataScopeList();
    }

}
