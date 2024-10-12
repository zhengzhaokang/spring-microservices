package com.microservices.otmp.financing.controller;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.constant.StateConstants;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.enums.BusinessStatus;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.AddressUtils;
import com.microservices.otmp.common.utils.IpUtils;
import com.microservices.otmp.common.utils.ServletUtils;
import com.microservices.otmp.financing.domain.param.IdParam;
import com.microservices.otmp.financing.domain.param.entity.AddEntityParam;
import com.microservices.otmp.financing.domain.param.entity.EditEntityParam;
import com.microservices.otmp.financing.domain.param.entity.EntityListParam;
import com.microservices.otmp.financing.domain.vo.entity.EntityVo;
import com.microservices.otmp.financing.service.EntityService;
import com.microservices.otmp.system.domain.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/anchor/entity")
@RestController
public class AnchorEntityController extends BaseController {

    @Autowired
    private EntityService entityService;

    @OperLog(title = "添加实体对象", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public ResultDTO<Boolean> add(@Validated @RequestBody AddEntityParam param) {
        String loginName = getLoginName();
        param.setCreateBy(loginName);
        Boolean result = entityService.add(param);
        return ResultDTO.success(result);
    }

    //    @OperLog(title = "查询实体列表", businessType = BusinessType.QUERY)
    @PostMapping("/list")
    public ResultDTO<PageInfo<EntityVo>> list(@Validated @RequestBody EntityListParam param) {
        param.setUserId(getCurrentUserId());
        PageInfo<EntityVo> list = entityService.list(param);
        return ResultDTO.success(list);
    }

    //    @OperLog(title = "查询实体信息", businessType = BusinessType.QUERY)
    @GetMapping("/info")
    public ResultDTO<EntityVo> info(@RequestParam("id") Long id) {
        EntityVo result = entityService.detail(id);
        return ResultDTO.success(result);
    }

    //    @OperLog(title = "编辑实体信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public ResultDTO<Boolean> edit(@Validated @RequestBody EditEntityParam param) {
        String loginName = getLoginName();
        param.setUpdateBy(loginName);
        SysOperLog operatorLog = createLog();
        operatorLog.setOperName(loginName);
        Boolean result = entityService.update(param, operatorLog);
        return ResultDTO.success(result);
    }

    private SysOperLog createLog() {
        SysOperLog operatorLog = new SysOperLog();
        operatorLog.setStatus(BusinessStatus.SUCCESS.ordinal());
        HttpServletRequest request = ServletUtils.getRequest();
        String ip = IpUtils.getIpAddr(request);
        operatorLog.setOperIp(ip);
        operatorLog.setOperUrl(request != null ? request.getRequestURI() : null);
        operatorLog.setOperLocation(AddressUtils.getRealAddressByIP(ip));
        Class<AnchorEntityController> clazz = AnchorEntityController.class;
        String className = clazz.getName();
        operatorLog.setMethod(className + ".edit()");
        operatorLog.setRequestMethod(request != null ? request.getMethod() : RequestMethod.POST.name());
        operatorLog.setTitle("编辑实体信息");
        operatorLog.setBusinessType(BusinessType.UPDATE.ordinal());
        return operatorLog;
    }

    @OperLog(title = "禁用实体", businessType = BusinessType.UPDATE)
    @PostMapping("/suspend")
    public ResultDTO<Boolean> suspend(@Validated @RequestBody IdParam param) {
        String loginName = getLoginName();
        param.setUpdateBy(loginName);
        Boolean result = entityService.updateStatus(param.getId(), StateConstants.FLAG_DELETED_STR);
        return ResultDTO.success(result);
    }

    @OperLog(title = "激活实体", businessType = BusinessType.UPDATE)
    @PostMapping("/active")
    public ResultDTO<Boolean> active(@Validated @RequestBody IdParam param) {
        String loginName = getLoginName();
        param.setUpdateBy(loginName);
        Boolean result = entityService.updateStatus(param.getId(), StateConstants.FLAG_NORMAL_STR);
        return ResultDTO.success(result);
    }
}
