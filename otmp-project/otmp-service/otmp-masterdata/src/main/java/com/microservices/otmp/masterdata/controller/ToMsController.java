package com.microservices.otmp.masterdata.controller;


import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.masterdata.domain.entity.dto.ToMsParty3rdVendorDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.ToMsCountryDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.ToMsParty3rdVendorDTO;
import com.microservices.otmp.masterdata.domain.entity.vo.MsParty3rdVendorVo;
import com.microservices.otmp.masterdata.domain.entity.vo.TpMsCountryVo;
import com.microservices.otmp.masterdata.service.ToMsService;
import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * baseComBiz 提供者
 *
 * @author sdms
 * @date 2022-01-17
 */
@Api("ms")
@RestController
@RequestMapping("/to/ms")
public class ToMsController extends BaseController {
    @Autowired
    private ToMsService toMsService;

    @HasPermissions("masterData:bizBaseGtnType:list")
    @OperLog(title = "提供给ms的api接口(获取Country)", businessType = BusinessType.QUERY)
    @PostMapping("/getCountry")
    public ResultDTO<Map<String, List<TpMsCountryVo>>> getCountryList(@RequestBody ToMsCountryDTO toMsCountryDTO) {

        Map<String, List<TpMsCountryVo>> map = toMsService.getCountryList(toMsCountryDTO);
           return ResultDTO.success(map);
    }

    @HasPermissions("masterData:bizBaseGtnType:list")
    @OperLog(title = "提供给ms的api接口(getParty3rdVendor)", businessType = BusinessType.QUERY)
    @PostMapping("/getParty3rdVendor")
    public ResultDTO<List<MsParty3rdVendorVo>> getCountryList(@RequestBody ToMsParty3rdVendorDTO toMs3rdPartVendorDTO) {
        List<MsParty3rdVendorVo> vos =  toMsService.getParty3rdVendorList(toMs3rdPartVendorDTO);
        return ResultDTO.success(vos);
    }
}
