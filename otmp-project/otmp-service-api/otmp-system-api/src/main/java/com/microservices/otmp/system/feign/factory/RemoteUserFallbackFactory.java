package com.microservices.otmp.system.feign.factory;

import com.microservices.otmp.common.core.domain.R;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.feign.RemoteUserService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService>
{
    @Override
    public RemoteUserService create(Throwable throwable)
    {
        log.error(throwable.getMessage());
        return new RemoteUserService()
        {
            @Override
            public SysUser selectSysUserByUsername(String username)
            {
                return null;
            }

            @Override
            public R updateUserLoginRecord(SysUser user)
            {
                return R.error();
            }

            @Override
            public SysUser selectSysUserByUserId( long userId)
            {
               /* SysUser user = new SysUser();
                user.setUserId(0l);
                user.setLoginName("no user");
                return user;*/
                throw new OtmpException(throwable.getMessage());
            }

            @Override
            public Set<Long> selectUserIdsHasRoles(String roleId)
            {
                return null;
            }

            @Override
            public Set<Long> selectUserIdsInDepts(String deptIds)
            {
                return null;
            }

            @Override
            public List<SysUser> selectUserList(SysUser user) {
                throw new OtmpException(throwable.getMessage());
            }
            @Override
            public List<SysUser> selectUserListByCommonGeos(SysUser user,String token,String current_id){
                throw new OtmpException(throwable.getMessage());
            }
        };
    }
}
