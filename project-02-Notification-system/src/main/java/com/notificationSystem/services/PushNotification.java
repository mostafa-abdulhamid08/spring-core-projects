package com.notificationSystem.services;

import org.springframework.stereotype.Component;

@Component("pushNotification")
public class PushNotification implements NotificationService {

    @Override
    public void sendNotification(String message) {
        System.out.println("[PUSH] Sending: " + message);
    }

    @Override
    public String getType() {
        return "Push";
    }
}
