package com.microservices.otmp.financing.remote;

import com.microservices.otmp.common.core.domain.R;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.financing.remote.factory.RemoteFileServiceFallBackFactory;
import com.microservices.otmp.financing.remote.param.BatchUpdateParam;
import com.microservices.otmp.financing.remote.param.FileUpdateParam;
import com.microservices.otmp.system.interceptor.APIQualifierInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${otfp.service.filestorage.uri}", fallbackFactory = RemoteFileServiceFallBackFactory.class, configuration = APIQualifierInterceptor.class)
public interface RemoteFileService {

    @PostMapping(value = "/filestorage/update")
    R update(@RequestBody FileUpdateParam param);

    @PostMapping(value = "/filestorage/batchUpdate")
    ResultDTO<Object> batchUpdate(@RequestBody BatchUpdateParam param);

}
