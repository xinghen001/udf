package com.xinghen.udf.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@ServletComponentScan
@ComponentScan(basePackages = {"com.xinghen"})
@EnableAsync
@RefreshScope
@EnableRetry
@EnableScheduling
public class BaseApplication {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Bean(name = "externalRestTemplate")
    public RestTemplate externalRestTemplate(ClientHttpRequestFactory requestFactory) {
        return new RestTemplate(requestFactory);
    }

    @Bean
    public ClientHttpRequestFactory requestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(applicationConfig.getBufferRequestBody());
        return requestFactory;
    }

}
