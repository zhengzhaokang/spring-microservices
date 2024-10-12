package com.microservices.otmp.notice.controller;

import com.google.common.collect.Lists;
import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.notice.common.NoticeConstant;
import com.microservices.otmp.notice.dto.NoticeDto;
import com.microservices.otmp.notice.service.NoticeService;
import com.microservices.otmp.notice.vo.NoticeVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notice/template")
public class NoticeController extends BaseController {
    @Autowired
    private NoticeService noticeService;

    /**
     * 查询通知公告
     */
    @GetMapping("get/{noticeId}")
    @HasPermissions("notice:message:view")
    public ResultDTO<NoticeVO> get(@PathVariable("noticeId") String noticeId)
    {
        return ResultDTO.success(noticeService.selectNoticeById(Long.valueOf(noticeId)));
    }

    /**
     * 查询通知公告列表
     */
    @PostMapping("list")
    @HasPermissions("notice:message:view")
    public ResultDTO<TableDataInfo> list(@RequestBody NoticeVO sysNotice)
    {
        startPage();
        List<NoticeDto> noticeDtos = noticeService.selectNoticeList(sysNotice);
        TableDataInfo dataTable = getDataTable(noticeDtos);
        List<NoticeVO> noticeVOList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(noticeDtos)) {
            return ResultDTO.success(dataTable);
        }
        noticeDtos.forEach(item -> {
            NoticeVO noticeVO = NoticeVO.builder().noticeId(String.valueOf(item.getNoticeId())).noticeTitle(item.getNoticeTitle()).
                    noticeType(item.getNoticeType()).noticeContent(item.getNoticeContent()).status(item.getStatus()).
                    createTime(item.getCreateTime()).updateTime(item.getUpdateTime()).remark(item.getRemark()).
                    createBy(item.getCreateBy()).updateBy(item.getUpdateBy()).
                    build();
            noticeVOList.add(noticeVO);
        });
        dataTable.setRows(noticeVOList);
        return ResultDTO.success(dataTable);
    }


    /**
     * 新增保存通知公告
     */
    @PostMapping("save")
    @HasPermissions("notice:message:add")
    public ResultDTO<Object> addSave(@RequestBody NoticeVO sysNotice)
    {
        int result = noticeService.insertNotice(sysNotice);
        if (result == NoticeConstant.INSERT_ERROR_CODE_0)
        {
            return ResultDTO.fail("input param exist null");
        } else if (result == NoticeConstant.INSERT_ERROR_CODE_2) {
            return ResultDTO.fail("input noticeType exist");
        }
        return ResultDTO.success(result);
    }

    /**
     * 修改保存通知公告
     */
    @PostMapping("update")
    @HasPermissions("notice:message:edit")
    public ResultDTO<Integer> editSave(@RequestBody NoticeVO sysNotice)
    {
        return ResultDTO.success(noticeService.updateNotice(sysNotice));
    }

    /**
     * 删除通知公告
     */
    @PostMapping("remove")
    @HasPermissions("notice:message:remove")
    public ResultDTO<Integer> remove(@RequestParam("ids")  String ids)
    {
        return ResultDTO.success(noticeService.deleteNoticeByIds(ids));
    }
}
