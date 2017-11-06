package com.xinghen.udf.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * 系统配置
 */
@Configuration
@ConfigurationProperties(prefix = "com.xinghen.application.config")
@Validated
@Data
public class ApplicationConfig {

    /**
     * 是否输出异常堆栈信息
     */
    @NotNull
    private boolean outputExceptionStackTrace = false;


    /**
     * 编码
     */
    @NotNull
    private String encoding = "UTF-8";

    /**
     * 时区
     */
    @NotNull
    private String timeZone = "Asia/Shanghai";

    /**
     * 日期格式
     */
    @NotNull
    private String dateFormat = "yyyy-MM-dd'T'HH:mm:ss:SSSZ";

    /**
     * 线程池属性
     */
    private ThreadPoolProperties threadPool;

    /**
     * 如有大文件通过restTemplate传输，请设置为false
     */
    @NotNull
    private Boolean bufferRequestBody = true;

    @Data
    public static class ThreadPoolProperties {

        /**
         * 默认线程池大小
         */
        private Integer poolSize = 1;

        /**
         * 线程优先级
         */
        private Integer threadPriority = Thread.NORM_PRIORITY;

        /**
         * 线程名称
         */
        private String threadNamePrefix = "defaultThreadPool";

    }

}
