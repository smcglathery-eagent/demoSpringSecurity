package com.dci.demo.config.swagger;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ModelRef;

@Configuration
@EnableOpenApi
abstract class SwaggerConfigBase {

    public static final String NOT_FOUND_ERROR_MESSAGE = "Not Found";
    public static final String BAD_REQUEST_ERROR_MESSAGE = "Bad Request";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal Server Error";
    public static final ModelRef ERROR_INFO_MODEL_REF = new ModelRef("ErrorInfo");
}