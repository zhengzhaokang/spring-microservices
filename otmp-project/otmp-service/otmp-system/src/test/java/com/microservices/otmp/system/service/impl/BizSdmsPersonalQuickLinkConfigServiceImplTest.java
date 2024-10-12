package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.system.domain.BizSdmsPersonalQuickLinkConfig;
import com.microservices.otmp.system.domain.entity.BizSdmsPersonalQuickLinkConfigDO;
import com.microservices.otmp.system.manager.BizSdmsPersonalQuickLinkConfigManager;
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
public class BizSdmsPersonalQuickLinkConfigServiceImplTest {

    @Mock
    private BizSdmsPersonalQuickLinkConfigManager mockBizSdmsPersonalQuickLinkConfigManager;

    @InjectMocks
    private BizSdmsPersonalQuickLinkConfigServiceImpl bizSdmsPersonalQuickLinkConfigServiceImplUnderTest;

    @Test
    public void testSelectBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId() {
        // Setup
        // Configure BizSdmsPersonalQuickLinkConfigManager.selectBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(...).
        final BizSdmsPersonalQuickLinkConfigDO bizSdmsPersonalQuickLinkConfigDO = new BizSdmsPersonalQuickLinkConfigDO();
        when(mockBizSdmsPersonalQuickLinkConfigManager.selectBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(0L)).thenReturn(bizSdmsPersonalQuickLinkConfigDO);

        // Run the test
        final BizSdmsPersonalQuickLinkConfig result = bizSdmsPersonalQuickLinkConfigServiceImplUnderTest.selectBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(0L);

        // Verify the results
    }

    @Test
    public void testSelectBizSdmsPersonalQuickLinkConfigList() {
        // Setup
        final BizSdmsPersonalQuickLinkConfig bizSdmsPersonalQuickLinkConfig = new BizSdmsPersonalQuickLinkConfig();
        bizSdmsPersonalQuickLinkConfig.setQuickLinkConfigId(0L);
        bizSdmsPersonalQuickLinkConfig.setUserCode("userCode");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkTitle("quickLinkTitle");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkName("quickLinkName");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkDsc("quickLinkDsc");
        bizSdmsPersonalQuickLinkConfig.setIcon("icon");
        bizSdmsPersonalQuickLinkConfig.setStatus("status");
        bizSdmsPersonalQuickLinkConfig.setPath("path");

        // Configure BizSdmsPersonalQuickLinkConfigManager.selectBizSdmsPersonalQuickLinkConfigList(...).
        final BizSdmsPersonalQuickLinkConfigDO bizSdmsPersonalQuickLinkConfigDO = new BizSdmsPersonalQuickLinkConfigDO();
        final List<BizSdmsPersonalQuickLinkConfigDO> bizSdmsPersonalQuickLinkConfigDOS = Arrays.asList(bizSdmsPersonalQuickLinkConfigDO);
        when(mockBizSdmsPersonalQuickLinkConfigManager.selectBizSdmsPersonalQuickLinkConfigList(any(BizSdmsPersonalQuickLinkConfigDO.class))).thenReturn(bizSdmsPersonalQuickLinkConfigDOS);

        // Run the test
        final List<BizSdmsPersonalQuickLinkConfig> result = bizSdmsPersonalQuickLinkConfigServiceImplUnderTest.selectBizSdmsPersonalQuickLinkConfigList(bizSdmsPersonalQuickLinkConfig);

        // Verify the results
    }

    @Test
    public void testSelectBizSdmsPersonalQuickLinkConfigList_BizSdmsPersonalQuickLinkConfigManagerReturnsNoItems() {
        // Setup
        final BizSdmsPersonalQuickLinkConfig bizSdmsPersonalQuickLinkConfig = new BizSdmsPersonalQuickLinkConfig();
        bizSdmsPersonalQuickLinkConfig.setQuickLinkConfigId(0L);
        bizSdmsPersonalQuickLinkConfig.setUserCode("userCode");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkTitle("quickLinkTitle");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkName("quickLinkName");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkDsc("quickLinkDsc");
        bizSdmsPersonalQuickLinkConfig.setIcon("icon");
        bizSdmsPersonalQuickLinkConfig.setStatus("status");
        bizSdmsPersonalQuickLinkConfig.setPath("path");

        when(mockBizSdmsPersonalQuickLinkConfigManager.selectBizSdmsPersonalQuickLinkConfigList(any(BizSdmsPersonalQuickLinkConfigDO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<BizSdmsPersonalQuickLinkConfig> result = bizSdmsPersonalQuickLinkConfigServiceImplUnderTest.selectBizSdmsPersonalQuickLinkConfigList(bizSdmsPersonalQuickLinkConfig);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizSdmsPersonalQuickLinkConfig() {
        // Setup
        final BizSdmsPersonalQuickLinkConfig bizSdmsPersonalQuickLinkConfig = new BizSdmsPersonalQuickLinkConfig();
        bizSdmsPersonalQuickLinkConfig.setQuickLinkConfigId(0L);
        bizSdmsPersonalQuickLinkConfig.setUserCode("userCode");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkTitle("quickLinkTitle");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkName("quickLinkName");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkDsc("quickLinkDsc");
        bizSdmsPersonalQuickLinkConfig.setIcon("icon");
        bizSdmsPersonalQuickLinkConfig.setStatus("status");
        bizSdmsPersonalQuickLinkConfig.setPath("path");

        when(mockBizSdmsPersonalQuickLinkConfigManager.insertBizSdmsPersonalQuickLinkConfig(any(BizSdmsPersonalQuickLinkConfigDO.class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsPersonalQuickLinkConfigServiceImplUnderTest.insertBizSdmsPersonalQuickLinkConfig(bizSdmsPersonalQuickLinkConfig);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizSdmsPersonalQuickLinkConfig() {
        // Setup
        final BizSdmsPersonalQuickLinkConfig bizSdmsPersonalQuickLinkConfig = new BizSdmsPersonalQuickLinkConfig();
        bizSdmsPersonalQuickLinkConfig.setQuickLinkConfigId(0L);
        bizSdmsPersonalQuickLinkConfig.setUserCode("userCode");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkTitle("quickLinkTitle");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkName("quickLinkName");
        bizSdmsPersonalQuickLinkConfig.setQuickLinkDsc("quickLinkDsc");
        bizSdmsPersonalQuickLinkConfig.setIcon("icon");
        bizSdmsPersonalQuickLinkConfig.setStatus("status");
        bizSdmsPersonalQuickLinkConfig.setPath("path");

        when(mockBizSdmsPersonalQuickLinkConfigManager.updateBizSdmsPersonalQuickLinkConfig(any(BizSdmsPersonalQuickLinkConfigDO.class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsPersonalQuickLinkConfigServiceImplUnderTest.updateBizSdmsPersonalQuickLinkConfig(bizSdmsPersonalQuickLinkConfig);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigIds() {
        // Setup
        when(mockBizSdmsPersonalQuickLinkConfigManager.deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizSdmsPersonalQuickLinkConfigServiceImplUnderTest.deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId() {
        // Setup
        when(mockBizSdmsPersonalQuickLinkConfigManager.deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(0L)).thenReturn(0);

        // Run the test
        final int result = bizSdmsPersonalQuickLinkConfigServiceImplUnderTest.deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(0L);

        // Verify the results
        assertEquals(0, result);
    }
}
