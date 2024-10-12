package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.entity.SysLogininforDO;
import com.microservices.otmp.system.manager.SysLogininforManager;
import com.microservices.otmp.system.mapper.SysLogininforMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:20
 */

@Service
public class SysLogininforManagerImpl implements SysLogininforManager {

    @Autowired
    private SysLogininforMapper sysLogininforMapper;

    @Override
    public void insertLogininfor(SysLogininforDO logininfor) {
        sysLogininforMapper.insertLogininfor(logininfor);
    }

    @Override
    public List<SysLogininforDO> selectLogininforList(SysLogininforDO logininfor) {
        return sysLogininforMapper.selectLogininforList(logininfor);
    }

    @Override
    public int deleteLogininforByIds(String[] ids) {
        return sysLogininforMapper.deleteLogininforByIds(ids);
    }

    @Override
    public int cleanLogininfor() {
        return sysLogininforMapper.cleanLogininfor();
    }
}
