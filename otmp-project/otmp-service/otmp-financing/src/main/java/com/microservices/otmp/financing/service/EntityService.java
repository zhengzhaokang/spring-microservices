package com.microservices.otmp.financing.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.financing.domain.param.entity.AddEntityParam;
import com.microservices.otmp.financing.domain.param.entity.EditEntityParam;
import com.microservices.otmp.financing.domain.param.entity.EntityListParam;
import com.microservices.otmp.financing.domain.vo.entity.EntityVo;
import com.microservices.otmp.system.domain.SysOperLog;

public interface EntityService {

    Boolean add(AddEntityParam param);

    Boolean update(EditEntityParam param, SysOperLog operatorLog);

    PageInfo<EntityVo> list(EntityListParam param);

    EntityVo detail(Long id);

    Boolean updateStatus(Long id, String status);
}
