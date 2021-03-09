package com.yintu.rixing.config.component;

import cn.hutool.core.date.DateUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: mlf
 * @Date: 2020/11/26 18:23
 * @Version: 1.0
 */
@Component
public class CustomConverter implements Converter<String, Date> {

    private static final int TIME_LENGTH = 8;

    private static final int DATE_LENGTH = 10;

    private static final int DATE_TIME_LENGTH = 19;

    @Override
    public Date convert(String source) {
        int length = source.length();
        if (length == 0) {
            return null;
        }
        // HH:mm:ss 8
        // yyyy-MM-dd 10
        // yyyy-MM-dd HH:mm:ss 19
        if (length == TIME_LENGTH) {
            return DateUtil.parseTime(source);
        } else if (length == DATE_LENGTH) {
            return DateUtil.parseDate(source);
        } else if (length == DATE_TIME_LENGTH) {
            return DateUtil.parseDateTime(source);
        }
        return DateUtil.parseUTC(source);
    }
}
