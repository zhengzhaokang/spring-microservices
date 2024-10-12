package com.microservices.otmp.disclosures.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.disclosures.entity.DisclosuresBasicCount;
import com.microservices.otmp.disclosures.vo.DisclosuresBasicVO;

import java.util.List;

public interface DisclosuresBasicService {
    int insertDisclosuresBasic(DisclosuresBasicVO disclosuresBasicVO);

    int updateDisclosuresBasic(DisclosuresBasicVO disclosuresBasicVO);

    int deleteDisclosuresBasicByIds(List<String> ids, String updateBy);

    DisclosuresBasicVO selectDisclosuresBasicById(Long id);

    PageInfo<DisclosuresBasicVO> selectDisclosuresBasicList(DisclosuresBasicVO disclosuresBasicVO);

    int selectDisclosuresBasicCountByStatus(String status);

    List<DisclosuresBasicCount> selectDisclosuresBasicCount();

    List<DisclosuresBasicVO> selectDisclosuresBasic(DisclosuresBasicVO disclosuresBasicVO);
}
