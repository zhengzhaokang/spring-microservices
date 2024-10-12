package com.microservices.otmp.financing.mapper;

import com.microservices.otmp.common.core.dao.BaseMapper;
import com.microservices.otmp.financing.domain.entity.InviteLinkDo;
import com.microservices.otmp.financing.domain.entity.InviteLinkSupplierDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InviteLinkMapper extends BaseMapper<InviteLinkDo> {

    InviteLinkSupplierDo companyName(@Param("code") String code);
}
