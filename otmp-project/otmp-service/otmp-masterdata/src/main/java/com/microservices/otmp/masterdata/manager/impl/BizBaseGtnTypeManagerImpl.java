package com.microservices.otmp.masterdata.manager.impl;

import com.microservices.otmp.masterdata.domain.entity.BizBaseCurrencyDO;
import com.microservices.otmp.masterdata.domain.entity.dto.ToMsGtnTypeDTO;
import com.microservices.otmp.masterdata.domain.entity.vo.MsGtnTypeVo;
import com.microservices.otmp.masterdata.manager.BizBaseCurrencyManager;
import com.microservices.otmp.masterdata.manager.BizBaseGtnTypeManager;
import com.microservices.otmp.masterdata.mapper.BizBaseCurrencyMapper;
import com.microservices.otmp.masterdata.mapper.BizBaseGtnTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @description
 * @date 2022/6/1 18:02
 */

@Service
public class BizBaseGtnTypeManagerImpl implements BizBaseGtnTypeManager {

    @Autowired
    private BizBaseGtnTypeMapper bizBaseGtnTypeMapper;


    @Override
    public List<MsGtnTypeVo> toMsGtnTypeList(ToMsGtnTypeDTO toMsGtnTypeDTO) {
        return bizBaseGtnTypeMapper.toMsGtnTypeList(toMsGtnTypeDTO);
    }
}
