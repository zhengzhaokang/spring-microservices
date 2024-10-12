package com.microservices.otmp.bank.domain.req;

import lombok.Data;

/**
 * created with IntelliJ IDEA.
 * user: licf
 * Date: 2023/8/16
 * time: 9:55
 * Description:
 */
@Data
public class BatchReq {
    /**
     * 编号
     */
    private String batchNo;
    /**
     * 下载后的位置
     */
    private String fileUrl;
    /**
     * 文件名称
     */
    private String localFileName;

}
