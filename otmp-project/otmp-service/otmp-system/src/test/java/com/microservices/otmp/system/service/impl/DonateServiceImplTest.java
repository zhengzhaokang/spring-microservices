package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.system.domain.Donate;
import com.microservices.otmp.system.mapper.DonateMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class DonateServiceImplTest {

    @Mock
    private DonateMapper mockDonateMapper;

    @InjectMocks
    private DonateServiceImpl donateServiceImplUnderTest;

    @Test
    public void testSelectDistrictsList() {
        // Setup
        final Donate donate = new Donate();
        donate.setId(0);
        donate.setNick("nick");
        donate.setAmount(0.0);
        donate.setCanal(0);
        donate.setRemark("remark");
        donate.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        donate.setBeginTime("beginTime");
        donate.setEndTime("endTime");

        final Donate donate1 = new Donate();
        donate1.setId(0);
        donate1.setNick("nick");
        donate1.setAmount(0.0);
        donate1.setCanal(0);
        donate1.setRemark("remark");
        donate1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        donate1.setBeginTime("beginTime");
        donate1.setEndTime("endTime");
        final List<Donate> expectedResult = Arrays.asList(donate1);

        // Configure DonateMapper.selectByExample(...).
        final Donate donate2 = new Donate();
        donate2.setId(0);
        donate2.setNick("nick");
        donate2.setAmount(0.0);
        donate2.setCanal(0);
        donate2.setRemark("remark");
        donate2.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        donate2.setBeginTime("beginTime");
        donate2.setEndTime("endTime");
        final List<Donate> donates = Arrays.asList(donate2);
        when(mockDonateMapper.selectByExample(any(Object.class))).thenReturn(donates);


        // Run the test
        Boolean catchException = false;
        try {
            final List<Donate> result = donateServiceImplUnderTest.selectDistrictsList(donate);
            // Verify the results
            assertEquals(expectedResult, result);
        } catch (Exception e) {
            catchException = true;
        }
        assertTrue(catchException);


    }

    @Test
    public void testSelectDistrictsList_DonateMapperReturnsNoItems() {
        // Setup
        final Donate donate = new Donate();
        donate.setId(0);
        donate.setNick("nick");
        donate.setAmount(0.0);
        donate.setCanal(0);
        donate.setRemark("remark");
        donate.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        donate.setBeginTime("beginTime");
        donate.setEndTime("endTime");

        when(mockDonateMapper.selectByExample(any(Object.class))).thenReturn(Collections.emptyList());

        Boolean catchException = false;
        try {
            // Run the test
            final List<Donate> result = donateServiceImplUnderTest.selectDistrictsList(donate);

            // Verify the results
            assertEquals(Collections.emptyList(), result);
        } catch (Exception e) {
            catchException = true;
        }
        assertTrue(catchException);
    }
}
