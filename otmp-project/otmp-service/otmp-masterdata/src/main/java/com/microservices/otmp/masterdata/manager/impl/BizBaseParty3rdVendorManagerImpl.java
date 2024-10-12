package com.microservices.otmp.masterdata.manager.impl;

import java.util.List;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBaseParty3rdVendorDO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseParty3rdVendorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.masterdata.mapper.BizBaseParty3rdVendorMapper;
import com.microservices.otmp.masterdata.domain.BizBaseParty3rdVendorDO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseParty3rdVendorDTO;
import com.microservices.otmp.masterdata.manager.IBizBaseParty3rdVendorManager;

/**
 * bizBaseParty3rdVendorManager业务层处理
 * 
 * @author lovefamily
 * @date 2022-10-12
 */
@Service
public class BizBaseParty3rdVendorManagerImpl implements IBizBaseParty3rdVendorManager
{
    @Autowired
    private BizBaseParty3rdVendorMapper bizBaseParty3rdVendorMapper;

    /**
     * 查询bizBaseParty3rdVendor
     * 
     * @param id bizBaseParty3rdVendor主键
     * @return bizBaseParty3rdVendorDO
     */
    @Override
    public BizBaseParty3rdVendorDO selectBizBaseParty3rdVendorById(Long id)
    {
        return bizBaseParty3rdVendorMapper.selectBizBaseParty3rdVendorById(id);
    }

    /**
     * 查询bizBaseParty3rdVendor列表
     *
     * @param bizBaseParty3rdVendor bizBaseParty3rdVendor
     * @return bizBaseParty3rdVendorDO
     */
    @Override
    public List<BizBaseParty3rdVendorDO> selectBizBaseParty3rdVendorList(BizBaseParty3rdVendorDTO bizBaseParty3rdVendor)
    {
        return bizBaseParty3rdVendorMapper.selectBizBaseParty3rdVendorList(bizBaseParty3rdVendor);
    }

    /**
     * 新增bizBaseParty3rdVendor
     *
     * @param bizBaseParty3rdVendor bizBaseParty3rdVendor
     * @return 结果
     */
    @Override
    public int insertBizBaseParty3rdVendor(BizBaseParty3rdVendorDO bizBaseParty3rdVendor)
    {
        bizBaseParty3rdVendor.setCreateTime(DateUtils.getNowDate());
        return bizBaseParty3rdVendorMapper.insertBizBaseParty3rdVendor (bizBaseParty3rdVendor);
    }

    /**
     * 修改bizBaseParty3rdVendor
     *
     * @param bizBaseParty3rdVendor  bizBaseParty3rdVendor
     * @return 结果
     */
    @Override
    public int updateBizBaseParty3rdVendor(BizBaseParty3rdVendorDO bizBaseParty3rdVendor)
    {
        bizBaseParty3rdVendor.setUpdateTime(DateUtils.getNowDate());
        return bizBaseParty3rdVendorMapper.updateBizBaseParty3rdVendor (bizBaseParty3rdVendor);
    }

    /**
     * 批量删除bizBaseParty3rdVendor
     * 
     * @param ids 需要删除的bizBaseParty3rdVendor主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseParty3rdVendorByIds(Long[] ids)
    {
        return bizBaseParty3rdVendorMapper.deleteBizBaseParty3rdVendorByIds(ids);
    }

    /**
     * 删除bizBaseParty3rdVendor信息
     * 
     * @param id bizBaseParty3rdVendor主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseParty3rdVendorById(Long id)
    {
        return bizBaseParty3rdVendorMapper.deleteBizBaseParty3rdVendorById(id);
    }
}
