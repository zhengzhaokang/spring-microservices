package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.entity.SysConfigDO;
import com.microservices.otmp.system.mapper.SysConfigMapper;
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
public class SysConfigManagerImplTest {

    @Mock
    private SysConfigMapper mockSysConfigMapper;

    @InjectMocks
    private SysConfigManagerImpl sysConfigManagerImplUnderTest;

    @Test
    public void testSelectConfig() {
        // Setup
        final SysConfigDO sysConfigDO = new SysConfigDO();
        sysConfigDO.setConfigId(0L);
        sysConfigDO.setConfigName("configName");
        sysConfigDO.setConfigKey("configKey");
        sysConfigDO.setConfigValue("configValue");
        sysConfigDO.setConfigType("configType");

        // Configure SysConfigMapper.selectConfig(...).
        final SysConfigDO sysConfigDO1 = new SysConfigDO();
        sysConfigDO1.setConfigId(0L);
        sysConfigDO1.setConfigName("configName");
        sysConfigDO1.setConfigKey("configKey");
        sysConfigDO1.setConfigValue("configValue");
        sysConfigDO1.setConfigType("configType");
        when(mockSysConfigMapper.selectConfig(any(SysConfigDO.class))).thenReturn(sysConfigDO1);

        // Run the test
        final SysConfigDO result = sysConfigManagerImplUnderTest.selectConfig(sysConfigDO);

        // Verify the results
    }

    @Test
    public void testSelectConfigList() {
        // Setup
        final SysConfigDO sysConfigDO = new SysConfigDO();
        sysConfigDO.setConfigId(0L);
        sysConfigDO.setConfigName("configName");
        sysConfigDO.setConfigKey("configKey");
        sysConfigDO.setConfigValue("configValue");
        sysConfigDO.setConfigType("configType");

        // Configure SysConfigMapper.selectConfigList(...).
        final SysConfigDO sysConfigDO1 = new SysConfigDO();
        sysConfigDO1.setConfigId(0L);
        sysConfigDO1.setConfigName("configName");
        sysConfigDO1.setConfigKey("configKey");
        sysConfigDO1.setConfigValue("configValue");
        sysConfigDO1.setConfigType("configType");
        final List<SysConfigDO> sysConfigDOS = Arrays.asList(sysConfigDO1);
        when(mockSysConfigMapper.selectConfigList(any(SysConfigDO.class))).thenReturn(sysConfigDOS);

        // Run the test
        final List<SysConfigDO> result = sysConfigManagerImplUnderTest.selectConfigList(sysConfigDO);

        // Verify the results
    }

    @Test
    public void testSelectConfigList_SysConfigMapperReturnsNoItems() {
        // Setup
        final SysConfigDO sysConfigDO = new SysConfigDO();
        sysConfigDO.setConfigId(0L);
        sysConfigDO.setConfigName("configName");
        sysConfigDO.setConfigKey("configKey");
        sysConfigDO.setConfigValue("configValue");
        sysConfigDO.setConfigType("configType");

        when(mockSysConfigMapper.selectConfigList(any(SysConfigDO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysConfigDO> result = sysConfigManagerImplUnderTest.selectConfigList(sysConfigDO);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testCheckConfigKeyUnique() {
        // Setup
        // Configure SysConfigMapper.checkConfigKeyUnique(...).
        final SysConfigDO sysConfigDO = new SysConfigDO();
        sysConfigDO.setConfigId(0L);
        sysConfigDO.setConfigName("configName");
        sysConfigDO.setConfigKey("configKey");
        sysConfigDO.setConfigValue("configValue");
        sysConfigDO.setConfigType("configType");
        when(mockSysConfigMapper.checkConfigKeyUnique("configKey")).thenReturn(sysConfigDO);

        // Run the test
        final SysConfigDO result = sysConfigManagerImplUnderTest.checkConfigKeyUnique("configKey");

        // Verify the results
    }

    @Test
    public void testInsertConfig() {
        // Setup
        final SysConfigDO sysConfigDO = new SysConfigDO();
        sysConfigDO.setConfigId(0L);
        sysConfigDO.setConfigName("configName");
        sysConfigDO.setConfigKey("configKey");
        sysConfigDO.setConfigValue("configValue");
        sysConfigDO.setConfigType("configType");

        when(mockSysConfigMapper.insertConfig(any(SysConfigDO.class))).thenReturn(0);

        // Run the test
        final int result = sysConfigManagerImplUnderTest.insertConfig(sysConfigDO);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateConfig() {
        // Setup
        final SysConfigDO sysConfigDO = new SysConfigDO();
        sysConfigDO.setConfigId(0L);
        sysConfigDO.setConfigName("configName");
        sysConfigDO.setConfigKey("configKey");
        sysConfigDO.setConfigValue("configValue");
        sysConfigDO.setConfigType("configType");

        when(mockSysConfigMapper.updateConfig(any(SysConfigDO.class))).thenReturn(0);

        // Run the test
        final int result = sysConfigManagerImplUnderTest.updateConfig(sysConfigDO);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteConfigByIds() {
        // Setup
        when(mockSysConfigMapper.deleteConfigByIds(any(String[].class))).thenReturn(0);

        // Run the test
        final int result = sysConfigManagerImplUnderTest.deleteConfigByIds(new String[]{"value"});

        // Verify the results
        assertEquals(0, result);
    }
}
