package com.yintu.rixing.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Author: mlf
 * @Date: 2021/1/19 19:53:02
 * @Version: 1.0
 */
public class FileSizeUtil {
    public static final double B = 1 * 1.0;
    public static final double KB = B * 1024.0;
    public static final double MB = KB * 1024.0;
    public static final double GB = MB * 1024.0;
    public static final String B1 = "B";
    public static final String KB1 = "KB";
    public static final String MB1 = "MB";
    public static final String GB1 = "GB";

    public static double getSize(long size) {
        BigDecimal bigDecimal;
        if (size > B && size < KB) {
            bigDecimal = new BigDecimal(size);
        } else if (size > KB && size < MB) {
            bigDecimal = new BigDecimal(size / KB);
        } else if (size > MB && size < GB) {
            bigDecimal = new BigDecimal(size / MB);
        } else {
            bigDecimal = new BigDecimal(size / GB);
        }
        return bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public static String getUnit(long size) {
        if (size > B && size < KB) {
            return B1;
        } else if (size > KB && size < MB) {
            return KB1;
        } else if (size > MB && size < GB) {
            return MB1;
        } else {
            return GB1;
        }
    }
}
