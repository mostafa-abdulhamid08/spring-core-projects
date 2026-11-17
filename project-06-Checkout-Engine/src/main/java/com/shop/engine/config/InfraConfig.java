package com.shop.engine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class InfraConfig {

    @Bean
    public ExecutorService checkoutPool() {
        return Executors.newVirtualThreadPerTaskExecutor();   // virtual thread جديد لكل مهمة
    }
}