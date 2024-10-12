package com.microservices.otmp.disclosures.service;

import com.microservices.otmp.disclosures.dto.DisclosureNumberDto;
import org.apache.ibatis.annotations.Param;

public interface DisclosureNumberService {

    String getDisclosureNumber(String type);
}
