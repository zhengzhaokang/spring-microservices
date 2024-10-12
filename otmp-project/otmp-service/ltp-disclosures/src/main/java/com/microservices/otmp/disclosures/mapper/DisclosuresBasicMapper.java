package com.microservices.otmp.disclosures.mapper;

import com.microservices.otmp.disclosures.dto.DisclosuresBasicDto;
import com.microservices.otmp.disclosures.entity.DisclosuresBasicCount;
import com.microservices.otmp.common.core.dao.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DisclosuresBasicMapper extends BaseMapper<DisclosuresBasicDto> {

    Integer deleteDisclosuresBasicByIds(@Param("updateBy") String updateBy, @Param("ids") List<String> ids);

    List<DisclosuresBasicDto> selectDisclosuresBasicList(DisclosuresBasicDto disclosuresBasicDto);

    Integer selectDisclosuresBasicCountByStatus(@Param("status") String status);

    List<DisclosuresBasicCount> selectDisclosuresBasicCount();
}
