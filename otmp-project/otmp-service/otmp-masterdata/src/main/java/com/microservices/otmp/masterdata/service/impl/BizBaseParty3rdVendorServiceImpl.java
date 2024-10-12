package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBaseParty3rdVendorDO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseParty3rdVendorDTO;
import com.microservices.otmp.masterdata.manager.IBizBaseParty3rdVendorManager;
import com.microservices.otmp.masterdata.service.IBizBaseParty3rdVendorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * bizBaseParty3rdVendorService业务层处理
 * 
 * @author lovefamily
 * @date 2022-10-12
 */
@Service
public class BizBaseParty3rdVendorServiceImpl implements IBizBaseParty3rdVendorService
{
    @Autowired
    private IBizBaseParty3rdVendorManager bizBaseParty3rdVendorManager;

    /**
     * 查询bizBaseParty3rdVendor
     * 
     * @param id bizBaseParty3rdVendor主键
     * @return bizBaseParty3rdVendorDTO
     */
    @Override
    public BizBaseParty3rdVendorDTO selectBizBaseParty3rdVendorById(Long id)
    {
         BizBaseParty3rdVendorDO bizBaseParty3rdVendorDO =  bizBaseParty3rdVendorManager.selectBizBaseParty3rdVendorById(id);
         BizBaseParty3rdVendorDTO bizBaseParty3rdVendorDTO = new BizBaseParty3rdVendorDTO();
         BeanUtils.copyProperties(bizBaseParty3rdVendorDO, bizBaseParty3rdVendorDTO);
        return bizBaseParty3rdVendorDTO;
    }

    /**
     * 查询bizBaseParty3rdVendor列表
     *
     * @param bizBaseParty3rdVendor bizBaseParty3rdVendor
     * @return bizBaseParty3rdVendorDTO
     */
    @Override
    public List<BizBaseParty3rdVendorDTO> selectBizBaseParty3rdVendorList(BizBaseParty3rdVendorDTO bizBaseParty3rdVendor)
    {

        List<BizBaseParty3rdVendorDO> bizBaseParty3rdVendorDOS =
                    bizBaseParty3rdVendorManager.selectBizBaseParty3rdVendorList(bizBaseParty3rdVendor);
        List<BizBaseParty3rdVendorDTO> bizBaseParty3rdVendorList = new ArrayList<>(bizBaseParty3rdVendorDOS.size());
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(bizBaseParty3rdVendorDOS, bizBaseParty3rdVendorList, BizBaseParty3rdVendorDTO.class);
        return bizBaseParty3rdVendorList;

    }

    /**
     * 新增bizBaseParty3rdVendor
     * 
     * @param  bizBaseParty3rdVendor
     * @return 结果
     */
    @Override
    public int insertBizBaseParty3rdVendor(BizBaseParty3rdVendorDTO bizBaseParty3rdVendor)
    {
        bizBaseParty3rdVendor.setCreateTime(DateUtils.getNowDate());
        BizBaseParty3rdVendorDO bizBaseParty3rdVendorDO =new  BizBaseParty3rdVendorDO();
        BeanUtils.copyProperties(bizBaseParty3rdVendor, bizBaseParty3rdVendorDO);
        return bizBaseParty3rdVendorManager.insertBizBaseParty3rdVendor(bizBaseParty3rdVendorDO);
    }

    /**
     * 修改bizBaseParty3rdVendor
     * 
     * @param  bizBaseParty3rdVendor
     * @return 结果
     */
    @Override
    public int updateBizBaseParty3rdVendor(BizBaseParty3rdVendorDTO bizBaseParty3rdVendor)
    {
        bizBaseParty3rdVendor.setUpdateTime(DateUtils.getNowDate());
       BizBaseParty3rdVendorDO bizBaseParty3rdVendorDO =new  BizBaseParty3rdVendorDO();
        BeanUtils.copyProperties(bizBaseParty3rdVendor, bizBaseParty3rdVendorDO);
        return bizBaseParty3rdVendorManager.updateBizBaseParty3rdVendor(bizBaseParty3rdVendorDO);
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
        return bizBaseParty3rdVendorManager.deleteBizBaseParty3rdVendorByIds(ids);
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
        return bizBaseParty3rdVendorManager.deleteBizBaseParty3rdVendorById(id);
    }

    @Override
    public List<BizBaseParty3rdVendorDTO> remoteList(BizBaseParty3rdVendorDTO bizBaseParty3rdVendor) {
        List<BizBaseParty3rdVendorDO> bizBaseParty3rdVendorDOS =
                bizBaseParty3rdVendorManager.selectBizBaseParty3rdVendorList(bizBaseParty3rdVendor);
        List<BizBaseParty3rdVendorDTO> bizBaseParty3rdVendorList = new ArrayList<>(bizBaseParty3rdVendorDOS.size());
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(bizBaseParty3rdVendorDOS, bizBaseParty3rdVendorList, BizBaseParty3rdVendorDTO.class);
        return bizBaseParty3rdVendorList;
    }
}
