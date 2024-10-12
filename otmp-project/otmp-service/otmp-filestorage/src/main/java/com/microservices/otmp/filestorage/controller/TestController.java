package com.microservices.otmp.filestorage.controller;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.utils.file.FTPUtil;
import com.microservices.otmp.filestorage.config.SftpConfig;
import com.microservices.otmp.filestorage.mapper.SysFileMapper;
import com.microservices.otmp.filestorage.util.SftpUtil;
import com.microservices.otmp.system.domain.SysMenu;
import com.microservices.otmp.system.domain.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private SysFileMapper sysFileMapper;

    @Autowired
    private FTPUtil ftpUtil;

    @Autowired
    private SftpUtil sftpUtil;

    @GetMapping("aaa")
    public String selectMenuByMenuId()
    {
        InputStream inputStream = null;
        try {
//            boolean connectFTP = ftpUtil.connectFTP("10.64.188.40", 22, "filetest", "rNySJ@#1248");
//
//            if (!connectFTP) {
//                return "aaa";
//            }
            File file = new File("D:/file/1.txt");
            inputStream = new FileInputStream(file);
            SftpConfig sftpConfig = new SftpConfig();
            sftpConfig.setHostname("10.64.188.40");
            sftpConfig.setPort(22);
            sftpConfig.setUsername("filetest");
            sftpConfig.setPassword("rNySJ@#1248");
            SftpUtil sftpUtil1 = new SftpUtil(3L, 200L);
//            return ftpUtil.uploadFile(file, "/data", "2.txt");
            boolean upload = sftpUtil1.upload("/data/file1/", inputStream, sftpConfig, "20231109");
            if (upload) {
                return "aaa";
            }
            return "bbb";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("files")
    public ResultDTO<Object> testFiles()
    {
        return ResultDTO.success(sysFileMapper.selectForTest());
    }

}
