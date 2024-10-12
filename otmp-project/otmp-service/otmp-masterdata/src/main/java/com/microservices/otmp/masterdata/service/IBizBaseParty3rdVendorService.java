package com.microservices.otmp.masterdata.service;

import java.util.List;
import com.microservices.otmp.masterdata.domain.dto.BizBaseParty3rdVendorDTO;

/**
 * bizBaseParty3rdVendorService接口
 * 
 * @author lovefamily
 * @date 2022-10-12
 */
public interface IBizBaseParty3rdVendorService
{
    /**
     * 查询bizBaseParty3rdVendor
     * 
     * @param id bizBaseParty3rdVendor主键
     * @return bizBaseParty3rdVendorDTO
     */
    public BizBaseParty3rdVendorDTO selectBizBaseParty3rdVendorById(Long id);

    /**
     * 查询bizBaseParty3rdVendor列表
     *
     * @param bizBaseParty3rdVendor bizBaseParty3rdVendor
     * @return bizBaseParty3rdVendorDTO集合
     */
    public List<BizBaseParty3rdVendorDTO> selectBizBaseParty3rdVendorList(BizBaseParty3rdVendorDTO bizBaseParty3rdVendor);

    /**
     * 新增bizBaseParty3rdVendor
     * 
     * @param bizBaseParty3rdVendor bizBaseParty3rdVendor
     * @return 结果
     */
    public int insertBizBaseParty3rdVendor(BizBaseParty3rdVendorDTO bizBaseParty3rdVendor);

    /**
     * 修改bizBaseParty3rdVendor
     * 
     * @param bizBaseParty3rdVendor bizBaseParty3rdVendor
     * @return 结果
     */
    public int updateBizBaseParty3rdVendor(BizBaseParty3rdVendorDTO bizBaseParty3rdVendor);

    /**
     * 批量删除bizBaseParty3rdVendor
     * 
     * @param ids 需要删除的bizBaseParty3rdVendor主键集合
     * @return 结果
     */
    public int deleteBizBaseParty3rdVendorByIds(Long[] ids);

    /**
     * 删除bizBaseParty3rdVendor信息
     * 
     * @param id bizBaseParty3rdVendor主键
     * @return 结果
     */
    public int deleteBizBaseParty3rdVendorById(Long id);

    List<BizBaseParty3rdVendorDTO> remoteList(BizBaseParty3rdVendorDTO bizBaseParty3rdVendor);
}
