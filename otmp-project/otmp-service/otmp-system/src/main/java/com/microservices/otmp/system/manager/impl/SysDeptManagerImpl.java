package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysDept;
import com.microservices.otmp.system.domain.entity.SysDeptDO;
import com.microservices.otmp.system.manager.SysDeptManager;
import com.microservices.otmp.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author guowb1
 * @date 2022/6/10 17:20
 */

@Service
public class SysDeptManagerImpl implements SysDeptManager {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public int selectDeptCount(SysDeptDO dept) {
        return sysDeptMapper.selectDeptCount(dept);
    }

    @Override
    public int checkDeptExistUser(Long deptId) {
        return sysDeptMapper.checkDeptExistUser(deptId);
    }

    @Override
    public List<SysDeptDO> selectDeptList(SysDept dept) {
        return sysDeptMapper.selectDeptList(dept);
    }

    @Override
    public int deleteDeptById(Long deptId) {
        return sysDeptMapper.deleteDeptById(deptId);
    }

    @Override
    public int insertDept(SysDeptDO dept) {
        return sysDeptMapper.insertDept(dept);
    }

    @Override
    public int updateDept(SysDeptDO dept) {
        return sysDeptMapper.updateDept(dept);
    }

    @Override
    public int updateDeptChildren(List<SysDeptDO> depts) {
        return sysDeptMapper.updateDeptChildren(depts);
    }

    @Override
    public SysDeptDO selectDeptById(Long deptId) {
        return sysDeptMapper.selectDeptById(deptId);
    }

    @Override
    public SysDeptDO checkDeptNameUnique(String deptName, Long parentId) {
        return sysDeptMapper.checkDeptNameUnique(deptName, parentId);
    }

    @Override
    public List<String> selectRoleDeptTree(Long roleId) {
        return sysDeptMapper.selectRoleDeptTree(roleId);
    }

    @Override
    public void updateDeptStatus(SysDeptDO dept) {
        sysDeptMapper.updateDeptStatus(dept);
    }

    @Override
    public List<SysDeptDO> selectChildrenDeptById(Long id) {
        return sysDeptMapper.selectChildrenDeptById(id);
    }

    @Override
    public Set<String> selectRoleDeptIds(Long roleId) {
        return sysDeptMapper.selectRoleDeptIds(roleId);
    }
}
