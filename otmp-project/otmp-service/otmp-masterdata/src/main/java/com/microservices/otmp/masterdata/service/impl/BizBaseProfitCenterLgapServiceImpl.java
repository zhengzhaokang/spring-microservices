package com.microservices.otmp.masterdata.service.impl;

import java.util.List;
import java.util.ArrayList;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.dto.BizBaseProfitCenterLgapDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseProfitCenterLgapDO;
import com.microservices.otmp.masterdata.manager.IBizBaseProfitCenterLgapManager;
import com.microservices.otmp.masterdata.service.IBizBaseProfitCenterLgapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * ProfitCenter Table from LGAPService业务层处理
 * 
 * @author lovefamily
 * @date 2023-07-01
 */
@Service
public class BizBaseProfitCenterLgapServiceImpl implements IBizBaseProfitCenterLgapService
{
    private static final Logger log = LoggerFactory.getLogger(BizBaseProfitCenterLgapServiceImpl.class);

    @Autowired
    private IBizBaseProfitCenterLgapManager bizBaseProfitCenterLgapManager;

    /**
     * 查询ProfitCenter Table from LGAP
     * 
     * @param id ProfitCenter Table from LGAP主键
     * @return ProfitCenter Table from LGAPDTO
     */
    @Override
    public BizBaseProfitCenterLgapDTO selectBizBaseProfitCenterLgapById(Long id)
    {
        log.info("根据ID查询ProfitCenter Table from LGAP对象");
        BizBaseProfitCenterLgapDO bizBaseProfitCenterLgapDO =  bizBaseProfitCenterLgapManager.selectBizBaseProfitCenterLgapById(id);
        BizBaseProfitCenterLgapDTO bizBaseProfitCenterLgapDTO = new BizBaseProfitCenterLgapDTO();
        BeanUtils.copyProperties(bizBaseProfitCenterLgapDO, bizBaseProfitCenterLgapDTO);
        return bizBaseProfitCenterLgapDTO;
    }

    /**
     * 查询ProfitCenter Table from LGAP列表
     *
     * @param bizBaseProfitCenterLgap ProfitCenter Table from LGAP
     * @return ProfitCenter Table from LGAPDTO
     */
    @Override
    public List<BizBaseProfitCenterLgapDTO> selectBizBaseProfitCenterLgapList(BizBaseProfitCenterLgapDTO bizBaseProfitCenterLgap)
    {

        List<BizBaseProfitCenterLgapDO> bizBaseProfitCenterLgapDOS =
                    bizBaseProfitCenterLgapManager.selectBizBaseProfitCenterLgapList(bizBaseProfitCenterLgap);
        List<BizBaseProfitCenterLgapDTO> bizBaseProfitCenterLgapList = new ArrayList<>(bizBaseProfitCenterLgapDOS.size());
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(bizBaseProfitCenterLgapDOS, bizBaseProfitCenterLgapList, BizBaseProfitCenterLgapDTO.class);
        return bizBaseProfitCenterLgapList;

    }

    /**
     * 新增ProfitCenter Table from LGAP
     * 
     * @param bizBaseProfitCenterLgap ProfitCenter Table from LGAP
     * @return 结果
     */
    @Override
    public int insertBizBaseProfitCenterLgap(BizBaseProfitCenterLgapDTO bizBaseProfitCenterLgap)
    {
        bizBaseProfitCenterLgap.setCreateTime(DateUtils.getNowDate());
        BizBaseProfitCenterLgapDO bizBaseProfitCenterLgapDO =new  BizBaseProfitCenterLgapDO();
        BeanUtils.copyProperties(bizBaseProfitCenterLgap, bizBaseProfitCenterLgapDO);
        return bizBaseProfitCenterLgapManager.insertBizBaseProfitCenterLgap(bizBaseProfitCenterLgapDO);
    }

    /**
     * 修改ProfitCenter Table from LGAP
     * 
     * @param bizBaseProfitCenterLgap ProfitCenter Table from LGAP
     * @return 结果
     */
    @Override
    public int updateBizBaseProfitCenterLgap(BizBaseProfitCenterLgapDTO bizBaseProfitCenterLgap)
    {
        bizBaseProfitCenterLgap.setUpdateTime(DateUtils.getNowDate());
       BizBaseProfitCenterLgapDO bizBaseProfitCenterLgapDO =new  BizBaseProfitCenterLgapDO();
        BeanUtils.copyProperties(bizBaseProfitCenterLgap, bizBaseProfitCenterLgapDO);
        return bizBaseProfitCenterLgapManager.updateBizBaseProfitCenterLgap(bizBaseProfitCenterLgapDO);
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
        return bizBaseProfitCenterLgapManager.deleteBizBaseProfitCenterLgapByIds(ids);
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
        return bizBaseProfitCenterLgapManager.deleteBizBaseProfitCenterLgapById(id);
    }

    /**
     * 校验ProfitCenter Code是否存在
     *
     * @param profitCenterCode ProfitCenter Code
     * @return 结果
     */
    @Override
    public ResultDTO<Object> checkProfitCenterCode(String profitCenterCode) {
        BizBaseProfitCenterLgapDTO bizBaseProfitCenterLgapDTO = new BizBaseProfitCenterLgapDTO();
        bizBaseProfitCenterLgapDTO.setProfitCenterCode(profitCenterCode);
        bizBaseProfitCenterLgapDTO.setStatus("Y");
        List<BizBaseProfitCenterLgapDO> bizBaseProfitCenterLgapDOS = bizBaseProfitCenterLgapManager.selectBizBaseProfitCenterLgapList(bizBaseProfitCenterLgapDTO);
        if (bizBaseProfitCenterLgapDOS.size() > 0) {
            return ResultDTO.success();
        }
        throw new OtmpException("Profit Center not exist in ECC");
    }
}
