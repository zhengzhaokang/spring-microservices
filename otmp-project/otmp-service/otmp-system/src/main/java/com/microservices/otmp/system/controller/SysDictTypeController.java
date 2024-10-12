package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.PageDomain;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.system.domain.SysDictType;
import com.microservices.otmp.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 字典类型 提供者
 *
 * @Author lovefamily
 * @date 2019-05-20
 */
@RestController
@RequestMapping("dict/type")
public class SysDictTypeController extends BaseController
{

	private static final String SHOW_DATA_STATUS="1";
	@Autowired
	private ISysDictTypeService sysDictTypeService;
	
	/**
	 * 查询字典类型
	 */
	@GetMapping("get/{dictId}")
	public SysDictType get(@PathVariable("dictId") Long dictId)
	{
		return sysDictTypeService.selectDictTypeById(dictId);
		
	}
	
	/**
	 * 查询字典类型列表
	 */
	@GetMapping("list")
	@HasPermissions("system:dict:list")
	public TableDataInfo list(SysDictType sysDictType, PageDomain page)
	{
		startPage();
        return getDataTable(sysDictTypeService.selectDictTypePage(sysDictType));
	}

	/**
	 * 查询字典类型列表(展示给用户的数据)
	 */
	@GetMapping("cusList")
	@HasPermissions("system:dict:list")
	public ResultDTO<List<SysDictType> > cusList(SysDictType sysDictType, PageDomain page)
	{
		sysDictType.setShowDataStatus(SHOW_DATA_STATUS);
		startPage();
		return ResultDTO.success(sysDictTypeService.selectDictTypeListLike(sysDictType));
	}

	@OperLog(title = "字典类型", businessType = BusinessType.EXPORT)
	@HasPermissions("system:dict:export")
	@PostMapping("/export")
	public void export(HttpServletResponse response, SysDictType dictType)
	{
		dictType.setShowDataStatus(SHOW_DATA_STATUS);
		List<SysDictType> list = sysDictTypeService.selectDictTypeListLike(dictType);
		NewExcelUtil<SysDictType> util = new NewExcelUtil<>(SysDictType.class);
		 util.exportExcel(response,list, "DictType");

	}
	@OperLog(title = "字典类型", businessType = BusinessType.EXPORT)
	@HasPermissions("system:dict:export")
	@PostMapping("/export/dict")
	public void exportDict(HttpServletResponse response, SysDictType dictType)
	{
		List<SysDictType> list = sysDictTypeService.selectDictTypeListLike(dictType);
		NewExcelUtil<SysDictType> util = new NewExcelUtil<>(SysDictType.class);
		util.exportExcel(response,list, "DictType");

	}
	/**
	 * 新增保存字典类型
	 */
	@OperLog(title = "字典类型", businessType = BusinessType.INSERT)
    @HasPermissions("system:dict:add")
	@PostMapping("save")
	public ResultDTO<Integer> addSave(@RequestBody SysDictType sysDictType)
	{
		sysDictType.setCreateBy(getLoginName());
		return ResultDTO.success(sysDictTypeService.save(sysDictType));
	}

	/**
	 * 修改保存字典类型
	 */
	@OperLog(title = "字典类型", businessType = BusinessType.UPDATE)
    @HasPermissions("system:dict:edit")
	@PostMapping("update")
	public ResultDTO<Integer> editSave(@RequestBody SysDictType sysDictType)
	{
		sysDictType.setUpdateBy(getLoginName());
		return ResultDTO.success(sysDictTypeService.updateDictType(sysDictType));
	}
	
	/**
	 * 删除字典类型
	 */
	@OperLog(title = "字典类型", businessType = BusinessType.DELETE)
	@HasPermissions("system:dict:remove")
	@PostMapping("remove")
	public ResultDTO<Integer> remove(String ids) {
		String loginName = getLoginName();
		return ResultDTO.success(sysDictTypeService.deleteDictTypeByIds(loginName,ids));
	}
	/**
	 * 获取字典选择框列表
	 */
	@GetMapping("/optionselect")
	public ResultDTO<List<SysDictType> > optionselect()
	{
		List<SysDictType> dictTypes = sysDictTypeService.selectDictTypeAll();
		return ResultDTO.success(dictTypes);
	}
	
}
