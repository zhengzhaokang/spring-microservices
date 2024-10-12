package com.microservices.otmp.masterdata.manager;

import com.microservices.otmp.masterdata.domain.BizBaseSelfInvoice;
import com.microservices.otmp.masterdata.domain.BizBaseSelfInvoice;

import java.util.List;


/**
 * gtnCategoryManager接口
 * 
 * @author lovefamily
 * @date 2023-06-05
 */
public interface IBizBaseSelfInvoiceManager
{
    /**
     * 查询BizBaseSelfInvoice
     *
     * @param id BizBaseSelfInvoice主键
     * @return BizBaseSelfInvoice
     */
    BizBaseSelfInvoice selectBizBaseSelfInvoiceById(Long id);

    /**
     * 查询BizBaseSalesOrg列表
     *
     * @param bizBaseSalesOrg BizBaseSelfInvoice
     * @return BizBaseSelfInvoice集合
     */
    List<BizBaseSelfInvoice> selectBizBaseSelfInvoiceList(BizBaseSelfInvoice bizBaseSalesOrg);

    /**
     * 新增BizBaseSalesOrg
     *
     * @param bizBaseSalesOrg BizBaseSelfInvoice
     * @return 结果
     */
    int insertBizBaseSelfInvoice(BizBaseSelfInvoice bizBaseSalesOrg);

    /**
     * 修改BizBaseSelfInvoice
     *
     * @param bizBaseSalesOrg BizBaseSelfInvoice
     * @return 结果
     */
    int updateBizBaseSelfInvoice(BizBaseSelfInvoice bizBaseSalesOrg);

    /**
     * 批量删除BizBaseSelfInvoice
     *
     * @param ids 需要删除的BizBaseSalesOrg主键集合
     * @return 结果
     */
    int deleteBizBaseSelfInvoiceByIds(Long[] ids);

    /**
     * 删除BizBaseSelfInvoice信息
     *
     * @param id BizBaseSelfInvoice主键
     * @return 结果
     */
    int deleteBizBaseSelfInvoiceById(Long id);

}
