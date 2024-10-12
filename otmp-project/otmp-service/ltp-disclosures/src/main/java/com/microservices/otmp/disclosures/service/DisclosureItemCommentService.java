package com.microservices.otmp.disclosures.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.disclosures.vo.DisclosureItemCommentVO;

import java.util.List;

public interface DisclosureItemCommentService {

    int saveDisclosureItemComment(DisclosureItemCommentVO disclosureItemCommentVO);

    int deleteDisclosureItemComment(String id, String updateBy);

    PageInfo<DisclosureItemCommentVO> getDisclosureItemCommentList(DisclosureItemCommentVO disclosureItemCommentVO);

    List<DisclosureItemCommentVO> getDisclosureItemCommentVOList(DisclosureItemCommentVO disclosureItemCommentVO);
}
