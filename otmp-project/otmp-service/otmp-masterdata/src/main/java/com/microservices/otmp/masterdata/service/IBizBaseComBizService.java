package com.microservices.otmp.masterdata.service;

import com.microservices.otmp.masterdata.domain.BizBaseComBiz;
import java.util.List;

/**
 * baseComBizService接口
 * 
 * @author sdms
 * @date 2022-01-17
 */
public interface IBizBaseComBizService 
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
    public List<BizBaseComBiz> selectBizBaseComBizList(BizBaseComBiz bizBaseComBiz);
    public List<BizBaseComBiz> getComBiz();
    /**
     * 新增baseComBiz
     * 
     * @param bizBaseComBiz baseComBiz
     * @return 结果
     */
    public int insertBizBaseComBiz(BizBaseComBiz bizBaseComBiz);

    /**
     * 修改baseComBiz
     * 
     * @param bizBaseComBiz baseComBiz
     * @return 结果
     */
    public int updateBizBaseComBiz(BizBaseComBiz bizBaseComBiz);

    /**
     * 批量删除baseComBiz
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizBaseComBizByIds(String loginName,String bizTable,String ids);

    /**
     * 删除baseComBiz信息
     * 
     * @param id baseComBizID
     * @return 结果
     */
    public int deleteBizBaseComBizById(Integer id);

    String importExcel(List<BizBaseComBiz> bizs,String bizTable,String name);
}
