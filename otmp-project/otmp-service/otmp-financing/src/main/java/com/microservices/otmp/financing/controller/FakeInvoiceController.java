package com.microservices.otmp.financing.controller;

import cn.hutool.core.date.DateUtil;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.financing.constant.StringConstants;
import com.microservices.otmp.financing.domain.entity.FinanceInvoiceApDo;
import com.microservices.otmp.financing.mapper.FinanceInvoiceApMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RequestMapping("fake")
@RestController
public class FakeInvoiceController extends BaseController {

    @Autowired
    private FinanceInvoiceApMapper mapper;

    private static final int BATCH_SIZE = 500;
    private static final Random RANDOM = new Random();
    private static final List<String> CODES = new ArrayList<>();

    @GetMapping("make")
    public ResultDTO<Boolean> create(@RequestParam(value = "prefix", required = false) String prefix
            , @RequestParam(value = "amount", required = false) Double amount
            , @RequestParam("companyCode") String companyCode
            , @RequestParam("vendorCode") String vendorCode
            , @RequestParam("issueDate") String issueDate
            , @RequestParam("baselineDate") String baselineDate
            , @RequestParam("dueDate") String dueDate
            , @RequestParam("enterDate") String enterDate
            , @RequestParam("payDate") String payDate
            , @RequestParam("maturityDate") String maturityDate
            , @RequestParam("type") String type
            , @RequestParam("count") int count) {

        CODES.clear();
        int remainder = count % BATCH_SIZE;
        int limit = remainder == 0 ? count / BATCH_SIZE : count / BATCH_SIZE + 1;
        Date createTime = new Date();
        int numberCount = 0;
        for (int i = 0; i < limit; i++) {
            List<FinanceInvoiceApDo> invoices = new ArrayList<>();
            int loopCount = (i == limit - 1) && remainder != 0 ? remainder : BATCH_SIZE;
            for (int j = 0; j < loopCount; j++) {
                String invoiceRef = prefix + StringConstants.MID_LINE + randomNumberStr(9);
                invoices.add(createInvoice(invoiceRef, amount, numberCount++, companyCode, vendorCode, issueDate, baselineDate, dueDate, enterDate, payDate, maturityDate, type, createTime));
            }
            mapper.insertListWithId(invoices);
        }

        return ResultDTO.success(true);
    }

    private FinanceInvoiceApDo createInvoice(String invoiceRef,Double amount, int count, String companyCode, String vendorCode, String issueDate, String baselineDate, String dueDate, String enterDate, String payDate, String maturityDate, String type, Date createTime) {
        FinanceInvoiceApDo item = new FinanceInvoiceApDo();
        item.setId(SnowFlakeUtil.nextId());
        item.setCompanyCode(companyCode);
        item.setVendorCode(vendorCode);
        item.setFiscalYear(String.valueOf(DateUtil.thisYear()));
        item.setInvoiceAdditionalReference(randomNumberStr(10));

        item.setInvoiceIssueDate(issueDate);
        item.setInvoiceBaselineDate(baselineDate);
        item.setInvoiceDueDate(dueDate);
        item.setInvoiceCurrency("USD");
        item.setEnterDate(enterDate);
        item.setInvoicePayDate(payDate);

        item.setInvoiceType(type);
        item.setInvoiceReference(invoiceRef);
        item.setMaturityDate(getMd(maturityDate));
        item.setStatus("Waiting");

        item.setDeleteFlag(false);
        item.setCreateTime(createTime);
        item.setCreateBy("system");

        if(amount == null || amount <= 0.0) {
            item.setInvoiceAmount((double) RANDOM.nextInt(9000) + 1000);
        }else {
            item.setInvoiceAmount(amount);
        }

        item.setInvoiceUniqueId(uniqueInvoiceId(item, count));
        return item;
    }

    private static LocalDateTime getMd(String maturityDate) {
        try {
            Date date = DateUtils.parseDate(maturityDate, DateUtils.DATE_NO_PATTERN);
            return dateToLocalDateTime(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private static String uniqueInvoiceId(FinanceInvoiceApDo invoice, int count) {
        return invoice.getCompanyCode() + StringConstants.UNDER_LINE
                + invoice.getVendorCode() + StringConstants.UNDER_LINE
                + invoice.getFiscalYear() + StringConstants.UNDER_LINE
                + invoice.getInvoiceAdditionalReference() + StringConstants.UNDER_LINE
                + format(String.valueOf(count), 6);
    }

    private static String randomNumberStr(int len) {
        String code = String.valueOf(RANDOM.nextInt(Integer.MAX_VALUE >> 2));
//        String code = String.valueOf(RANDOM.nextInt(50));
        while(CODES.contains(code)){
            code = randomNumberStr(len);
        }
        CODES.add(code);
        return format(code,len);
    }

    public static void main(String[] args) {
        for(int i = 0;i<15;i++){
            System.out.println(randomNumberStr(5));
        }
    }

    private static String format(String code, int len) {
        if (code.length() < len) {
            code = String.format("%" + len + "s", code).replace(" ", "0");
        }
        return code;
    }

//    public static void main(String[] args) {
//        System.out.println(randomNumberStr(10));
//    }
}
