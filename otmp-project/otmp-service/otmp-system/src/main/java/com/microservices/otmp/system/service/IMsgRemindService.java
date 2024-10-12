package com.microservices.otmp.system.service;

import com.microservices.otmp.system.domain.MsgRemindDTO;

import java.util.List;

/**
 * 消息通知记录Service接口
 *
 * @author lovefamily
 * @date 2022-10-26
 */
public interface IMsgRemindService
{
    /**
     * 查询消息通知记录
     *
     * @param id 消息通知记录主键
     * @return 消息通知记录DTO
     */
    MsgRemindDTO selectMsgRemindById(Long id);

    /**
     * 查询消息通知记录列表
     *
     * @param msgRemind 消息通知记录
     * @return 消息通知记录DTO集合
     */
    List<MsgRemindDTO> selectMsgRemindList(MsgRemindDTO msgRemind);

    /**
     * 新增消息通知记录
     *
     * @param msgRemind 消息通知记录
     */
    void insertMsgRemind(MsgRemindDTO msgRemind);

    /**
     * 修改消息通知记录
     *
     * @param msgRemind 消息通知记录
     * @return 结果
     */
    int updateMsgRemind(MsgRemindDTO msgRemind);

    /**
     * 批量删除消息通知记录
     *
     * @param ids 需要删除的消息通知记录主键集合
     * @return 结果
     */
    int deleteMsgRemindByIds(Long[] ids);

    /**
     * 删除消息通知记录信息
     *
     * @param id 消息通知记录主键
     * @return 结果
     */
    int deleteMsgRemindById(Long id);

    int batchUpdateMsgRemind(List<Long> ids);
}
