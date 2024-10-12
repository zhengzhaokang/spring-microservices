package com.microservices.otmp.disclosures.manager;

import com.microservices.otmp.disclosures.dto.DisclosuresBasicDto;
import com.microservices.otmp.disclosures.entity.DisclosuresBasicCount;

import java.util.List;

public interface DisclosuresBasicManager {

    int insertDisclosuresBasic(DisclosuresBasicDto disclosuresBasicDto);

    int updateDisclosuresBasic(DisclosuresBasicDto disclosuresBasicDto);

    int deleteDisclosuresBasicByIds(List<String> ids, String updateBy);

    DisclosuresBasicDto selectDisclosuresBasicById(Long id);

    List<DisclosuresBasicDto> selectDisclosuresBasicList(DisclosuresBasicDto disclosuresBasicDto);

    int selectDisclosuresBasicCountByStatus(String status);

    List<DisclosuresBasicCount> selectDisclosuresBasicCount();
}
