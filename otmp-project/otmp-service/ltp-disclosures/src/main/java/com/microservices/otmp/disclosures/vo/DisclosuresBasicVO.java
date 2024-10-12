package com.microservices.otmp.disclosures.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisclosuresBasicVO {

    /**
     * 返回给前端的ID
     */
    private String basicId;

    private Long id;

    private String basicNumber;

    private String businessGroup;

    private String geoCode;

    private String fiscalYear;

    private String quarter;

    private String qdpFocal;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dueDate;

    private String status;

    private String delFlag;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;
}
