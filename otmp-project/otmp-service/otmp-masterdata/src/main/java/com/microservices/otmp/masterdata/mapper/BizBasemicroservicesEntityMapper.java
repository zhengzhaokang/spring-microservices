package com.microservices.otmp.masterdata.mapper;

import com.microservices.otmp.masterdata.domain.dto.BizBasemicroservicesEntityDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBasemicroservicesEntityDO;
import com.microservices.otmp.masterdata.domain.dto.BizBasemicroservicesEntityDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBasemicroservicesEntityDO;

import java.util.List;


/**
 * microservices Entity Table From ECCMapper接口
 * 
 * @author lovefamily
 * @date 2023-03-21
 */
public interface BizBasemicroservicesEntityMapper
{
    /**
     * 查询microservices Entity Table From ECC
     * 
     * @param id microservices Entity Table From ECC主键
     * @return microservices Entity Table From ECC
     */
    public BizBasemicroservicesEntityDO selectBizBasemicroservicesEntityById(Long id);

    /**
     * 查询microservices Entity Table From ECC列表
     * 
     * @param bizBasemicroservicesEntityDTO microservices Entity Table From ECC
     * @return microservices Entity Table From ECC集合
     */
    public List<BizBasemicroservicesEntityDO> selectBizBasemicroservicesEntityList(BizBasemicroservicesEntityDTO bizBasemicroservicesEntity);

    /**
     * 新增microservices Entity Table From ECC
     * 
     * @param bizBasemicroservicesEntityDO microservices Entity Table From ECC
     * @return 结果
     */
    public int insertBizBasemicroservicesEntity(BizBasemicroservicesEntityDO bizBasemicroservicesEntity);

    /**
     * 修改microservices Entity Table From ECC
     * 
     * @param bizBasemicroservicesEntityDO microservices Entity Table From ECC
     * @return 结果
     */
    public int updateBizBasemicroservicesEntity (BizBasemicroservicesEntityDO bizBasemicroservicesEntity);

    /**
     * 删除microservices Entity Table From ECC
     * 
     * @param id microservices Entity Table From ECC主键
     * @return 结果
     */
    public int deleteBizBasemicroservicesEntityById(Long id);

    /**
     * 批量删除microservices Entity Table From ECC
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBasemicroservicesEntityByIds(Long[] ids);
}
