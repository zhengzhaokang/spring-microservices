package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.dto.BizBaseMbgCustomerDrmTomsTofiDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseMbgCustomerDrmTomsTofiDO;
import com.microservices.otmp.masterdata.manager.IBizBaseMbgCustomerDrmTomsTofiManager;
import com.microservices.otmp.masterdata.service.IBizBaseMbgCustomerDrmTomsTofiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * BizBaseMbgCustomerDrmTomsTofiService业务层处理
 *
 * @author lovefamily
 * @date 2023-03-01
 */
@Service
public class BizBaseMbgCustomerDrmTomsTofiServiceImpl implements IBizBaseMbgCustomerDrmTomsTofiService {
    private static final Logger log = LoggerFactory.getLogger(BizBaseMbgCustomerDrmTomsTofiServiceImpl.class);

    @Autowired
    private IBizBaseMbgCustomerDrmTomsTofiManager bizBaseMbgCustomerDrmTomsTofiManager;

    /**
     * 查询BizBaseMbgCustomerDrmTomsTofi
     *
     * @param id BizBaseMbgCustomerDrmTomsTofi主键
     * @return BizBaseMbgCustomerDrmTomsTofiDTO
     */
    @Override
    public BizBaseMbgCustomerDrmTomsTofiDTO selectBizBaseMbgCustomerDrmTomsTofiById(Long id) {
        BizBaseMbgCustomerDrmTomsTofiDO bizBaseMbgCustomerDrmTomsTofiDO = bizBaseMbgCustomerDrmTomsTofiManager.selectBizBaseMbgCustomerDrmTomsTofiById(id);
        BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofiDTO = new BizBaseMbgCustomerDrmTomsTofiDTO();
        BeanUtils.copyProperties(bizBaseMbgCustomerDrmTomsTofiDO, bizBaseMbgCustomerDrmTomsTofiDTO);
        return bizBaseMbgCustomerDrmTomsTofiDTO;
    }

    /**
     * 查询BizBaseMbgCustomerDrmTomsTofi列表
     *
     * @param bizBaseMbgCustomerDrmTomsTofi BizBaseMbgCustomerDrmTomsTofi
     * @return
     */
    @Override
    public List<BizBaseMbgCustomerDrmTomsTofiDTO> selectBizBaseMbgCustomerDrmTomsTofiList(BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi) {

        List<BizBaseMbgCustomerDrmTomsTofiDO> bizBaseMbgCustomerDrmTomsTofiDOS =
                bizBaseMbgCustomerDrmTomsTofiManager.selectBizBaseMbgCustomerDrmTomsTofiList(bizBaseMbgCustomerDrmTomsTofi);
        List<BizBaseMbgCustomerDrmTomsTofiDTO> bizBaseMbgCustomerDrmTomsTofiList = new ArrayList<>(bizBaseMbgCustomerDrmTomsTofiDOS.size());
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(bizBaseMbgCustomerDrmTomsTofiDOS, bizBaseMbgCustomerDrmTomsTofiList, BizBaseMbgCustomerDrmTomsTofiDTO.class);
        return bizBaseMbgCustomerDrmTomsTofiList;

    }

    /**
     * 新增BizBaseMbgCustomerDrmTomsTofi
     *
     * @param bizBaseMbgCustomerDrmTomsTofiDTO BizBaseMbgCustomerDrmTomsTofi
     * @return 结果
     */
    @Override
    public int insertBizBaseMbgCustomerDrmTomsTofi(BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi) {
        bizBaseMbgCustomerDrmTomsTofi.setCreateTime(DateUtils.getNowDate());
        BizBaseMbgCustomerDrmTomsTofiDO bizBaseMbgCustomerDrmTomsTofiDO = new BizBaseMbgCustomerDrmTomsTofiDO();
        BeanUtils.copyProperties(bizBaseMbgCustomerDrmTomsTofi, bizBaseMbgCustomerDrmTomsTofiDO);
        return bizBaseMbgCustomerDrmTomsTofiManager.insertBizBaseMbgCustomerDrmTomsTofi(bizBaseMbgCustomerDrmTomsTofiDO);
    }

    /**
     * 修改BizBaseMbgCustomerDrmTomsTofi
     *
     * @param bizBaseMbgCustomerDrmTomsTofiDTO BizBaseMbgCustomerDrmTomsTofi
     * @return 结果
     */
    @Override
    public int updateBizBaseMbgCustomerDrmTomsTofi(BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi) {
        bizBaseMbgCustomerDrmTomsTofi.setUpdateTime(DateUtils.getNowDate());
        BizBaseMbgCustomerDrmTomsTofiDO bizBaseMbgCustomerDrmTomsTofiDO = new BizBaseMbgCustomerDrmTomsTofiDO();
        BeanUtils.copyProperties(bizBaseMbgCustomerDrmTomsTofi, bizBaseMbgCustomerDrmTomsTofiDO);
        return bizBaseMbgCustomerDrmTomsTofiManager.updateBizBaseMbgCustomerDrmTomsTofi(bizBaseMbgCustomerDrmTomsTofiDO);
    }

    /**
     * 批量删除BizBaseMbgCustomerDrmTomsTofi
     *
     * @param ids 需要删除的BizBaseMbgCustomerDrmTomsTofi主键
     * @return 结果
     */
    @Override
    @CacheEvict(value = "customerNumber", allEntries = true)
    public int deleteBizBaseMbgCustomerDrmTomsTofiByIds(Long[] ids) {
        return bizBaseMbgCustomerDrmTomsTofiManager.deleteBizBaseMbgCustomerDrmTomsTofiByIds(ids);
    }

    /**
     * 删除BizBaseMbgCustomerDrmTomsTofi信息
     *
     * @param id BizBaseMbgCustomerDrmTomsTofi主键
     * @return 结果
     */
    @Override
    @CacheEvict(value = "customerNumber", allEntries = true)
    public int deleteBizBaseMbgCustomerDrmTomsTofiById(Long id) {
        return bizBaseMbgCustomerDrmTomsTofiManager.deleteBizBaseMbgCustomerDrmTomsTofiById(id);
    }

    /**
     * 根据customerNumber获取BizBaseMbgCustomerDrmTomsTofi信息
     *
     * @param customerNumber
     * @return
     */
    @Override
    @Cacheable(value = "customerNumber", key = "#customerNumber+'_'+#businessGroup")
    public BizBaseMbgCustomerDrmTomsTofiDTO getByCustomerNumber(String customerNumber, String businessGroup) {
        log.info("根据customerNumber获取BizBaseMbgCustomerDrmTomsTofi信息,入参:{}", customerNumber);
        BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofiDTO = new BizBaseMbgCustomerDrmTomsTofiDTO();
        BizBaseMbgCustomerDrmTomsTofiDO byCustomerNumber = bizBaseMbgCustomerDrmTomsTofiManager.getByCustomerNumber(customerNumber, businessGroup);
        if (null == byCustomerNumber) {
            return null;
        }
        BeanUtils.copyProperties(byCustomerNumber, bizBaseMbgCustomerDrmTomsTofiDTO);
        return bizBaseMbgCustomerDrmTomsTofiDTO;
    }

    /**
     * 获取dropDownList
     *
     * @param bizBaseMbgCustomerDrmTomsTofi
     * @return
     */
    @Override
    public List<BizBaseMbgCustomerDrmTomsTofiDO> getDropDownList(BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi) {
        List<BizBaseMbgCustomerDrmTomsTofiDO> list = null;
        if (CommonUtils.stringIsNotEmpty(bizBaseMbgCustomerDrmTomsTofi.getTempField())) {
            if (bizBaseMbgCustomerDrmTomsTofi.getTempField().equals("key_account")) {
                list = bizBaseMbgCustomerDrmTomsTofiManager.getDropDownListByKeyAccount(bizBaseMbgCustomerDrmTomsTofi);
            } else {
                list = bizBaseMbgCustomerDrmTomsTofiManager.getDropDownListByCustomerGroup(bizBaseMbgCustomerDrmTomsTofi);
            }
        }
        return list;
    }
}
