package com.microservices.otmp.masterdata.mapper;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBasePreferredBankMaintenance;
import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.masterdata.domain.BizBasePreferredBankMaintenance;

import java.util.List;

/**
 * BizBasePreferredBankMaintenanceMapper接口
 *
 * @author xiaozy8
 * @date 2022-11-30
 */
public interface BizBasePreferredBankMaintenanceMapper
{
    /**
     * 查询BizBasePreferredBankMaintenance
     * 
     * @param id BizBasePreferredBankMaintenance主键
     * @return BizBasePreferredBankMaintenance
     */
    public BizBasePreferredBankMaintenance selectBizBasePreferredBankMaintenanceById(Long id);

    /**
     * 查询BizBasePreferredBankMaintenance列表
     * 
     * @param bizBasePreferredBankMaintenance BizBasePreferredBankMaintenance
     * @return BizBasePreferredBankMaintenance集合
     */
    @DataPermissions(tableName = "biz_base_preferred_bank_maintenance")
    public List<BizBasePreferredBankMaintenance> selectBizBasePreferredBankMaintenanceList(BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance);


    /**
     * 新增BizBasePreferredBankMaintenance
     * 
     * @param bizBasePreferredBankMaintenance BizBasePreferredBankMaintenance
     * @return 结果
     */
    public int insertBizBasePreferredBankMaintenance(BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance);

    /**
     * 修改BizBasePreferredBankMaintenance
     * 
     * @param bizBasePreferredBankMaintenance BizBasePreferredBankMaintenance
     * @return 结果
     */
    public int updateBizBasePreferredBankMaintenance(BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance);

    /**
     * 删除BizBasePreferredBankMaintenance
     * 
     * @param id BizBasePreferredBankMaintenance主键
     * @return 结果
     */
    public int deleteBizBasePreferredBankMaintenanceById(Long id);

    /**
     * 批量删除BizBasePreferredBankMaintenance
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBasePreferredBankMaintenanceByIds(Long[] ids);

    int checkLgvmBank(BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance);
}
