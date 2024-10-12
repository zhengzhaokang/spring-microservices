package com.microservices.otmp.financing.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.financing.domain.param.limit.EditLimitParam;
import com.microservices.otmp.financing.domain.param.limit.LimitListParam;
import com.microservices.otmp.financing.domain.vo.limit.LimitVo;

import java.util.Date;

public interface LimitService {

    PageInfo<LimitVo> list(LimitListParam param);

    Boolean edit(EditLimitParam param);

    void statisticLimitDaily(Date calDate);
}
