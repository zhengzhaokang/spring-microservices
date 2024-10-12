package com.microservices.otmp.masterdata.service;

import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.dto.BizBaseGtnCategoryDTO;

import java.util.List;

/**
 * gtnCategoryService接口
 * 
 * @author lovefamily
 * @date 2023-06-05
 */
public interface IBizBaseGtnCategoryService
{
    /**
     * 查询gtnCategory
     * 
     * @param id gtnCategory主键
     * @return gtnCategoryDTO
     */
    BizBaseGtnCategoryDTO selectBizBaseGtnCategoryById(Long id);

    /**
     * 查询gtnCategory列表
     *
     * @param bizBaseGtnCategory gtnCategory
     * @return gtnCategoryDTO集合
     */
    List<BizBaseGtnCategoryDTO> selectBizBaseGtnCategoryList(BizBaseGtnCategoryDTO bizBaseGtnCategory);

    List<BizBaseGtnCategoryDTO> getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition);

}
