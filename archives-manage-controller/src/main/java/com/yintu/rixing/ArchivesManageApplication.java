package com.yintu.rixing;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwagger2Doc
@EnableSwaggerBootstrapUI
@SpringBootApplication
public class ArchivesManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArchivesManageApplication.class, args);
    }
}
