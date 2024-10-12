package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.common.constant.UserConstants;
import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.common.exception.BusinessException;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.SysPost;
import com.microservices.otmp.system.domain.entity.SysPostDO;
import com.microservices.otmp.system.manager.SysPostManager;
import com.microservices.otmp.system.manager.SysUserPostManager;
import com.microservices.otmp.system.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 岗位信息 服务层处理
 *
 * @author lovefamily
 */
@Service
public class SysPostServiceImpl implements ISysPostService {
    @Autowired
    private SysPostManager sysPostManager;

    @Autowired
    private SysUserPostManager sysUserPostManager;

    /**
     * 查询岗位信息集合
     *
     * @param post 岗位信息
     * @return 岗位信息集合
     */
    @Override
    public List<SysPost> selectPostList(SysPost post) {
        List<SysPostDO> sysPostDOS = sysPostManager.selectPostList(post);
        List<SysPost> sysPosts = new ArrayList<>(sysPostDOS.size());
        BeanUtils.copyListProperties(sysPostDOS, sysPosts, SysPost.class);
        return sysPosts;
    }

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    @Override
    public List<SysPost> selectPostAll() {
        List<SysPostDO> sysPostDOS = sysPostManager.selectPostAll();
        List<SysPost> sysPosts = new ArrayList<>(sysPostDOS.size());
        BeanUtils.copyListProperties(sysPostDOS, sysPosts, SysPost.class);
        return sysPosts;
    }

    /**
     * 根据用户ID查询岗位
     *
     * @param userId 用户ID
     * @return 岗位列表
     */
    @Override
    public List<SysPost> selectPostsByUserId(Long userId) {
        List<SysPostDO> userPosts = sysPostManager.selectPostsByUserId(userId);
        List<SysPostDO> posts = sysPostManager.selectPostAll();
        for (SysPostDO post : posts) {
            for (SysPostDO userRole : userPosts) {
                if (post.getPostId().longValue() == userRole.getPostId().longValue()) {
                    post.setFlag(true);
                    break;
                }
            }
        }
        List<SysPost> sysPosts = new ArrayList<>(posts.size());
        BeanUtils.copyListProperties(posts, sysPosts, SysPost.class);
        return sysPosts;
    }

    /**
     * 通过岗位ID查询岗位信息
     *
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    @Override
    public SysPost selectPostById(Long postId) {
        SysPostDO sysPostDO = sysPostManager.selectPostById(postId);
        SysPost sysPost = new SysPost();
        org.springframework.beans.BeanUtils.copyProperties(sysPostDO, sysPost);
        return sysPost;
    }

    /**
     * 批量删除岗位信息
     *
     * @param ids 需要删除的数据ID
     * @throws Exception
     */
    @Override
    public int deletePostByIds(String ids) throws BusinessException {
        Long[] postIds = Convert.toLongArray(ids);
        for (Long postId : postIds) {
            SysPost post = selectPostById(postId);
            if (countUserPostById(postId) > 0) {
                throw new BusinessException(String.format("%1$s已分配,不能删除", post.getPostName()));
            }
        }
        return sysPostManager.deletePostByIds(postIds);
    }

    /**
     * 新增保存岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public int insertPost(SysPost post) {
        SysPostDO sysPostDO = new SysPostDO();
        org.springframework.beans.BeanUtils.copyProperties(post, sysPostDO);
        return sysPostManager.insertPost(sysPostDO);
    }

    /**
     * 修改保存岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public int updatePost(SysPost post) {
        SysPostDO sysPostDO = new SysPostDO();
        org.springframework.beans. BeanUtils.copyProperties(post, sysPostDO);
        return sysPostManager.updatePost(sysPostDO);
    }

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public int countUserPostById(Long postId) {
        return sysUserPostManager.countUserPostById(postId);
    }

    /**
     * 校验岗位名称是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public String checkPostNameUnique(SysPost post) {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPostDO info = sysPostManager.checkPostNameUnique(post.getPostName());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
            return UserConstants.POST_NAME_NOT_UNIQUE;
        }
        return UserConstants.POST_NAME_UNIQUE;
    }

    /**
     * 校验岗位编码是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public String checkPostCodeUnique(SysPost post) {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPostDO info = sysPostManager.checkPostCodeUnique(post.getPostCode());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
            return UserConstants.POST_CODE_NOT_UNIQUE;
        }
        return UserConstants.POST_CODE_UNIQUE;
    }
}
