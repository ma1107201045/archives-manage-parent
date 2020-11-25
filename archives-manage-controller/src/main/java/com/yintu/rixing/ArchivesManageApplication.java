package com.yintu.rixing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yintu.rixing")
public class ArchivesManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArchivesManageApplication.class, args);
    }
}
