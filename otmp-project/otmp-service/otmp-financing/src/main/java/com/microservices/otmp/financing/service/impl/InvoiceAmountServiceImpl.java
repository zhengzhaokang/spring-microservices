package com.microservices.otmp.financing.service.impl;

import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.financing.constant.InvoiceConstants;
import com.microservices.otmp.financing.domain.entity.InvoiceAmountDo;
import com.microservices.otmp.financing.domain.entity.InvoiceAmountParamDo;
import com.microservices.otmp.financing.domain.param.invoice.InvoiceQueryParam;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceAmountInVo;
import com.microservices.otmp.financing.mapper.InvoiceAmountMapper;
import com.microservices.otmp.financing.service.InvoiceAmountService;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.feign.RemoteUserService;
import com.microservices.otmp.financing.constant.InvoiceConstants;
import com.microservices.otmp.financing.service.InvoiceAmountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InvoiceAmountServiceImpl implements InvoiceAmountService {

    @Autowired
    private InvoiceAmountMapper invoiceAmountMapper;
    @Autowired
    private RemoteUserService remoteUserService;

    private OtmpException throwEx(Long errCode) {
        return new OtmpException(DefaultErrorMessage.getErrorMessage(errCode), errCode.intValue());
    }

    private SysUser getSysUserByUserId(Long userId) {
        log.info("getSysUserByUserId,userId:{}", userId);
        SysUser sysUser = remoteUserService.selectSysUserByUserId(userId);
        if (sysUser == null) {
            log.info("submit,sysUser is null,userId:{}", userId);
            throw throwEx(DefaultErrorMessage.ERR_USER_NOT_EXIST);
        }
        return sysUser;
    }

    private String getSupplierIdByUserId(Long userId) {
        SysUser sysUser = getSysUserByUserId(userId);
        String supplierId = sysUser.getSupplierId();
        if (com.microservices.otmp.common.utils.StringUtils.isEmpty(supplierId)) {
            log.error("getSupplierIdByUserId,supplierId is null,userId:{}", userId);
            throw throwEx(DefaultErrorMessage.SUPPLIER_ID_NOT_FOUND);
        }
        return supplierId;
    }

    @Override
    public InvoiceAmountInVo availableAmount(InvoiceQueryParam param) {
        InvoiceAmountInVo invoiceAmountInVo = new InvoiceAmountInVo();
        InvoiceAmountParamDo paramDo = new InvoiceAmountParamDo();
        String supplierId = getSupplierIdByUserId(param.getUserId());
        log.info("availableAmount,supplierId:{},param:{}", supplierId, JsonUtil.toJSON(param));
        param.setSupplierId(supplierId);

        paramDo.setSupplierId(param.getSupplierId());
        paramDo.setInvoiceType(InvoiceConstants.INVOICE_TYPE_DEBIT_STR);
        List<InvoiceAmountDo> invoiceAmountDos = invoiceAmountMapper.availableAmount(paramDo);
        if (CollectionUtils.isNotEmpty(invoiceAmountDos)) {
            Map<String, String> invoiceAmountDetailMap = new HashMap<>();
            BigDecimal invoiceAmount = new BigDecimal(0L);
            BigDecimal invoiceCount = new BigDecimal(0L);
            for (InvoiceAmountDo invoiceAmountDo : invoiceAmountDos) {
                invoiceAmountDetailMap.put(invoiceAmountDo.getCompanyCode(), String.valueOf(invoiceAmountDo.getAmount()));
                invoiceAmount = invoiceAmount.add(invoiceAmountDo.getAmount());
                invoiceCount = invoiceCount.add(invoiceAmountDo.getCount());
            }
            invoiceAmountInVo.setInvoiceAmount(String.valueOf(invoiceAmount));
            invoiceAmountInVo.setInvoiceCount(String.valueOf(invoiceCount));
            invoiceAmountInVo.setInvoiceAmountDetailMap(invoiceAmountDetailMap);
        } else {
            invoiceAmountInVo.setInvoiceAmount("0");
            invoiceAmountInVo.setInvoiceCount("0");
        }

        paramDo.setInvoiceType(InvoiceConstants.INVOICE_TYPE_CREDIT_STR);
        List<InvoiceAmountDo> creditAmountDos = invoiceAmountMapper.availableAmount(paramDo);
        if (CollectionUtils.isNotEmpty(creditAmountDos)) {
            Map<String, String> creditAmountDetailMap = new HashMap<>();
            BigDecimal creditAmount = new BigDecimal(0L);
            BigDecimal creditCount = new BigDecimal(0L);
            for (InvoiceAmountDo creditAmountDo : creditAmountDos) {
                creditAmountDetailMap.put(creditAmountDo.getCompanyCode(), String.valueOf(creditAmountDo.getAmount()));
                creditAmount = creditAmount.add(creditAmountDo.getAmount());
                creditCount = creditCount.add(creditAmountDo.getCount());
            }
            invoiceAmountInVo.setCreditAmount(String.valueOf(creditAmount));
            invoiceAmountInVo.setCreditCount(String.valueOf(creditCount));
            invoiceAmountInVo.setCreditAmountDetailMap(creditAmountDetailMap);
        } else {
            invoiceAmountInVo.setCreditAmount("0");
            invoiceAmountInVo.setCreditCount("0");
        }

        return invoiceAmountInVo;
    }

    @Override
    public InvoiceAmountInVo submittedAmount(InvoiceQueryParam param) {
        InvoiceAmountParamDo paramDo = new InvoiceAmountParamDo();
        String supplierId = getSupplierIdByUserId(param.getUserId());
        log.info("submittedAmount,supplierId:{},param:{}", supplierId, JsonUtil.toJSON(param));
        param.setSupplierId(supplierId);
        paramDo.setSupplierId(param.getSupplierId());
        List<InvoiceAmountDo> invoiceAmountDos = invoiceAmountMapper.submittedAmount(paramDo);
        return getInvoiceAmountInVo(invoiceAmountDos);
    }

    private InvoiceAmountInVo getInvoiceAmountInVo(List<InvoiceAmountDo> invoiceAmountDos) {
        InvoiceAmountInVo invoiceAmountInVo = new InvoiceAmountInVo();
        BigDecimal netCount = new BigDecimal("0");
        BigDecimal netAmount = new BigDecimal("0");
        if (CollectionUtils.isNotEmpty(invoiceAmountDos)) {
            for (InvoiceAmountDo invoiceAmountDo : invoiceAmountDos) {
                if (StringUtils.equals(InvoiceConstants.INVOICE_TYPE_DEBIT_STR, invoiceAmountDo.getInvoiceType())) {
                    if (invoiceAmountDo.getCount() != null && invoiceAmountDo.getAmount() != null) {
                        invoiceAmountInVo.setInvoiceAmount(String.valueOf(invoiceAmountDo.getAmount().abs()));
                        netCount = netCount.add(invoiceAmountDo.getCount());
                        netAmount = netAmount.add(invoiceAmountDo.getAmount().abs());
                        invoiceAmountInVo.setInvoiceCount(String.valueOf(invoiceAmountDo.getCount()));
                    }
                } else if (StringUtils.equals(InvoiceConstants.INVOICE_TYPE_CREDIT_STR, invoiceAmountDo.getInvoiceType())) {
                    if (invoiceAmountDo.getCount() != null && invoiceAmountDo.getAmount() != null) {
                        netCount = netCount.add(invoiceAmountDo.getCount());
                        invoiceAmountInVo.setCreditAmount(String.valueOf(invoiceAmountDo.getAmount().abs()));
                        netAmount = netAmount.subtract(invoiceAmountDo.getAmount().abs());
                        invoiceAmountInVo.setCreditCount(String.valueOf(invoiceAmountDo.getCount()));
                    }
                }
            }
        } else {
            invoiceAmountInVo.setInvoiceAmount("0");
            invoiceAmountInVo.setCreditAmount("0");
        }
        invoiceAmountInVo.setNetAmount(String.valueOf(netAmount));
        invoiceAmountInVo.setNetCount(String.valueOf(netCount));
        return invoiceAmountInVo;
    }

    @Override
    public InvoiceAmountInVo rejectedAmount(InvoiceQueryParam param) {
        InvoiceAmountParamDo paramDo = new InvoiceAmountParamDo();
        String supplierId = getSupplierIdByUserId(param.getUserId());
        log.info("rejectedAmount,supplierId:{},param:{}", supplierId, JsonUtil.toJSON(param));
        param.setSupplierId(supplierId);
        paramDo.setSupplierId(param.getSupplierId());
        // invoice_ap 的Credit数据 负数， finance_invoice_ap的 Credit数据是正数
        List<InvoiceAmountDo> invoiceAmountDos = invoiceAmountMapper.rejectedAmount(paramDo);
        List<InvoiceAmountDo> apInvoiceAmountDos = invoiceAmountMapper.apRejectedAmount(paramDo);
        if (CollectionUtils.isEmpty(invoiceAmountDos)) {
            return getInvoiceAmountInVo(apInvoiceAmountDos);
        }
        if (CollectionUtils.isEmpty(apInvoiceAmountDos)) {
            return getInvoiceAmountInVo(invoiceAmountDos);
        }
        Map<String, InvoiceAmountDo> apInvoiceAmountMap = apInvoiceAmountDos.stream().collect(Collectors.
                toMap(InvoiceAmountDo::getInvoiceType, Function.identity(), (key1, key2) -> key1));
        for (InvoiceAmountDo invoiceAmountDo : invoiceAmountDos) {
            if (MapUtils.isEmpty(apInvoiceAmountMap)) {
                continue;
            }
            String invoiceType = invoiceAmountDo.getInvoiceType();
            InvoiceAmountDo apInvoiceDo = apInvoiceAmountMap.get(invoiceType);
            if (apInvoiceDo != null && invoiceAmountDo.getAmount() != null && invoiceAmountDo.getCount() != null
                    && apInvoiceDo.getAmount() != null && apInvoiceDo.getCount() != null) {
                // Credit数据取绝对值相加
                invoiceAmountDo.setAmount(invoiceAmountDo.getAmount().abs().add(apInvoiceDo.getAmount().abs()));
                invoiceAmountDo.setCount(invoiceAmountDo.getCount().add(apInvoiceDo.getCount()));
            }
        }
        return getInvoiceAmountInVo(invoiceAmountDos);
    }

    @Override
    public InvoiceAmountInVo financedAmount(InvoiceQueryParam param) {
        InvoiceAmountParamDo paramDo = new InvoiceAmountParamDo();
        String supplierId = getSupplierIdByUserId(param.getUserId());
        log.info("financedAmount,supplierId:{},param:{}", supplierId, JsonUtil.toJSON(param));
        param.setSupplierId(supplierId);
        paramDo.setSupplierId(param.getSupplierId());
        List<InvoiceAmountDo> invoiceAmountDos = invoiceAmountMapper.financedAmount(paramDo);
        return getInvoiceAmountInVo(invoiceAmountDos);
    }
}
