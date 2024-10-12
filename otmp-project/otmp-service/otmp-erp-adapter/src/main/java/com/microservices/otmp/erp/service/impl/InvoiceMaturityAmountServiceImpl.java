package com.microservices.otmp.erp.service.impl;


import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.erp.domain.InvoiceMaturityAmountInfo;
import com.microservices.otmp.erp.domain.vo.InvoiceMaturityAmountInfoVo;
import com.microservices.otmp.erp.mapper.InvoiceMaturityAmountInfoMapper;
import com.microservices.otmp.erp.service.InvoiceMaturityAmountSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceMaturityAmountServiceImpl implements InvoiceMaturityAmountSerice {

    @Autowired
    private InvoiceMaturityAmountInfoMapper invoiceMaturityAmountInfoMapper;
    @Autowired
    private RedisUtils redis;

    @Override
    public List<InvoiceMaturityAmountInfoVo> searchMaturityAmount(String bankId, String supplierId) {
       return invoiceMaturityAmountInfoMapper.findInvoiceBankMaturityAmountInfo(bankId,supplierId);
    }
}
