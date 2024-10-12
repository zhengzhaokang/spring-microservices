package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 通过excel获取批量查询条件
 *
 * @author xiaozy8
 * @date 2022-12-14
 */
@Api("BatchQuery")
@RestController
@RequestMapping("/batchQuery")
public class BatchQueryController extends BaseController {

    @Log(title = "GetConditionByFile", businessType = BusinessType.IMPORT)
    @PostMapping("/getConditionByFile")
    public ResultDTO<String> getConditionsByFile(@RequestPart("file") MultipartFile file) throws Exception {
        NewExcelUtil<String> util = new NewExcelUtil<>();
        List<String> conditions = util.getFirstColumn(file.getInputStream());
        return ResultDTO.success(String.join(",", conditions));
    }

}
