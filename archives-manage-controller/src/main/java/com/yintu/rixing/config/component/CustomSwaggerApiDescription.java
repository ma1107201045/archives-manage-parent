package com.yintu.rixing.config.component;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiDescriptionBuilder;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.Operation;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author: mlf
 * @Date: 2020/11/28 19:27
 * @Version: 1.0
 */
@Component
public class CustomSwaggerApiDescription implements ApiListingScannerPlugin {


    /**
     * Implement this method to manually add ApiDescriptions
     * 实现此方法可手动添加ApiDescriptions
     *
     * @param context - Documentation context that can be used infer documentation context
     * @return List of {@link ApiDescription}
     * @see ApiDescription
     */
    @Override
    public List<ApiDescription> apply(DocumentationContext context) {

        Operation loginOperation = new OperationBuilder(new CachingOperationNameGenerator())
                .tags(Sets.newHashSet("登录接口"))
                .method(HttpMethod.POST)
                .summary("用户名密码登录")
                .consumes(Sets.newHashSet(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) // 接收参数格式
                .produces(Sets.newHashSet(MediaType.APPLICATION_JSON_VALUE)) // 返回参数格式
                .parameters(Arrays.asList(
                        new ParameterBuilder()
                                .description("用户名")
                                .type(new TypeResolver().resolve(String.class))
                                .name("username")
                                .defaultValue("super")
                                .parameterType("form")
                                .parameterAccess("access")
                                .required(true)
                                .modelRef(new ModelRef("string"))
                                .build(),
                        new ParameterBuilder()
                                .description("密码")
                                .type(new TypeResolver().resolve(String.class))
                                .name("password")
                                .defaultValue("123456")
                                .parameterType("form")
                                .parameterAccess("access")
                                .required(true)
                                .modelRef(new ModelRef("string"))
                                .build(),
//                        new ParameterBuilder()
//                                .description("验证码唯一标识")
//                                .type(new TypeResolver().resolve(String.class))
//                                .name("randomKey")
//                                .defaultValue("666666")
//                                .parameterType("query")
//                                .parameterAccess("access")
//                                .required(true)
//                                .modelRef(new ModelRef("string"))
//                                .build(),
                        new ParameterBuilder()
                                .description("验证码")
                                .type(new TypeResolver().resolve(String.class))
                                .name("captcha")
                                .parameterType("form")
                                .parameterAccess("access")
                                .required(true)
                                .modelRef(new ModelRef("string"))
                                .build()
                ))
                .responseMessages(Collections.singleton(
                        new ResponseMessageBuilder().code(200).message("请求成功")
                                .responseModel(new ModelRef(
                                        "com.yintu.rixing.util.ResponseDataUtil")
                                ).build()))
                .build();
        Operation logoutOperation = new OperationBuilder(new CachingOperationNameGenerator())
                .tags(Sets.newHashSet("登录接口"))
                .method(HttpMethod.POST)
                .summary("用户名密码登录")
                .consumes(Sets.newHashSet(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) // 接收参数格式
                .produces(Sets.newHashSet(MediaType.APPLICATION_JSON_VALUE)) // 返回参数格式
                .responseMessages(Collections.singleton(
                        new ResponseMessageBuilder().code(200).message("请求成功")
                                .responseModel(new ModelRef(
                                        "com.yintu.rixing.util.ResponseDataUtil")
                                ).build()))
                .build();

        ApiDescriptionBuilder apiDescriptionBuilder = new ApiDescriptionBuilder(null);
        ApiDescription login = apiDescriptionBuilder
                .groupName("login")
                .path("/login")
                .description("登录接口")
                .operations(Collections.singletonList(loginOperation))
                .hidden(false)
                .build();
        ApiDescription logout = apiDescriptionBuilder
                .groupName("login")
                .path("/logout")
                .description("登录接口")
                .operations(Collections.singletonList(logoutOperation))
                .hidden(false)
                .build();
        return Arrays.asList(login, logout);
    }

    /**
     * 是否使用此插件
     *
     * @param delimiter swagger文档类型
     * @return true 启用
     */
    @Override
    public boolean supports(DocumentationType delimiter) {
        return DocumentationType.SWAGGER_2.equals(delimiter);
    }
}
