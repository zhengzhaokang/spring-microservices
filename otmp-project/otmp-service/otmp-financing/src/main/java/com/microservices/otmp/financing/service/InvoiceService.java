package com.microservices.otmp.financing.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.financing.domain.entity.SupplierDo;
import com.microservices.otmp.financing.domain.param.invoice.*;
import com.microservices.otmp.financing.domain.vo.bank.BankVo;
import com.microservices.otmp.financing.domain.vo.invoice.*;

import java.util.List;

public interface InvoiceService {

    List<MaturityDateAmountVo> maturityDateAmount(String bankId);

    /**
     * 获取融资过期时间
     */
    Long confirmTimeExpiry(long currentUserId);

    /**
     * 融资时设置过期时间
     */
    Boolean setConfirmTime(long currentUserId);

    /**
     * 清除提交倒计时
     */
    Boolean clearConfirmTime(long userId);

    /**
     * 银行列表，按rank排序
     */
    List<BankVo> rankList(Long userId);

    /**
     * 查询发票列表
     */
//    PageInfo<InvoiceVo> availableInvoicesFree(AvailableFreeListParam param);

    FinanceInvoiceListVo availableInvoicesFreeWithRelation(AvailableFreeListParam param);

    /**
     * 根据融资额度查询发票列表
     */
    FinanceInvoiceListVo availableInvoicesSelect(AvailableSelectListParam param);

    PageInfo<InvoiceVo> confirmInvoiceList(ConfirmInvoiceListParam param);

    boolean renewalLockPageKey(Long userId);

    boolean removeLockPageKey(Long userId);

    /**
     * 提交发票进行融资
     */
    SubmitResultVo submit(InvoiceSubmitParam param);

    SubmitBatchResult doSubmit(InvoiceSubmitParam param);

    void sendMailAndNotice(String amount, SupplierDo supplier);

    /**
     * 选择发票时计算最大可融资额度
     */
    InvoiceCardVo availableMaximumAmount(Long userId, String bankId);

    /**
     * 提交发票时计算discountRate
     */
    String discountRate(Long userId, String bankId);

    /**
     * 提交发票时计算discountAmount
     */
    String discountAmount(ConfirmInvoiceListParam param);

    /**
     * 处理到期/银行退回的发票提交记录
     */
    void processExpiredRecords(String updateBy);

    List<MaturityGroupVo> maturityDateGroup(MaturityDateGroupParam param);

    boolean updateMaturityDate(UpdateMaturityParam param);
}
