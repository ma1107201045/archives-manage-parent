package com.yintu.rixing.config.component;

import com.yintu.rixing.util.PathUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: mlf
 * @Date: 2020/11/25 11:49
 * @Version: 1.0
 */
@Component
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(PathUtil.getGlobbingFileMapping()).addResourceLocations("file:" + PathUtil.getFilePath());
        registry.addResourceHandler(PathUtil.getGlobbingBackupMapping()).addResourceLocations("file:" + PathUtil.getBackupPath());
        registry.addResourceHandler(PathUtil.getGlobbingLogMapping()).addResourceLocations("file:" + PathUtil.getLogPath());
    }

}
