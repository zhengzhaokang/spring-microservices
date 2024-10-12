package com.microservices.otmp.financing.util;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;

import java.text.ParseException;
import java.util.Date;

public class TimeUtil {
    public static String convert(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        Date sourceDate;
        if (DateUtils.DATE_NO_PATTERN.length() == source.length()) {
            try {
                sourceDate = DateUtils.dateParse(source, DateUtils.DATE_NO_PATTERN);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            return DateUtils.dateFormat(sourceDate, DateUtils.DATE_PATTERN);
        } else {
            return source;
        }
    }
}
