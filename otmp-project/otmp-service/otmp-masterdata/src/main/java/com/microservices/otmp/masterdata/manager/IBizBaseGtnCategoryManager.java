package com.microservices.otmp.masterdata.manager;

import com.microservices.otmp.masterdata.domain.dto.BizBaseGtnCategoryDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseGtnCategoryDO;

import java.util.List;


/**
 * gtnCategoryManager接口
 * 
 * @author lovefamily
 * @date 2023-06-05
 */
public interface IBizBaseGtnCategoryManager
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
     * @param bizBaseGtnCategory gtnCategory
     * @return gtnCategory集合
     */
    List<BizBaseGtnCategoryDO> selectBizBaseGtnCategoryList(BizBaseGtnCategoryDTO bizBaseGtnCategory);


}
