package com.microservices.otmp.download.mapper;

import com.microservices.otmp.download.domain.dto.BizSdmsAsyncImportExportTaskDTO;
import com.microservices.otmp.download.domain.entity.BizSdmsAsyncImportExportTaskDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Async Import Export TaskMapper接口
 * 
 * @author lovefamily
 * @date 2023-02-16
 */
public interface BizSdmsAsyncImportExportTaskMapper
{
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
     * @param bizSdmsAsyncImportExportTaskDTO Async Import Export Task
     * @return Async Import Export Task集合
     */
    public List<BizSdmsAsyncImportExportTaskDO> selectBizSdmsAsyncImportExportTaskList(BizSdmsAsyncImportExportTaskDTO bizSdmsAsyncImportExportTask);

    public List<BizSdmsAsyncImportExportTaskDO> findBizSdmsAsyncImportExportTaskList(BizSdmsAsyncImportExportTaskDTO bizSdmsAsyncImportExportTask);

    /**
     * 新增Async Import Export Task
     * 
     * @param bizSdmsAsyncImportExportTaskDO Async Import Export Task
     * @return 结果
     */
    public int insertBizSdmsAsyncImportExportTask(BizSdmsAsyncImportExportTaskDO bizSdmsAsyncImportExportTask);

    /**
     * 修改Async Import Export Task
     * 
     * @param bizSdmsAsyncImportExportTaskDO Async Import Export Task
     * @return 结果
     */
    public int updateBizSdmsAsyncImportExportTask (BizSdmsAsyncImportExportTaskDO bizSdmsAsyncImportExportTask);

    /**
     * 删除Async Import Export Task
     * 
     * @param taskId Async Import Export Task主键
     * @return 结果
     */
    public int deleteBizSdmsAsyncImportExportTaskByTaskId(Long taskId);

    /**
     * 批量删除Async Import Export Task
     *
     * @param taskIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizSdmsAsyncImportExportTaskByTaskIds(Long[] taskIds);

    List<BizSdmsAsyncImportExportTaskDO> listByInit();

    int updateStatus(@Param("id") long id, @Param("status") int status);

    int updateStatusHasPre(@Param("taskDO") BizSdmsAsyncImportExportTaskDO bizSdmsAsyncImportExportTask,
                           @Param("preStatus") int preStatus);
}
