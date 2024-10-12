package com.microservices.otmp.system.service;


import com.microservices.otmp.system.domain.SysTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 模板管理Service接口
 * 
 * @author lovefamily
 * @date 2022-08-18
 */
public interface ISysTemplateService
{
    /**
     * 查询模板管理
     * 
     * @param id 模板管理主键
     * @return 模板管理
     */
    public SysTemplate selectSysTemplateById(Integer id);

    /**
     * 查询模板管理列表
     * 
     * @param sysTemplate 模板管理
     * @return 模板管理集合
     */
    public List<SysTemplate> selectSysTemplateList(SysTemplate sysTemplate);

    /**
     * 新增模板管理
     * 
     * @param sysTemplate 模板管理
     * @return 结果
     */
    public int insertSysTemplate(SysTemplate sysTemplate);

    /**
     * 修改模板管理
     * 
     * @param sysTemplate 模板管理
     * @return 结果
     */
    public int updateSysTemplate(SysTemplate sysTemplate);

    /**
     * 批量删除模板管理
     * 
     * @param ids 需要删除的模板管理主键集合
     * @return 结果
     */
    public int deleteSysTemplateByIds(Integer[] ids);

    /**
     * 删除模板管理信息
     * 
     * @param id 模板管理主键
     * @return 结果
     */
    public int deleteSysTemplateById(Integer id);

    void downLoadTemplateByModule(String module,String templateCode, HttpServletResponse response) throws IOException;

    int removeFileById(Integer id);
}
