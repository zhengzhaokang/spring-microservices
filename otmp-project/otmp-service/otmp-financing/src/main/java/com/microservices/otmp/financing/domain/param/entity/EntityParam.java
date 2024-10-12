package com.microservices.otmp.financing.domain.param.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class EntityParam {

    @NotNull
    private String entityName;
    @NotNull
    private List<String> currency;
    @NotNull
    private String region;
    private String address;
    private String type;
    private List<String> kycDocumentIds;
    private List<String> contractIds;
    private List<String> erpEntities;
    private List<String> bankIds;

}
