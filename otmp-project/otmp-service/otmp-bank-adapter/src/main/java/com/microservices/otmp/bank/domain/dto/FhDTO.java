package com.microservices.otmp.bank.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * created with IntelliJ IDEA.
 * user: licf
 * Date: 2023/8/10
 * time: 14:33
 * Description:
 */
@Data
public class FhDTO {
    //FH 数据
    private String identifier;
    /**
     * 供应商主数据(组织ID)
     */
    private String senderId;
    /**
     * 文件名称(按照银行格式生成)
     */
    private String fileName;
    /**
     * 文件生成日期
     */
    private Date fileCreationDate;
    /**
     * 联想批号
     */
    private String microservicesBatchNumber;
}
