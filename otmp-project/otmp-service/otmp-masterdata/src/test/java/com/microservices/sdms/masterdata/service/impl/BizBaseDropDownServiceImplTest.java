package com.microservices.sdms.masterdata.service.impl;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownList;
import com.microservices.otmp.masterdata.mapper.BizBaseCurrencyMapper;
import com.microservices.otmp.masterdata.mapper.BizBaseDropDownMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseDropDownServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BizBaseDropDownServiceImplTest {

    @Mock
    private BizBaseDropDownMapper mockBizBaseRegionMarketMapper;
    @Mock
    private BizBaseCurrencyMapper mockBizBaseCurrencyMapper;

    @InjectMocks
    private BizBaseDropDownServiceImpl bizBaseDropDownServiceImplUnderTest;

    @Test
    public void testGetBptype() {
        // Setup
        final BizBaseDropDownCondition bd = new BizBaseDropDownCondition();
        bd.setGeoCode("geo");
        bd.setBusinessGroup("businessGroup");
        bd.setRegionMarketCode("regionMarket");
        bd.setSalesOrgCode("salesOrg");
        bd.setSalesOrgs(new String[]{"value"});

        // Configure BizBaseDropDownMapper.getBptype(...).
        final BizBaseDropDownList bizBaseDropDownList = new BizBaseDropDownList();
        bizBaseDropDownList.setLabel("label");
        bizBaseDropDownList.setValue("value");
        final List<BizBaseDropDownList> bizBaseDropDownLists = Arrays.asList(bizBaseDropDownList);
        when(mockBizBaseRegionMarketMapper.getBptype(any(BizBaseDropDownCondition.class))).thenReturn(bizBaseDropDownLists);

        // Run the test
        final List<BizBaseDropDownList> result = bizBaseDropDownServiceImplUnderTest.getBptype(bd);
        assertEquals(bizBaseDropDownLists, result);
        // Verify the results
    }

    @Test
    public void testGetBptype_BizBaseDropDownMapperReturnsNoItems() {
        // Setup
        final BizBaseDropDownCondition bd = new BizBaseDropDownCondition();
        bd.setGeoCode("geo");
        bd.setBusinessGroup("businessGroup");
        bd.setRegionMarketCode("regionMarket");
        bd.setSalesOrgCode("salesOrg");
        bd.setSalesOrgs(new String[]{"value"});

        when(mockBizBaseRegionMarketMapper.getBptype(any(BizBaseDropDownCondition.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseDropDownList> result = bizBaseDropDownServiceImplUnderTest.getBptype(bd);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

}
