package com.yintu.rixing.config.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author: mlf
 * @Date: 2021/1/29 19:12:44
 * @Version: 1.0
 */
@Configuration
public class SpringfoxConfiguration {
    @Bean
    public Docket demo() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.demo")).build()
                .groupName("01.测试模块");

    }

    @Bean
    public Docket login() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.config.controller")).build()
                .groupName("02.登录模块");

    }

    @Bean
    public Docket common() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.common")).build()
                .groupName("03.公共模块");

    }

    @Bean
    public Docket make() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.make")).build()
                .groupName("04.利用中心模块");

    }

    @Bean
    public Docket data() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.data")).build()
                .groupName("05.数据中心模块");

    }

    @Bean
    public Docket warehouse() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.warehouse")).build()
                .groupName("06.库房管理模块");

    }

    @Bean
    public Docket security() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.security")).build()
                .groupName("07.安全中心模块");

    }

    @Bean
    public Docket notification() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.notification")).build()
                .groupName("08.通知公告模块");

    }

    @Bean
    public Docket system() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.system")).build()
                .groupName("09.系统设置模块");

    }

    @Bean
    public Docket remote() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.remote")).build()
                .groupName("10.远程借阅模块");

    }


}
