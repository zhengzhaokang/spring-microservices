package com.microservices.otmp.disclosures.controller;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.disclosures.common.DisclosureConstant;
import com.microservices.otmp.disclosures.service.DisclosureItemCommentService;
import com.microservices.otmp.disclosures.vo.DisclosureItemCommentVO;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("disclosuresItemComment")
@RestController
@RequestMapping("disclosures/item/comment")
@Slf4j
public class DisclosureItemCommentController extends BaseController {

    @Autowired
    private DisclosureItemCommentService disclosureItemCommentService;

    @ApiOperation(value = "Disclosure Item Comment List")
    @PostMapping("list")
    public TableDataInfo list(@RequestBody DisclosureItemCommentVO disclosureItemCommentVO) {
        startPage();
        PageInfo<DisclosureItemCommentVO> disclosureItemCommentList = disclosureItemCommentService.getDisclosureItemCommentList(disclosureItemCommentVO);
        if (disclosureItemCommentList == null) {
            log.info("### DisclosureItemCommentController list disclosureItemCommentList is null");
            return getDataDefaultTable();
        }
        return getDataTable(disclosureItemCommentList);
    }

    @ApiOperation(value = "Disclosure Item Comment Add")
    @PostMapping("add")
    @OperLog(title = "Add Disclosures Item Comment", businessType = BusinessType.INSERT)
    public ResultDTO<Object> add(@RequestBody DisclosureItemCommentVO disclosureItemCommentVO) {
        if (disclosureItemCommentVO == null || StringUtils.isAnyBlank(disclosureItemCommentVO.getComment(), disclosureItemCommentVO.getBusiness())) {
            return ResultDTO.fail("Disclosures Item Comment param is null");
        }
        disclosureItemCommentVO.setOperator(getLoginName());
        disclosureItemCommentVO.setCreateBy(getLoginName());
        disclosureItemCommentVO.setUpdateBy(getLoginName());
        int count = disclosureItemCommentService.saveDisclosureItemComment(disclosureItemCommentVO);
        return ResultDTO.success(count);
    }

    @ApiOperation(value = "Disclosure Item Comment Delete")
    @PostMapping("remove")
    @OperLog(title = "Disclosures Item Comment Delete", businessType = BusinessType.DELETE)
    public ResultDTO<Object> remove(@RequestParam("id")  String id) {
        if (StringUtils.isBlank(id)) {
            return ResultDTO.fail("Disclosures Item Comment param is null");
        }
        int count = disclosureItemCommentService.deleteDisclosureItemComment(id, getLoginName());
        if (count == DisclosureConstant.CAN_NOT_CHANGE) {
            return ResultDTO.fail("Disclosures Item Comment can not change");
        }
        return ResultDTO.success(count);
    }

}
