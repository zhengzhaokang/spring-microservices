package com.microservices.otmp.system.mapper;

import com.microservices.otmp.system.domain.MsgRemindDTO;
import com.microservices.otmp.system.domain.entity.MsgRemindDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 消息通知记录Mapper接口
 * 
 * @author lovefamily
 * @date 2022-10-26
 */
public interface MsgRemindMapper
{
    /**
     * 查询消息通知记录
     * 
     * @param id 消息通知记录主键
     * @return 消息通知记录
     */
    public MsgRemindDO selectMsgRemindById(Long id);

    /**
     * 查询消息通知记录列表
     * 
     * @param msgRemind 消息通知记录
     * @return 消息通知记录集合
     */
    public List<MsgRemindDO> selectMsgRemindList(MsgRemindDTO msgRemind);

    public int selectMsgRemindListForCount(MsgRemindDTO msgRemind);

    /**
     * 新增消息通知记录
     * 
     * @param msgRemind 消息通知记录
     * @return 结果
     */
    public int insertMsgRemind(MsgRemindDO msgRemind);

    /**
     * 修改消息通知记录
     * 
     * @param msgRemind 消息通知记录
     * @return 结果
     */
    public int updateMsgRemind (MsgRemindDO msgRemind);

    /**
     * 删除消息通知记录
     * 
     * @param id 消息通知记录主键
     * @return 结果
     */
    public int deleteMsgRemindById(Long id);

    /**
     * 批量删除消息通知记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMsgRemindByIds(Long[] ids);

    int batchUpdateMsgRemindRead(@Param("ids") List<Long> ids, @Param("hasRead") Integer hasRead);
}
