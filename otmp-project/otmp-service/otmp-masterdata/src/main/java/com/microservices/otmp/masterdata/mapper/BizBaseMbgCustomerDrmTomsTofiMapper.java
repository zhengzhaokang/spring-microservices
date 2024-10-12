package com.microservices.otmp.masterdata.mapper;

import com.microservices.otmp.masterdata.domain.dto.BizBaseMbgCustomerDrmTomsTofiDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseMbgCustomerDrmTomsTofiDO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseMbgCustomerDrmTomsTofiDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * BizBaseMbgCustomerDrmTomsTofiMapper接口
 *
 * @author lovefamily
 * @date 2023-03-01
 */
public interface BizBaseMbgCustomerDrmTomsTofiMapper {
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
     * @param bizBaseMbgCustomerDrmTomsTofiDTO BizBaseMbgCustomerDrmTomsTofi
     * @return BizBaseMbgCustomerDrmTomsTofi集合
     */
    public List<BizBaseMbgCustomerDrmTomsTofiDO> selectBizBaseMbgCustomerDrmTomsTofiList(BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi);

    /**
     * 新增BizBaseMbgCustomerDrmTomsTofi
     *
     * @param bizBaseMbgCustomerDrmTomsTofiDO BizBaseMbgCustomerDrmTomsTofi
     * @return 结果
     */
    public int insertBizBaseMbgCustomerDrmTomsTofi(BizBaseMbgCustomerDrmTomsTofiDO bizBaseMbgCustomerDrmTomsTofi);

    /**
     * 修改BizBaseMbgCustomerDrmTomsTofi
     *
     * @param bizBaseMbgCustomerDrmTomsTofiDO BizBaseMbgCustomerDrmTomsTofi
     * @return 结果
     */
    public int updateBizBaseMbgCustomerDrmTomsTofi(BizBaseMbgCustomerDrmTomsTofiDO bizBaseMbgCustomerDrmTomsTofi);

    /**
     * 删除BizBaseMbgCustomerDrmTomsTofi
     *
     * @param id BizBaseMbgCustomerDrmTomsTofi主键
     * @return 结果
     */
    public int deleteBizBaseMbgCustomerDrmTomsTofiById(Long id);

    /**
     * 批量删除BizBaseMbgCustomerDrmTomsTofi
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseMbgCustomerDrmTomsTofiByIds(Long[] ids);

    /**
     * 根据customerNumber获取BizBaseMbgCustomerDrmTomsTofi信息
     *
     * @param customerNumber
     * @return
     */
    public BizBaseMbgCustomerDrmTomsTofiDO getByCustomerNumber(@Param("customerNumber") String customerNumber, @Param("businessGroup")String businessGroup);

    /**
     * 获取dropDownList
     *
     * @param bizBaseMbgCustomerDrmTomsTofi
     * @return
     */
    public List<BizBaseMbgCustomerDrmTomsTofiDO> getDropDownListByKeyAccount(BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi);

    /**
     * 获取dropDownList
     * @param bizBaseMbgCustomerDrmTomsTofi
     * @return
     */
    public List<BizBaseMbgCustomerDrmTomsTofiDO> getDropDownListByCustomerGroup(BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi);
}
