package com.microservices.otmp.disclosures.manager;

import com.microservices.otmp.disclosures.dto.DisclosuresItemDto;

import java.util.List;

public interface DisclosuresItemManager {

    int insertDisclosuresItem(DisclosuresItemDto disclosuresItemDto);

    int updateDisclosuresItem(DisclosuresItemDto disclosuresItemDto);

    int deleteDisclosuresItemByIds(List<String> ids, String updateBy);

    DisclosuresItemDto selectDisclosuresItemById(Long id);

    List<DisclosuresItemDto> selectDisclosuresItemList(DisclosuresItemDto disclosuresItemDto);
}
