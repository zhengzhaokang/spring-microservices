package com.microservices.otmp.masterdata.mapper;

import java.util.List;
import com.microservices.otmp.masterdata.domain.BizBaseParty3rdVendorDO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseParty3rdVendorDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.ToMsParty3rdVendorDTO;
import com.microservices.otmp.masterdata.domain.entity.vo.MsParty3rdVendorVo;


/**
 * bizBaseParty3rdVendorMapper接口
 * 
 * @author lovefamily
 * @date 2022-10-12
 */
public interface BizBaseParty3rdVendorMapper
{
    /**
     * 查询bizBaseParty3rdVendor
     * 
     * @param id bizBaseParty3rdVendor主键
     * @return bizBaseParty3rdVendor
     */
    public BizBaseParty3rdVendorDO selectBizBaseParty3rdVendorById(Long id);

    /**
     * 查询bizBaseParty3rdVendor列表
     * 
     * @param bizBaseParty3rdVendorDTO bizBaseParty3rdVendor
     * @return bizBaseParty3rdVendor集合
     */
    public List<BizBaseParty3rdVendorDO> selectBizBaseParty3rdVendorList(BizBaseParty3rdVendorDTO bizBaseParty3rdVendorDTO);

    /**
     * 新增bizBaseParty3rdVendor
     * 
     * @param bizBaseParty3rdVendorDO bizBaseParty3rdVendor
     * @return 结果
     */
    public int insertBizBaseParty3rdVendor(BizBaseParty3rdVendorDO bizBaseParty3rdVendorDO);

    /**
     * 修改bizBaseParty3rdVendor
     * 
     * @param bizBaseParty3rdVendorDO bizBaseParty3rdVendor
     * @return 结果
     */
    public int updateBizBaseParty3rdVendor (BizBaseParty3rdVendorDO bizBaseParty3rdVendorDO);

    /**
     * 删除bizBaseParty3rdVendor
     * 
     * @param id bizBaseParty3rdVendor主键
     * @return 结果
     */
    public int deleteBizBaseParty3rdVendorById(Long id);

    /**
     * 批量删除bizBaseParty3rdVendor
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseParty3rdVendorByIds(Long[] ids);

    /**
     *
     * @param toMs3rdPartVendorDTO
     * @return 结果
     */
    public List<MsParty3rdVendorVo> getParty3rdVendorList(ToMsParty3rdVendorDTO toMs3rdPartVendorDTO);
}
