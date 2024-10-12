package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.entity.BizSdmsPersonalQuickLinkConfigDO;
import com.microservices.otmp.system.mapper.BizSdmsPersonalQuickLinkConfigMapper;
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
public class BizSdmsPersonalQuickLinkConfigManagerImplTest {

    @Mock
    private BizSdmsPersonalQuickLinkConfigMapper mockBizSdmsPersonalQuickLinkConfigMapper;

    @InjectMocks
    private BizSdmsPersonalQuickLinkConfigManagerImpl bizSdmsPersonalQuickLinkConfigManagerImplUnderTest;

    @Test
    public void testSelectBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId() {
        // Setup
        // Configure BizSdmsPersonalQuickLinkConfigMapper.selectBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(...).
        final BizSdmsPersonalQuickLinkConfigDO bizSdmsPersonalQuickLinkConfigDO = new BizSdmsPersonalQuickLinkConfigDO();
        bizSdmsPersonalQuickLinkConfigDO.setQuickLinkConfigId(0L);
        bizSdmsPersonalQuickLinkConfigDO.setUserCode("userCode");
        bizSdmsPersonalQuickLinkConfigDO.setQuickLinkTitle("quickLinkTitle");
        bizSdmsPersonalQuickLinkConfigDO.setQuickLinkName("quickLinkName");
        bizSdmsPersonalQuickLinkConfigDO.setQuickLinkDsc("quickLinkDsc");
        bizSdmsPersonalQuickLinkConfigDO.setIcon("icon");
        bizSdmsPersonalQuickLinkConfigDO.setStatus("status");
        bizSdmsPersonalQuickLinkConfigDO.setPath("path");
        when(mockBizSdmsPersonalQuickLinkConfigMapper.selectBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(0L)).thenReturn(bizSdmsPersonalQuickLinkConfigDO);

        // Run the test
        final BizSdmsPersonalQuickLinkConfigDO result = bizSdmsPersonalQuickLinkConfigManagerImplUnderTest.selectBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(0L);

        // Verify the results
    }

    @Test
    public void testSelectBizSdmsPersonalQuickLinkConfigList() {
        // Setup
        final BizSdmsPersonalQuickLinkConfigDO bizSdmsPersonalQuickLinkConfig = new BizSdmsPersonalQuickLinkConfigDO();
        bizSdmsPersonalQuickLinkConfig.setQuickLinkConfigId(0L);
        bizSdmsPersonalQuickLinkConfig.setUserCode("userCode");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkTitle("quickLinkTitle");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkName("quickLinkName");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkDsc("quickLinkDsc");
        bizSdmsPersonalQuickLinkConfig.setIcon("icon");
        bizSdmsPersonalQuickLinkConfig.setStatus("status");
        bizSdmsPersonalQuickLinkConfig.setPath("path");

        // Configure BizSdmsPersonalQuickLinkConfigMapper.selectBizSdmsPersonalQuickLinkConfigList(...).
        final BizSdmsPersonalQuickLinkConfigDO bizSdmsPersonalQuickLinkConfigDO = new BizSdmsPersonalQuickLinkConfigDO();
        bizSdmsPersonalQuickLinkConfigDO.setQuickLinkConfigId(0L);
        bizSdmsPersonalQuickLinkConfigDO.setUserCode("userCode");
        bizSdmsPersonalQuickLinkConfigDO.setQuickLinkTitle("quickLinkTitle");
        bizSdmsPersonalQuickLinkConfigDO.setQuickLinkName("quickLinkName");
        bizSdmsPersonalQuickLinkConfigDO.setQuickLinkDsc("quickLinkDsc");
        bizSdmsPersonalQuickLinkConfigDO.setIcon("icon");
        bizSdmsPersonalQuickLinkConfigDO.setStatus("status");
        bizSdmsPersonalQuickLinkConfigDO.setPath("path");
        final List<BizSdmsPersonalQuickLinkConfigDO> bizSdmsPersonalQuickLinkConfigDOS = Arrays.asList(bizSdmsPersonalQuickLinkConfigDO);
        when(mockBizSdmsPersonalQuickLinkConfigMapper.selectBizSdmsPersonalQuickLinkConfigList(any(BizSdmsPersonalQuickLinkConfigDO.class))).thenReturn(bizSdmsPersonalQuickLinkConfigDOS);

        // Run the test
        final List<BizSdmsPersonalQuickLinkConfigDO> result = bizSdmsPersonalQuickLinkConfigManagerImplUnderTest.selectBizSdmsPersonalQuickLinkConfigList(bizSdmsPersonalQuickLinkConfig);

        // Verify the results
    }

    @Test
    public void testSelectBizSdmsPersonalQuickLinkConfigList_BizSdmsPersonalQuickLinkConfigMapperReturnsNoItems() {
        // Setup
        final BizSdmsPersonalQuickLinkConfigDO bizSdmsPersonalQuickLinkConfig = new BizSdmsPersonalQuickLinkConfigDO();
        bizSdmsPersonalQuickLinkConfig.setQuickLinkConfigId(0L);
        bizSdmsPersonalQuickLinkConfig.setUserCode("userCode");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkTitle("quickLinkTitle");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkName("quickLinkName");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkDsc("quickLinkDsc");
        bizSdmsPersonalQuickLinkConfig.setIcon("icon");
        bizSdmsPersonalQuickLinkConfig.setStatus("status");
        bizSdmsPersonalQuickLinkConfig.setPath("path");

        when(mockBizSdmsPersonalQuickLinkConfigMapper.selectBizSdmsPersonalQuickLinkConfigList(any(BizSdmsPersonalQuickLinkConfigDO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizSdmsPersonalQuickLinkConfigDO> result = bizSdmsPersonalQuickLinkConfigManagerImplUnderTest.selectBizSdmsPersonalQuickLinkConfigList(bizSdmsPersonalQuickLinkConfig);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizSdmsPersonalQuickLinkConfig() {
        // Setup
        final BizSdmsPersonalQuickLinkConfigDO bizSdmsPersonalQuickLinkConfig = new BizSdmsPersonalQuickLinkConfigDO();
        bizSdmsPersonalQuickLinkConfig.setQuickLinkConfigId(0L);
        bizSdmsPersonalQuickLinkConfig.setUserCode("userCode");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkTitle("quickLinkTitle");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkName("quickLinkName");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkDsc("quickLinkDsc");
        bizSdmsPersonalQuickLinkConfig.setIcon("icon");
        bizSdmsPersonalQuickLinkConfig.setStatus("status");
        bizSdmsPersonalQuickLinkConfig.setPath("path");

        when(mockBizSdmsPersonalQuickLinkConfigMapper.insertBizSdmsPersonalQuickLinkConfig(any(BizSdmsPersonalQuickLinkConfigDO.class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsPersonalQuickLinkConfigManagerImplUnderTest.insertBizSdmsPersonalQuickLinkConfig(bizSdmsPersonalQuickLinkConfig);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizSdmsPersonalQuickLinkConfig() {
        // Setup
        final BizSdmsPersonalQuickLinkConfigDO bizSdmsPersonalQuickLinkConfig = new BizSdmsPersonalQuickLinkConfigDO();
        bizSdmsPersonalQuickLinkConfig.setQuickLinkConfigId(0L);
        bizSdmsPersonalQuickLinkConfig.setUserCode("userCode");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkTitle("quickLinkTitle");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkName("quickLinkName");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkDsc("quickLinkDsc");
        bizSdmsPersonalQuickLinkConfig.setIcon("icon");
        bizSdmsPersonalQuickLinkConfig.setStatus("status");
        bizSdmsPersonalQuickLinkConfig.setPath("path");

        when(mockBizSdmsPersonalQuickLinkConfigMapper.updateBizSdmsPersonalQuickLinkConfig(any(BizSdmsPersonalQuickLinkConfigDO.class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsPersonalQuickLinkConfigManagerImplUnderTest.updateBizSdmsPersonalQuickLinkConfig(bizSdmsPersonalQuickLinkConfig);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigIds() {
        // Setup
        when(mockBizSdmsPersonalQuickLinkConfigMapper.deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsPersonalQuickLinkConfigManagerImplUnderTest.deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId() {
        // Setup
        when(mockBizSdmsPersonalQuickLinkConfigMapper.deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(0L)).thenReturn(0);

        // Run the test
        final int result = bizSdmsPersonalQuickLinkConfigManagerImplUnderTest.deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(0L);

        // Verify the results
        assertEquals(0, result);
    }
}
