package com.microservices.otmp.masterdata.manager.impl;

import java.util.List;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.dto.BizBaseProfitCenterLgapDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseProfitCenterLgapDO;
import com.microservices.otmp.masterdata.manager.IBizBaseProfitCenterLgapManager;
import com.microservices.otmp.masterdata.mapper.BizBaseProfitCenterLgapMapper;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.dto.BizBaseProfitCenterLgapDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ProfitCenter Table from LGAPManager业务层处理
 * 
 * @author lovefamily
 * @date 2023-07-01
 */
@Service
public class BizBaseProfitCenterLgapManagerImpl implements IBizBaseProfitCenterLgapManager
{
    private static final Logger log = LoggerFactory.getLogger(BizBaseProfitCenterLgapManagerImpl.class);

    @Autowired
    private BizBaseProfitCenterLgapMapper bizBaseProfitCenterLgapMapper;

    /**
     * 查询ProfitCenter Table from LGAP
     * 
     * @param id ProfitCenter Table from LGAP主键
     * @return ProfitCenter Table from LGAPDO
     */
    @Override
    public BizBaseProfitCenterLgapDO selectBizBaseProfitCenterLgapById(Long id)
    {
        log.info("根据ID查询ProfitCenter Table from LGAP对象");
        return bizBaseProfitCenterLgapMapper.selectBizBaseProfitCenterLgapById(id);
    }

    /**
     * 查询ProfitCenter Table from LGAP列表
     *
     * @param bizBaseProfitCenterLgap ProfitCenter Table from LGAP
     * @return ProfitCenter Table from LGAPDO
     */
    @Override
    public List<BizBaseProfitCenterLgapDO> selectBizBaseProfitCenterLgapList(BizBaseProfitCenterLgapDTO bizBaseProfitCenterLgap)
    {
        return bizBaseProfitCenterLgapMapper.selectBizBaseProfitCenterLgapList(bizBaseProfitCenterLgap);
    }

    /**
     * 新增ProfitCenter Table from LGAP
     *
     * @param bizBaseProfitCenterLgap ProfitCenter Table from LGAP
     * @return 结果
     */
    @Override
    public int insertBizBaseProfitCenterLgap(BizBaseProfitCenterLgapDO bizBaseProfitCenterLgap)
    {
        bizBaseProfitCenterLgap.setCreateTime(DateUtils.getNowDate());
        return bizBaseProfitCenterLgapMapper.insertBizBaseProfitCenterLgap (bizBaseProfitCenterLgap);
    }

    /**
     * 修改ProfitCenter Table from LGAP
     *
     * @param bizBaseProfitCenterLgap  ProfitCenter Table from LGAP
     * @return 结果
     */
    @Override
    public int updateBizBaseProfitCenterLgap(BizBaseProfitCenterLgapDO bizBaseProfitCenterLgap)
    {
        bizBaseProfitCenterLgap.setUpdateTime(DateUtils.getNowDate());
        return bizBaseProfitCenterLgapMapper.updateBizBaseProfitCenterLgap (bizBaseProfitCenterLgap);
    }

    /**
     * 批量删除ProfitCenter Table from LGAP
     * 
     * @param ids 需要删除的ProfitCenter Table from LGAP主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseProfitCenterLgapByIds(Long[] ids)
    {
        return bizBaseProfitCenterLgapMapper.deleteBizBaseProfitCenterLgapByIds(ids);
    }

    /**
     * 删除ProfitCenter Table from LGAP信息
     * 
     * @param id ProfitCenter Table from LGAP主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseProfitCenterLgapById(Long id)
    {
        return bizBaseProfitCenterLgapMapper.deleteBizBaseProfitCenterLgapById(id);
    }
}
