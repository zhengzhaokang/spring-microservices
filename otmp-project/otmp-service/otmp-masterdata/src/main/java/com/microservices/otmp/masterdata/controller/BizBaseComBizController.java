package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.feign.RemoteDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.masterdata.domain.BizBaseComBiz;
import com.microservices.otmp.masterdata.service.IBizBaseComBizService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * baseComBiz 提供者
 * 
 * @author sdms
 * @date 2022-01-17
 */
@Api("bizBaseComBiz")
@RestController
@RequestMapping("bizBaseComBiz")
public class BizBaseComBizController extends BaseController
{
	
	@Autowired
	private IBizBaseComBizService bizBaseComBizService;
	@Autowired
    private RemoteDictService remoteDictDataService;
	
	/**
	 * 查询${tableComment}
	 */
	@ApiOperation(value = "查询${tableComment}")
	@GetMapping("get/{id}")
	public BizBaseComBiz get(@PathVariable("id") Integer id)
	{
		return bizBaseComBizService.selectBizBaseComBizById(id);
		
	}

	@GetMapping("getComBiz")
	public ResultDTO<Object> getComBiz() {

		List<SysDictData> list = remoteDictDataService.getDictSelect("com_biz_table");
		if (CollectionUtils.isNotEmpty(list))
		{
			List<String> resultList = list.stream().map(SysDictData::getDictValue).collect(Collectors.toList());
			return ResultDTO.success(resultList);
		}
		return ResultDTO.fail("no record");
	}

	/**
	 * 查询baseComBiz列表
	 */
	@ApiOperation(value = "查询baseComBiz列表")
	@PostMapping("list")
	public TableDataInfo list(BizBaseComBiz bizBaseComBiz)
	{
		startPage();
		List<BizBaseComBiz> bizBaseComBizs = bizBaseComBizService.selectBizBaseComBizList(bizBaseComBiz);
		return getDataTable(bizBaseComBizs);

	}

	@OperLog(title = "MasterData", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@HasPermissions("masterdata:bizBaseComBiz:export")
	public void export(HttpServletResponse response, @RequestBody BizBaseComBiz bizBaseComBiz)
	{
		if(CommonUtils.stringIsEmpty(bizBaseComBiz.getBizTable())){
			throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.PLS_SELECT_BIZ_TABLE, ""), DefaultErrorMessage.PLS_SELECT_BIZ_TABLE.intValue());
		}
		List<BizBaseComBiz> bizs = bizBaseComBizService.selectBizBaseComBizList(bizBaseComBiz);
		NewExcelUtil<BizBaseComBiz> util = new NewExcelUtil<BizBaseComBiz>(BizBaseComBiz.class);
		util.exportExcel(response, bizs, "record");
 	}

	@PostMapping("/importExcel")
	@HasPermissions("masterdata/factor:BizBaseComBiz:import")
	@OperLog(title = "MasterData", businessType = BusinessType.IMPORT)
	public ResultDTO<String> importExcel( MultipartFile file, String bizTable) throws Exception {
		ExcelUtil<BizBaseComBiz> util = new ExcelUtil<BizBaseComBiz>(BizBaseComBiz.class);
		List <BizBaseComBiz> bizs = util.importExcel(file.getInputStream());
		CommonUtils.isNull(bizTable, "Business Type is empty");
		String message = 	bizBaseComBizService.importExcel(bizs,bizTable,getLoginName());

		return ResultDTO.success(message);
	}

	
	/**
	 * 新增保存baseComBiz
	 */
	@ApiOperation(value = "新增保存baseComBiz")
	@PostMapping("save")
	@OperLog(title = "MasterData", businessType = BusinessType.INSERT)
	@RequiresPermissions("masterData:baseComBiz:add")
	public ResultDTO<Integer> addSave(@RequestBody BizBaseComBiz bizBaseComBiz)
	{
		bizBaseComBiz.setCreateBy(getLoginName());
		bizBaseComBiz.setCreateTime(DateUtils.getNowDate());
		bizBaseComBiz.setStatus("Y");
		return toResultDTO(bizBaseComBizService.insertBizBaseComBiz(bizBaseComBiz), true);
	}

	/**
	 * 修改保存baseComBiz
	 */
	@ApiOperation(value = "修改保存baseComBiz")
	@PostMapping("update")
	@OperLog(title = "MasterData", businessType = BusinessType.UPDATE)
	public ResultDTO<Integer> editSave(@RequestBody BizBaseComBiz bizBaseComBiz)
	{
		bizBaseComBiz.setUpdateBy(getLoginName());
		bizBaseComBiz.setUpdateTime(DateUtils.getNowDate());
		return toResultDTO(bizBaseComBizService.updateBizBaseComBiz(bizBaseComBiz), true);
	}
	
	/**
	 * 删除${tableComment}
	 */
	@ApiOperation(value = "删除${tableComment}")
	@PostMapping("remove")
	@OperLog(title = "MasterData", businessType = BusinessType.DELETE)
	public ResultDTO<Integer> remove(@RequestParam("ids") String ids,@RequestParam("bizTable")String bizTable)
	{
		String loginName = getLoginName();
		return toResultDTO(bizBaseComBizService.deleteBizBaseComBizByIds(loginName, bizTable,ids), true);
	}




}
