package com.microservices.otmp.bank.service.strategy.exec;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.CharsetUtil;
import com.microservices.otmp.bank.domain.constant.BankBNPP;
import com.microservices.otmp.bank.domain.constant.BankTransferConstant;
import com.microservices.otmp.bank.domain.dto.*;
import com.microservices.otmp.bank.domain.dto.*;
import com.microservices.otmp.bank.mapper.SupplierBankSettingMapper;
import com.microservices.otmp.bank.service.IBankTransferMetadataInfoService;
import com.microservices.otmp.bank.util.PgpHelper;
import com.microservices.otmp.bank.util.XlsxtoCSV;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.BigDecimalUtil;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.finance.domain.dto.FinanceBatchDTO;
import com.microservices.otmp.finance.domain.dto.FinanceBatchInvoiceDTO;
import com.microservices.otmp.finance.mapper.FinanceInvoiceApMapper;
import com.microservices.otmp.bank.service.IBankTransferMetadataInfoService;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.BigDecimalUtil;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.finance.domain.dto.FinanceBatchDTO;
import com.microservices.otmp.finance.domain.dto.FinanceBatchInvoiceDTO;
import com.microservices.otmp.finance.mapper.FinanceInvoiceApMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * BNPP提交实现
 */
@Service
@Slf4j
public class BnppBankStrategyTemplate extends BaseBankStrategy {

    @Value("${bank.bnpp.pgpPublicKey}")
    private String pgpPublicKey;//pgp公钥地址
    @Value("${bank.bnpp.pgpPrivateKey}")
    private String pgpPrivateKey;//解密的私钥
    @Value("${bank.bnpp.encryptedFile}")
    private String encryptedFile;//加密文件存储位置
    @Value("${bank.bnpp.pgpSuffix}")
    private String pgpSuffix;//加密后的文件格式

    @Value("${bank.bnpp.requestFilePath}")
    private String requestFilePath;//生成的原始csv文件存储路径
    @Value("${bank.bnpp.localFilePath}")
    private String localFilePath;//生成的csv文件加密后存储路径


    @Value("${bank.bnpp.decryptedFile}")
    private String decryptedFile;//待解密文件存储位置
    @Value("${bank.bnpp.decryptedOriginalFile}")
    private String decryptedOriginalFile;//解密后文件存储位置
    @Value("${ftp.bnpp.address}")
    private String ftpAddress;//服务器ip地址
    @Value("${ftp.bnpp.port}")
    private String ftpPort;//端口号
    @Value("${ftp.bnpp.userName}")
    private String ftpUserName;//用户名
    @Value("${ftp.bnpp.password}")
    private String ftpPassword;//密码
    @Value("${ftp.bnpp.basePath}")
    private String ftpBasePath;//文件在服务器中实际的位置
    @Value("${ftp.bnpp.baseDownloadPath}")
    private String ftpBaseDownloadPath;//response文件在服务器中实际的位置
    @Value("${ftp.bnpp.privateKeyPath}")
    private String ftpPrivateKeyPath;//生成的原始csv文件存储路径

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IBankTransferMetadataInfoService bankTransferMetadataInfoService;


    @Autowired
    private SupplierBankSettingMapper supplierBankSettingMapper;

    @Autowired
    private FinanceInvoiceApMapper financeInvoiceApMapper;


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");


    public String strategyType() {
        return BankBNPP.BANK_TYPE;
    }

    public BankFinancingDTO parseFinancingData(FinanceBatchInvoiceDTO financeBatchInvoice) {
        BankFinancingDTO bankUpload = new BankFinancingDTO();
        /**
         * 组装CSV与第三方系统所需数据
         * 1、通过配置表获取需要传输的元数据信息
         * 2、根据元数据配置信息对于固定逻辑数据生成对数据对应的值、根据元数据配置信息对于动态数据来源的数据去各自服务获取数据
         */

        BankTransferMetadataInfoDTO bankTransferMetadataInfo = new BankTransferMetadataInfoDTO();
        bankTransferMetadataInfo.setTransferType(BankTransferConstant.BANK_TRANSFER_REQUEST);
        bankTransferMetadataInfo.setBankType(strategyType());
        List<BankTransferMetadataInfoDTO> bankTransferMetadataInfoDTOList = bankTransferMetadataInfoService.selectBankTransferMetadataInfoList(bankTransferMetadataInfo);

        //获取供应商信息
        String buyerOrg = getBuyerOrg(financeBatchInvoice);
        //生成文件名
        String fileName = getFileName(buyerOrg);
        //构建传输数据
        Map<String, List<Map<String, Object>>> transferDataMap = buildTransferDataMapList(bankTransferMetadataInfoDTOList, financeBatchInvoice, fileName);
        bankUpload.setFileName(fileName);
        bankUpload.setTransferDataMap(transferDataMap);
        return bankUpload;
    }

    private String getBuyerOrg(FinanceBatchInvoiceDTO financeBatchInvoice) {
        return supplierBankSettingMapper.getSupplierBankSettingMapper(financeBatchInvoice.getSupplierCode(), financeBatchInvoice.getBankId(), financeBatchInvoice.getEntityId());
    }

    private Map<String, List<Map<String, Object>>> buildTransferDataMapList(List<BankTransferMetadataInfoDTO> bankTransferMetadataInfoDTOList, FinanceBatchInvoiceDTO financeBatchInvoice, String fileName) {

        Map<String, Object> transferDataColumnFormatMap = new LinkedHashMap<>();

        Map<String, Map<String, Object>> returnTransferDataColumnMap = new HashMap<>();

        Map<String, List<BankTransferMetadataInfoDTO>> transferGroupDataListMap = bankTransferMetadataInfoDTOList.stream().collect(Collectors.groupingBy(BankTransferMetadataInfoDTO::getFieldType));

        for (String key : transferGroupDataListMap.keySet()) {
            Map<String, Object> transferDataColumnMap = new LinkedHashMap<>();

            List<BankTransferMetadataInfoDTO> gropuBankTransferMetadataInfoDTOList = transferGroupDataListMap.get(key);
            gropuBankTransferMetadataInfoDTOList.sort((x, y) -> x.getSort() - y.getSort());
            gropuBankTransferMetadataInfoDTOList.forEach(bankTransferMetadataInfoItem -> {
                String transferColumn = bankTransferMetadataInfoItem.getJavaField();
                String fieldFormat = bankTransferMetadataInfoItem.getFieldFormat();
                String javaType = bankTransferMetadataInfoItem.getJavaType();
                transferDataColumnMap.put(transferColumn, bankTransferMetadataInfoItem.getColumnName());
                //构建字段格式化配置
                if (StringUtils.isNotEmpty(javaType)) {
                    buildFormatMap(transferDataColumnMap, transferDataColumnFormatMap, transferColumn, fieldFormat, javaType);
                }
            });
            returnTransferDataColumnMap.put(key, transferDataColumnMap);
        }
        /**
         *  构建数据
         */
        Map<String, List<Map<String, Object>>> resultTransferDataMap = new HashMap<>();
        resultTransferDataMap = buidResultTransferData(financeBatchInvoice, transferDataColumnFormatMap, returnTransferDataColumnMap, resultTransferDataMap, fileName);
        return resultTransferDataMap;
    }


    //构建传输结果数据
    private Map<String, List<Map<String, Object>>> buidResultTransferData(FinanceBatchInvoiceDTO financeBatchInvoice, Map<String, Object> transferDataColumnFormatMap, Map<String, Map<String, Object>> returnTransferDataColumnMap, Map<String, List<Map<String, Object>>> resultTransferDataMap, String fileName) {

        String batchNo = financeBatchInvoice.getBatchNumber();
        String supplierCode = financeBatchInvoice.getSupplierCode();
        String bankId = financeBatchInvoice.getBankId();
        Long entityId = financeBatchInvoice.getEntityId();
        //获取供应商信息
        String buyerOrg = supplierBankSettingMapper.getSupplierBankSettingMapper(supplierCode, bankId, entityId);
        //获取所需发票信息
        List<Map<String, Object>> financeInvoiceApMapList = financeInvoiceApMapper.findFinanceInvoiceApMap(batchNo, supplierCode, bankId, entityId);

        for (String key : returnTransferDataColumnMap.keySet()) {
            Map<String, Object> transferDataColumnMap = returnTransferDataColumnMap.get(key);
            List<Map<String, Object>> transferDataMapList = new ArrayList<>();
            if (BankBNPP.TRANSFER_BODY.equals(key)) {
                financeInvoiceApMapList.forEach(invoiceApMap -> {
                    Map<String, Object> transferDataMap = new LinkedHashMap<>();
                    //按照字段配置构建格式化数据
                    buildFormatTransferData(transferDataColumnMap, transferDataColumnFormatMap, invoiceApMap, transferDataMap);
                    transferDataMapList.add(transferDataMap);
                });
            }
            if (BankBNPP.TRANSFER_HEADER.equals(key)) {
                Map<String, Object> headerDataMap = new LinkedHashMap<>();
                headerDataMap.put("Identifier", BankBNPP.FH);
                headerDataMap.put("SenderId", buyerOrg);
                headerDataMap.put("FileName", fileName);
                headerDataMap.put("FileCreationDate", DateUtils.parseDateToStr("yyyyMMdd", DateUtils.parseDate(LocalDate.now())));
                headerDataMap.put("microservicesBatchNumber", batchNo);
                transferDataMapList.add(headerDataMap);
            }
            resultTransferDataMap.put(key, transferDataMapList);
        }
        return resultTransferDataMap;
    }

    //构建字段格式化配置
    private void buildFormatMap(Map<String, Object> transferDataColumnMap, Map<String, Object> transferDataColumnFormatMap, String transferColumn, String fieldFormat, String javaType) {
        if (StringUtils.isNotEmpty(fieldFormat)) {
            transferDataColumnFormatMap.put(transferColumn, javaType + ":" + fieldFormat);

        } else {
            if (StringUtils.isNotEmpty(javaType) && "Date".equals(javaType)) {
                transferDataColumnMap.put(transferColumn, "Date" + ":" + BankBNPP.DATE_FORMAT);
            }
            if (StringUtils.isNotEmpty(javaType) && "BigDecimal".equals(javaType)) {
                transferDataColumnMap.put(transferColumn, "BigDecimal" + ":" + BankBNPP.BIGDECIMAL_FORMAT);
            }
            if (StringUtils.isNotEmpty(javaType) && "FixedValue".equals(javaType)) {
                transferDataColumnMap.put(transferColumn, "FixedValue" + ":" + "");
            }
        }
    }

    //按照字段配置构建格式化数据
    private void buildFormatTransferData(Map<String, Object> transferDataColumnMap, Map<String, Object> transferDataColumnFormatMap, Map<String, Object> invoiceApMap, Map<String, Object> transferDataMap) {
        transferDataColumnMap.forEach((k, v) -> {
            if (Objects.isNull(v)) {
                transferDataMap.put(k, v);
            } else {
                Object value = invoiceApMap.get(v);
                if (transferDataColumnFormatMap.containsKey(k)) {
                    String columnFormatStr = transferDataColumnFormatMap.get(k).toString();
                    String columnFormatStrArray[] = columnFormatStr.split(":");
                    if ("Date".equals(columnFormatStrArray[0])) {
                        if (!Objects.isNull(value)) {
                            String type = value.getClass().getSimpleName();
                            if ("Timestamp".equals(type)) {
                                value = value.toString().split("\\.")[0];
                            }
                        }
                        value = Objects.isNull(value) ? value : DateUtils.parseDateToStr(columnFormatStrArray[1], DateUtils.parseDate(value));
                    }
                    if ("BigDecimal".equals(columnFormatStrArray[0])) {
                        value = Objects.isNull(value) ? value : BigDecimalUtil.formatPrecision(new BigDecimal(String.valueOf(value)), Integer.valueOf(columnFormatStrArray[1]), RoundingMode.HALF_UP);
                    }
                    if ("FixedValue".equals(columnFormatStrArray[0])) {
                        if (("Credit Memo").equals(String.valueOf(value))) {
                            value = BankBNPP.CN;
                        }
                        if (("Debit Memo").equals(String.valueOf(value))) {
                            value = BankBNPP.INV;
                        }
                    }
                }
                transferDataMap.put(k, value);

            }
        });

        String identifier = transferDataMap.get("INV-Identifier") == null ? "" : transferDataMap.get("INV-Identifier").toString();
        if ("INV".equals(identifier)) {
            String amount = transferDataMap.get("INV-Invoice Amount").toString();
            BigDecimal bigDecimalValue = new BigDecimal(String.valueOf(amount));
            transferDataMap.put("INV-Invoice Amount", bigDecimalValue.negate());
        }
    }

    /**
     * 生成文件名称
     *
     * @return String
     */
    private String getFileName(String buyerOrgId) {
        String key = LocalDate.now().format(formatter);
        Long next = 1L;
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("000");
        next = redisUtils.increment(key, 1);
        return "PFLE002.ISC" + BankBNPP.FH + "." + buyerOrgId + ".D" + key + df.format(next) + ".csv";
    }


    /**
     * 生成microservicesBatchNumber
     *
     * @return String
     */
    private String getmicroservicesBatchNumber(String buyerOrgId) {
        String key = LocalDate.now().format(formatter);
        Long next = 1L;
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("000");
        next = Long.parseLong(redisUtils.get(key));
        return "BNP_LEN_" + key + df.format(next);

    }

    @Override
    public ParamDTO buildParamDTO() {
        ParamDTO paramDTO = new ParamDTO();
        paramDTO.setPgpPublicKey(pgpPublicKey);
        paramDTO.setEncryptedFile(encryptedFile);
        paramDTO.setPgpSuffix(pgpSuffix);
        paramDTO.setPgpPrivateKey(pgpPrivateKey);
        paramDTO.setDecryptedFile(decryptedFile);
        paramDTO.setDecryptedOriginalFile(decryptedOriginalFile);
        paramDTO.setFtpAddress(ftpAddress);
        paramDTO.setFtpPort(Integer.parseInt(ftpPort));
        paramDTO.setFtpUserName(ftpUserName);
        paramDTO.setFtpPassword(ftpPassword);
        paramDTO.setFtpBasePath(ftpBasePath);
        paramDTO.setFtpBaseDownloadPath(ftpBaseDownloadPath);
        paramDTO.setPrivateKeyPath(ftpPrivateKeyPath);
        // paramDTO.setFileName(bankUpload.getFileName());
        //  paramDTO.setEncryptFileUrl(encryptedFile + paramDTO.getFileName() + pgpSuffix);
        return paramDTO;
    }

    @Override
    public ParamDTO getEncryptParamDTO(BankFinancingDTO bankUpload) {
        ParamDTO paramDTO = new ParamDTO();
        paramDTO.setPgpPublicKey(pgpPublicKey);
        paramDTO.setEncryptedFile(encryptedFile);
        paramDTO.setPgpSuffix(pgpSuffix);
        paramDTO.setPgpPrivateKey(pgpPrivateKey);
        paramDTO.setDecryptedFile(decryptedFile);
        paramDTO.setDecryptedOriginalFile(decryptedOriginalFile);
        paramDTO.setFtpAddress(ftpAddress);
        paramDTO.setFtpPort(Integer.parseInt(ftpPort));
        paramDTO.setFtpUserName(ftpUserName);
        paramDTO.setFtpPassword(ftpPassword);
        paramDTO.setFtpBasePath(ftpBasePath);
        paramDTO.setFtpBaseDownloadPath(ftpBaseDownloadPath);
        paramDTO.setPrivateKeyPath(ftpPrivateKeyPath);
        paramDTO.setFileName(bankUpload.getFileName());
        paramDTO.setAssemble(XlsxtoCSV.buildCsvData(bankUpload.getTransferDataMap(), false));
        paramDTO.setEncryptFileUrl(encryptedFile + paramDTO.getFileName() + pgpSuffix);
        paramDTO.setRequestFilePath(requestFilePath + strategyType());
        paramDTO.setLocalFilePath(localFilePath);
        return paramDTO;
    }

    @Override
    public ParamDTO getSendParamDTO(ParamDTO paramDTO) {


        return paramDTO;
    }

    @Override
    public void pullToLocal(String fileUrl, String fileName) {


    }

    @Override
    public void decryptFile(ParamDTO paramDTO) throws Exception {
        String responseFileName = paramDTO.getResponseFileName();
        String decryptedFile = paramDTO.getDecryptedFile() + responseFileName;
        String pgpPrivateKey = paramDTO.getPgpPrivateKey();
        log.info("DecryptFile:{} Start.", decryptedFile);
        FileInputStream fileInputStream = new FileInputStream(decryptedFile);
        FileInputStream priKeyIs = new FileInputStream(pgpPrivateKey);
        String decryptedOriginalFile = paramDTO.getDecryptedOriginalFile() + responseFileName.replace("ppg", "csv");
        log.info("decryptedOriginalFile:{}.", decryptedOriginalFile);
        OutputStream out = new FileOutputStream(decryptedOriginalFile);
        PgpHelper.getInstance().decryptFile(fileInputStream, out, priKeyIs, null);
        log.info("DecryptFile End.", decryptedOriginalFile);
        fileInputStream.close();
        priKeyIs.close();
        out.close();
    }


    @Override
    public List<CsvRow> parseFeedbackData(ParamDTO paramDTO) throws Exception {
        String responseFileName = paramDTO.getResponseFileName();
        String originalFilePath = paramDTO.getDecryptedOriginalFile() + responseFileName;
        log.info("ResponseFileName:{},OriginalFilePath:{}", responseFileName, originalFilePath);
        CsvData csvData =
                CsvUtil.getReader().read(FileUtil.file(originalFilePath), CharsetUtil.CHARSET_UTF_8);
        if (csvData != null) {
            saveResponseData(csvData.getRows(), responseFileName);
            return csvData.getRows();
        }
        return null;
    }


    private void saveResponseData(List<CsvRow> csvRowList, String responseFileName) {
        Map<String, Object> resultMap = new HashMap<>();

        log.info("csvRowList:{}", JsonUtil.toJSON(csvRowList));
        log.info("responseFileName:{}", responseFileName);
        if (responseFileName.contains(BankBNPP.FILE_TYPE_DIR)) {
            processDirResponse(csvRowList, responseFileName);
        } else if (responseFileName.contains(BankBNPP.FILE_TYPE_ADF)) {
            processAdfResponse(csvRowList, responseFileName);
        } else if (CollectionUtil.isNotEmpty(csvRowList) && csvRowList.size() >= 1) {
            processOtherResponse(csvRowList, responseFileName);
        }
        log.info("SaveResponseData end & success.");

    }

    private void processOtherResponse(List<CsvRow> csvRowList, String responseFileName) {
        CsvRow csvRow1 = csvRowList.get(0);
        String requestFileName = csvRow1.get(3);
        String fileValidationStatus = csvRow1.get(5);
        BankTransferTaskInfoDTO queryBankTransferTaskInfo = new BankTransferTaskInfoDTO();
        queryBankTransferTaskInfo.setRequestFileName(requestFileName);
        queryBankTransferTaskInfo.setOperType(BankTransferConstant.BANK_TRANSFER_REQUEST);
        List<BankTransferTaskInfoDTO> bankTransferTaskInfoDTOList = bankTransferTaskInfoService.selectBankTransferTaskInfoList(queryBankTransferTaskInfo);
        List<BankTransferTaskInfoDTO> insertBankTransferTaskInfoList = new ArrayList<>();

        if (CollectionUtil.isNotEmpty(bankTransferTaskInfoDTOList)) {
            CsvRow csvRow2 = csvRowList.get(1);
            String responseStatusDescription = csvRow2.get(12);
            //获取到Request的记录
            BankTransferTaskInfoDTO bankTransferTaskInfo = bankTransferTaskInfoDTOList.get(0);
            String microservicesBatchNumber = bankTransferTaskInfo.getMicroservicesBatchNumber();
            BankTransferTaskInfoDTO insertBankTransferTaskInfo = new BankTransferTaskInfoDTO();
            insertBankTransferTaskInfo.setMicroservicesBatchNumber(microservicesBatchNumber);
            insertBankTransferTaskInfo.setResponseStatus(fileValidationStatus);
            insertBankTransferTaskInfo.setResponseStatusDescription(responseStatusDescription);
            insertBankTransferTaskInfoList.add(insertBankTransferTaskInfo);

            BankCommonResponseItemInfoDTO tempBankCommonResponseItemInfoDTO = new BankCommonResponseItemInfoDTO();
            if (BankBNPP.FILE_VALIDATION_FAILED.equals(fileValidationStatus)) {
                tempBankCommonResponseItemInfoDTO.setTxnStatus("RJCT");
                tempBankCommonResponseItemInfoDTO.setTxnStatusDescription(responseStatusDescription);
            } else {
                tempBankCommonResponseItemInfoDTO.setTxnStatus("ACTC");
            }
            tempBankCommonResponseItemInfoDTO.setExternalRefNo(microservicesBatchNumber);
            updateFinanceBatchAndInvoicInfo(tempBankCommonResponseItemInfoDTO);
            log.info("updateFinanceBatchAndInvoicInfo success.");

            updateBankTransferTaskInfo(responseFileName, insertBankTransferTaskInfoList);
            log.info("updateBankTransferTaskInfo success.");

        }
    }

    private void processAdfResponse(List<CsvRow> csvRowList, String responseFileName) {
        List<Integer> dataIndexList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(csvRowList) && csvRowList.size() >= 1) {
            log.info("csvRowList size:{}", csvRowList.size());
            for (int i = 0; i < csvRowList.size(); i++) {
                CsvRow csvRow = csvRowList.get(i);
                String csvRowTitle = csvRow.get(0);
                if (BankBNPP.CELL_TITLE_BNP_BRANCH.equals(csvRowTitle)) {
                    dataIndexList.add(i + 1);
                }
            }

            /**
             * 处理header数据
             *
             */
            int headerInvoiceFinacingIndexStart = dataIndexList.get(0);
            int headerInvoiceFinacingIndexEnd = dataIndexList.get(1);
            log.info("headerInvoiceFinacingIndexStart:{},headerInvoiceFinacingIndexEnd:{}", headerInvoiceFinacingIndexStart, headerInvoiceFinacingIndexEnd - 2);
            List<CsvRow> headerInvoiceFinacingList = csvRowList.subList(headerInvoiceFinacingIndexStart, headerInvoiceFinacingIndexEnd - 2);
            List<BankTransferTaskInfoDTO> insertBankTransferTaskInfoList = new ArrayList<>();

            for (int f = 0; f < headerInvoiceFinacingList.size(); f++) {
                CsvRow csvRow = headerInvoiceFinacingList.get(f);
                log.info("csvRow:{}", csvRow);
                String microservicesBatchNumber = csvRow.get(21);//microservices Batch Number
                String discountErrorCode = csvRow.get(14);//Discount Error Code
                String discountErrorMessage = csvRow.get(15);//Discount Error Message
                String fileName = csvRow.get(17);//File Name
                String fileStatus = csvRow.get(18);//File Status
                String paymentReference = csvRow.get(6);//Payment Reference

                log.info("fileName:{}", fileName);
                log.info("fileStatus:{}", fileStatus);
                log.info("discountErrorCode:{}", discountErrorCode);
                log.info("discountErrorMessage:{}", discountErrorMessage);
                log.info("microservicesBatchNumber:{}", microservicesBatchNumber);
                log.info("paymentReference:{}", paymentReference);

                List<String> microservicesBatchNumberList = getmicroservicesBatchNumberList(microservicesBatchNumber);
                if (CollectionUtil.isNotEmpty(microservicesBatchNumberList) && microservicesBatchNumberList.size() > 1) {
                    for (String item : microservicesBatchNumberList) {
                        BankTransferTaskInfoDTO insertBankTransferTaskInfo = new BankTransferTaskInfoDTO();
                        insertBankTransferTaskInfo.setMicroservicesBatchNumber(item);
                        insertBankTransferTaskInfo.setResponseStatus(fileStatus);
                        insertBankTransferTaskInfo.setResponseStatusDescription(discountErrorMessage);
                        insertBankTransferTaskInfoList.add(insertBankTransferTaskInfo);

                        BankCommonResponseItemInfoDTO tempBankCommonResponseItemInfoDTO = new BankCommonResponseItemInfoDTO();
                        tempBankCommonResponseItemInfoDTO.setTxnStatus("RJCT");
                        tempBankCommonResponseItemInfoDTO.setTxnStatusDescription(discountErrorMessage);
                        tempBankCommonResponseItemInfoDTO.setTxnRefNo(paymentReference);
                        tempBankCommonResponseItemInfoDTO.setExternalRefNo(item);
                        updateFinanceBatchAndInvoicInfo(tempBankCommonResponseItemInfoDTO);
                    }
                } else {

                    BankTransferTaskInfoDTO insertBankTransferTaskInfo = new BankTransferTaskInfoDTO();
                    insertBankTransferTaskInfo.setMicroservicesBatchNumber(microservicesBatchNumber);
                    insertBankTransferTaskInfo.setResponseStatus(fileStatus);
                    insertBankTransferTaskInfo.setResponseStatusDescription(discountErrorMessage);
                    insertBankTransferTaskInfoList.add(insertBankTransferTaskInfo);

                    BankCommonResponseItemInfoDTO tempBankCommonResponseItemInfoDTO = new BankCommonResponseItemInfoDTO();
                    tempBankCommonResponseItemInfoDTO.setTxnStatus("RJCT");
                    tempBankCommonResponseItemInfoDTO.setTxnStatusDescription(discountErrorMessage);
                    tempBankCommonResponseItemInfoDTO.setTxnRefNo(paymentReference);
                    tempBankCommonResponseItemInfoDTO.setExternalRefNo(microservicesBatchNumber);
                    updateFinanceBatchAndInvoicInfo(tempBankCommonResponseItemInfoDTO);
                }
            }
            updateBankTransferTaskInfo(responseFileName, insertBankTransferTaskInfoList);

        }
        log.info("ADF File Handle Success.");
    }

    private void processDirResponse(List<CsvRow> csvRowList, String responseFileName) {
        List<Integer> dataIndexList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(csvRowList) && csvRowList.size() >= 1) {
            log.info("csvRowList size:{}", csvRowList.size());
            for (int i = 0; i < csvRowList.size(); i++) {
                CsvRow csvRow = csvRowList.get(i);
                String csvRowTitle = csvRow.get(0);
                if (BankBNPP.CELL_TITLE_BNP_BRANCH.equals(csvRowTitle)) {
                    dataIndexList.add(i + 1);
                }
            }
            List<BankCommonResponseItemInfoDTO> bankCommonResponseItemInfoDTOList = new ArrayList<>();
            List<FinanceBatchDTO> bankFinanceBatchDTOList = new ArrayList<>();
            /**
             * 处理header数据
             *
             */
            int headerInvoiceFinacingIndexStart = dataIndexList.get(0);
            int headerInvoiceFinacingIndexEnd = dataIndexList.get(1);
            log.info("headerInvoiceFinacingIndexStart:{},headerInvoiceFinacingIndexEnd:{}", headerInvoiceFinacingIndexStart, headerInvoiceFinacingIndexEnd - 2);
            List<CsvRow> headerInvoiceFinacingList = csvRowList.subList(headerInvoiceFinacingIndexStart, headerInvoiceFinacingIndexEnd - 2);
            for (int f = 0; f < headerInvoiceFinacingList.size(); f++) {
                CsvRow csvRow = headerInvoiceFinacingList.get(f);
                log.info("csvRow:{}", csvRow);
                String microservicesBatchNumber = csvRow.get(21);//microservices Batch Number
                String financeAmount = csvRow.get(10);//Financing Amount
                String interestAmt = csvRow.get(17);//Confirmed Interest Amount
                String tenor = csvRow.get(13);//No of Tenor Days
                String dueDate = csvRow.get(12);//Adjusted Due Date
                String financeDate = csvRow.get(11);//Discount Date
                String loanorinterestRate = csvRow.get(16);//Confirmed Interest Rate

                log.info("loanorinterestRate:{}", loanorinterestRate);
                log.info("financeAmount:{}", financeAmount);
                log.info("interestAmt:{}", interestAmt);
                BigDecimal interestRate = new BigDecimal(loanorinterestRate);
                BigDecimal discountAmount = new BigDecimal(financeAmount);
                BigDecimal discount = new BigDecimal(interestAmt);

                List<String> microservicesBatchNumberList = getmicroservicesBatchNumberList(microservicesBatchNumber);
                if (CollectionUtil.isNotEmpty(microservicesBatchNumberList) && microservicesBatchNumberList.size() > 1) {
                    for (String item : microservicesBatchNumberList) {
                        FinanceBatchDTO financeBatchDTO = new FinanceBatchDTO();
                        financeBatchDTO.setBatchNumber(item);
                        financeBatchDTO.setInterestRate(interestRate);
                        financeBatchDTO.setDiscountDate(
                                DateUtils.parseDate(financeDate)
                        );
                        financeBatchDTO.setTenor(tenor);
                        financeBatchDTO.setDiscountAmount(discountAmount);
                        financeBatchDTO.setDiscount(discount);
                        financeBatchDTO.setMaturityDate(dueDate);
                        bankFinanceBatchDTOList.add(financeBatchDTO);
                    }
                } else {
                    FinanceBatchDTO financeBatchDTO = new FinanceBatchDTO();
                    financeBatchDTO.setInterestRate(interestRate);
                    financeBatchDTO.setBatchNumber(microservicesBatchNumber);
                    financeBatchDTO.setDiscountDate(
                            DateUtils.parseDate(financeDate)
                    );
                    financeBatchDTO.setTenor(tenor);
                    financeBatchDTO.setDiscountAmount(discountAmount);
                    financeBatchDTO.setDiscount(discount);
                    financeBatchDTO.setMaturityDate(dueDate);
                    bankFinanceBatchDTOList.add(financeBatchDTO);
                }

            }


            /**
             * 处理item数据
             */
            int itemInvoiceFinacingIndexStart = dataIndexList.get(1);
            int itemInvoiceFinacingIndexEnd = dataIndexList.get(2);
            log.info("itemInvoiceFinacingIndexStart:{},itemInvoiceFinacingIndexEnd:{}", itemInvoiceFinacingIndexStart, itemInvoiceFinacingIndexEnd - 2);
            List<CsvRow> csvRowInvoiceFinacingList = csvRowList.subList(itemInvoiceFinacingIndexStart, itemInvoiceFinacingIndexEnd - 2);

            for (int j = 0; j < csvRowInvoiceFinacingList.size(); j++) {
                BankCommonResponseItemInfoDTO bankCommonResponseItemInfoDTO = new BankCommonResponseItemInfoDTO();
                CsvRow csvRow = csvRowInvoiceFinacingList.get(j);
                String txnStatus = "ACSP";
                String externalRefNo = csvRow.get(17);//microservices Batch Number
                String loanorinterestRate = csvRow.get(21);//Confirmed Interest Rate
                String financeAmount = csvRow.get(15);//Financing Amount-----------
                String interestAmt = csvRow.get(22);//Confirmed Interest Amount-------------
                String tenor = csvRow.get(18);//No of Tenor Days
                String dueDate = csvRow.get(12);//Adjusted Due Date
                String financeDate = csvRow.get(18);//暂未给出
                String txnStatusDescription = "BNP Financed";
                bankCommonResponseItemInfoDTO.setTxnStatus(txnStatus);
                bankCommonResponseItemInfoDTO.setExternalRefNo(externalRefNo);
                bankCommonResponseItemInfoDTO.setLoanorinterestRate(loanorinterestRate);
                bankCommonResponseItemInfoDTO.setFinanceAmount(financeAmount);
                bankCommonResponseItemInfoDTO.setInterestAmt(interestAmt);
                if (StringUtils.isNotEmpty(financeAmount)) {
                    bankCommonResponseItemInfoDTO.setFinanceAmountNumber(new BigDecimal(financeAmount));
                } else {
                    bankCommonResponseItemInfoDTO.setFinanceAmountNumber(new BigDecimal("0.00"));
                }
                if (StringUtils.isNotEmpty(interestAmt)) {
                    bankCommonResponseItemInfoDTO.setInterestAmtNumber(new BigDecimal(interestAmt));
                } else {
                    bankCommonResponseItemInfoDTO.setInterestAmtNumber(new BigDecimal("0.00"));
                }
                if (StringUtils.isNotEmpty(dueDate)) {
                    bankCommonResponseItemInfoDTO.setDueDate(
                            DateUtils.parseDate(dueDate)
                    );
                }
                bankCommonResponseItemInfoDTO.setTenor(tenor);
                bankCommonResponseItemInfoDTO.setFinanceDate(new Date());
                bankCommonResponseItemInfoDTO.setTxnStatusDescription(txnStatusDescription);
                bankCommonResponseItemInfoDTOList.add(bankCommonResponseItemInfoDTO);
            }

            /*****
             * 将返回的发票数据分组 按照批次分组并且汇总金额
             */

            Map<String, List<BankCommonResponseItemInfoDTO>> bankCommonResponseItemInfoListMap =
                    bankCommonResponseItemInfoDTOList.stream().
                            collect(Collectors.groupingBy(BankCommonResponseItemInfoDTO::getExternalRefNo));
            log.info("bankFinanceBatchDTOList sumObjProperties start:{}", JsonUtil.toJSON(bankFinanceBatchDTOList));
            for (FinanceBatchDTO tempFinanceBatchDTO : bankFinanceBatchDTOList) {
                String batchNumber = tempFinanceBatchDTO.getBatchNumber();
                List<BankCommonResponseItemInfoDTO> mapValue = bankCommonResponseItemInfoListMap.get(batchNumber);
                try {
                    BankCommonResponseItemInfoDTO sumBankCommonResponseItemInfoDTO = sumObjProperties(mapValue, "financeAmountNumber", "interestAmtNumber");
                    tempFinanceBatchDTO.setDiscountAmount(sumBankCommonResponseItemInfoDTO.getInterestAmtNumber());
                    tempFinanceBatchDTO.setDiscount(sumBankCommonResponseItemInfoDTO.getFinanceAmountNumber());
                } catch (NoSuchMethodException e) {
                    log.error("bankCommonResponseItemInfoListMap sumObjProperties NoSuchMethodException:{}", e);
                }
            }
            log.info("bankFinanceBatchDTOList sumObjProperties end:{}", JsonUtil.toJSON(bankFinanceBatchDTOList));

            /*****
             * 将返回的数据分组 按照批次更新系统数据库
             */
            log.info("updateBankTransferTaskInfo start...bankCommonResponseItemInfoDTOList:{}", JsonUtil.toJSON(bankCommonResponseItemInfoDTOList));
            List<BankTransferTaskInfoDTO> insertBankTransferTaskInfoList = updateFinanceBatch(bankCommonResponseItemInfoDTOList);

            log.info("updateFinanceBatchByFinanceBatch start...bankFinanceBatchDTOList:{}", JsonUtil.toJSON(bankFinanceBatchDTOList));
            updateFinanceBatchByFinanceBatch(bankFinanceBatchDTOList);
            log.info("updateFinanceBatchByFinanceBatch end...}");
            updateBankTransferTaskInfo(responseFileName, insertBankTransferTaskInfoList);
            log.info("updateBankTransferTaskInfo end...}");

        }
        log.info("DIR File Handle Success.");

    }

    /**
     * 对list的多个属性求和
     *
     * @param list
     * @param properties 属性名集
     * @param <T>
     * @return 返回实体对象
     */
    public static <T> T sumObjProperties(List<T> list, String... properties) throws NoSuchMethodException {
        Map<String, Function<T, Object>> propertyMap = getPropertyFunctionMap(list, properties);

        Class clazz = list.get(0).getClass();
        T result = (T) createNewInstance(clazz);

        for (String property : properties) {
            if (propertyMap.containsKey(property)) {
                Function<T, Object> function = propertyMap.get(property);
                Object sum = list.stream().map(function).reduce(null, (a, b) -> {
                    return getSum(property, a, b);
                });

                // 设置求和结果给新对象的对应属性
                try {
                    Method setterMethod =
                            clazz.getMethod("set" + property.substring(0, 1).toUpperCase() + property.substring(1),
                                    sum.getClass());
                    setterMethod.invoke(result, sum);
                } catch (Exception e) {
                    log.error("SumObjProperties Exception:{}", e.fillInStackTrace());
                }
            }
        }
        return result;
    }


    private static Object getSum(String property, Object a, Object b) {
        if (a == null) {
            return b;
        } else if (b == null) {
            return a;
        } else if (a instanceof Integer) {
            return (int) a + (int) b;
        } else if (a instanceof BigDecimal) {
            return ((BigDecimal) a).add((BigDecimal) b);
        } else {
            throw new IllegalArgumentException("不支持的属性计算类型:" + property);
        }
    }

    private static <T> Map<String, Function<T, Object>> getPropertyFunctionMap(List<T> list, String[] properties) throws NoSuchMethodException {
        Map<String, Function<T, Object>> propertyMap = new HashMap<>();
        Class<?> clazz = list.get(0).getClass();
        for (String property : properties) {
            Method method = clazz.getMethod("get" + property.substring(0, 1).toUpperCase() + property.substring(1));
            Class<?> returnType = method.getReturnType();
            if (returnType == Integer.class || returnType == int.class) {
                propertyMap.put(property, (Function<T, Object>) value -> {
                    try {
                        return method.invoke(value);
                    } catch (Exception e) {
                        log.error("getPropertyFunctionMap Exception:{}", e);
                        return null;
                    }
                });
            } else if (returnType == BigDecimal.class) {
                propertyMap.put(property, (Function<T, Object>) value -> {
                    try {
                        return method.invoke(value);
                    } catch (Exception e) {
                        log.error("getPropertyFunctionMap Exception:{}", e);
                        return null;
                    }
                });
            } else {
                throw new IllegalArgumentException("Unsupported type for " + property);
            }
        }
        return propertyMap;
    }

    private static <T> T createNewInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private List<String> getmicroservicesBatchNumberList(String microservicesBatchNumber) {
        List<String> microservicesBatchNumberList = new ArrayList<>();
        String microservicesBatchNumberArray[] = microservicesBatchNumber.split("/");
        if (microservicesBatchNumberArray.length == 0) {
            return null;
        } else {
            for (String item : microservicesBatchNumberArray) {
                microservicesBatchNumberList.add(item.trim());
            }
        }
        return microservicesBatchNumberList;

    }

    public static void getFiles(String path) {
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
                    //System.out.println("目录：" + files[i].getPath());
                    getFiles(files[i].getPath());
                } else {
                    System.out.println("文件：" + files[i].getPath());
                }
            }
        } else {
            System.out.println("文件：" + file.getPath());
        }
    }

}
