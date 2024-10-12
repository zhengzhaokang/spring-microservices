package com.microservices.otmp.bank.service.strategy.exec;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.csv.CsvRow;
import com.microservices.otmp.bank.domain.constant.BankTransferConstant;
import com.microservices.otmp.bank.domain.dto.BankCommonResponseItemInfoDTO;
import com.microservices.otmp.bank.domain.dto.BankFinancingDTO;
import com.microservices.otmp.bank.domain.dto.BankTransferTaskInfoDTO;
import com.microservices.otmp.bank.domain.dto.ParamDTO;
import com.microservices.otmp.bank.kafka.BankInvoiceTransferKafkaSender;
import com.microservices.otmp.bank.service.IBankTransferTaskInfoService;
import com.microservices.otmp.bank.service.strategy.IBankStrategy;
import com.microservices.otmp.bank.util.FtpUtil;
import com.microservices.otmp.bank.util.PgpHelper;
import com.microservices.otmp.bank.util.SftpUtil;
import com.microservices.otmp.bank.util.XlsxtoCSV;
import com.microservices.otmp.common.enums.BatchStatusEnum;
import com.microservices.otmp.common.enums.InvoiceStatusEnum;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.finance.domain.dto.FinanceBatchDTO;
import com.microservices.otmp.finance.domain.dto.FinanceBatchInvoiceDTO;
import com.microservices.otmp.finance.domain.dto.FinanceInvoiceApDTO;
import com.microservices.otmp.finance.service.IFinanceBatchService;
import com.microservices.otmp.finance.service.IFinanceInvoiceApService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 基于不同银行抽象的公共基类用于实现通用功能
 */
@Slf4j
public abstract class BaseBankStrategy implements IBankStrategy {


    @Autowired
    private SftpUtil sftpUtil;

    @Autowired
    protected IBankTransferTaskInfoService bankTransferTaskInfoService;

    @Autowired
    private IFinanceInvoiceApService financeInvoiceApService;

    @Autowired
    private IFinanceBatchService financeBatchService;

    @Autowired
    private BankInvoiceTransferKafkaSender bankInvoiceTransferKafkaSender;


    /**
     * 获取发票信息
     *
     * @param financeBatchInvoice 客户银行对应对象
     * @return BankFinancingDTO
     */
    public abstract BankFinancingDTO parseFinancingData(FinanceBatchInvoiceDTO financeBatchInvoice);


    /**
     * 构建基础FTP参数
     *
     * @return ParamDTO
     */
    public abstract ParamDTO buildParamDTO();

    /**
     * 获取加密所需参数
     *
     * @return ParamDTO
     */
    public abstract ParamDTO getEncryptParamDTO(BankFinancingDTO bankFinancingDTO);

    /**
     * 补齐上传到银行所需参数
     *
     * @param paramDTO 参数
     * @return paramDTO
     */
    public abstract ParamDTO getSendParamDTO(ParamDTO paramDTO);

    @Override
    public void submit(FinanceBatchInvoiceDTO financeBatchInvoice) throws Exception {
        //获取发票数据
        BankFinancingDTO bankFinancingDTO = parseFinancingData(financeBatchInvoice);
        //获取加密所需参数
        ParamDTO paramDTO = getEncryptParamDTO(bankFinancingDTO);
        //String path = "D:\\test\\request\\" + paramDTO.getFileName();
        XlsxtoCSV.writeCsv(paramDTO.getRequestFilePath(), paramDTO.getFileName(), paramDTO.getAssemble());
        //XlsxtoCSV.writeCsv( "D:\\test\\request\\", paramDTO.getFileName(), paramDTO.getAssemble());
        //文件加密
        encryptFile(paramDTO);
        //补齐 上传到银行所需参数
        paramDTO = getSendParamDTO(paramDTO);
        //保存传输任务到数据库
        saveBankTransferTaskInfo(financeBatchInvoice, paramDTO);
        //上传到对应的银行
        // sendToBank(paramDTO);
    }

    private int saveBankTransferTaskInfo(FinanceBatchInvoiceDTO financeBatchInvoice, ParamDTO paramDTO) {
        int resultNum = 0;
        BankTransferTaskInfoDTO bankTransferTaskInfo = new BankTransferTaskInfoDTO();
        bankTransferTaskInfo.setBankTransferId(SnowFlakeUtil.nextId());
        bankTransferTaskInfo.setOperType(BankTransferConstant.BANK_TRANSFER_REQUEST);
        bankTransferTaskInfo.setMicroservicesBatchNumber(financeBatchInvoice.getBatchNumber());
        bankTransferTaskInfo.setBankCode(financeBatchInvoice.getBankName());
//        bankTransferTaskInfo.setSupplierCode(financeBatchInvoice.getSupplierCode());
        bankTransferTaskInfo.setRequestFileName(paramDTO.getFileName());
        bankTransferTaskInfo.setEntityId(financeBatchInvoice.getEntityId());
        bankTransferTaskInfo.setCreateTime(DateUtils.getNowDate());
        bankTransferTaskInfo.setStatus(BatchStatusEnum.SUBMITTED.code);
        //清空文件数据-存储到bankTransferTaskInfo
        paramDTO.setAssemble(null);
        bankTransferTaskInfo.setParamData(JsonUtil.toJSON(paramDTO));
        resultNum = bankTransferTaskInfoService.insertBankTransferTaskInfo(bankTransferTaskInfo);
        return resultNum;
    }

//    /**
//     * 生成文件名称
//     *
//     * @return String
//     */
//    protected abstract String getFileName(String buyerOrgId);


//    /**
//     * 构建基础FTP传输数据
//     *
//     * @return Map<String, List < Map < String, Object>>>
//     */
//    protected abstract Map<String, List<Map<String, Object>>> buildTransferDataMapList(List<BankTransferMetadataInfoDTO> bankTransferMetadataInfoDTOList, FinanceBatchInvoice financeBatchInvoice);


    /**
     * 加密
     *
     * @param paramDTO 参数
     */
    public void encryptFile(ParamDTO paramDTO) throws Exception {
        byte[] bytes = paramDTO.getAssemble().toString().getBytes(StandardCharsets.UTF_8);
        //生成临时文件
        File file = File.createTempFile(paramDTO.getFileName(), "");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        FileInputStream pubKeyIs = new FileInputStream(paramDTO.getPgpPublicKey());
        FileOutputStream cipheredFileIs = new FileOutputStream(paramDTO.getEncryptFileUrl());
        PgpHelper.getInstance().encryptFile(cipheredFileIs, file.getPath(), PgpHelper.getInstance().readPublicKey(pubKeyIs), false, false);
        cipheredFileIs.close();
        pubKeyIs.close();
    }

    public static void main(String[] args) throws Exception {

        String ttt = "{\"assemble\":\"FH,HKmicroservices,PFLE002.ISCFH.HKmicroservices.D20231019001.csv,20231019,BNPP_20231019_000001,\\nINV,1000036945,9954547314,20230714,20231113,USD,-532.71,0700473323,20230916,HK08,,\\nINV,1000036945,9954547779,20230714,20231113,USD,-29412.00,0700473727,20230917,HK08,,\\nINV,1000036945,9954548061,20230714,20231113,USD,-553.76,0700474173,20230917,HK08,,\\nINV,1000036945,9954548753,20230714,20231113,USD,-11677.50,0700474718,20230917,HK08,,\\nINV,1000036945,9954548757,20230714,20231113,USD,-494.67,0700474722,20230917,HK08,,\\nINV,1000036945,9954549064,20230714,20231113,USD,-519.62,0700474837,20230917,HK08,,\\nINV,1000036945,9954549294,20230714,20231113,USD,-860.84,0700474977,20230917,HK08,,\\nINV,1000036945,9954549297,20230714,20231113,USD,-423871.00,0700474980,20230917,HK08,,\\nINV,1000036945,8212647970,20230714,20231113,USD,-6621.27,0700489572,20230917,HK08,,\\nINV,1000036945,8212653696,20230715,20231113,USD,-427.18,0700489573,20230917,HK08,,\\nINV,1000036945,8212621989,20230707,20231113,USD,-70994.52,0700489568,20230917,HK08,,\\n\",\"decryptedFile\":\"/home/banktest/responseFile/bnpp/PAYABLE_REPORTS/\",\"decryptedOriginalFile\":\"/home/banktest/responseFile/bnpp/originalFile/\",\"encryptFileUrl\":\"D:\\\\test\\\\request\\\\PFLE002.ISCFH.HKmicroservices.D20231019001.csv.pgp\",\"encryptedFile\":\"D:\\\\test\\\\request\\\\\",   \"fileName\": \"PFLE002.ISCFH.HKmicroservices.D20231019001.csv\",   \"ftpAddress\": \"203.116.104.211\",   \"ftpBaseDownloadPath\": \"\",   \"ftpBasePath\": \"PAYABLE_INVOICE/\",   \"ftpPassword\": \"Q8Secure$6@\",   \"ftpPort\": 2222,   \"ftpUserName\": \"HKLNV\",   \"localDirPath\": \"\",   \"localFilePath\": \"D:\\\\test\\\\request\\\\\",   \"pgpPrivateKey\": \"C:\\\\Users\\\\T14\\\\Desktop\\\\otfp\\\\prod_key\\\\microservices_prod_pgp_pri.asc\",   \"pgpPublicKey\": \"C:\\\\Users\\\\T14\\\\Desktop\\\\otfp\\\\prod_key\\\\microservices_prod_pgp_pub.asc\",   \"pgpSuffix\": \".pgp\",   \"privateKeyPath\": \"\",   \"requestFilePath\": \"D:\\\\test\\\\request\\\\\" }";
        ParamDTO paramDTO = JsonUtil.jsonToObject(ttt, ParamDTO.class);
//        byte[] bytes = paramDTO.getAssemble().toString().getBytes(StandardCharsets.UTF_8);
//        //生成临时文件
//        File file = File.createTempFile(paramDTO.getFileName(), "");
//        FileOutputStream fos = new FileOutputStream(file);
//        fos.write(bytes);
//        FileInputStream pubKeyIs = new FileInputStream(paramDTO.getPgpPublicKey());
//        FileOutputStream cipheredFileIs = new FileOutputStream(paramDTO.getEncryptFileUrl());
//        PgpHelper.getInstance().encryptFile(cipheredFileIs, file.getPath(), PgpHelper.getInstance().readPublicKey(pubKeyIs), false, false);
//        cipheredFileIs.close();
//        pubKeyIs.close();


        // String decryptedFile = paramDTO.getDecryptedFile() + responseFileName;
        String pgpPrivateKey = paramDTO.getPgpPrivateKey();
        FileInputStream fileInputStream = new FileInputStream("D:\\test\\request\\PFLE002.ISCFH.HKmicroservices.D20231019001.csv.pgp");
        // FileInputStream fileInputStream = new FileInputStream(decryptedFile);
//        FileInputStream priKeyIs = new FileInputStream(pgpPrivateKey);
        FileInputStream priKeyIs = new FileInputStream("C:\\Users\\T14\\Desktop\\otfp\\prod_key\\microservices_prod_pgp_pri.asc");
        // PgpHelper.getInstance().decryptFile(fileInputStream,priKeyIs,null,fileDataList);
        String decryptedOriginalFile = paramDTO.getDecryptedOriginalFile() + "PFLE002.ISCFH.HKmicroservices.D20231019001.csv.pgp".replace("pgg", "csv");
        OutputStream out = new FileOutputStream("D:\\test\\decrypt\\PFLE002.ISCFH.HKmicroservices.D20231019001.csv");
        log.info(decryptedOriginalFile);

        //  OutputStream out = new FileOutputStream(decryptedOriginalFile);

        PgpHelper.getInstance().decryptFile(fileInputStream, out, priKeyIs, null);
        fileInputStream.close();
        priKeyIs.close();
        out.close();

    }


    /**
     * 上传到服务器
     */
    public void sendToBank(ParamDTO paramDTO) throws Exception {
//        FtpUtil fileUtil = new FtpUtil();
//        // ftpAddress 服务器ip地址 ftpPort 端口号  ftpName 用户名  ftpPassWord 密码
//        fileUtil.uploadFile(paramDTO.getFileName() + paramDTO.getPgpSuffix(),
//                Files.newInputStream(Paths.get(paramDTO.getEncryptedFile() + paramDTO.getFileName() + paramDTO.getPgpSuffix())),
//                paramDTO.getFtpAddress(), paramDTO.getFtpPort(), paramDTO.getFtpUserName(), paramDTO.getFtpPassword(), paramDTO.getFtpBasePath());
        uploadFileToSftp(paramDTO);
    }

    private void uploadFileToSftp(ParamDTO paramDTO) {
        String ipAddress = paramDTO.getFtpAddress();
        String port = paramDTO.getFtpPort() + "";
        String privateKeyPath = paramDTO.getPrivateKeyPath();
        // String privateKeyPath = ftpPrivateKeyPath;
        String username = paramDTO.getFtpUserName();
        String passphrase = paramDTO.getFtpPassword();
        String remoteFilePath = paramDTO.getFtpBasePath();
        String remoteFileName = paramDTO.getFileName();
        String localFilePath = paramDTO.getLocalDirPath();
        String password = paramDTO.getFtpPassword();
        try {

            sftpUtil.connectWithPublicKey(ipAddress, Integer.parseInt(port), username, password, privateKeyPath, passphrase);
            System.out.println("sftpUtilUpload  Start.");
            sftpUtil.uploadFile(remoteFilePath, remoteFileName, localFilePath, remoteFileName);
            System.out.println("sftpUtilUpload  end.remoteFilePath=" + remoteFilePath);
        } catch (Exception e) {
            log.error("sftpUtilUpload Request Error:{}", e);
        }

    }

//
//    /**
//     * 生成文件名称
//     *
//     * @param type 发票类型
//     * @return String
//     */
//    public String getFileName(Integer type, String buyerOrgId) {
//        String key = LocalDate.now().format(formatter);
//        Long next = 1L;
//        DecimalFormat df = new DecimalFormat();
//        df.applyPattern("000");
//        switch (type) {
//            case 1: // Invoice File  BNPP
//                next = redisUtils.increment(key, 1);
//                return "PFLE002.ISC" + BankBNPP.FH + "." + buyerOrgId + ".D" + key + df.format(next) + ".csv";
//            case 2: // microservicesBatchNumber  BNPP
//                next = Long.parseLong(redisUtils.get(key));
//                return "BNP_LEN_" + key + df.format(next);
//            case 3: // DBS FileName  DBS
//                next = redisUtils.increment(key, 1);
//                return "PFLE001.ISC" + BankDBS.SF + "." + buyerOrgId + ".D" + key + df.format(next) + ".csv";
//            default:
//                return null;
//        }
//    }


    /**
     * 从服务器拉取数据到本地
     */
    @Override
    public void pullFileToLocal() {
        ParamDTO paramDTO = buildParamDTO();
        paramDTO.setLocalDirPath("D:\\test\\response\\");
        FtpUtil fileUtil = new FtpUtil();
        // ftpAddress 服务器ip地址 ftpPort 端口号  ftpName 用户名  ftpPassWord 密码
        try {
            fileUtil.getFileDir(paramDTO.getLocalDirPath(), paramDTO.getFtpAddress(), paramDTO.getFtpPort(), paramDTO.getFtpUserName(), paramDTO.getFtpPassword(), paramDTO.getFtpBaseDownloadPath());
        } catch (Exception e) {
            log.error("Pull File To Local Error:{},e:{}", e, e.getMessage());
        }
    }

    /**
     * 从服务器拉取数据到本地
     *
     * @param fileUrl  存储路径
     * @param fileName 文件名字
     */
    public abstract void pullToLocal(String fileUrl, String fileName);

    /**
     * 解密文件
     *
     * @param paramDTO
     */
    public abstract void decryptFile(ParamDTO paramDTO) throws Exception;

    /**
     * 解析回馈的数据 存入数据库
     *
     * @param paramDTO
     * @return Integer
     */
    public abstract List<CsvRow> parseFeedbackData(ParamDTO paramDTO) throws Exception;

    /**
     * 收取发票数据 生成加密文件 上传到服务器
     *
     * @param batchNo 编码
     */
    public void financing(Integer batchNo, String supplierCode, String bankCode) throws Exception {
//        sendToBank(encryptFile(parseFinancingData(batchNo, supplierCode, bankCode)));
    }

    /**
     * 拉取银行反馈文件并解密 读取解密后的文件获得数据 保存数据到数据库
     *
     * @param fileUrl 文件存储路径
     */
    public List<List<String>> feedback(String fileUrl, String fileName, String localFileName) throws Exception {
        //文件下载到本地
        pullToLocal(fileUrl, fileName);
        //解密
//        decryptFile(fileUrl + fileName);
//        //读取解密后的csv文件
//        parseFeedbackData(fileUrl + localFileName);
        return null;
    }

    public void updateLocalDbData(BankCommonResponseItemInfoDTO bankCommonResponseItemInfoDTO) throws Exception {
        updateFinanceBatchAndInvoicInfo(bankCommonResponseItemInfoDTO);
    }

    public void updateFinanceBatchAndInvoicInfo(BankCommonResponseItemInfoDTO bankCommonResponseItemInfoDTO) {
        String txnStatus = bankCommonResponseItemInfoDTO.getTxnStatus();
        String externalRefNo = bankCommonResponseItemInfoDTO.getExternalRefNo();
        String loanorinterestRate = bankCommonResponseItemInfoDTO.getLoanorinterestRate();
        String financeAmount = bankCommonResponseItemInfoDTO.getFinanceAmount();
        String interestAmt = bankCommonResponseItemInfoDTO.getInterestAmt();
        String tenor = bankCommonResponseItemInfoDTO.getTenor();
        Date dueDate = bankCommonResponseItemInfoDTO.getDueDate();
        Date financeDate = bankCommonResponseItemInfoDTO.getFinanceDate();
        String txnStatusDescription = bankCommonResponseItemInfoDTO.getTxnStatusDescription();
        FinanceBatchDTO financeBatch = new FinanceBatchDTO();
        financeBatch.setBatchNumber(externalRefNo);
        List<FinanceBatchDTO> financeBatchDTOList = financeBatchService.selectFinanceBatchList(financeBatch);
        if (CollectionUtil.isNotEmpty(financeBatchDTOList)) {
            FinanceBatchDTO oldFinanceBatch = financeBatchDTOList.get(0);
            financeBatch.setBankId(oldFinanceBatch.getBankId());
            String oldStatus = oldFinanceBatch.getStatus();
            if (!InvoiceStatusEnum.REJECTED.code.equals(oldStatus) && !InvoiceStatusEnum.FINANCED.code.equals(oldStatus)) {
                financeBatch.setStatusDescription(txnStatusDescription);
            }
        } else {
            return;
        }

        //对应Rejected
        if ("RJCT".equals(txnStatus)) {
            financeBatch.setStatus(InvoiceStatusEnum.REJECTED.code);
            financeBatch.setStatusDescription(txnStatusDescription);

        }
        //对应Financed
        if ("ACSP".equals(txnStatus)) {
            financeBatch.setDiscountDate(financeDate);
            financeBatch.setTenor(tenor);
            if (!Objects.isNull(dueDate)) {
                financeBatch.setMaturityDate(DateUtils.parseDateToStr("yyyy-MM-dd", dueDate));
            }
            if (StringUtils.isEmpty(loanorinterestRate)) {
                loanorinterestRate = "0.00";
            }
            financeBatch.setInterestRate(new BigDecimal(loanorinterestRate));
            if (StringUtils.isEmpty(financeAmount)) {
                financeAmount = "0.00";
            }
            if (StringUtils.isEmpty(interestAmt)) {
                interestAmt = "0.00";
            }
            financeBatch.setDiscountAmount(new BigDecimal(interestAmt));
            financeBatch.setDiscount(new BigDecimal(financeAmount));
            financeBatch.setStatus(InvoiceStatusEnum.FINANCED.code);
            financeBatch.setStatusDescription(txnStatusDescription);

        }
        int num = financeBatchService.updateBatchInvoiceAndOtherInfo(financeBatch);
        log.info("updateBatchInvoiceAndOtherInfo success.");
        if (num > 0) {
            // updateInvoiceApInfo(bankCommonResponseItemInfoDTO);
            log.info("update FinanceInvoiceAp success stop.");
        }
    }

    private void updateInvoiceApInfo(BankCommonResponseItemInfoDTO bankCommonResponseItemInfoDTO) {

        String responseType = bankCommonResponseItemInfoDTO.getResponseType();
        String txnStatus = bankCommonResponseItemInfoDTO.getTxnStatus();
        String txnRefNo = bankCommonResponseItemInfoDTO.getTxnRefNo();
        String loanorinterestRate = bankCommonResponseItemInfoDTO.getLoanorinterestRate();
        String txnStatusDescription = bankCommonResponseItemInfoDTO.getTxnStatusDescription();

        if (StringUtils.isEmpty(loanorinterestRate)) {
            loanorinterestRate = "0.00";
        }
        BigDecimal interestRate = new BigDecimal(loanorinterestRate);
        Date updateDateTime = DateUtils.getNowDate();

        FinanceInvoiceApDTO queryFinanceInvoiceAp = new FinanceInvoiceApDTO();
        queryFinanceInvoiceAp.setInvoiceReference(txnRefNo);
        queryFinanceInvoiceAp.setStatus(InvoiceStatusEnum.FINANCING.code);
        List<FinanceInvoiceApDTO> financeInvoiceApDTOS = financeInvoiceApService.selectFinanceInvoiceApList(queryFinanceInvoiceAp);

        if (CollectionUtil.isEmpty(financeInvoiceApDTOS)) {
            return;
        }

        if ("ACK1A".equals(responseType)) {
            if ("RJCT".equals(txnStatus)) {
                FinanceInvoiceApDTO financeInvoiceAp = new FinanceInvoiceApDTO();
                financeInvoiceAp.setStatusUpdateDate(updateDateTime);
                financeInvoiceAp.setStatusDescription(txnStatusDescription);
                financeInvoiceAp.setStatus(InvoiceStatusEnum.REJECTED.code);
                financeInvoiceApService.updateFinanceInvoiceApOfBatch(financeInvoiceApDTOS, financeInvoiceAp);

            }
        }
        if ("ACK2A".equals(responseType)) {
            if ("RJCT".equals(txnStatus)) {
                FinanceInvoiceApDTO financeInvoiceAp = new FinanceInvoiceApDTO();
                financeInvoiceAp.setStatusUpdateDate(updateDateTime);
                financeInvoiceAp.setStatusDescription(txnStatusDescription);
                financeInvoiceAp.setStatus(InvoiceStatusEnum.REJECTED.code);
                financeInvoiceApService.updateFinanceInvoiceApOfBatch(financeInvoiceApDTOS, financeInvoiceAp);

            }
        }

        if ("ACK2B".equals(responseType)) {
            if ("ACCP".equals(txnStatus)) {
                FinanceInvoiceApDTO financeInvoiceAp = new FinanceInvoiceApDTO();
                financeInvoiceAp = financeInvoiceApDTOS.get(0);
                financeInvoiceAp.setStatusUpdateDate(updateDateTime);
                financeInvoiceAp.setStatusDescription(txnStatusDescription);
                financeInvoiceApService.updateFinanceInvoiceApOfBatch(financeInvoiceApDTOS, financeInvoiceAp);

            }
            if ("RJCT".equals(txnStatus)) {
                FinanceInvoiceApDTO financeInvoiceAp = new FinanceInvoiceApDTO();
                financeInvoiceAp = financeInvoiceApDTOS.get(0);
                financeInvoiceAp.setStatusUpdateDate(updateDateTime);
                financeInvoiceAp.setStatusDescription(txnStatusDescription);
                financeInvoiceAp.setStatus(InvoiceStatusEnum.REJECTED.code);
                financeInvoiceApService.updateFinanceInvoiceApOfBatch(financeInvoiceApDTOS, financeInvoiceAp);

            }
        }

        if ("ACK3B".equals(responseType)) {
            if ("ACSP".equals(txnStatus)) {

                FinanceInvoiceApDTO financeInvoiceAp = new FinanceInvoiceApDTO();
                financeInvoiceAp = financeInvoiceApDTOS.get(0);
                financeInvoiceAp.setInterestRate(interestRate);
                financeInvoiceAp.setStatusUpdateDate(updateDateTime);
                financeInvoiceAp.setStatusDescription(txnStatusDescription);
                financeInvoiceAp.setStatus(InvoiceStatusEnum.FINANCED.code);
                financeInvoiceApService.updateFinanceInvoiceApOfBatch(financeInvoiceApDTOS, financeInvoiceAp);

            }
            if ("RJCT".equals(txnStatus)) {

                FinanceInvoiceApDTO financeInvoiceAp = new FinanceInvoiceApDTO();
                financeInvoiceAp = financeInvoiceApDTOS.get(0);
                financeInvoiceAp.setStatusUpdateDate(updateDateTime);
                financeInvoiceAp.setStatusDescription(txnStatusDescription);
                financeInvoiceAp.setStatus(InvoiceStatusEnum.REJECTED.code);
                financeInvoiceApService.updateFinanceInvoiceApOfBatch(financeInvoiceApDTOS, financeInvoiceAp);

            }
        }
    }

    protected List<BankTransferTaskInfoDTO> updateFinanceBatch(List<BankCommonResponseItemInfoDTO> bankCommonResponseItemInfoDTOList) {
        log.info("UpdateFinanceBatch Start.");
        Map<String, List<BankCommonResponseItemInfoDTO>> bankCommonResponseItemInfoListMap =
                bankCommonResponseItemInfoDTOList.stream().
                        collect(Collectors.groupingBy(BankCommonResponseItemInfoDTO::getExternalRefNo));

        List<BankTransferTaskInfoDTO> insertBankTransferTaskInfoList = new ArrayList<>();
        for (Map.Entry entry : bankCommonResponseItemInfoListMap.entrySet()) {
            List<BankCommonResponseItemInfoDTO> mapValue = (List<BankCommonResponseItemInfoDTO>) entry.getValue();
            BankCommonResponseItemInfoDTO tempBankCommonResponseItemInfoDTO = mapValue.get(0);
            String externalRefNo = tempBankCommonResponseItemInfoDTO.getExternalRefNo();
            String responseStatus = tempBankCommonResponseItemInfoDTO.getTxnStatus();
            String responseStatusDescription = tempBankCommonResponseItemInfoDTO.getTxnStatusDescription();
            BankTransferTaskInfoDTO insertBankTransferTaskInfo = new BankTransferTaskInfoDTO();
            insertBankTransferTaskInfo.setMicroservicesBatchNumber(externalRefNo);
            insertBankTransferTaskInfo.setResponseStatus(responseStatus);
            insertBankTransferTaskInfo.setResponseStatusDescription(responseStatusDescription);
            insertBankTransferTaskInfoList.add(insertBankTransferTaskInfo);
            updateFinanceBatchAndInvoicInfo(tempBankCommonResponseItemInfoDTO);
        }
        log.info("UpdateFinanceBatch End.");

        return insertBankTransferTaskInfoList;
    }

    protected void updateFinanceBatchByFinanceBatch(List<FinanceBatchDTO> financeBatchDTOList) {
        log.info("UpdateFinanceBatchByFinanceBatch Start.");
        int resultNum = 0;
        for (FinanceBatchDTO financeBatch : financeBatchDTOList) {
            resultNum += financeBatchService.updateBatchInvoiceAndOtherInfo(financeBatch);
        }
        log.info("UpdateFinanceBatchByFinanceBatch End.Success Number:{}", resultNum);

    }


    @Async("bankTransferTaskExecutor")
    public void updateBankTransferTaskInfo(String responseFileName, List<BankTransferTaskInfoDTO> insertBankTransferTaskInfoList) {
        BankTransferTaskInfoDTO queryBankTransferTaskInfo = new BankTransferTaskInfoDTO();
        queryBankTransferTaskInfo.setResponseFileName(responseFileName);
        queryBankTransferTaskInfo.setOperType(BankTransferConstant.BANK_TRANSFER_RESPONSE);
        List<BankTransferTaskInfoDTO> bankTransferTaskInfoDTOList = bankTransferTaskInfoService.selectBankTransferTaskInfoList(queryBankTransferTaskInfo);
        if (CollectionUtil.isNotEmpty(bankTransferTaskInfoDTOList)) {
            //获取到存入的记录
            BankTransferTaskInfoDTO bankTransferTaskInfo = bankTransferTaskInfoDTOList.get(0);
            //通过解析的csv数据更新上batch number 先删除 再插入（可能会插入多条）
            bankTransferTaskInfoService.deleteBankTransferTaskInfoByBankTransferId(bankTransferTaskInfo.getBankTransferId());
            log.info("ResponseFileName:{},Old Recod Delete Success.", responseFileName);
            for (BankTransferTaskInfoDTO insertBankTransferTaskInfo : insertBankTransferTaskInfoList) {
                Long bankTransferId = SnowFlakeUtil.nextId();
                bankTransferTaskInfo.setBankTransferId(bankTransferId);
                bankTransferTaskInfo.setResponseStatus(insertBankTransferTaskInfo.getResponseStatus());
                bankTransferTaskInfo.setResponseStatusDescription(insertBankTransferTaskInfo.getResponseStatusDescription());
                bankTransferTaskInfo.setMicroservicesBatchNumber(insertBankTransferTaskInfo.getMicroservicesBatchNumber());
                bankTransferTaskInfo.setUpdateTime(DateUtils.getNowDate());
                bankTransferTaskInfo.setUpdateBy("UPDATE_BATCH");
                bankTransferTaskInfoService.insertBankTransferTaskInfo(bankTransferTaskInfo);
            }
            log.info("ResponseFileName:{},Update Batch Number Success.", responseFileName);

        }

    }


}
