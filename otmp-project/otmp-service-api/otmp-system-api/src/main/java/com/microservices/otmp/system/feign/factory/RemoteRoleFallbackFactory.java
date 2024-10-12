package com.microservices.otmp.system.feign.factory;

import com.microservices.otmp.system.domain.SysRole;
import com.microservices.otmp.system.feign.RemoteRoleService;
import com.microservices.otmp.system.domain.SysRole;
import com.microservices.otmp.system.feign.RemoteRoleService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RemoteRoleFallbackFactory implements FallbackFactory<RemoteRoleService>
{/* (non-Javadoc)
  * @see feign.hystrix.FallbackFactory#create(java.lang.Throwable)
  */
    @Override
    public RemoteRoleService create(Throwable throwable)
    {
        log.error(throwable.getMessage());
        return new RemoteRoleService()
        {
            @Override
            public SysRole selectSysRoleByRoleId(long roleId)
            {
                return null;
            }

            @Override
            public List<SysRole> selectRoleList(SysRole sysRole) {
                return null;
            }

            @Override
            public List<SysRole> selectRoleAll() {
                return null;
            }
        };
    }
}
