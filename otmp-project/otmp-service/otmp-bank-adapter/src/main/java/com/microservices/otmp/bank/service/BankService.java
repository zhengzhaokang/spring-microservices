package com.microservices.otmp.bank.service;

import com.microservices.otmp.bank.domain.dto.BankCommonResponseItemInfoDTO;
import com.microservices.otmp.bank.domain.req.BatchReq;

import java.util.List;

/**
 * created with IntelliJ IDEA.
 * user: licf
 * Date: 2023/7/24
 * time: 14:36
 * Description:
 */
public interface BankService {

    /**
     * 下载文件并解密
     */
    List<List<String>> download(BatchReq batchReq);

    /**
     * 提交接口
     *
     * @param batchReq 参数
     */
    Integer submit(BatchReq batchReq);

    /**
     * 发送银行
     *
     * @param batchReq 参数
     */
    void sendBank(BatchReq batchReq);


    void pullFileToLocal(BatchReq batchReq);

    void updateLocalDbData(BankCommonResponseItemInfoDTO commonResponseItemInfoDTO);


}
