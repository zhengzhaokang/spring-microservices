package com.microservices.otmp.masterdata.manager;

import com.microservices.otmp.masterdata.domain.BizBaseSegment;
import com.microservices.otmp.masterdata.domain.entity.dto.ToMsSegmentDTO;
import com.microservices.otmp.masterdata.domain.entity.vo.MsSegmentVo;
import com.microservices.otmp.masterdata.domain.BizBaseSegment;

import java.util.List;

/**
 * @author guowb1
 * @description
 * @date 2022/6/1 18:02
 */
public interface BizBaseSegmentManager {


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
