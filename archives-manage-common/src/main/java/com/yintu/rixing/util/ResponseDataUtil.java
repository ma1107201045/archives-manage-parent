package com.yintu.rixing.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 200 成功 500 失败 401未登录 403 未授权
 * @Version: 1.0
 */
public class ResponseDataUtil {


    private static final String CODE = "code";
    private static final Integer CODE_VALUE_200 = 200;
    private static final Integer CODE_VALUE_500 = 500;
    private static final Integer CODE_VALUE_401 = 401;
    private static final Integer CODE_VALUE_403 = 403;
    private static final String MESSAGE = "message";
    private static final String DATA = "data";

    public static Map<String, Object> ok(String message) {
        Map<String, Object> returnData = new HashMap<>();
        returnData.put(CODE, CODE_VALUE_200);
        returnData.put(MESSAGE, message);
        return returnData;
    }

    public static Map<String, Object> ok(String message, Object data) {
        Map<String, Object> returnData = new HashMap<>();
        returnData.put(CODE, CODE_VALUE_200);
        returnData.put(MESSAGE, message);
        returnData.put(DATA, data);
        return returnData;
    }

    public static Map<String, Object> ok(String message, Object data, Map<String, Object> map) {
        Map<String, Object> returnData = new HashMap<>();
        returnData.put(CODE, CODE_VALUE_200);
        returnData.put(MESSAGE, message);
        returnData.put(DATA, data);
        returnData.putAll(map);
        return returnData;
    }

    public static Map<String, Object> error(String message) {
        Map<String, Object> returnData = new HashMap<>();
        returnData.put(CODE, CODE_VALUE_500);
        returnData.put(MESSAGE, message);
        return returnData;
    }

    public static Map<String, Object> error(String message, Object data) {
        Map<String, Object> returnData = new HashMap<>();
        returnData.put(CODE, CODE_VALUE_500);
        returnData.put(MESSAGE, message);
        returnData.put(DATA, data);
        return returnData;
    }

    public static Map<String, Object> error(String message, Object data, Map<String, Object> map) {
        Map<String, Object> returnData = new HashMap<>();
        returnData.put(CODE, CODE_VALUE_500);
        returnData.put(MESSAGE, message);
        returnData.put(DATA, data);
        returnData.putAll(map);
        return returnData;
    }

    public static Map<String, Object> noLogin(String message) {
        Map<String, Object> returnData = new HashMap<>();
        returnData.put(CODE, CODE_VALUE_401);
        returnData.put(MESSAGE, message);
        return returnData;
    }

    public static Map<String, Object> noLogin(String message, Object data) {
        Map<String, Object> returnData = new HashMap<>();
        returnData.put(CODE, CODE_VALUE_401);
        returnData.put(MESSAGE, message);
        returnData.put(DATA, data);
        return returnData;
    }

    public static Map<String, Object> noLogin(String message, Map<String, Object> map) {
        Map<String, Object> returnData = new HashMap<>();
        returnData.put(CODE, CODE_VALUE_401);
        returnData.put(MESSAGE, message);
        returnData.putAll(map);
        return returnData;
    }

    public static Map<String, Object> noAuthorize(String message) {
        Map<String, Object> returnData = new HashMap<>();
        returnData.put(CODE, CODE_VALUE_403);
        returnData.put(MESSAGE, message);
        return returnData;
    }

    public static Map<String, Object> noAuthorize(String message, Object data) {
        Map<String, Object> returnData = new HashMap<>();
        returnData.put(CODE, CODE_VALUE_403);
        returnData.put(MESSAGE, message);
        returnData.put(DATA, data);
        return returnData;
    }

    public static Map<String, Object> noAuthorize(String message, Map<String, Object> map) {
        Map<String, Object> returnData = new HashMap<>();
        returnData.put(CODE, CODE_VALUE_403);
        returnData.put(MESSAGE, message);
        returnData.putAll(map);
        return returnData;
    }

}
