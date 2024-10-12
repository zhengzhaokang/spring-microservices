package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.domain.Server;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.ToolUtil;
import com.microservices.otmp.common.utils.file.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用请求处理
 *
 * @author lovefamily
 */
@RestController
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.isValidFilename(fileName)) {
                throw new OtmpException(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = DateUtils.dateTimeNow() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = ToolUtil.getDownloadPath() + fileName;
            response.setCharacterEncoding("utf-8");
            // 下载使用"application/octet-stream"更标准
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + FileUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete.booleanValue()) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    @GetMapping("monitor/server")
    public ResultDTO<Server> getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return ResultDTO.success(server);
    }
}