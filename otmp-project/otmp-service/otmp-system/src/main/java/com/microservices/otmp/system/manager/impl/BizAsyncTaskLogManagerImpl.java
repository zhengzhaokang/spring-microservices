package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.system.domain.BizAsyncTaskLogDTO;
import com.microservices.otmp.system.domain.entity.BizAsyncTaskLogDO;
import com.microservices.otmp.system.manager.IBizAsyncTaskLogManager;
import com.microservices.otmp.system.mapper.BizAsyncTaskLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 记录文件上传下载信息Manager业务层处理
 *
 * @author lovefamily
 * @date 2022-09-29
 */
@Service
public class BizAsyncTaskLogManagerImpl implements IBizAsyncTaskLogManager {
    @Autowired
    private BizAsyncTaskLogMapper bizAsyncTaskLogMapper;

    /**
     * 查询记录文件上传下载信息
     *
     * @param id 记录文件上传下载信息主键
     * @return 记录文件上传下载信息DO
     */
    @Override
    public BizAsyncTaskLogDO selectBizAsyncTaskLogById(Long id) {
        return bizAsyncTaskLogMapper.selectBizAsyncTaskLogById(id);
    }

    /**
     * 查询记录文件上传下载信息列表
     *
     * @param bizAsyncTaskLog 记录文件上传下载信息
     * @return 记录文件上传下载信息DO
     */
    @Override
    public List<BizAsyncTaskLogDO> selectBizAsyncTaskLogList(BizAsyncTaskLogDTO bizAsyncTaskLog) {
        return bizAsyncTaskLogMapper.selectBizAsyncTaskLogList(bizAsyncTaskLog);
    }

    /**
     * 新增记录文件上传下载信息
     *
     * @param bizAsyncTaskLog 记录文件上传下载信息
     * @return 结果
     */
    @Override
    public int insertBizAsyncTaskLog(BizAsyncTaskLogDO bizAsyncTaskLog) {
        bizAsyncTaskLog.setCreateTime(DateUtils.getNowDate());
        return bizAsyncTaskLogMapper.insertBizAsyncTaskLog(bizAsyncTaskLog);
    }

    /**
     * 修改记录文件上传下载信息
     *
     * @param bizAsyncTaskLog 记录文件上传下载信息
     * @return 结果
     */
    @Override
    public int updateBizAsyncTaskLog(BizAsyncTaskLogDO bizAsyncTaskLog) {
        bizAsyncTaskLog.setUpdateTime(DateUtils.getNowDate());
        return bizAsyncTaskLogMapper.updateBizAsyncTaskLog(bizAsyncTaskLog);
    }

    /**
     * 批量删除记录文件上传下载信息
     *
     * @param ids 需要删除的记录文件上传下载信息主键
     * @return 结果
     */
    @Override
    public int deleteBizAsyncTaskLogByIds(Long[] ids) {
        return bizAsyncTaskLogMapper.deleteBizAsyncTaskLogByIds(ids);
    }

    /**
     * 删除记录文件上传下载信息信息
     *
     * @param id 记录文件上传下载信息主键
     * @return 结果
     */
    @Override
    public int deleteBizAsyncTaskLogById(Long id) {
        return bizAsyncTaskLogMapper.deleteBizAsyncTaskLogById(id);
    }
}
