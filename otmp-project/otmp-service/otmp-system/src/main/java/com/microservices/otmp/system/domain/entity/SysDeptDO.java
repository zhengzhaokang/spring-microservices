package com.microservices.otmp.system.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 部门表 sys_dept
 * 
 * @author lovefamily
 */
public class SysDeptDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** 部门ID */
    private Long              deptId;

    /** 父部门ID */
    private Long              parentId;

    /** 祖级列表 */
    private String            ancestors;

    /** 部门名称 */
    private String            deptName;

    /** 显示顺序 */
    private String            orderNum;

    /** 负责人 */
    private String            leader;

    /** 负责人编号 */
    private Long              leaderId;

    /** 联系电话 */
    private String            phone;

    /** 邮箱 */
    private String            email;

    /** 部门状态:0正常,1停用 */
    private String            status;

    /** 删除标志（0代表存在 2代表删除） */
    private String            delFlag;

    /** 父部门名称 */
    private String            parentName;

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public String getAncestors()
    {
        return ancestors;
    }

    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(String orderNum)
    {
        this.orderNum = orderNum;
    }

    public String getLeader()
    {
        return leader;
    }

    public void setLeader(String leader)
    {
        this.leader = leader;
    }

    public Long getLeaderId()
    {
        return leaderId;
    }

    public void setLeaderId(Long leaderId)
    {
        this.leaderId = leaderId;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("deptId", getDeptId())
                .append("parentId", getParentId()).append("ancestors", getAncestors()).append("deptName", getDeptName())
                .append("orderNum", getOrderNum()).append("leader", getLeader()).append("leaderId", getLeaderId()).append("phone", getPhone())
                .append("email", getEmail()).append("status", getStatus()).append("delFlag", getDelFlag())
                .append("createBy", getCreateBy()).append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy()).append("updateTime", getUpdateTime()).toString();
    }
}
