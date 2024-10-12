package com.microservices.otmp.analysis.common;

import com.microservices.otmp.common.exception.base.BaseException;

/**
 * 报表异常
 *
 * @author db117
 * @since 2023/4/20
 */
public class ReportException extends BaseException {
    public ReportException(String defaultMessage) {
        super(defaultMessage);
    }
}
