package com.microservices.otmp.erp.common;

import org.junit.Assert;
import org.junit.Test;

public class WordBaseDataTest {

    @Test
    public void testGetInstance() {
        // Setup
        // Run the test
        final String result = WordBaseData.REDIS_WORD;

        // Verify the results
        Assert.assertEquals( "word:json",result);
    }
}
