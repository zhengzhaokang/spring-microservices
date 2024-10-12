package com.microservices.otmp.financing.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.financing.domain.param.IdParam;
import com.microservices.otmp.financing.domain.param.supplier.*;
import com.microservices.otmp.financing.domain.vo.supplier.*;

import java.util.List;

public interface SupplierService {

    Boolean updateStatus(Long id, Integer status);

    PageInfo<SupplierVo> list(SupplierListParam param, Integer status);

    /**
     * 发送邀请邮件
     * @param param 邮件内容参数
     * @return inviteCode
     */
    String invite(MailParam param);

    boolean deleteInvite(Long id, String updateBy);

    InviteLinkVo companyName(String inventionCode);

    /**
     * supplier注册流程
     * @param param 注册参数
     * @return 注册结果
     */
    ResultDTO<Boolean> register(RegisterParam param);

    SupplierSimpleVo simple(Long id);

    SupplierDetailVo detail(Long id);

    List<SupplierInformationVo> info(String code);

    List<String> availableCurrency(Long bankId, Long entityId);

    /**
     * anchor激活supplier账号
     * @param param 参数
     * @return 执行结果
     */
    boolean active(ActiveSupplierParam param);

    boolean edit(EditSupplierParam param);

    /**
     * 设置onHold状态的时间段
     */
    boolean onHold(OnHoldParam param);

    /**
     * 手动取消onHold
     */
    boolean cancelOnHold(IdParam param);

    /**
     * 修改supplier的状态为onHold，并调用远程API停止抓取发票数据
     */
    void doOnHold();

    /**
     * 修改supplier的状态为正常，并调用远程API开始抓取发票数据
     */
    void doCancelOnHold();

    List<EntityBankPairVo> entityBankRelation();
}
