package com.microservices.otmp.financing.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.financing.domain.param.bank.AddBankParam;
import com.microservices.otmp.financing.domain.param.bank.BankListParam;
import com.microservices.otmp.financing.domain.param.bank.EditBankParam;
import com.microservices.otmp.financing.domain.vo.bank.BankVo;

public interface BankService {

    Boolean add(AddBankParam param);

    Boolean update(EditBankParam param);

    PageInfo<BankVo> list(BankListParam param);

    BankVo info(Long id);

    Boolean changeStatus(Long id,String status);
}
