package com.microservices.otmp.masterdata.domain.entity.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class ToMsCountryDTO {

    /**
     * 请求的数据类型
     * 1: geo, 2: region, 3: country, 4: currency, 5: salesOrg, 6: salesOffice
     */
    private int type;

    private List<String> geoCodeList;

    private List<String> regionCodeList;

    private List<String> countryCodeList;

}
