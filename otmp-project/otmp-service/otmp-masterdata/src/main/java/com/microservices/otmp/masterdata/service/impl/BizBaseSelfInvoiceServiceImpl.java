package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.masterdata.domain.BizBaseSelfInvoice;
import com.microservices.otmp.masterdata.manager.IBizBaseSelfInvoiceManager;
import com.microservices.otmp.masterdata.service.IBizBaseSelfInvoiceService;
import com.microservices.otmp.masterdata.util.ConvertStringToLongListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BizBaseSelfInvoiceService业务层处理
 *
 * @author xiaozy8
 * @date 2022-09-28
 */
@Service
public class BizBaseSelfInvoiceServiceImpl implements IBizBaseSelfInvoiceService
{
    @Autowired
    private IBizBaseSelfInvoiceManager bizBaseSelfInvoiceManager;

    @Override
    public BizBaseSelfInvoice selectBizBaseSelfInvoiceById(Long id) {
        return bizBaseSelfInvoiceManager.selectBizBaseSelfInvoiceById(id);
    }

    @Override
    public List<BizBaseSelfInvoice> selectBizBaseSelfInvoiceList(BizBaseSelfInvoice bizBaseSelfInvoice) {
        List<Long> longIdsList = ConvertStringToLongListUtil.splitToLongList(bizBaseSelfInvoice.getIds());
        bizBaseSelfInvoice.setIdsList(longIdsList);
        return bizBaseSelfInvoiceManager.selectBizBaseSelfInvoiceList(bizBaseSelfInvoice);
    }

    @Override
    public int insertBizBaseSelfInvoice(BizBaseSelfInvoice bizBaseSelfInvoice) {
        return bizBaseSelfInvoiceManager.insertBizBaseSelfInvoice(bizBaseSelfInvoice);
    }

    @Override
    public int updateBizBaseSelfInvoice(BizBaseSelfInvoice bizBaseSelfInvoice) {
        return bizBaseSelfInvoiceManager.updateBizBaseSelfInvoice(bizBaseSelfInvoice);
    }

    @Override
    public int deleteBizBaseSelfInvoiceByIds(Long[] ids) {
        return bizBaseSelfInvoiceManager.deleteBizBaseSelfInvoiceByIds(ids);
    }

    @Override
    public int deleteBizBaseSelfInvoiceById(Long id) {
        return bizBaseSelfInvoiceManager.deleteBizBaseSelfInvoiceById(id);
    }

}
