package com.microservices.otmp.bank.service.template;

import com.microservices.otmp.bank.domain.constant.BankBNPP;
import com.microservices.otmp.bank.domain.constant.BankDBS;
import com.microservices.otmp.bank.domain.dto.*;
import com.microservices.otmp.bank.mapper.SupplierBankSettingMapper;
import com.microservices.otmp.bank.util.FtpUtil;
import com.microservices.otmp.bank.util.PGPUtils;
import com.microservices.otmp.bank.util.PgpHelper;
import com.microservices.otmp.bank.util.XlsxtoCSV;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.finance.domain.dto.FinanceBatchInvoiceDTO;
import com.microservices.otmp.finance.mapper.FinanceInvoiceApMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * created with IntelliJ IDEA.
 * user: licf
 * Date: 2023/8/15
 * time: 15:52
 * Description:
 */
public abstract class BankTemplate extends FinancingTemplate {

    @Value("${bank.dbs.pgpPublicKey}")
    private String pgpPublicKey;//pgp公钥地址
    @Value("${bank.dbs.encryptedFile}")
    private String encryptedFile;//加密文件存储位置
    @Value("${bank.dbs.pgpSuffix}")
    private String pgpSuffix;//加密后的文件格式

    @Value("${bank.dbs.pgpPrivateKey}")
    private String pgpPrivateKey;//解密的私钥
    @Value("${bank.dbs.decryptedFile}")
    private String decryptedFile;//解密文件存储位置

    private String ftpAddress;//服务器ip地址
    private String ftpPort;//端口号
    private String ftpUserName;//用户名
    private String ftpPassword;//密码
    private String ftpBasePath;//文件在服务器中实际的位置
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private FinanceInvoiceApMapper financeInvoiceApMapper;
    @Autowired
    private SupplierBankSettingMapper supplierBankSettingMapper;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public BankFinancingDTO parseFinancingData(FinanceBatchInvoiceDTO financeBatchInvoice) {
        String batchNo = financeBatchInvoice.getBatchNumber();
        String supplierCode = financeBatchInvoice.getSupplierCode();
        String bankCode = financeBatchInvoice.getBankId();
        Long entityId = financeBatchInvoice.getEntityId();

        BankFinancingDTO bankUpload = new BankFinancingDTO();
        //获取DBS银行所需字段
        if (BankDBS.BANK_TYPE.equals(financeBatchInvoice.getBankId())) {
            bankUpload.setDbsList(financeInvoiceApMapper.getFinancingDataDBS(batchNo, supplierCode, bankCode,entityId));
        }
        if (BankBNPP.BANK_TYPE.equals(financeBatchInvoice.getBankId())) {
            //获取供应商信息
            String buyerOrgId = supplierBankSettingMapper.getSupplierBankSettingMapper(supplierCode, bankCode, entityId);
            //获取所需发票信息
            List<FinanceInvoiceApHasSupplierDTO> financeInvoiceApList = financeInvoiceApMapper.getFinanceInvoiceAp(batchNo, supplierCode, bankCode,entityId);
            FhDTO fhDTO = new FhDTO();
            List<CnDTO> cnList = new ArrayList<>();
            List<InvDTO> invList = new ArrayList<>();
            //获取标头数据
            fhDTO.setIdentifier(BankBNPP.FH);
            fhDTO.setSenderId(buyerOrgId);// supplier_bank_setting 的 buyer_org_id
            fhDTO.setFileName(getFileName(1, buyerOrgId));
            fhDTO.setFileCreationDate(new Date());
            fhDTO.setMicroservicesBatchNumber(getFileName(2, buyerOrgId));

            //获取内容
            financeInvoiceApList.forEach(financeInvoiceAp -> {
                try {
                    if ("1".equals(financeInvoiceAp.getInvoiceType())) {
                        //Credit Note

                        cnList.add(getCnDTO(financeInvoiceAp));

                    }
                    if ("2".equals(financeInvoiceAp.getInvoiceType())) {
                        //Debit Note
                        invList.add(getInvDTO(financeInvoiceAp));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            });

            bankUpload.setFhDTO(fhDTO);
            bankUpload.setCnList(cnList);
            bankUpload.setInvList(invList);
        }
        return bankUpload;
    }

    private InvDTO getInvDTO(FinanceInvoiceApHasSupplierDTO financeInvoiceAp) throws ParseException {
        InvDTO invDTO = new InvDTO();
        invDTO.setIdentifier(BankBNPP.INV);
        invDTO.setErpId(financeInvoiceAp.getSupplierUniqueId());//supplier_bank_setting 的 supplier_erp_id
        invDTO.setInvoiceReference(financeInvoiceAp.getInvoiceReference());//finance_invoice_ap 的 invoice_reference
        Date invoiceIssueDate = DateUtils.dateParse(financeInvoiceAp.getInvoiceIssueDate(), BankBNPP.DATE_FORMAT);
        invDTO.setInvoiceIssueDate(invoiceIssueDate);//finance_invoice_ap 的 issue_date
        Date maturityDate = DateUtils.dateParse(financeInvoiceAp.getMaturityDate(), BankBNPP.DATE_FORMAT);
        Date invoiceDueDate = DateUtils.dateParse(financeInvoiceAp.getInvoiceDueDate(), BankBNPP.DATE_FORMAT);
        invDTO.setInvoiceDueDate(maturityDate); //finance_invoice_ap 的 maturity_date
        invDTO.setAdditionalReference2(invoiceDueDate);//finance_invoice_ap 的 due_date
        invDTO.setInvoiceCurrency(financeInvoiceAp.getCurrency()); // supplier_bank_setting 的 currency
        invDTO.setInvoiceAmount(BigDecimal.ZERO.subtract(financeInvoiceAp.getInvoiceAmount())); //finance_invoice_ap 的 amount TODO INV 为负数
        invDTO.setAdditionalReference1(financeInvoiceAp.getInvoiceReference());//finance_invoice_ap 的 invoice_reference
        invDTO.setAdditionalReference3(financeInvoiceAp.getCompanyCode());//finance_invoice_ap 的 company_code
        return invDTO;
    }

    private CnDTO getCnDTO(FinanceInvoiceApHasSupplierDTO financeInvoiceAp) throws ParseException {
        CnDTO cnDTO = new CnDTO();
        cnDTO.setIdentifier(BankBNPP.CN);
        cnDTO.setErpId(financeInvoiceAp.getSupplierUniqueId());//supplier_bank_setting 的 supplier_erp_id
        cnDTO.setCreditNoteReference(financeInvoiceAp.getInvoiceReference());//finance_invoice_ap 的 invoice_reference
        Date creditNoteIssueDate = DateUtils.dateParse(financeInvoiceAp.getInvoiceIssueDate(), BankBNPP.DATE_FORMAT);
        cnDTO.setCreditNoteIssueDate(creditNoteIssueDate);//finance_invoice_ap 的 issue_date
        Date creditNoteEffectiveDate = DateUtils.dateParse(financeInvoiceAp.getMaturityDate(), BankBNPP.DATE_FORMAT);
        cnDTO.setCreditNoteEffectiveDate(creditNoteEffectiveDate); //finance_invoice_ap 的 maturity_date
        Date invoiceDueDate = DateUtils.dateParse(financeInvoiceAp.getInvoiceDueDate(), BankBNPP.DATE_FORMAT);
        cnDTO.setAdditionalReference2(invoiceDueDate);//finance_invoice_ap 的 due_date
        cnDTO.setCreditNoteCurrency(financeInvoiceAp.getCurrency());// supplier_bank_setting 的 currency
        cnDTO.setCreditNoteAmount(BigDecimal.ZERO.subtract(financeInvoiceAp.getInvoiceAmount()));//finance_invoice_ap 的 amount CN 为正数
        cnDTO.setAdditionalReference1(financeInvoiceAp.getInvoiceReference());//finance_invoice_ap 的 invoice_reference
        cnDTO.setAdditionalReference3(financeInvoiceAp.getCompanyCode());//finance_invoice_ap 的 company_code
        return cnDTO;
    }


    @Override
    public String encryptFile(BankFinancingDTO bankUpload, FinanceBatchInvoiceDTO financeBatchInvoice) throws Exception {
        //生成的数据整合
        StringBuffer assemble = new StringBuffer("");
        //文件名称
        String fileName = "";
        if (BankDBS.BANK_TYPE.equals(financeBatchInvoice.getBankId())) {
            assemble = XlsxtoCSV.assembleDBS(bankUpload.getDbsList());
            fileName = getFileName(3, bankUpload.getDbsList().get(0).getBuyer());
        }
        if (BankBNPP.BANK_TYPE.equals(financeBatchInvoice.getBankId())) {
            assemble = XlsxtoCSV.assembleBNPP(bankUpload);
            fileName = bankUpload.getFhDTO().getFileName();
        }
        byte[] bytes = assemble.toString().getBytes(StandardCharsets.UTF_8);
        //生成临时文件
        File file = File.createTempFile(fileName, "");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        //TODO 后续根据银行生成对应的加密文件
        String localFileName = encryptedFile + fileName + pgpSuffix;//文件存储位置
        FileInputStream pubKeyIs = new FileInputStream(pgpPublicKey);
        FileOutputStream cipheredFileIs = new FileOutputStream(localFileName);
        PgpHelper.getInstance().encryptFile(cipheredFileIs, fileName, PgpHelper.getInstance().readPublicKey(pubKeyIs), false, false);
        cipheredFileIs.close();
        pubKeyIs.close();
        return fileName + pgpSuffix;
    }

    /**
     * 上传到服务器
     */
    @Override
    public void sendToBank(String fileName, FinanceBatchInvoiceDTO financeBatchInvoice) throws Exception {
        FtpUtil fileUtil = new FtpUtil();
        // ftpAddress 服务器ip地址 ftpPort 端口号  ftpName 用户名  ftpPassWord 密码
//        fileUtil.uploadFile(fileName, Files.newInputStream(Paths.get(encryptedFile + fileName)), ftpAddress, Integer.parseInt(ftpPort), ftpUserName, ftpPassword, ftpBasePath);
    }


    /*
     * 1.从服务器上把数据拉取下来
     * 2.解密
     * 3.读取csv文件数据并解析,写入到数据库中
     */
    @Override
    public void pullToLocal(String fileUrl, String fileName) {
        //文件从服务器上下载下来
        FtpUtil fileUtil = new FtpUtil();
        fileUtil.downloadFile(ftpAddress, Integer.parseInt(ftpPort), ftpUserName, ftpPassword, ftpBasePath, fileUrl, fileName);
    }

    @Override
    public void decryptFile(String localFileName) throws Exception {
        File encryptedFileFile = new File(localFileName);
        PGPUtils.decrypt(encryptedFileFile, pgpPrivateKey, decryptedFile, "");
    }

    @Override
    public List<List<String>> parseFeedbackData(String localFileName) throws Exception {
        //读取csv文件，获取到数据信息
        FileReader fr = new FileReader(localFileName);
        List<List<String>> data = new ArrayList<>();
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        while (line != null) {
            List<String> lineData = Arrays.asList(line.split(","));//splitting lines
            data.add(lineData);
            line = br.readLine();
        }
        return data;
    }

    @Override
    public String getFileName(Integer type, String buyerOrgId) {
        String key = LocalDate.now().format(formatter);
        Long next = 1L;
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("000");
        switch (type) {
            case 1: // Invoice File
                next = redisUtils.increment(key, 1);
                return "PFLE002.ISC" + BankBNPP.FH + "." + buyerOrgId + ".D" + key + df.format(next) + ".csv";
            case 2: // microservicesBatchNumber
                next = Long.parseLong(redisUtils.get(key));
                return "BNP_LEN_" + key + df.format(next);
            case 3: // DBS FileName
                next = redisUtils.increment(key, 1);
                return "PFLE001.ISC" + BankDBS.SF + "." + buyerOrgId + ".D" + key + df.format(next) + ".csv";
            default:
                return null;
        }
    }


}
