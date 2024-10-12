package com.microservices.otmp.common.utils.file;

import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.file.FileNameLengthLimitExceededException;
import com.microservices.otmp.common.exception.file.FileSizeLimitExceededException;
import com.microservices.otmp.common.exception.file.InvalidExtensionException;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.security.Md5Utils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.util.Objects;

/**
 * 文件上传工具类
 * 
 * @author lovefamily
 */
public class FileUploadUtils
{
    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE         = 50L * 1024L * 1024L;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int  DEFAULT_FILE_NAME_LENGTH = 100;

    private static int       counter                  = 0;

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String upload(String baseDir, MultipartFile file) throws IOException
    {
        try
        {
            return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 文件上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException 比如读写文件出错时
     * @throws InvalidExtensionException 文件校验异常
     */
    public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException
    {
        if (file == null) {
            throw new RuntimeException("upload file is null");
        }
        String originalFilename = file.getOriginalFilename();
        int fileNamelength = originalFilename == null ? 0 : originalFilename.length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH)
        {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }
        assertAllowed(file, allowedExtension);
        String fileName = extractFilename(file);
        File desc = getAbsoluteFile(baseDir, fileName);
        file.transferTo(desc);
        String pathFileName = getPathFileName(baseDir, fileName);
        return pathFileName;
    }

    /**
     * 编码文件名
     */
    public static final String extractFilename(MultipartFile file)
    {
        if (file == null) {
            throw new RuntimeException("file is null");
        }
        String fileName = file.getOriginalFilename();
        fileName = fileName == null ? "" : fileName;
        String extension = getExtension(file);
        fileName = DateUtils.datePath() + "/" + encodingFilename(fileName) + "." + extension;
        return fileName;
    }

    private static final String getPathFileName(String uploadDir, String fileName) throws IOException
    {
        int dirLastIndex = uploadDir.lastIndexOf("/") + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        String pathFileName = Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
        return pathFileName;
    }

    private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException
    {
        File desc = new File(uploadDir + File.separator + fileName);
        // 高版本spring中CommonsMultipartFile会自动创建desc,如果创建会报错
        // if (!desc.getParentFile().exists())
        // {
        // desc.getParentFile().mkdirs();
        // }
        // if (!desc.exists())
        // {
        // desc.createNewFile();
        // }
        return desc;
    }

    /**
     * 编码文件名
     */
    private static final String encodingFilename(String fileName)
    {
        fileName = fileName.replace("_", " ");
        fileName = Md5Utils.hash(fileName + System.nanoTime() + counter++);
        return fileName;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws InvalidExtensionException
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, InvalidExtensionException
    {
        long size = file.getSize();
        if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE)
        {
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
        }
        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension))
        {
            if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
                        fileName);
            }
            else
            {
                throw new InvalidExtensionException(allowedExtension, extension, fileName);
            }
        }
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension)
    {
        for (String str : allowedExtension)
        {
            if (str.equalsIgnoreCase(extension))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件名的后缀
     * 
     * @param file 表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file)
    {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension))
        {
            extension = MimeTypeUtils.getExtension(file.getContentType());
        }
        return extension;
    }
    /**
     * File 转 MultipartFile
     * @param  fieldName
     * @param  file
     * @throws Exception
     */
    public static MultipartFile toMultipartFile(String fieldName, File file)  {
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        String contentType = new MimetypesFileTypeMap().getContentType(file);
        FileItem fileItem = diskFileItemFactory.createItem(fieldName, contentType, false, file.getName());
        try (
                InputStream inputStream = new ByteArrayInputStream(FileCopyUtils.copyToByteArray(file));
                OutputStream outputStream = fileItem.getOutputStream()
        ) {
            FileCopyUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            throw new OtmpException("Upload file to MultipartFile error:{}",e);
        }
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        return multipartFile;
    }
    /**
     * MultipartFile 转 File
     *
     * @param multipartFile
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile multipartFile) {
        File toFile;
        try {
            InputStream ins;
            ins = multipartFile.getInputStream();
            toFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            inputStreamToFile(ins, toFile);
            ins.close();
        } catch (Exception e) {
            throw new RuntimeException("MultipartFile changge File abnormal!");
        }
        return toFile;
    }
    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) throws IOException {
        OutputStream os =null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (os != null) {
                os.close();
            }
            if (ins != null) {
                ins.close();
            }
        }
    }

}