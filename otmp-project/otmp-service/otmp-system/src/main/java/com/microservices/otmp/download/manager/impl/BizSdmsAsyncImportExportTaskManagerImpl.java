package com.microservices.otmp.download.manager.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.download.domain.dto.BizSdmsAsyncImportExportTaskDTO;
import com.microservices.otmp.download.domain.entity.BizSdmsAsyncImportExportTaskDO;
import com.microservices.otmp.download.manager.IBizSdmsAsyncImportExportTaskManager;
import com.microservices.otmp.download.mapper.BizSdmsAsyncImportExportTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Async Import Export TaskManager业务层处理
 *
 * @author lovefamily
 * @date 2023-02-16
 */
@Service
public class BizSdmsAsyncImportExportTaskManagerImpl implements IBizSdmsAsyncImportExportTaskManager {
    @Autowired
    private BizSdmsAsyncImportExportTaskMapper bizSdmsAsyncImportExportTaskMapper;

    /**
     * 查询Async Import Export Task
     *
     * @param taskId Async Import Export Task主键
     * @return Async Import Export TaskDO
     */
    @Override
    public BizSdmsAsyncImportExportTaskDO selectBizSdmsAsyncImportExportTaskByTaskId(Long taskId) {
        return bizSdmsAsyncImportExportTaskMapper.selectBizSdmsAsyncImportExportTaskByTaskId(taskId);
    }

    /**
     * 查询Async Import Export Task列表
     *
     * @param bizSdmsAsyncImportExportTask Async Import Export Task
     * @return Async Import Export TaskDO
     */
    @Override
    public List<BizSdmsAsyncImportExportTaskDO> selectBizSdmsAsyncImportExportTaskList(BizSdmsAsyncImportExportTaskDTO bizSdmsAsyncImportExportTask) {
        return bizSdmsAsyncImportExportTaskMapper.selectBizSdmsAsyncImportExportTaskList(bizSdmsAsyncImportExportTask);
    }

    @Override
    public List<BizSdmsAsyncImportExportTaskDO> findBizSdmsAsyncImportExportTaskList(BizSdmsAsyncImportExportTaskDTO bizSdmsAsyncImportExportTask) {
        return bizSdmsAsyncImportExportTaskMapper.findBizSdmsAsyncImportExportTaskList(bizSdmsAsyncImportExportTask);
    }

    @Override
    public List<BizSdmsAsyncImportExportTaskDO> listByInit() {
        return bizSdmsAsyncImportExportTaskMapper.listByInit();
    }

    @Override
    public int updateStatus(long id, int status) {
        return bizSdmsAsyncImportExportTaskMapper.updateStatus(id, status);
    }


    /**
     * 新增Async Import Export Task
     *
     * @param bizSdmsAsyncImportExportTask Async Import Export Task
     * @return 结果
     */
    @Override
    public int insertBizSdmsAsyncImportExportTask(BizSdmsAsyncImportExportTaskDO bizSdmsAsyncImportExportTask) {
        bizSdmsAsyncImportExportTask.setCreateTime(DateUtils.getNowDate());
        return bizSdmsAsyncImportExportTaskMapper.insertBizSdmsAsyncImportExportTask(bizSdmsAsyncImportExportTask);
    }

    /**
     * 修改Async Import Export Task
     *
     * @param bizSdmsAsyncImportExportTask Async Import Export Task
     * @return 结果
     */
    @Override
    public int updateBizSdmsAsyncImportExportTask(BizSdmsAsyncImportExportTaskDO bizSdmsAsyncImportExportTask) {
        bizSdmsAsyncImportExportTask.setUpdateTime(DateUtils.getNowDate());
        return bizSdmsAsyncImportExportTaskMapper.updateBizSdmsAsyncImportExportTask(bizSdmsAsyncImportExportTask);
    }

    /**
     * 批量删除Async Import Export Task
     *
     * @param taskIds 需要删除的Async Import Export Task主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsAsyncImportExportTaskByTaskIds(Long[] taskIds) {
        return bizSdmsAsyncImportExportTaskMapper.deleteBizSdmsAsyncImportExportTaskByTaskIds(taskIds);
    }

    /**
     * 删除Async Import Export Task信息
     *
     * @param taskId Async Import Export Task主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsAsyncImportExportTaskByTaskId(Long taskId) {
        return bizSdmsAsyncImportExportTaskMapper.deleteBizSdmsAsyncImportExportTaskByTaskId(taskId);
    }

    @Override
    public int updateStatusHasPre(BizSdmsAsyncImportExportTaskDO bizSdmsAsyncImportExportTask,
                                  int preStatus) {
        return bizSdmsAsyncImportExportTaskMapper.updateStatusHasPre(bizSdmsAsyncImportExportTask, preStatus);
    }

    ;
}
