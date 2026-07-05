package com.notificationSystem.beans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class AppLogger {
    // بيتشتغل بعد ما Spring ينشئ الـ Bean ويحقن كل الـ dependencies
    @PostConstruct
    public void init() {
        System.out.println("AppLogger: @PostConstruct → Logger initialized ✅");
    }

    public void log(String message) {
        System.out.println("AppLogger: " + message);
    }
    // بيتشتغل لما تعمل context.close()
    @PreDestroy
    public void destroy() {
        System.out.println("AppLogger: @PreDestroy → Logger shutting down 🛑");
    }
}
