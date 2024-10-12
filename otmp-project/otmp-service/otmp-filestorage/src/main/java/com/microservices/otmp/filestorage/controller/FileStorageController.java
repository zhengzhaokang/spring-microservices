package com.microservices.otmp.filestorage.controller;

import com.alibaba.fastjson.JSON;
import com.microservices.otmp.common.annotation.LoginUser;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.R;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.file.FileUtils;
import com.microservices.otmp.filestorage.domain.ISysFileService;
import com.microservices.otmp.filestorage.dto.SystemFile;
import com.microservices.otmp.filestorage.dto.SystemFileParam;
import com.microservices.otmp.filestorage.dto.SystemFileUpdateInfo;
import com.microservices.otmp.filestorage.dto.SystemFileUpdateVO;
import com.microservices.otmp.filestorage.util.SysDateUtils;
import com.microservices.otmp.system.domain.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用请求处理
 * 
 * @author lovefamily
 */
@RestController
@RequestMapping("/filestorage")
@Api(value = "FileStorageController", tags = {"Common公共上传下载接口模块"})
@CrossOrigin
public class FileStorageController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(FileStorageController.class);

    @Autowired
    private ISysFileService iSysFileService;

    @Value("${file.data.path}")
    private String serverFilePath;

    /**
     * 根据uuid下载和预览文件
     *
     * @param fileUuid 文件名称
     */
    @ApiOperation(value = "downloadAndPreview",  notes = "预览和下载接口")
    @GetMapping("/downloadAndPreview")
    public void downloadAndPreview(String fileUuid, HttpServletResponse response, HttpServletRequest request) {
        try
        {
        SystemFile sysFile = iSysFileService.selectByFileUuid(fileUuid);
        if (sysFile == null) {
            throw new RuntimeException(StringUtils.format("查询的文件file_uuid-->({})不存在。 ", fileUuid));
        }
        String realFileName = sysFile.getFileNameOri();
        String filePath = serverFilePath + sysFile.getFolder() + sysFile.getFileUuid();
        response.setCharacterEncoding("utf-8");
        // 下载使用"application/octet-stream"更标准
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + FileUtils.setFileDownloadHeader(request, realFileName));
        FileUtils.writeBytes(filePath, response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用文件上传请求
     *
     * @param uploadFiles 上传的文件---数组（可一 可多）
     * @param dataId 费用条目id
     */
    @ApiOperation(value = "fileUpload",  notes = "通用上传接口")
    @PostMapping("/uploads")
    public ResultDTO<Object> fileUpload(@ApiParam(name = "sysUser" , value = "登陆用户名称", required = true)
                        String sysUser,
                        @ApiParam(name = "uploadFiles" , value = "上传的文件可一可多", required = true)
                        @RequestParam("uploadFiles") MultipartFile[] uploadFiles,
                        @ApiParam(name = "dataId", value = "费用条目id")String dataId,
                        HttpServletRequest request) {
        if (uploadFiles == null || uploadFiles.length < 1) {
            return ResultDTO.fail("上传文件不能为空");
        }
        List<String> fileUuids = new ArrayList<>();
        try {
            iSysFileService.fileUpload(uploadFiles, serverFilePath,fileUuids, sysUser, dataId);
            return ResultDTO.success(String.join(",", fileUuids));
        } catch (Exception e) {
            log.error("fileUpload File Error is ", e);
            //上传失败后需要删除表结构数据和文件
            for (String fileUuid : fileUuids) {
                if (FileUtils.deleteFile(serverFilePath + SysDateUtils.getDateYYYYMMDD() + File.separator + fileUuid)) {
                    iSysFileService.deleteByFileUuid(fileUuid);
                }
            }
            return ResultDTO.fail("上传失败");
        }
    }

    /**
     * 根据dataId查询附件信息列表
     *
     * @param sysFile
     */
    @ApiOperation(value = "queryBySysFile",  notes = "查询附件列表")
    @PostMapping("/queryBySysFile")
    @ResponseBody
    public ResultDTO<Object> queryBySysFile(@RequestBody SystemFileParam sysFile) {
        if (sysFile == null) {
            log.info("###FileStorageController queryBySysFile sysFile is null");
            return ResultDTO.fail();
        }
        SystemFile systemFile = SystemFile.builder().fileUuid(sysFile.getFileUuid()).
                dataId(Long.parseLong(sysFile.getDataId())).build();
        return iSysFileService.selectBySysFile(systemFile);
    }

    /**
     * 更新文件为可用状态
     *
     * @param sysFile
     */
    @ApiOperation(value = "update",  notes = "更新文件为可用状态")
    @PostMapping("/update")
    @ResponseBody
    public ResultDTO<Object> updateBySysFile(@RequestBody SystemFileUpdateVO sysFile, @ApiIgnore @LoginUser SysUser sysUser) {
        log.info("------------"+JSON.toJSONString(sysFile));
        List<SystemFileUpdateVO> list = new ArrayList<>();
        list.add(sysFile);
        return iSysFileService.updateBySysFile(list);
    }

    /**
     * 更新文件为可用状态
     *
     * @param systemFileUpdateInfo 文件信息
     */
    @ApiOperation(value = "batchUpdate",  notes = "更新文件为可用状态")
    @PostMapping("/batchUpdate")
    @ResponseBody
    public ResultDTO<Object> batchUpdateBySysFile(@RequestBody SystemFileUpdateInfo systemFileUpdateInfo, @ApiIgnore @LoginUser SysUser sysUser) {
        log.info("### batchUpdateBySysFile is " + JSON.toJSONString(systemFileUpdateInfo));
        if (systemFileUpdateInfo == null) {
            log.info("---batchUpdateBySysFile systemFileUpdateInfo is null");
            return ResultDTO.success();
        }
        List<SystemFileUpdateVO> list = new ArrayList<>(systemFileUpdateInfo.getSystemFileUpdateVOList());
        return iSysFileService.updateBySysFile(list);
    }

    /**
     * 根据fileUuid删除附件信息
     *
     * @param sysFile
     */
    @ApiOperation(value = "deleteSysFile",  notes = "删除附件信息")
    @PostMapping("/deleteSysFile")
    @ResponseBody
    public ResultDTO<Object> deleteBySysFile(@RequestBody SystemFile sysFile) {
        int count = iSysFileService.deleteByFileUuid(sysFile.getFileUuid());
        return ResultDTO.success(count);
    }

}