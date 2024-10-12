package com.microservices.otmp.financing.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Table(name="entity_company_code")
@Data
public class EntityCompanyCodeDo {

    @Id
    private Long id ;
    /** 实体代码 */
    private Long entityId ;
    /** 公司代码 */
    private String companyCode ;
    /** 数据状态，0：正常，1：禁用 */
    private String deleteFlag ;

    private LocalDateTime createTime;
    private String createBy;
    private LocalDateTime updateTime;
    private String updateBy;
}
