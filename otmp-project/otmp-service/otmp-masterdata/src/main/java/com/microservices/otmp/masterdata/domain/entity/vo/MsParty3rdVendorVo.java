package com.microservices.otmp.masterdata.domain.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
public class MsParty3rdVendorVo {

    private Integer id;

    private String geo;

    private String vendorCode;

    private String vendorName;

    private String countryCode;

    private String countryName;

    private String bankType;

    private String bankAccount;

    private String bankName;

    private String deleteFlag;

    private String creator;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    private String modifier;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date modifyTime;


}
