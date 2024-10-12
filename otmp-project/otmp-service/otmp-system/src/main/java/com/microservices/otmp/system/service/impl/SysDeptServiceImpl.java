package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.annotation.DataScope;
import com.microservices.otmp.common.constant.UserConstants;
import com.microservices.otmp.common.core.domain.Ztree;
import com.microservices.otmp.common.exception.BusinessException;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.SysDept;
import com.microservices.otmp.system.domain.SysRole;
import com.microservices.otmp.system.domain.entity.SysDeptDO;
import com.microservices.otmp.system.manager.SysDeptManager;
import com.microservices.otmp.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 部门管理 服务实现
 * 
 * @author lovefamily
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService
{
    @Autowired
    private SysDeptManager deptManager;

    /**
     * 查询部门管理数据
     * 
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysDept> selectDeptList(SysDept dept)
    {
        List<SysDeptDO> sysDeptDOS = deptManager.selectDeptList(dept);
        List<SysDept> sysDepts = new ArrayList<>(sysDeptDOS.size());
        for (SysDeptDO deptDO : sysDeptDOS) {
            SysDept sysDept = new SysDept();
            org.springframework.beans.BeanUtils.copyProperties(deptDO, sysDept);
            sysDepts.add(sysDept);
        }
        return sysDepts;
    }

    /**
     * 查询部门管理树
     * 
     * @param dept 部门信息
     * @return 所有部门信息
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<Ztree> selectDeptTree(SysDept dept)
    {
        List<SysDeptDO> sysDeptDOS = deptManager.selectDeptList(dept);
        List<SysDept> sysDepts = new ArrayList<>(sysDeptDOS.size());
        for (SysDeptDO deptDO : sysDeptDOS) {
            SysDept sysDept = new SysDept();
            org.springframework.beans.BeanUtils.copyProperties(deptDO, sysDept);
            sysDepts.add(sysDept);
        }
        return initZtree(sysDepts);
    }

    /**
     * 根据角色ID查询部门（数据权限）
     *
     * @param role 角色对象
     * @return 部门列表（数据权限）
     */
    @Override
    public List<Ztree> roleDeptTreeData(SysRole role)
    {
        Long roleId = role.getRoleId();
        List<Ztree> ztrees;
        List<SysDept> deptList = selectDeptList(new SysDept());
        if (StringUtils.isNotNull(roleId))
        {
            List<String> roleDeptList = deptManager.selectRoleDeptTree(roleId);
            ztrees = initZtree(deptList, roleDeptList);
        }
        else
        {
            ztrees = initZtree(deptList);
        }
        return ztrees;
    }

    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysDept> deptList)
    {
        return initZtree(deptList, null);
    }

    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @param roleDeptList 角色已存在菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysDept> deptList, List<String> roleDeptList)
    {

        List<Ztree> ztrees = new ArrayList<>();
        boolean isCheck = StringUtils.isNotNull(roleDeptList);
        for (SysDept dept : deptList)
        {
            if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()))
            {
                Ztree ztree = new Ztree();
                ztree.setId(dept.getDeptId());
                ztree.setpId(dept.getParentId());
                ztree.setName(dept.getDeptName());
                ztree.setTitle(dept.getDeptName());
                if (isCheck)
                {
                    ztree.setChecked(roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
                }
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

    /**
     * 查询部门人数
     * 
     * @param parentId 部门ID
     * @return 结果
     */
    @Override
    public int selectDeptCount(Long parentId)
    {
        SysDeptDO sysDeptDO = new SysDeptDO();
        sysDeptDO.setParentId(parentId);
        return deptManager.selectDeptCount(sysDeptDO);
    }

    /**
     * 查询部门是否存在用户
     * 
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId)
    {
        int result = deptManager.checkDeptExistUser(deptId);
        return result > 0;
    }

    /**
     * 删除部门管理信息
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId)
    {
        return deptManager.deleteDeptById(deptId);
    }

    /**
     * 新增保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(SysDept dept)
    {
        if(dept.getParentId()>0) {
            SysDeptDO info = deptManager.selectDeptById(dept.getParentId());
            // 如果父节点不为"正常"状态,则不允许新增子节点
            if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
            {
                throw new BusinessException("部门停用，不允许新增");
            }
            dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        }
        SysDeptDO sysDeptDO = new SysDeptDO();
        org.springframework.beans.BeanUtils.copyProperties(dept, sysDeptDO);
        return deptManager.insertDept(sysDeptDO);
    }

    /**
     * 修改保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDept(SysDept dept)
    {
        SysDeptDO newParentDept = deptManager.selectDeptById(dept.getParentId());
        SysDept oldDept = selectDeptById(dept.getDeptId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept))
        {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors,oldAncestors);
        }
        SysDeptDO sysDeptDO = new SysDeptDO();
        org.springframework.beans.BeanUtils.copyProperties(dept, sysDeptDO);
        int result = deptManager.updateDept(sysDeptDO);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()))
        {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(sysDeptDO);
        }
        return result;
    }

    /**
     * 修改该部门的父级部门状态
     * 
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(SysDeptDO dept)
    {
        String updateBy = dept.getUpdateBy();
        dept = deptManager.selectDeptById(dept.getDeptId());
        dept.setUpdateBy(updateBy);
        deptManager.updateDeptStatus(dept);
    }

    /**
     * 修改子元素关系
     * @param deptId 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors,String oldAncestors)
    {
        List<SysDeptDO> children = deptManager.selectChildrenDeptById(deptId);
        for (SysDeptDO child : children)
        {
            child.setAncestors(child.getAncestors().replace(oldAncestors,newAncestors));
        }
        if (!children.isEmpty())
        {
            deptManager.updateDeptChildren(children);
        }
    }

    /**
     * 根据部门ID查询信息
     * 
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public SysDept selectDeptById(Long deptId)
    {
        SysDeptDO sysDeptDO = deptManager.selectDeptById(deptId);
        SysDept sysDept = new SysDept();
        org.springframework.beans.BeanUtils.copyProperties(sysDeptDO, sysDept);
        return sysDept;
    }

    /**
     * 校验部门名称是否唯一
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(SysDept dept)
    {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        SysDeptDO info = deptManager.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue())
        {
            return UserConstants.DEPT_NAME_NOT_UNIQUE;
        }
        return UserConstants.DEPT_NAME_UNIQUE;
    }

    @Override
    public Set<String> roleDeptIds(Long roleId)
    {
        return deptManager.selectRoleDeptIds(roleId);
    }

    @Override
    public PageInfo<SysDept> selectDeptPage(SysDept dept) {
        List<SysDeptDO> sysDeptDOS = deptManager.selectDeptList(dept);
        PageInfo<SysDeptDO> pageInfo = new PageInfo<>(sysDeptDOS);
        List<SysDept> sysDepts = new ArrayList<>(sysDeptDOS.size());
        BeanUtils.copyListProperties(sysDeptDOS, sysDepts, SysDept.class);
        PageInfo<SysDept> resultPageInfo = new PageInfo<>(sysDepts);
        resultPageInfo.setTotal(pageInfo.getTotal());
        return resultPageInfo;
    }
}
