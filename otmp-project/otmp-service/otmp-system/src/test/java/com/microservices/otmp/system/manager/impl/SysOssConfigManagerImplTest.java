package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.dto.SysOssConfigDTO;
import com.microservices.otmp.system.domain.entity.SysOssConfigDO;
import com.microservices.otmp.system.mapper.SysOssConfigMapper;
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

@RunWith(MockitoJUnitRunner.Silent.class)
public class SysOssConfigManagerImplTest {

    @Mock
    private SysOssConfigMapper mockSysOssConfigMapper;

    @InjectMocks
    private SysOssConfigManagerImpl sysOssConfigManagerImplUnderTest;

    @Test
    public void testSelectSysOssConfigByOssConfigId() {
        // Setup
        final SysOssConfigDO expectedResult = new SysOssConfigDO();
        expectedResult.setOssConfigId(0L);
        expectedResult.setConfigKey("configKey");
        expectedResult.setAccessKey("accessKey");
        expectedResult.setSecretKey("secretKey");
        expectedResult.setBucketName("bucketName");
        expectedResult.setPrefix("prefix");
        expectedResult.setEndpoint("endpoint");
        expectedResult.setDomain("domain");
        expectedResult.setIsHttps("isHttps");
        expectedResult.setRegion("region");

        // Configure SysOssConfigMapper.selectSysOssConfigByOssConfigId(...).
        final SysOssConfigDO sysOssConfigDO = new SysOssConfigDO();
        sysOssConfigDO.setOssConfigId(0L);
        sysOssConfigDO.setConfigKey("configKey");
        sysOssConfigDO.setAccessKey("accessKey");
        sysOssConfigDO.setSecretKey("secretKey");
        sysOssConfigDO.setBucketName("bucketName");
        sysOssConfigDO.setPrefix("prefix");
        sysOssConfigDO.setEndpoint("endpoint");
        sysOssConfigDO.setDomain("domain");
        sysOssConfigDO.setIsHttps("isHttps");
        sysOssConfigDO.setRegion("region");
        when(mockSysOssConfigMapper.selectSysOssConfigByOssConfigId(0L)).thenReturn(sysOssConfigDO);

        // Run the test
        final SysOssConfigDO result = sysOssConfigManagerImplUnderTest.selectSysOssConfigByOssConfigId(0L);

        // Verify the results
    }

    @Test
    public void testSelectSysOssConfigList() {
        // Setup
        final SysOssConfigDTO sysOssConfig = new SysOssConfigDTO();
        sysOssConfig.setOssConfigId(0L);
        sysOssConfig.setConfigKey("configKey");
        sysOssConfig.setAccessKey("accessKey");
        sysOssConfig.setSecretKey("secretKey");
        sysOssConfig.setBucketName("bucketName");
        sysOssConfig.setPrefix("prefix");
        sysOssConfig.setEndpoint("endpoint");
        sysOssConfig.setDomain("domain");
        sysOssConfig.setIsHttps("isHttps");
        sysOssConfig.setRegion("region");

        final SysOssConfigDO sysOssConfigDO = new SysOssConfigDO();
        sysOssConfigDO.setOssConfigId(0L);
        sysOssConfigDO.setConfigKey("configKey");
        sysOssConfigDO.setAccessKey("accessKey");
        sysOssConfigDO.setSecretKey("secretKey");
        sysOssConfigDO.setBucketName("bucketName");
        sysOssConfigDO.setPrefix("prefix");
        sysOssConfigDO.setEndpoint("endpoint");
        sysOssConfigDO.setDomain("domain");
        sysOssConfigDO.setIsHttps("isHttps");
        sysOssConfigDO.setRegion("region");
        final List<SysOssConfigDO> expectedResult = Arrays.asList(sysOssConfigDO);

        // Configure SysOssConfigMapper.selectSysOssConfigList(...).
        final SysOssConfigDO sysOssConfigDO1 = new SysOssConfigDO();
        sysOssConfigDO1.setOssConfigId(0L);
        sysOssConfigDO1.setConfigKey("configKey");
        sysOssConfigDO1.setAccessKey("accessKey");
        sysOssConfigDO1.setSecretKey("secretKey");
        sysOssConfigDO1.setBucketName("bucketName");
        sysOssConfigDO1.setPrefix("prefix");
        sysOssConfigDO1.setEndpoint("endpoint");
        sysOssConfigDO1.setDomain("domain");
        sysOssConfigDO1.setIsHttps("isHttps");
        sysOssConfigDO1.setRegion("region");
        final List<SysOssConfigDO> sysOssConfigDOS = Arrays.asList(sysOssConfigDO1);
        when(mockSysOssConfigMapper.selectSysOssConfigList(any(SysOssConfigDTO.class))).thenReturn(sysOssConfigDOS);

        // Run the test
        final List<SysOssConfigDO> result = sysOssConfigManagerImplUnderTest.selectSysOssConfigList(sysOssConfig);

        // Verify the results
    }

    @Test
    public void testSelectSysOssConfigList_SysOssConfigMapperReturnsNoItems() {
        // Setup
        final SysOssConfigDTO sysOssConfig = new SysOssConfigDTO();
        sysOssConfig.setOssConfigId(0L);
        sysOssConfig.setConfigKey("configKey");
        sysOssConfig.setAccessKey("accessKey");
        sysOssConfig.setSecretKey("secretKey");
        sysOssConfig.setBucketName("bucketName");
        sysOssConfig.setPrefix("prefix");
        sysOssConfig.setEndpoint("endpoint");
        sysOssConfig.setDomain("domain");
        sysOssConfig.setIsHttps("isHttps");
        sysOssConfig.setRegion("region");

        when(mockSysOssConfigMapper.selectSysOssConfigList(any(SysOssConfigDTO.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysOssConfigDO> result = sysOssConfigManagerImplUnderTest.selectSysOssConfigList(sysOssConfig);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertSysOssConfig() {
        // Setup
        final SysOssConfigDO sysOssConfig = new SysOssConfigDO();
        sysOssConfig.setOssConfigId(0L);
        sysOssConfig.setConfigKey("configKey");
        sysOssConfig.setAccessKey("accessKey");
        sysOssConfig.setSecretKey("secretKey");
        sysOssConfig.setBucketName("bucketName");
        sysOssConfig.setPrefix("prefix");
        sysOssConfig.setEndpoint("endpoint");
        sysOssConfig.setDomain("domain");
        sysOssConfig.setIsHttps("isHttps");
        sysOssConfig.setRegion("region");

        when(mockSysOssConfigMapper.insertSysOssConfig(new SysOssConfigDO())).thenReturn(0);

        // Run the test
        final int result = sysOssConfigManagerImplUnderTest.insertSysOssConfig(sysOssConfig);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateSysOssConfig() {
        // Setup
        final SysOssConfigDO sysOssConfig = new SysOssConfigDO();
        sysOssConfig.setOssConfigId(0L);
        sysOssConfig.setConfigKey("configKey");
        sysOssConfig.setAccessKey("accessKey");
        sysOssConfig.setSecretKey("secretKey");
        sysOssConfig.setBucketName("bucketName");
        sysOssConfig.setPrefix("prefix");
        sysOssConfig.setEndpoint("endpoint");
        sysOssConfig.setDomain("domain");
        sysOssConfig.setIsHttps("isHttps");
        sysOssConfig.setRegion("region");

        when(mockSysOssConfigMapper.updateSysOssConfig(new SysOssConfigDO())).thenReturn(0);

        // Run the test
        final int result = sysOssConfigManagerImplUnderTest.updateSysOssConfig(sysOssConfig);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteSysOssConfigByOssConfigIds() {
        // Setup
        when(mockSysOssConfigMapper.deleteSysOssConfigByOssConfigIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysOssConfigManagerImplUnderTest.deleteSysOssConfigByOssConfigIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteSysOssConfigByOssConfigId() {
        // Setup
        when(mockSysOssConfigMapper.deleteSysOssConfigByOssConfigId(0L)).thenReturn(0);

        // Run the test
        final int result = sysOssConfigManagerImplUnderTest.deleteSysOssConfigByOssConfigId(0L);

        // Verify the results
        assertEquals(0, result);
    }
}
