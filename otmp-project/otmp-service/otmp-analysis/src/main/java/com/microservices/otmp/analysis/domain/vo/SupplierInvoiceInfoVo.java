package com.microservices.otmp.analysis.domain.vo;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import com.microservices.otmp.common.utils.DateUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liuzy48
 */
public class SupplierInvoiceInfoVo extends BaseDTO {

    private Long id;

    private String invoiceUniqueId;

    @Excel(name = "Company Code", sort = 2)
    private String companyCode;

    @Excel(name = "Vendor Code", sort = 11)
    private String vendorCode;

    private String fiscalYear;

    @Excel(name = "Invoice", sort = 5)
    private String invoiceReference;

    private String invoiceAdditionalReference;

    private BigDecimal eccInvoiceNumber;

    private String eccInvoiceItem;

    private String invoiceIssueDate;

    @Excel(name = "Issue Date", sort = 6, dateFormat = "yyyy-MM-dd")
    private Date dInvoiceIssueDate;

    private String invoiceBaselineDate;

    private String invoiceDueDate;

    @Excel(name = "Due Date", sort = 7, dateFormat = "yyyy-MM-dd")
    private Date dInvoiceDueDate;

    @Excel(name = "Currency", sort = 10)
    private String invoiceCurrency;

    @Excel(name = "Amount", sort = 9, align = Excel.Align.RIGHT)
    private BigDecimal invoiceAmount;

    private String typeOfBilling;

    private String purchaseOrder;

    private String purchaseItem;

    private String purchaseDate;

    private String goodsDescription;

    private String shippingCompany;

    private String shipperName;

    private String transportDocumentReferenceNumber;

    private String transportDocumentDate;

    private String portOfLoading;

    private String portOfDischarge;

    private String vesselName;

    private String vesselMoNumber;

    private String enterDate;

    @Excel(name = "Invoice Type", sort = 4)
    private String invoiceType;

    private String invoiceEntryDate;

    private String invoicePayDate;

    @Excel(name = "Pay Date", sort = 12, dateFormat = "yyyy-MM-dd")
    private Date dInvoicePayDate;

    @Excel(name = "Maturity Date", sort = 8, dateFormat = "yyyy-MM-dd")
    private Date maturityDate;

    private String maturityDateStr;

    private String status;

    private String statusDescription;

    private Date crateTime;

    @Excel(name = "Entity Name", sort = 1)
    private String entityName;

    @Excel(name = "Supplier Name", sort = 3, width = 50)
    private String supplierName;

    public Long getId() {
        return id;
    }

    public SupplierInvoiceInfoVo setId(Long id) {
        this.id = id;
        return this;
    }

    public String getInvoiceUniqueId() {
        return invoiceUniqueId;
    }

    public SupplierInvoiceInfoVo setInvoiceUniqueId(String invoiceUniqueId) {
        this.invoiceUniqueId = invoiceUniqueId;
        return this;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public SupplierInvoiceInfoVo setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public SupplierInvoiceInfoVo setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
        return this;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public SupplierInvoiceInfoVo setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
        return this;
    }

    public String getInvoiceReference() {
        return invoiceReference;
    }

    public SupplierInvoiceInfoVo setInvoiceReference(String invoiceReference) {
        this.invoiceReference = invoiceReference;
        return this;
    }

    public String getInvoiceAdditionalReference() {
        return invoiceAdditionalReference;
    }

    public SupplierInvoiceInfoVo setInvoiceAdditionalReference(String invoiceAdditionalReference) {
        this.invoiceAdditionalReference = invoiceAdditionalReference;
        return this;
    }

    public BigDecimal getEccInvoiceNumber() {
        return eccInvoiceNumber;
    }

    public SupplierInvoiceInfoVo setEccInvoiceNumber(BigDecimal eccInvoiceNumber) {
        this.eccInvoiceNumber = eccInvoiceNumber;
        return this;
    }

    public String getEccInvoiceItem() {
        return eccInvoiceItem;
    }

    public SupplierInvoiceInfoVo setEccInvoiceItem(String eccInvoiceItem) {
        this.eccInvoiceItem = eccInvoiceItem;
        return this;
    }

    public String getInvoiceIssueDate() {
        return invoiceIssueDate;
    }

    public SupplierInvoiceInfoVo setInvoiceIssueDate(String invoiceIssueDate) {
        this.invoiceIssueDate = invoiceIssueDate;
        setdInvoiceIssueDate(DateUtils.parseDate(invoiceIssueDate));
        return this;
    }

    public Date getdInvoiceIssueDate() {
        return dInvoiceIssueDate;
    }

    public SupplierInvoiceInfoVo setdInvoiceIssueDate(Date dInvoiceIssueDate) {
        this.dInvoiceIssueDate = dInvoiceIssueDate;
        return this;
    }

    public String getInvoiceBaselineDate() {
        return invoiceBaselineDate;
    }

    public SupplierInvoiceInfoVo setInvoiceBaselineDate(String invoiceBaselineDate) {
        this.invoiceBaselineDate = invoiceBaselineDate;
        return this;
    }

    public String getInvoiceDueDate() {
        return invoiceDueDate;
    }

    public SupplierInvoiceInfoVo setInvoiceDueDate(String invoiceDueDate) {
        this.invoiceDueDate = invoiceDueDate;
        setdInvoiceDueDate(DateUtils.parseDate(invoiceDueDate));
        return this;
    }

    public Date getdInvoiceDueDate() {
        return dInvoiceDueDate;
    }

    public SupplierInvoiceInfoVo setdInvoiceDueDate(Date dInvoiceDueDate) {
        this.dInvoiceDueDate = dInvoiceDueDate;
        return this;
    }

    public String getInvoiceCurrency() {
        return invoiceCurrency;
    }

    public SupplierInvoiceInfoVo setInvoiceCurrency(String invoiceCurrency) {
        this.invoiceCurrency = invoiceCurrency;
        return this;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public SupplierInvoiceInfoVo setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
        return this;
    }

    public String getTypeOfBilling() {
        return typeOfBilling;
    }

    public SupplierInvoiceInfoVo setTypeOfBilling(String typeOfBilling) {
        this.typeOfBilling = typeOfBilling;
        return this;
    }

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public SupplierInvoiceInfoVo setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
        return this;
    }

    public String getPurchaseItem() {
        return purchaseItem;
    }

    public SupplierInvoiceInfoVo setPurchaseItem(String purchaseItem) {
        this.purchaseItem = purchaseItem;
        return this;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public SupplierInvoiceInfoVo setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public SupplierInvoiceInfoVo setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
        return this;
    }

    public String getShippingCompany() {
        return shippingCompany;
    }

    public SupplierInvoiceInfoVo setShippingCompany(String shippingCompany) {
        this.shippingCompany = shippingCompany;
        return this;
    }

    public String getShipperName() {
        return shipperName;
    }

    public SupplierInvoiceInfoVo setShipperName(String shipperName) {
        this.shipperName = shipperName;
        return this;
    }

    public String getTransportDocumentReferenceNumber() {
        return transportDocumentReferenceNumber;
    }

    public SupplierInvoiceInfoVo setTransportDocumentReferenceNumber(String transportDocumentReferenceNumber) {
        this.transportDocumentReferenceNumber = transportDocumentReferenceNumber;
        return this;
    }

    public String getTransportDocumentDate() {
        return transportDocumentDate;
    }

    public SupplierInvoiceInfoVo setTransportDocumentDate(String transportDocumentDate) {
        this.transportDocumentDate = transportDocumentDate;
        return this;
    }

    public String getPortOfLoading() {
        return portOfLoading;
    }

    public SupplierInvoiceInfoVo setPortOfLoading(String portOfLoading) {
        this.portOfLoading = portOfLoading;
        return this;
    }

    public String getPortOfDischarge() {
        return portOfDischarge;
    }

    public SupplierInvoiceInfoVo setPortOfDischarge(String portOfDischarge) {
        this.portOfDischarge = portOfDischarge;
        return this;
    }

    public String getVesselName() {
        return vesselName;
    }

    public SupplierInvoiceInfoVo setVesselName(String vesselName) {
        this.vesselName = vesselName;
        return this;
    }

    public String getVesselMoNumber() {
        return vesselMoNumber;
    }

    public SupplierInvoiceInfoVo setVesselMoNumber(String vesselMoNumber) {
        this.vesselMoNumber = vesselMoNumber;
        return this;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public SupplierInvoiceInfoVo setEnterDate(String enterDate) {
        this.enterDate = enterDate;
        return this;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public SupplierInvoiceInfoVo setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
        return this;
    }

    public String getInvoiceEntryDate() {
        return invoiceEntryDate;
    }

    public SupplierInvoiceInfoVo setInvoiceEntryDate(String invoiceEntryDate) {
        this.invoiceEntryDate = invoiceEntryDate;
        return this;
    }

    public String getInvoicePayDate() {
        return invoicePayDate;
    }

    public SupplierInvoiceInfoVo setInvoicePayDate(String invoicePayDate) {
        this.invoicePayDate = invoicePayDate;
        setdInvoicePayDate(DateUtils.parseDate(invoicePayDate));
        return this;
    }

    public Date getdInvoicePayDate() {
        return dInvoicePayDate;
    }

    public SupplierInvoiceInfoVo setdInvoicePayDate(Date dInvoicePayDate) {
        this.dInvoicePayDate = dInvoicePayDate;
        return this;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public SupplierInvoiceInfoVo setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
        setMaturityDateStr(DateUtils.dateFormat(maturityDate, null));
        return this;
    }

    public String getStatus() {
        return status;
    }

    public SupplierInvoiceInfoVo setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public SupplierInvoiceInfoVo setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
        return this;
    }

    public Date getCrateTime() {
        return crateTime;
    }

    public SupplierInvoiceInfoVo setCrateTime(Date crateTime) {
        this.crateTime = crateTime;
        return this;
    }

    public String getEntityName() {
        return entityName;
    }

    public SupplierInvoiceInfoVo setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public SupplierInvoiceInfoVo setSupplierName(String supplierName) {
        this.supplierName = supplierName;
        return this;
    }

    public String getMaturityDateStr() {
        return maturityDateStr;
    }

    public SupplierInvoiceInfoVo setMaturityDateStr(String maturityDateStr) {
        this.maturityDateStr = maturityDateStr;
        return this;
    }
}