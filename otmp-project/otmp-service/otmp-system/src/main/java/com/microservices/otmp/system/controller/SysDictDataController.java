package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 字典数据 提供者
 * 
 * @Author lovefamily
 * @date 2019-05-20
 */
@RestController
@RequestMapping("dict/data")
public class SysDictDataController extends BaseController
{
	
	@Autowired
	private ISysDictDataService sysDictDataService;
	
	/**
	 * 查询字典数据
	 */
	@GetMapping("get/{dictCode}")
	public SysDictData get(@PathVariable("dictCode") Long dictCode)
	{
		return sysDictDataService.selectDictDataById(dictCode);
		
	}
	
	/**
	 * 查询字典数据列表
	 */
	@GetMapping("list")
	@HasPermissions("system:dict:list")
	public TableDataInfo list(SysDictData sysDictData)
	{
		startPage();
        return getDataTable(sysDictDataService.selectDictDataPage(sysDictData));
	}

	@OperLog(title = "字典类型", businessType = BusinessType.EXPORT)
	@HasPermissions("system:dict:export")
	@PostMapping("/export")
	public void export(HttpServletResponse response, SysDictData dictData)
	{
		List<SysDictData> list = sysDictDataService.selectDictDataList(dictData);
		NewExcelUtil<SysDictData> util = new NewExcelUtil<>(SysDictData.class);
		 util.exportExcel(response,list, "DictData");

	}
	/**
     * 根据字典类型查询字典数据信息
     * 
     * @param dictType 字典类型
     * @return 参数键值
     */
	@GetMapping("type")
    public List<SysDictData> getType(String dictType)
    {

		return sysDictDataService.selectDictDataByType(dictType);
    }

	/**
	 * 根据字典类型查询字典数据信息
	 */
	@GetMapping(value = "type/{dictType}")
	public ResultDTO<List<SysDictData>> dictType(@PathVariable String dictType)
	{
		List<SysDictData> data = sysDictDataService.selectDictDataByType(dictType);
		if (StringUtils.isNull(data))
		{
			data = new ArrayList<>();
		}
		return ResultDTO.success(data);
	}

	/**
     * 根据字典类型和字典键值查询字典数据信息
     * 
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
	@GetMapping("label")
    public String getLabel(String dictType, String dictValue)
    {
        return sysDictDataService.selectDictLabel(dictType, dictValue);
    }
	
	
	/**
	 * 新增保存字典数据
	 */
	@OperLog(title = "字典数据", businessType = BusinessType.INSERT)
    @HasPermissions("system:dict:add")
	@PostMapping("save")
	public ResultDTO<Integer> addSave(@RequestBody SysDictData sysDictData)
	{		sysDictData.setCreateBy(getLoginName());
		return ResultDTO.success(sysDictDataService.insertDictData(sysDictData));
	}

	/**
	 * 修改保存字典数据
	 */
	@OperLog(title = "字典数据", businessType = BusinessType.UPDATE)
    @HasPermissions("system:dict:edit")
	@PostMapping("update")
	public ResultDTO<Integer> editSave(@RequestBody SysDictData sysDictData)
	{
		sysDictData.setUpdateBy(getLoginName());
		return ResultDTO.success(sysDictDataService.updateDictData(sysDictData));
	}
	
	/**
	 * 删除字典数据
	 */
	@OperLog(title = "字典数据", businessType = BusinessType.DELETE)
    @HasPermissions("system:dict:remove")
	@PostMapping("remove")
	public ResultDTO<Integer> remove(String ids)
	{
		String loginName = getLoginName();
		return ResultDTO.success(sysDictDataService.deleteDictDataByIds(  loginName,ids));
	}
	
}
