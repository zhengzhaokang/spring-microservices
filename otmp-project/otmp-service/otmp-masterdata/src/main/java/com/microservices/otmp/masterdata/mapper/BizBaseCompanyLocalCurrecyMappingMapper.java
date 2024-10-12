package com.microservices.otmp.masterdata.mapper;

import java.util.List;
import com.microservices.otmp.masterdata.domain.entity.BizBaseCompanyLocalCurrecyMappingDO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseCompanyLocalCurrecyMappingDTO;


/**
 * companyCode和localcurrency映射Mapper接口
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
public interface BizBaseCompanyLocalCurrecyMappingMapper
{
    /**
     * 查询companyCode和localcurrency映射
     * 
     * @param id companyCode和localcurrency映射主键
     * @return companyCode和localcurrency映射
     */
    public BizBaseCompanyLocalCurrecyMappingDO selectBizBaseCompanyLocalCurrecyMappingById(Long id);

    /**
     * 查询companyCode和localcurrency映射列表
     * 
     * @param bizBaseCompanyLocalCurrecyMappingDTO companyCode和localcurrency映射
     * @return companyCode和localcurrency映射集合
     */
    public List<BizBaseCompanyLocalCurrecyMappingDO> selectBizBaseCompanyLocalCurrecyMappingList(BizBaseCompanyLocalCurrecyMappingDTO bizBaseCompanyLocalCurrecyMapping);

    /**
     * 新增companyCode和localcurrency映射
     * 
     * @param bizBaseCompanyLocalCurrecyMappingDO companyCode和localcurrency映射
     * @return 结果
     */
    public int insertBizBaseCompanyLocalCurrecyMapping(BizBaseCompanyLocalCurrecyMappingDO bizBaseCompanyLocalCurrecyMapping);

    /**
     * 修改companyCode和localcurrency映射
     * 
     * @param bizBaseCompanyLocalCurrecyMappingDO companyCode和localcurrency映射
     * @return 结果
     */
    public int updateBizBaseCompanyLocalCurrecyMapping (BizBaseCompanyLocalCurrecyMappingDO bizBaseCompanyLocalCurrecyMapping);

    /**
     * 删除companyCode和localcurrency映射
     * 
     * @param id companyCode和localcurrency映射主键
     * @return 结果
     */
    public int deleteBizBaseCompanyLocalCurrecyMappingById(Long id);

    /**
     * 批量删除companyCode和localcurrency映射
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseCompanyLocalCurrecyMappingByIds(Long[] ids);

    int updateByCompanyCode(BizBaseCompanyLocalCurrecyMappingDO bizBaseCompanyLocalCurrecyMapping);
}
