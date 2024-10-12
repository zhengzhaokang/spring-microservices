package com.microservices.otmp.system.domain.entity;

import com.microservices.otmp.system.domain.dto.DimensionDTO;

import java.util.Date;
import java.util.Objects;

/**
 * SysBusinessOperatorLog对象 sys_business_operator_log
 * 
 * @author lovefamily
 * @date 2022-08-18
 */
public class SysBusinessOperatorLogDO extends DimensionDTO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** Operation */

    private String operation;

    /** Operator */

    private String operator;

    /** Operation Date */

    private Date operationDate;

    /** Target */

    private String target;

    /** Detail */

    private String detail;

    /** $column.columnComment */

    private String moduleType;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOperation(String operation) 
    {
        this.operation = operation;
    }

    public String getOperation() 
    {
        return operation;
    }
    public void setOperator(String operator) 
    {
        this.operator = operator;
    }

    public String getOperator() 
    {
        return operator;
    }
    public void setOperationDate(Date operationDate)
    {
        this.operationDate	 = operationDate;
    }

    public Date getOperationDate()
    {
        return operationDate;
    }
    public void setTarget(String target) 
    {
        this.target = target;
    }

    public String getTarget() 
    {
        return target;
    }
    public void setDetail(String detail) 
    {
        this.detail = detail;
    }

    public String getDetail() 
    {
        return detail;
    }
    public void setModuleType(String moduleType) 
    {
        this.moduleType = moduleType;
    }

    public String getModuleType() 
    {
        return moduleType;
    }

    @Override
    public String toString() {
        return "SysBusinessOperatorLogDO{" +
                "id=" + id +
                ", operation='" + operation + '\'' +
                ", operator='" + operator + '\'' +
                ", operationDate=" + operationDate +
                ", target='" + target + '\'' +
                ", detail='" + detail + '\'' +
                ", moduleType='" + moduleType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SysBusinessOperatorLogDO that = (SysBusinessOperatorLogDO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(operation, that.operation) &&
                Objects.equals(operator, that.operator) &&
                Objects.equals(operationDate, that.operationDate) &&
                Objects.equals(target, that.target) &&
                Objects.equals(detail, that.detail) &&
                Objects.equals(moduleType, that.moduleType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, operation, operator, operationDate, target, detail, moduleType);
    }
}
