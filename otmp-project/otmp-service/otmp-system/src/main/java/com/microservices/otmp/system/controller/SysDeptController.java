package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.system.domain.SysDept;
import com.microservices.otmp.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 部门 提供者
 * 
 * @Author lovefamily
 * @date 2019-05-20
 */
@RestController
@RequestMapping("dept")
public class SysDeptController extends BaseController
{
    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 查询部门
     */
    @GetMapping("get/{deptId}")
    public SysDept get(@PathVariable("deptId") Long deptId)
    {
        return sysDeptService.selectDeptById(deptId);
    }

    /**
     * 查询部门列表
     */
    @GetMapping("list")
    public TableDataInfo list(SysDept sysDept)
    {
        startPage();
        return getDataTable(sysDeptService.selectDeptPage(sysDept));
    }

    /**
     * 查询所有可用部门
     */
    @GetMapping("list/enable")
    public ResultDTO<List<SysDept>> listEnable(SysDept sysDept)
    {
        sysDept.setStatus("0");
        return ResultDTO.success(sysDeptService.selectDeptList(sysDept));
    }

    /**
     * 新增保存部门
     */
    @PostMapping("save")
    public ResultDTO<Integer> addSave(@RequestBody SysDept sysDept)
    {
        return ResultDTO.success(sysDeptService.insertDept(sysDept));
    }

    /**
     * 修改保存部门
     */
    @PostMapping("update")
    public ResultDTO<Integer> editSave(@RequestBody SysDept sysDept)
    {
        return ResultDTO.success(sysDeptService.updateDept(sysDept));
    }

    /**
     * 删除部门
     */
    @PostMapping("remove/{deptId}")
    public ResultDTO<Integer> remove(@PathVariable("deptId") Long deptId)
    {
        return ResultDTO.success(sysDeptService.deleteDeptById(deptId));
    }

    /**
     * 加载角色部门（数据权限）列表树
     */
    @GetMapping("/role/{roleId}")
    public Set<String> deptTreeData(@PathVariable("roleId") Long roleId)
    {
        if (null == roleId || roleId <= 0) return Collections.emptySet();
        return sysDeptService.roleDeptIds(roleId);
    }
}
