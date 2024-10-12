package com.microservices.otmp.masterdata.manager.impl;

import com.microservices.otmp.masterdata.domain.BizBaseSegment;
import com.microservices.otmp.masterdata.domain.entity.dto.ToMsSegmentDTO;
import com.microservices.otmp.masterdata.domain.entity.vo.MsSegmentVo;
import com.microservices.otmp.masterdata.manager.BizBaseSegmentManager;
import com.microservices.otmp.masterdata.mapper.BizBaseSegmentMapper;
import com.microservices.otmp.masterdata.domain.BizBaseSegment;
import com.microservices.otmp.masterdata.manager.BizBaseSegmentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @description
 * @date 2022/6/1 18:02
 */

@Service
public class BizBaseSegmentManagerImpl implements BizBaseSegmentManager {
    @Autowired
    private BizBaseSegmentMapper bizBaseSegmentMapper;


    @Override
    public List<MsSegmentVo> toMsSegmentList(ToMsSegmentDTO toMsSegmentDTO) {
        return bizBaseSegmentMapper.toMsSegmentList(toMsSegmentDTO);
    }

    @Override
    public BizBaseSegment selectBizBaseSegment(String businessGroup, String segmentCode, String segmentLevel) {
        return bizBaseSegmentMapper.selectBizBaseSegment(businessGroup, segmentCode, segmentLevel);
    }
}
