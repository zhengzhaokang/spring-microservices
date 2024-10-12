package com.microservices.otmp.disclosures.controller;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.disclosures.entity.DisclosuresBasicCount;
import com.microservices.otmp.disclosures.service.DisclosuresBasicService;
import com.microservices.otmp.disclosures.vo.DisclosuresBasicVO;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.disclosures.entity.DisclosuresBasicCount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Api("disclosuresBasic")
@RestController
@RequestMapping("disclosures/basic")
@Slf4j
public class DisclosuresBasicController extends BaseController {

    @Autowired
    private DisclosuresBasicService disclosuresBasicService;

    @ApiOperation(value = "Query Disclosures Basic List")
    @PostMapping("list")
    public TableDataInfo list(@RequestBody DisclosuresBasicVO disclosuresBasicVO) {
        startPage();
        PageInfo<DisclosuresBasicVO> disclosuresBasicVOPageInfo = disclosuresBasicService.selectDisclosuresBasicList(disclosuresBasicVO);
        if (disclosuresBasicVOPageInfo == null) {
            logger.info("### DisclosuresBasicController list disclosuresBasicVOPageInfo is null");
            return getDataDefaultTable();
        }
        return getDataTable(disclosuresBasicVOPageInfo);
    }

    @ApiOperation(value = "Query Disclosures Basic Detail")
    @GetMapping("info/{basicId}")
    public ResultDTO<Object> info(@PathVariable("basicId") String basicId) {
        if (StringUtils.isBlank(basicId)) {
            return ResultDTO.fail("param is null");
        }
        DisclosuresBasicVO disclosuresBasicVO = disclosuresBasicService.selectDisclosuresBasicById(Long.parseLong(basicId));
        return ResultDTO.success(disclosuresBasicVO);
    }

    @ApiOperation(value = "Query Disclosures Basic Count")
    @GetMapping("count")
    public ResultDTO<Object> count() {
        List<DisclosuresBasicCount> disclosuresBasicCount = disclosuresBasicService.selectDisclosuresBasicCount();
        return ResultDTO.success(disclosuresBasicCount);
    }

    @ApiOperation(value = "Add Disclosures Basic")
    @PostMapping("add")
    @OperLog(title = "Add Disclosures Basic", businessType = BusinessType.INSERT)
    public ResultDTO<Object> add(@RequestBody DisclosuresBasicVO disclosuresBasicVO) {
        if (disclosuresBasicVO == null) {
            return ResultDTO.fail("disclosuresBasic param is null");
        }
        // 校验是否重复
        List<DisclosuresBasicVO> disclosuresBasicVOList = getDisclosuresBasicVOS(disclosuresBasicVO);
        if (CollectionUtils.isNotEmpty(disclosuresBasicVOList)) {
            return ResultDTO.fail("Same disclosure already existed, please check it");
        }
        disclosuresBasicVO.setCreateBy(getLoginName());
        disclosuresBasicVO.setUpdateBy(getLoginName());
        int count = disclosuresBasicService.insertDisclosuresBasic(disclosuresBasicVO);
        return ResultDTO.success(count);
    }

    private List<DisclosuresBasicVO> getDisclosuresBasicVOS(DisclosuresBasicVO disclosuresBasicVO) {
        DisclosuresBasicVO param = new DisclosuresBasicVO();
        param.setBusinessGroup(disclosuresBasicVO.getBusinessGroup());
        param.setGeoCode(disclosuresBasicVO.getGeoCode());
        param.setFiscalYear(disclosuresBasicVO.getFiscalYear());
        param.setQuarter(disclosuresBasicVO.getQuarter());
        param.setDueDate(disclosuresBasicVO.getDueDate());
        return disclosuresBasicService.selectDisclosuresBasic(param);
    }

    @ApiOperation(value = "Update Disclosures Basic")
    @PostMapping("update")
    @OperLog(title = "Update Disclosures Basic", businessType = BusinessType.UPDATE)
    public ResultDTO<Object> update(@RequestBody DisclosuresBasicVO disclosuresBasicVO) {
        if (disclosuresBasicVO == null || StringUtils.isBlank(disclosuresBasicVO.getBasicId())) {
            return ResultDTO.fail("disclosuresBasic param is null");
        }
        DisclosuresBasicVO oldDisclosuresBasicVO  = disclosuresBasicService.selectDisclosuresBasicById(Long.parseLong(disclosuresBasicVO.getBasicId()));
        if (StringUtils.equals(disclosuresBasicVO.getBusinessGroup(), oldDisclosuresBasicVO.getBusinessGroup()) &&
            StringUtils.equals(disclosuresBasicVO.getGeoCode(), oldDisclosuresBasicVO.getGeoCode()) &&
            StringUtils.equals(disclosuresBasicVO.getFiscalYear(), oldDisclosuresBasicVO.getFiscalYear()) &&
            StringUtils.equals(disclosuresBasicVO.getQuarter(), oldDisclosuresBasicVO.getQuarter()) &&
            StringUtils.equals(disclosuresBasicVO.getQdpFocal(), oldDisclosuresBasicVO.getQdpFocal()) &&
            disclosuresBasicVO.getDueDate().equals(oldDisclosuresBasicVO.getDueDate()) ) { //待验证
            return ResultDTO.fail("Not modified, please check it");
        }
        // 校验是否重复
        List<DisclosuresBasicVO> disclosuresBasicVOList = getDisclosuresBasicVOS(disclosuresBasicVO);
        if (CollectionUtils.isNotEmpty(disclosuresBasicVOList)) {
            if (disclosuresBasicVOList.size() == 1 && StringUtils.equals(disclosuresBasicVOList.get(0).getBasicId(),disclosuresBasicVO.getBasicId())) {
                logger.info("### DisclosuresBasicController disclosuresBasicVOList size is 1 and basicId is equal");
            } else {
                return ResultDTO.fail("Same disclosure already existed, please check it");
            }
        }
        disclosuresBasicVO.setUpdateBy(getLoginName());
        int count = disclosuresBasicService.updateDisclosuresBasic(disclosuresBasicVO);
        return ResultDTO.success(count);
    }

    @ApiOperation(value = "Update Disclosures Basic Status")
    @PostMapping("status/update")
    @OperLog(title = "Update Disclosures Basic Status", businessType = BusinessType.UPDATE)
    public ResultDTO<Object> updateStatus(@RequestBody DisclosuresBasicVO disclosuresBasicVO) {
        if (disclosuresBasicVO == null || StringUtils.isBlank(disclosuresBasicVO.getBasicId())) {
            return ResultDTO.fail("disclosuresBasic param is null");
        }
        DisclosuresBasicVO param = new DisclosuresBasicVO();
        param.setBasicId(disclosuresBasicVO.getBasicId());
        param.setUpdateBy(getLoginName());
        param.setStatus(disclosuresBasicVO.getStatus());
        int count = disclosuresBasicService.updateDisclosuresBasic(param);
        return ResultDTO.success(count);
    }


    @ApiOperation(value = "Del Disclosures Basic")
    @PostMapping("remove")
    @OperLog(title = "Del Disclosures Basic", businessType = BusinessType.DELETE)
    public ResultDTO<Object> remove(@RequestParam("ids")  String ids)
    {
        if (StringUtils.isBlank(ids)) {
            return ResultDTO.fail("disclosuresBasic param is null");
        }
        List<String> idList = Arrays.asList(ids.split(","));
        int count = disclosuresBasicService.deleteDisclosuresBasicByIds(idList, getLoginName());
        return ResultDTO.success(count);
    }
}
