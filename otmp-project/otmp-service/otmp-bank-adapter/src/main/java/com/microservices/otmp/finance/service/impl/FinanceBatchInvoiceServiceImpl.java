package com.microservices.otmp.finance.service.impl;

import java.util.List;
import java.util.ArrayList;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.finance.manager.IFinanceBatchInvoiceManager;
import com.microservices.otmp.finance.domain.dto.FinanceBatchInvoiceDTO;
import com.microservices.otmp.finance.domain.entity.FinanceBatchInvoiceDO;
import com.microservices.otmp.finance.service.IFinanceBatchInvoiceService;

/**
 * Finance Batch InvoiceService业务层处理
 * 
 * @author lovefamily
 * @date 2023-10-12
 */
@Service
public class FinanceBatchInvoiceServiceImpl implements IFinanceBatchInvoiceService
{
    private static final Logger log = LoggerFactory.getLogger(FinanceBatchInvoiceServiceImpl.class);

    @Autowired
    private IFinanceBatchInvoiceManager financeBatchInvoiceManager;

    /**
     * 查询Finance Batch Invoice
     * 
     * @param id Finance Batch Invoice主键
     * @return Finance Batch InvoiceDTO
     */
    @Override
    public FinanceBatchInvoiceDTO selectFinanceBatchInvoiceById(Long id)
    {
         FinanceBatchInvoiceDO financeBatchInvoiceDO =  financeBatchInvoiceManager.selectFinanceBatchInvoiceById(id);
         FinanceBatchInvoiceDTO financeBatchInvoiceDTO = new FinanceBatchInvoiceDTO();
         BeanUtils.copyProperties(financeBatchInvoiceDO, financeBatchInvoiceDTO);
        return financeBatchInvoiceDTO;
    }

    /**
     * 查询Finance Batch Invoice列表
     *
     * @param financeBatchInvoice Finance Batch Invoice
     * @return Finance Batch InvoiceDTO
     */
    @Override
    public List<FinanceBatchInvoiceDTO> selectFinanceBatchInvoiceList(FinanceBatchInvoiceDTO financeBatchInvoice)
    {

        List<FinanceBatchInvoiceDO> financeBatchInvoiceDOS =
                    financeBatchInvoiceManager.selectFinanceBatchInvoiceList(financeBatchInvoice);
        List<FinanceBatchInvoiceDTO> financeBatchInvoiceList = new ArrayList<>(financeBatchInvoiceDOS.size());
        BeanUtils.copyListProperties(financeBatchInvoiceDOS, financeBatchInvoiceList, FinanceBatchInvoiceDTO.class);
        return financeBatchInvoiceList;

    }

    /**
     * 新增Finance Batch Invoice
     * 
     * @param financeBatchInvoice Finance Batch Invoice
     * @return 结果
     */
    @Override
    public int insertFinanceBatchInvoice(FinanceBatchInvoiceDTO financeBatchInvoice)
    {
        financeBatchInvoice.setCreateTime(DateUtils.getNowDate());
        FinanceBatchInvoiceDO financeBatchInvoiceDO =new  FinanceBatchInvoiceDO();
        BeanUtils.copyProperties(financeBatchInvoice, financeBatchInvoiceDO);
        return financeBatchInvoiceManager.insertFinanceBatchInvoice(financeBatchInvoiceDO);
    }

    /**
     * 修改Finance Batch Invoice
     * 
     * @param financeBatchInvoice Finance Batch Invoice
     * @return 结果
     */
    @Override
    public int updateFinanceBatchInvoice(FinanceBatchInvoiceDTO financeBatchInvoice)
    {
        financeBatchInvoice.setUpdateTime(DateUtils.getNowDate());
       FinanceBatchInvoiceDO financeBatchInvoiceDO =new  FinanceBatchInvoiceDO();
        BeanUtils.copyProperties(financeBatchInvoice, financeBatchInvoiceDO);
        return financeBatchInvoiceManager.updateFinanceBatchInvoice(financeBatchInvoiceDO);
    }

    /**
     * 批量删除Finance Batch Invoice
     * 
     * @param ids 需要删除的Finance Batch Invoice主键
     * @return 结果
     */
    @Override
    public int deleteFinanceBatchInvoiceByIds(Long[] ids)
    {
        return financeBatchInvoiceManager.deleteFinanceBatchInvoiceByIds(ids);
    }

    /**
     * 删除Finance Batch Invoice信息
     * 
     * @param id Finance Batch Invoice主键
     * @return 结果
     */
    @Override
    public int deleteFinanceBatchInvoiceById(Long id)
    {
        return financeBatchInvoiceManager.deleteFinanceBatchInvoiceById(id);
    }
}
