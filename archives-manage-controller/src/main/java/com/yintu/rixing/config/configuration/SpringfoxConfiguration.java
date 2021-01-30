package com.yintu.rixing.config.configuration;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: mlf
 * @Date: 2021/1/29 19:12:44
 * @Version: 1.0
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SpringfoxConfiguration {

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
    public Docket make() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.make")).paths(PathSelectors.any()).build()
                .groupName("04.利用中心模块");

    }

    @Bean
    public Docket data() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.data")).paths(PathSelectors.any()).build()
                .groupName("05.数据中心模块");

    }

    @Bean
    public Docket warehouse() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.warehouse")).paths(PathSelectors.any()).build()
                .groupName("06.库房管理模块");

    }

    @Bean
    public Docket security() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.security")).paths(PathSelectors.any()).build()
                .groupName("07.安全中心模块");

    }

    @Bean
    public Docket notification() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.notification")).paths(PathSelectors.any()).build()
                .groupName("08.通知公告模块");

    }

    @Bean
    public Docket system() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.system")).paths(PathSelectors.any()).build()
                .groupName("09.系统设置模块");

    }

    @Bean
    public Docket remote() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.basePackage("com.yintu.rixing.remote")).paths(PathSelectors.any()).build()
                .groupName("10.远程借阅模块");

    }


}
