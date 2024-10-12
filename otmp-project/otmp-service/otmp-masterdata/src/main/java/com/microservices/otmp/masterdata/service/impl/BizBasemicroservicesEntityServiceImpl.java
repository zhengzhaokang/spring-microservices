package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.dto.BizBasemicroservicesEntityDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBasemicroservicesEntityDO;
import com.microservices.otmp.masterdata.manager.IBizBasemicroservicesEntityManager;
import com.microservices.otmp.masterdata.service.IBizBasemicroservicesEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * microservices Entity Table From ECCService业务层处理
 * 
 * @author lovefamily
 * @date 2023-03-21
 */
@Service
public class BizBasemicroservicesEntityServiceImpl implements IBizBasemicroservicesEntityService
{
    private static final Logger log = LoggerFactory.getLogger(BizBasemicroservicesEntityServiceImpl.class);

    @Autowired
    private IBizBasemicroservicesEntityManager bizBasemicroservicesEntityManager;

    /**
     * 查询microservices Entity Table From ECC
     * 
     * @param id microservices Entity Table From ECC主键
     * @return microservices Entity Table From ECCDTO
     */
    @Override
    public BizBasemicroservicesEntityDTO selectBizBasemicroservicesEntityById(Long id)
    {
         BizBasemicroservicesEntityDO bizBasemicroservicesEntityDO =  bizBasemicroservicesEntityManager.selectBizBasemicroservicesEntityById(id);
         BizBasemicroservicesEntityDTO bizBasemicroservicesEntityDTO = new BizBasemicroservicesEntityDTO();
         BeanUtils.copyProperties(bizBasemicroservicesEntityDO, bizBasemicroservicesEntityDTO);
        return bizBasemicroservicesEntityDTO;
    }

    /**
     * 查询microservices Entity Table From ECC列表
     *
     * @param bizBasemicroservicesEntity microservices Entity Table From ECC
     * @return microservices Entity Table From ECCDTO
     */
    @Override
    public List<BizBasemicroservicesEntityDTO> selectBizBasemicroservicesEntityList(BizBasemicroservicesEntityDTO bizBasemicroservicesEntity)
    {

        List<BizBasemicroservicesEntityDO> bizBasemicroservicesEntityDOS =
                    bizBasemicroservicesEntityManager.selectBizBasemicroservicesEntityList(bizBasemicroservicesEntity);
        List<BizBasemicroservicesEntityDTO> bizBasemicroservicesEntityList = new ArrayList<>(bizBasemicroservicesEntityDOS.size());
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(bizBasemicroservicesEntityDOS, bizBasemicroservicesEntityList, BizBasemicroservicesEntityDTO.class);
        return bizBasemicroservicesEntityList;

    }

    /**
     * 新增microservices Entity Table From ECC
     * 
     * @param bizBasemicroservicesEntityDTO microservices Entity Table From ECC
     * @return 结果
     */
    @Override
    public int insertBizBasemicroservicesEntity(BizBasemicroservicesEntityDTO bizBasemicroservicesEntity)
    {
        bizBasemicroservicesEntity.setCreateTime(DateUtils.getNowDate());
        BizBasemicroservicesEntityDO bizBasemicroservicesEntityDO =new  BizBasemicroservicesEntityDO();
        BeanUtils.copyProperties(bizBasemicroservicesEntity, bizBasemicroservicesEntityDO);
        return bizBasemicroservicesEntityManager.insertBizBasemicroservicesEntity(bizBasemicroservicesEntityDO);
    }

    /**
     * 修改microservices Entity Table From ECC
     * 
     * @param bizBasemicroservicesEntityDTO microservices Entity Table From ECC
     * @return 结果
     */
    @Override
    public int updateBizBasemicroservicesEntity(BizBasemicroservicesEntityDTO bizBasemicroservicesEntity)
    {
        bizBasemicroservicesEntity.setUpdateTime(DateUtils.getNowDate());
       BizBasemicroservicesEntityDO bizBasemicroservicesEntityDO =new  BizBasemicroservicesEntityDO();
        BeanUtils.copyProperties(bizBasemicroservicesEntity, bizBasemicroservicesEntityDO);
        return bizBasemicroservicesEntityManager.updateBizBasemicroservicesEntity(bizBasemicroservicesEntityDO);
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
        return bizBasemicroservicesEntityManager.deleteBizBasemicroservicesEntityByIds(ids);
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
        return bizBasemicroservicesEntityManager.deleteBizBasemicroservicesEntityById(id);
    }
}
