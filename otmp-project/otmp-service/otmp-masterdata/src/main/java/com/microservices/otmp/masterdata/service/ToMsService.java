package com.microservices.otmp.masterdata.service;

import com.microservices.otmp.masterdata.domain.entity.dto.ToMsCountryDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.ToMsParty3rdVendorDTO;
import com.microservices.otmp.masterdata.domain.entity.vo.MsParty3rdVendorVo;
import com.microservices.otmp.masterdata.domain.entity.vo.TpMsCountryVo;

import java.util.List;
import java.util.Map;

public interface ToMsService {
    Map<String, List<TpMsCountryVo>> getCountryList(ToMsCountryDTO toMsCountryDTO);

    List<MsParty3rdVendorVo> getParty3rdVendorList(ToMsParty3rdVendorDTO toMs3rdPartVendorDTO);
}
