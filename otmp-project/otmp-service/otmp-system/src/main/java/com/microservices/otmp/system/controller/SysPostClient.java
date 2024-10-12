package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.system.domain.SysPost;
import com.microservices.otmp.system.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 岗位 提供者
 *
 * @Author lovefamily
 * @date 2019-05-20
 */
@RestController
@RequestMapping("/sys/sysPost")
public class SysPostClient extends BaseController {

    @Autowired
    private ISysPostService sysPostService;

    /**
     * 查询岗位
     */
    @GetMapping("get/{postId}")
    public SysPost get(@PathVariable("postId") Long postId) {
        return sysPostService.selectPostById(postId);

    }

    /**
     * 查询岗位列表
     */
    @PostMapping("list")
    public List<SysPost> list(SysPost sysPost) {
        startPage();
        return sysPostService.selectPostList(sysPost);
    }


    /**
     * 新增保存岗位
     */
    @PostMapping("save")
    @RequiresPermissions("system:post:create")
    public int addSave(SysPost sysPost) {
        return sysPostService.insertPost(sysPost);
    }

    /**
     * 修改保存岗位
     */
    @PostMapping("update")
    @RequiresPermissions("system:post:edit")
    public int editSave(SysPost sysPost) {
        return sysPostService.updatePost(sysPost);
    }

    /**
     * 删除岗位
     */
    @PostMapping("remove")
    @RequiresPermissions("system:post:remove")
    public int remove(String ids) {
        return sysPostService.deletePostByIds(ids);
    }

}
