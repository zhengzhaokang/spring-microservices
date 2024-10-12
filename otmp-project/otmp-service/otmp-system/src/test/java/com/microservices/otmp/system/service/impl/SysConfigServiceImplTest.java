package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.system.domain.SysConfig;
import com.microservices.otmp.system.domain.entity.SysConfigDO;
import com.microservices.otmp.system.manager.SysConfigManager;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SysConfigServiceImplTest {

    @Mock
    private SysConfigManager mockSysConfigManager;
    @Mock
    private RedisUtils mockRedis;

    @InjectMocks
    private SysConfigServiceImpl sysConfigServiceImplUnderTest;

    @Test
    public void testSelectConfigById() {
        // Setup
        // Configure SysConfigManager.selectConfig(...).
        final SysConfigDO sysConfigDO = new SysConfigDO();
        sysConfigDO.setConfigId(0L);
        sysConfigDO.setConfigName("configName");
        sysConfigDO.setConfigKey("configKey");
        sysConfigDO.setConfigValue("configValue");
        sysConfigDO.setConfigType("configType");
        when(mockSysConfigManager.selectConfig(any(SysConfigDO.class))).thenReturn(sysConfigDO);

        // Run the test
        final SysConfig result = sysConfigServiceImplUnderTest.selectConfigById(0L);

        // Verify the results
    }

    @Test
    public void testSelectConfigByKey() {
        // Setup
        // Configure SysConfigManager.selectConfig(...).
        final SysConfigDO sysConfigDO = new SysConfigDO();
        sysConfigDO.setConfigId(0L);
        sysConfigDO.setConfigName("configName");
        sysConfigDO.setConfigKey("configKey");
        sysConfigDO.setConfigValue("configValue");
        sysConfigDO.setConfigType("configType");
        when(mockSysConfigManager.selectConfig(any(SysConfigDO.class))).thenReturn(sysConfigDO);

        // Run the test
        final String result = sysConfigServiceImplUnderTest.selectConfigByKey("configKey");

        // Verify the results
        assertEquals("configValue", result);
    }

    @Test
    public void testSelectConfigList() {
        // Setup
        final SysConfig sysConfig = new SysConfig();
        sysConfig.setConfigId(0L);
        sysConfig.setConfigName("configName");
        sysConfig.setConfigKey("configKey");
        sysConfig.setConfigValue("configValue");
        sysConfig.setConfigType("configType");

        // Configure SysConfigManager.selectConfigList(...).
        final SysConfigDO sysConfigDO = new SysConfigDO();
        sysConfigDO.setConfigId(0L);
        sysConfigDO.setConfigName("configName");
        sysConfigDO.setConfigKey("configKey");
        sysConfigDO.setConfigValue("configValue");
        sysConfigDO.setConfigType("configType");
        final List<SysConfigDO> sysConfigDOS = Arrays.asList(sysConfigDO);
        when(mockSysConfigManager.selectConfigList(any(SysConfigDO.class))).thenReturn(sysConfigDOS);

        // Run the test
        final List<SysConfig> result = sysConfigServiceImplUnderTest.selectConfigList(sysConfig);

        // Verify the results
    }

    @Test
    public void testSelectConfigList_SysConfigManagerReturnsNoItems() {
        // Setup
        final SysConfig sysConfig = new SysConfig();
        sysConfig.setConfigId(0L);
        sysConfig.setConfigName("configName");
        sysConfig.setConfigKey("configKey");
        sysConfig.setConfigValue("configValue");
        sysConfig.setConfigType("configType");

        when(mockSysConfigManager.selectConfigList(any(SysConfigDO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysConfig> result = sysConfigServiceImplUnderTest.selectConfigList(sysConfig);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertConfig() {
        // Setup
        final SysConfig config = new SysConfig();
        config.setConfigId(0L);
        config.setConfigName("configName");
        config.setConfigKey("configKey");
        config.setConfigValue("configValue");
        config.setConfigType("configType");

        when(mockSysConfigManager.insertConfig(any(SysConfigDO.class))).thenReturn(0);

        // Configure SysConfigManager.selectConfigList(...).
        final SysConfigDO sysConfigDO = new SysConfigDO();
        sysConfigDO.setConfigId(0L);
        sysConfigDO.setConfigName("configName");
        sysConfigDO.setConfigKey("configKey");
        sysConfigDO.setConfigValue("configValue");
        sysConfigDO.setConfigType("configType");
        final List<SysConfigDO> sysConfigDOS = Arrays.asList(sysConfigDO);
        when(mockSysConfigManager.selectConfigList(any(SysConfigDO.class))).thenReturn(sysConfigDOS);

        // Run the test
        final int result = sysConfigServiceImplUnderTest.insertConfig(config);

        // Verify the results
        assertEquals(0, result);

    }

    @Test
    public void testInsertConfig_SysConfigManagerSelectConfigListReturnsNoItems() {
        // Setup
        final SysConfig config = new SysConfig();
        config.setConfigId(0L);
        config.setConfigName("configName");
        config.setConfigKey("configKey");
        config.setConfigValue("configValue");
        config.setConfigType("configType");

        when(mockSysConfigManager.insertConfig(any(SysConfigDO.class))).thenReturn(0);
        when(mockSysConfigManager.selectConfigList(any(SysConfigDO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = sysConfigServiceImplUnderTest.insertConfig(config);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateConfig() {
        // Setup
        final SysConfig config = new SysConfig();
        config.setConfigId(0L);
        config.setConfigName("configName");
        config.setConfigKey("configKey");
        config.setConfigValue("configValue");
        config.setConfigType("configType");

        when(mockSysConfigManager.updateConfig(any(SysConfigDO.class))).thenReturn(0);

        // Configure SysConfigManager.selectConfigList(...).
        final SysConfigDO sysConfigDO = new SysConfigDO();
        sysConfigDO.setConfigId(0L);
        sysConfigDO.setConfigName("configName");
        sysConfigDO.setConfigKey("configKey");
        sysConfigDO.setConfigValue("configValue");
        sysConfigDO.setConfigType("configType");
        final List<SysConfigDO> sysConfigDOS = Arrays.asList(sysConfigDO);
        when(mockSysConfigManager.selectConfigList(any(SysConfigDO.class))).thenReturn(sysConfigDOS);

        // Run the test
        final int result = sysConfigServiceImplUnderTest.updateConfig(config);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateConfig_SysConfigManagerSelectConfigListReturnsNoItems() {
        // Setup
        final SysConfig config = new SysConfig();
        config.setConfigId(0L);
        config.setConfigName("configName");
        config.setConfigKey("configKey");
        config.setConfigValue("configValue");
        config.setConfigType("configType");

        when(mockSysConfigManager.updateConfig(any(SysConfigDO.class))).thenReturn(0);
        when(mockSysConfigManager.selectConfigList(any(SysConfigDO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = sysConfigServiceImplUnderTest.updateConfig(config);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteConfigByIds() {
        // Setup
        when(mockSysConfigManager.deleteConfigByIds(any(String[].class))).thenReturn(0);

        // Configure SysConfigManager.selectConfigList(...).
        final SysConfigDO sysConfigDO = new SysConfigDO();
        sysConfigDO.setConfigId(0L);
        sysConfigDO.setConfigName("configName");
        sysConfigDO.setConfigKey("configKey");
        sysConfigDO.setConfigValue("configValue");
        sysConfigDO.setConfigType("configType");
        final List<SysConfigDO> sysConfigDOS = Arrays.asList(sysConfigDO);
        when(mockSysConfigManager.selectConfigList(any(SysConfigDO.class))).thenReturn(sysConfigDOS);

        // Run the test
        final int result = sysConfigServiceImplUnderTest.deleteConfigByIds("ids");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteConfigByIds_SysConfigManagerSelectConfigListReturnsNoItems() {
        final SysConfig sysConfig = new SysConfig();
        sysConfig.setConfigId(1001L);
        sysConfig.setConfigName("configName");
        sysConfig.setConfigKey("configKey");
        sysConfig.setConfigValue("configValue");
        sysConfig.setConfigType("configType");
        final List<SysConfig> sysConfigs = Arrays.asList(sysConfig);
        // Setup
        when(mockSysConfigManager.deleteConfigByIds(any(String[].class))).thenReturn(0);
        when(mockSysConfigManager.selectConfigList(any(SysConfigDO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = sysConfigServiceImplUnderTest.deleteConfigByIds("ids");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testCheckConfigKeyUnique() {
        // Setup
        final SysConfig config = new SysConfig();
        config.setConfigId(0L);
        config.setConfigName("configName");
        config.setConfigKey("configKey");
        config.setConfigValue("configValue");
        config.setConfigType("configType");

        // Configure SysConfigManager.checkConfigKeyUnique(...).
        final SysConfigDO sysConfigDO = new SysConfigDO();
        sysConfigDO.setConfigId(0L);
        sysConfigDO.setConfigName("configName");
        sysConfigDO.setConfigKey("configKey");
        sysConfigDO.setConfigValue("configValue");
        sysConfigDO.setConfigType("configType");
        when(mockSysConfigManager.checkConfigKeyUnique("configKey")).thenReturn(sysConfigDO);

        // Run the test
        final String result = sysConfigServiceImplUnderTest.checkConfigKeyUnique(config);

        // Verify the results
        assertEquals("0", result);
    }

    @Test
    public void testUpdateValueByKey() {
        // Setup
        // Configure SysConfigManager.checkConfigKeyUnique(...).
        final SysConfigDO sysConfigDO = new SysConfigDO();
        sysConfigDO.setConfigId(0L);
        sysConfigDO.setConfigName("configName");
        sysConfigDO.setConfigKey("configKey");
        sysConfigDO.setConfigValue("configValue");
        sysConfigDO.setConfigType("configType");
        when(mockSysConfigManager.checkConfigKeyUnique("configKey")).thenReturn(sysConfigDO);

        when(mockSysConfigManager.updateConfig(any(SysConfigDO.class))).thenReturn(0);

        // Configure SysConfigManager.selectConfigList(...).
        final SysConfigDO sysConfigDO1 = new SysConfigDO();
        sysConfigDO1.setConfigId(0L);
        sysConfigDO1.setConfigName("configName");
        sysConfigDO1.setConfigKey("configKey");
        sysConfigDO1.setConfigValue("configValue");
        sysConfigDO1.setConfigType("configType");
        final List<SysConfigDO> sysConfigDOS = Arrays.asList(sysConfigDO1);
        when(mockSysConfigManager.selectConfigList(any(SysConfigDO.class))).thenReturn(sysConfigDOS);

        // Run the test
        final int result = sysConfigServiceImplUnderTest.updateValueByKey("key", "configValue");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateValueByKey_SysConfigManagerSelectConfigListReturnsNoItems() {
        // Setup
        // Configure SysConfigManager.checkConfigKeyUnique(...).
        final SysConfigDO sysConfigDO = new SysConfigDO();
        sysConfigDO.setConfigId(0L);
        sysConfigDO.setConfigName("configName");
        sysConfigDO.setConfigKey("configKey");
        sysConfigDO.setConfigValue("configValue");
        sysConfigDO.setConfigType("configType");
        when(mockSysConfigManager.checkConfigKeyUnique("configKey")).thenReturn(sysConfigDO);

        when(mockSysConfigManager.updateConfig(any(SysConfigDO.class))).thenReturn(0);
        when(mockSysConfigManager.selectConfigList(any(SysConfigDO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final int result = sysConfigServiceImplUnderTest.updateValueByKey("key", "configValue");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testSelectConfigPage() {
        // Setup
        final SysConfig sysConfig = new SysConfig();
        sysConfig.setConfigId(0L);
        sysConfig.setConfigName("configName");
        sysConfig.setConfigKey("configKey");
        sysConfig.setConfigValue("configValue");
        sysConfig.setConfigType("configType");

        // Configure SysConfigManager.selectConfigList(...).
        final SysConfigDO sysConfigDO = new SysConfigDO();
        sysConfigDO.setConfigId(0L);
        sysConfigDO.setConfigName("configName");
        sysConfigDO.setConfigKey("configKey");
        sysConfigDO.setConfigValue("configValue");
        sysConfigDO.setConfigType("configType");
        final List<SysConfigDO> sysConfigDOS = Arrays.asList(sysConfigDO);
        when(mockSysConfigManager.selectConfigList(any(SysConfigDO.class))).thenReturn(sysConfigDOS);

        // Run the test
        final PageInfo<SysConfig> result = sysConfigServiceImplUnderTest.selectConfigPage(sysConfig);

        // Verify the results
    }

    @Test
    public void testSelectConfigPage_SysConfigManagerReturnsNoItems() {
        // Setup
        final SysConfig sysConfig = new SysConfig();
        sysConfig.setConfigId(0L);
        sysConfig.setConfigName("configName");
        sysConfig.setConfigKey("configKey");
        sysConfig.setConfigValue("configValue");
        sysConfig.setConfigType("configType");

        when(mockSysConfigManager.selectConfigList(any(SysConfigDO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final PageInfo<SysConfig> result = sysConfigServiceImplUnderTest.selectConfigPage(sysConfig);

        // Verify the results
    }
}
