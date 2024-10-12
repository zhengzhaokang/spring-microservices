package com.microservices.otmp.bank.service.template;

import com.microservices.otmp.bank.domain.dto.BankFinancingDTO;
import com.microservices.otmp.finance.domain.dto.FinanceBatchInvoiceDTO;

import java.util.List;

/**
 * created with IntelliJ IDEA.
 * user: licf
 * Date: 2023/8/15
 * time: 15:33
 * Description:
 */
public abstract class FinancingTemplate {

    /**
     * 生成文件名称
     *
     * @param type 发票类型
     * @return String
     */
    public abstract String getFileName(Integer type, String buyerOrgId);

    /**
     * 获取发票信息
     *
     * @return BankTemplateDTO
     */
    public abstract BankFinancingDTO parseFinancingData(FinanceBatchInvoiceDTO financeBatchInvoice);


    /**
     * 文件加密
     *
     * @param bankUploadDTO 发票数据
     * @return String
     */
    public abstract String encryptFile(BankFinancingDTO bankUploadDTO, FinanceBatchInvoiceDTO financeBatchInvoice) throws Exception;

    /**
     * 上传到服务器
     *
     * @param fileName 文件名称
     */
    public abstract void sendToBank(String fileName, FinanceBatchInvoiceDTO financeBatchInvoice) throws Exception;

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
     * @param localFileName 文件下载存储的路径
     */
    public abstract void decryptFile(String localFileName) throws Exception;

    /**
     * 解析回馈的数据 存入数据库
     *
     * @param localFileName 文件解密后的路径存储地址
     * @return Integer
     */
    public abstract List<List<String>> parseFeedbackData(String localFileName) throws Exception;

    /**
     * 收取发票数据 生成加密文件 上传到服务器
     *
     * @param financeBatchInvoice 供应商信息
     */
    public void financing(FinanceBatchInvoiceDTO financeBatchInvoice) throws Exception {

        sendToBank(encryptFile(parseFinancingData(financeBatchInvoice), financeBatchInvoice), financeBatchInvoice);
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
        decryptFile(fileUrl + fileName);
        //读取解密后的csv文件
        return parseFeedbackData(fileUrl + localFileName);
    }


}
