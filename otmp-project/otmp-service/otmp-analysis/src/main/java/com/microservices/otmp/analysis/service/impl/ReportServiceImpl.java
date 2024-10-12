package com.microservices.otmp.analysis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.microservices.otmp.analysis.common.domain.QueryAccountsPayableParam;
import com.microservices.otmp.analysis.common.domain.SupplierAccountsValue;
import com.microservices.otmp.analysis.domain.dto.FinanceBatchDTO;
import com.microservices.otmp.analysis.domain.vo.SupplierCompanyCodeInfo;
import com.microservices.otmp.analysis.domain.vo.SupplierInvoiceInfoVo;
import com.microservices.otmp.analysis.mapper.SupplierInvoiceMapper;
import com.microservices.otmp.analysis.service.IReportService;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.enums.BatchStatusEnum;
import com.microservices.otmp.common.enums.InvoiceStatusEnum;
import com.microservices.otmp.common.utils.StringUtils;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    private SupplierInvoiceMapper supplierInvoiceMapper;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public List<SupplierAccountsValue> findAccountsFinancingReport(QueryAccountsPayableParam param, List<SupplierCompanyCodeInfo> supplierCompanyCodeInfo) {
        List<String> supplierCode = getVenderCode(supplierCompanyCodeInfo);
        if(!StringUtils.isEmpty(param.getTypeValue())) {
            param.setTypeValue(param.getTypeValue().replaceAll("-", "").replaceAll("/", ""));
        }
        return supplierInvoiceMapper.findAccountsFinancingAmount(param,supplierCode);
    }

    @Override
    public List<SupplierInvoiceInfoVo> findSupplierInvoiceReport(QueryAccountsPayableParam param, List<SupplierCompanyCodeInfo> supplierCompanyCodeInfo) {
        List<String> supplierCodes = getVenderCode(supplierCompanyCodeInfo);
        List<String> companyCodes = getCompanyCode(supplierCompanyCodeInfo);
        if(!StringUtils.isEmpty(param.getTypeValue())) {
            param.setTypeValue(param.getTypeValue().replaceAll("-", "").replaceAll("/", ""));
        }
        List<SupplierInvoiceInfoVo> suppliers = supplierInvoiceMapper.findSupplierInvoice(param, supplierCodes, companyCodes);
        buildNameData(suppliers, supplierCompanyCodeInfo);
        return suppliers;
    }

    @Override
    public List<SupplierCompanyCodeInfo> findSupplierCode(QueryAccountsPayableParam param) {
        return supplierInvoiceMapper.findSupplierCode(param);
    }

    private void buildNameData(List<SupplierInvoiceInfoVo> financeBatchList, List<SupplierCompanyCodeInfo> supplierCompanyCodeInfo) {
        Map<String, SupplierCompanyCodeInfo> maps = Maps.newHashMap();
        supplierCompanyCodeInfo.forEach(su ->{
            maps.put(su.getSupplierCode().concat(su.getCompanyCode()), su);
        });
        financeBatchList.stream().forEach(item -> {
            SupplierCompanyCodeInfo info = maps.get(item.getVendorCode().concat(item.getCompanyCode()));
            if(null != info) {
                item.setEntityName(info.getEntityName());
                item.setSupplierName(info.getSupplierName());
            }
        });
    }

    public List<String> getVenderCode(List<SupplierCompanyCodeInfo> supplierCompanyCodeInfo){
        List<String> supplierCode = null;
        if(supplierCompanyCodeInfo != null){
            supplierCode = Lists.newArrayList();
            List<String> finalSupplierCode = supplierCode;
            supplierCompanyCodeInfo.stream().forEach(su ->{
                finalSupplierCode.add(su.getSupplierCode());
            });
            finalSupplierCode.stream().distinct();
            supplierCode = finalSupplierCode;
        }
        return supplierCode;
    }

    public List<String> getCompanyCode(List<SupplierCompanyCodeInfo> supplierCompanyCodeInfo){
        List<String> companyCode = null;
        if(supplierCompanyCodeInfo != null){
            companyCode = Lists.newArrayList();
            List<String> finalSupplierCode = companyCode;
            supplierCompanyCodeInfo.stream().forEach(su ->{
                finalSupplierCode.add(su.getCompanyCode());
            });
            finalSupplierCode.stream().distinct();
            companyCode = finalSupplierCode;
        }
        return companyCode;
    }
}
