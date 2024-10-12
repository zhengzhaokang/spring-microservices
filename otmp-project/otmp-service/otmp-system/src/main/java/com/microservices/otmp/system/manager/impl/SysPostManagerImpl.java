package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysPost;
import com.microservices.otmp.system.domain.entity.SysPostDO;
import com.microservices.otmp.system.manager.SysPostManager;
import com.microservices.otmp.system.mapper.SysPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:21
 */

@Service
public class SysPostManagerImpl implements SysPostManager {

    @Autowired
    private SysPostMapper sysPostMapper;

    @Override
    public List<SysPostDO> selectPostList(SysPost post) {
        return sysPostMapper.selectPostList(post);
    }

    @Override
    public List<SysPostDO> selectPostAll() {
        return sysPostMapper.selectPostAll();
    }

    @Override
    public List<SysPostDO> selectPostsByUserId(Long userId) {
        return sysPostMapper.selectPostsByUserId(userId);
    }

    @Override
    public SysPostDO selectPostById(Long postId) {
        return sysPostMapper.selectPostById(postId);
    }

    @Override
    public int deletePostByIds(Long[] ids) {
        return sysPostMapper.deletePostByIds(ids);
    }

    @Override
    public int updatePost(SysPostDO post) {
        return sysPostMapper.updatePost(post);
    }

    @Override
    public int insertPost(SysPostDO post) {
        return sysPostMapper.insertPost(post);
    }

    @Override
    public SysPostDO checkPostNameUnique(String postName) {
        return sysPostMapper.checkPostNameUnique(postName);
    }

    @Override
    public SysPostDO checkPostCodeUnique(String postCode) {
        return sysPostMapper.checkPostCodeUnique(postCode);
    }
}
