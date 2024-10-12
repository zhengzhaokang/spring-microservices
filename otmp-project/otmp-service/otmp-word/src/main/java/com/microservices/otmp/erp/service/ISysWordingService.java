package com.microservices.otmp.erp.service;

import com.microservices.otmp.erp.domain.SysWording;

import java.util.List;

/**
 * wordService接口
 * 
 * @author shirui3
 * @date 2022-05-07
 */
public interface ISysWordingService
{
    /**
     * 查询word
     * 
     * @param id word主键
     * @return word
     */
    public SysWording selectSysWordingById(Long id);

    /**
     * 从redis获取word提示语
     * @return word
     */
    public SysWording getWordFromRedis(Long wordingCode);
    /**
     * 从redis获取word提示语 json
     * @return word
     */
    public String json();

    /**
     * 查询word列表
     * 
     * @param sysWording word
     * @return word集合
     */
    public List<SysWording> selectSysWordingList(SysWording sysWording);

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
     * 批量删除word
     * 
     * @param ids 需要删除的word主键集合
     * @return 结果
     */
    public int deleteSysWordingByIds(Long[] ids);

    /**
     * 删除word信息
     * 
     * @param id word主键
     * @return 结果
     */
    public int deleteSysWordingById(Long id);
    /**
     * word提示语插入到redis
     */
    public void insertWordIntoRedis(SysWording sysWording);
    /**
     * 刷新word提示语插入到redis
     */
    public void refreshWordIntoRedis();

    /**
     * 导入提示语
     */
    public String importExcel(List<SysWording> wordings, String loginName);
}
