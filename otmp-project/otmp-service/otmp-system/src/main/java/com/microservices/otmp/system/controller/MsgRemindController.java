package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.kafka.util.KafkaHandleListener;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.system.domain.MsgRemindDTO;
import com.microservices.otmp.system.service.IMsgRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 消息通知记录Controller
 * 
 * @author lovefamily
 * @date 2022-10-26
 */
@RestController
@RequestMapping("/msgRemind")
public class MsgRemindController extends BaseController
{
    private static final String KAFKA_LISTENER_ID = "saveMsg";

    @Autowired
    private KafkaHandleListener kafkaHandleListener;

    @Autowired
    private IMsgRemindService msgRemindService;

    /**
     * 查询消息通知记录列表
     */
    @RequiresPermissions("system:msgRemind:list")
    @GetMapping("/list")
    public TableDataInfo list(MsgRemindDTO msgRemind)
    {
        startPage();
        List<MsgRemindDTO> list = msgRemindService.selectMsgRemindList(msgRemind);
        return getDataTable(list);
    }

    /**
     * 导出消息通知记录列表
     */
    @RequiresPermissions("system:msgRemind:export")
    @Log(title = "消息通知记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MsgRemindDTO msgRemind)
    {
        List<MsgRemindDTO> list = msgRemindService.selectMsgRemindList(msgRemind);
        ExcelUtil<MsgRemindDTO> util = new ExcelUtil<>(MsgRemindDTO.class);
        util.exportExcel(response, list, "消息通知记录数据");
    }

    /**
     * 获取消息通知记录详细信息
     */
    @RequiresPermissions("system:msgRemind:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<MsgRemindDTO> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(msgRemindService.selectMsgRemindById(id));
    }

    /**
     * 新增消息通知记录
     */
    @RequiresPermissions("system:msgRemind:add")
    @Log(title = "消息通知记录", businessType = BusinessType.INSERT)
    @PostMapping(value = "/send")
    public ResultDTO<Object> send(@RequestBody MsgRemindDTO msgRemind)
    {
        msgRemindService.insertMsgRemind(msgRemind);
        return ResultDTO.success();
    }

    /**
     * 修改消息通知记录
     */
    @RequiresPermissions("system:msgRemind:edit")
    @Log(title = "消息通知记录", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/setReadFlag")
    public ResultDTO<Integer> setReadFlag(@RequestBody MsgRemindDTO msgRemind)
    {
        return toResultDTO(msgRemindService.updateMsgRemind(msgRemind),true);
    }


    /**
     * 批量修改消息通知记录
     */
    @RequiresPermissions("system:msgRemind:edit")
    @Log(title = "消息通知记录", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/batchSetReadFlag")
    public ResultDTO<Integer> batchSetReadFlag(@RequestBody List<Long> ids) {
        return toResultDTO(msgRemindService.batchUpdateMsgRemind(ids), true);
    }

    /**
     * 删除消息通知记录
     */
    @RequiresPermissions("system:msgRemind:remove")
    @Log(title = "消息通知记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(msgRemindService.deleteMsgRemindByIds(ids),true);
    }


    /**
     * 开启监听
     */
    @GetMapping("/startKafkaListener")
    public void start(String listenerId) {
        if(CommonUtils.stringIsEmpty(listenerId)){
            listenerId = KAFKA_LISTENER_ID;
        }
        kafkaHandleListener.start(listenerId);
    }

    /**
     * 关闭监听
     */
    @GetMapping("/stopKafkaListener")
    public void stop(String listenerId) {
        if(CommonUtils.stringIsEmpty(listenerId)){
            listenerId = KAFKA_LISTENER_ID;
        }
        kafkaHandleListener.stop(listenerId);
    }
}
