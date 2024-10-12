package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.masterdata.domain.BizBaseRegionMarket;
import com.microservices.otmp.masterdata.domain.entity.dto.MsDropDownDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.ToMsCountryDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.ToMsParty3rdVendorDTO;
import com.microservices.otmp.masterdata.domain.entity.vo.MsParty3rdVendorVo;
import com.microservices.otmp.masterdata.domain.entity.vo.TpMsCountryVo;
import com.microservices.otmp.masterdata.mapper.BizBaseCountryMapper;
import com.microservices.otmp.masterdata.mapper.BizBaseParty3rdVendorMapper;
import com.microservices.otmp.masterdata.mapper.BizBaseRegionMarketMapper;
import com.microservices.otmp.masterdata.service.impl.ToMsServiceImpl;
import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.feign.RemoteDictService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ToMsServiceImplTest {

    @Mock
    private BizBaseRegionMarketMapper mockBizBaseRegionMarketMapper;
    @Mock
    private BizBaseCountryMapper mockBizBaseCountryMapper;
    @Mock
    private RemoteDictService mockRemoteDictService;
    @Mock
    private BizBaseParty3rdVendorMapper mockBizBaseParty3rdVendorMapper;

    @InjectMocks
    private ToMsServiceImpl toMsServiceImplUnderTest;

    @Test
    public void testGetCountryList() {
        // Setup
        final ToMsCountryDTO toMsCountryDTO = new ToMsCountryDTO();
        toMsCountryDTO.setType(0);
        toMsCountryDTO.setGeoCodeList(Arrays.asList("value"));
        toMsCountryDTO.setRegionCodeList(Arrays.asList("value"));
        toMsCountryDTO.setCountryCodeList(Arrays.asList("value"));

        final Map<String, List<TpMsCountryVo>> expectedResult = new HashMap<>();

        // Configure RemoteDictService.getDictSelect(...).
        final SysDictData sysDictData1 = new SysDictData();
        sysDictData1.setIds("ids");
        sysDictData1.setIdsArray(new Long[]{0L});
        sysDictData1.setDeleteFlag("deleteFlag");
        sysDictData1.setDictCode(0L);
        sysDictData1.setDictSort(0L);
        sysDictData1.setDictLabel("dictLabel");
        sysDictData1.setDictValue("code");
        sysDictData1.setDictType("dictType");
        sysDictData1.setCssClass("cssClass");
        sysDictData1.setListClass("listClass");
        sysDictData1.setIsDefault("isDefault");
        sysDictData1.setStatus("status");
        final List<SysDictData> sysDictData = Arrays.asList(sysDictData1);
//        when(mockRemoteDictService.getDictSelect("GEO")).thenReturn(sysDictData);

        // Configure BizBaseRegionMarketMapper.getMsDropDownList(...).
        final BizBaseRegionMarket bizBaseRegionMarket = new BizBaseRegionMarket();
        bizBaseRegionMarket.setIds("ids");
        bizBaseRegionMarket.setIdsList(Arrays.asList(0L));
        bizBaseRegionMarket.setId(0L);
        bizBaseRegionMarket.setGeoCode("geoCode");
        bizBaseRegionMarket.setRegionMarketCode("code");
        bizBaseRegionMarket.setRegionMarketName("regionMarketName");
        bizBaseRegionMarket.setRegionMarketCurrency("regionMarketCurrency");
        bizBaseRegionMarket.setStatus("status");
        bizBaseRegionMarket.setCreateBy("createBy");
        bizBaseRegionMarket.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseRegionMarket.setCreateDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseRegionMarket.setCreateSecond(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseRegionMarket> bizBaseRegionMarkets = Arrays.asList(bizBaseRegionMarket);
//        when(mockBizBaseRegionMarketMapper.getMsDropDownList(Arrays.asList("value"))).thenReturn(bizBaseRegionMarkets);

        // Configure BizBaseCountryMapper.getMsDropDownList(...).
        final MsDropDownDTO msDropDownDTO = new MsDropDownDTO();
        msDropDownDTO.setRegionMarketCode("regionMarketCode");
        msDropDownDTO.setCountryCode("code");
        msDropDownDTO.setCountryName("code");
        final List<MsDropDownDTO> msDropDownDTOS = Arrays.asList(msDropDownDTO);
//        when(mockBizBaseCountryMapper.getMsDropDownList(Arrays.asList("value"), Arrays.asList("value")))
//                .thenReturn(msDropDownDTOS);

        // Run the test
        final Map<String, List<TpMsCountryVo>> result = toMsServiceImplUnderTest.getCountryList(toMsCountryDTO);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetCountryList_RemoteDictServiceReturnsNoItems() {
        List<String> list = Arrays.asList("value");
        // Setup
        final ToMsCountryDTO toMsCountryDTO = new ToMsCountryDTO();
        toMsCountryDTO.setType(1);
        toMsCountryDTO.setGeoCodeList(list);
        toMsCountryDTO.setRegionCodeList(list);
        toMsCountryDTO.setCountryCodeList(list);

        final Map<String, List<TpMsCountryVo>> expectedResult = new HashMap<>();
//        when(mockRemoteDictService.getDictSelect("GEO")).thenReturn(Collections.emptyList());

        // Run the test
        final Map<String, List<TpMsCountryVo>> result = toMsServiceImplUnderTest.getCountryList(toMsCountryDTO);

        // Verify the results
        assertEquals(1, result.size());
    }

    @Test
    public void testGetCountryList_BizBaseRegionMarketMapperReturnsNoItems() {
        // Setup
        final ToMsCountryDTO toMsCountryDTO = new ToMsCountryDTO();
        toMsCountryDTO.setType(2);
        toMsCountryDTO.setGeoCodeList(Arrays.asList("value"));
        toMsCountryDTO.setRegionCodeList(Arrays.asList("value"));
        toMsCountryDTO.setCountryCodeList(Arrays.asList("value"));

        final Map<String, List<TpMsCountryVo>> expectedResult = new HashMap<>();
//        when(mockBizBaseRegionMarketMapper.getMsDropDownList(Arrays.asList("value")))
//                .thenReturn(Collections.emptyList());

        // Run the test
        final Map<String, List<TpMsCountryVo>> result = toMsServiceImplUnderTest.getCountryList(toMsCountryDTO);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetCountryList_BizBaseCountryMapperReturnsNoItems() {
        // Setup
        final ToMsCountryDTO toMsCountryDTO = new ToMsCountryDTO();
        toMsCountryDTO.setType(3);
        toMsCountryDTO.setGeoCodeList(Arrays.asList("value"));
        toMsCountryDTO.setRegionCodeList(Arrays.asList("value"));
        toMsCountryDTO.setCountryCodeList(Arrays.asList("value"));

        final Map<String, List<TpMsCountryVo>> expectedResult = new HashMap<>();
//        when(mockBizBaseCountryMapper.getMsDropDownList(Arrays.asList("value"), Arrays.asList("value")))
//                .thenReturn(Collections.emptyList());

        // Run the test
        final Map<String, List<TpMsCountryVo>> result = toMsServiceImplUnderTest.getCountryList(toMsCountryDTO);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetParty3rdVendorList() {
        // Setup
        final ToMsParty3rdVendorDTO toMs3rdPartVendorDTO = new ToMsParty3rdVendorDTO();
        toMs3rdPartVendorDTO.setCountryCodeList(Arrays.asList("value"));
        toMs3rdPartVendorDTO.setVendorInfo("vendorInfo");

        final MsParty3rdVendorVo msParty3rdVendorVo = new MsParty3rdVendorVo();
        msParty3rdVendorVo.setId(0);
        msParty3rdVendorVo.setGeo("geo");
        msParty3rdVendorVo.setVendorCode("vendorCode");
        msParty3rdVendorVo.setVendorName("vendorName");
        msParty3rdVendorVo.setCountryCode("countryCode");
        msParty3rdVendorVo.setCountryName("countryName");
        msParty3rdVendorVo.setBankType("bankType");
        msParty3rdVendorVo.setBankAccount("bankAccount");
        msParty3rdVendorVo.setBankName("bankName");
        msParty3rdVendorVo.setDeleteFlag("deleteFlag");
        msParty3rdVendorVo.setCreator("creator");
        msParty3rdVendorVo.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        msParty3rdVendorVo.setModifier("modifier");
        msParty3rdVendorVo.setModifyTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<MsParty3rdVendorVo> expectedResult = Arrays.asList(msParty3rdVendorVo);

        // Configure BizBaseParty3rdVendorMapper.getParty3rdVendorList(...).
        final MsParty3rdVendorVo msParty3rdVendorVo1 = new MsParty3rdVendorVo();
        msParty3rdVendorVo1.setId(0);
        msParty3rdVendorVo1.setGeo("geo");
        msParty3rdVendorVo1.setVendorCode("vendorCode");
        msParty3rdVendorVo1.setVendorName("vendorName");
        msParty3rdVendorVo1.setCountryCode("countryCode");
        msParty3rdVendorVo1.setCountryName("countryName");
        msParty3rdVendorVo1.setBankType("bankType");
        msParty3rdVendorVo1.setBankAccount("bankAccount");
        msParty3rdVendorVo1.setBankName("bankName");
        msParty3rdVendorVo1.setDeleteFlag("deleteFlag");
        msParty3rdVendorVo1.setCreator("creator");
        msParty3rdVendorVo1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        msParty3rdVendorVo1.setModifier("modifier");
        msParty3rdVendorVo1.setModifyTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<MsParty3rdVendorVo> vos = Arrays.asList(msParty3rdVendorVo1);
        when(mockBizBaseParty3rdVendorMapper.getParty3rdVendorList(any(ToMsParty3rdVendorDTO.class))).thenReturn(vos);

        // Run the test
        final List<MsParty3rdVendorVo> result = toMsServiceImplUnderTest.getParty3rdVendorList(toMs3rdPartVendorDTO);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetParty3rdVendorList_BizBaseParty3rdVendorMapperReturnsNoItems() {
        // Setup
        final ToMsParty3rdVendorDTO toMs3rdPartVendorDTO = new ToMsParty3rdVendorDTO();
        toMs3rdPartVendorDTO.setCountryCodeList(Arrays.asList("value"));
        toMs3rdPartVendorDTO.setVendorInfo("vendorInfo");

        when(mockBizBaseParty3rdVendorMapper.getParty3rdVendorList(any(ToMsParty3rdVendorDTO.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<MsParty3rdVendorVo> result = toMsServiceImplUnderTest.getParty3rdVendorList(toMs3rdPartVendorDTO);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }
}
