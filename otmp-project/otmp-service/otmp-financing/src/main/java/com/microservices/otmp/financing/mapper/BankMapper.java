package com.microservices.otmp.financing.mapper;

import com.microservices.otmp.common.core.dao.BaseMapper;
import com.microservices.otmp.financing.domain.entity.BankDo;
import com.microservices.otmp.financing.domain.entity.BankWithEntityIdDo;
import com.microservices.otmp.financing.domain.entity.BankWithMarginDo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface BankMapper extends BaseMapper<BankDo> {

    List<BankWithEntityIdDo> banksByEntityId(@Param("ids") List<Long> entityId, @Param("status") String status);

    List<BankWithEntityIdDo> banksBySingleEntityId(@Param("entityId") Long entityId);

    List<BankWithMarginDo> rankList(@Param("supplierId") Long supplierId);

    List<BankDo> bankList(@Param("status") String status, @Param("supplierId") String supplierId);

    List<BankDo> selectByIdsAndStatus(@Param("bankIds") Collection<String> bankIds, @Param("delete") String deleteFlag);
}
