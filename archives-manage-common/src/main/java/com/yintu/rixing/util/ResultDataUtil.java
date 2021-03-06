package com.yintu.rixing.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: mlf
 * @Date: 2020/12/31 9:39:21
 * @Version: 1.0
 */
@Data
@ApiModel(value = "ResultDataUtil对象", description = "返回参数")
public class ResultDataUtil<T> {

    public static final Integer CODE_VALUE_200 = 200;
    public static final Integer CODE_VALUE_401 = 401;
    public static final Integer CODE_VALUE_666 = 666;
    public static final Integer CODE_VALUE_403 = 403;
    public static final Integer CODE_VALUE_404 = 404;
    public static final Integer CODE_VALUE_500 = 500;

    @ApiModelProperty("返回码（200:正常 401:未认证（未登录）403：未授权（未授权）404：请求接口不存在（404） 500：后台错误")
    private Integer code;

    @ApiModelProperty("返回数据（对象或者数组（{}or[]）,只有查询才有此key值）")
    private T data;

    @ApiModelProperty("返回信息(用于提示的消息)")
    private String message;


    public static <T> ResultDataUtil<T> ok(String message) {
        return ResultDataUtil.ok(message, null);

    }

    public static <T> ResultDataUtil<T> ok(String message, T data) {
        ResultDataUtil<T> responseData = new ResultDataUtil<>();
        responseData.setCode(CODE_VALUE_200);
        responseData.setData(data);
        responseData.setMessage(message);
        return responseData;
    }


    public static <T> ResultDataUtil<T> noAuthentication(String message) {
        return ResultDataUtil.noAuthentication(message, null);

    }


    public static <T> ResultDataUtil<T> noAuthentication(String message, T data) {
        ResultDataUtil<T> responseData = new ResultDataUtil<>();
        responseData.setCode(CODE_VALUE_401);
        responseData.setData(data);
        responseData.setMessage(message);
        return responseData;
    }

    public static <T> ResultDataUtil<T> noJwtAuthentication(String message) {
        return ResultDataUtil.noJwtAuthentication(message, null);

    }


    public static <T> ResultDataUtil<T> noJwtAuthentication(String message, T data) {
        ResultDataUtil<T> responseData = new ResultDataUtil<>();
        responseData.setCode(CODE_VALUE_666);
        responseData.setData(data);
        responseData.setMessage(message);
        return responseData;
    }

    public static <T> ResultDataUtil<T> noAuthorization(String message) {
        return ResultDataUtil.noAuthorization(message, null);

    }

    public static <T> ResultDataUtil<T> noAuthorization(String message, T data) {
        ResultDataUtil<T> responseData = new ResultDataUtil<>();
        responseData.setCode(CODE_VALUE_403);
        responseData.setData(data);
        responseData.setMessage(message);
        return responseData;
    }

    public static <T> ResultDataUtil<T> noPath(String message, T data) {
        ResultDataUtil<T> responseData = new ResultDataUtil<>();
        responseData.setCode(CODE_VALUE_404);
        responseData.setData(data);
        responseData.setMessage(message);
        return responseData;
    }

    public static <T> ResultDataUtil<T> noPath(String message) {
        return ResultDataUtil.noPath(message, null);

    }

    public static <T> ResultDataUtil<T> error(String message) {
        return ResultDataUtil.error(message, null);

    }

    public static <T> ResultDataUtil<T> error(String message, T data) {
        ResultDataUtil<T> responseData = new ResultDataUtil<>();
        responseData.setCode(CODE_VALUE_500);
        responseData.setData(data);
        responseData.setMessage(message);
        return responseData;
    }

}
