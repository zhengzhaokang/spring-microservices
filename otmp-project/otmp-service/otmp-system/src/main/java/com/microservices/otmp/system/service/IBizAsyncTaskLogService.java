package com.microservices.otmp.system.service;

import com.microservices.otmp.system.domain.BizAsyncTaskLogDTO;
import com.microservices.otmp.system.domain.SysDictData;

import java.util.List;

/**
 * 记录文件上传下载信息Service接口
 *
 * @author lovefamily
 * @date 2022-09-29
 */
public interface IBizAsyncTaskLogService {
    /**
     * 查询记录文件上传下载信息
     *
     * @param id 记录文件上传下载信息主键
     * @return 记录文件上传下载信息DTO
     */
    public BizAsyncTaskLogDTO selectBizAsyncTaskLogById(Long id);

    /**
     * 查询记录文件上传下载信息列表
     *
     * @param bizAsyncTaskLog 记录文件上传下载信息
     * @return 记录文件上传下载信息DTO集合
     */
    public List<BizAsyncTaskLogDTO> selectBizAsyncTaskLogList(BizAsyncTaskLogDTO bizAsyncTaskLog);

    /**
     * 新增记录文件上传下载信息
     *
     * @param bizAsyncTaskLog 记录文件上传下载信息
     * @return 结果
     */
    public BizAsyncTaskLogDTO insertBizAsyncTaskLog(BizAsyncTaskLogDTO bizAsyncTaskLog);

    /**
     * 修改记录文件上传下载信息
     *
     * @param bizAsyncTaskLog 记录文件上传下载信息
     * @return 结果
     */
    public int updateBizAsyncTaskLog(BizAsyncTaskLogDTO bizAsyncTaskLog);

    /**
     * 批量删除记录文件上传下载信息
     *
     * @param ids 需要删除的记录文件上传下载信息主键集合
     * @return 结果
     */
    public int deleteBizAsyncTaskLogByIds(Long[] ids);

    /**
     * 删除记录文件上传下载信息信息
     *
     * @param id 记录文件上传下载信息主键
     * @return 结果
     */
    public int deleteBizAsyncTaskLogById(Long id);

    List<BizAsyncTaskLogDTO> getProgress(Long[] ids);

    void deleteAsyncTask(Long id);

    List<SysDictData> businessTypeSelect();

    /**
     *  module 模块
     * @return
     */
    List<SysDictData> moduleSelect();

    List<SysDictData> statusSelect();
}
