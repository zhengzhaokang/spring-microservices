package com.microservices.otmp.analysis.controller;

import com.microservices.otmp.analysis.domain.dto.BankInterestRateTrendDTO;
import com.microservices.otmp.analysis.domain.dto.BankInterestRateTrendVO;
import com.microservices.otmp.analysis.domain.dto.BankInvoiceBatchStatisticsNumDTO;
import com.microservices.otmp.analysis.domain.dto.InvoiceExtractionStatisticsDTO;
import com.microservices.otmp.analysis.service.IBankTransferTaskInfoService;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.BigDecimalUtil;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * BankInvoiceBatch statisticsController
 *
 * @author lovefamily
 * @date 2023-10-30
 */
@RestController
@RequestMapping("/bankInvoiceBatch")
public class BankInvoiceBatchStatisticsController extends BaseController {

    @Autowired
    private IBankTransferTaskInfoService bankTransferTaskInfoService;

    /**
     * 查询bankInvoiceBatch列表
     */
    @GetMapping("/list")
    public TableDataInfo list(InvoiceExtractionStatisticsDTO invoiceExtractionStatistics) {
        startPage();
        List<InvoiceExtractionStatisticsDTO> list = new ArrayList<>();
        return getDataTable(list);
    }

    @GetMapping("/findBankInvoiceBatchCount")
    public ResultDTO findInvoiceCount(BankInvoiceBatchStatisticsNumDTO bankInvoiceBatchStatisticsNumDTO) throws ParseException {
        String queryDateType = bankInvoiceBatchStatisticsNumDTO.getQueryDateType();
        int intervals = 7;
        if (StringUtils.isNotEmpty(queryDateType)) {
            intervals = Integer.valueOf(queryDateType);
        }
        List<String> pastDaysList = getLastDateTimeList(intervals);
        String startDateTime = pastDaysList.get(pastDaysList.size() - 1) + " 00:00:00";
        String endDateTime = pastDaysList.get(0) + " 23:59:59";
        bankInvoiceBatchStatisticsNumDTO.setStartDateTime(startDateTime);
        bankInvoiceBatchStatisticsNumDTO.setEndDateTime(endDateTime);
        List<Map<String, Object>> bankInvoiceBatchStatisticsMap = bankTransferTaskInfoService.findBankInvoiceBatchCount(bankInvoiceBatchStatisticsNumDTO);
        List<BankInvoiceBatchStatisticsNumDTO> resultList = new ArrayList<>();
        List<BankInvoiceBatchStatisticsNumDTO> returnDataList = new ArrayList<>();
        buildResultList(bankInvoiceBatchStatisticsMap, resultList, returnDataList, pastDaysList, bankInvoiceBatchStatisticsNumDTO);
        return ResultDTO.success(returnDataList);
    }

    @GetMapping("/findBankInvoiceBatchCountPageList")
    public TableDataInfo findBankInvoiceBatchCountPageList(BankInvoiceBatchStatisticsNumDTO bankInvoiceBatchStatisticsNumDTO) throws ParseException {
        String queryDateTime = bankInvoiceBatchStatisticsNumDTO.getQueryDateTime();
        String queryDateType = bankInvoiceBatchStatisticsNumDTO.getQueryDateType();
        int intervals = 7;
        if (StringUtils.isNotEmpty(queryDateType)) {
            intervals = Integer.valueOf(queryDateType);
        }
        List<String> pastDaysList = getLastDateTimeList(intervals);
        if (StringUtils.isNotEmpty(queryDateTime)) {
            bankInvoiceBatchStatisticsNumDTO.setStartDateTime(queryDateTime + " 00:00:00");
            bankInvoiceBatchStatisticsNumDTO.setEndDateTime(queryDateTime + " 23:59:59");
        } else {
            String startDateTime = pastDaysList.get(pastDaysList.size() - 1) + " 00:00:00";
            String endDateTime = pastDaysList.get(0) + " 23:59:59";
            bankInvoiceBatchStatisticsNumDTO.setStartDateTime(startDateTime);
            bankInvoiceBatchStatisticsNumDTO.setEndDateTime(endDateTime);
        }
        startPage();
        List<Map<String, Object>> bankInvoiceBatchStatisticsMap = bankTransferTaskInfoService.findBankInvoiceBatchCount(bankInvoiceBatchStatisticsNumDTO);
        List<BankInvoiceBatchStatisticsNumDTO> resultList = new ArrayList<>();
        List<BankInvoiceBatchStatisticsNumDTO> returnDataList = new ArrayList<>();
        buildResultList(bankInvoiceBatchStatisticsMap, resultList, returnDataList, pastDaysList, bankInvoiceBatchStatisticsNumDTO);
        return getDataTable(returnDataList);
    }


    /**
     * 导出bankInvoiceBatch列表
     */
    @Log(title = "Bank Invoice Batch Statistics Num List", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BankInvoiceBatchStatisticsNumDTO bankInvoiceBatchStatisticsNumDTO) {
        List<BankInvoiceBatchStatisticsNumDTO> list = new ArrayList<>();
        NewExcelUtil<BankInvoiceBatchStatisticsNumDTO> util = new NewExcelUtil<>(BankInvoiceBatchStatisticsNumDTO.class);
        util.exportByFileType(response, list, "Bank Invoice Batch Statistics Number",getRequest().getHeader(Constants.FILE_TYPE));
    }

    @GetMapping("/findInterestRateTrend")
    public ResultDTO findInterestRateTrend(BankInterestRateTrendDTO bankInterestRateTrendDTO) throws ParseException {
        String bankId = bankInterestRateTrendDTO.getBankId();
        String queryDateYear = bankInterestRateTrendDTO.getLastUpdateTime();
        Calendar calendar = Calendar.getInstance();
        int cyear = calendar.get(Calendar.YEAR);
        int inputYear = StringUtils.isEmpty(queryDateYear) ? cyear : Integer.valueOf(queryDateYear);
        List<String> pastMonthsList = DateUtils.get12Month(inputYear);
        Collections.reverse(pastMonthsList);
         String  startDateTime = pastMonthsList.get(pastMonthsList.size() - 1) + "-01 00:00:00";
        String endDateTime = pastMonthsList.get(0) + "-31 23:59:59";
        if (cyear == inputYear) {
            endDateTime = DateUtils.dateFormat(new Date(), DateUtils.DATE_TIME_PATTERN);
        }
        bankInterestRateTrendDTO.setStartDateTime(startDateTime);
        bankInterestRateTrendDTO.setEndDateTime(endDateTime);
        List<BankInterestRateTrendDTO> interestRateTrendDTOList = bankTransferTaskInfoService.findBankInvoiceBatchList(bankInterestRateTrendDTO);
        List<BankInterestRateTrendDTO> returnDataList = new ArrayList<>();
        buildInterestRateTrendResultList(interestRateTrendDTOList, returnDataList, pastMonthsList, bankInterestRateTrendDTO);
        Collections.reverse(returnDataList);
        return ResultDTO.success(returnDataList);
    }

    @GetMapping("/findInterestRateTrendPageList")
    public TableDataInfo findInterestRateTrendPageList(BankInterestRateTrendVO bankInterestRateTrendVO) throws ParseException {
        String queryDateMonth = bankInterestRateTrendVO.getQueryDateTime();
        String queryDateYear = bankInterestRateTrendVO.getLastUpdateTime();

        Calendar calendar = Calendar.getInstance();
        int cyear = calendar.get(Calendar.YEAR);
        int inputYear = StringUtils.isEmpty(queryDateYear) ? cyear : Integer.valueOf(queryDateYear);
        List<String> pastMonthsList = DateUtils.get12Month(inputYear);
        Collections.reverse(pastMonthsList);

        String beginTime = pastMonthsList.get(pastMonthsList.size() - 1) + "-01 00:00:00";
        String endTime = pastMonthsList.get(0) + "-31 23:59:59";
        if (cyear == inputYear) {
            endTime = DateUtils.dateFormat(new Date(), DateUtils.DATE_TIME_PATTERN);
        }
        //上个月最后一天23点59分59秒的时间
        // String beginTime = DateUtils.getLastMonthdateStr();
        //  String endTime = DateUtils.dateFormat(new Date(), DateUtils.DATE_TIME_PATTERN);

        if (StringUtils.isNotEmpty(queryDateMonth)) {
            beginTime = queryDateMonth + "-01 00:00:00";
            endTime = DateUtils.dateFormat(DateUtils.getLastDayOfMonth(DateUtils.dateParse(queryDateMonth + "-01 23:59:59", DateUtils.DATE_TIME_PATTERN)), DateUtils.DATE_TIME_PATTERN);
        }
        bankInterestRateTrendVO.setBeginTime(beginTime);
        bankInterestRateTrendVO.setEndTime(endTime);
        List<BankInterestRateTrendVO> returnDataList = new ArrayList<>();
        startPage();
        returnDataList = bankTransferTaskInfoService.findBankInvoiceBatchPageList(bankInterestRateTrendVO);

        return getDataTable(returnDataList);
    }

    /**
     * 导出InterestRateTrend列表
     */
    @Log(title = "Export InterestRateTrend List", businessType = BusinessType.EXPORT)
    @PostMapping("/exportInterestRateTrendList")
    public void exportInterestRateTrendList(HttpServletResponse response, BankInterestRateTrendVO bankInterestRateTrendVO) {
        List<BankInterestRateTrendVO> list = new ArrayList<>();
        list = bankTransferTaskInfoService.findBankInvoiceBatchPageList(bankInterestRateTrendVO);
        NewExcelUtil<BankInterestRateTrendVO> util = new NewExcelUtil<>(BankInterestRateTrendVO.class);
        util.exportByFileType(response, list, "InterestRateTrend Data",getRequest().getHeader(Constants.FILE_TYPE));
    }

    private void buildInterestRateTrendResultList(List<BankInterestRateTrendDTO> list,
                                                  List<BankInterestRateTrendDTO> returnDataList, List<String> pastMonthsList,
                                                  BankInterestRateTrendDTO bankInterestRateTrendDTO) {
        for (String pastDay : pastMonthsList) {
            BankInterestRateTrendDTO newBankInterestRateTrendDTO = new BankInterestRateTrendDTO();
            newBankInterestRateTrendDTO.setLastUpdateTime(pastDay);
            newBankInterestRateTrendDTO.setInterestRate(new BigDecimal(0.0000));
            newBankInterestRateTrendDTO.setInterestAmount(new BigDecimal(0.00));
            for (BankInterestRateTrendDTO tempBankInterestRateTrendDTO : list) {
                if (null == tempBankInterestRateTrendDTO.getInterestRate()) {
                    tempBankInterestRateTrendDTO.setInterestRate(new BigDecimal(0.00));
                }
                if (null == tempBankInterestRateTrendDTO.getInterestAmount()) {
                    tempBankInterestRateTrendDTO.setInterestAmount(new BigDecimal(0.0000));
                }
                if (pastDay.equals(tempBankInterestRateTrendDTO.getLastUpdateTime())) {
                    tempBankInterestRateTrendDTO.setInterestRate(BigDecimalUtil.formatPrecision(tempBankInterestRateTrendDTO.getInterestRate(), 4, null));
                    newBankInterestRateTrendDTO = tempBankInterestRateTrendDTO;
                    break;
                }

            }
            returnDataList.add(newBankInterestRateTrendDTO);
        }

    }


    private void buildResultList(List<Map<String, Object>> list, List<BankInvoiceBatchStatisticsNumDTO> resultList,
                                 List<BankInvoiceBatchStatisticsNumDTO> returnDataList, List<String> pastDaysList,
                                 BankInvoiceBatchStatisticsNumDTO bankInvoiceBatchStatisticsNumDTO) throws ParseException {

        Long supplierId = bankInvoiceBatchStatisticsNumDTO.getSupplierId();
        String bankId = bankInvoiceBatchStatisticsNumDTO.getBankId() == null ? "" : bankInvoiceBatchStatisticsNumDTO.getBankId() + "";
        Long entityId = bankInvoiceBatchStatisticsNumDTO.getEntityId();
        Map<String, List<Map<String, Object>>> bankInvoiceBatchMap = list.stream()
                .collect(Collectors.groupingBy(new Function<Map<String, Object>, String>() {
                                                   @Override
                                                   public String apply(Map<String, Object> invoiceBatchMap) {
                                                       if (StringUtils.isEmpty(bankId) && Objects.isNull(supplierId) && Objects.isNull(entityId)) {
                                                           return invoiceBatchMap.get("update_time") + "";
                                                       } else if (StringUtils.isEmpty(bankId) && Objects.isNull(supplierId)) {
                                                           return invoiceBatchMap.get("entity_id") + "_" + invoiceBatchMap.get("update_time");
                                                       } else if (StringUtils.isEmpty(bankId) && Objects.isNull(entityId)) {
                                                           return invoiceBatchMap.get("supplier_id") + "_" + invoiceBatchMap.get("update_time");
                                                       } else if (Objects.isNull(supplierId) && Objects.isNull(entityId)) {
                                                           return invoiceBatchMap.get("bank_id") + "_" + invoiceBatchMap.get("update_time");
                                                       } else if (StringUtils.isEmpty(bankId)) {
                                                           return invoiceBatchMap.get("supplier_id") + "_" + invoiceBatchMap.get("entity_id") + "_" + invoiceBatchMap.get("update_time");
                                                       } else if (Objects.isNull(supplierId)) {
                                                           return invoiceBatchMap.get("bank_id") + "_" + invoiceBatchMap.get("entity_id") + "_" + invoiceBatchMap.get("update_time");
                                                       } else if (Objects.isNull(entityId)) {
                                                           return invoiceBatchMap.get("bank_id") + "_" + invoiceBatchMap.get("supplier_id") + "_" + invoiceBatchMap.get("update_time");
                                                       } else {
                                                           return invoiceBatchMap.get("bank_id") + "_" + invoiceBatchMap.get("supplier_id") + "_" + invoiceBatchMap.get("entity_id") + "_" + invoiceBatchMap.get("update_time");
                                                       }
                                                   }
                                               }
                        )
                );


        for (String key : bankInvoiceBatchMap.keySet()) {
            List<Map<String, Object>> tempDataList = bankInvoiceBatchMap.get(key);
            BankInvoiceBatchStatisticsNumDTO newBankInvoiceBatchStatisticsNumDTO = new BankInvoiceBatchStatisticsNumDTO();
            Long batchCount = 0l;
            Long submittedNum = 0l;
            Long rejectedNum = 0l;
            Long acceptedNum = 0l;
            for (Map<String, Object> tempMap : tempDataList) {
                String updateTime = (String) tempMap.get("update_time");
                String status = (String) tempMap.get("status");
                batchCount = (Long) tempMap.get("batch_count");

                if ("Financing".equals(status)) {
                    submittedNum+=batchCount;
                    newBankInvoiceBatchStatisticsNumDTO.setSubmittedNum(submittedNum.intValue());
                    newBankInvoiceBatchStatisticsNumDTO.setLastUpdateTime(updateTime);
                }
                if ("Rejected".equals(status)) {
                    rejectedNum+=batchCount;
                    newBankInvoiceBatchStatisticsNumDTO.setRejectedNum(rejectedNum.intValue());
                    newBankInvoiceBatchStatisticsNumDTO.setLastUpdateTime(updateTime);
                }
                if ("Financed".equals(status)) {
                    acceptedNum+=batchCount;
                    newBankInvoiceBatchStatisticsNumDTO.setAcceptedNum(acceptedNum.intValue());
                    newBankInvoiceBatchStatisticsNumDTO.setLastUpdateTime(updateTime);
                }

            }
            resultList.add(newBankInvoiceBatchStatisticsNumDTO);

        }

        Collections.reverse(pastDaysList);
        for (String pastDay : pastDaysList) {
            BankInvoiceBatchStatisticsNumDTO newBankInvoiceBatchStatisticsNumDTO = new BankInvoiceBatchStatisticsNumDTO();
            newBankInvoiceBatchStatisticsNumDTO.setLastUpdateTime(pastDay);
            newBankInvoiceBatchStatisticsNumDTO.setRejectedNum(0);
            newBankInvoiceBatchStatisticsNumDTO.setAcceptedNum(0);
            newBankInvoiceBatchStatisticsNumDTO.setSubmittedNum(0);
            for (BankInvoiceBatchStatisticsNumDTO invoiceBatchStatisticsNumDTO : resultList) {
                if (null == invoiceBatchStatisticsNumDTO.getSubmittedNum()) {
                    invoiceBatchStatisticsNumDTO.setSubmittedNum(0);
                }
                if (null == invoiceBatchStatisticsNumDTO.getAcceptedNum()) {
                    invoiceBatchStatisticsNumDTO.setAcceptedNum(0);
                }
                if (null == invoiceBatchStatisticsNumDTO.getRejectedNum()) {
                    invoiceBatchStatisticsNumDTO.setRejectedNum(0);
                }
                if (pastDay.equals(invoiceBatchStatisticsNumDTO.getLastUpdateTime())) {
                    newBankInvoiceBatchStatisticsNumDTO = invoiceBatchStatisticsNumDTO;
                    break;
                }

            }
            returnDataList.add(newBankInvoiceBatchStatisticsNumDTO);
        }

    }


    private ArrayList<String> getLastDateTimeList(int intervals) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        for (int i = 0; i < intervals; i++) {
            pastDaysList.add(DateUtils.getPastDate(i));
        }
        return pastDaysList;
    }


}
