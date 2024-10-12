package com.microservices.otmp.masterdata.mapper;

import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.dto.BizBaseGtnCategoryDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseGtnCategoryDO;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.dto.BizBaseGtnCategoryDTO;

import java.util.List;


/**
 * gtnCategoryMapper接口
 * 
 * @author lovefamily
 * @date 2023-06-05
 */
public interface BizBaseGtnCategoryMapper
{
    /**
     * 查询gtnCategory
     * 
     * @param id gtnCategory主键
     * @return gtnCategory
     */
    BizBaseGtnCategoryDO selectBizBaseGtnCategoryById(Long id);

    /**
     * 查询gtnCategory列表
     * 
     * @param bizBaseGtnCategoryDTO gtnCategory
     * @return gtnCategory集合
     */
    List<BizBaseGtnCategoryDO> selectBizBaseGtnCategoryList(BizBaseGtnCategoryDTO bizBaseGtnCategory);


    /*
    * 获取L1
    * */
    List<BizBaseGtnCategoryDO> getDropDownl1List(BizBaseDropDownCondition bizBaseDropDownCondition);
    /*
    * 获取OrderReason
    * */
    List<BizBaseGtnCategoryDO> getDropDownOrderReasonList(BizBaseDropDownCondition bizBaseDropDownCondition);
}
