package com.microservices.otmp.bank.service.strategy.exec;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.CharsetUtil;
import com.alibaba.fastjson.JSON;
import com.microservices.otmp.bank.domain.constant.BankBNPP;
import com.microservices.otmp.bank.domain.constant.BankDBS;
import com.microservices.otmp.bank.domain.constant.BankTransferConstant;
import com.microservices.otmp.bank.domain.dto.*;
import com.microservices.otmp.bank.domain.entity.BankCommonResponseItemInfoDO;
import com.microservices.otmp.bank.kafka.BankInvoiceTransferKafkaSender;
import com.microservices.otmp.bank.mapper.SupplierBankSettingMapper;
import com.microservices.otmp.bank.service.IBankCommonResponseHeaderInfoService;
import com.microservices.otmp.bank.service.IBankCommonResponseItemInfoService;
import com.microservices.otmp.bank.service.IBankTransferMetadataInfoService;
import com.microservices.otmp.bank.service.IBankTransferTaskInfoService;
import com.microservices.otmp.bank.util.PgpHelper;
import com.microservices.otmp.bank.util.XlsxtoCSV;
import com.microservices.otmp.common.enums.InvoiceStatusEnum;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.*;
import com.microservices.otmp.finance.domain.dto.FinanceBatchDTO;
import com.microservices.otmp.finance.domain.dto.FinanceBatchInvoiceDTO;
import com.microservices.otmp.finance.domain.dto.FinanceInvoiceApDTO;
import com.microservices.otmp.finance.mapper.FinanceInvoiceApMapper;
import com.microservices.otmp.finance.service.IFinanceBatchService;
import com.microservices.otmp.finance.service.IFinanceInvoiceApService;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * DBS 提交实现
 */
@Service
@Slf4j
public class DbsBankStrategyTemplate extends BaseBankStrategy {

    @Value("${bank.dbs.pgpPublicKey}")
    private String pgpPublicKey;//pgp公钥地址
    @Value("${bank.dbs.pgpPrivateKey}")
    private String pgpPrivateKey;//解密的私钥
    @Value("${bank.dbs.encryptedFile}")
    private String encryptedFile;//加密文件存储位置
    @Value("${bank.dbs.pgpSuffix}")
    private String pgpSuffix;//加密后的文件格式

    @Value("${bank.dbs.decryptedFile}")
    private String decryptedFile;//待解密文件存储位置
    @Value("${bank.dbs.decryptedOriginalFile}")
    private String decryptedOriginalFile;//解密后文件存储位置

    @Value("${bank.dbs.requestFilePath}")
    private String requestFilePath;//生成的原始csv文件存储路径

    @Value("${bank.dbs.localFilePath}")
    private String localFilePath;//生成的csv文件加密后存储路径

    @Value("${ftp.dbs.address}")
    private String ftpAddress;//服务器ip地址
    @Value("${ftp.dbs.port}")
    private String ftpPort;//端口号
    @Value("${ftp.dbs.userName}")
    private String ftpUserName;//用户名
    @Value("${ftp.dbs.password}")
    private String ftpPassword;//密码
    @Value("${ftp.dbs.basePath}")
    private String ftpBasePath;//文件在服务器中实际的位置
    @Value("${ftp.dbs.baseDownloadPath}")
    private String ftpBaseDownloadPath;//文件在服务器中实际的位置
    @Value("${ftp.dbs.privateKeyPath}")
    private String ftpPrivateKeyPath;//生成的原始csv文件存储路径


    @Autowired
    private FinanceInvoiceApMapper financeInvoiceApMapper;

    @Autowired
    private IBankTransferMetadataInfoService bankTransferMetadataInfoService;

    @Autowired
    private SupplierBankSettingMapper supplierBankSettingMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IBankCommonResponseItemInfoService bankCommonResponseItemInfoService;

    @Autowired
    private IBankCommonResponseHeaderInfoService bankCommonResponseHeaderInfoService;


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public String strategyType() {
        return BankDBS.BANK_TYPE;
    }

    @Override
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

        //构建传输数据
        Map<String, List<Map<String, Object>>> transferDataMap = buildTransferDataMapList(bankTransferMetadataInfoDTOList, financeBatchInvoice);

        //获取供应商信息
        String buyerOrgId = getBuyerOrgId(financeBatchInvoice);
        bankUpload.setFileName(getFileName(buyerOrgId));
        bankUpload.setTransferDataMap(transferDataMap);
        return bankUpload;
    }

    private String getBuyerOrgId(FinanceBatchInvoiceDTO financeBatchInvoice) {
        return supplierBankSettingMapper.getSupplierBankSettingMapper(financeBatchInvoice.getSupplierCode(), financeBatchInvoice.getBankId(), financeBatchInvoice.getEntityId());
    }

    private Map<String, List<Map<String, Object>>> buildTransferDataMapList(List<BankTransferMetadataInfoDTO> bankTransferMetadataInfoDTOList, FinanceBatchInvoiceDTO financeBatchInvoice) {


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
        resultTransferDataMap = buidResultTransferData(financeBatchInvoice, transferDataColumnFormatMap, returnTransferDataColumnMap, resultTransferDataMap);
        return resultTransferDataMap;
    }


    //构建传输结果数据
    private Map<String, List<Map<String, Object>>> buidResultTransferData(FinanceBatchInvoiceDTO financeBatchInvoice, Map<String, Object> transferDataColumnFormatMap, Map<String, Map<String, Object>> returnTransferDataColumnMap, Map<String, List<Map<String, Object>>> resultTransferDataMap) {

        String batchNo = financeBatchInvoice.getBatchNumber();
        String supplierCode = financeBatchInvoice.getSupplierCode();
        String bankCode = financeBatchInvoice.getBankId();
        Long entityId = financeBatchInvoice.getEntityId();
        //获取所需发票信息
        List<Map<String, Object>> financeInvoiceApMapList = financeInvoiceApMapper.findFinancingDataDBSMap(batchNo, supplierCode, bankCode, entityId);

        for (String key : returnTransferDataColumnMap.keySet()) {
            Map<String, Object> transferDataColumnMap = returnTransferDataColumnMap.get(key);
            List<Map<String, Object>> transferDataMapList = new ArrayList<>();
            if (BankBNPP.TRANSFER_BODY.equals(key)) {
                AtomicInteger idx = new AtomicInteger();

                financeInvoiceApMapList.forEach(invoiceApMap -> {
                    Map<String, Object> transferDataMap = new LinkedHashMap<>();
                    //按照字段配置构建格式化数据
                    buildFormatTransferData(transferDataColumnMap, transferDataColumnFormatMap, invoiceApMap, transferDataMap, batchNo, idx.incrementAndGet());
                    transferDataMapList.add(transferDataMap);
                });
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
                transferDataColumnMap.put(transferColumn, "Date" + ":" + BankDBS.DATE_FORMAT);
            }
            if (StringUtils.isNotEmpty(javaType) && "BigDecimal".equals(javaType)) {
                transferDataColumnMap.put(transferColumn, "BigDecimal" + ":" + BankDBS.BIGDECIMAL_FORMAT);
            }
            if (StringUtils.isNotEmpty(javaType) && "FixedValue".equals(javaType)) {
                transferDataColumnMap.put(transferColumn, "FixedValue" + ":" + "");
            }
        }
    }

    //按照字段配置构建格式化数据
    private void buildFormatTransferData(Map<String, Object> transferDataColumnMap, Map<String, Object> transferDataColumnFormatMap, Map<String, Object> invoiceApMap, Map<String, Object> transferDataMap, String batchNo, int idx) {
        transferDataColumnMap.forEach((k, v) -> {
            if (Objects.isNull(v)) {
                transferDataMap.put(k, v);
            } else {
                if ("no".equals(v)) {
                    transferDataMap.put(k, idx);
                    return;
                }
                if ("programsId".equals(v)) {
                    transferDataMap.put(k, "SF");
                    return;
                }
                if ("externalRefNo".equals(v)) {
                    transferDataMap.put(k, batchNo);
                    return;
                }
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
                        if (!Objects.isNull(value)) {

                            if (("Credit Memo").equals(String.valueOf(value)) || ("Debit Memo").equals(String.valueOf(value))) {
                                value = "I";
                            }
                            if (("Credit Note").equals(String.valueOf(value)) || ("Debit Noted").equals(String.valueOf(value))) {
                                value = "A";
                            }
                        }

                    }

                }
                transferDataMap.put(k, value);

            }
        });
        String instrumentType = transferDataMap.get("Instrument Type").toString();
        if ("A".equals(instrumentType)) {
            String instrumentAmount = transferDataMap.get("Instrument Amount").toString();
            BigDecimal bigDecimalValue = new BigDecimal(String.valueOf(instrumentAmount));
            transferDataMap.put("Instrument Amount", bigDecimalValue.negate());
        }
    }


    /**
     * 生成文件名称
     *
     * @return String
     */
    protected String getFileName(String buyerOrgId) {
        String key = LocalDate.now().format(formatter);
        Long next = 1L;
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("000");
        next = redisUtils.increment(key, 1);
        return "PFLE001.ISCF" + BankDBS.SF + "." + buyerOrgId + ".D" + key + df.format(next) + ".csv";
    }

    @Override
    public ParamDTO buildParamDTO() {
        return null;
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
        paramDTO.setAssemble(XlsxtoCSV.buildCsvData(bankUpload.getTransferDataMap(), true));
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
        List<String> fileDataList = new ArrayList<>();
        String responseFileName = paramDTO.getResponseFileName();
        String decryptedFile = paramDTO.getDecryptedFile() + responseFileName;
        String pgpPrivateKey = paramDTO.getPgpPrivateKey();
        //FileInputStream fileInputStream = new FileInputStream("C:\\Users\\T14\\Desktop\\otfp\\DBS\\testdata\\PFLE001.ISCFSF.SSGLT009.D20231023001.ack1a.pgp");
        FileInputStream fileInputStream = new FileInputStream(decryptedFile);
        FileInputStream priKeyIs = new FileInputStream(pgpPrivateKey);
        //FileInputStream priKeyIs = new FileInputStream("C:\\Users\\T14\\Desktop\\otfp\\BNP\\h2h\\microservicesnuatkey\\microservicesuat_pgp_pri.asc");
        // PgpHelper.getInstance().decryptFile(fileInputStream,priKeyIs,null,fileDataList);
        String decryptedOriginalFile = paramDTO.getDecryptedOriginalFile() + responseFileName.replace("ppg", "csv");
        // OutputStream out = new FileOutputStream("D:\\test\\decrypt\\PFLE001.ISCFSF.SSGLT009.D20231023001.ack1a");
        log.info(decryptedOriginalFile);

        OutputStream out = new FileOutputStream(decryptedOriginalFile);

        PgpHelper.getInstance().decryptFile(fileInputStream, out, priKeyIs, null);
        fileInputStream.close();
        priKeyIs.close();
        out.close();

    }

    @Override
    public List<CsvRow> parseFeedbackData(ParamDTO paramDTO) throws Exception {
        //localFileName = "C:\\Users\\T14\\Desktop\\otfp\\DBS\\microservices AP Program 2023\\microservices AP Program 2023\\ISCFSF.LENOVPC3.DTSTATUS.D20230623000.ack.csv";
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


    public List<List<String>> feedback(String fileUrl, String fileName, String localFileName, String responseFileName) throws Exception {
        //文件下载到本地
        pullToLocal(fileUrl, fileName);

        ParamDTO paramDTO = new ParamDTO();
        //解密
        decryptFile(paramDTO);
        //读取解密后的csv文件
        List<CsvRow> csvRowList = parseFeedbackData(paramDTO);
        saveResponseData(csvRowList, responseFileName);
        return null;
    }

    private void saveResponseData(List<CsvRow> csvRowList, String responseFileName) {
        Map<String, Object> resultMap = new HashMap<>();
        int bodyStartIndex = 6;
        //前5行为header头信息 第6行为body的title 第7行开始为body返回的具体数据
        if (CollectionUtil.isNotEmpty(csvRowList) && csvRowList.size() >= 6) {
            CsvRow csvRow1 = csvRowList.get(0);
            resultMap.put("msgId", csvRow1.get(0));
            CsvRow csvRow2 = csvRowList.get(1);
            resultMap.put("orgId", csvRow2.get(0));
            CsvRow csvRow3 = csvRowList.get(2);
            resultMap.put("timeStamp", csvRow3.get(0));
            CsvRow csvRow4 = csvRowList.get(3);
            String row4Value = csvRow4.get(0);
            resultMap.put("channelId", row4Value);
//            if("responseType".equals(row4Value)){
//                resultMap.put("channelId","");
//                resultMap.put("ctry","");
//                bodyStartIndex=4;
//            }else{
//                resultMap.put("channelId", row4Value);
//            }
            CsvRow csvRow5 = csvRowList.get(4);
            String row5Value = csvRow5.get(0);
            if ("responseType".equals(row5Value)) {
                resultMap.put("ctry", "");
                bodyStartIndex = 5;
            } else {
                resultMap.put("ctry", row5Value);
            }
        }

        //第7行开始为body返回的具体数据
        Map<String, String> responseBodyMappingMap = BankDBS.RESPONSE_BODY_TITLE_MAPPING_MAP;

        List<Map<String, String>> bodyRowList = new ArrayList<>();
        for (int i = bodyStartIndex; i < csvRowList.size(); i++) {
            CsvRow csvRow = csvRowList.get(i);
            Map<String, String> rowMapData = new HashMap<>();
            List<String> titleList = new LinkedList<>(responseBodyMappingMap.keySet());
            for (int index = 0; index < titleList.size(); index++) {
                rowMapData.put(titleList.get(index), csvRow.get(index));
            }
            bodyRowList.add(rowMapData);
        }

        resultMap.put("bodyRowList", bodyRowList);
        log.info(resultMap.toString());
        ///后续将解析出的数据入库
        saveIndb(resultMap, responseFileName);
    }

    private void saveIndb(Map<String, Object> resultMap, String responseFileName) {
        try {
            int headerNum = 0;
            int itemNum = 0;
            BankCommonResponseHeaderInfoDTO bankCommonResponseHeaderInfoDTO =
                    BeanUtil.mapToBean(resultMap, BankCommonResponseHeaderInfoDTO.class, false);
            Long headerId = SnowFlakeUtil.nextId();
            Date createTime = DateUtils.getNowDate();
            bankCommonResponseHeaderInfoDTO.setId(headerId);
            bankCommonResponseHeaderInfoDTO.setCreateTime(createTime);
            bankCommonResponseHeaderInfoDTO.setCreateBy("SYSTEM");
            String msgId = bankCommonResponseHeaderInfoDTO.getMsgId();
            headerNum = bankCommonResponseHeaderInfoService.insertBankCommonResponseHeaderInfo(bankCommonResponseHeaderInfoDTO);
            List<Map<String, String>> bodyRowList = new ArrayList<>();
            bodyRowList = (List<Map<String, String>>) resultMap.get("bodyRowList");
            if (CollectionUtil.isNotEmpty(bodyRowList)) {
                List<BankCommonResponseItemInfoDTO> bankCommonResponseItemInfoDTOList = new ArrayList<>();
                for (Map<String, String> tempMap : bodyRowList) {
                    BankCommonResponseItemInfoDTO bankCommonResponseItemInfoDTO =
                            BeanUtil.mapToBean(tempMap, BankCommonResponseItemInfoDTO.class, false);
                    Long itemId = SnowFlakeUtil.nextId();
                    bankCommonResponseItemInfoDTO.setHeaderId(headerId);
                    bankCommonResponseItemInfoDTO.setId(itemId);
                    bankCommonResponseItemInfoDTO.setMsgId(msgId);
                    bankCommonResponseItemInfoDTO.setCreateTime(createTime);
                    bankCommonResponseItemInfoDTO.setCreateBy("SYSTEM");
                    itemNum += bankCommonResponseItemInfoService.insertBankCommonResponseItemInfo(bankCommonResponseItemInfoDTO);
                    bankCommonResponseItemInfoDTOList.add(bankCommonResponseItemInfoDTO);
                    //////处理后续业务以及发送kafka
                    //updateBatchAndInvoiceInfo(bankCommonResponseItemInfoDTO);
                }
                /*****
                 * 将返回的数据分组 按照批次更新系统数据库
                 */
                List<BankTransferTaskInfoDTO> insertBankTransferTaskInfoList = updateFinanceBatch(bankCommonResponseItemInfoDTOList);
                updateBankTransferTaskInfo(responseFileName, insertBankTransferTaskInfoList);
            }
            log.info("Insert in DB Success.headerNum:{},itemNum:{}", headerNum, itemNum);
        } catch (Exception e) {
            log.error("Insert in DB Fail.Exception:{}", e);
            e.printStackTrace();
        }
    }

}
