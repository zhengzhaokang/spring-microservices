package com.microservices.otmp.financing.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Table(name = "finance_invoice_ap")
@Data
public class FinanceInvoiceApDo extends BaseInvoiceInfo {
    private LocalDateTime confirmedMaturityDate;
}
