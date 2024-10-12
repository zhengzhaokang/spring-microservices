package com.microservices.otmp.masterdata.feign;

import com.microservices.otmp.masterdata.common.Response;
import com.microservices.otmp.masterdata.domain.ToLpcApcDTO;
import com.microservices.otmp.system.domain.dto.DataIntegrateApcDTO;
import com.microservices.otmp.masterdata.common.Response;
import com.microservices.otmp.masterdata.domain.ToLpcApcDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 用户 Feign服务层
 *
 * @author qiaodf2
 */

@FeignClient(name = "lpc", url = "${wso2.maps.lpc.url}/sit/v1.0/sales/lpc/mbg/product/category/")
public interface RemoteApcWso2Service {
    /**
     * 通过lpc查询apc
     * @param dataIntegrateApcDTO
     * @param token
     * @return
     */
    @PostMapping("page")
    Response<ToLpcApcDTO> getApcCodeByCallLpc(@RequestBody DataIntegrateApcDTO dataIntegrateApcDTO, @RequestHeader("Authorization") String token);

}
