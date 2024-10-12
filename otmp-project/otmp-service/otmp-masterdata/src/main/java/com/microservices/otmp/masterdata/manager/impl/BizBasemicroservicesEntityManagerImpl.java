package com.microservices.otmp.masterdata.manager.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.dto.BizBasemicroservicesEntityDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBasemicroservicesEntityDO;
import com.microservices.otmp.masterdata.manager.IBizBasemicroservicesEntityManager;
import com.microservices.otmp.masterdata.mapper.BizBasemicroservicesEntityMapper;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.dto.BizBasemicroservicesEntityDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBasemicroservicesEntityDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * microservices Entity Table From ECCManager业务层处理
 * 
 * @author lovefamily
 * @date 2023-03-21
 */
@Service
public class BizBasemicroservicesEntityManagerImpl implements IBizBasemicroservicesEntityManager
{
    private static final Logger log = LoggerFactory.getLogger(BizBasemicroservicesEntityManagerImpl.class);

    @Autowired
    private BizBasemicroservicesEntityMapper bizBasemicroservicesEntityMapper;

    /**
     * 查询microservices Entity Table From ECC
     * 
     * @param id microservices Entity Table From ECC主键
     * @return microservices Entity Table From ECCDO
     */
    @Override
    public BizBasemicroservicesEntityDO selectBizBasemicroservicesEntityById(Long id)
    {
        return bizBasemicroservicesEntityMapper.selectBizBasemicroservicesEntityById(id);
    }

    /**
     * 查询microservices Entity Table From ECC列表
     *
     * @param bizBasemicroservicesEntity microservices Entity Table From ECC
     * @return microservices Entity Table From ECCDO
     */
    @Override
    public List<BizBasemicroservicesEntityDO> selectBizBasemicroservicesEntityList(BizBasemicroservicesEntityDTO bizBasemicroservicesEntity)
    {
        return bizBasemicroservicesEntityMapper.selectBizBasemicroservicesEntityList(bizBasemicroservicesEntity);
    }

    /**
     * 新增microservices Entity Table From ECC
     *
     * @param bizBasemicroservicesEntity microservices Entity Table From ECC
     * @return 结果
     */
    @Override
    public int insertBizBasemicroservicesEntity(BizBasemicroservicesEntityDO bizBasemicroservicesEntity)
    {
        bizBasemicroservicesEntity.setCreateTime(DateUtils.getNowDate());
        return bizBasemicroservicesEntityMapper.insertBizBasemicroservicesEntity (bizBasemicroservicesEntity);
    }

    /**
     * 修改microservices Entity Table From ECC
     *
     * @param bizBasemicroservicesEntity  microservices Entity Table From ECC
     * @return 结果
     */
    @Override
    public int updateBizBasemicroservicesEntity(BizBasemicroservicesEntityDO bizBasemicroservicesEntity)
    {
        bizBasemicroservicesEntity.setUpdateTime(DateUtils.getNowDate());
        return bizBasemicroservicesEntityMapper.updateBizBasemicroservicesEntity (bizBasemicroservicesEntity);
    }

    /**
     * 批量删除microservices Entity Table From ECC
     * 
     * @param ids 需要删除的microservices Entity Table From ECC主键
     * @return 结果
     */
    @Override
    public int deleteBizBasemicroservicesEntityByIds(Long[] ids)
    {
        return bizBasemicroservicesEntityMapper.deleteBizBasemicroservicesEntityByIds(ids);
    }

    /**
     * 删除microservices Entity Table From ECC信息
     * 
     * @param id microservices Entity Table From ECC主键
     * @return 结果
     */
    @Override
    public int deleteBizBasemicroservicesEntityById(Long id)
    {
        log.info("删除microservices Entity Table From ECC信息, id:{}", id);
        return bizBasemicroservicesEntityMapper.deleteBizBasemicroservicesEntityById(id);
    }
}
