package com.microservices.otmp.disclosures.mapper;

import com.microservices.otmp.disclosures.dto.DisclosuresItemDto;
import com.microservices.otmp.common.core.dao.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DisclosuresItemMapper extends BaseMapper<DisclosuresItemDto>  {

    Integer deleteDisclosuresItemByIds(@Param("updateBy") String updateBy, @Param("ids") List<String> ids);

    List<DisclosuresItemDto> selectDisclosuresItemList(DisclosuresItemDto disclosuresItemDto);
}
