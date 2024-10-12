package com.microservices.otmp.financing.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Table(name = "invoice_ap")
@Data
public class InvoiceApDo extends BaseInvoiceInfo{

}
