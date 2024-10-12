package com.microservices.otmp.masterdata.domain.entity.dto;

import lombok.Data;

@Data
public class Wso2GetDcDTO {

    private String salesOrg;
    private String salesOffice;
    private String distributionChannel;
    private String division;
}
