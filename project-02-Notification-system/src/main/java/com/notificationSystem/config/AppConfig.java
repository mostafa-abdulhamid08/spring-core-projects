package com.notificationSystem.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.notificationSystem")
@Import(NotificationConfig.class)
public class AppConfig {
}
