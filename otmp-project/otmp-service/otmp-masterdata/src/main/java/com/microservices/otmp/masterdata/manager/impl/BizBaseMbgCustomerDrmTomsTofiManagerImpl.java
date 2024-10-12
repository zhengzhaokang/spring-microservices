package com.microservices.otmp.masterdata.manager.impl;

import java.util.List;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.entity.BizBaseMbgCustomerDrmTomsTofiDO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseMbgCustomerDrmTomsTofiDTO;
import com.microservices.otmp.masterdata.manager.IBizBaseMbgCustomerDrmTomsTofiManager;
import com.microservices.otmp.masterdata.mapper.BizBaseMbgCustomerDrmTomsTofiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BizBaseMbgCustomerDrmTomsTofiManager业务层处理
 * 
 * @author lovefamily
 * @date 2023-03-01
 */
@Service
public class BizBaseMbgCustomerDrmTomsTofiManagerImpl implements IBizBaseMbgCustomerDrmTomsTofiManager
{
    private static final Logger log = LoggerFactory.getLogger(BizBaseMbgCustomerDrmTomsTofiManagerImpl.class);

    @Autowired
    private BizBaseMbgCustomerDrmTomsTofiMapper bizBaseMbgCustomerDrmTomsTofiMapper;

    /**
     * 查询BizBaseMbgCustomerDrmTomsTofi
     * 
     * @param id BizBaseMbgCustomerDrmTomsTofi主键
     * @return BizBaseMbgCustomerDrmTomsTofiDO
     */
    @Override
    public BizBaseMbgCustomerDrmTomsTofiDO selectBizBaseMbgCustomerDrmTomsTofiById(Long id)
    {
        return bizBaseMbgCustomerDrmTomsTofiMapper.selectBizBaseMbgCustomerDrmTomsTofiById(id);
    }

    /**
     * 查询BizBaseMbgCustomerDrmTomsTofi列表
     *
     * @param bizBaseMbgCustomerDrmTomsTofi BizBaseMbgCustomerDrmTomsTofi
     * @return BizBaseMbgCustomerDrmTomsTofiDO
     */
    @Override
    public List<BizBaseMbgCustomerDrmTomsTofiDO> selectBizBaseMbgCustomerDrmTomsTofiList(BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi)
    {
        return bizBaseMbgCustomerDrmTomsTofiMapper.selectBizBaseMbgCustomerDrmTomsTofiList(bizBaseMbgCustomerDrmTomsTofi);
    }

    /**
     * 新增BizBaseMbgCustomerDrmTomsTofi
     *
     * @param bizBaseMbgCustomerDrmTomsTofi BizBaseMbgCustomerDrmTomsTofi
     * @return 结果
     */
    @Override
    public int insertBizBaseMbgCustomerDrmTomsTofi(BizBaseMbgCustomerDrmTomsTofiDO bizBaseMbgCustomerDrmTomsTofi)
    {
        bizBaseMbgCustomerDrmTomsTofi.setCreateTime(DateUtils.getNowDate());
        return bizBaseMbgCustomerDrmTomsTofiMapper.insertBizBaseMbgCustomerDrmTomsTofi (bizBaseMbgCustomerDrmTomsTofi);
    }

    /**
     * 修改BizBaseMbgCustomerDrmTomsTofi
     *
     * @param bizBaseMbgCustomerDrmTomsTofi  BizBaseMbgCustomerDrmTomsTofi
     * @return 结果
     */
    @Override
    public int updateBizBaseMbgCustomerDrmTomsTofi(BizBaseMbgCustomerDrmTomsTofiDO bizBaseMbgCustomerDrmTomsTofi)
    {
        bizBaseMbgCustomerDrmTomsTofi.setUpdateTime(DateUtils.getNowDate());
        return bizBaseMbgCustomerDrmTomsTofiMapper.updateBizBaseMbgCustomerDrmTomsTofi (bizBaseMbgCustomerDrmTomsTofi);
    }

    /**
     * 批量删除BizBaseMbgCustomerDrmTomsTofi
     * 
     * @param ids 需要删除的BizBaseMbgCustomerDrmTomsTofi主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseMbgCustomerDrmTomsTofiByIds(Long[] ids)
    {
        return bizBaseMbgCustomerDrmTomsTofiMapper.deleteBizBaseMbgCustomerDrmTomsTofiByIds(ids);
    }

    /**
     * 删除BizBaseMbgCustomerDrmTomsTofi信息
     * 
     * @param id BizBaseMbgCustomerDrmTomsTofi主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseMbgCustomerDrmTomsTofiById(Long id)
    {
        return bizBaseMbgCustomerDrmTomsTofiMapper.deleteBizBaseMbgCustomerDrmTomsTofiById(id);
    }

    /**
     * 根据customerNumber获取BizBaseMbgCustomerDrmTomsTofi信息
     * @param customerNumber
     * @return
     */
    @Override
    public BizBaseMbgCustomerDrmTomsTofiDO getByCustomerNumber(String customerNumber,String businessGroup) {
        log.info("根据customerNumber获取BizBaseMbgCustomerDrmTomsTofi信息,入参:{}", customerNumber);
        return bizBaseMbgCustomerDrmTomsTofiMapper.getByCustomerNumber(customerNumber, businessGroup);
    }

    /**
     * 获取dropDownList
     * @param bizBaseMbgCustomerDrmTomsTofi
     * @return
     */
    @Override
    public List<BizBaseMbgCustomerDrmTomsTofiDO> getDropDownListByKeyAccount(BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi) {
        return bizBaseMbgCustomerDrmTomsTofiMapper.getDropDownListByKeyAccount(bizBaseMbgCustomerDrmTomsTofi);
    }

    /**
     * 获取dropDownList
     * @param bizBaseMbgCustomerDrmTomsTofi
     * @return
     */
    @Override
    public List<BizBaseMbgCustomerDrmTomsTofiDO> getDropDownListByCustomerGroup(BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi) {
        return bizBaseMbgCustomerDrmTomsTofiMapper.getDropDownListByCustomerGroup(bizBaseMbgCustomerDrmTomsTofi);
    }
}
