package com.microservices.otmp.masterdata.manager;

import com.microservices.otmp.masterdata.domain.entity.dto.ToMsGtnTypeDTO;
import com.microservices.otmp.masterdata.domain.entity.vo.MsGtnTypeVo;

import java.util.List;

/**
 * @author guowb1
 * @description
 * @date 2022/6/1 18:02
 */
public interface BizBaseGtnTypeManager {


    List<MsGtnTypeVo> toMsGtnTypeList(ToMsGtnTypeDTO toMsGtnTypeDTO);

}
