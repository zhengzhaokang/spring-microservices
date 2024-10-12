package com.microservices.otmp.analysis.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.analysis.domain.param.LimitDashboardParam;
import com.microservices.otmp.analysis.domain.vo.LimitDashboardTableVo;
import com.microservices.otmp.analysis.domain.vo.LimitDashboardVo;

import java.util.List;

public interface LimitDashBoardService {

    List<LimitDashboardVo> limitDashBoardValues(String bankId, Long entityId, String startTime, String endTime, boolean useMilli);

    PageInfo<LimitDashboardTableVo> limitDashBoardTable(LimitDashboardParam param);

}
