package com.github.erf88.realmeet.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorsConfig {

    @Bean
    public Executor controllersExecutor(
            @Value("${realmeet.executors.core-pool-size:10}") int corePoolSize,
            @Value("${realmeet.executors.max-pool-size:20}") int maximumPoolSize,
            @Value("${realmeet.executors.queue-capacity:50}") int queueCapacity,
            @Value("${realmeet.executors.keep-alive-seconds:60}") long keepAliveSeconds) {
        return new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveSeconds,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueCapacity, true)
        );
    }

}
