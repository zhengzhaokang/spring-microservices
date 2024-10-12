package com.microservices.otmp.bank.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import com.microservices.otmp.common.core.domain.BaseDO;
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
public class BankCommonResponseHeaderInfoDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * Bank Message ID
     */

    private String msgId;

    /**
     * Org ID
     */

    private String orgId;

    /**
     * Timestamp
     */

    private String timeStamp;

    /**
     * Bank Channel Id
     */

    private String channelId;

    /**
     * Bank Ctry
     */

    private String ctry;

    /**
     * Status
     */

    private String status;

    private List<BankCommonResponseItemInfoDO> bankCommonResponseItemInfoList;


}
