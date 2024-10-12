package com.microservices.otmp.download.service;

import com.microservices.otmp.download.domain.ReportExportTask;
import com.microservices.otmp.download.mapper.ReportExportTaskMapper;
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
public class ReportExportTaskServiceTest {

    @Mock
    private ReportExportTaskMapper mockReportExportTaskMapper;

    @InjectMocks
    private ReportExportTaskService reportExportTaskServiceUnderTest;

    @Test
    public void testSelectReportExportTaskById() {
        // Setup
        // Configure ReportExportTaskMapper.selectReportExportTaskById(...).
        final ReportExportTask reportExportTask = new ReportExportTask();
        reportExportTask.setId(0);
        reportExportTask.setReportingId("reportingId");
        reportExportTask.setItCode("itCode");
        reportExportTask.setReportName("reportName");
        reportExportTask.setObjectType("objectType");
        reportExportTask.setObjectName("objectName");
        reportExportTask.setCondition("condition");
        reportExportTask.setParameters("parameters");
        reportExportTask.setRules("rules");
        reportExportTask.setRequestTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockReportExportTaskMapper.selectReportExportTaskById(0)).thenReturn(reportExportTask);

        // Run the test
        final ReportExportTask result = reportExportTaskServiceUnderTest.selectReportExportTaskById(0);

        // Verify the results
    }

    @Test
    public void testSelectReportExportTaskList() {
        // Setup
        final ReportExportTask reportExportTask = new ReportExportTask();
        reportExportTask.setId(0);
        reportExportTask.setReportingId("reportingId");
        reportExportTask.setItCode("itCode");
        reportExportTask.setReportName("reportName");
        reportExportTask.setObjectType("objectType");
        reportExportTask.setObjectName("objectName");
        reportExportTask.setCondition("condition");
        reportExportTask.setParameters("parameters");
        reportExportTask.setRules("rules");
        reportExportTask.setRequestTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Configure ReportExportTaskMapper.selectReportExportTaskList(...).
        final ReportExportTask reportExportTask1 = new ReportExportTask();
        reportExportTask1.setId(0);
        reportExportTask1.setReportingId("reportingId");
        reportExportTask1.setItCode("itCode");
        reportExportTask1.setReportName("reportName");
        reportExportTask1.setObjectType("objectType");
        reportExportTask1.setObjectName("objectName");
        reportExportTask1.setCondition("condition");
        reportExportTask1.setParameters("parameters");
        reportExportTask1.setRules("rules");
        reportExportTask1.setRequestTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<ReportExportTask> reportExportTasks = Arrays.asList(reportExportTask1);
        when(mockReportExportTaskMapper.selectReportExportTaskList(any(ReportExportTask.class))).thenReturn(reportExportTasks);

        // Run the test
        final List<ReportExportTask> result = reportExportTaskServiceUnderTest.selectReportExportTaskList(reportExportTask);

        // Verify the results
    }

    @Test
    public void testSelectReportExportTaskList_ReportExportTaskMapperReturnsNoItems() {
        // Setup
        final ReportExportTask reportExportTask = new ReportExportTask();
        reportExportTask.setId(0);
        reportExportTask.setReportingId("reportingId");
        reportExportTask.setItCode("itCode");
        reportExportTask.setReportName("reportName");
        reportExportTask.setObjectType("objectType");
        reportExportTask.setObjectName("objectName");
        reportExportTask.setCondition("condition");
        reportExportTask.setParameters("parameters");
        reportExportTask.setRules("rules");
        reportExportTask.setRequestTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockReportExportTaskMapper.selectReportExportTaskList(any(ReportExportTask.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<ReportExportTask> result = reportExportTaskServiceUnderTest.selectReportExportTaskList(reportExportTask);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testInsertReportExportTask() {
        // Setup
        final ReportExportTask reportExportTask = new ReportExportTask();
        reportExportTask.setId(0);
        reportExportTask.setReportingId("reportingId");
        reportExportTask.setItCode("itCode");
        reportExportTask.setReportName("reportName");
        reportExportTask.setObjectType("objectType");
        reportExportTask.setObjectName("objectName");
        reportExportTask.setCondition("condition");
        reportExportTask.setParameters("parameters");
        reportExportTask.setRules("rules");
        reportExportTask.setRequestTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockReportExportTaskMapper.insertReportExportTask(any(ReportExportTask.class))).thenReturn(0);

        // Run the test
        final int result = reportExportTaskServiceUnderTest.insertReportExportTask(reportExportTask);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testUpdateReportExportTask() {
        // Setup
        final ReportExportTask reportExportTask = new ReportExportTask();
        reportExportTask.setId(0);
        reportExportTask.setReportingId("reportingId");
        reportExportTask.setItCode("itCode");
        reportExportTask.setReportName("reportName");
        reportExportTask.setObjectType("objectType");
        reportExportTask.setObjectName("objectName");
        reportExportTask.setCondition("condition");
        reportExportTask.setParameters("parameters");
        reportExportTask.setRules("rules");
        reportExportTask.setRequestTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockReportExportTaskMapper.updateReportExportTask(any(ReportExportTask.class))).thenReturn(0);

        // Run the test
        final int result = reportExportTaskServiceUnderTest.updateReportExportTask(reportExportTask);

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteReportExportTaskByIds() {
        // Setup
        when(mockReportExportTaskMapper.deleteReportExportTaskByIds(any(String[].class))).thenReturn(0);

        // Run the test
        final int result = reportExportTaskServiceUnderTest.deleteReportExportTaskByIds("ids");

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    public void testDeleteReportExportTaskById() {
        // Setup
        when(mockReportExportTaskMapper.deleteReportExportTaskById(0)).thenReturn(0);

        // Run the test
        final int result = reportExportTaskServiceUnderTest.deleteReportExportTaskById(0);

        // Verify the results
        assertEquals(0, result);
    }
}
