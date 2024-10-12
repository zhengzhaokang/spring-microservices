package com.microservices.otmp.masterdata.service;

import java.util.List;
import com.microservices.otmp.masterdata.domain.BizBaseTos;

/**
 * BaseTosService接口
 * 
 * @author lovefamily
 * @date 2022-06-24
 */
public interface IBizBaseTosService
{
    /**
     * 查询BaseTos
     * 
     * @param id BaseTos主键
     * @return BaseTos
     */
    public BizBaseTos selectBizBaseTosById(Long id);

    /**
     * 查询BaseTos列表
     * 
     * @param bizBaseTos BaseTos
     * @return BaseTos集合
     */
    public List<BizBaseTos> selectBizBaseTosList(BizBaseTos bizBaseTos);

    /**
     * 新增BaseTos
     * 
     * @param bizBaseTos BaseTos
     * @return 结果
     */
    public int insertBizBaseTos(BizBaseTos bizBaseTos);

    /**
     * 修改BaseTos
     * 
     * @param bizBaseTos BaseTos
     * @return 结果
     */
    public int updateBizBaseTos(BizBaseTos bizBaseTos);

    /**
     * 批量删除BaseTos
     * 
     * @param ids 需要删除的BaseTos主键集合
     * @return 结果
     */
    public int deleteBizBaseTosByIds(Long[] ids);

    /**
     * 删除BaseTos信息
     * 
     * @param id BaseTos主键
     * @return 结果
     */
    public int deleteBizBaseTosById(Long id);

    String importExcel(List<BizBaseTos> bizs, String loginName);
}
