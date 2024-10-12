package com.microservices.otmp.common;

import cn.hutool.core.util.StrUtil;
import com.microservices.otmp.common.constant.ImportSuffConstants;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.domain.ImportResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件验证
 * @author daihuaicai
 */
public class FileValidate {

    private FileValidate() {
    }

    /**
     * 验证excel
     * @param file
     * @return
     */
    public static ImportResult<MultipartFile> validate(MultipartFile file) {
        if (null == file) {
            return ImportResult.importFail("The file cannot be empty");
        }
        String fileName = file.getOriginalFilename();
        if (null != fileName) {
            String suff = fileName.substring(fileName.lastIndexOf("."));
            if (StrUtil.isNotBlank(suff) && (!ImportSuffConstants.XLS.equals(suff) && !ImportSuffConstants.XLSX.equals(suff))) {
                return ImportResult.importFail(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.ACCRUAL_UPLOAD_FILE_TYPE));
            }
            if (StrUtil.isNotBlank(fileName) && fileName.length() > 150) {
                return ImportResult.importFail(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.PAYMENT_FILE_UPLOADING_FILE_SIZE_NAME));
            }
        }
        if (file.getSize() > 104857600) {
            return ImportResult.importFail(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.PAYMENT_FILE_UPLOADING_FILE_SIZE));
        }
        return ImportResult.importSuccess("", file);
    }
}
