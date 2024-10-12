package com.microservices.otmp.system.manager;

import com.microservices.otmp.system.domain.SysUserOnline;
import com.microservices.otmp.system.domain.entity.SysUserOnlineDO;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:23
 */
public interface SysUserOnlineManager {
    /**
     * 通过会话序号查询信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    public SysUserOnlineDO selectOnlineById(String sessionId);

    /**
     * 通过会话序号删除信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    public int deleteOnlineById(String sessionId);

    /**
     * 保存会话信息
     *
     * @param online 会话信息
     * @return 结果
     */
    public int saveOnline(SysUserOnlineDO online);

    /**
     * 查询会话集合
     *
     * @param userOnline 会话参数
     * @return 会话集合
     */
    public List<SysUserOnlineDO> selectUserOnlineList(SysUserOnline userOnline);

    /**
     * 查询过期会话集合
     *
     * @param lastAccessTime 过期时间
     * @return 会话集合
     */
    public List<SysUserOnlineDO> selectOnlineByExpired(String lastAccessTime);
}
