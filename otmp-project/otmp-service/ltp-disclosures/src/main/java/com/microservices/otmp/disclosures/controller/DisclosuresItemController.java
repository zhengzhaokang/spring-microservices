package com.microservices.otmp.disclosures.controller;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.disclosures.common.enums.DisclosureBasicEnum;
import com.microservices.otmp.disclosures.service.DisclosuresBasicService;
import com.microservices.otmp.disclosures.service.DisclosuresItemService;
import com.microservices.otmp.disclosures.vo.DisclosuresBasicVO;
import com.microservices.otmp.disclosures.vo.DisclosuresItemVO;
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

import java.util.Arrays;
import java.util.List;

@Api("disclosuresItem")
@RestController
@RequestMapping("disclosures/item")
@Slf4j
public class DisclosuresItemController extends BaseController {

    @Autowired
    private DisclosuresItemService disclosuresItemService;

    @Autowired
    private DisclosuresBasicService disclosuresBasicService;

    @ApiOperation(value = "Query Disclosure Item List")
    @PostMapping("list")
    public TableDataInfo list(@RequestBody DisclosuresItemVO disclosuresItemVO) {
        if (disclosuresItemVO == null || StringUtils.isBlank(disclosuresItemVO.getDisclosuresId())) {
            logger.info("### DisclosuresItemController list param null");
            return getDataDefaultTable();
        }
        startPage();
        PageInfo<DisclosuresItemVO> disclosuresItemVOPageInfo = disclosuresItemService.selectDisclosuresItemList(disclosuresItemVO);
        if (disclosuresItemVOPageInfo == null) {
            logger.info("### DisclosuresItemController list disclosuresItemVOPageInfo is null");
            return getDataDefaultTable();
        }
        return getDataTable(disclosuresItemVOPageInfo);
    }

    @ApiOperation(value = "Query Disclosures Item Detail")
    @GetMapping("info/{itemId}")
    public ResultDTO<Object> info(@PathVariable("itemId") String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return ResultDTO.fail("disclosuresItem param is null");
        }
        DisclosuresItemVO disclosuresItemVO = disclosuresItemService.selectDisclosuresItemById(Long.parseLong(itemId));
        return ResultDTO.success(disclosuresItemVO);
    }

    @ApiOperation(value = "Add Disclosures Item")
    @PostMapping("add")
    @OperLog(title = "Add Disclosures Item", businessType = BusinessType.INSERT)
    public ResultDTO<Object> add(@RequestBody DisclosuresItemVO disclosuresItemVO) {
        if (disclosuresItemVO == null || StringUtils.isBlank(disclosuresItemVO.getDisclosuresId())) {
            return ResultDTO.fail("disclosuresItem param is null");
        }
        disclosuresItemVO.setCreateBy(getLoginName());
        disclosuresItemVO.setUpdateBy(getLoginName());
        int count = disclosuresItemService.insertDisclosuresItem(disclosuresItemVO);

        // 修改basic状态
        if (count > 0) {
            DisclosuresBasicVO param = new DisclosuresBasicVO();
            param.setBasicId(disclosuresItemVO.getDisclosuresId());
            param.setUpdateBy(getLoginName());
            param.setStatus(DisclosureBasicEnum.DATA_COLLECTED.code);
            disclosuresBasicService.updateDisclosuresBasic(param);
        }
        return ResultDTO.success(count);
    }

    @ApiOperation(value = "Update Disclosures Item")
    @PostMapping("update")
    @OperLog(title = "Update Disclosures Item", businessType = BusinessType.UPDATE)
    public ResultDTO<Object> update(@RequestBody DisclosuresItemVO disclosuresItemVO) {
        if (disclosuresItemVO == null || StringUtils.isBlank(disclosuresItemVO.getItemId())) {
            return ResultDTO.fail("disclosuresItem param is null");
        }
        disclosuresItemVO.setUpdateBy(getLoginName());
        int count = disclosuresItemService.updateDisclosuresItem(disclosuresItemVO);
        return ResultDTO.success(count);
    }

    @ApiOperation(value = "Update Disclosures Item Status")
    @PostMapping("status/update")
    @OperLog(title = "Update Disclosures Item Status", businessType = BusinessType.UPDATE)
    public ResultDTO<Object> updateStatus(@RequestBody DisclosuresItemVO disclosuresItemVO) {
        if (disclosuresItemVO == null || StringUtils.isBlank(disclosuresItemVO.getItemId())) {
            return ResultDTO.fail("disclosuresItem param is null");
        }
        DisclosuresItemVO param = new DisclosuresItemVO();
        param.setItemId(disclosuresItemVO.getItemId());
        param.setUpdateBy(getLoginName());
        param.setStatus(disclosuresItemVO.getStatus());
        int count = disclosuresItemService.updateDisclosuresItem(disclosuresItemVO);
        return ResultDTO.success(count);
    }

    @ApiOperation(value = "Del Disclosures Item")
    @PostMapping("remove")
    @OperLog(title = "Del Disclosures Item", businessType = BusinessType.DELETE)
    public ResultDTO<Object> remove(@RequestParam("ids")  String ids)
    {
        if (StringUtils.isBlank(ids)) {
            return ResultDTO.fail("disclosuresItem param is null");
        }
        List<String> idList = Arrays.asList(ids.split(","));
        int count = disclosuresItemService.deleteDisclosuresItemByIds(idList, getLoginName());
        return ResultDTO.success(count);
    }

}
