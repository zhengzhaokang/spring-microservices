package com.microservices.otmp;

import com.google.common.collect.Lists;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.erp.domain.InvoiceMaturityAmountInfo;
import com.microservices.otmp.erp.domain.SupplierInvoiceInfo;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FinanceSupplierDataQueryServiceTest {

    @Test
    public void computeInvoiceMaturityAmount(){
        List<SupplierInvoiceInfo> suppliers = Lists.newArrayList();
        SupplierInvoiceInfo s1 = new SupplierInvoiceInfo();
        SupplierInvoiceInfo s2 = new SupplierInvoiceInfo();
        s1.setMaturityDate(new Date());
        s2.setMaturityDate(new Date());
        s1.setInvoiceAmount(new BigDecimal(100));
        s2.setInvoiceAmount(new BigDecimal(200));
        suppliers.add(s1);
        suppliers.add(s2);
        BigDecimal variable = new BigDecimal(-1);
        List<SupplierInvoiceInfo> sumSuppliers = suppliers.stream().collect(Collectors.toMap(supplier ->
                        DateUtils.dateTime(supplier.getMaturityDate()), supplier -> supplier,
                (si1, si2) ->{
                    si1.setInvoiceAmount(si1.getInvoiceAmount().add(si2.getInvoiceAmount()));
                    return si1;
                }
        )).values().stream().collect(Collectors.toList());
        log.info(String.valueOf(sumSuppliers.size()));
        log.info(JsonUtil.toJSON(sumSuppliers));
        List<InvoiceMaturityAmountInfo> invoiceMaturityAmountInfos = buildInvoiceMaturityAmountInfo(sumSuppliers, variable);
        List<InvoiceMaturityAmountInfo> finalInfo = invoiceMaturityAmountInfos.stream().collect(Collectors.toMap(info ->
                        DateUtils.dateTime(info.getMaturityDate()), info -> info,
                (i1, i2) ->{
                    i1.setAmount(i1.getAmount().add(i2.getAmount()));
                    return i1;
                }
        )).values().stream().collect(Collectors.toList());
        log.info(String.valueOf(finalInfo.size()));
        log.info(JsonUtil.toJSON(finalInfo));
    }

    public List<InvoiceMaturityAmountInfo> buildInvoiceMaturityAmountInfo(List<SupplierInvoiceInfo> sumSuppliers, BigDecimal variable){
        List<InvoiceMaturityAmountInfo> invoiceMaturityAmountInfos = Lists.newArrayList();
        Date date = new Date();
        sumSuppliers.stream().forEach(supplier ->{
            InvoiceMaturityAmountInfo info = new InvoiceMaturityAmountInfo();
            info.setAmount(supplier.getInvoiceAmount().multiply(variable));
            info.setCreateTime(date);
            info.setMaturityDate(supplier.getMaturityDate());
            info.setSupplierCode(supplier.getVendorCode());
            info.setStatus("0");
            invoiceMaturityAmountInfos.add(info);
        });
        return invoiceMaturityAmountInfos;
    }

}
