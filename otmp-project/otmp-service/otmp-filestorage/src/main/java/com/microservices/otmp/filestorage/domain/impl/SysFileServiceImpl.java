package com.microservices.otmp.filestorage.domain.impl;

import com.alibaba.fastjson.JSON;
import com.microservices.otmp.common.core.domain.R;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.filestorage.dto.SystemFile;
import com.microservices.otmp.filestorage.dto.SystemFileDO;
import com.microservices.otmp.filestorage.dto.SystemFileUpdateVO;
import com.microservices.otmp.filestorage.domain.ISysFileService;
import com.microservices.otmp.filestorage.service.SysFileManager;
import com.microservices.otmp.filestorage.util.SysDateUtils;
import com.microservices.otmp.filestorage.util.SysUUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;


@Service
public class SysFileServiceImpl implements ISysFileService {

    private static final Logger log = LoggerFactory.getLogger(SysFileServiceImpl.class);
    @Autowired
    private SysFileManager sysFileManager;

    @Autowired
    private SnowFlakeUtil snowFlakeUtil;

    @Override
    @Transactional
    public void fileUpload(MultipartFile[] uploadFiles, String serverFilePath, List<String> fileUuids, String loginName, String dataId) throws IOException {
        log.info(loginName);
        String yyyyMMdd = SysDateUtils.getDateYYYYMMDD() + File.separator;
        String realPath = serverFilePath + yyyyMMdd;
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        for (MultipartFile uploadFile : uploadFiles) {
            String filename = uploadFile.getOriginalFilename();
            log.info("拿到的文件原名为： {} " , filename);
            String fileUUID = SysUUID.getRandomUUID();
            //获取雪花ID
            long snowFlakeId = SnowFlakeUtil.nextId();
            log.info("UUID为：{}" , fileUUID);
            File fileServer = new File(dir, fileUUID);
            log.info("file文件真实路径:" + fileServer.getAbsolutePath());
            uploadFile.transferTo(fileServer);
            //上传成功后进行入库保存
            SystemFile sysFile = new SystemFile();
            sysFile.setFileId(snowFlakeId);
            sysFile.setFileNameOri(filename);
            sysFile.setFileUuid(fileUUID);
            sysFile.setFolder(yyyyMMdd);
            sysFile.setDataId(Long.parseLong(dataId));
            sysFile.setCreateBy(loginName);
            sysFile.setCreateTime(new Date());
            sysFile.setUpdateBy(loginName);
            sysFile.setUpdateTime(new Date());
            SystemFileDO sysFileDO = new SystemFileDO();
            BeanUtils.copyProperties(sysFile, sysFileDO);
            sysFileManager.insertSelective(sysFileDO);
            fileUuids.add(fileUUID);
        }
    }

    @Override
    public SystemFile selectByFileUuid(String fileUUID) {
        SystemFile sysFile = new SystemFile();
        SystemFileDO sysFileDO = sysFileManager.selectByFileUuid(fileUUID);
        if (sysFileDO != null) {
            BeanUtils.copyProperties(sysFileDO, sysFile);
            return sysFile;
        } else {
            return null;
        }
    }

    @Override
    public int deleteByFileUuid(String fileUuid) {
        return sysFileManager.deleteByFileUuid(fileUuid);
    }

    @Override
    public ResultDTO<Object> selectBySysFile(SystemFile sysFile) {
        List<SystemFile> sysFileList = new ArrayList<>();
        List<SystemFileDO> sysFileDOList = sysFileManager.selectBySysFile(sysFile);
        if (CollectionUtils.isEmpty(sysFileDOList)) {
            log.warn("###SysFileServiceImpl selectBySysFile warn is {}", StringUtils.format("未查询到： {} 对应的附件信息", JSON.toJSONString(sysFile)));
            return ResultDTO.success(sysFileList);
        }
        BeanUtils.copyListProperties(sysFileDOList, sysFileList, SystemFile.class, null);
        for (SystemFile systemFile : sysFileList) {
            Date createTime = systemFile.getCreateTime();
            systemFile.setCreateTimeFormat(SysDateUtils.dateToStr(createTime, SysDateUtils.HOUR_PATTERN_1));
        }
        return ResultDTO.success(sysFileList);
    }

    @Override
    public ResultDTO<Object> updateBySysFile(List<SystemFileUpdateVO> sysFileUpdateVOS) {
        if (CollectionUtils.isEmpty(sysFileUpdateVOS)) {
            return ResultDTO.success();
        }
        for (SystemFileUpdateVO vo : sysFileUpdateVOS) {
            String dataId = vo.getDataId();
            String attachments = vo.getAttachments();
            Map<String, String> params = new HashMap<>();
            params.put("dataId", dataId);
            //1.传过来的attachments是null，需要将ticketNumber和dataId对应的所有文件状态置为0：不可用
            if (StringUtils.isEmpty(attachments)) {
                sysFileManager.updateByDataId(params);
            } else {
                //先更新表的ticketNumber和dataId字段值为传过来的数据
                List<String> fileUuids = Arrays.asList(attachments.split(","));
                sysFileManager.updateByFileUuidsAndDataId(dataId, fileUuids);
                //根据ticketNumber和dataId查到所有的文件，同fileUuids去做比对，如果不同，则将查询出来的集合去除掉fileUuids包含的数据得到一个list，
                // 该list剩下的元素进行不可用状态更新
                List<String> fileUuidBySql = sysFileManager.selectByDataId(params);
                fileUuidBySql.removeAll(fileUuids);
                sysFileManager.batchUpdateFileStatusByFileUuid(fileUuidBySql);
                sysFileManager.batchUpdateFileStatusAvilableByFileUuids(fileUuids);
            }
        }
        return ResultDTO.success();
    }
}
