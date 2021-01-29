package com.yintu.rixing.config.component;

import com.google.common.collect.Sets;
import io.swagger.v3.oas.models.media.MediaType;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiDescriptionBuilder;
import springfox.documentation.builders.MultipartFormDataParameterSpecificationProvider;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.Operation;
import springfox.documentation.service.ParameterType;
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
public class CustomSpringfoxApiDescription implements ApiListingScannerPlugin {


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
        if ("02.登录模块".equals(context.getGroupName())) {
            Operation loginOperation = new OperationBuilder(new CachingOperationNameGenerator())
                    .uniqueId("login")
                    .tags(Sets.newHashSet("登录有关接口"))
                    .method(HttpMethod.POST)
                    .summary("登录系统")
                    .notes("登录系统")
                    .requestParameters(Arrays.asList(
                            new RequestParameterBuilder()
                                    .name("username")
                                    .description("用户名")
                                    .required(true)
                                    .in(ParameterType.QUERY)
                                    .parameterIndex(1)
                                    .build(),
                            new RequestParameterBuilder()
                                    .name("username")
                                    .description("密码")
                                    .required(true)
                                    .in(ParameterType.QUERY)
                                    .parameterIndex(2)
                                    .build(),
                            new RequestParameterBuilder()
                                    .name("captcha")
                                    .description("验证码")
                                    .required(true)
                                    .in(ParameterType.QUERY)
                                    .parameterIndex(3)
                                    .build(),
                            new RequestParameterBuilder()
                                    .name("rememberMe")
                                    .in("int")
                                    .description("记住密码")
                                    .in(ParameterType.QUERY)
                                    .parameterIndex(4)
                                    .build()
                    ))
                    .produces(context.getProduces())
                    .consumes(context.getConsumes())
                    .build();

            ApiDescription login = new ApiDescriptionBuilder(context.operationOrdering())
                    .groupName("login")
                    .path("/login")
                    .pathDecorator(s -> s)
                    .operations(Collections.singletonList(loginOperation))
                    .hidden(false)
                    .build();


            Operation logoutOperation = new OperationBuilder(new CachingOperationNameGenerator())
                    .uniqueId("logout")
                    .tags(Sets.newHashSet("登录有关接口"))
                    .method(HttpMethod.POST)
                    .summary("注销系统")
                    .notes("注销系统")
                    .produces(context.getProduces())
                    .consumes(context.getConsumes())
                    .build();
            ApiDescription logout = new ApiDescriptionBuilder(context.operationOrdering())
                    .groupName("login")
                    .path("/logout")
                    .pathDecorator(s -> s)
                    .operations(Collections.singletonList(logoutOperation))
                    .hidden(false)
                    .build();


            return Arrays.asList(login, logout);
        }
        return Collections.emptyList();
    }

    /**
     * 是否使用此插件
     *
     * @param delimiter swagger文档类型
     * @return true 启用
     */
    @Override
    public boolean supports(DocumentationType delimiter) {
        return delimiter.equals(DocumentationType.OAS_30);
    }
}
