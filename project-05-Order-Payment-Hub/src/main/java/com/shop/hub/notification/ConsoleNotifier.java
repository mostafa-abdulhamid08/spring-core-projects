package com.shop.hub.notification;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class ConsoleNotifier implements NotificationChannel {
    @Override
    public void send(String message) {
        System.out.println("[CONSOLE] " + message);
    }
}
