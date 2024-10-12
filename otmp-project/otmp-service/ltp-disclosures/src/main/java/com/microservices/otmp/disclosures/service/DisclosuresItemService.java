package com.microservices.otmp.disclosures.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.disclosures.vo.DisclosuresItemVO;

import java.util.List;

public interface DisclosuresItemService {
    int insertDisclosuresItem(DisclosuresItemVO disclosuresItemVO);

    int updateDisclosuresItem(DisclosuresItemVO disclosuresItemVO);

    int deleteDisclosuresItemByIds(List<String> ids, String updateBy);

    DisclosuresItemVO selectDisclosuresItemById(Long id);

    PageInfo<DisclosuresItemVO> selectDisclosuresItemList(DisclosuresItemVO disclosuresItemVO);
}
