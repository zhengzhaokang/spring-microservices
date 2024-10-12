package com.microservices.otmp.disclosures.service;

import com.microservices.otmp.disclosures.vo.DisclosureItemAttachmentVO;

import java.util.List;

public interface DisclosureItemAttachmentService {

    int saveDisclosureItemAttachment(List<DisclosureItemAttachmentVO> disclosureItemAttachmentVOList, String business, String module);

    List<DisclosureItemAttachmentVO> queryDisclosureItemAttachment(String business, String module);

    int deleteDisclosureItemAttachment(String business, String module);

}
