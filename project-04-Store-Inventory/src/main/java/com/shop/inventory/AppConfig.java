package com.shop.inventory;

import com.shop.infra.InfraConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import(InfraConfig.class)
public class AppConfig {
}
