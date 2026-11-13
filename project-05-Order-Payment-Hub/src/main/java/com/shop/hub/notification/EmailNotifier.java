package com.shop.hub.notification;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class EmailNotifier implements NotificationChannel {

    @Override
    public void send(String message) {
        System.out.println("[EMAIL] " + message);
    }
}