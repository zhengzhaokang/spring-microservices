package com.microservices.otmp.masterdata.service;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.masterdata.domain.BizBasePreferredBankMaintenance;

import java.util.List;

/**
 * BizBasePreferredBankMaintenanceService接口
 *
 * @author xiaozy8
 * @date 2022-11-30
 */
public interface IBizBasePreferredBankMaintenanceService
{
    /**
     * 查询BizBasePreferredBankMaintenance
     * 
     * @param id BizBasePreferredBankMaintenance主键
     * @return BizBasePreferredBankMaintenance
     */
    public BizBasePreferredBankMaintenance selectBizBasePreferredBankMaintenanceById(Long id);

    /**
     * 查询BizBaseSalesOrg列表
     * 
     * @param bizBaseSalesOrg BizBasePreferredBankMaintenance
     * @return BizBasePreferredBankMaintenance集合
     */
    public List<BizBasePreferredBankMaintenance> selectBizBasePreferredBankMaintenanceList(BizBasePreferredBankMaintenance bizBaseSalesOrg);

    /**
     * 新增BizBaseSalesOrg
     * 
     * @param bizBaseSalesOrg BizBasePreferredBankMaintenance
     * @return 结果
     */
    public int insertBizBasePreferredBankMaintenance(BizBasePreferredBankMaintenance bizBaseSalesOrg);

    /**
     * 修改BizBasePreferredBankMaintenance
     * 
     * @param bizBaseSalesOrg BizBasePreferredBankMaintenance
     * @return 结果
     */
    public int updateBizBasePreferredBankMaintenance(BizBasePreferredBankMaintenance bizBaseSalesOrg);

    /**
     * 批量删除BizBasePreferredBankMaintenance
     * 
     * @param ids 需要删除的BizBaseSalesOrg主键集合
     * @return 结果
     */
    public int deleteBizBasePreferredBankMaintenanceByIds(Long[] ids);

    /**
     * 删除BizBasePreferredBankMaintenance信息
     * 
     * @param id BizBasePreferredBankMaintenance主键
     * @return 结果
     */
    public int deleteBizBasePreferredBankMaintenanceById(Long id);

    public ResultDTO<String> importExcel(List<BizBasePreferredBankMaintenance> bizs, String loginName);


    public int checkLgvmBank(BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance);
}
