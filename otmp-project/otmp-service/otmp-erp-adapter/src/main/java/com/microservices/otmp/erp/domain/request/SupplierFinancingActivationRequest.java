package com.microservices.otmp.erp.domain.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class SupplierFinancingActivationRequest {
	
	public static final String statusType1 = "1";
	public static final String statusType2 = "2";
	public static final String statusType3 = "3";
	public static final String statusType4 = "4";

	private String supplierCode;
	private String companyCode;
	private String status;
	private String supplierModel;
	private String bankVendor;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date validFrom;
	private String vendorName;
	private String targetSystem;

}
