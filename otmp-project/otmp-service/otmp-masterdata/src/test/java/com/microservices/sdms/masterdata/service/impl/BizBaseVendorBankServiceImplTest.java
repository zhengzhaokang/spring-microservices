package com.microservices.sdms.masterdata.service.impl;

import com.microservices.otmp.masterdata.domain.BizBaseVendorBank;
import com.microservices.otmp.masterdata.mapper.BizBaseVendorBankMapper;
import com.microservices.otmp.masterdata.service.impl.BizBaseVendorBankServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BizBaseVendorBankServiceImplTest {

    @Mock
    private BizBaseVendorBankMapper mockBizBaseVendorBankMapper;

    @InjectMocks
    private BizBaseVendorBankServiceImpl bizBaseVendorBankServiceImplUnderTest;

    @Test
    public void testSelectBizBaseVendorBankById() {
        // Setup
        final BizBaseVendorBank expectedResult = new BizBaseVendorBank();
        expectedResult.setId(0);
        expectedResult.setVendorCode("vendorCode");
        expectedResult.setBankType("bankType");
        expectedResult.setBankAccount("bankAccount");
        expectedResult.setBankNumber("bankNumber");
        expectedResult.setBankName("bankName");
        expectedResult.setBankCountry("bankCountry");
        expectedResult.setSwiftCode("swiftCode");
        expectedResult.setIban("iban");
        expectedResult.setAccountHolder("accountHolder");
        expectedResult.setBranchCode("branchCode");
        expectedResult.setStatus("status");
        expectedResult.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure BizBaseVendorBankMapper.selectBizBaseVendorBankById(...).
        final BizBaseVendorBank bizBaseVendorBank = new BizBaseVendorBank();
        bizBaseVendorBank.setId(0);
        bizBaseVendorBank.setVendorCode("vendorCode");
        bizBaseVendorBank.setBankType("bankType");
        bizBaseVendorBank.setBankAccount("bankAccount");
        bizBaseVendorBank.setBankNumber("bankNumber");
        bizBaseVendorBank.setBankName("bankName");
        bizBaseVendorBank.setBankCountry("bankCountry");
        bizBaseVendorBank.setSwiftCode("swiftCode");
        bizBaseVendorBank.setIban("iban");
        bizBaseVendorBank.setAccountHolder("accountHolder");
        bizBaseVendorBank.setBranchCode("branchCode");
        bizBaseVendorBank.setStatus("status");
        bizBaseVendorBank.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorBank.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockBizBaseVendorBankMapper.selectBizBaseVendorBankById(0L)).thenReturn(bizBaseVendorBank);

        // Run the test
        final BizBaseVendorBank result = bizBaseVendorBankServiceImplUnderTest.selectBizBaseVendorBankById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBaseVendorBankList() {
        // Setup
        final BizBaseVendorBank bizBaseVendorBank = new BizBaseVendorBank();
        bizBaseVendorBank.setId(0);
        bizBaseVendorBank.setVendorCode("vendorCode");
        bizBaseVendorBank.setBankType("bankType");
        bizBaseVendorBank.setBankAccount("bankAccount");
        bizBaseVendorBank.setBankNumber("bankNumber");
        bizBaseVendorBank.setBankName("bankName");
        bizBaseVendorBank.setBankCountry("bankCountry");
        bizBaseVendorBank.setSwiftCode("swiftCode");
        bizBaseVendorBank.setIban("iban");
        bizBaseVendorBank.setAccountHolder("accountHolder");
        bizBaseVendorBank.setBranchCode("branchCode");
        bizBaseVendorBank.setStatus("status");
        bizBaseVendorBank.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorBank.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        final BizBaseVendorBank bizBaseVendorBank1 = new BizBaseVendorBank();
        bizBaseVendorBank1.setId(0);
        bizBaseVendorBank1.setVendorCode("vendorCode");
        bizBaseVendorBank1.setBankType("bankType");
        bizBaseVendorBank1.setBankAccount("bankAccount");
        bizBaseVendorBank1.setBankNumber("bankNumber");
        bizBaseVendorBank1.setBankName("bankName");
        bizBaseVendorBank1.setBankCountry("bankCountry");
        bizBaseVendorBank1.setSwiftCode("swiftCode");
        bizBaseVendorBank1.setIban("iban");
        bizBaseVendorBank1.setAccountHolder("accountHolder");
        bizBaseVendorBank1.setBranchCode("branchCode");
        bizBaseVendorBank1.setStatus("status");
        bizBaseVendorBank1.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorBank1.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseVendorBank> expectedResult = Arrays.asList(bizBaseVendorBank1);

        // Configure BizBaseVendorBankMapper.selectBizBaseVendorBankList(...).
        final BizBaseVendorBank bizBaseVendorBank2 = new BizBaseVendorBank();
        bizBaseVendorBank2.setId(0);
        bizBaseVendorBank2.setVendorCode("vendorCode");
        bizBaseVendorBank2.setBankType("bankType");
        bizBaseVendorBank2.setBankAccount("bankAccount");
        bizBaseVendorBank2.setBankNumber("bankNumber");
        bizBaseVendorBank2.setBankName("bankName");
        bizBaseVendorBank2.setBankCountry("bankCountry");
        bizBaseVendorBank2.setSwiftCode("swiftCode");
        bizBaseVendorBank2.setIban("iban");
        bizBaseVendorBank2.setAccountHolder("accountHolder");
        bizBaseVendorBank2.setBranchCode("branchCode");
        bizBaseVendorBank2.setStatus("status");
        bizBaseVendorBank2.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorBank2.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<BizBaseVendorBank> bizBaseVendorBanks = Arrays.asList(bizBaseVendorBank2);
        when(mockBizBaseVendorBankMapper.selectBizBaseVendorBankList(any(BizBaseVendorBank.class)))
                .thenReturn(bizBaseVendorBanks);

        // Run the test
        final List<BizBaseVendorBank> result = bizBaseVendorBankServiceImplUnderTest.selectBizBaseVendorBankList(
                bizBaseVendorBank);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSelectBizBaseVendorBankList_BizBaseVendorBankMapperReturnsNoItems() {
        // Setup
        final BizBaseVendorBank bizBaseVendorBank = new BizBaseVendorBank();
        bizBaseVendorBank.setId(0);
        bizBaseVendorBank.setVendorCode("vendorCode");
        bizBaseVendorBank.setBankType("bankType");
        bizBaseVendorBank.setBankAccount("bankAccount");
        bizBaseVendorBank.setBankNumber("bankNumber");
        bizBaseVendorBank.setBankName("bankName");
        bizBaseVendorBank.setBankCountry("bankCountry");
        bizBaseVendorBank.setSwiftCode("swiftCode");
        bizBaseVendorBank.setIban("iban");
        bizBaseVendorBank.setAccountHolder("accountHolder");
        bizBaseVendorBank.setBranchCode("branchCode");
        bizBaseVendorBank.setStatus("status");
        bizBaseVendorBank.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorBank.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseVendorBankMapper.selectBizBaseVendorBankList(any(BizBaseVendorBank.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<BizBaseVendorBank> result = bizBaseVendorBankServiceImplUnderTest.selectBizBaseVendorBankList(
                bizBaseVendorBank);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertBizBaseVendorBank() {
        // Setup
        final BizBaseVendorBank bizBaseVendorBank = new BizBaseVendorBank();
        bizBaseVendorBank.setId(0);
        bizBaseVendorBank.setVendorCode("vendorCode");
        bizBaseVendorBank.setBankType("bankType");
        bizBaseVendorBank.setBankAccount("bankAccount");
        bizBaseVendorBank.setBankNumber("bankNumber");
        bizBaseVendorBank.setBankName("bankName");
        bizBaseVendorBank.setBankCountry("bankCountry");
        bizBaseVendorBank.setSwiftCode("swiftCode");
        bizBaseVendorBank.setIban("iban");
        bizBaseVendorBank.setAccountHolder("accountHolder");
        bizBaseVendorBank.setBranchCode("branchCode");
        bizBaseVendorBank.setStatus("status");
        bizBaseVendorBank.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorBank.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseVendorBankMapper.insertBizBaseVendorBank(any(BizBaseVendorBank.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseVendorBankServiceImplUnderTest.insertBizBaseVendorBank(bizBaseVendorBank);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateBizBaseVendorBank() {
        // Setup
        final BizBaseVendorBank bizBaseVendorBank = new BizBaseVendorBank();
        bizBaseVendorBank.setId(0);
        bizBaseVendorBank.setVendorCode("vendorCode");
        bizBaseVendorBank.setBankType("bankType");
        bizBaseVendorBank.setBankAccount("bankAccount");
        bizBaseVendorBank.setBankNumber("bankNumber");
        bizBaseVendorBank.setBankName("bankName");
        bizBaseVendorBank.setBankCountry("bankCountry");
        bizBaseVendorBank.setSwiftCode("swiftCode");
        bizBaseVendorBank.setIban("iban");
        bizBaseVendorBank.setAccountHolder("accountHolder");
        bizBaseVendorBank.setBranchCode("branchCode");
        bizBaseVendorBank.setStatus("status");
        bizBaseVendorBank.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        bizBaseVendorBank.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockBizBaseVendorBankMapper.updateBizBaseVendorBank(any(BizBaseVendorBank.class))).thenReturn(0);

        // Run the test
        final int result = bizBaseVendorBankServiceImplUnderTest.updateBizBaseVendorBank(bizBaseVendorBank);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseVendorBankByIds() {
        // Setup
        when(mockBizBaseVendorBankMapper.deleteBizBaseVendorBankByIds(any(Long[].class))).thenReturn(0);

        // Run the test
        final int result = bizBaseVendorBankServiceImplUnderTest.deleteBizBaseVendorBankByIds(new Long[]{0L});

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBizBaseVendorBankById() {
        // Setup
        when(mockBizBaseVendorBankMapper.deleteBizBaseVendorBankById(0L)).thenReturn(0);

        // Run the test
        final int result = bizBaseVendorBankServiceImplUnderTest.deleteBizBaseVendorBankById(0L);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteBank() {
        // Setup
        when(mockBizBaseVendorBankMapper.deleteBank("vendorCode",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(0);

        // Run the test
        final int result = bizBaseVendorBankServiceImplUnderTest.deleteBank("vendorCode",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Verify the results
        assertEquals(0, result);
    }
}
