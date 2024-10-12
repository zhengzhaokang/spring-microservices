package com.microservices.otmp.bank.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * 银行处理后返回发票状态信息对象 bank_transfer_invoice_response_info
 * 
 * @author lovefamily
 * @date 2023-10-09
 */
@Data
public class BankTransferInvoiceResponseInfoDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 发票编号 */

    private String invoiceId;

    /** 批次号 */

    private String microservicesBatchNumber;

    /** 状态 */

    private String status;

    /** 是否已读,0为未读,1为已读 */

    private String isRead;


}
