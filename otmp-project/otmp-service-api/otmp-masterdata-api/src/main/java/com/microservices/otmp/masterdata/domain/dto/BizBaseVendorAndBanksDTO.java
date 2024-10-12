package com.microservices.otmp.masterdata.domain.dto;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * lgvm vendor对象 biz_base_vendor
 * 
 * @author lovefamily
 * @date 2023-03-21
 */
@Data
public class BizBaseVendorAndBanksDTO extends BizBaseVendorDTO
{

    private List<BizBaseVendorBankDTO> banks;

}
