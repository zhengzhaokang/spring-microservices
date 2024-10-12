package com.microservices.otmp.disclosures.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisclosuresItemVO {

    /**
     * 返回给前端的ID
     */
    private String itemId;

    private Long id;

    private String itemNumber;

    private String disclosuresId;

    private String keyInfo;

    private String description;

    private String rootCause;

    private String impact;

    private String inQtrBooks;

    private String itemClassification;

    private String action;

    private String issueStatus;

    private String owner;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date targetDate;

    private String acctEntry;

    private String inPriorQuarter;

    private String status;

    private String delFlag;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private List<DisclosureItemAttachmentVO> disclosureItemAttachmentVOList;

    private List<DisclosureItemCommentVO> disclosureItemCommentVOList;

    private DisclosuresBasicVO disclosuresBasicVO;
}
