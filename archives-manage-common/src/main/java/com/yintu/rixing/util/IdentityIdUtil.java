package com.yintu.rixing.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: mlf
 * @Date: 2021/2/1 10:22:29
 * @Version: 1.0
 */
public class IdentityIdUtil {

    public static final String IDENTITY_ID_NAME = "identityId";

    public static Integer get(HttpServletRequest request) {
        Object identityIdValue = request.getAttribute(IDENTITY_ID_NAME);
        if (identityIdValue instanceof String) {
            return Integer.valueOf((String) identityIdValue);
        }
        return null;
    }
}
