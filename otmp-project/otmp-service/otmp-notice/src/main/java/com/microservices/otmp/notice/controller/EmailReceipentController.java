package com.microservices.otmp.notice.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.notice.domain.dto.EmailReceipentDTO;
import com.microservices.otmp.notice.service.IEmailReceipentService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Email ReceipentController
 * 
 * @author lovefamily
 * @date 2022-11-10
 */
@RestController
@RequestMapping("/receipent")
public class EmailReceipentController extends BaseController
{
    @Autowired
    private IEmailReceipentService emailReceipentService;

    /**
     * 查询Email Receipent列表
     */
    @RequiresPermissions("email:receipent:list")
    @GetMapping("/list")
    public TableDataInfo list(EmailReceipentDTO emailReceipent)
    {
        startPage();
        List<EmailReceipentDTO> list = emailReceipentService.selectEmailReceipentList(emailReceipent);
        return getDataTable(list);
    }

    @RequiresPermissions("email:receipent:list")
    @PostMapping("/getSendToList")
    public ResultDTO<List<EmailReceipentDTO>> getSendToList(@RequestBody EmailReceipentDTO emailReceipent)
    {
        List<EmailReceipentDTO> list = emailReceipentService.selectEmailReceipentList(emailReceipent);
        return ResultDTO.success(list);
    }

    /**
     * 导出Email Receipent列表
     */
    @RequiresPermissions("email:receipent:export")
    @Log(title = "Email Receipent", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EmailReceipentDTO emailReceipent)
    {
        List<EmailReceipentDTO> list = emailReceipentService.selectEmailReceipentList(emailReceipent);
        ExcelUtil<EmailReceipentDTO> util = new ExcelUtil<>(EmailReceipentDTO.class);
        util.exportExcel(response, list, "Email Receipent数据");
    }

    /**
     * 获取Email Receipent详细信息
     */
    @RequiresPermissions("email:receipent:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<EmailReceipentDTO> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(emailReceipentService.selectEmailReceipentById(id));
    }

    /**
     * 新增Email Receipent
     */
    @RequiresPermissions("email:receipent:add")
    @Log(title = "Email Receipent", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody EmailReceipentDTO emailReceipent)
    {
        return toResultDTO(emailReceipentService.insertEmailReceipent(emailReceipent,getLoginName()),true);
    }

    /**
     * 修改Email Receipent
     */
    @RequiresPermissions("email:receipent:edit")
    @Log(title = "Email Receipent", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody EmailReceipentDTO emailReceipent)
    {
        return toResultDTO(emailReceipentService.updateEmailReceipent(emailReceipent,getLoginName() ),true);
    }

    /**
     * 删除Email Receipent
     */
    @RequiresPermissions("email:receipent:remove")
    @Log(title = "Email Receipent", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(emailReceipentService.deleteEmailReceipentByIds(ids),true);
    }
}
