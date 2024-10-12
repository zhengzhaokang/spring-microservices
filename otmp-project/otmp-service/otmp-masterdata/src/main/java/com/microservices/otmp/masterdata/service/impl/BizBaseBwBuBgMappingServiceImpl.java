package com.microservices.otmp.masterdata.service.impl;

import java.util.List;
import java.util.ArrayList;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.dto.BizBaseBwBuBgMappingDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseBwBuBgMappingDO;
import com.microservices.otmp.masterdata.manager.IBizBaseBwBuBgMappingManager;
import com.microservices.otmp.masterdata.service.IBizBaseBwBuBgMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * bwBuBgMappingService业务层处理
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
@Service
public class BizBaseBwBuBgMappingServiceImpl implements IBizBaseBwBuBgMappingService
{
    @Autowired
    private IBizBaseBwBuBgMappingManager bizBaseBwBuBgMappingManager;

    /**
     * 查询bwBuBgMapping
     * 
     * @param id bwBuBgMapping主键
     * @return bwBuBgMappingDTO
     */
    @Override
    public BizBaseBwBuBgMappingDTO selectBizBaseBwBuBgMappingById(Long id)
    {
         BizBaseBwBuBgMappingDO bizBaseBwBuBgMappingDO =  bizBaseBwBuBgMappingManager.selectBizBaseBwBuBgMappingById(id);
         BizBaseBwBuBgMappingDTO bizBaseBwBuBgMappingDTO = new BizBaseBwBuBgMappingDTO();
         BeanUtils.copyProperties(bizBaseBwBuBgMappingDO, bizBaseBwBuBgMappingDTO);
        return bizBaseBwBuBgMappingDTO;
    }

    /**
     * 查询bwBuBgMapping列表
     *
     * @param bizBaseBwBuBgMapping bwBuBgMapping
     * @return bwBuBgMappingDTO
     */
    @Override
    public List<BizBaseBwBuBgMappingDTO> selectBizBaseBwBuBgMappingList(BizBaseBwBuBgMappingDTO bizBaseBwBuBgMapping)
    {

        List<BizBaseBwBuBgMappingDO> bizBaseBwBuBgMappingDOS =
                    bizBaseBwBuBgMappingManager.selectBizBaseBwBuBgMappingList(bizBaseBwBuBgMapping);
        List<BizBaseBwBuBgMappingDTO> bizBaseBwBuBgMappingList = new ArrayList<>(bizBaseBwBuBgMappingDOS.size());
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(bizBaseBwBuBgMappingDOS, bizBaseBwBuBgMappingList, BizBaseBwBuBgMappingDTO.class);
        return bizBaseBwBuBgMappingList;

    }

    /**
     * 新增bwBuBgMapping
     * 
     * @param bizBaseBwBuBgMapping bwBuBgMapping
     * @return 结果
     */
    @Override
    public int insertBizBaseBwBuBgMapping(BizBaseBwBuBgMappingDTO bizBaseBwBuBgMapping)
    {
        bizBaseBwBuBgMapping.setCreateTime(DateUtils.getNowDate());
        BizBaseBwBuBgMappingDO bizBaseBwBuBgMappingDO =new  BizBaseBwBuBgMappingDO();
        BeanUtils.copyProperties(bizBaseBwBuBgMapping, bizBaseBwBuBgMappingDO);
        return bizBaseBwBuBgMappingManager.insertBizBaseBwBuBgMapping(bizBaseBwBuBgMappingDO);
    }

    /**
     * 修改bwBuBgMapping
     * 
     * @param bizBaseBwBuBgMapping bwBuBgMapping
     * @return 结果
     */
    @Override
    public int updateBizBaseBwBuBgMapping(BizBaseBwBuBgMappingDTO bizBaseBwBuBgMapping)
    {
        bizBaseBwBuBgMapping.setUpdateTime(DateUtils.getNowDate());
       BizBaseBwBuBgMappingDO bizBaseBwBuBgMappingDO =new  BizBaseBwBuBgMappingDO();
        BeanUtils.copyProperties(bizBaseBwBuBgMapping, bizBaseBwBuBgMappingDO);
        return bizBaseBwBuBgMappingManager.updateBizBaseBwBuBgMapping(bizBaseBwBuBgMappingDO);
    }

    /**
     * 批量删除bwBuBgMapping
     * 
     * @param ids 需要删除的bwBuBgMapping主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseBwBuBgMappingByIds(Long[] ids)
    {
        return bizBaseBwBuBgMappingManager.deleteBizBaseBwBuBgMappingByIds(ids);
    }

    /**
     * 删除bwBuBgMapping信息
     * 
     * @param id bwBuBgMapping主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseBwBuBgMappingById(Long id)
    {
        return bizBaseBwBuBgMappingManager.deleteBizBaseBwBuBgMappingById(id);
    }

    @Override
    public int updateByKey(BizBaseBwBuBgMappingDTO bizBaseBwBuBgMapping) {
        bizBaseBwBuBgMapping.setUpdateTime(DateUtils.getNowDate());
        BizBaseBwBuBgMappingDO bizBaseBwBuBgMappingDO =new  BizBaseBwBuBgMappingDO();
        BeanUtils.copyProperties(bizBaseBwBuBgMapping, bizBaseBwBuBgMappingDO);
        return bizBaseBwBuBgMappingManager.updateByKey(bizBaseBwBuBgMappingDO);
    }

}
