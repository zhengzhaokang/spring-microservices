package com.microservices.otmp.masterdata.mapper;

import java.util.List;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseTos;

/**
 * BaseTosMapper接口
 * 
 * @author lovefamily
 * @date 2022-06-24
 */
public interface BizBaseTosMapper
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
    @DataPermissions(tableName = "biz_base_tos")
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
     * 删除BaseTos
     * 
     * @param id BaseTos主键
     * @return 结果
     */
    public int deleteBizBaseTosById(Long id);

    /**
     * 批量删除BaseTos
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseTosByIds(Long[] ids);
}
