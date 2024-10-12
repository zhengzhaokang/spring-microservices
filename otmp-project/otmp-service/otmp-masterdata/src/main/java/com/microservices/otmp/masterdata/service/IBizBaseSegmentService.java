package com.microservices.otmp.masterdata.service;

import java.util.List;

import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseSegment;
import com.microservices.otmp.masterdata.domain.entity.dto.ToMsSegmentDTO;
import com.microservices.otmp.masterdata.domain.entity.vo.MsSegmentVo;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * BaseSegmentService接口
 *
 * @author lovefamily
 * @date 2022-04-24
 */
public interface IBizBaseSegmentService
{
    /**
     * 查询BaseSegment
     *
     * @param id BaseSegment主键
     * @return BaseSegment
     */
    public BizBaseSegment selectBizBaseSegmentById(Long id);

    /**
     * 查询BaseSegment列表
     *
     * @param bizBaseSegment BaseSegment
     * @return BaseSegment集合
     */
    public List<BizBaseSegment> selectBizBaseSegmentList(BizBaseSegment bizBaseSegment);

    /**
     * 查询BaseSegment列表
     *
     * @param bizBaseSegment BaseSegment
     * @return BaseSegment集合
     */
    public List<BizBaseSegment> getGpnCodeList(BizBaseSegment bizBaseSegment);


    /**
     * 查询下拉框
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BaseSegment集合
     */
    public List<BizBaseSegment> getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition);
    /**
     * 新增BaseSegment
     *
     * @param bizBaseSegment BaseSegment
     * @return 结果
     */
    public int insertBizBaseSegment(BizBaseSegment bizBaseSegment);

    /**
     * 修改BaseSegment
     *
     * @param bizBaseSegment BaseSegment
     * @return 结果
     */
    public int updateBizBaseSegment(BizBaseSegment bizBaseSegment);

    /**
     * 批量删除BaseSegment
     *
     * @param ids 需要删除的BaseSegment主键集合
     * @return 结果
     */
    public int deleteBizBaseSegmentByIds(Long[] ids);

    /**
     * 删除BaseSegment信息
     *
     * @param id BaseSegment主键
     * @return 结果
     */
    public int deleteBizBaseSegmentById(Long id);

    String importExcel(List<BizBaseSegment> bizs, String loginName);

    List<MsSegmentVo> toMsSegmentList(ToMsSegmentDTO toMsSegmentDTO);

    /**
     * 根据businessGroup、geo、segment code、level查询BaseSegment信息
     * @param businessGroup
     * @param segmentCode
     * @param segmentLevel
     * @return
     */
    BizBaseSegment selectBizBaseSegment(String businessGroup, String segmentCode, String segmentLevel);
}
