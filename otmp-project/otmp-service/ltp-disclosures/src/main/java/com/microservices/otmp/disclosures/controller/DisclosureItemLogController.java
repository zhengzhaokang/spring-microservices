package com.microservices.otmp.disclosures.controller;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.disclosures.service.DisclosureItemLogService;
import com.microservices.otmp.disclosures.vo.DisclosureItemLogVO;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("disclosuresItemLog")
@RestController
@RequestMapping("disclosures/item/log")
@Slf4j
public class DisclosureItemLogController extends BaseController {

    @Autowired
    private DisclosureItemLogService disclosureItemLogService;

    @PostMapping("list")
    public TableDataInfo list(@RequestBody DisclosureItemLogVO disclosureItemLogVO) {
        if (StringUtils.isBlank(disclosureItemLogVO.getBusiness())) {
            log.info("### DisclosureItemLogController list disclosureItemLogVO Business is null");
            return getDataDefaultTable();
        }
        startPage();
        PageInfo<DisclosureItemLogVO> disclosureItemLogVOPageInfo = disclosureItemLogService.selectDisclosureItemLogPage(disclosureItemLogVO);
        if  (disclosureItemLogVOPageInfo == null) {
            log.info("### DisclosureItemLogController list disclosureItemLogVOPageInfo is null");
            return getDataDefaultTable();
        }
        return getDataTable(disclosureItemLogVOPageInfo);
    }
}
