package com.microservices.otmp.financing.domain.vo.entity;

import com.microservices.otmp.financing.domain.vo.bank.BankVo;
import lombok.Data;

import java.util.List;

@Data
public class EntityVo {
    private String id;
    private String entityName;
    private List<String> currency;
    private String region;
    private String address;
    private String type;
    private List<BankVo> banks;
    private List<String> kycDocumentIds;
    private List<String> contractIds;
    private List<ErpEntityId> erpEntityIds;

    @Data
    public static class ErpEntityId{
        private String id;
        private String code;
    }
}
