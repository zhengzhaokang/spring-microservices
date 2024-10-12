package com.microservices.otmp.masterdata.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.masterdata.domain.dto.BizBaseApcDTO;

/**
 * BaseApcService接口
 * 
 * @author lovefamily
 * @date 2022-07-15
 */
public interface IBizBaseApcService
{
    /**
     * 查询BaseApc
     * 
     * @param id BaseApc主键
     * @return BaseApc
     */
    public BizBaseApcDTO selectBizBaseApcById(Long id);

    /**
     * 查询BaseApc列表
     * 
     * @param bizBaseApc BaseApc
     * @return BaseApc集合
     */
    public List<BizBaseApcDTO> selectBizBaseApcList(BizBaseApcDTO bizBaseApc);
    public PageInfo<BizBaseApcDTO> selectBizBaseApcListByPage(BizBaseApcDTO bizBaseApc);
    /**
     * 新增BaseApc
     * 
     * @param bizBaseApc BaseApc
     * @return 结果
     */
    public int insertBizBaseApc(BizBaseApcDTO bizBaseApc);

    /**
     * 修改BaseApc
     * 
     * @param bizBaseApc BaseApc
     * @return 结果
     */
    public int updateBizBaseApc(BizBaseApcDTO bizBaseApc);

    /**
     * 批量删除BaseApc
     * 
     * @param ids 需要删除的BaseApc主键集合
     * @return 结果
     */
    public int deleteBizBaseApcByIds(Long[] ids);

    /**
     * 删除BaseApc信息
     * 
     * @param id BaseApc主键
     * @return 结果
     */
    public int deleteBizBaseApcById(Long id);

}
