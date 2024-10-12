package com.microservices.otmp.system.remote;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.system.domain.dto.SupplierSimpleDTO;
import com.microservices.otmp.system.remote.factory.RemoteFinancingFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${otfp.service.financing.uri}", fallbackFactory = RemoteFinancingFallbackFactory.class)
public interface RemoteFinancingService {
    @GetMapping("/anchor/supplier/info/simple")
    ResultDTO<SupplierSimpleDTO> simple(@RequestParam("id") Long id);
}
