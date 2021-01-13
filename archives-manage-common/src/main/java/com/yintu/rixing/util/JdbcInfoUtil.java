package com.yintu.rixing.util;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: mlf
 * @Date: 2021/1/13 14:21:43
 * @Version: 1.0
 */

public class JdbcInfoUtil {


    public static JdbcInfo get() {
        return SpringContextUtil.getBean(JdbcInfo.class);
    }

    public static String getHost() {
        return get().getHost();
    }

    public static int getPort() {
        return get().getPort();
    }

    public static String getDatabase() {
        return get().getDatabase();
    }

    public static String getUsername() {
        return get().getUsername();
    }

    public static String getPassword() {
        return get().getPassword();
    }

}

@Component
class JdbcInfo {

    @Value("${spring.datasource.druid.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.druid.url}")
    private String url;

    private String host;

    private int port;

    private String database;
    @Value("${spring.datasource.druid.username}")
    private String username;
    @Value("${spring.datasource.druid.password}")
    private String password;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        String str = StrUtil.subBetween(this.getUrl(), "//", "?");
        String[] strArr = StrUtil.splitToArray(str, '/');
        if (strArr.length == 2) {
            this.setDatabase(strArr[1]);
            String[] strArr1 = StrUtil.splitToArray(strArr[0], ':');
            if (strArr1.length == 2) {
                this.setPort(Integer.parseInt(strArr1[1]));
            }
            this.setHost(strArr1[0]);
        }
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port == 0 ? 3306 : port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
