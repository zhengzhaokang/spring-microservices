package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.system.domain.Donate;
import com.microservices.otmp.system.service.IDonateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>File：DonateController.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2019 2019年12月20日 下午2:32:25</p>
 * <p>
 * @Author lovefamily
 * @version 1.0
 */
@RestController
@RequestMapping("donate")
public class DonateController extends BaseController
{
    @Autowired
    private IDonateService donateService;

    @GetMapping("list")
    public TableDataInfo list(Donate donate)
    {
        startPage();
        List<Donate> list = donateService.selectDistrictsList(donate);
        return getDataTable(list);
    }
}
