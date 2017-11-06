package com.xinghen.udf.core.except.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Servlet;
import java.util.List;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({Servlet.class, DispatcherServlet.class})
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties(ResourceProperties.class)
public class ExceptionControllerConfig {

    @Autowired(required = false)
    private List<ErrorViewResolver> errorViewResolvers;

    private final ServerProperties serverProperties;

    public ExceptionControllerConfig(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Bean
    public ExceptionController exceptionController(ErrorAttributes errorAttributes) {
        return new ExceptionController(errorAttributes, this.serverProperties.getError(), this.errorViewResolvers);
    }
}
