package com.microservices.otmp.masterdata.mapper;

import java.util.List;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseApcDO;

/**
 * BaseApcMapper接口
 * 
 * @author lovefamily
 * @date 2022-07-15
 */
public interface BizBaseApcMapper
{
    /**
     * 查询BaseApc
     * 
     * @param id BaseApc主键
     * @return BaseApc
     */
    public BizBaseApcDO selectBizBaseApcById(Long id);

    /**
     * 查询BaseApc列表
     * 
     * @param bizBaseApc BaseApc
     * @return BaseApc集合
     */
    @DataPermissions(tableName = "biz_base_apc")
    public List<BizBaseApcDO> selectBizBaseApcList(BizBaseApcDO bizBaseApc);

    /**
     * 新增BaseApc
     * 
     * @param bizBaseApc BaseApc
     * @return 结果
     */
    public int insertBizBaseApc(BizBaseApcDO bizBaseApc);

    /**
     * 修改BaseApc
     * 
     * @param bizBaseApc BaseApc
     * @return 结果
     */
    public int updateBizBaseApc(BizBaseApcDO bizBaseApc);

    /**
     * 删除BaseApc
     * 
     * @param id BaseApc主键
     * @return 结果
     */
    public int deleteBizBaseApcById(Long id);

    /**
     * 批量删除BaseApc
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseApcByIds(Long[] ids);

}
