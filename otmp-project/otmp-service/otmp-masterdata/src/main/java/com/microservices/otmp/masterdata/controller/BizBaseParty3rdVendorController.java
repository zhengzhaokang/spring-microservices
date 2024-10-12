package com.microservices.otmp.masterdata.controller;

import com.github.pagehelper.Page;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.dto.BizBaseParty3rdVendorDTO;
import com.microservices.otmp.masterdata.service.IBizBaseParty3rdVendorService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * bizBaseParty3rdVendorController
 *
 * @author lovefamily
 * @date 2022-10-12
 */
@RestController
@RequestMapping("/bizBaseParty3rdVendor")
public class BizBaseParty3rdVendorController extends BaseController {
    @Autowired
    private IBizBaseParty3rdVendorService bizBaseParty3rdVendorService;

    /**
     * 查询bizBaseParty3rdVendor列表
     */
    @RequiresPermissions("masterdata:BizBaseParty3rdVendor:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseParty3rdVendorDTO bizBaseParty3rdVendor) {
        Page<Object> count = startPage();
        List<BizBaseParty3rdVendorDTO> list = bizBaseParty3rdVendorService.selectBizBaseParty3rdVendorList(bizBaseParty3rdVendor);
        return getDataTable(list, count);
    }

    /**
     * 导出bizBaseParty3rdVendor列表
     */
    @RequiresPermissions("masterdata:BizBaseParty3rdVendor:export")
    @Log(title = "bizBaseParty3rdVendor", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseParty3rdVendorDTO bizBaseParty3rdVendor) {
        List<BizBaseParty3rdVendorDTO> list = bizBaseParty3rdVendorService.selectBizBaseParty3rdVendorList(bizBaseParty3rdVendor);
        NewExcelUtil<BizBaseParty3rdVendorDTO> util = new NewExcelUtil<>(BizBaseParty3rdVendorDTO.class);
        util.exportExcel(response, list, "3rd Party Vendor");
    }

    /**
     * 获取bizBaseParty3rdVendor详细信息
     */
    @RequiresPermissions("masterdata:BizBaseParty3rdVendor:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseParty3rdVendorDTO> getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(bizBaseParty3rdVendorService.selectBizBaseParty3rdVendorById(id));
    }

    /**
     * 新增bizBaseParty3rdVendor
     */
    @RequiresPermissions("masterdata:BizBaseParty3rdVendor:add")
    @Log(title = "bizBaseParty3rdVendor", businessType = BusinessType.INSERT)
    @PostMapping("add")
    public ResultDTO<Integer> add(@RequestBody BizBaseParty3rdVendorDTO bizBaseParty3rdVendor) {
        //重复性校验
        BizBaseParty3rdVendorDTO dto = new BizBaseParty3rdVendorDTO();
        dto.setVendorCode(bizBaseParty3rdVendor.getVendorCode());
        dto.setBankType(bizBaseParty3rdVendor.getBankType());
        dto.setBankAccount(bizBaseParty3rdVendor.getBankAccount());
        List<BizBaseParty3rdVendorDTO> list = bizBaseParty3rdVendorService.selectBizBaseParty3rdVendorList(dto);
        if (CollectionUtils.isNotEmpty(list)) {
            return ResultDTO.fail("Record exists already");
        }
        return toResultDTO(bizBaseParty3rdVendorService.insertBizBaseParty3rdVendor(bizBaseParty3rdVendor), true);
    }

    /**
     * 修改bizBaseParty3rdVendor
     */
    @RequiresPermissions("masterdata:BizBaseParty3rdVendor:edit")
    @Log(title = "bizBaseParty3rdVendor", businessType = BusinessType.UPDATE)
    @PostMapping("edit")
    public ResultDTO<Integer> edit(@RequestBody BizBaseParty3rdVendorDTO bizBaseParty3rdVendor) {
        return toResultDTO(bizBaseParty3rdVendorService.updateBizBaseParty3rdVendor(bizBaseParty3rdVendor), true);
    }

    /**
     * 删除bizBaseParty3rdVendor
     */
    @RequiresPermissions("masterdata:BizBaseParty3rdVendor:remove")
    @Log(title = "bizBaseParty3rdVendor", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids) {
        return toResultDTO(bizBaseParty3rdVendorService.deleteBizBaseParty3rdVendorByIds(ids), true);
    }

    @RequiresPermissions("masterdata:BizBaseParty3rdVendor:remote/list")
    @GetMapping("/remote/list")
    public ResultDTO<List<BizBaseParty3rdVendorDTO>> remoteList(BizBaseParty3rdVendorDTO bizBaseParty3rdVendor) {
        List<BizBaseParty3rdVendorDTO> list = bizBaseParty3rdVendorService.remoteList(bizBaseParty3rdVendor);
        return ResultDTO.success(list);
    }
}
