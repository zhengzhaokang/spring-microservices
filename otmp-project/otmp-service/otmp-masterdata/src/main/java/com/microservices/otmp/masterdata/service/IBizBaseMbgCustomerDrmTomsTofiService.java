package com.microservices.otmp.masterdata.service;

import com.microservices.otmp.masterdata.domain.dto.BizBaseMbgCustomerDrmTomsTofiDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseMbgCustomerDrmTomsTofiDO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseMbgCustomerDrmTomsTofiDTO;

import java.util.List;

/**
 * BizBaseMbgCustomerDrmTomsTofiService接口
 * 
 * @author lovefamily
 * @date 2023-03-01
 */
public interface IBizBaseMbgCustomerDrmTomsTofiService
{
    /**
     * 查询BizBaseMbgCustomerDrmTomsTofi
     * 
     * @param id BizBaseMbgCustomerDrmTomsTofi主键
     * @return BizBaseMbgCustomerDrmTomsTofiDTO
     */
    public BizBaseMbgCustomerDrmTomsTofiDTO selectBizBaseMbgCustomerDrmTomsTofiById(Long id);

    /**
     * 查询BizBaseMbgCustomerDrmTomsTofi列表
     *
     * @param bizBaseMbgCustomerDrmTomsTofi BizBaseMbgCustomerDrmTomsTofi
     * @return BizBaseMbgCustomerDrmTomsTofiDTO集合
     */
    public List<BizBaseMbgCustomerDrmTomsTofiDTO> selectBizBaseMbgCustomerDrmTomsTofiList(BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi);

    /**
     * 新增BizBaseMbgCustomerDrmTomsTofi
     * 
     * @param bizBaseMbgCustomerDrmTomsTofiDTO BizBaseMbgCustomerDrmTomsTofi
     * @return 结果
     */
    public int insertBizBaseMbgCustomerDrmTomsTofi(BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi);

    /**
     * 修改BizBaseMbgCustomerDrmTomsTofi
     * 
     * @param bizBaseMbgCustomerDrmTomsTofiDTO BizBaseMbgCustomerDrmTomsTofi
     * @return 结果
     */
    public int updateBizBaseMbgCustomerDrmTomsTofi(BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi);

    /**
     * 批量删除BizBaseMbgCustomerDrmTomsTofi
     * 
     * @param ids 需要删除的BizBaseMbgCustomerDrmTomsTofi主键集合
     * @return 结果
     */
    public int deleteBizBaseMbgCustomerDrmTomsTofiByIds(Long[] ids);

    /**
     * 删除BizBaseMbgCustomerDrmTomsTofi信息
     * 
     * @param id BizBaseMbgCustomerDrmTomsTofi主键
     * @return 结果
     */
    public int deleteBizBaseMbgCustomerDrmTomsTofiById(Long id);

    /**
     * 根据customerNumber获取BizBaseMbgCustomerDrmTomsTofi信息
     * @param customerNumber
     * @return
     */
    public BizBaseMbgCustomerDrmTomsTofiDTO getByCustomerNumber(String customerNumber,String businessGroup);

    /**
     * 获取dropDownList
     * @param bizBaseMbgCustomerDrmTomsTofi
     * @return
     */
    public List<BizBaseMbgCustomerDrmTomsTofiDO> getDropDownList(BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi);
}
