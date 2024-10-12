package com.microservices.otmp.bank.util;

import com.microservices.otmp.bank.domain.constant.BankBNPP;
import com.microservices.otmp.bank.domain.constant.BankDBS;
import com.microservices.otmp.bank.domain.dto.BankFinancingDTO;
import com.microservices.otmp.bank.domain.dto.DbsDTO;
import com.microservices.otmp.bank.domain.dto.FhDTO;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.bank.domain.constant.BankBNPP;
import com.microservices.otmp.bank.domain.constant.BankDBS;
import com.microservices.otmp.bank.domain.dto.BankFinancingDTO;
import com.microservices.otmp.bank.domain.dto.DbsDTO;
import com.microservices.otmp.bank.domain.dto.FhDTO;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;


public class XlsxtoCSV {

    //根据数据生成 csv 文件
    //读取 csv 文件
    public static void xlsx(File inputFile, File outputFile) {
        StringBuffer data = new StringBuffer();
        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            FileInputStream fis = new FileInputStream(inputFile);
            XSSFWorkbook workbook = null;

            String ext = FilenameUtils.getExtension(inputFile.toString());

            if (ext.equalsIgnoreCase("xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (ext.equalsIgnoreCase("xls")) {
                workbook = new XSSFWorkbook(fis);
            }
            int numberOfSheets = workbook.getNumberOfSheets();
            XSSFRow row;
            XSSFCell cell;
            for (int i = 0; i < numberOfSheets; i++) {
                XSSFSheet sheet = workbook.getSheetAt(0);
                Iterator rowIterator = sheet.iterator();

                while (rowIterator.hasNext()) {
                    row = (XSSFRow) rowIterator.next();
                    Iterator cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {

                        cell = (XSSFCell) cellIterator.next();

                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_BOOLEAN:
                                data.append(cell.getBooleanCellValue() + ",");

                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                data.append(cell.getNumericCellValue() + ",");

                                break;
                            case Cell.CELL_TYPE_STRING:
                                data.append(cell.getStringCellValue() + ",");
                                break;

                            case Cell.CELL_TYPE_BLANK:
                                data.append("" + ",");
                                break;
                            default:
                                data.append(cell + ",");

                        }
                    }
                    data.append('\n'); // appending new line after each row
                }

            }
            fos.write(data.toString().getBytes());
            fos.close();
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * csv 文件列分隔符
     */
    private static final String CSV_COLUMN_SEPARATOR = ",";
    /**
     * csv 文件行分隔符
     */
    private static final String CSV_ROW_SEPARATOR = System.lineSeparator();

    public static StringBuffer assembleBNPP(BankFinancingDTO bankUpload) {
        StringBuffer buf = new StringBuffer();
        FhDTO fhDTO = bankUpload.getFhDTO();
        // 组装表头
        buf.append(fhDTO.getIdentifier()).append(CSV_COLUMN_SEPARATOR)
                .append(fhDTO.getSenderId()).append(CSV_COLUMN_SEPARATOR)
                .append(fhDTO.getFileName()).append(CSV_COLUMN_SEPARATOR)
                .append(DateUtils.dateFormat(fhDTO.getFileCreationDate(), BankBNPP.DATE_FORMAT)).append(CSV_COLUMN_SEPARATOR)
                .append(fhDTO.getMicroservicesBatchNumber());
        buf.append(CSV_ROW_SEPARATOR);
        // 组装内容
        bankUpload.getInvList().forEach(invDTO -> {
            tidyUp(buf, invDTO.getIdentifier(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, invDTO.getErpId(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, invDTO.getInvoiceReference(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, invDTO.getInvoiceIssueDate(), BankBNPP.DATE_FORMAT, CSV_COLUMN_SEPARATOR);
            tidyUp(buf, invDTO.getInvoiceDueDate(), BankBNPP.DATE_FORMAT, CSV_COLUMN_SEPARATOR);
            buf.append(invDTO.getInvoiceCurrency()).append(CSV_COLUMN_SEPARATOR).append(invDTO.getInvoiceAmount()).append(CSV_COLUMN_SEPARATOR);
            tidyUp(buf, invDTO.getAdditionalReference1(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, invDTO.getAdditionalReference2(), BankBNPP.DATE_FORMAT, CSV_COLUMN_SEPARATOR);
            tidyUp(buf, invDTO.getAdditionalReference3(), CSV_ROW_SEPARATOR);
        });

        bankUpload.getCnList().forEach(cnDTO -> {
            tidyUp(buf, cnDTO.getIdentifier(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, cnDTO.getErpId(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, cnDTO.getCreditNoteReference(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, cnDTO.getCreditNoteIssueDate(), BankBNPP.DATE_FORMAT, CSV_COLUMN_SEPARATOR);
            tidyUp(buf, cnDTO.getCreditNoteEffectiveDate(), BankBNPP.DATE_FORMAT, CSV_COLUMN_SEPARATOR);
            buf.append(cnDTO.getCreditNoteCurrency()).append(CSV_COLUMN_SEPARATOR).append(cnDTO.getCreditNoteAmount()).append(CSV_COLUMN_SEPARATOR);
            tidyUp(buf, cnDTO.getAdditionalReference1(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, cnDTO.getAdditionalReference2(), BankBNPP.DATE_FORMAT, CSV_COLUMN_SEPARATOR);
            tidyUp(buf, cnDTO.getAdditionalReference3(), CSV_ROW_SEPARATOR);
        });
        return buf;
    }


    public static StringBuffer assembleDBS(List<DbsDTO> dbsList) {
        StringBuffer buf = new StringBuffer();
        // 组装表头
        buf.append(BankDBS.ONE_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.TWO_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.THREE_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.FOUR_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.FIVE_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.SIX_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.SEVEN_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.EIGHT_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.NINE_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.TEN_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.ELEVEN_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.TWELVE_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.THIRTEEN_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.FOURTEEN_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.FIFTEEN_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.SIXTEEN_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.SEVENTEEN_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.EIGHTEEN_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.NINETEEN_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.TWENTY_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.TWENTY_ONE_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.TWENTY_TWO_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.TWENTY_THREE_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.TWENTY_FOUR_HEAD).append(CSV_COLUMN_SEPARATOR)
                .append(BankDBS.TWENTY_FIVE_HEAD).append(CSV_ROW_SEPARATOR);
        // 组装内容
        for (int i = 0; i < dbsList.size(); i++) {
            DbsDTO bank = dbsList.get(i);
            buf.append((i + 1)).append(CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getSeller(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getBuyer(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getInstrumentType(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getInstrumentNumber(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getInstrumentDate(), BankDBS.DATE_FORMAT, CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getInstrumentCurrency(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getInstrumentAmount(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getInstrumentDueDate(), BankDBS.DATE_FORMAT, CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getPaymentDueDate(), BankDBS.DATE_FORMAT, CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getCorrespondingPONumber(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getTransportDocumentReferenceNumber(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getTransportDocumentDate(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getPortOfLoading(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getPortOfdischarge(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getGoods(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getNameOfShipper(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getNameOfShippingCompany(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getNameOfVessel(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, BankDBS.SF, CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getFinanceAmount(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getSellerFees(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getBuyerName(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getSellerName(), CSV_COLUMN_SEPARATOR);
            tidyUp(buf, bank.getExternalRefNo(), CSV_ROW_SEPARATOR);
        }

        return buf;
    }

    private static void tidyUp(StringBuffer buf, String name, String separator) {
        if (StringUtils.isNotEmpty(name)) {
            buf.append(name);
        }
        buf.append(separator);
    }

    private static void tidyUp(StringBuffer buf, Date date, String format, String separator) {
        if (null != date) {
            buf.append(DateUtils.dateFormat(date, format));
        }
        buf.append(separator);
    }

    public static StringBuffer buildCsvData(Map<String, List<Map<String, Object>>> data, boolean isHeader) {
        StringBuffer csvContent = new StringBuffer();

        // 遍历Map对象的键值对
        for (Map.Entry<String, List<Map<String, Object>>> entry : data.entrySet()) {
            //String columnName = entry.getKey();
            //csvContent.append(columnName).append(",");

            List<Map<String, Object>> values = entry.getValue();
            if (isHeader) {
                Map<String, Object> firstData = values.get(0);
                Set<String> set = firstData.keySet();
                for (String val : set) {
                    csvContent.append(val).append(",");
                }
                csvContent=new StringBuffer(csvContent.substring(0,csvContent.length()-1));
                csvContent.append("\n");
            }
            // 遍历值列表
            for (Map<String, Object> value : values) {
                // 遍历Map对象的键值对
                for (Map.Entry<String, Object> valueEntry : value.entrySet()) {
                    Object cellValue = valueEntry.getValue();
                    if (Objects.isNull(cellValue)) {
                        csvContent.append("").append(",");
                    } else {
                        csvContent.append(cellValue.toString()).append(",");

                    }
                }
                csvContent=new StringBuffer(csvContent.substring(0,csvContent.length()-1));
                csvContent.append("\n");
            }
        }
        //writeCsv("D:\\test\\request\\test001.csv", csvContent);
        return csvContent;

    }

    public static void writeCsv(String filePath,String fileName, StringBuffer csvContent) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();//创建成功   mkdirs可以在不存在的目录下创建目录，并可以创建多级
            }
            FileWriter writer = new FileWriter(filePath+"/"+fileName);
            writer.write(csvContent.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}