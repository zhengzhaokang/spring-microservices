package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.entity.SysUserPostDO;
import com.microservices.otmp.system.manager.SysUserPostManager;
import com.microservices.otmp.system.mapper.SysUserPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:23
 */

@Service
public class SysUserPostManagerImpl implements SysUserPostManager {
    
    @Autowired
    private SysUserPostMapper sysUserPostMapper;
    
    @Override
    public int deleteUserPostByUserId(Long userId) {
        return sysUserPostMapper.deleteUserPostByUserId(userId);
    }

    @Override
    public int countUserPostById(Long postId) {
        return sysUserPostMapper.countUserPostById(postId);
    }

    @Override
    public int deleteUserPost(Long[] ids) {
        return sysUserPostMapper.deleteUserPost(ids);
    }

    @Override
    public int batchUserPost(List<SysUserPostDO> userPostList) {
        return sysUserPostMapper.batchUserPost(userPostList);
    }
}
