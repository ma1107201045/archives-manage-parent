package com.yintu.rixing.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: mlf
 * @Date: 2021/1/6 20:06:44
 * @Version: 1.0
 */
public class AddressUtil {


    /**
     * @param request 请求对象
     * @return 返回请求地址
     */
    public static String getAddress(HttpServletRequest request) {
        int port = request.getServerPort();
        String portStr = port == 80 || port == 443 ? "" : ":" + port;
        return request.getScheme() + "://" + request.getServerName() + portStr + request.getContextPath();
    }
}
