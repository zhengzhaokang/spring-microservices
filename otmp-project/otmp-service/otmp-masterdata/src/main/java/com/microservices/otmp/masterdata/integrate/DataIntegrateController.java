package com.microservices.otmp.masterdata.integrate;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.utils.wso2.Wso2ModuleType;
import com.microservices.otmp.common.utils.wso2.Wso2Util;
import com.microservices.otmp.masterdata.common.Response;
import com.microservices.otmp.masterdata.domain.ToLpcApcDTO;
import com.microservices.otmp.masterdata.feign.RemoteApcWso2Service;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.system.domain.dto.DataIntegrateApcDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data/integrate")
public class DataIntegrateController extends BaseController {
    @Autowired
    private RemoteApcWso2Service remoteApcWso2Service;
    @Autowired
    private Wso2Util wso2Util;


    @RequiresPermissions("masterdata:integrate :list")
    @PostMapping("/getTosList")
    public TableDataInfo addTos(@RequestBody  DataIntegrateApcDTO dataIntegrateApcDTO) {
        String token = wso2Util.getToken(Wso2ModuleType.lpc.name());
        Response<ToLpcApcDTO> apcCodeByCallLpc = remoteApcWso2Service.getApcCodeByCallLpc(dataIntegrateApcDTO, token);
        TableDataInfo tableDataInfo = new TableDataInfo();
        if(apcCodeByCallLpc.getCode().equals("200")){
            ToLpcApcDTO data = apcCodeByCallLpc.getData();
            Integer totalElements = data.getTotalElements();
            List<ToLpcApcDTO.ToLpcApcSubsetDTO> content = data.getContent();
            tableDataInfo.setTotal(totalElements);
            tableDataInfo.setRows(content);
        }
        return tableDataInfo;
    }
}
