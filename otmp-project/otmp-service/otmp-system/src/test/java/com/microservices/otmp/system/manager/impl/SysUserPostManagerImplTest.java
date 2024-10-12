package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.entity.SysUserPostDO;
import com.microservices.otmp.system.mapper.SysUserPostMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SysUserPostManagerImplTest {

    @Mock
    private SysUserPostMapper mockSysUserPostMapper;

    @InjectMocks
    private SysUserPostManagerImpl sysUserPostManagerImplUnderTest;

    @Test
    public void testDeleteUserPostByUserId() {
        // Setup
        when(mockSysUserPostMapper.deleteUserPostByUserId(0L)).thenReturn(0);

        // Run the test
        final int result = sysUserPostManagerImplUnderTest.deleteUserPostByUserId(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testCountUserPostById() {
        // Setup
        when(mockSysUserPostMapper.countUserPostById(0L)).thenReturn(0);

        // Run the test
        final int result = sysUserPostManagerImplUnderTest.countUserPostById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteUserPost() {
        // Setup
        when(mockSysUserPostMapper.deleteUserPost(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysUserPostManagerImplUnderTest.deleteUserPost(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testBatchUserPost() {
        // Setup
        final SysUserPostDO sysUserPostDO = new SysUserPostDO();
        sysUserPostDO.setUserId(0L);
        sysUserPostDO.setPostId(0L);
        final List<SysUserPostDO> userPostList = Arrays.asList(sysUserPostDO);
        when(mockSysUserPostMapper.batchUserPost(Arrays.asList(new SysUserPostDO()))).thenReturn(0);

        // Run the test
        final int result = sysUserPostManagerImplUnderTest.batchUserPost(userPostList);

        // Verify the results
        assertEquals(0, result);
    }
}
