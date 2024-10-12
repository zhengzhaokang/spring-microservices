package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.MsgRemindDTO;
import com.microservices.otmp.system.domain.entity.MsgRemindDO;
import com.microservices.otmp.system.manager.IMsgRemindManager;
import com.microservices.otmp.system.mapper.MsgRemindMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息通知记录Manager业务层处理
 *
 * @author lovefamily
 * @date 2022-10-26
 */
@Service
public class MsgRemindManagerImpl implements IMsgRemindManager {

    @Autowired
    private MsgRemindMapper msgRemindMapper;

    /**
     * 查询消息通知记录
     *
     * @param id 消息通知记录主键
     * @return 消息通知记录DO
     */
    @Override
    public MsgRemindDO selectMsgRemindById(Long id) {
        return msgRemindMapper.selectMsgRemindById(id);
    }

    /**
     * 查询消息通知记录列表
     *
     * @param msgRemind 消息通知记录
     * @return 消息通知记录DO
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<MsgRemindDO> selectMsgRemindList(MsgRemindDTO msgRemind) {
        int notReadCount = msgRemindMapper.selectMsgRemindListForCount(msgRemind);
        //查询所有状态0未读 1已读 2新消息
        List<MsgRemindDO> msgRemindDOS = msgRemindMapper.selectMsgRemindList(msgRemind);
        List<MsgRemindDO> retMsgRemindDOS = new ArrayList<>();
        BeanUtils.copyListProperties(msgRemindDOS, retMsgRemindDOS, MsgRemindDO.class);
        //更新 将2更新为0
        for (MsgRemindDO msgRemindDO : msgRemindDOS) {
            if (msgRemindDO.getHasRead() == 2) {
                msgRemindDO.setHasRead(0);
                msgRemindMapper.updateMsgRemind(msgRemindDO);
            }
        }
        //设置未读数量
        retMsgRemindDOS.forEach(e -> e.setNotReadCount(notReadCount));
        return retMsgRemindDOS;
    }

    /**
     * 新增消息通知记录
     *
     * @param msgRemind 消息通知记录
     * @return 结果
     */
    @Override
    public int insertMsgRemind(MsgRemindDO msgRemind) {
        msgRemind.setCreateTime(DateUtils.getNowDate());
        //默认设置为新的
        msgRemind.setHasRead(2);
        return msgRemindMapper.insertMsgRemind(msgRemind);
    }

    /**
     * 修改消息通知记录
     *
     * @param msgRemind 消息通知记录
     * @return 结果
     */
    @Override
    public int updateMsgRemind(MsgRemindDO msgRemind) {
        msgRemind.setUpdateTime(DateUtils.getNowDate());
        //sql限制了只能改 hasRead字段
        return msgRemindMapper.updateMsgRemind(msgRemind);
    }

    /**
     * 批量删除消息通知记录
     *
     * @param ids 需要删除的消息通知记录主键
     * @return 结果
     */
    @Override
    public int deleteMsgRemindByIds(Long[] ids) {
        return msgRemindMapper.deleteMsgRemindByIds(ids);
    }

    /**
     * 删除消息通知记录信息
     *
     * @param id 消息通知记录主键
     * @return 结果
     */
    @Override
    public int deleteMsgRemindById(Long id) {
        return msgRemindMapper.deleteMsgRemindById(id);
    }

    @Override
    public int batchUpdateMsgRemindRead(List<Long> ids, Integer hasRead) {
        return msgRemindMapper.batchUpdateMsgRemindRead(ids, hasRead);
    }
}
