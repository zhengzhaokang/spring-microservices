package com.microservices.otmp.masterdata.manager.impl;

import com.microservices.otmp.masterdata.domain.BizBaseApcDO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseApcDTO;
import com.microservices.otmp.masterdata.manager.BizBaseApcManager;
import com.microservices.otmp.masterdata.mapper.BizBaseApcMapper;
import com.microservices.otmp.masterdata.domain.BizBaseApcDO;
import com.microservices.otmp.masterdata.manager.BizBaseApcManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @description
 * @date 2022/6/1 18:02
 */

@Service
public class BizBaseApcManagerImpl implements BizBaseApcManager {

    @Autowired
    private BizBaseApcMapper bizBaseApcMapper;


    @Override
    public BizBaseApcDO selectBizBaseApcById(Long id) {
        return bizBaseApcMapper.selectBizBaseApcById(id);
    }

    @Override
    public List<BizBaseApcDO> selectBizBaseApcList(BizBaseApcDO bizBaseApcDO) {
        return bizBaseApcMapper.selectBizBaseApcList(bizBaseApcDO);
    }

    @Override
    public int insertBizBaseApc(BizBaseApcDO bizBaseApcDO) {
        return bizBaseApcMapper.insertBizBaseApc(bizBaseApcDO);
    }

    @Override
    public int updateBizBaseApc(BizBaseApcDO bizBaseApcDO) {
        return bizBaseApcMapper.updateBizBaseApc(bizBaseApcDO);

    }

    @Override
    public int deleteBizBaseApcByIds(Long[] ids) {
        return bizBaseApcMapper.deleteBizBaseApcByIds(ids);
    }

    @Override
    public int deleteBizBaseApcById(Long id) {
        return bizBaseApcMapper.deleteBizBaseApcById(id);
    }

}
