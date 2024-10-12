package com.microservices.otmp.disclosures.mapper;

import com.microservices.otmp.common.core.dao.BaseMapper;
import com.microservices.otmp.disclosures.dto.DisclosureNumberDto;

public interface DisclosureNumberMapper extends BaseMapper<DisclosureNumberDto> {
    int updateDisclosureNumber(DisclosureNumberDto disclosureNumberDto);

}
