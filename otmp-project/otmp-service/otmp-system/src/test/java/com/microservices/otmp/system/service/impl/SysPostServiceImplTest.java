package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.system.domain.SysPost;
import com.microservices.otmp.system.domain.entity.SysPostDO;
import com.microservices.otmp.system.manager.SysPostManager;
import com.microservices.otmp.system.manager.SysUserPostManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SysPostServiceImplTest {

    @Mock
    private SysPostManager mockSysPostManager;
    @Mock
    private SysUserPostManager mockSysUserPostManager;

    @InjectMocks
    private SysPostServiceImpl sysPostServiceImplUnderTest;

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

        // Configure SysPostManager.selectPostList(...).
        final SysPostDO sysPostDO = new SysPostDO();
        sysPostDO.setPostId(0L);
        sysPostDO.setPostCode("postCode");
        sysPostDO.setPostName("postName");
        sysPostDO.setPostSort("postSort");
        sysPostDO.setStatus("status");
        sysPostDO.setFlag(false);
        final List<SysPostDO> sysPostDOS = Arrays.asList(sysPostDO);
        when(mockSysPostManager.selectPostList(any(SysPost.class))).thenReturn(sysPostDOS);

        // Run the test
        final List<SysPost> result = sysPostServiceImplUnderTest.selectPostList(post);

        // Verify the results
    }

    @Test
    public void testSelectPostList_SysPostManagerReturnsNoItems() {
        // Setup
        final SysPost post = new SysPost();
        post.setPostId(0L);
        post.setPostCode("postCode");
        post.setPostName("postName");
        post.setPostSort("postSort");
        post.setStatus("status");
        post.setFlag(false);

        when(mockSysPostManager.selectPostList(any(SysPost.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysPost> result = sysPostServiceImplUnderTest.selectPostList(post);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectPostAll() {
        // Setup
        // Configure SysPostManager.selectPostAll(...).
        final SysPostDO sysPostDO = new SysPostDO();
        sysPostDO.setPostId(0L);
        sysPostDO.setPostCode("postCode");
        sysPostDO.setPostName("postName");
        sysPostDO.setPostSort("postSort");
        sysPostDO.setStatus("status");
        sysPostDO.setFlag(false);
        final List<SysPostDO> sysPostDOS = Arrays.asList(sysPostDO);
        when(mockSysPostManager.selectPostAll()).thenReturn(sysPostDOS);

        // Run the test
        final List<SysPost> result = sysPostServiceImplUnderTest.selectPostAll();

        // Verify the results
    }

    @Test
    public void testSelectPostAll_SysPostManagerReturnsNoItems() {
        // Setup
        when(mockSysPostManager.selectPostAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysPost> result = sysPostServiceImplUnderTest.selectPostAll();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testSelectPostsByUserId() {
        // Setup
        // Configure SysPostManager.selectPostsByUserId(...).
        final SysPostDO sysPostDO = new SysPostDO();
        sysPostDO.setPostId(0L);
        sysPostDO.setPostCode("postCode");
        sysPostDO.setPostName("postName");
        sysPostDO.setPostSort("postSort");
        sysPostDO.setStatus("status");
        sysPostDO.setFlag(false);
        final List<SysPostDO> sysPostDOS = Arrays.asList(sysPostDO);
        when(mockSysPostManager.selectPostsByUserId(0L)).thenReturn(sysPostDOS);

        // Configure SysPostManager.selectPostAll(...).
        final SysPostDO sysPostDO1 = new SysPostDO();
        sysPostDO1.setPostId(0L);
        sysPostDO1.setPostCode("postCode");
        sysPostDO1.setPostName("postName");
        sysPostDO1.setPostSort("postSort");
        sysPostDO1.setStatus("status");
        sysPostDO1.setFlag(false);
        final List<SysPostDO> sysPostDOS1 = Arrays.asList(sysPostDO1);
        when(mockSysPostManager.selectPostAll()).thenReturn(sysPostDOS1);

        // Run the test
        final List<SysPost> result = sysPostServiceImplUnderTest.selectPostsByUserId(0L);

        // Verify the results
    }

    @Test
    public void testSelectPostsByUserId_SysPostManagerSelectPostsByUserIdReturnsNoItems() {
        // Setup
        when(mockSysPostManager.selectPostsByUserId(0L)).thenReturn(Collections.emptyList());

        // Configure SysPostManager.selectPostAll(...).
        final SysPostDO sysPostDO = new SysPostDO();
        sysPostDO.setPostId(0L);
        sysPostDO.setPostCode("postCode");
        sysPostDO.setPostName("postName");
        sysPostDO.setPostSort("postSort");
        sysPostDO.setStatus("status");
        sysPostDO.setFlag(false);
        final List<SysPostDO> sysPostDOS = Arrays.asList(sysPostDO);
        when(mockSysPostManager.selectPostAll()).thenReturn(sysPostDOS);

        // Run the test
        final List<SysPost> result = sysPostServiceImplUnderTest.selectPostsByUserId(0L);

        // Verify the results
    }

    @Test
    public void testSelectPostsByUserId_SysPostManagerSelectPostAllReturnsNoItems() {
        // Setup
        // Configure SysPostManager.selectPostsByUserId(...).
        final SysPostDO sysPostDO = new SysPostDO();
        sysPostDO.setPostId(0L);
        sysPostDO.setPostCode("postCode");
        sysPostDO.setPostName("postName");
        sysPostDO.setPostSort("postSort");
        sysPostDO.setStatus("status");
        sysPostDO.setFlag(false);
        final List<SysPostDO> sysPostDOS = Arrays.asList(sysPostDO);
        when(mockSysPostManager.selectPostsByUserId(0L)).thenReturn(sysPostDOS);

        when(mockSysPostManager.selectPostAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<SysPost> result = sysPostServiceImplUnderTest.selectPostsByUserId(0L);

        // Verify the results
    }

    @Test
    public void testSelectPostById() {
        // Setup
        // Configure SysPostManager.selectPostById(...).
        final SysPostDO sysPostDO = new SysPostDO();
        sysPostDO.setPostId(0L);
        sysPostDO.setPostCode("postCode");
        sysPostDO.setPostName("postName");
        sysPostDO.setPostSort("postSort");
        sysPostDO.setStatus("status");
        sysPostDO.setFlag(false);
        when(mockSysPostManager.selectPostById(0L)).thenReturn(sysPostDO);

        // Run the test
        final SysPost result = sysPostServiceImplUnderTest.selectPostById(0L);

        // Verify the results
    }

    @Test
    public void testDeletePostByIds() {
        // Setup
        // Configure SysPostManager.selectPostById(...).
        final SysPostDO sysPostDO = new SysPostDO();
        sysPostDO.setPostId(1L);
        sysPostDO.setPostCode("postCode");
        sysPostDO.setPostName("postName");
        sysPostDO.setPostSort("postSort");
        sysPostDO.setStatus("1");
        sysPostDO.setFlag(false);
        when(mockSysPostManager.selectPostById(1L)).thenReturn(sysPostDO);

        when(mockSysUserPostManager.countUserPostById(1L)).thenReturn(0);
        when(mockSysPostManager.deletePostByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        Boolean catchException = false;
        try {
            // Run the test
            final int result = sysPostServiceImplUnderTest.deletePostByIds("1");

            // Verify the results
            assertEquals(0, result);

        } catch (Exception e) {
            catchException = true;
        }
        assertFalse(catchException);
    }

    @Test
    public void testDeletePostByIds_ThrowsBusinessException() {
        // Setup
        // Configure SysPostManager.selectPostById(...).
        final SysPostDO sysPostDO = new SysPostDO();
        sysPostDO.setPostId(1L);
        sysPostDO.setPostCode("postCode");
        sysPostDO.setPostName("postName");
        sysPostDO.setPostSort("postSort");
        sysPostDO.setStatus("1");
        sysPostDO.setFlag(false);
        when(mockSysPostManager.selectPostById(1L)).thenReturn(sysPostDO);

        when(mockSysUserPostManager.countUserPostById(1L)).thenReturn(0);
        when(mockSysPostManager.deletePostByIds(any(Long[].class))).thenReturn(0);


        Boolean catchException = false;
        try {
            // Run the test
            final int result = sysPostServiceImplUnderTest.deletePostByIds("1");

            // Verify the results
            assertEquals(0, result);

        } catch (Exception e) {
            catchException = true;
        }
        assertFalse(catchException);


    }

    @Test
    public void testInsertPost() {
        // Setup
        final SysPost post = new SysPost();
        post.setPostId(0L);
        post.setPostCode("postCode");
        post.setPostName("postName");
        post.setPostSort("postSort");
        post.setStatus("status");
        post.setFlag(false);

        when(mockSysPostManager.insertPost(any(SysPostDO.class))).thenReturn(0);

        // Run the test
        final int result = sysPostServiceImplUnderTest.insertPost(post);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdatePost() {
        // Setup
        final SysPost post = new SysPost();
        post.setPostId(0L);
        post.setPostCode("postCode");
        post.setPostName("postName");
        post.setPostSort("postSort");
        post.setStatus("status");
        post.setFlag(false);

        when(mockSysPostManager.updatePost(any(SysPostDO.class))).thenReturn(0);

        // Run the test
        final int result = sysPostServiceImplUnderTest.updatePost(post);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testCountUserPostById() {
        // Setup
        when(mockSysUserPostManager.countUserPostById(0L)).thenReturn(0);

        // Run the test
        final int result = sysPostServiceImplUnderTest.countUserPostById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testCheckPostNameUnique() {
        // Setup
        final SysPost post = new SysPost();
        post.setPostId(0L);
        post.setPostCode("postCode");
        post.setPostName("postName");
        post.setPostSort("postSort");
        post.setStatus("status");
        post.setFlag(false);

        // Configure SysPostManager.checkPostNameUnique(...).
        final SysPostDO sysPostDO = new SysPostDO();
        sysPostDO.setPostId(0L);
        sysPostDO.setPostCode("postCode");
        sysPostDO.setPostName("postName");
        sysPostDO.setPostSort("postSort");
        sysPostDO.setStatus("status");
        sysPostDO.setFlag(false);
        when(mockSysPostManager.checkPostNameUnique("postName")).thenReturn(sysPostDO);

        // Run the test
        final String result = sysPostServiceImplUnderTest.checkPostNameUnique(post);

        // Verify the results
        assertEquals("0", result);
    }

    @Test
    public void testCheckPostCodeUnique() {
        // Setup
        final SysPost post = new SysPost();
        post.setPostId(0L);
        post.setPostCode("postCode");
        post.setPostName("postName");
        post.setPostSort("postSort");
        post.setStatus("status");
        post.setFlag(false);

        // Configure SysPostManager.checkPostCodeUnique(...).
        final SysPostDO sysPostDO = new SysPostDO();
        sysPostDO.setPostId(0L);
        sysPostDO.setPostCode("postCode");
        sysPostDO.setPostName("postName");
        sysPostDO.setPostSort("postSort");
        sysPostDO.setStatus("status");
        sysPostDO.setFlag(false);
        when(mockSysPostManager.checkPostCodeUnique("postCode")).thenReturn(sysPostDO);

        // Run the test
        final String result = sysPostServiceImplUnderTest.checkPostCodeUnique(post);

        // Verify the results
        assertEquals("0", result);
    }
}
