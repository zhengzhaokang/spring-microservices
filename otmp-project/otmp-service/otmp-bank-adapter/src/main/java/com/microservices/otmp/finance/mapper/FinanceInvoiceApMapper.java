package com.microservices.otmp.finance.mapper;

import java.util.List;
import java.util.Map;

import com.microservices.otmp.bank.domain.dto.DbsDTO;
import com.microservices.otmp.bank.domain.dto.FinanceInvoiceApHasSupplierDTO;
import com.microservices.otmp.finance.domain.entity.FinanceInvoiceApDO;
import com.microservices.otmp.finance.domain.dto.FinanceInvoiceApDTO;
import org.apache.ibatis.annotations.Param;


/**
 * Finance Invoice ApMapper接口
 *
 * @author lovefamily
 * @date 2023-10-12
 */
public interface FinanceInvoiceApMapper {
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
     * 删除Finance Invoice Ap
     *
     * @param id Finance Invoice Ap主键
     * @return 结果
     */
    public int deleteFinanceInvoiceApById(Long id);

    /**
     * 批量删除Finance Invoice Ap
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceInvoiceApByIds(Long[] ids);

    /**
     * 获取所需的发票信息
     *
     * @param batchNo      编号
     * @param supplierCode 供应商编号
     * @param bankCode     银行信息
     * @param entityId     entityId
     * @return List<FinanceInvoiceApDTO>
     */
    List<FinanceInvoiceApHasSupplierDTO> getFinanceInvoiceAp(@Param("batchNo") String batchNo, @Param("supplierCode") String supplierCode,
                                                             @Param("bankCode") String bankCode, @Param("entityId") Long entityId);


    /**
     * 获取所需的发票信息
     *
     * @param batchNo      编号
     * @param supplierCode 供应商编号
     * @param bankCode     银行信息
     * @param entityId     entityId
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> findFinanceInvoiceApMap(@Param("batchNo") String batchNo, @Param("supplierCode") String supplierCode,
                                                      @Param("bankId") String bankCode, @Param("entityId") Long entityId);

    /**
     * 获取所需的发票信息
     *
     * @param batchNo      编号
     * @param supplierCode 供应商编号
     * @param bankCode     银行信息
     * @param entityId     entityId
     * @return List<BankUploadDBSDTO>
     */
    List<DbsDTO> getFinancingDataDBS(@Param("batchNo") String batchNo, @Param("supplierCode") String supplierCode,
                                     @Param("bankId") String bankCode, @Param("entityId") Long entityId);


    /**
     * 获取所需的发票信息
     *
     * @param batchNo      编号
     * @param supplierCode 供应商编号
     * @param bankCode     银行信息
     * @param entityId     entityId
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> findFinancingDataDBSMap(@Param("batchNo") String batchNo, @Param("supplierCode") String supplierCode,
                                                      @Param("bankId") String bankCode, @Param("entityId") Long entityId);


}
