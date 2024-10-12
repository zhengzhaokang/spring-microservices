package com.microservices.otmp.masterdata.manager;

import com.microservices.otmp.masterdata.domain.dto.BizBasemicroservicesEntityDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBasemicroservicesEntityDO;

import java.util.List;


/**
 * microservices Entity Table From ECCManager接口
 * 
 * @author lovefamily
 * @date 2023-03-21
 */
public interface IBizBasemicroservicesEntityManager
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
     * @param bizBasemicroservicesEntity microservices Entity Table From ECC
     * @return microservices Entity Table From ECC集合
     */
    public List<BizBasemicroservicesEntityDO> selectBizBasemicroservicesEntityList(BizBasemicroservicesEntityDTO bizBasemicroservicesEntity);

    /**
     * 新增microservices Entity Table From ECC
     *
     * @param bizBasemicroservicesEntity microservices Entity Table From ECC
     * @return 结果
     */
    public int insertBizBasemicroservicesEntity(BizBasemicroservicesEntityDO bizBasemicroservicesEntity);

    /**
     * 修改microservices Entity Table From ECC
     *
     * @param bizBasemicroservicesEntity microservices Entity Table From ECC
     * @return 结果
     */
    public int updateBizBasemicroservicesEntity(BizBasemicroservicesEntityDO bizBasemicroservicesEntity);

    /**
     * 批量删除microservices Entity Table From ECC
     * 
     * @param ids 需要删除的microservices Entity Table From ECC主键集合
     * @return 结果
     */
    public int deleteBizBasemicroservicesEntityByIds(Long[] ids);

    /**
     * 删除microservices Entity Table From ECC信息
     * 
     * @param id microservices Entity Table From ECC主键
     * @return 结果
     */
    public int deleteBizBasemicroservicesEntityById(Long id);
}
