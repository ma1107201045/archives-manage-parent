package com.yintu.rixing.config.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author: mlf
 * @Date: 2021/1/29 19:12:44
 * @Version: 1.0
 */
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket demo() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.demo")).paths(PathSelectors.any()).build()
                .groupName("01.测试模块");

    }

    @Bean
    public Docket login() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.config.controller")).paths(PathSelectors.any()).build()
                .groupName("02.登录模块");

    }

    @Bean
    public Docket common() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.common")).paths(PathSelectors.any()).build()
                .groupName("03.公共模块");

    }

    @Bean
    public Docket index() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.index")).paths(PathSelectors.any()).build()
                .groupName("04.首页模块");

    }

    @Bean
    public Docket person() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.person")).paths(PathSelectors.any()).build()
                .groupName("05.个人中心模块");

    }

    @Bean
    public Docket make() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.make")).paths(PathSelectors.any()).build()
                .groupName("06.利用中心模块");

    }

    @Bean
    public Docket data() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.data")).paths(PathSelectors.any()).build()
                .groupName("07.数据中心模块");

    }

    @Bean
    public Docket warehouse() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.warehouse")).paths(PathSelectors.any()).build()
                .groupName("08.库房管理模块");

    }

    @Bean
    public Docket archives() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.archives")).paths(PathSelectors.any()).build()
                .groupName("09.档案统计模板");

    }

    @Bean
    public Docket security() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.security")).paths(PathSelectors.any()).build()
                .groupName("10.安全中心模块");

    }

    @Bean
    public Docket notification() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.notification")).paths(PathSelectors.any()).build()
                .groupName("11.通知公告模块");

    }

    @Bean
    public Docket system() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.system")).paths(PathSelectors.any()).build()
                .groupName("12.系统设置模块");

    }

    @Bean
    public Docket remote() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.remote")).paths(PathSelectors.any()).build()
                .groupName("13.远程借阅模块");

    }


}
