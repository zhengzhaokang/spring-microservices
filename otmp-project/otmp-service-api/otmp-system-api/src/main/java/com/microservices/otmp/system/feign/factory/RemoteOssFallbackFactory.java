package com.microservices.otmp.system.feign.factory;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.system.domain.SysOss;
import com.microservices.otmp.system.feign.RemoteOssService;
import feign.Response;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class RemoteOssFallbackFactory implements FallbackFactory<RemoteOssService> {/* (non-Javadoc)
 * @see feign.hystrix.FallbackFactory#create(java.lang.Throwable)
 */

    @Override
    public RemoteOssService create(Throwable throwable) {
        log.error(throwable.getMessage());
        return new RemoteOssService() {

            @Override
            public ResultDTO<SysOss> upload(MultipartFile file, String module) {
                return null;
            }

            @Override
            public ResultDTO<SysOss> fileUpload(MultipartFile file, String module, String status) {
                return null;
            }

            @Override
            public Response downloadByOss(SysOss sysOss) {
                return null;
            }

            @Override
            public ResultDTO removeByObj(SysOss sysOss) {
                return null;
            }

            @Override
            public ResultDTO saveOss(SysOss sysOss) {
                throwable.printStackTrace();
                return null;
            }
        };
    }
}
