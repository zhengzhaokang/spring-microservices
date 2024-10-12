package com.microservices.otmp.masterdata.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.microservices.otmp.masterdata.domain.dto.BizBaseApcDTO;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.masterdata.domain.dto.BizBaseApcDTO;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.masterdata.service.IBizBaseApcService;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.core.page.TableDataInfo;

/**
 * BaseApcController
 * 
 * @author lovefamily
 * @date 2022-07-15
 */
@RestController
@RequestMapping("/bizBaseApc")
public class BizBaseApcController extends BaseController
{
    @Autowired
    private IBizBaseApcService bizBaseApcService;

    /**
     * 查询BaseApc列表
     */
    /*@RequiresPermissions("masterData:bizBaseApc:list")*/
    @GetMapping("/list")
    public TableDataInfo list(BizBaseApcDTO bizBaseApc)
    {
        startPage();
        return getDataTable(bizBaseApcService.selectBizBaseApcListByPage(bizBaseApc));
    }

    /**
     * 导出BaseApc列表
     */
    @RequiresPermissions("masterData:bizBaseApc:export")
    @Log(title = "BaseApc", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseApcDTO bizBaseApc)
    {
        List<BizBaseApcDTO> list = bizBaseApcService.selectBizBaseApcList(bizBaseApc);
        ExcelUtil<BizBaseApcDTO> util = new ExcelUtil<BizBaseApcDTO>(BizBaseApcDTO.class);
        util.exportExcel(response, list, "BaseApc数据");
    }




    /**
     * 获取BaseApc详细信息
     */
    @RequiresPermissions("masterData:bizBaseApc:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseApcDTO> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBaseApcService.selectBizBaseApcById(id));
    }

    /**
     * 新增BaseApc
     */
    @RequiresPermissions("masterData:bizBaseApc:add")
    @Log(title = "BaseApc", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseApcDTO bizBaseApc)
    {
        return toResultDTO(bizBaseApcService.insertBizBaseApc(bizBaseApc),true);
    }

    /**
     * 修改BaseApc
     */
    @RequiresPermissions("masterData:bizBaseApc:edit")
    @Log(title = "BaseApc", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseApcDTO bizBaseApc)
    {
        return toResultDTO(bizBaseApcService.updateBizBaseApc(bizBaseApc),true);
    }

    /**
     * 删除BaseApc
     */
    @RequiresPermissions("masterData:bizBaseApc:remove")
    @Log(title = "BaseApc", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(bizBaseApcService.deleteBizBaseApcByIds(ids),true);
    }
}
