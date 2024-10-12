package com.microservices.otmp.masterdata.service;

import java.util.List;


import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseGtnType;



import com.microservices.otmp.masterdata.domain.entity.dto.ToMsGtnTypeDTO;
import com.microservices.otmp.masterdata.domain.entity.vo.MsGtnTypeVo;

/**
 * BaseGtnTypeService接口
 *
 * @author lovefamily
 * @date 2022-04-24
 */
public interface IBizBaseGtnTypeService
{
    /**
     * 查询BaseGtnType
     *
     * @param id BaseGtnType主键
     * @return BaseGtnType
     */
    public BizBaseGtnType selectBizBaseGtnTypeById(Long id);

    /**
     * 查询BaseGtnType列表
     *
     * @param bizBaseGtnType BaseGtnType
     * @return BaseGtnType集合
     */


    public List<BizBaseGtnType> selectBizBaseGtnTypeList(BizBaseGtnType bizBaseGtnType);

    /**
     * 查询下拉框
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseGtnType集合
     */
    public List<BizBaseGtnType> getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition);

    public List<BizBaseGtnType> getAllList(BizBaseDropDownCondition bizBaseDropDownConditione);
    /**
     * 新增BaseGtnType
     *
     * @param bizBaseGtnType BaseGtnType
     * @return 结果
     */
    public int insertBizBaseGtnType(BizBaseGtnType bizBaseGtnType);

    /**
     * 修改BaseGtnType
     *
     * @param bizBaseGtnType BaseGtnType
     * @return 结果
     */
    public int updateBizBaseGtnType(BizBaseGtnType bizBaseGtnType);

    /**
     * 批量删除BaseGtnType
     *
     * @param ids 需要删除的BaseGtnType主键集合
     * @return 结果
     */
    public int deleteBizBaseGtnTypeByIds(Long[] ids);

    /**
     * 删除BaseGtnType信息
     *
     * @param id BaseGtnType主键
     * @return 结果
     */
    public int deleteBizBaseGtnTypeById(Long id);

    String importExcel(List<BizBaseGtnType> bizs, String loginName);

    List<MsGtnTypeVo> toMsGtnTypeList(ToMsGtnTypeDTO toMsGtnTypeDTO);

}
