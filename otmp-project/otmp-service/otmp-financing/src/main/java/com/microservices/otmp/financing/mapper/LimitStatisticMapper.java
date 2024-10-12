package com.microservices.otmp.financing.mapper;

import com.microservices.otmp.common.core.dao.BaseMapper;
import com.microservices.otmp.financing.domain.entity.LimitStatisticDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LimitStatisticMapper extends BaseMapper<LimitStatisticDo> {

    void deleteRecords(@Param("statisticDate") String deleteRecords);

    void insertWithId(@Param("records") List<LimitStatisticDo> records);

    List<LimitStatisticDo> selectLastDate(@Param("date") String date);

    String selectLastStatisticDate();
}
