package com.microservices.otmp.finance.manager.impl;

import java.util.List;

import com.microservices.otmp.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.finance.mapper.FinanceBatchMapper;
import com.microservices.otmp.finance.domain.entity.FinanceBatchDO;
import com.microservices.otmp.finance.domain.dto.FinanceBatchDTO;
import com.microservices.otmp.finance.manager.IFinanceBatchManager;

/**
 * Finance Batch InfoManager业务层处理
 *
 * @author lovefamily
 * @date 2023-10-12
 */
@Service
public class FinanceBatchManagerImpl implements IFinanceBatchManager {
    private static final Logger log = LoggerFactory.getLogger(FinanceBatchManagerImpl.class);

    @Autowired
    private FinanceBatchMapper financeBatchMapper;

    /**
     * 查询Finance Batch Info
     *
     * @param id Finance Batch Info主键
     * @return Finance Batch InfoDO
     */
    @Override
    public FinanceBatchDO selectFinanceBatchById(Long id) {
        return financeBatchMapper.selectFinanceBatchById(id);
    }

    /**
     * 查询Finance Batch Info列表
     *
     * @param financeBatch Finance Batch Info
     * @return Finance Batch InfoDO
     */
    @Override
    public List<FinanceBatchDO> selectFinanceBatchList(FinanceBatchDTO financeBatch) {
        return financeBatchMapper.selectFinanceBatchList(financeBatch);
    }

    /**
     * 新增Finance Batch Info
     *
     * @param financeBatch Finance Batch Info
     * @return 结果
     */
    @Override
    public int insertFinanceBatch(FinanceBatchDO financeBatch) {
        financeBatch.setCreateTime(DateUtils.getNowDate());
        return financeBatchMapper.insertFinanceBatch(financeBatch);
    }

    /**
     * 修改Finance Batch Info
     *
     * @param financeBatch Finance Batch Info
     * @return 结果
     */
    @Override
    public int updateFinanceBatch(FinanceBatchDO financeBatch) {
        financeBatch.setUpdateTime(DateUtils.getNowDate());
        return financeBatchMapper.updateFinanceBatch(financeBatch);
    }

    @Override
    public int updateFinanceBatchByBatchNumber(FinanceBatchDO financeBatch) {
        financeBatch.setUpdateTime(DateUtils.getNowDate());
        return financeBatchMapper.updateFinanceBatchByBatchNumber(financeBatch);
    }


    /**
     * 批量删除Finance Batch Info
     *
     * @param ids 需要删除的Finance Batch Info主键
     * @return 结果
     */
    @Override
    public int deleteFinanceBatchByIds(Long[] ids) {
        return financeBatchMapper.deleteFinanceBatchByIds(ids);
    }

    /**
     * 删除Finance Batch Info信息
     *
     * @param id Finance Batch Info主键
     * @return 结果
     */
    @Override
    public int deleteFinanceBatchById(Long id) {
        return financeBatchMapper.deleteFinanceBatchById(id);
    }
}
