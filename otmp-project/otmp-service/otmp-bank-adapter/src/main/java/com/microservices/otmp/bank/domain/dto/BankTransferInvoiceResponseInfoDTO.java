package com.microservices.otmp.bank.domain.dto;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;

/**
 * 银行处理后返回发票状态信息对象 bank_transfer_invoice_response_info
 *
 * @author lovefamily
 * @date 2023-10-09
 */
@Data
public class BankTransferInvoiceResponseInfoDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 发票编号
     */
    @Excel(name = "发票编号")
    private String invoiceId;

    /**
     * 批次号
     */
    @Excel(name = "批次号")
    private String microservicesBatchNumber;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String status;
    private String statusDescription;
    private String discountDate;
    private String bankMaturityDate;
    private String bankVendorCode;

    /**
     * 是否已读,0为未读,1为已读
     */
    @Excel(name = "是否已读,0为未读,1为已读")
    private String isRead;

}
