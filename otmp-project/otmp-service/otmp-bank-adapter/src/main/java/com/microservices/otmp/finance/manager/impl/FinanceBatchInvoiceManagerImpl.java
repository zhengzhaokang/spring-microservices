package com.microservices.otmp.finance.manager.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.finance.domain.dto.FinanceBatchInvoiceDTO;
import com.microservices.otmp.finance.domain.entity.FinanceBatchInvoiceDO;
import com.microservices.otmp.finance.manager.IFinanceBatchInvoiceManager;
import com.microservices.otmp.finance.mapper.FinanceBatchInvoiceMapper;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.finance.domain.dto.FinanceBatchInvoiceDTO;
import com.microservices.otmp.finance.domain.entity.FinanceBatchInvoiceDO;
import com.microservices.otmp.finance.mapper.FinanceBatchInvoiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Finance Batch InvoiceManager业务层处理
 *
 * @author lovefamily
 * @date 2023-10-12
 */
@Service
public class FinanceBatchInvoiceManagerImpl implements IFinanceBatchInvoiceManager {
    private static final Logger log = LoggerFactory.getLogger(FinanceBatchInvoiceManagerImpl.class);

    @Autowired
    private FinanceBatchInvoiceMapper financeBatchInvoiceMapper;

    /**
     * 查询Finance Batch Invoice
     *
     * @param id Finance Batch Invoice主键
     * @return Finance Batch InvoiceDO
     */
    @Override
    public FinanceBatchInvoiceDO selectFinanceBatchInvoiceById(Long id) {
        return financeBatchInvoiceMapper.selectFinanceBatchInvoiceById(id);
    }

    /**
     * 查询Finance Batch Invoice列表
     *
     * @param financeBatchInvoice Finance Batch Invoice
     * @return Finance Batch InvoiceDO
     */
    @Override
    public List<FinanceBatchInvoiceDO> selectFinanceBatchInvoiceList(FinanceBatchInvoiceDTO financeBatchInvoice) {
        return financeBatchInvoiceMapper.selectFinanceBatchInvoiceList(financeBatchInvoice);
    }

    /**
     * 新增Finance Batch Invoice
     *
     * @param financeBatchInvoice Finance Batch Invoice
     * @return 结果
     */
    @Override
    public int insertFinanceBatchInvoice(FinanceBatchInvoiceDO financeBatchInvoice) {
        financeBatchInvoice.setCreateTime(DateUtils.getNowDate());
        return financeBatchInvoiceMapper.insertFinanceBatchInvoice(financeBatchInvoice);
    }

    /**
     * 修改Finance Batch Invoice
     *
     * @param financeBatchInvoice Finance Batch Invoice
     * @return 结果
     */
    @Override
    public int updateFinanceBatchInvoice(FinanceBatchInvoiceDO financeBatchInvoice) {
        financeBatchInvoice.setUpdateTime(DateUtils.getNowDate());
        return financeBatchInvoiceMapper.updateFinanceBatchInvoice(financeBatchInvoice);
    }

    /**
     * 批量删除Finance Batch Invoice
     *
     * @param ids 需要删除的Finance Batch Invoice主键
     * @return 结果
     */
    @Override
    public int deleteFinanceBatchInvoiceByIds(Long[] ids) {
        return financeBatchInvoiceMapper.deleteFinanceBatchInvoiceByIds(ids);
    }

    /**
     * 删除Finance Batch Invoice信息
     *
     * @param id Finance Batch Invoice主键
     * @return 结果
     */
    @Override
    public int deleteFinanceBatchInvoiceById(Long id) {
        return financeBatchInvoiceMapper.deleteFinanceBatchInvoiceById(id);
    }
}
