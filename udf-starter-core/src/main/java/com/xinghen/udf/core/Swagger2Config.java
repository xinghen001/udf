package com.xinghen.udf.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2配置文件
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * 系统版本
     */
    @Value("${info.build.version}")
    private String version;

    /**
     * 系统名称
     */
    @Value("${info.build.name}")
    private String projectName;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.xinghen")).paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(this.projectName + "online api document")
                .version(version).build();
    }

}
