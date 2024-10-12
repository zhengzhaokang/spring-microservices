package com.microservices.otmp.system.mapper;

import com.microservices.otmp.system.domain.BizAsyncTaskLogDTO;
import com.microservices.otmp.system.domain.entity.BizAsyncTaskLogDO;

import java.util.List;


/**
 * 记录文件上传下载信息Mapper接口
 *
 * @author lovefamily
 * @date 2022-09-29
 */
public interface BizAsyncTaskLogMapper {
    /**
     * 查询记录文件上传下载信息
     *
     * @param id 记录文件上传下载信息主键
     * @return 记录文件上传下载信息
     */
    public BizAsyncTaskLogDO selectBizAsyncTaskLogById(Long id);

    /**
     * 查询记录文件上传下载信息列表
     *
     * @param bizAsyncTaskLogDTO 记录文件上传下载信息
     * @return 记录文件上传下载信息集合
     */
    public List<BizAsyncTaskLogDO> selectBizAsyncTaskLogList(BizAsyncTaskLogDTO bizAsyncTaskLogDTO);

    /**
     * 新增记录文件上传下载信息
     *
     * @param bizAsyncTaskLogDO 记录文件上传下载信息
     * @return 结果
     */
    public int insertBizAsyncTaskLog(BizAsyncTaskLogDO bizAsyncTaskLogDO);

    /**
     * 修改记录文件上传下载信息
     *
     * @param bizAsyncTaskLogDO 记录文件上传下载信息
     * @return 结果
     */
    public int updateBizAsyncTaskLog(BizAsyncTaskLogDO bizAsyncTaskLogDO);

    /**
     * 删除记录文件上传下载信息
     *
     * @param id 记录文件上传下载信息主键
     * @return 结果
     */
    public int deleteBizAsyncTaskLogById(Long id);

    /**
     * 批量删除记录文件上传下载信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizAsyncTaskLogByIds(Long[] ids);
}
