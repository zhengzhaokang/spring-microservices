package com.microservices.otmp.download.manager;

import com.microservices.otmp.download.domain.dto.BizSdmsAsyncImportExportTaskDTO;
import com.microservices.otmp.download.domain.entity.BizSdmsAsyncImportExportTaskDO;

import java.util.List;


/**
 * Async Import Export TaskManager接口
 *
 * @author lovefamily
 * @date 2023-02-16
 */
public interface IBizSdmsAsyncImportExportTaskManager {
    /**
     * 查询Async Import Export Task
     *
     * @param taskId Async Import Export Task主键
     * @return Async Import Export Task
     */
    public BizSdmsAsyncImportExportTaskDO selectBizSdmsAsyncImportExportTaskByTaskId(Long taskId);

    /**
     * 查询Async Import Export Task列表
     *
     * @param bizSdmsAsyncImportExportTask Async Import Export Task
     * @return Async Import Export Task集合
     */
    public List<BizSdmsAsyncImportExportTaskDO> selectBizSdmsAsyncImportExportTaskList(BizSdmsAsyncImportExportTaskDTO bizSdmsAsyncImportExportTask);

    /**
     * 新增Async Import Export Task
     *
     * @param bizSdmsAsyncImportExportTask Async Import Export Task
     * @return 结果
     */
    public int insertBizSdmsAsyncImportExportTask(BizSdmsAsyncImportExportTaskDO bizSdmsAsyncImportExportTask);

    /**
     * 修改Async Import Export Task
     *
     * @param bizSdmsAsyncImportExportTask Async Import Export Task
     * @return 结果
     */
    public int updateBizSdmsAsyncImportExportTask(BizSdmsAsyncImportExportTaskDO bizSdmsAsyncImportExportTask);

    /**
     * 批量删除Async Import Export Task
     *
     * @param taskIds 需要删除的Async Import Export Task主键集合
     * @return 结果
     */
    public int deleteBizSdmsAsyncImportExportTaskByTaskIds(Long[] taskIds);

    /**
     * 删除Async Import Export Task信息
     *
     * @param taskId Async Import Export Task主键
     * @return 结果
     */
    public int deleteBizSdmsAsyncImportExportTaskByTaskId(Long taskId);

    public List<BizSdmsAsyncImportExportTaskDO> findBizSdmsAsyncImportExportTaskList(BizSdmsAsyncImportExportTaskDTO bizSdmsAsyncImportExportTask);

    List<BizSdmsAsyncImportExportTaskDO> listByInit();

    int updateStatus(long id, int status);

    int updateStatusHasPre(BizSdmsAsyncImportExportTaskDO bizSdmsAsyncImportExportTask,
                           int preStatus);
}
