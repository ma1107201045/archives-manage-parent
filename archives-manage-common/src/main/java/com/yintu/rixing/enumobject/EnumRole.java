package com.yintu.rixing.enumobject;

/**
 * @Author: mlf
 * @Date: 2021/3/6 14:41:40
 * @Version: 1.0
 */
public enum EnumRole {
    /**
     * 不需要权限的角色
     */
    URL_NOT_AUTHORIZATION,
    /**
     * 需要权限的角色
     */
    URL_NEED_AUTHORIZATION
}


class Test {
    public static void main(String[] args) {
        System.out.println(EnumRole.URL_NEED_AUTHORIZATION.toString());
    }
}
