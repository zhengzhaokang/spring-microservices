package com.microservices.otmp.bank.domain.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * created with IntelliJ IDEA.
 * user: licf
 * Date: 2023/8/10
 * time: 11:11
 * Description:
 */
@Data
public class BankFinancingDTO {

    private String fileName;
    //BNPP 对象数据
    private FhDTO fhDTO;
    private List<CnDTO> cnList;
    private List<InvDTO> invList;

    //DBS 对象数据
    private List<DbsDTO> dbsList;

    private Map<String, List<Map<String, Object>>> transferDataMap;


}
