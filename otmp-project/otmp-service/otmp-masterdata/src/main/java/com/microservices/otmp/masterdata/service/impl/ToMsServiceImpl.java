package com.microservices.otmp.masterdata.service.impl;

import com.google.common.collect.Maps;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.masterdata.domain.BizBaseRegionMarket;
import com.microservices.otmp.masterdata.domain.entity.dto.MsDropDownDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.ToMsCountryDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.ToMsParty3rdVendorDTO;
import com.microservices.otmp.masterdata.domain.entity.vo.MsParty3rdVendorVo;
import com.microservices.otmp.masterdata.domain.entity.vo.TpMsCountryVo;
import com.microservices.otmp.masterdata.mapper.BizBaseCountryMapper;
import com.microservices.otmp.masterdata.mapper.BizBaseParty3rdVendorMapper;
import com.microservices.otmp.masterdata.mapper.BizBaseRegionMarketMapper;
import com.microservices.otmp.masterdata.service.ToMsService;
import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.feign.RemoteDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ToMsServiceImpl implements ToMsService {

    @Autowired
    private BizBaseRegionMarketMapper bizBaseRegionMarketMapper;
    @Autowired
    private BizBaseCountryMapper bizBaseCountryMapper;
    @Autowired
    private RemoteDictService remoteDictService;
    @Autowired
    private BizBaseParty3rdVendorMapper bizBaseParty3rdVendorMapper;

    @Override
    public Map<String, List<TpMsCountryVo>> getCountryList(ToMsCountryDTO toMsCountryDTO) {
        //参数type: 1: all geo, 2: 查region geo可作为条件, 3: 查country， geo、region可作为条件
        int type = toMsCountryDTO.getType();
        switch (type) {
            case 1:
                List<SysDictData> dictData = remoteDictService.getDictSelect("GEO");
                List<TpMsCountryVo> countryVos = dictData.stream().map(data -> new TpMsCountryVo(data.getDictValue(), data.getDictValue())).collect(Collectors.toList());
                HashMap<String, List<TpMsCountryVo>> hashMap = Maps.newHashMap();
                hashMap.put("geo", countryVos);
                return hashMap;
            case 2:
                List<BizBaseRegionMarket> downList = bizBaseRegionMarketMapper.getMsDropDownList(toMsCountryDTO.getGeoCodeList());
                return downList.stream().filter(t -> StringUtils.isNotEmpty(t.getGeoCode())).collect(Collectors.groupingBy(BizBaseRegionMarket::getGeoCode, Collectors.mapping(p -> new TpMsCountryVo(p.getRegionMarketCode(), p.getRegionMarketCode()), Collectors.toList())));
            case 3:
                List<MsDropDownDTO> bizBaseCountryList = bizBaseCountryMapper.getMsDropDownList(toMsCountryDTO.getGeoCodeList(), toMsCountryDTO.getRegionCodeList());
                List<MsDropDownDTO> result = bizBaseCountryList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(msdropDownDto -> msdropDownDto.getCountryCode() + "/" + msdropDownDto.getCountryName()))), ArrayList::new));
                return result.stream().filter(t -> StringUtils.isNotEmpty(t.getRegionMarketCode())).collect(Collectors.groupingBy(MsDropDownDTO::getRegionMarketCode, Collectors.mapping(p -> new TpMsCountryVo(p.getCountryCode(), p.getCountryName()), Collectors.toList())));
            default:
                break;
        }
        return Maps.newHashMap();
    }

    @Override
    public List<MsParty3rdVendorVo> getParty3rdVendorList(ToMsParty3rdVendorDTO toMs3rdPartVendorDTO) {
        return bizBaseParty3rdVendorMapper.getParty3rdVendorList(toMs3rdPartVendorDTO);
    }
}
