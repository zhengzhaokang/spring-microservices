package com.microservices.otmp.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.notice.domain.dto.EmailReceipentDTO;
import com.microservices.otmp.notice.feign.RemoteEmailService;
import com.microservices.otmp.system.domain.MsgRemindDTO;
import com.microservices.otmp.system.domain.dto.BizSdmsJobLogDTO;
import com.microservices.otmp.system.domain.entity.BizSdmsJobLogDO;
import com.microservices.otmp.system.manager.IBizSdmsJobLogManager;
import com.microservices.otmp.system.service.IBizSdmsJobLogService;
import com.microservices.otmp.system.service.IMsgRemindService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * jobMonitorService业务层处理
 *
 * @author dhc
 * @date 2022-10-11
 */
@Service
public class BizSdmsJobLogServiceImpl implements IBizSdmsJobLogService {
    @Autowired
    private IBizSdmsJobLogManager bizSdmsJobLogManager;

    @Autowired
    IMsgRemindService msgRemindService;

    @Autowired
    RemoteEmailService remoteEmailService;

    /**
     * 查询jobMonitor
     *
     * @param id jobMonitor主键
     * @return jobMonitorDTO
     */
    @Override
    public BizSdmsJobLogDTO selectBizSdmsJobLogById(Long id) {
        BizSdmsJobLogDO bizSdmsJobLogDO = bizSdmsJobLogManager.selectBizSdmsJobLogById(id);
        BizSdmsJobLogDTO bizSdmsJobLogDTO = new BizSdmsJobLogDTO();
        org.springframework.beans.BeanUtils.copyProperties(bizSdmsJobLogDO, bizSdmsJobLogDTO);
        return bizSdmsJobLogDTO;
    }

    /**
     * 查询jobMonitor列表
     *
     * @param bizSdmsJobLog jobMonitor
     * @return jobMonitorDTO
     */
    @Override
    public List<BizSdmsJobLogDTO> selectBizSdmsJobLogList(BizSdmsJobLogDTO bizSdmsJobLog) {

        List<BizSdmsJobLogDO> bizSdmsJobLogDOS =
                bizSdmsJobLogManager.selectBizSdmsJobLogList(bizSdmsJobLog);
        List<BizSdmsJobLogDTO> bizSdmsJobLogList = new ArrayList<>(bizSdmsJobLogDOS.size());
        BeanUtils.copyListProperties(bizSdmsJobLogDOS, bizSdmsJobLogList, BizSdmsJobLogDTO.class);
        return bizSdmsJobLogList;

    }

    /**
     * 新增jobMonitor
     *
     * @param bizSdmsJobLog
     * @return 结果
     */
    @Override
    public int insertBizSdmsJobLog(BizSdmsJobLogDTO bizSdmsJobLog) {
        BizSdmsJobLogDO bizSdmsJobLogDO = new BizSdmsJobLogDO();
        org.springframework.beans.BeanUtils.copyProperties(bizSdmsJobLog, bizSdmsJobLogDO);
        int i = bizSdmsJobLogManager.insertBizSdmsJobLog(bizSdmsJobLogDO);
        bizSdmsJobLog.setId(bizSdmsJobLogDO.getId());
        return i;
    }

    /**
     * * 发送消息通知
     *
     * @param bizSdmsJobLog
     */
    private void sendMsg(BizSdmsJobLogDTO bizSdmsJobLog) {
        String itCodes = getSendList(bizSdmsJobLog);
        if (StrUtil.isBlank(itCodes)) {
            return;
        }
        MsgRemindDTO msgRemindDTO = new MsgRemindDTO();
        msgRemindDTO.setItCodes(itCodes);
        msgRemindDTO.setMsgTitle(bizSdmsJobLog.getGeo() + "  " + bizSdmsJobLog.getJobType() + "  job finished");
        msgRemindDTO.setModule(bizSdmsJobLog.getModules());
        msgRemindDTO.setSysCatalog("Finance");
        msgRemindDTO.setMsgTopic("autoJobFinish");
        msgRemindDTO.setMsgType("Info");
        msgRemindDTO.setMsgInfo(bizSdmsJobLog.getGeo() + "  " + bizSdmsJobLog.getJobType() + " job is finished. Please access the Job Monitor page for the details.");
        msgRemindService.insertMsgRemind(msgRemindDTO);
    }

    private String getSendList(BizSdmsJobLogDTO bizSdmsJobLog) {
        EmailReceipentDTO receipentDTO = new EmailReceipentDTO();
        receipentDTO.setGeoCode(bizSdmsJobLog.getGeo());
        receipentDTO.setJobType(bizSdmsJobLog.getJobType());
        ResultDTO<List<EmailReceipentDTO>> resultDTO = remoteEmailService.getSendToList(receipentDTO);
        Set<String> sendTList = new LinkedHashSet<>();
        if (resultDTO.isSuccess() && CollectionUtils.isNotEmpty(resultDTO.getData())) {
            resultDTO.getData().forEach(e ->
                    sendTList.addAll(Arrays.asList(e.getEmailReceipent().split(",")))
            );
        }
        if (CollectionUtils.isEmpty(sendTList)) {
            return "";
        }
        return StringUtils.join(sendTList, ",");
    }

    /**
     * 修改jobMonitor
     *
     * @param bizSdmsJobLogDTO jobMonitor
     * @return 结果
     */
    @Override
    public int updateBizSdmsJobLog(BizSdmsJobLogDTO bizSdmsJobLog) {
        BizSdmsJobLogDO bizSdmsJobLogDO = new BizSdmsJobLogDO();
        org.springframework.beans.BeanUtils.copyProperties(bizSdmsJobLog, bizSdmsJobLogDO);
        if ("completed".equals(bizSdmsJobLog.getStatus())) {
            sendMsg(bizSdmsJobLog);
        }
        return bizSdmsJobLogManager.updateBizSdmsJobLog(bizSdmsJobLogDO);
    }

    /**
     * 批量删除jobMonitor
     *
     * @param ids 需要删除的jobMonitor主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsJobLogByIds(Long[] ids) {
        return bizSdmsJobLogManager.deleteBizSdmsJobLogByIds(ids);
    }

    /**
     * 删除jobMonitor信息
     *
     * @param id jobMonitor主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsJobLogById(Long id) {
        return bizSdmsJobLogManager.deleteBizSdmsJobLogById(id);
    }
}
