package com.microservices.otmp.system.feign;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.system.domain.SysOss;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.system.domain.SysOss;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * Oss Feign服务层
 *
 * @Author lovefamily
 * @date 2022-08-10
 */
@FeignClient(name = "${otfp.service.system.uri}")
public interface RemoteOssService {

    @PostMapping(value = "/oss/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultDTO<SysOss> upload(@RequestPart("file") MultipartFile file, @RequestPart("module") String module);

    @PostMapping(value = "/oss/fileUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultDTO<SysOss> fileUpload(@RequestPart("file") MultipartFile file, @RequestPart("module") String module,
                                    @RequestPart("status") String status);

    @PostMapping(value = "/oss/download", consumes = MediaType.APPLICATION_PROBLEM_JSON_VALUE)
    public Response downloadByOss(@RequestBody SysOss sysOss);

    @PostMapping("/oss/deleteByObj")
    public ResultDTO removeByObj(@RequestBody SysOss sysOss);

    @PostMapping("/oss/save")
    public ResultDTO saveOss(@RequestBody SysOss sysOss);

}
