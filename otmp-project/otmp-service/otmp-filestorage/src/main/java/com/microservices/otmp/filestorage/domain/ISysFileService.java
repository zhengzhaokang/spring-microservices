package com.microservices.otmp.filestorage.domain;

import com.microservices.otmp.common.core.domain.R;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.filestorage.dto.SystemFile;
import com.microservices.otmp.filestorage.dto.SystemFileUpdateVO;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.filestorage.dto.SystemFile;
import com.microservices.otmp.filestorage.dto.SystemFileUpdateVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 文件保存  服务层
 */
public interface ISysFileService {
    /**
     * 保存至sys_file
     *
     * @return
     */
    void fileUpload(MultipartFile[] uploadFiles, String serverFilePath, List<String> fileUuids, String loginName, String dataId) throws IOException;

    SystemFile selectByFileUuid(String fileUUID);

    int deleteByFileUuid(String fileUuid);

    ResultDTO<Object> selectBySysFile(SystemFile sysFile);

    /**
     * 需要调用方传三个参数： ticketNumber, dataId, fileUuids
     * @param sysFileUpdateVOS
     * @return
     */
    ResultDTO<Object> updateBySysFile(List<SystemFileUpdateVO> sysFileUpdateVOS);
}
