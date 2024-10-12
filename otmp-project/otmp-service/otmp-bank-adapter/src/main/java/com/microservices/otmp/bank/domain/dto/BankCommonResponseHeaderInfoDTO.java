package com.microservices.otmp.bank.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.microservices.otmp.bank.domain.entity.BankCommonResponseItemInfoDO;
import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Bank Common Response Header Info对象 bank_common_response_header_info
 *
 * @author lovefamily
 * @date 2023-10-10
 */
@Data
public class BankCommonResponseHeaderInfoDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * Bank Message ID
     */
    @Excel(name = "Bank Message ID")
    private String msgId;

    /**
     * Org ID
     */
    @Excel(name = "Org ID")
    private String orgId;

    /**
     * Timestamp
     */

    private String timeStamp;

    /**
     * Bank Channel Id
     */
    @Excel(name = "Bank Channel Id")
    private String channelId;

    /**
     * Bank Ctry
     */
    @Excel(name = "Bank Ctry")
    private String ctry;

    /**
     * Status
     */
    @Excel(name = "Status")
    private String status;

    private List<BankCommonResponseItemInfoDTO> bankCommonResponseItemInfoList;


}
