package com.microservices.otmp.masterdata.manager.impl;

import com.microservices.otmp.masterdata.domain.BizBaseSelfInvoice;
import com.microservices.otmp.masterdata.manager.IBizBaseSelfInvoiceManager;
import com.microservices.otmp.masterdata.mapper.BizBaseSelfInvoiceMapper;
import com.microservices.otmp.masterdata.util.ConvertStringToLongListUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * gtnCategoryManager业务层处理
 * 
 * @author lovefamily
 * @date 2023-06-05
 */
@Service
public class BizBaseSelfInvoiceManagerImpl implements IBizBaseSelfInvoiceManager
{
    private static final Logger log = LoggerFactory.getLogger(BizBaseSelfInvoiceManagerImpl.class);

    @Autowired
    private BizBaseSelfInvoiceMapper bizBaseSelfInvoiceMapper;

    @Override
    public BizBaseSelfInvoice selectBizBaseSelfInvoiceById(Long id) {
        return bizBaseSelfInvoiceMapper.selectBizBaseSelfInvoiceById(id);
    }

    @Override
    public List<BizBaseSelfInvoice> selectBizBaseSelfInvoiceList(BizBaseSelfInvoice bizBaseSelfInvoice) {
        List<Long> longIdsList = ConvertStringToLongListUtil.splitToLongList(bizBaseSelfInvoice.getIds());
        bizBaseSelfInvoice.setIdsList(longIdsList);
        return bizBaseSelfInvoiceMapper.selectBizBaseSelfInvoiceList(bizBaseSelfInvoice);
    }

    @Override
    public int insertBizBaseSelfInvoice(BizBaseSelfInvoice bizBaseSelfInvoice) {
        return bizBaseSelfInvoiceMapper.insertBizBaseSelfInvoice(bizBaseSelfInvoice);
    }

    @Override
    public int updateBizBaseSelfInvoice(BizBaseSelfInvoice bizBaseSelfInvoice) {
        log.info("bizBaseSelfInvoice update:{}",bizBaseSelfInvoice);
        return bizBaseSelfInvoiceMapper.updateBizBaseSelfInvoice(bizBaseSelfInvoice);
    }

    @Override
    public int deleteBizBaseSelfInvoiceByIds(Long[] ids) {
        return bizBaseSelfInvoiceMapper.deleteBizBaseSelfInvoiceByIds(ids);
    }

    @Override
    public int deleteBizBaseSelfInvoiceById(Long id) {
        return bizBaseSelfInvoiceMapper.deleteBizBaseSelfInvoiceById(id);
    }
}
