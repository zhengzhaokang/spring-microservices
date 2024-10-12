package com.microservices.otmp.masterdata.mapper;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseComBiz;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * baseComBizMapper接口
 * 
 * @author sdms
 * @date 2022-01-17
 */
public interface BizBaseComBizMapper 
{
    /**
     * 查询baseComBiz
     * 
     * @param id baseComBizID
     * @return baseComBiz
     */
    public BizBaseComBiz selectBizBaseComBizById(Integer id);

    /**
     * 查询baseComBiz列表
     * 
     * @param bizBaseComBiz baseComBiz
     * @return baseComBiz集合
     */
    @DataPermissions(tableName = "biz_base_com_biz")
    public List<BizBaseComBiz> selectBizBaseDataList(@Param("bizBaseComBiz") BizBaseComBiz bizBaseComBiz);

    public List<BizBaseComBiz> selectBizBaseComBizList(@Param("bizBaseComBiz") BizBaseComBiz bizBaseComBiz);
    public List<BizBaseComBiz> getComBiz();

    int insertBizBaseData(@Param("bizTable")String bizTable,@Param("bizBaseComBiz")BizBaseComBiz bizBaseComBiz);

    public int updateBizBaseData(@Param("bizTable")String bizTable,@Param("bizBaseComBiz")BizBaseComBiz bizBaseComBiz);

    /**
     * 删除baseComBiz
     * 
     * @param id baseComBizID
     * @return 结果
     */
    public int deleteBizBaseComBizById(Integer id);

    /**
     * 批量删除baseComBiz
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizBaseComBizByIds(@Param("loginName")String loginName,@Param("bizTable")String bizTable,@Param("ids") String[] ids);

    List<BizBaseComBiz> selectBizBaseDataListCheck(@Param("bizTable")String bizTable,@Param("bizBaseComBiz")BizBaseComBiz bizBaseComBiz);
}
