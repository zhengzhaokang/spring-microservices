package com.microservices.otmp.bank.common;

/**
 * @author lovefamily
 */
public class KafkaFactory {

    private KafkaFactory() {
        throw new IllegalStateException("Utility class");
    }

    /************消费工厂*****************/
    public static final String MSG_KAFKA_FACTORY = "msgKafkaFactory";
    public static final String ASYNC_TASK_KAFKA_FACTORY = "asyncTaskKafkaFactory";
    public static final String SYS_LOG_KAFKA_FACTORY = "sysLogKafkaFactory";
    public static final String BANK_INVOICE_SUBMISSION_FACTORY = "bankInvoiceSubmissionFactory";

    /************生产工厂*****************/
    public static final String MSG_KAFKA_PRODUCER_FACTORY = "msgKafkaProduceFactory";
    public static final String SEND_EMAIL_KAFKA_PRODUCER_FACTORY = "sendEmailKafkaProduceFactory";
    public static final String BANK_INVOICE_TRANSFER_PRODUCER_FACTORY = "bankInvoiceTransferProduceFactory";

}
