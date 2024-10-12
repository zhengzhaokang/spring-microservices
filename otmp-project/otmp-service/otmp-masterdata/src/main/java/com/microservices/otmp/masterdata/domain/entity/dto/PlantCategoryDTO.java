package com.microservices.otmp.masterdata.domain.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlantCategoryDTO {

    private List<String>  plants;
    private String regionCountry;
    private List<String> partNumbers;
    private String salesOrg;
    private String language;

}
