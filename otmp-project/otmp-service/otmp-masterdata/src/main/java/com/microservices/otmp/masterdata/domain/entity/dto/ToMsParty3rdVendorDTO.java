package com.microservices.otmp.masterdata.domain.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class ToMsParty3rdVendorDTO {

    private List<String> countryCodeList;

    private String vendorInfo;

}
