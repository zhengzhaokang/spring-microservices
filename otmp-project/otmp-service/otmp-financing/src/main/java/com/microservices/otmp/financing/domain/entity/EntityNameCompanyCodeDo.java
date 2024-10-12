package com.microservices.otmp.financing.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntityNameCompanyCodeDo extends EntityCompanyCodeDo{

    private String entityName;

}
