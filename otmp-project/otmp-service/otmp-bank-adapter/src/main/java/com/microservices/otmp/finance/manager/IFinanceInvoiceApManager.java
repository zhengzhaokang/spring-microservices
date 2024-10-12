package com.microservices.otmp.finance.manager;

import java.util.List;
import com.microservices.otmp.finance.domain.entity.FinanceInvoiceApDO;
import com.microservices.otmp.finance.domain.dto.FinanceInvoiceApDTO;
import com.microservices.otmp.finance.domain.dto.FinanceInvoiceApDTO;
import com.microservices.otmp.finance.domain.entity.FinanceInvoiceApDO;


/**
 * Finance Invoice ApManager接口
 * 
 * @author lovefamily
 * @date 2023-10-12
 */
public interface IFinanceInvoiceApManager
{
    /**
     * 查询Finance Invoice Ap
     * 
     * @param id Finance Invoice Ap主键
     * @return Finance Invoice Ap
     */
    public FinanceInvoiceApDO selectFinanceInvoiceApById(Long id);

    /**
     * 查询Finance Invoice Ap列表
     *
     * @param financeInvoiceAp Finance Invoice Ap
     * @return Finance Invoice Ap集合
     */
    public List<FinanceInvoiceApDO> selectFinanceInvoiceApList(FinanceInvoiceApDTO financeInvoiceAp);

    /**
     * 新增Finance Invoice Ap
     *
     * @param financeInvoiceAp Finance Invoice Ap
     * @return 结果
     */
    public int insertFinanceInvoiceAp(FinanceInvoiceApDO financeInvoiceAp);

    /**
     * 修改Finance Invoice Ap
     *
     * @param financeInvoiceAp Finance Invoice Ap
     * @return 结果
     */
    public int updateFinanceInvoiceAp(FinanceInvoiceApDO financeInvoiceAp);

    /**
     * 批量删除Finance Invoice Ap
     * 
     * @param ids 需要删除的Finance Invoice Ap主键集合
     * @return 结果
     */
    public int deleteFinanceInvoiceApByIds(Long[] ids);

    /**
     * 删除Finance Invoice Ap信息
     * 
     * @param id Finance Invoice Ap主键
     * @return 结果
     */
    public int deleteFinanceInvoiceApById(Long id);
}
