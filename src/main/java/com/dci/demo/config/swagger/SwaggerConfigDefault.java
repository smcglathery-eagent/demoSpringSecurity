package com.dci.demo.config.swagger;

import com.dci.demo.exception.ErrorInfo;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableOpenApi
@Profile("!production")
public class SwaggerConfigDefault extends SwaggerConfigBase {

    @Autowired
    private TypeResolver typeResolver;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    private static final List<ResponseMessage> GET_RESPONSE_MESSAGES;

    static {

        GET_RESPONSE_MESSAGES = new ArrayList<ResponseMessage>(3);
        GET_RESPONSE_MESSAGES.add(new ResponseMessageBuilder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(BAD_REQUEST_ERROR_MESSAGE)
                .responseModel(ERROR_INFO_MODEL_REF)
                .build());
        GET_RESPONSE_MESSAGES.add(new ResponseMessageBuilder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(NOT_FOUND_ERROR_MESSAGE)
                .responseModel(ERROR_INFO_MODEL_REF)
                .build());
        GET_RESPONSE_MESSAGES.add(new ResponseMessageBuilder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(INTERNAL_SERVER_ERROR_MESSAGE)
                .responseModel(ERROR_INFO_MODEL_REF)
                .build());
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, GET_RESPONSE_MESSAGES)
                .additionalModels(typeResolver.resolve(ErrorInfo.class))
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }


    private ApiInfo apiInfo() {
        String title = "Demo Service (" + activeProfile + ")";

        return new ApiInfoBuilder()
                .title(title)
                .description("This service handles all demo operations")
                .version("1.0")
                .termsOfServiceUrl("http://google.com")
                .license("©2021 All Rights Reserved.")
                .licenseUrl("http://google.com")
                .build();
    }
}