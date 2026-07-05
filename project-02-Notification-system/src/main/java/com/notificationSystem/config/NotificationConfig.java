package com.notificationSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// توضيح @Bean — بتستخدمه لما الكلاس من library خارجية
@Configuration
public class NotificationConfig {

    @Bean
    public AppStats appStats() {
        return new AppStats("NotificationSystem", "1.0.0");
    }

    public static class AppStats {
        private final String appName;
        private final String version;

        public AppStats(String appName, String version) {
            this.appName = appName;
            this.version = version;
            System.out.println("AppStats Bean created: " + appName + " v" + version);
        }

        public void printStats() {
            System.out.println("App: " + appName + " | Version: " + version);
        }
    }
}
