package com.microservices.otmp.analysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.microservices.otmp.analysis.domain.entity.LimitDashboardDo;
import com.microservices.otmp.analysis.domain.entity.LimitStatisticDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface LimitStatisticMapper extends BaseMapper<LimitStatisticDo> {

    Integer selectAmountCount(@Param("type") Integer type
            , @Param("bankId") String bankId
            , @Param("entityId") Long entityId
            , @Param("startTime") String startTime
            , @Param("endTime") String endTime);

    List<LimitStatisticDo> selectAmount(@Param("type") Integer type
            , @Param("bankId") String bankId
            , @Param("entityId") Long entityId
            , @Param("startTime") String startTime
            , @Param("endTime") String endTime
            , @Param("pageSize") Integer pageSize
            , @Param("startNum") Integer startNum);

//    Integer selectCount(@Param("bankId") String bankId
//            , @Param("entityId") Long entityId
//            , @Param("startTime") Date startTime
//            , @Param("endTime") Date endTime);

    List<LimitDashboardDo> selectByTimeRange(@Param("type") Integer type
            , @Param("bankId") String bankId
            , @Param("entityId") Long entityId
            , @Param("startTime") Date startTime
            , @Param("endTime") Date endTime
            , @Param("pageSize") Integer pageSize
            , @Param("startNum") Integer startNum);

}
