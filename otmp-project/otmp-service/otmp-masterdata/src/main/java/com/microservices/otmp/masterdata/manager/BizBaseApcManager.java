package com.microservices.otmp.masterdata.manager;

import com.microservices.otmp.masterdata.domain.BizBaseApcDO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseApcDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseCurrencyDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author guowb1
 * @description
 * @date 2022/6/1 18:02
 */
public interface BizBaseApcManager {

    BizBaseApcDO selectBizBaseApcById(Long id);

    List<BizBaseApcDO> selectBizBaseApcList(BizBaseApcDO bizBaseApcDO);

    int insertBizBaseApc(BizBaseApcDO bizBaseApcDO);

    int updateBizBaseApc(BizBaseApcDO bizBaseApcDO);

    int deleteBizBaseApcByIds(Long[] ids);

    int deleteBizBaseApcById(Long id);

}
