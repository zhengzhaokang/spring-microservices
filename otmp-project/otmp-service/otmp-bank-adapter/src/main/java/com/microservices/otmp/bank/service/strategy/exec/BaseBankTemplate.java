package com.microservices.otmp.bank.service.strategy.exec;

import com.microservices.otmp.bank.domain.constant.BankTransferConstant;
import com.microservices.otmp.bank.domain.dto.BankFinancingDTO;
import com.microservices.otmp.bank.domain.dto.BankTransferMetadataInfoDTO;
import com.microservices.otmp.bank.domain.dto.ParamDTO;
import com.microservices.otmp.bank.service.IBankTransferMetadataInfoService;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.finance.domain.dto.FinanceBatchInvoiceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;

@Slf4j
public class BaseBankTemplate  {

    @Value("${bank.dbs.pgpPublicKey}")
    protected String pgpPublicKey;//pgp公钥地址
    @Value("${bank.dbs.encryptedFile}")
    protected String encryptedFile;//加密文件存储位置
    @Value("${bank.dbs.pgpSuffix}")
    protected String pgpSuffix;//加密后的文件格式

    @Value("${bank.dbs.pgpPrivateKey}")
    protected String pgpPrivateKey;//解密的私钥
    @Value("${bank.decryptedFile}")
    protected String decryptedFile;//解密文件存储位置

    protected String ftpAddress;//服务器ip地址
    protected String ftpPort;//端口号
    protected String ftpUserName;//用户名
    protected String ftpPassword;//密码
    protected String ftpBasePath;//文件在服务器中实际的位置
    protected String ftpBaseDownloadPath;//response文件在服务器中实际的位置

    @Autowired
    protected RedisUtils redisUtils;

    @Autowired
    protected IBankTransferMetadataInfoService bankTransferMetadataInfoService;

//    @Autowired
//    public SupplierBankSettingMapper supplierBankSettingMapper;
//
//    @Autowired
//    public FinanceInvoiceApMapper financeInvoiceApMapper;


    public BankFinancingDTO parseRequestData(FinanceBatchInvoiceDTO financeBatchInvoice) {
        String batchNo = financeBatchInvoice.getBatchNumber();
        String supplierCode = financeBatchInvoice.getSupplierCode();
        String bankCode = financeBatchInvoice.getBankId();

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
       // String buyerOrgId = supplierBankSettingMapper.getSupplierBankSettingMapper(supplierCode, bankCode);
        String buyerOrgId =" supplierBankSettingMapper.getSupplierBankSettingMapper(supplierCode, bankCode)";
        bankUpload.setFileName(getFileName(buyerOrgId));
        bankUpload.setTransferDataMap(transferDataMap);
        return bankUpload;
    }



    public BankFinancingDTO parseFinancingData(FinanceBatchInvoiceDTO financeBatchInvoice) {
        return null;
    }

    public ParamDTO buildParamDTO() {
        return null;
    }

    public ParamDTO getEncryptParamDTO(BankFinancingDTO bankFinancingDTO) {
        return null;
    }

    public ParamDTO getSendParamDTO(ParamDTO paramDTO) {
        return null;
    }

    protected String getFileName(String buyerOrgId) {
        return null;
    }

    protected Map<String, List<Map<String, Object>>> buildTransferDataMapList(List<BankTransferMetadataInfoDTO> bankTransferMetadataInfoDTOList, FinanceBatchInvoiceDTO financeBatchInvoice) {
        return null;
    }

    public void pullToLocal(String fileUrl, String fileName) {

    }


    public String strategyType() {
        return null;
    }

    public void decryptFile(String localFileName) throws Exception {

    }

    public List<List<String>> parseFeedbackData(String localFileName) throws Exception {
        return null;
    }
}
