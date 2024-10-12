package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysPost;
import com.microservices.otmp.system.domain.entity.SysPostDO;
import com.microservices.otmp.system.mapper.SysPostMapper;
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
public class SysPostManagerImplTest {

    @Mock
    private SysPostMapper mockSysPostMapper;

    @InjectMocks
    private SysPostManagerImpl sysPostManagerImplUnderTest;

    @Test
    public void testSelectPostList() {
        // Setup
        final SysPost post = new SysPost();
        post.setPostId(0L);
        post.setPostCode("postCode");
        post.setPostName("postName");
        post.setPostSort("postSort");
        post.setStatus("status");
        post.setFlag(false);

        // Configure SysPostMapper.selectPostList(...).
        final SysPostDO sysPostDO = new SysPostDO();
        sysPostDO.setPostId(0L);
        sysPostDO.setPostCode("postCode");
        sysPostDO.setPostName("postName");
        sysPostDO.setPostSort("postSort");
        sysPostDO.setStatus("status");
        sysPostDO.setFlag(false);
        final List<SysPostDO> sysPostDOS = Arrays.asList(sysPostDO);
        when(mockSysPostMapper.selectPostList(any(SysPost.class))).thenReturn(sysPostDOS);

        // Run the test
        final List<SysPostDO> result = sysPostManagerImplUnderTest.selectPostList(post);

        // Verify the results
    }

    @Test
    public void testSelectPostList_SysPostMapperReturnsNoItems() {
        // Setup
        final SysPost post = new SysPost();
        post.setPostId(0L);
        post.setPostCode("postCode");
        post.setPostName("postName");
        post.setPostSort("postSort");
        post.setStatus("status");
        post.setFlag(false);

        when(mockSysPostMapper.selectPostList(any(SysPost.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysPostDO> result = sysPostManagerImplUnderTest.selectPostList(post);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectPostAll() {
        // Setup
        // Configure SysPostMapper.selectPostAll(...).
        final SysPostDO sysPostDO = new SysPostDO();
        sysPostDO.setPostId(0L);
        sysPostDO.setPostCode("postCode");
        sysPostDO.setPostName("postName");
        sysPostDO.setPostSort("postSort");
        sysPostDO.setStatus("status");
        sysPostDO.setFlag(false);
        final List<SysPostDO> sysPostDOS = Arrays.asList(sysPostDO);
        when(mockSysPostMapper.selectPostAll()).thenReturn(sysPostDOS);

        // Run the test
        final List<SysPostDO> result = sysPostManagerImplUnderTest.selectPostAll();

        // Verify the results
    }

    @Test
    public void testSelectPostAll_SysPostMapperReturnsNoItems() {
        // Setup
        when(mockSysPostMapper.selectPostAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysPostDO> result = sysPostManagerImplUnderTest.selectPostAll();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectPostsByUserId() {
        // Setup
        // Configure SysPostMapper.selectPostsByUserId(...).
        final SysPostDO sysPostDO = new SysPostDO();
        sysPostDO.setPostId(0L);
        sysPostDO.setPostCode("postCode");
        sysPostDO.setPostName("postName");
        sysPostDO.setPostSort("postSort");
        sysPostDO.setStatus("status");
        sysPostDO.setFlag(false);
        final List<SysPostDO> sysPostDOS = Arrays.asList(sysPostDO);
        when(mockSysPostMapper.selectPostsByUserId(0L)).thenReturn(sysPostDOS);

        // Run the test
        final List<SysPostDO> result = sysPostManagerImplUnderTest.selectPostsByUserId(0L);

        // Verify the results
    }

    @Test
    public void testSelectPostsByUserId_SysPostMapperReturnsNoItems() {
        // Setup
        when(mockSysPostMapper.selectPostsByUserId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysPostDO> result = sysPostManagerImplUnderTest.selectPostsByUserId(0L);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectPostById() {
        // Setup
        // Configure SysPostMapper.selectPostById(...).
        final SysPostDO sysPostDO = new SysPostDO();
        sysPostDO.setPostId(0L);
        sysPostDO.setPostCode("postCode");
        sysPostDO.setPostName("postName");
        sysPostDO.setPostSort("postSort");
        sysPostDO.setStatus("status");
        sysPostDO.setFlag(false);
        when(mockSysPostMapper.selectPostById(0L)).thenReturn(sysPostDO);

        // Run the test
        final SysPostDO result = sysPostManagerImplUnderTest.selectPostById(0L);

        // Verify the results
    }

    @Test
    public void testDeletePostByIds() {
        // Setup
        when(mockSysPostMapper.deletePostByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = sysPostManagerImplUnderTest.deletePostByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdatePost() {
        // Setup
        final SysPostDO post = new SysPostDO();
        post.setPostId(0L);
        post.setPostCode("postCode");
        post.setPostName("postName");
        post.setPostSort("postSort");
        post.setStatus("status");
        post.setFlag(false);

        when(mockSysPostMapper.updatePost(any(SysPostDO.class))).thenReturn(0);

        // Run the test
        final int result = sysPostManagerImplUnderTest.updatePost(post);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testInsertPost() {
        // Setup
        final SysPostDO post = new SysPostDO();
        post.setPostId(0L);
        post.setPostCode("postCode");
        post.setPostName("postName");
        post.setPostSort("postSort");
        post.setStatus("status");
        post.setFlag(false);

        when(mockSysPostMapper.insertPost(any(SysPostDO.class))).thenReturn(0);

        // Run the test
        final int result = sysPostManagerImplUnderTest.insertPost(post);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testCheckPostNameUnique() {
        // Setup
        // Configure SysPostMapper.checkPostNameUnique(...).
        final SysPostDO sysPostDO = new SysPostDO();
        sysPostDO.setPostId(0L);
        sysPostDO.setPostCode("postCode");
        sysPostDO.setPostName("postName");
        sysPostDO.setPostSort("postSort");
        sysPostDO.setStatus("status");
        sysPostDO.setFlag(false);
        when(mockSysPostMapper.checkPostNameUnique("postName")).thenReturn(sysPostDO);

        // Run the test
        final SysPostDO result = sysPostManagerImplUnderTest.checkPostNameUnique("postName");

        // Verify the results
    }

    @Test
    public void testCheckPostCodeUnique() {
        // Setup
        // Configure SysPostMapper.checkPostCodeUnique(...).
        final SysPostDO sysPostDO = new SysPostDO();
        sysPostDO.setPostId(0L);
        sysPostDO.setPostCode("postCode");
        sysPostDO.setPostName("postName");
        sysPostDO.setPostSort("postSort");
        sysPostDO.setStatus("status");
        sysPostDO.setFlag(false);
        when(mockSysPostMapper.checkPostCodeUnique("postCode")).thenReturn(sysPostDO);

        // Run the test
        final SysPostDO result = sysPostManagerImplUnderTest.checkPostCodeUnique("postCode");

        // Verify the results
    }
}
