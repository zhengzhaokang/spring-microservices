package com.microservices.otmp.system.service.impl;

import cn.hutool.core.util.IdUtil;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.redis.annotation.RedisEvict;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.system.domain.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("accessTokenService")
public class AccessTokenService
{
    @Autowired
    private RedisUtils          redis;

    /**
     * 12小时后过期
     */
    private static final long   EXPIRE        = 12L * 60L * 60L;

    private static final String ACCESS_TOKEN  = Constants.ACCESS_TOKEN;

    private static final String ACCESS_USERID = Constants.ACCESS_USERID;

    @Value("${microservices.id.switch}")
    private Boolean microservicesIdSwitch;

    public SysUser queryByToken(String token)
    {
        return redis.get(ACCESS_TOKEN + token, SysUser.class);
    }

    @RedisEvict(key = "user_perms", fieldKey = "#sysUser.userId")
    public Map<String, Object> createToken(SysUser sysUser)
    {
        // 踢出前面登陆的用户
        expireToken(sysUser.getUserId());
        // 生成token
        String token = IdUtil.fastSimpleUUID();
        // 保存或更新用户token
        Map<String, Object> map = new HashMap<>();
        map.put("userId", sysUser.getUserId());
        map.put("token", token);
        map.put("expire", EXPIRE);
        //返回开关
        map.put("microservicesIdSwitch", microservicesIdSwitch);
        redis.set(ACCESS_TOKEN + token, sysUser, EXPIRE);
        redis.set(ACCESS_USERID + sysUser.getUserId(), token, EXPIRE);
        return map;
    }

    public void expireToken(long userId)
    {
        String token = redis.get(ACCESS_USERID + userId);
        if (StringUtils.isNotBlank(token))
        {
            redis.delete(ACCESS_USERID + userId);
            redis.delete(ACCESS_TOKEN + token);
        }
    }
}