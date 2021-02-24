package com.yintu.rixing.util;

import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/28 18:26:01
 * @Version: 1.0
 */
public class PathIgnoringUtil {

    public static AntPathMatcher antPathMatcher = new AntPathMatcher();

    public static List<String> antPatterns;

    static {
        antPatterns = new ArrayList<>();
        antPatterns.add("/remote/**");
    }

    public static boolean antMatchers(HttpServletRequest request, String path) {
        for (String antPattern : antPatterns) {
            if (antPathMatcher.match(request.getContextPath() + antPattern, path)) {
                return true;
            }
        }
        return false;
    }

    public static boolean antMatchers(HttpServletRequest request, List<String> antPatterns, String path) {
        for (String antPattern : antPatterns) {
            if (antPathMatcher.match(request.getContextPath() + antPattern, path)) {
                return true;
            }
        }
        return false;
    }

}
