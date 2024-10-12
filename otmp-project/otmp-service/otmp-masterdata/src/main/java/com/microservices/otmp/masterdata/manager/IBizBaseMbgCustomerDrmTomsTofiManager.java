package com.microservices.otmp.masterdata.manager;

import com.microservices.otmp.masterdata.domain.entity.BizBaseMbgCustomerDrmTomsTofiDO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseMbgCustomerDrmTomsTofiDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseMbgCustomerDrmTomsTofiDTO;

import java.util.List;


/**
 * BizBaseMbgCustomerDrmTomsTofiManager接口
 * 
 * @author lovefamily
 * @date 2023-03-01
 */
public interface IBizBaseMbgCustomerDrmTomsTofiManager
{
    /**
     * 查询BizBaseMbgCustomerDrmTomsTofi
     * 
     * @param id BizBaseMbgCustomerDrmTomsTofi主键
     * @return BizBaseMbgCustomerDrmTomsTofi
     */
    public BizBaseMbgCustomerDrmTomsTofiDO selectBizBaseMbgCustomerDrmTomsTofiById(Long id);

    /**
     * 查询BizBaseMbgCustomerDrmTomsTofi列表
     *
     * @param bizBaseMbgCustomerDrmTomsTofi BizBaseMbgCustomerDrmTomsTofi
     * @return BizBaseMbgCustomerDrmTomsTofi集合
     */
    public List<BizBaseMbgCustomerDrmTomsTofiDO> selectBizBaseMbgCustomerDrmTomsTofiList(BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi);

    /**
     * 新增BizBaseMbgCustomerDrmTomsTofi
     *
     * @param bizBaseMbgCustomerDrmTomsTofi BizBaseMbgCustomerDrmTomsTofi
     * @return 结果
     */
    public int insertBizBaseMbgCustomerDrmTomsTofi(BizBaseMbgCustomerDrmTomsTofiDO bizBaseMbgCustomerDrmTomsTofi);

    /**
     * 修改BizBaseMbgCustomerDrmTomsTofi
     *
     * @param bizBaseMbgCustomerDrmTomsTofi BizBaseMbgCustomerDrmTomsTofi
     * @return 结果
     */
    public int updateBizBaseMbgCustomerDrmTomsTofi(BizBaseMbgCustomerDrmTomsTofiDO bizBaseMbgCustomerDrmTomsTofi);

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
    public BizBaseMbgCustomerDrmTomsTofiDO getByCustomerNumber(String customerNumber,String businessGroup);

    /**
     * 获取KeyAccount dropDownList
     * @param bizBaseMbgCustomerDrmTomsTofi
     * @return
     */
    public List<BizBaseMbgCustomerDrmTomsTofiDO> getDropDownListByKeyAccount(BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi);

    /**
     * 获取CustomerGroup dropDownList
     * @param bizBaseMbgCustomerDrmTomsTofi
     * @return
     */
    public List<BizBaseMbgCustomerDrmTomsTofiDO> getDropDownListByCustomerGroup(BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi);
}
