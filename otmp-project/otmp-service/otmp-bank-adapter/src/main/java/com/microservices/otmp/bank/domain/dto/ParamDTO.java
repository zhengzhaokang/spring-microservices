package com.microservices.otmp.bank.domain.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * created with IntelliJ IDEA.
 * user: licf
 * Date: 2023/8/25
 * time: 15:49
 * Description:
 */
@Data
public class ParamDTO {
    /**
     * pgp公钥地址
     */
    private String pgpPublicKey;
    /**
     * 加密文件存储位置
     */
    private String encryptedFile;
    /**
     * 加密后的文件格式
     */
    private String pgpSuffix;
    /**
     * 解密的私钥
     */
    private String pgpPrivateKey;
    /**
     * 待解密文件存储位置
     */
    private String decryptedFile;
    /**
     * 解密后文件存储位置
     */
    private String decryptedOriginalFile;
    /**
     * 服务器ip地址
     */
    private String ftpAddress;
    /**
     * 端口号
     */
    private Integer ftpPort;
    /**
     * 用户名
     */
    private String ftpUserName;
    /**
     * 密码
     */
    private String ftpPassword;

    /**
     * sftp连接私钥地址
     */
    private String privateKeyPath;
    /**
     * request文件在远程服务器中实际的位置
     */
    private String ftpBasePath;
    /**
     * response文件在远程服务器中实际的位置
     */
    private String ftpBaseDownloadPath;

    /**
     * 文件在本地服务器中的存储位置
     */
    private String localDirPath;

    /**
     * 生成的数据集合
     */
    private StringBuffer assemble;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 加密文件存储地址
     */
    private String encryptFileUrl;
    /**
     * 生成的原始csv文件存储路径
     */
    private String requestFilePath;

    /**
     * 返回的response文件名称
     */
    private String responseFileName;
    /**
     * 生成的csv文件加密后存储路径
     */
    private String localFilePath;


}
