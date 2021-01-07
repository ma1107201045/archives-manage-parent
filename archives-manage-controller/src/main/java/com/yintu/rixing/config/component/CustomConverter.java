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
    @Override
    public Date convert(String source) {
        if (source.length() == 0) {
            return null;
        }
        // HH:mm:ss 8
        // yyyy-MM-dd 10
        // yyyy-MM-dd HH:mm:ss 19
        if (source.length() == 8)
            return DateUtil.parseTime(source);
        else if (source.length() == 10) {
            return DateUtil.parseDate(source);
        } else if (source.length() == 19) {
            return DateUtil.parseDateTime(source);
        }
        return DateUtil.parseUTC(source);
    }
}
