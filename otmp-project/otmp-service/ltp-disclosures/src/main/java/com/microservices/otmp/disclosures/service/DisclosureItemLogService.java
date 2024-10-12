package com.microservices.otmp.disclosures.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.disclosures.vo.DisclosureItemLogVO;

import java.util.List;

public interface DisclosureItemLogService {
    int insertDisclosureItemLog(DisclosureItemLogVO disclosureItemLogVO);

    List<DisclosureItemLogVO> selectDisclosureItemLog(DisclosureItemLogVO disclosureItemLogVO);

    PageInfo<DisclosureItemLogVO> selectDisclosureItemLogPage(DisclosureItemLogVO disclosureItemLogVO);
}
