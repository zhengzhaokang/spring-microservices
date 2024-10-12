package com.microservices.otmp.masterdata.domain.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode()
@Accessors(chain = true)
public class TpMsCountryVo {

    private String code;

    private String name;

    /**
     * 排序序号
     */
    private Integer sort;

    public  TpMsCountryVo(String code,String name){
        this.code=code;
        this.name=name;
    }
}
