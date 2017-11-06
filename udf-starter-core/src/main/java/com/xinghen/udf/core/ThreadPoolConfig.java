package com.xinghen.udf.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 */
@Configuration
public class ThreadPoolConfig {

    @Autowired
    private ApplicationConfig applicationConfig;

    /**
     * 默认线程池
     *
     * @return
     */
    @Bean
    public Executor defaultThreadPool() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(applicationConfig.getThreadPool().getPoolSize());
        executor.setThreadPriority(applicationConfig.getThreadPool().getThreadPriority());
        executor.setThreadNamePrefix(applicationConfig.getThreadPool().getThreadNamePrefix());
        // 拒绝策略：由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
