package com.microservices.otmp.erp.mapper;

import com.microservices.otmp.erp.domain.SysWording;

import java.util.List;

/**
 * wordMapper接口
 * 
 * @author shirui3
 * @date 2022-05-07
 */
public interface SysWordingMapper
{
    /**
     * 查询word
     * 
     * @param id word主键
     * @return word
     */
    public SysWording selectSysWordingById(Long id);

    /**
     * 查询word列表
     * 
     * @param sysWording word
     * @return word集合
     */
    public List<SysWording> selectSysWordingList(SysWording sysWording);

    /**
     * 校验wordingCode/wordingKey
     *
     * @param sysWording word
     * @return word集合
     */
    public List<SysWording> selectSysWordingListForCheck(SysWording sysWording);

    public List<SysWording> selectSysWordingListForRedis(SysWording sysWording);

    /**
     * 新增word
     * 
     * @param sysWording word
     * @return 结果
     */
    public int insertSysWording(SysWording sysWording);

    /**
     * 修改word
     * 
     * @param sysWording word
     * @return 结果
     */
    public int updateSysWording(SysWording sysWording);

    /**
     * 删除word
     * 
     * @param id word主键
     * @return 结果
     */
    public int deleteSysWordingById(Long id);

    /**
     * 批量删除word
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysWordingByIds(Long[] ids);
}
