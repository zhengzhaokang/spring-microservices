package com.microservices.otmp.financing.domain.dto;

import lombok.Data;

import java.util.Map;

@Data
public class EntityCodeIdDto {

    private Map<String,String> codeNameMap;
    private Map<String,Long> codeIdMap;
}
