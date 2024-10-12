package com.microservices.otmp.system.manager;

import com.microservices.otmp.system.domain.entity.SysLogininforDO;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:20
 */
public interface SysLogininforManager {
    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    public void insertLogininfor(SysLogininforDO logininfor);

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    public List<SysLogininforDO> selectLogininforList(SysLogininforDO logininfor);

    /**
     * 批量删除系统登录日志
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    public int deleteLogininforByIds(String[] ids);

    /**
     * 清空系统登录日志
     *
     * @return 结果
     */
    public int cleanLogininfor();
}
