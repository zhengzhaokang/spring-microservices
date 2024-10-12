package com.microservices.otmp.finance.mapper;

import java.util.List;
import com.microservices.otmp.finance.domain.entity.FinanceBatchDO;
import com.microservices.otmp.finance.domain.dto.FinanceBatchDTO;


/**
 * Finance Batch InfoMapper接口
 * 
 * @author lovefamily
 * @date 2023-10-12
 */
public interface FinanceBatchMapper
{
    /**
     * 查询Finance Batch Info
     * 
     * @param id Finance Batch Info主键
     * @return Finance Batch Info
     */
    public FinanceBatchDO selectFinanceBatchById(Long id);

    /**
     * 查询Finance Batch Info列表
     * 
     * @param financeBatchDTO Finance Batch Info
     * @return Finance Batch Info集合
     */
    public List<FinanceBatchDO> selectFinanceBatchList(FinanceBatchDTO financeBatch);

    /**
     * 新增Finance Batch Info
     * 
     * @param financeBatchDO Finance Batch Info
     * @return 结果
     */
    public int insertFinanceBatch(FinanceBatchDO financeBatch);

    /**
     * 修改Finance Batch Info
     * 
     * @param financeBatchDO Finance Batch Info
     * @return 结果
     */
    public int updateFinanceBatch (FinanceBatchDO financeBatch);
    public int updateFinanceBatchByBatchNumber (FinanceBatchDO financeBatch);

    public List<FinanceBatchDO> findFinancedBatchList(FinanceBatchDTO financeBatch);


    /**
     * 删除Finance Batch Info
     * 
     * @param id Finance Batch Info主键
     * @return 结果
     */
    public int deleteFinanceBatchById(Long id);

    /**
     * 批量删除Finance Batch Info
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceBatchByIds(Long[] ids);
}
