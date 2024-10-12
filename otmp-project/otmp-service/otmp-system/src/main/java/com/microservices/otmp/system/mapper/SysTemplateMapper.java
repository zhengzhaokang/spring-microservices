package com.microservices.otmp.system.mapper;

import com.microservices.otmp.system.domain.SysTemplate;

import java.util.List;


/**
 * 模板管理Mapper接口
 * 
 * @author lovefamily
 * @date 2022-08-18
 */
public interface SysTemplateMapper
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
     * 删除模板管理
     * 
     * @param id 模板管理主键
     * @return 结果
     */
    public int deleteSysTemplateById(Integer id);

    /**
     * 批量删除模板管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysTemplateByIds(Integer[] ids);

    int removeFileById(Integer id);

    int countByTemplateCode(String templateCode);

    int countByTemplateName(String templateName);
}
