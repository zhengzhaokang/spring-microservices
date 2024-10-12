package com.microservices.otmp.bank.controller;

import com.jcraft.jsch.JSchException;
import com.microservices.otmp.bank.domain.constant.BankTransferConstant;
import com.microservices.otmp.bank.domain.dto.BankCommonResponseItemInfoDTO;
import com.microservices.otmp.bank.domain.dto.BankTransferInvoiceResponseInfoDTO;
import com.microservices.otmp.bank.domain.dto.BankTransferTaskInfoDTO;
import com.microservices.otmp.bank.domain.dto.ParamDTO;
import com.microservices.otmp.bank.domain.req.BatchReq;
import com.microservices.otmp.bank.kafka.BankInvoiceSubmissionKafkaSender;
import com.microservices.otmp.bank.kafka.BankInvoiceTransferKafkaSender;
import com.microservices.otmp.bank.kafka.NoticeKafkaSender;
import com.microservices.otmp.bank.service.BankService;
import com.microservices.otmp.bank.service.IBankTransferTaskInfoService;
import com.microservices.otmp.bank.util.PgpHelper;
import com.microservices.otmp.bank.util.SftpUtil;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.domain.R;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.enums.BatchStatusEnum;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.finance.domain.dto.FinanceBatchInvoiceDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * created with IntelliJ IDEA.
 * user: licf
 * Date: 2023/7/24
 * time: 14:29
 * Description:
 */
@RestController
@RequestMapping("/document")
@Slf4j
public class BankController {

    @Autowired
    private BankService bankService;
    @Autowired
    private BankInvoiceTransferKafkaSender bankInvoiceTransferKafkaSender;
    @Autowired
    private BankInvoiceSubmissionKafkaSender bankInvoiceSubmissionKafkaSender;
    @Autowired
    private NoticeKafkaSender noticeKafkaSender;

    @Autowired
    private IBankTransferTaskInfoService bankTransferTaskInfoService;
    @Autowired
    private SftpUtil sftpUtil;

    /**
     * 1.获取到发票,读取银行需要的字段，转为csv文件 进行加密 上传到服务器
     * <p>
     * <p>
     * 2.下载文件 进行解密转为csv文件 读取所需要的的字段信息，保存到数据库
     */

    @PostMapping("/submit")
    @OperLog(title = "Bank data integration submit", businessType = BusinessType.INSERT)
    public ResultDTO submit(@RequestBody BatchReq batchReq) {
        return ResultDTO.success(bankService.submit(batchReq));
    }


    @PostMapping("/pullFile")
    @OperLog(title = "Bank data integration pull file", businessType = BusinessType.INSERT)
    public ResultDTO pullFileToLocal(@RequestBody BatchReq batchReq) {
        bankService.pullFileToLocal(batchReq);
        return ResultDTO.success();
    }

    @PostMapping("/download")
    public ResultDTO download(@RequestBody BatchReq batchReq) {
        return ResultDTO.success(bankService.download(batchReq));
    }


    @PostMapping("/manualSendKafka")
    @OperLog(title = "Bank data integration send Kafka", businessType = BusinessType.OTHER)
    public ResultDTO manualSendKafka(@RequestBody BankTransferInvoiceResponseInfoDTO bankTransferInvoiceResponseInfoDTO) {
        try {
            bankTransferInvoiceResponseInfoDTO.setId(SnowFlakeUtil.nextId());
            bankTransferInvoiceResponseInfoDTO.setCreateTime(DateUtils.getNowDate());
            bankInvoiceTransferKafkaSender.sendBankInvoiceTransferData(bankTransferInvoiceResponseInfoDTO);
        } catch (Exception e) {
            log.error("Exception：{}", e.getMessage());
        }
        return ResultDTO.success("BatchNo " + bankTransferInvoiceResponseInfoDTO.getMicroservicesBatchNumber() + " Manual Send Kafka Success.");
    }


    @PostMapping("/manualSendEmail")
    public ResultDTO manualSendEmail(@RequestBody BankTransferInvoiceResponseInfoDTO bankTransferInvoiceResponseInfoDTO) {
        try {
            Map<String, String> param=new HashMap<>();
            //noticeKafkaSender.sendMsgRemindData(bankTransferInvoiceResponseInfoMap);
           // noticeKafkaSender.sendEmailData(param,);
        } catch (Exception e) {
            log.error("Exception：{}", e.getMessage());
        }
        return ResultDTO.success("BatchNo " + bankTransferInvoiceResponseInfoDTO.getMicroservicesBatchNumber() + " Manual Send Email Success.");
    }

    @PostMapping("/sftpUtilUpload")
    public void sftpUtilUpload(MultipartFile file, @RequestBody Map<String, String> requestParams) {
        String ipAddress = requestParams.get("ipAddress");
        String port = requestParams.get("port");
        String privateKeyPath = requestParams.get("privateKeyPath");
        String username = requestParams.get("username");
        String passphrase = requestParams.get("passphrase");
        String remoteFilePath = requestParams.get("remoteFilePath");
        String remoteFileName = requestParams.get("remoteFileName");
        String localFilePath = requestParams.get("localFilePath");
        String password = requestParams.get("password");
        try {

            sftpUtil.connectWithPublicKey(ipAddress, Integer.parseInt(port), username, password, privateKeyPath, passphrase);
            System.out.println("sftpUtilUpload  Start.");
            sftpUtil.uploadFile(remoteFilePath, remoteFileName, localFilePath, remoteFileName);
            System.out.println("sftpUtilUpload  end.remoteFilePath=" + remoteFilePath);

            Vector v = sftpUtil.listFiles(remoteFilePath);
            System.out.println("listFiles=" + v.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @PostMapping("/uploadSftpByBatchNo")
    @OperLog(title = "Bank data integration upload bank By BatchNo", businessType = BusinessType.OTHER)
    public ResultDTO uploadSftpByBatchNo(@RequestBody BatchReq batchReq) {
        BankTransferTaskInfoDTO bankTransferTaskInfo = new BankTransferTaskInfoDTO();
        String batchNo = batchReq.getBatchNo();
        bankTransferTaskInfo.setMicroservicesBatchNumber(batchNo);
        bankTransferTaskInfo.setStatus(BatchStatusEnum.SUBMITTED.code);
        bankTransferTaskInfo.setOperType(BankTransferConstant.BANK_TRANSFER_REQUEST);
        List<BankTransferTaskInfoDTO> bankTransferTaskInfoDTOList = bankTransferTaskInfoService.selectBankTransferTaskInfoList(bankTransferTaskInfo);

        ParamDTO paramDTO = new ParamDTO();
        BankTransferTaskInfoDTO bankTransferTaskInfoDTO = new BankTransferTaskInfoDTO();
        if (CollectionUtils.isEmpty(bankTransferTaskInfoDTOList)) {
            return ResultDTO.fail("uploadSftpByBatchNo fail.BatchNo:" + batchReq.getBatchNo());
        } else {
            bankTransferTaskInfoDTO = bankTransferTaskInfoDTOList.get(0);
            String paramData = bankTransferTaskInfoDTO.getParamData();
            paramDTO = JsonUtil.jsonToObject(paramData, ParamDTO.class);
        }

        String ipAddress = paramDTO.getFtpAddress();
        Integer port = paramDTO.getFtpPort();
        String privateKeyPath = paramDTO.getPrivateKeyPath();
        String username = paramDTO.getFtpUserName();
        String passphrase = paramDTO.getFtpPassword();
        String remoteFilePath = paramDTO.getFtpBasePath();
        String remoteFileName = paramDTO.getFileName() + ".pgp";
        String localFilePath = paramDTO.getLocalFilePath();
        String password = paramDTO.getFtpPassword();
        try {
            sftpUtil.connectWithPublicKey(ipAddress, port, username, password, privateKeyPath, passphrase);
            log.info("sftpUtilUpload  Start.");
            sftpUtil.uploadFile(remoteFilePath, remoteFileName, localFilePath, remoteFileName);
            log.info("sftpUtilUpload  end.remoteFilePath=" + remoteFilePath);
            int resultNum = updateBankTransferTaskInfo(bankTransferTaskInfoDTO);
            log.info("sftpUtil Upload file save in db num:{}", resultNum);
            Vector v = sftpUtil.listFiles(remoteFilePath);
            log.info("listFiles=" + v.toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("sftpUtilUpload error:{}", e.getMessage());
            return ResultDTO.fail("BatchNo " + batchNo + " Upload Sftp Fail." + e.getMessage());
        }
        return ResultDTO.success("BatchNo " + batchNo + " Upload Sftp Success.");
    }

    @PostMapping("/downloadSftpByBatchNo")
    @OperLog(title = "Bank data integration download bank By BatchNo", businessType = BusinessType.OTHER)
    public ResultDTO downloadSftpByBatchNo(@RequestBody BatchReq batchReq) {
        BankTransferTaskInfoDTO bankTransferTaskInfo = new BankTransferTaskInfoDTO();
        String batchNo = batchReq.getBatchNo();
        bankTransferTaskInfo.setMicroservicesBatchNumber(batchNo);
        bankTransferTaskInfo.setStatus(BatchStatusEnum.SUBMITTED.code);
        bankTransferTaskInfo.setOperType(BankTransferConstant.BANK_TRANSFER_REQUEST);
        List<BankTransferTaskInfoDTO> bankTransferTaskInfoDTOList = bankTransferTaskInfoService.selectBankTransferTaskInfoList(bankTransferTaskInfo);

        ParamDTO paramDTO = new ParamDTO();
        BankTransferTaskInfoDTO bankTransferTaskInfoDTO = new BankTransferTaskInfoDTO();
        if (CollectionUtils.isEmpty(bankTransferTaskInfoDTOList)) {
            return ResultDTO.fail("DownloadSftpByBatchNo fail.BatchNo:" + batchReq.getBatchNo());
        } else {
            bankTransferTaskInfoDTO = bankTransferTaskInfoDTOList.get(0);
            String paramData = bankTransferTaskInfoDTO.getParamData();
            paramDTO = JsonUtil.jsonToObject(paramData, ParamDTO.class);
        }

        String ipAddress = paramDTO.getFtpAddress();
        Integer port = paramDTO.getFtpPort();
        String privateKeyPath = paramDTO.getPrivateKeyPath();
        String username = paramDTO.getFtpUserName();
        String passphrase = paramDTO.getFtpPassword();
        String remoteDownFilePath = paramDTO.getFtpBaseDownloadPath();
        String decryptedFilePath = paramDTO.getDecryptedFile();
        String password = paramDTO.getFtpPassword();
        try {
            sftpUtil.connectWithPublicKey(ipAddress, port, username, password, privateKeyPath, passphrase);
            log.info("SftpUtilDownload  Start.");
            List<File> fileList = sftpUtil.batchDownLoadFileReturnFile(remoteDownFilePath, decryptedFilePath);
            log.info("SftpUtilDownload  end.remoteDownFilePath=" + remoteDownFilePath);
            int resultNum = saveBankTransferTaskInfoOfDownload(bankTransferTaskInfoDTO, paramDTO, fileList);
            log.info("SftpUtil Download file save in db num:{}", resultNum);
            log.info("Decrypt File&Parse Data Start.");
            bankService.pullFileToLocal(batchReq);
            log.info("Decrypt File&Parse Data Complete.");

        } catch (Exception e) {
            e.printStackTrace();
            log.error("sftpUtilDownload error:{}", e.getMessage());
            return ResultDTO.fail("BatchNo " + batchNo + " Download Sftp Fail." + e.getMessage());
        }
        return ResultDTO.success("BatchNo " + batchNo + " Download Sftp Success.");
    }


    @PostMapping("/decryptFileByBatchNo")
    public ResultDTO decryptFileByBatchNo(@RequestBody BatchReq batchReq) {


        return ResultDTO.success("BatchNo " + " DecryptFile Success.");
    }


    @PostMapping("/deleteSftpByBatchNo")
    public ResultDTO deleteSftpByBatchNo(@RequestBody BatchReq batchReq) {
        BankTransferTaskInfoDTO bankTransferTaskInfo = new BankTransferTaskInfoDTO();
        String batchNo = batchReq.getBatchNo();
        bankTransferTaskInfo.setMicroservicesBatchNumber(batchNo);
        bankTransferTaskInfo.setStatus(BatchStatusEnum.SUBMITTED.code);
        bankTransferTaskInfo.setOperType(BankTransferConstant.BANK_TRANSFER_REQUEST);
        List<BankTransferTaskInfoDTO> bankTransferTaskInfoDTOList = bankTransferTaskInfoService.selectBankTransferTaskInfoList(bankTransferTaskInfo);

        ParamDTO paramDTO = new ParamDTO();
        if (CollectionUtils.isEmpty(bankTransferTaskInfoDTOList)) {
            return ResultDTO.fail("DeleteSftpByBatchNo fail.BatchNo");
        } else {
            BankTransferTaskInfoDTO bankTransferTaskInfoDTO = bankTransferTaskInfoDTOList.get(0);
            String paramData = bankTransferTaskInfoDTO.getParamData();
            paramDTO = JsonUtil.jsonToObject(paramData, ParamDTO.class);
        }

        String ipAddress = paramDTO.getFtpAddress();
        Integer port = paramDTO.getFtpPort();
        String privateKeyPath = paramDTO.getPrivateKeyPath();
        String username = paramDTO.getFtpUserName();
        String passphrase = paramDTO.getFtpPassword();
        String remoteFilePath = paramDTO.getFtpBasePath();
        String remoteFileName = paramDTO.getFileName();
        String password = paramDTO.getFtpPassword();
        try {
            sftpUtil.connectWithPublicKey(ipAddress, port, username, password, privateKeyPath, passphrase);
            log.info("sftpUtilDelete  Start.");
            sftpUtil.deleteSFTP(remoteFilePath, remoteFileName);
            log.info("sftpUtilDelete  end.remoteFilePath=" + remoteFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("sftpUtilDelete error:{}", e.getMessage());
            return ResultDTO.fail("BatchNo " + batchNo + " Delete Sftp Fail." + e.getMessage());
        }
        return ResultDTO.success("BatchNo " + batchNo + " Delete Sftp Success.");
    }

    @PostMapping("/rejectedByBatchNo")
    public ResultDTO rejectedByBatchNo(@RequestBody BankCommonResponseItemInfoDTO commonResponseItemInfoDTO) {
        String batchNo = commonResponseItemInfoDTO.getExternalRefNo();
        String txnStatus = commonResponseItemInfoDTO.getTxnStatus();
        bankService.updateLocalDbData(commonResponseItemInfoDTO);
        String msg = "ACSP".equals(txnStatus) ? " Financed Success." : " Rejected Success.";
        return ResultDTO.success("BatchNo " + batchNo + msg);
    }

    @PostMapping("/financedByBatchNo")
    public ResultDTO financedByBatchNo(@RequestBody BankCommonResponseItemInfoDTO commonResponseItemInfoDTO) {

        String batchNo = commonResponseItemInfoDTO.getExternalRefNo();
        bankService.updateLocalDbData(commonResponseItemInfoDTO);

        return ResultDTO.success("BatchNo " + batchNo + " Financed Success.");
    }



    private int updateBankTransferTaskInfo(BankTransferTaskInfoDTO bankTransferTaskInfo) {
        bankTransferTaskInfo.setUpdateTime(DateUtils.getNowDate());
        bankTransferTaskInfo.setUpdateBy("SENDBANK");
        return bankTransferTaskInfoService.updateBankTransferTaskInfo(bankTransferTaskInfo);
    }

    private int saveBankTransferTaskInfoOfDownload(BankTransferTaskInfoDTO requestTaskInfoDTO, ParamDTO paramDTO, List<File> fileList) {
        int resultNum = 0;
        BankTransferTaskInfoDTO bankTransferTaskInfo = new BankTransferTaskInfoDTO();
        bankTransferTaskInfo.setOperType(BankTransferConstant.BANK_TRANSFER_RESPONSE);
        bankTransferTaskInfo.setMicroservicesBatchNumber(requestTaskInfoDTO.getMicroservicesBatchNumber());
        bankTransferTaskInfo.setBankCode(requestTaskInfoDTO.getBankCode());
        bankTransferTaskInfo.setCreateBy("RECIVEBANK");
        bankTransferTaskInfo.setCreateTime(DateUtils.getNowDate());
        bankTransferTaskInfo.setStatus(BatchStatusEnum.FILE_DOWNLOAD_COMPLETED.code);
        bankTransferTaskInfo.setParamData(JsonUtil.toJSON(paramDTO));
        if (CollectionUtils.isNotEmpty(fileList)) {
            for (File file : fileList) {
                bankTransferTaskInfo.setBankTransferId(SnowFlakeUtil.nextId());
                String responseFileName = file.getName();
                bankTransferTaskInfo.setResponseFileName(responseFileName);
                if (!checkBankTransferTaskInfoIsExist(responseFileName)) {
                    resultNum += bankTransferTaskInfoService.insertBankTransferTaskInfo(bankTransferTaskInfo);
                } else {
                    log.info("The response file:{} already exists in the database.", responseFileName);
                }
            }
        }
        return resultNum;
    }

    /**
     * 校验文件是否已经下载下来并且已入库
     *
     * @param responseFileName
     * @return
     */
    private boolean checkBankTransferTaskInfoIsExist(String responseFileName) {
        BankTransferTaskInfoDTO bankTransferTaskInfo = new BankTransferTaskInfoDTO();
        bankTransferTaskInfo.setOperType(BankTransferConstant.BANK_TRANSFER_RESPONSE);
        bankTransferTaskInfo.setResponseFileName(responseFileName);
        List<BankTransferTaskInfoDTO> bankTransferTaskInfoDTOList = bankTransferTaskInfoService.selectBankTransferTaskInfoList(bankTransferTaskInfo);
        return CollectionUtils.isNotEmpty(bankTransferTaskInfoDTOList);
    }
    @PostMapping("/decryptFile")
    public ResultDTO decryptFile(ParamDTO paramDTO) throws Exception {
        String responseFileName = paramDTO.getResponseFileName();
        String decryptedFile = paramDTO.getDecryptedFile() + responseFileName;
        String pgpPrivateKey = paramDTO.getPgpPrivateKey();
        //FileInputStream fileInputStream = new FileInputStream("C:\\Users\\T14\\Desktop\\otfp\\DBS\\testdata\\PFLE001.ISCFSF.SSGLT009.D20231023001.ack1a.pgp");

        log.info("DecryptFile:{} Start.", decryptedFile);
        FileInputStream fileInputStream = new FileInputStream(decryptedFile);
        FileInputStream priKeyIs = new FileInputStream(pgpPrivateKey);
        //FileInputStream priKeyIs = new FileInputStream("C:\\Users\\T14\\Desktop\\otfp\\BNP\\h2h\\microservicesnuatkey\\microservicesuat_pgp_pri.asc");

        String decryptedOriginalFile = paramDTO.getDecryptedOriginalFile() + responseFileName.replace("ppg", "csv");
        log.info("decryptedOriginalFile:{}.", decryptedOriginalFile);
        OutputStream out = new FileOutputStream(decryptedOriginalFile);
        // OutputStream out = new FileOutputStream("D:\\test\\decrypt\\PFLE001.ISCFSF.SSGLT009.D20231023001.ack1a");
        PgpHelper.getInstance().decryptFile(fileInputStream, out, priKeyIs, null);
        log.info("DecryptFile End.", decryptedOriginalFile);
        fileInputStream.close();
        priKeyIs.close();
        out.close();

       return ResultDTO.success();
    }

    public static void main(String[] args) throws Exception {

        String decryptedFile = "C:\\Users\\T14\\Desktop\\otfp\\BNP\\20240112\\plain.csv.pgp";
        String pgpPrivateKey = "C:\\Users\\T14\\Desktop\\otfp\\prod_key\\microservices_prod_pgp_pri.asc";
        String decryptedOriginalFile = "C:\\Users\\T14\\Desktop\\otfp\\BNP\\20240112\\plain233.csv";

        log.info("DecryptFile:{} Start.", decryptedFile);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(decryptedFile);

        FileInputStream priKeyIs = new FileInputStream(pgpPrivateKey);

        log.info("decryptedOriginalFile:{}.", decryptedOriginalFile);
        OutputStream out = new FileOutputStream(decryptedOriginalFile);
        // OutputStream out = new FileOutputStream("D:\\test\\decrypt\\PFLE001.ISCFSF.SSGLT009.D20231023001.ack1a");
        PgpHelper.getInstance().decryptFile(fileInputStream, out, priKeyIs, null);
        log.info("DecryptFile End.", decryptedOriginalFile);
        fileInputStream.close();
        priKeyIs.close();
        out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("11111111111");
    }

}


