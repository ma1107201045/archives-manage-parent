package com.yintu.rixing.util;

/**
 * @Author: mlf
 * @Date: 2021/1/28 14:54:47
 * @Version: 1.0
 */
public class ArchivesNumGenerateUtil {

    private static final String format = "%s-%s-%s-%s";

    public static String get(Object... args) {
        return String.format(format, args);
    }
}
