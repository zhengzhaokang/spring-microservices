package com.microservices.otmp.masterdata.mapper;

import java.util.List;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseGtnType;
import com.microservices.otmp.masterdata.domain.entity.dto.ToMsGtnTypeDTO;
import com.microservices.otmp.masterdata.domain.entity.vo.MsGtnTypeVo;

/**
 * BaseGtnTypeMapper接口
 *
 * @author lovefamily
 * @date 2022-04-24
 */
public interface BizBaseGtnTypeMapper
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
    @DataPermissions(tableName = "biz_base_gtn_type")
    public List<BizBaseGtnType> selectBizBaseGtnTypeList(BizBaseGtnType bizBaseGtnType);

    /**
     * 查询下拉框
     *
     * @param bizBaseDropDownCondition BizBaseDropDownCondition
     * @return BizBaseGtnType集合
     */
    @DataPermissions(tableName = "biz_base_gtn_type")
    public List<BizBaseGtnType> getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition);

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
     * 删除BaseGtnType
     *
     * @param id BaseGtnType主键
     * @return 结果
     */
    public int deleteBizBaseGtnTypeById(Long id);

    /**
     * 批量删除BaseGtnType
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseGtnTypeByIds(Long[] ids);

    List<BizBaseGtnType> selectBizBaseGtnTypeListCheck(BizBaseGtnType bizBaseComBiz);

    int updateBizBaseGtnTypeIds(Long[] ids);

    List<MsGtnTypeVo> toMsGtnTypeList(ToMsGtnTypeDTO toMsGtnTypeDTO);
}
