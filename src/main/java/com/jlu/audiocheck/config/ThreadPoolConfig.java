package com.jlu.audiocheck.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {
    @Bean("MyThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(
            @Value("${thread-pool.core-size}") String coreSize,
            @Value("${thread-pool.max-size}") String maxSize,
            @Value("${thread-pool.queue-capacity}") String queueCapacity,
            @Value("${thread-pool.keep-alive-seconds}") String keepAliveSeconds) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Integer.parseInt(coreSize)); //核心线程数
        executor.setQueueCapacity(Integer.parseInt(maxSize)); //任务队列大小
        executor.setMaxPoolSize(Integer.parseInt(queueCapacity)); //最大线程数
        executor.setKeepAliveSeconds(Integer.parseInt(keepAliveSeconds)); //线程存在时间
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy()); //拒绝策略：默认
        executor.initialize();
        return executor;
    }
}
