package com.microservices.otmp.analysis.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.microservices.otmp.analysis.domain.dto.InvoiceExtractionQueryVO;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.otmp.analysis.domain.dto.InvoiceExtractionStatisticsDTO;
import com.microservices.otmp.analysis.service.IInvoiceExtractionStatisticsService;


/**
 * Invoice extraction statisticsController
 *
 * @author lovefamily
 * @date 2023-10-27
 */
@RestController
@RequestMapping("/invoiceExtraction")
public class InvoiceExtractionStatisticsController extends BaseController {
    @Autowired
    private IInvoiceExtractionStatisticsService invoiceExtractionStatisticsService;

    /**
     * 查询Invoice extraction statistics列表
     */
//    @HasPermissions("invoiceExtraction:invoiceExtraction:list")
    @GetMapping("/list")
    public TableDataInfo list(InvoiceExtractionStatisticsDTO invoiceExtractionStatistics) {
        startPage();
        List<InvoiceExtractionStatisticsDTO> list = invoiceExtractionStatisticsService.selectInvoiceExtractionStatisticsList(invoiceExtractionStatistics);
        return getDataTable(list);
    }

    @GetMapping("/findInvoiceCount")
    public ResultDTO findInvoiceCount(InvoiceExtractionQueryVO invoiceExtractionQueryVO) throws ParseException {
        Long supplierId = invoiceExtractionQueryVO.getSupplierId();
        String queryDateType = invoiceExtractionQueryVO.getQueryDateType();
        int intervals = 7;
        if (StringUtils.isNotEmpty(queryDateType)) {
            intervals = Integer.valueOf(queryDateType);
        }
        List<String> pastDaysList = getLastDateTimeList(intervals);
        String startDateTime = pastDaysList.get(pastDaysList.size() - 1) + " 00:00:00";
        String endDateTime = pastDaysList.get(0) + " 23:59:59";
        invoiceExtractionQueryVO.setStartDateTime(startDateTime);
        invoiceExtractionQueryVO.setEndDateTime(endDateTime);
        List<InvoiceExtractionStatisticsDTO> list = invoiceExtractionStatisticsService.findInvoiceExtractionStatisticsList(invoiceExtractionQueryVO);
        List<InvoiceExtractionStatisticsDTO> resultList = new ArrayList<>();
        List<InvoiceExtractionStatisticsDTO> returnDataList = new ArrayList<>();

        buildResultList(list, resultList, returnDataList, pastDaysList, supplierId);

        return ResultDTO.success(returnDataList);
    }

    @GetMapping("/findInvoiceCountPageList")
    public TableDataInfo findInvoiceCountPageList(InvoiceExtractionQueryVO invoiceExtractionQueryVO) throws ParseException {
        Long supplierId = invoiceExtractionQueryVO.getSupplierId();
        String queryDateType = invoiceExtractionQueryVO.getQueryDateType();
        String queryDateTime = invoiceExtractionQueryVO.getQueryDateTime();

        int intervals = 7;
        if (StringUtils.isNotEmpty(queryDateType)) {
            intervals = Integer.valueOf(queryDateType);
        }
        List<String> pastDaysList = getLastDateTimeList(intervals);
        if (StringUtils.isNotEmpty(queryDateTime)) {
            invoiceExtractionQueryVO.setStartDateTime(queryDateTime + " 00:00:00");
            invoiceExtractionQueryVO.setEndDateTime(queryDateTime + " 23:59:59");
        } else {
            String startDateTime = pastDaysList.get(pastDaysList.size() - 1) + " 00:00:00";
            String endDateTime = pastDaysList.get(0) + " 23:59:59";
            invoiceExtractionQueryVO.setStartDateTime(startDateTime);
            invoiceExtractionQueryVO.setEndDateTime(endDateTime);
        }

        startPage();
        List<InvoiceExtractionStatisticsDTO> list = invoiceExtractionStatisticsService.findInvoiceExtractionStatisticsList(invoiceExtractionQueryVO);
        // List<InvoiceExtractionStatisticsDTO> resultList = new ArrayList<>();
        // List<InvoiceExtractionStatisticsDTO> returnDataList = new ArrayList<>();
        // buildResultList(list, resultList, returnDataList, pastDaysList, supplierId);
        return getDataTable(list);
    }

    private void buildResultList(List<InvoiceExtractionStatisticsDTO> list, List<InvoiceExtractionStatisticsDTO> resultList, List<InvoiceExtractionStatisticsDTO> returnDataList, List<String> pastDaysList, Long supplierId) throws ParseException {
        Map<String, List<InvoiceExtractionStatisticsDTO>> invoiceExtractionMap =
                list.stream().collect(Collectors.groupingBy(item -> {
                            if (Objects.isNull(supplierId)) {
                                return DateUtils.dateFormat(item.getLastUpdateTime(), DateUtils.DATE_PATTERN);
                            } else {
                                return item.getSupplierId() + "_" + DateUtils.dateFormat(item.getLastUpdateTime(), DateUtils.DATE_PATTERN);
                            }
                        }
                ));
        for (String key : invoiceExtractionMap.keySet()) {
            List<InvoiceExtractionStatisticsDTO> tempDataList = invoiceExtractionMap.get(key);
            InvoiceExtractionStatisticsDTO resultData = new InvoiceExtractionStatisticsDTO();
            Long allInvoiceCount = 0l;
            Long allRejectInvoiceCount = 0l;
            Long allCreditMemoInvoiceCount = 0l;
            Long allRejectCreditMemoInvoiceCount = 0l;
            Date lastUpdateTime = DateUtils.getNowDate();
            Long tempSupplierId = supplierId;
            String tempSupplierName = "";
            for (InvoiceExtractionStatisticsDTO tempInvoiceExtractionStatisticsDTO : tempDataList) {
                allInvoiceCount += tempInvoiceExtractionStatisticsDTO.getAllInvoiceCount();
                allRejectInvoiceCount += tempInvoiceExtractionStatisticsDTO.getAllRejectInvoiceCount();
                allCreditMemoInvoiceCount += tempInvoiceExtractionStatisticsDTO.getAllCreditMemoInvoiceCount();
                allRejectCreditMemoInvoiceCount += tempInvoiceExtractionStatisticsDTO.getAllRejectCreditMemoInvoiceCount();
                lastUpdateTime = tempInvoiceExtractionStatisticsDTO.getLastUpdateTime();
                tempSupplierId = tempInvoiceExtractionStatisticsDTO.getSupplierId();
                tempSupplierName = tempInvoiceExtractionStatisticsDTO.getSupplierName();
            }
            resultData.setSupplierId(tempSupplierId);
            resultData.setSupplierName(tempSupplierName);
            resultData.setLastUpdateTime(lastUpdateTime);
            resultData.setAllInvoiceCount(allInvoiceCount);
            resultData.setAllRejectInvoiceCount(allRejectInvoiceCount);
            resultData.setAllCreditMemoInvoiceCount(allCreditMemoInvoiceCount);
            resultData.setAllRejectCreditMemoInvoiceCount(allRejectCreditMemoInvoiceCount);
            resultList.add(resultData);
        }
        Collections.reverse(pastDaysList);
        for (String pastDay : pastDaysList) {
            InvoiceExtractionStatisticsDTO newInvoiceExtractionStatisticsDTO = new InvoiceExtractionStatisticsDTO();
            newInvoiceExtractionStatisticsDTO.setLastUpdateTime(DateUtils.parseDate(pastDay, DateUtils.DATE_PATTERN));
            newInvoiceExtractionStatisticsDTO.setAllRejectCreditMemoInvoiceCount(0L);
            newInvoiceExtractionStatisticsDTO.setAllRejectInvoiceCount(0l);
            newInvoiceExtractionStatisticsDTO.setAllInvoiceCount(0l);
            newInvoiceExtractionStatisticsDTO.setAllCreditMemoInvoiceCount(0l);
            for (InvoiceExtractionStatisticsDTO invoiceExtractionStatisticsDTO : resultList) {
                Date lastUpdateTime = invoiceExtractionStatisticsDTO.getLastUpdateTime();
                String lastUpdateTimeStr = DateUtils.dateFormat(lastUpdateTime, DateUtils.DATE_PATTERN);
                if (pastDay.equals(lastUpdateTimeStr)) {
                    newInvoiceExtractionStatisticsDTO = invoiceExtractionStatisticsDTO;
                    break;
                }
            }
            returnDataList.add(newInvoiceExtractionStatisticsDTO);
        }

    }

    private ArrayList<String> getLastDateTimeList(int intervals) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        for (int i = 0; i < intervals; i++) {
            pastDaysList.add(DateUtils.getPastDate(i));
        }
        return pastDaysList;
    }


    /**
     * 导出Invoice extraction statistics列表
     */
    // @HasPermissions("invoiceExtraction:invoiceExtraction:export")
    @Log(title = "Invoice extraction statistics", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InvoiceExtractionStatisticsDTO invoiceExtractionStatistics) {
        List<InvoiceExtractionStatisticsDTO> list = invoiceExtractionStatisticsService.selectInvoiceExtractionStatisticsList(invoiceExtractionStatistics);
        NewExcelUtil<InvoiceExtractionStatisticsDTO> util = new NewExcelUtil<InvoiceExtractionStatisticsDTO>(InvoiceExtractionStatisticsDTO.class);
        util.exportByFileType(response, list, "Invoice extraction statistics", getRequest().getHeader(Constants.FILE_TYPE));
    }

    /**
     * 获取Invoice extraction statistics详细信息
     */
    // @HasPermissions("invoiceExtraction:invoiceExtraction:query")
    @GetMapping(value = "/{id}")
    public ResultDTO getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(invoiceExtractionStatisticsService.selectInvoiceExtractionStatisticsById(id));
    }

    /**
     * 新增Invoice extraction statistics
     */
    @Log(title = "Invoice extraction statistics", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO add(@RequestBody InvoiceExtractionStatisticsDTO invoiceExtractionStatistics) {
        return toResultDTO(invoiceExtractionStatisticsService.insertInvoiceExtractionStatistics(invoiceExtractionStatistics), true);
    }

    @Log(title = "Insert Invoice extraction statistics", businessType = BusinessType.INSERT)
    @PostMapping("/insertStatistics")
    public ResultDTO insertInvoice(@RequestBody InvoiceExtractionStatisticsDTO invoiceExtractionStatistics) {
        return toResultDTO(invoiceExtractionStatisticsService.insertInvoiceExtractionStatisticsOfJob(invoiceExtractionStatistics), true);
    }

    /**
     * 修改Invoice extraction statistics
     */
    @Log(title = "Invoice extraction statistics", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO edit(@RequestBody InvoiceExtractionStatisticsDTO invoiceExtractionStatistics) {
        return toResultDTO(invoiceExtractionStatisticsService.updateInvoiceExtractionStatistics(invoiceExtractionStatistics), true);
    }

    /**
     * 删除Invoice extraction statistics
     */
    @HasPermissions("invoiceExtraction:invoiceExtraction:remove")
    @Log(title = "Invoice extraction statistics", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO remove(@PathVariable Long[] ids) {
        return toResultDTO(invoiceExtractionStatisticsService.deleteInvoiceExtractionStatisticsByIds(ids), true);
    }
}
